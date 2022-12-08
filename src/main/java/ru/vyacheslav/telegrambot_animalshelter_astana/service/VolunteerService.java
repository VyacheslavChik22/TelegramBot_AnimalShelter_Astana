package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.AnimalNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.VolunteerNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Volunteer;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.VolunteerRepository;

import java.util.Collection;
import java.util.Collections;

@Service
public class VolunteerService {

    private final Logger logger = LoggerFactory.getLogger(VolunteerService.class);
    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    /**
     * Creating of new volunteer in DB
     * @param volunteer
     * @return Volunteer created
     */
    public Volunteer createVolunteer(Volunteer volunteer) {
        logger.info("Was invoked method to create new volunteer");
        return volunteerRepository.save(volunteer);
    }

    /**
     * Search for volunteer in DB by ID
     * The repository method is being used{@link JpaRepository#findById(Object)}
     * @param id - volunteer ID, can't be {@code null}
     * @throws VolunteerNotFoundException if no entry was found in DB
     * @return Volunteer found
     */
    public Volunteer getVolunteerInfoById(Long id) {
        logger.info("Was invoked method to get volunteer by ID: {}", id);
        return volunteerRepository.findById(id).orElseThrow(VolunteerNotFoundException::new);
    }

    /**
     *List of all volunteers in the shelter
     * @return List of volunteers
     */
    public Collection<Volunteer> getAllVolunteers() {
        logger.info("Was invoked method to get list of all volunteers");
        return Collections.unmodifiableCollection(volunteerRepository.findAll());
    }

    /**
     * Delete animal from DB
     * @param id
     * @throws AnimalNotFoundException if no entry was found in DB
     */
    public void deleteVolunteer(Long id){
        logger.info("Was invoked method to delete volunteer with ID: {}", id);
        Volunteer volunteer = getVolunteerInfoById(id);
        volunteerRepository.delete(volunteer);
    }
}
