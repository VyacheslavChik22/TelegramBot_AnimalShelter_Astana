package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Animal;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.AnimalRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

    @ExtendWith(MockitoExtension.class)
    public class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AnimalService animalService;

    @Test
    public void createAnimalTest(){
        Animal testAnimal = new Animal();
        testAnimal.setName("Dog");
        testAnimal.setId(1L);

    when(animalRepository.save(testAnimal)).thenReturn(testAnimal);
    assertThat(animalRepository.save(testAnimal)).isEqualTo(animalService.createAnimal(testAnimal));
}

    @Test
    public void getAnimalTest() {
        Animal firstAnimal = new Animal();
        firstAnimal.setName("Dog1");
        firstAnimal.setId(1L);
        Animal secondAnimal = new Animal();
        secondAnimal.setName("Dog2");
        secondAnimal.setId(2L);
        animalService.createAnimal(firstAnimal);
        animalService.createAnimal(secondAnimal);

        when(animalRepository.findById(2L)).thenReturn(Optional.of(secondAnimal));
        assertThat(animalRepository.findById(2L)).contains(animalService.getAnimalInfoById(2L));
    }

}
