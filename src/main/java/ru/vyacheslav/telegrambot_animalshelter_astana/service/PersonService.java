package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
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


    public Collection<Person> findAll() {
        logger.info("Was invoked method to get all people");
        return Collections.unmodifiableCollection(personRepository.findAll());
    }
}
