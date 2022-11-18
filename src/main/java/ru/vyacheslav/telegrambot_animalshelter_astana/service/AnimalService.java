package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Animal;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.AnimalRepository;

import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    /**
     * Creating of new animal in DB
     * @param animal
     * @return Animal created
     */
    public Animal createAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    /**
     * Search for an animal  in DB by ID
     * The repository method is being used{@link JpaRepository#findById(Object)}
     * @param id - animal ID, can't be {@code null}
     * @return Animal found
     */
    public Animal getAnimalInfoById(long id) {
        return animalRepository.findById(id).orElseThrow();
    }

    /**
     *Counter of the total number of animals in DB
     * @return Total amount of animals
     */
    public int totalAmountOfAnimal() {
        return animalRepository.totalAmountOfAnimal();
    }

    /**
     *List of all animals in the shelter, paginated
     * @param pageNumber
     * @param pageSize
     * @return List of animals by pages
     */
    public List<Animal> allAnimalWithPagination(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        return animalRepository.findAll(pageRequest).getContent();
    }

}
