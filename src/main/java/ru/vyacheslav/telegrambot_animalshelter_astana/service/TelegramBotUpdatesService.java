package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.NoAnimalAdoptedException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonAlreadyExistsException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.TextDoesNotMatchPatternException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.AnimalType;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Person;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.PersonCat;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Report;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.vyacheslav.telegrambot_animalshelter_astana.constants.TelegramBotConstants.CONTACT_DATA_PATTERN;

@Service
public class TelegramBotUpdatesService {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesService.class);
    private final Pattern pattern = Pattern.compile(CONTACT_DATA_PATTERN);

    private final PersonService personService;
    private final PersonCatService personCatService;
    private final ReportService reportService;

    public TelegramBotUpdatesService(PersonService personService, PersonCatService personCatService, ReportService reportService) {
        this.personService = personService;
        this.personCatService = personCatService;
        this.reportService = reportService;
    }

    /** Creates personDog or personCat entity from the text message and saves it in DB
     *
     * @param chatId chat identification number from the telegram {@link com.pengrad.telegrambot.model.Message} object
     * @param messageText text from the telegram {@link com.pengrad.telegrambot.model.Message} object
     * @param animalType type of animal from enum {@link AnimalType}
     * @throws PersonAlreadyExistsException if DB contains entry with such chatId
     * @throws TextDoesNotMatchPatternException if text doesn't match the pattern
     */
    public void createPersonFromMessage(Long chatId, String messageText, AnimalType animalType) {
        logger.info("Was invoked method to create person and set their contact data from the message");

        if (animalType == AnimalType.DOG) {
            createPersonDogFromMessage(chatId, messageText);
        } else {
            createPersonCatFromMessage(chatId, messageText);
        }
    }

    /**
     * Counts number of days from pet adoption until now for person with chatId
     * @param chatId chat identification number from the telegram {@link com.pengrad.telegrambot.model.Message} object
     * @return number of days
     * @throws PersonNotFoundException when there is no person with such chatId in DB
     * @throws NoAnimalAdoptedException if person doesn't have adopted animal
     */
    public Long countDaysFromAdoption(Long chatId, AnimalType animalType) {
        if (animalType == AnimalType.DOG) {
            return countDaysFromAdoptionDog(chatId);
        } else {
            return countDaysFromAdoptionCat(chatId);
        }

    }

    /**
     * Creates report entity with text and photo file data, and saves it in DB
     *
     * @param chatId chat identification number from the telegram {@link com.pengrad.telegrambot.model.Message} object
     * @param fileMap map which contains key-value pairs with photo file data
     * @param caption text from the telegram message with photo file
     * @return report entity
     * @throws PersonNotFoundException when there is no person with such chatId in DB
     * @throws NoAnimalAdoptedException if person doesn't have adopted animal
     * @throws RuntimeException with specific message when person has already sent report today
     */
    public Report createReportFromMessage(Long chatId, Map<String, Object> fileMap, String caption, AnimalType animalType) {
        logger.info("Request method createReportFromMessage");
        if (animalType == AnimalType.DOG) {
            return createDogReport(chatId, fileMap, caption);
        } else {
            return createCatReport(chatId, fileMap, caption);
        }

    }

    private Report createDogReport(Long chatId, Map<String, Object> fileMap, String caption) {
        // Проверить существует ли пользователь в нашей БД по chatId - если нет, то бросить ошибку
        Person person = personService.findPersonByChatId(chatId).orElseThrow(PersonNotFoundException::new);

        // Проверить что у пользователя есть животное
        if (person.getAnimal() == null) {
            throw new NoAnimalAdoptedException();
        }
        // Проверить дату последнего отчета у пользователя, если сегодня - бросить ошибку
        if (person.getLastReportDate() != null && person.getLastReportDate().isEqual(LocalDate.now())) {
            throw new RuntimeException("Report has sent");
        }

        Report report = new Report();
        report.setReportDate(LocalDate.now());
        report.setDescription(caption);
        report.setPerson(person);
        report.setMediaType((String) fileMap.get("mediaType"));
        report.setPhotoData((byte[]) fileMap.get("photoData"));
        report.setPhotoSize((Integer) fileMap.get("photoSize"));
        report.setPhotoPath((String) fileMap.get("photoPath"));

        person.setLastReportDate(report.getReportDate());

        personService.updatePerson(person);

        return reportService.addReport(report);
    }

    private Report createCatReport(Long chatId, Map<String, Object> fileMap, String caption) {
        // Проверить существует ли пользователь в нашей БД по chatId - если нет, то бросить ошибку
        PersonCat person = personCatService.findPersonByChatId(chatId).orElseThrow(PersonNotFoundException::new);

        // Проверить что у пользователя есть животное
        if (person.getAnimal() == null) {
            throw new NoAnimalAdoptedException();
        }
        // Проверить дату последнего отчета у пользователя, если сегодня - бросить ошибку
        if (person.getLastReportDate() != null && person.getLastReportDate().isEqual(LocalDate.now())) {
            throw new RuntimeException("Report has sent");
        }

        Report report = new Report();
        report.setReportDate(LocalDate.now());
        report.setDescription(caption);
        report.setPersonCat(person);
        report.setMediaType((String) fileMap.get("mediaType"));
        report.setPhotoData((byte[]) fileMap.get("photoData"));
        report.setPhotoSize((Integer) fileMap.get("photoSize"));
        report.setPhotoPath((String) fileMap.get("photoPath"));

        person.setLastReportDate(report.getReportDate());

        personCatService.updatePerson(person);

        return reportService.addReport(report);
    }

    /**
     * Check if string matches the pattern and return list of it parts.
     * If string doesn't match throws exception.
     *
     * @param text string to parse
     * @return list of strings: name, phone, email, address
     * @throws TextDoesNotMatchPatternException if string doesn't match the pattern
     */
    private List<String> parseText(String text) {
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            logger.info("Text matches the pattern.");
            String name = matcher.group(3);
            String phone = matcher.group(6);
            String email = matcher.group(9);
            String address = matcher.group(13);
            return List.of(name, phone, email, address);
        } else {
            logger.info("User input doesn't match the pattern: {}", text);
            throw new TextDoesNotMatchPatternException();
        }
    }

    private void createPersonCatFromMessage(Long chatId, String messageText) {
        if (personCatService.findPersonByChatId(chatId).isPresent()) {
            logger.info("Person with {} is already saved in DB", chatId);
            throw new PersonAlreadyExistsException();
        }
        // Parse message text in accordance with the pattern
        List<String> contact_data = parseText(messageText);
        // Create new person entity and set necessary values
        PersonCat person = new PersonCat();
        person.setName(contact_data.get(0));
        person.setPhone(contact_data.get(1));
        person.setEmail(contact_data.get(2));
        person.setAddress(contact_data.get(3));
        person.setChatId(chatId);
        personCatService.createPerson(person);
    }

    private void createPersonDogFromMessage(Long chatId, String messageText) {
        if (personService.findPersonByChatId(chatId).isPresent()) {
            logger.info("Person with {} is already saved in DB", chatId);
            throw new PersonAlreadyExistsException();
        }
        // Parse message text in accordance with the pattern
        List<String> contact_data = parseText(messageText);
        // Create new person entity and set necessary values
        Person person = new Person();
        person.setName(contact_data.get(0));
        person.setPhone(contact_data.get(1));
        person.setEmail(contact_data.get(2));
        person.setAddress(contact_data.get(3));
        person.setChatId(chatId);
        personService.createPerson(person);
    }

    private Long countDaysFromAdoptionDog(Long chatId) {
        Person person = personService.findPersonByChatId(chatId).orElseThrow(PersonNotFoundException::new);

        if (person.getAnimal() == null) {
            throw new NoAnimalAdoptedException();
        }

        return ChronoUnit.DAYS.between(person.getAnimalAdoptDate(), LocalDate.now());
    }

    private Long countDaysFromAdoptionCat(Long chatId) {
        PersonCat person = personCatService.findPersonByChatId(chatId).orElseThrow(PersonNotFoundException::new);

        if (person.getAnimal() == null) {
            throw new NoAnimalAdoptedException();
        }

        return ChronoUnit.DAYS.between(person.getAnimalAdoptDate(), LocalDate.now());
    }
}
