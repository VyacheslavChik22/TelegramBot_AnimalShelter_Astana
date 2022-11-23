package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.NoAnimalAdoptedException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonAlreadyExistsException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.TextDoesNotMatchPatternException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Person;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.PersonRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.vyacheslav.telegrambot_animalshelter_astana.constants.TelegramBotConstants.CONTACT_DATA_PATTERN;

/**
 * @author Oleg Alekseenko
 */
@Service
public class PersonService {
    private final Logger logger = LoggerFactory.getLogger(PersonService.class);
    private final Pattern pattern = Pattern.compile(CONTACT_DATA_PATTERN);

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    /**
     * Finds all entries in DB.
     *
     * @return unmodifiable collection
     */
    public Collection<Person> findAll() {
        logger.info("Was invoked method to get all people");
        return Collections.unmodifiableCollection(personRepository.findAll());
    }

    /**
     * Finds record for person in DB.
     *
     * @param id identification number for person
     * @return person model
     * @throws PersonNotFoundException if no entry was found in DB
     */
    public Person findPerson(long id) {
        logger.info("Was invoked method for find person by id: {}", id);
        return personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    public Person createPerson(Person person) {
        logger.info("Was invoked method for create person");
        return personRepository.save(person);
    }

    public Person updatePerson(Person person) {
        logger.info("Was invoked method for update person");
        if (person.getId() == null || !personRepository.existsById(person.getId())) {
            logger.warn("Person with id: '{}' not found and could not be updated.", person.getId());
            throw new PersonNotFoundException();
        }
        return personRepository.save(person);
    }

    /**
     * Delete person from the DB
     *
     * @param id identification number for person
     * @throws PersonNotFoundException if no entry was found in DB
     */
    public void deletePerson(long id) {
        logger.info("Was invoked method for delete person by id: {}", id);
        Person person = findPerson(id);
        personRepository.delete(person);
    }

    /**
     * Creates person entity from the text message and saves it in DB
     *
     * @param chatId chat identification number from the telegram {@link com.pengrad.telegrambot.model.Message} object
     * @param messageText text from the telegram {@link com.pengrad.telegrambot.model.Message} object
     * @return person entity
     * @throws PersonAlreadyExistsException if DB contains entry with such chatId
     */
    public Person createPersonFromMessage(Long chatId, String messageText) {
        logger.info("Was invoked method to create person and set their contact data from the message");

        if (personRepository.findPersonByChatId(chatId).isPresent()) {
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
        // Save new person with contact details to DB
        return createPerson(person);
    }

    /**
     * Counts number of days from pet adoption until now for person with chatId
     * @param chatId chat identification number from the telegram {@link com.pengrad.telegrambot.model.Message} object
     * @return number of days
     * @throws PersonNotFoundException when there is no person with such chatId in DB
     * @throws NoAnimalAdoptedException if person doesn't have adopted animal
     */
    public Long countDaysFromAdoption(Long chatId) {
        Person person = personRepository.findPersonByChatId(chatId).orElseThrow(PersonNotFoundException::new);

        if (person.getAnimal() == null) {
            throw new NoAnimalAdoptedException();
        }

        return ChronoUnit.DAYS.between(person.getAnimalAdoptDate(), LocalDate.now());
    }

    /**
     * Check if string matches the pattern and return list of it parts.
     * If string doesn't match throws exception.
     *
     * @param text string to parse
     * @return list of strings: name, phone, email, address
     * @throws TextDoesNotMatchPatternException
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

}
