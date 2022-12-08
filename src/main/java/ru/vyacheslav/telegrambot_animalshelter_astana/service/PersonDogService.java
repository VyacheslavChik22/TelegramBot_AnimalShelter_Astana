package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.PersonDog;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.PersonDogRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Oleg Alekseenko
 */
@Service
public class PersonDogService {
    private final Logger logger = LoggerFactory.getLogger(PersonDogService.class);

    private final PersonDogRepository personDogRepository;

    public PersonDogService(PersonDogRepository personDogRepository) {
        this.personDogRepository = personDogRepository;
    }


    /**
     * Finds all entries in DB.
     *
     * @return unmodifiable collection
     */
    public Collection<PersonDog> findAll() {
        logger.info("Was invoked method to get all people");
        return Collections.unmodifiableCollection(personDogRepository.findAll());
    }

    /**
     * Finds record for person in DB.
     *
     * @param id identification number for person
     * @return person model
     * @throws PersonNotFoundException if no entry was found in DB
     */
    public PersonDog findPerson(long id) {
        logger.info("Was invoked method for find person by id: {}", id);
        return personDogRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    /**
     * Create person with dog in DB and save
     */
    public PersonDog createPerson(PersonDog person) {
        logger.info("Was invoked method for create person");
        return personDogRepository.save(person);
    }

    /**
     * Update person with dog from the DB and save
     *
     * @throws PersonNotFoundException if no entry was found in DB
     */
    public PersonDog updatePerson(PersonDog person) {
        logger.info("Was invoked method for update person");
        if (person.getId() == null || !personDogRepository.existsById(person.getId())) {
            logger.warn("Person with id: '{}' not found and could not be updated.", person.getId());
            throw new PersonNotFoundException();
        }
        return personDogRepository.save(person);
    }

    /**
     * Find all persons with dogs, who do not have a report today
     */
    public List<PersonDog> findAllByLastReportDateBefore(LocalDate date) {
        logger.info("Was invoked method to find all dog people with last report date before {}", date);
        return personDogRepository.findAllByLastReportDateBeforeOrLastReportDateIsNullAndAnimalIsNotNull(date);
    }

    /**
     * Delete person from the DB
     *
     * @param id identification number for person with dog
     * @throws PersonNotFoundException if no entry was found in DB
     */
    public void deletePerson(long id) {
        logger.info("Was invoked method for delete person by id: {}", id);
        PersonDog person = findPerson(id);
        personDogRepository.delete(person);
    }

    /**
     * Find record for person with dog by chatId.
     *
     * @param chatId telegram chat identification number
     * @return Optional of person with dog
     */
    public Optional<PersonDog> findPersonByChatId(Long chatId) {
        return personDogRepository.findPersonByChatId(chatId);
    }
}
