package ru.vyacheslav.telegrambot_animalshelter_astana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    @Query(value = "SELECT COUNT (*) FROM animal", nativeQuery = true)
    int totalAmountOfAnimal();

}
