package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.PersonCat;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.PersonCatRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PersonCatService {

    private final Logger logger = LoggerFactory.getLogger(PersonDogService.class);

    private final PersonCatRepository personCatRepository;

    public PersonCatService(PersonCatRepository personCatRepository) {
        this.personCatRepository = personCatRepository;
    }

    /**
     * Finds all entries in DB.
     *
     * @return unmodifiable collection
     */
    public Collection<PersonCat> findAll() {
        logger.info("Was invoked method to get all people");
        return Collections.unmodifiableCollection(personCatRepository.findAll());
    }

    /**
     * Finds record for person in DB.
     *
     * @param id identification number for person
     * @return person model
     * @throws PersonNotFoundException if no entry was found in DB
     */
    public PersonCat findPerson(long id) {
        logger.info("Was invoked method for find person by id: {}", id);
        return personCatRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    public PersonCat createPerson(PersonCat personCat) {
        logger.info("Was invoked method for create person");
        return personCatRepository.save(personCat);
    }

    public PersonCat updatePerson(PersonCat personCat) {
        logger.info("Was invoked method for update person");
        if (personCat.getId() == null || !personCatRepository.existsById(personCat.getId())) {
            logger.warn("Person with id: '{}' not found and could not be updated.", personCat.getId());
            throw new PersonNotFoundException();
        }
        return personCatRepository.save(personCat);
    }

    /**
     * Delete person from the DB
     *
     * @param id identification number for person
     * @throws PersonNotFoundException if no entry was found in DB
     */
    public void deletePerson(long id) {
        logger.info("Was invoked method for delete person by id: {}", id);
        PersonCat personCat = findPerson(id);
        personCatRepository.delete(personCat);
    }

    /**
     * Find record for person by chatId.
     *
     * @param chatId telegram chat identification number
     * @return Optional of person
     */
    public Optional<PersonCat> findPersonByChatId(Long chatId) {
        return personCatRepository.findPersonByChatId(chatId);
    }

    public List<PersonCat> findAllByLastReportDateBefore(LocalDate date) {
        logger.info("Was invoked method to find all cat people with last report date before {}", date);
        return personCatRepository.findAllByLastReportDateBeforeAndAnimalIsNotNull(date);
    }
}
