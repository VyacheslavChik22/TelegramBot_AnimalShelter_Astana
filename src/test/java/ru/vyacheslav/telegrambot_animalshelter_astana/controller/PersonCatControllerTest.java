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
import ru.vyacheslav.telegrambot_animalshelter_astana.model.PersonCat;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.PersonCatRepository;
import ru.vyacheslav.telegrambot_animalshelter_astana.service.PersonCatService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.vyacheslav.telegrambot_animalshelter_astana.service.PersonCatServiceTest.getTestPersonCat;

@WebMvcTest(controllers = PersonCatController.class)
public class PersonCatControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonCatRepository personCatRepository;

    @SpyBean
    private PersonCatService personCatService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private PersonCatController personCatController;

    @Test
    void getAllPeopleTest() throws Exception {
        PersonCat testPerson = getTestPersonCat(1L, "Test 1");

        when(personCatRepository.findAll()).thenReturn(List.of(testPerson));

        mockMvc.perform(get("/cat-people"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(testPerson))));
    }

    @Test
    void getPersonByIdTest() throws Exception {
        PersonCat testPerson = getTestPersonCat(1L, "Test 1");
        when(personCatRepository.findById(anyLong())).thenReturn(Optional.of(testPerson));

        mockMvc.perform(get("/cat-people/" + testPerson.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testPerson)));
    }

    @Test
    void getPersonById_whenNotFoundTest() throws Exception {
        when(personCatRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/cat-people/" + 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createPersonTest() throws Exception {
        PersonCat testPerson = getTestPersonCat(1L, "Test 1");
        JSONObject personObject = new JSONObject();
        personObject.put("name", testPerson.getName());

        when(personCatRepository.save(any(PersonCat.class))).thenReturn(testPerson);

        mockMvc.perform(post("/cat-people")
                        .content(personObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testPerson)));
    }

    @Test
    void createPerson_whenBadRequestTest() throws Exception {
        PersonCat testPerson = getTestPersonCat(1L, "Test 1");
        JSONObject personObject = new JSONObject();
        personObject.put("name", testPerson.getName());


        when(personCatRepository.save(any(PersonCat.class))).thenReturn(null);

        mockMvc.perform(post("/cat-people")
                        .content(personObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updatePersonTest() throws Exception {
        PersonCat testPerson = getTestPersonCat(1L, "Test 1");
        JSONObject personObject = new JSONObject();
        personObject.put("id", testPerson.getId());
        personObject.put("name", testPerson.getName());

        when(personCatRepository.existsById(anyLong())).thenReturn(true);
        when(personCatRepository.save(any(PersonCat.class))).thenReturn(testPerson);

        mockMvc.perform(put("/cat-people")
                        .content(personObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testPerson)));
    }

    @Test
    void updatePerson_whenPersonIdNotFoundTest() throws Exception {
        PersonCat testPerson = getTestPersonCat(1L, "Test 1");
        JSONObject personObject = new JSONObject();
        personObject.put("id", testPerson.getId());
        personObject.put("name", testPerson.getName());

        when(personCatRepository.existsById(testPerson.getId())).thenReturn(false);
        when(personCatRepository.save(any(PersonCat.class))).thenReturn(testPerson);

        mockMvc.perform(put("/cat-people")
                        .content(personObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(personCatRepository, never()).save(testPerson);
    }

    @Test
    void deletePersonTest() throws Exception {
        when(personCatRepository.findById(anyLong())).thenReturn(Optional.of(getTestPersonCat(1, "Test 1")));
        doNothing().when(personCatRepository).delete(any(PersonCat.class));

        mockMvc.perform(delete("/cat-people/" + 1))
                .andExpect(status().isOk());

        verify(personCatRepository, atLeastOnce()).delete(any(PersonCat.class));
    }

    @Test
    void deletePerson_whenNotFoundTest() throws Exception {
        when(personCatRepository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(personCatRepository).delete(any(PersonCat.class));

        mockMvc.perform(delete("/cat-people/" + 1))
                .andExpect(status().isNotFound());

        verify(personCatRepository, never()).delete(any(PersonCat.class));
    }
}
