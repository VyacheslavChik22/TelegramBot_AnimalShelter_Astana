package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import com.pengrad.telegrambot.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Person;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.PersonRepository;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Oleg Alekseenko
 */
@Service
public class PersonService {
    private final Logger logger = LoggerFactory.getLogger(PersonService.class);

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

    public void createPersonFromMessage(String messageText) {
        // TODO: 21.11.2022 Парсим сообщение на соответствие шаблону (создать приватный метод парсинга)
        // TODO: 21.11.2022 Создаем нового Person и устанавливаем нужные поля
        // TODO: 21.11.2022 Сохраняем пользователя в базу
        if (messageText.length() < 5) {
            // TODO: 21.11.2022 Исключения перенести в метод парсинга сообщения
            throw new RuntimeException("Телефон неверный");
        }
    }

    private void parseText(String text) {

    }
}
