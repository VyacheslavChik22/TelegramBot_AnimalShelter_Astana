package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.AnimalNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Animal;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.AnimalRepository;

import java.util.Collection;
import java.util.Collections;

@Service
public class AnimalService {

    private final Logger logger = LoggerFactory.getLogger(AnimalService.class);
    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    /**
     * Creating of new animal in DB
     *
     * @param animal
     * @return Animal created
     */
    public Animal createAnimal(Animal animal) {
        logger.info("Was invoked method to create new animal");
        return animalRepository.save(animal);
    }

    /**
     * Search for an animal  in DB by ID
     * The repository method is being used{@link JpaRepository#findById(Object)}
     *
     * @param id - animal ID, can't be {@code null}
     * @return Animal found
     * @throws AnimalNotFoundException if no entry was found in DB
     */
    public Animal getAnimalInfoById(long id) {
        logger.info("Was invoked method to get animal bi ID: {}", id);
        return animalRepository.findById(id).orElseThrow(AnimalNotFoundException::new);
    }

    /**
     * Counter of the total number of animals in DB
     *
     * @return Total amount of animals
     */
    public int totalAmountOfAnimal() {
        logger.info("Was invoked method for checking amount of animal in shelter");
        return animalRepository.totalAmountOfAnimal();
    }

    /**
     * List of all animals in the shelter
     *
     * @return List of animals
     */
    public Collection<Animal> getAllAnimals() {
        logger.info("Was invoked method to get all animal");
        return Collections.unmodifiableCollection(animalRepository.findAll());
    }

    /**
     * Delete animal from DB
     *
     * @param id
     * @throws AnimalNotFoundException if no entry was found in DB
     */
    public void deleteAnimal(Long id) {
        logger.info("Was invoked method to delete animal with ID: {}", id);
        Animal animal = getAnimalInfoById(id);
        animalRepository.delete(animal);
    }
}
