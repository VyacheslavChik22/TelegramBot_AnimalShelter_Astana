package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.vyacheslav.telegrambot_animalshelter_astana.dto.ContactDataDto;
import ru.vyacheslav.telegrambot_animalshelter_astana.dto.FotoObjectDto;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.NoAnimalAdoptedException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonAlreadyExistsException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.TextDoesNotMatchPatternException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static ru.vyacheslav.telegrambot_animalshelter_astana.constants.TelegramBotConstants.CONTACT_DATA_PATTERN;

@Service
public class TelegramBotUpdatesService {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesService.class);
    private final Pattern pattern = Pattern.compile(CONTACT_DATA_PATTERN);

    private final PersonDogService personDogService;
    private final PersonCatService personCatService;
    private final ReportService reportService;

    public TelegramBotUpdatesService(PersonDogService personDogService, PersonCatService personCatService, ReportService reportService) {
        this.personDogService = personDogService;
        this.personCatService = personCatService;
        this.reportService = reportService;
    }

    /**
     * Creates personDog or personCat entity from the text message and saves it in DB
     *
     * @param chatId      chat identification number from the telegram {@link com.pengrad.telegrambot.model.Message} object
     * @param messageText text from the telegram {@link com.pengrad.telegrambot.model.Message} object
     * @param animalType  type of animal from enum {@link AnimalType}
     * @throws PersonAlreadyExistsException     if DB contains entry with such chatId
     * @throws TextDoesNotMatchPatternException if text doesn't match the pattern
     */
    public void createPersonFromMessage(Long chatId, String messageText, AnimalType animalType) {
        logger.info("Was invoked method to create person and set their contact data from the message");
        Predicate<Long> existsPerson = cId -> animalType == AnimalType.DOG ?
                personDogService.findPersonByChatId(cId).isPresent() :
                personCatService.findPersonByChatId(cId).isPresent();
        AbstractPerson person = personProducer(chatId, animalType, existsPerson);

        // Parse message text in accordance with the pattern
        ContactDataDto contactData = parseText(messageText);
        // Set necessary values to abstract person entity
        person.setName(contactData.getName());
        person.setPhone(contactData.getPhone());
        person.setEmail(contactData.getEmail());
        person.setAddress(contactData.getAddress());
        person.setChatId(chatId);
        // Cast AbstractPerson based on AnimalType and use the proper service
        if (person instanceof PersonDog) {
            personDogService.createPerson((PersonDog) person);
        } else {
            personCatService.createPerson((PersonCat) person);
        }
    }

    private AbstractPerson personProducer(Long chatId,
                                          AnimalType animalType,
                                          Predicate<Long> existsPredicate) {
        if (existsPredicate.test(chatId)) {
            logger.info("Person with {} is already saved in DB", chatId);
            throw new PersonAlreadyExistsException();
        }
        return animalType == AnimalType.DOG ? new PersonDog() : new PersonCat();
    }

    /**
     * Counts number of days from pet adoption until now for person with chatId
     *
     * @param chatId chat identification number from the telegram {@link com.pengrad.telegrambot.model.Message} object
     * @return number of days
     * @throws PersonNotFoundException  when there is no person with such chatId in DB
     * @throws NoAnimalAdoptedException if person doesn't have adopted animal
     */
    public Long countDaysFromAdoption(Long chatId, AnimalType animalType) {
        AbstractPerson person = getAbstractPerson(chatId, animalType);

        if (person.getAnimal() == null) {
            throw new NoAnimalAdoptedException();
        }

        return ChronoUnit.DAYS.between(person.getAnimalAdoptDate(), LocalDate.now());
    }

    private AbstractPerson getAbstractPerson(Long chatId, AnimalType animalType) {

        return animalType == AnimalType.DOG ?
                personDogService.findPersonByChatId(chatId).orElseThrow(PersonNotFoundException::new)
                : personCatService.findPersonByChatId(chatId).orElseThrow(PersonNotFoundException::new);
    }

    /**
     * Creates report entity with text and photo file data, and saves it in DB
     *
     * @param chatId        chat identification number from the telegram {@link com.pengrad.telegrambot.model.Message} object
     * @param fotoObjectDto map which contains key-value pairs with photo file data
     * @param caption       text from the telegram message with photo file
     * @return report entity
     * @throws PersonNotFoundException  when there is no person with such chatId in DB
     * @throws NoAnimalAdoptedException if person doesn't have adopted animal
     * @throws RuntimeException         with specific message when person has already sent report today
     */
    public Report createReportFromMessage(Long chatId, FotoObjectDto fotoObjectDto, String caption, AnimalType animalType) {
        logger.info("Request method createReportFromMessage");
        AbstractPerson person = getAbstractPerson(chatId, animalType);

        if (person.getAnimal() == null) {
            throw new NoAnimalAdoptedException();
        }
        // Check last report date from user, if today - throw error
        if (person.getLastReportDate() != null && person.getLastReportDate().isEqual(LocalDate.now())) {
            throw new RuntimeException("Report has sent");
        }
        Report report = getNewReport(fotoObjectDto, caption);
        person.setLastReportDate(report.getReportDate());

        if (animalType == AnimalType.DOG) {
            report.setPersonDog((PersonDog) person);
            personDogService.updatePerson((PersonDog) person);
        } else {
            report.setPersonCat((PersonCat) person);
            personCatService.updatePerson((PersonCat) person);
        }
        return reportService.addReport(report);
    }

    /**
     * Get new report from user
     *
     * @return report
     */
    private Report getNewReport(FotoObjectDto fotoObjDto, String caption) {
        Report report = new Report();
        report.setReportDate(LocalDate.now());
        report.setDescription(caption);
        report.setMediaType(fotoObjDto.getMediaType());
        report.setPhotoData(fotoObjDto.getPhotoData());
        report.setPhotoSize(fotoObjDto.getPhotoSize());
        report.setPhotoPath(fotoObjDto.getPhotoPath());
        return report;
    }

    /**
     * Check if string matches the pattern and return list of it parts.
     * If string doesn't match throws exception.
     *
     * @param text string to parse
     * @return list of strings: name, phone, email, address
     * @throws TextDoesNotMatchPatternException if string doesn't match the pattern
     */
    private ContactDataDto parseText(String text) {
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            logger.info("Text matches the pattern.");
            ContactDataDto dto = new ContactDataDto();
            dto.setName(matcher.group(3));
            dto.setPhone(matcher.group(6));
            dto.setEmail(matcher.group(9));
            dto.setAddress(matcher.group(13));
            return dto;
        } else {
            logger.info("User input doesn't match the pattern: {}", text);
            throw new TextDoesNotMatchPatternException();
        }
    }

    //Find people who did not send the report on time for a reminder to submit the report
    public List<Long> findPeopleToRemind() {
        LocalDate date = LocalDate.now();
        List<PersonDog> dogPeopleNoReports = personDogService.findAllByLastReportDateBefore(date);
        logger.info("{} people with dogs were found with last report date before {}", dogPeopleNoReports.size(), date);
        List<PersonCat> catPeopleNoReports = personCatService.findAllByLastReportDateBefore(date);
        logger.info("{} people with cats were found with last report date before {}", catPeopleNoReports.size(), date);
        List<Long> peopleWithoutReports = dogPeopleNoReports.stream().map(PersonDog::getChatId).collect(Collectors.toList());
        peopleWithoutReports.addAll(catPeopleNoReports.stream().map(PersonCat::getChatId).collect(Collectors.toList()));
        logger.info("Total number of people to remind about report: {}", peopleWithoutReports.size());

        return peopleWithoutReports;
    }
}
