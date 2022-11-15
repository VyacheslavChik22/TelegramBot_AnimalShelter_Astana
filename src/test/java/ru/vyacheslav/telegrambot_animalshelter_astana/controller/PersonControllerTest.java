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
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Person;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.PersonRepository;
import ru.vyacheslav.telegrambot_animalshelter_astana.service.PersonService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.vyacheslav.telegrambot_animalshelter_astana.service.PersonServiceTest.getTestPerson;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository personRepository;

    @SpyBean
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private PersonController personController;

    @Test
    void getAllPeopleTest() throws Exception {
        Person testPerson = getTestPerson(1L, "Test 1");

        when(personRepository.findAll()).thenReturn(List.of(testPerson));

        mockMvc.perform(get("/people"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(testPerson))));
    }

    @Test
    void getPersonByIdTest() throws Exception {
        Person testPerson = getTestPerson(1L, "Test 1");
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(testPerson));

        mockMvc.perform(get("/people/" + testPerson.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testPerson)));
    }

    @Test
    void getPersonById_whenNotFoundTest() throws Exception {
        when(personRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/people/" + 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createPersonTest() throws Exception {
        Person testPerson = getTestPerson(1L, "Test 1");
        JSONObject personObject = new JSONObject();
        personObject.put("name", testPerson.getName());

        when(personRepository.save(any(Person.class))).thenReturn(testPerson);

        mockMvc.perform(post("/people")
                        .content(personObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testPerson)));
    }

    @Test
    void createPerson_whenBadRequestTest() throws Exception {
        Person testPerson = getTestPerson(1L, "Test 1");
        JSONObject personObject = new JSONObject();
        personObject.put("name", testPerson.getName());

        when(personRepository.save(any(Person.class))).thenReturn(null);

        mockMvc.perform(post("/people")
                        .content(personObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updatePersonTest() throws Exception {
        Person testPerson = getTestPerson(1L, "Test 1");
        JSONObject personObject = new JSONObject();
        personObject.put("name", testPerson.getName());

        when(personRepository.save(any(Person.class))).thenReturn(testPerson);

        mockMvc.perform(put("/people")
                        .content(personObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testPerson)));
    }

    @Test
    void deletePersonTest() throws Exception {
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(getTestPerson(1, "Test 1")));
        doNothing().when(personRepository).delete(any(Person.class));

        mockMvc.perform(delete("/people/" + 1))
                .andExpect(status().isOk());

        verify(personRepository, atLeastOnce()).delete(any(Person.class));
    }

    @Test
    void deletePerson_whenNotFoundTest() throws Exception {
        when(personRepository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(personRepository).delete(any(Person.class));

        mockMvc.perform(delete("/people/" + 1))
                .andExpect(status().isNotFound());

        verify(personRepository, never()).delete(any(Person.class));
    }
}
