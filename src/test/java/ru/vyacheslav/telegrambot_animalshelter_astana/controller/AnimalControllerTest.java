package ru.vyacheslav.telegrambot_animalshelter_astana.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Animal;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.AnimalRepository;
import ru.vyacheslav.telegrambot_animalshelter_astana.service.AnimalService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AnimalController.class)
public class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalRepository animalRepository;

    @SpyBean
    private AnimalService animalService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private AnimalController animalController;

    @Test
    public void createAnimalTest() throws Exception {
        Long id = 1L;
        String name = "Dog";

        JSONObject animalObj = new JSONObject();
        animalObj.put("id", id);
        animalObj.put("name", name);

        Animal animal = new Animal();
        animal.setId(id);
        animal.setName(name);

        when(animalRepository.save(any(Animal.class))).thenReturn(animal);
        when(animalRepository.findById(id)).thenReturn(Optional.of(animal));

        mockMvc.perform(post("/animals")
                        .content(animalObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(animal)));
    }

    @Test
    public void getAnimalTest() throws Exception {
        Long id = 1L;
        String name = "Dog";

        Animal animal = new Animal();
        animal.setId(id);
        animal.setName(name);

        when(animalRepository.findById(id)).thenReturn(Optional.of(animal));

        mockMvc.perform(get("/animals/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(animal)));
    }

    @Test
    public void totalAmountOfAnimalTest() throws Exception {
        Long id = 1L;
        String name = "Dog";

        Animal animal = new Animal();
        animal.setId(id);
        animal.setName(name);

       when(animalRepository.totalAmountOfAnimal()).thenReturn(1);

        mockMvc.perform(get("/animals/count_animals")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllAnimalsTest() throws Exception {
        Long id = 1L;
        String name = "Dog";

        Animal animal = new Animal();
        animal.setId(id);
        animal.setName(name);

        when(animalRepository.findAll()).thenReturn(List.of(animal));

        mockMvc.perform(get("/animals/all_animals")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(animal))));
    }

    @Test
    public void deleteAnimalTest() throws Exception {
        Long id = 1L;
        String name = "Dog";

        Animal animal = new Animal();
        animal.setId(id);
        animal.setName(name);

        when(animalRepository.findById(id)).thenReturn(Optional.of(animal));
        doNothing().when(animalRepository).delete(any(Animal.class));

        mockMvc.perform(delete("/animals/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(animalRepository, atLeastOnce()).delete(any(Animal.class));

    }

}
