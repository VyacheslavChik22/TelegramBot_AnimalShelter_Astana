package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.AnimalNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Animal;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.AnimalType;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.AnimalRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AnimalService animalService;

    @Test
    public void createAnimalTest(){
        Animal testAnimal = new Animal(1L, "  ", AnimalType.DOG);

    when(animalRepository.save(testAnimal)).thenReturn(testAnimal);
    assertThat(animalRepository.save(testAnimal)).isEqualTo(animalService.createAnimal(testAnimal));
}

    @Test
    public void getAnimalTest() {
        Animal firstAnimal = new Animal(1L, " ", AnimalType.CAT);
        Animal secondAnimal = new Animal(2L," ", AnimalType.DOG);
        animalService.createAnimal(firstAnimal);
        animalService.createAnimal(secondAnimal);

        when(animalRepository.findById(2L)).thenReturn(Optional.of(secondAnimal));
        assertThat(animalRepository.findById(2L)).contains(animalService.getAnimalInfoById(2L));
    }

    @Test
    public void getAllAnimalsTest(){
        Animal firstAnimal = new Animal(1L," ", AnimalType.CAT);
        Animal secondAnimal = new Animal(2L, "  ", AnimalType.DOG);
        animalService.createAnimal(firstAnimal);
        animalService.createAnimal(secondAnimal);

        when(animalRepository.findAll()).thenReturn(List.of(firstAnimal, secondAnimal));
        Assertions.assertThat(animalService.getAllAnimals()).hasSize(2);
    }

    @Test
    public void totalAmountOfAnimalTest(){
        Animal firstAnimal = new Animal(1L," ", AnimalType.CAT);
        Animal secondAnimal = new Animal(2L, "    ", AnimalType.CAT);
        Animal thirdAnimal = new Animal(3L," ", AnimalType.DOG);
        animalService.createAnimal(firstAnimal);
        animalService.createAnimal(secondAnimal);
        animalService.createAnimal(thirdAnimal);

        when(animalRepository.totalAmountOfAnimal()).thenReturn(3);
        Assertions.assertThat(animalService.totalAmountOfAnimal()).isEqualTo(3);
    }

    @Test
    public void deleteAnimalTest() {
        Animal firstAnimal = new Animal(1L,"   ", AnimalType.CAT);
        animalService.createAnimal(firstAnimal);

        when(animalRepository.findById(1L)).thenReturn(Optional.of(firstAnimal));
        animalService.deleteAnimal(1L);
        Assertions.assertThat(animalService.getAllAnimals()).isEmpty();
    }

        @Test
        void getAnimalExceptionTest() {
            when(animalRepository.findById(anyLong())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> animalService.getAnimalInfoById(anyLong()))
                    .isInstanceOf(AnimalNotFoundException.class);
        }

        @Test
        void deleteAnimalExceptionTest() {
            when(animalRepository.findById(anyLong())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> animalService.deleteAnimal(anyLong()))
                    .isInstanceOf(AnimalNotFoundException.class);
        }

        public static Animal testAnimal(long id, String name) {
            Animal testAnimal = new Animal();
            testAnimal.setId(id);
            testAnimal.setName(name);
            return testAnimal;
        }
}
