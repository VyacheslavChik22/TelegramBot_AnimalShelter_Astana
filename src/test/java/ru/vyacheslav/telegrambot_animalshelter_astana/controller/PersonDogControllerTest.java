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
import ru.vyacheslav.telegrambot_animalshelter_astana.model.PersonDog;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.PersonDogRepository;
import ru.vyacheslav.telegrambot_animalshelter_astana.service.PersonDogService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.vyacheslav.telegrambot_animalshelter_astana.service.PersonDogServiceTest.getTestPerson;

/**Integration tests for {@link PersonDogController} class.
 *
 * @author Oleg Alekseenko
 */
@WebMvcTest(controllers = PersonDogController.class)
public class PersonDogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonDogRepository personRepository;

    @SpyBean
    private PersonDogService personService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private PersonDogController personController;

    @Test
    void getAllPeopleTest() throws Exception {
        PersonDog testPerson = getTestPerson(1L, "Test 1");

        when(personRepository.findAll()).thenReturn(List.of(testPerson));

        mockMvc.perform(get("/dog-people"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(testPerson))));
    }

    @Test
    void getPersonByIdTest() throws Exception {
        PersonDog testPerson = getTestPerson(1L, "Test 1");
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(testPerson));

        mockMvc.perform(get("/dog-people/" + testPerson.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testPerson)));
    }

    @Test
    void getPersonById_whenNotFoundTest() throws Exception {
        when(personRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/dog-people/" + 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createPersonTest() throws Exception {
        PersonDog testPerson = getTestPerson(1L, "Test 1");
        JSONObject personObject = new JSONObject();
        personObject.put("name", testPerson.getName());

        when(personRepository.save(any(PersonDog.class))).thenReturn(testPerson);

        mockMvc.perform(post("/dog-people")
                        .content(personObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testPerson)));
    }

    @Test
    void createPerson_whenBadRequestTest() throws Exception {
        PersonDog testPerson = getTestPerson(1L, "Test 1");
        JSONObject personObject = new JSONObject();
        personObject.put("name", testPerson.getName());


        when(personRepository.save(any(PersonDog.class))).thenReturn(null);

        mockMvc.perform(post("/dog-people")
                        .content(personObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updatePersonTest() throws Exception {
        PersonDog testPerson = getTestPerson(1L, "Test 1");
        JSONObject personObject = new JSONObject();
        personObject.put("id", testPerson.getId());
        personObject.put("name", testPerson.getName());

        when(personRepository.existsById(anyLong())).thenReturn(true);
        when(personRepository.save(any(PersonDog.class))).thenReturn(testPerson);

        mockMvc.perform(put("/dog-people")
                        .content(personObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testPerson)));
    }

    @Test
    void updatePerson_whenPersonIdNotFoundTest() throws Exception {
        PersonDog testPerson = getTestPerson(1L, "Test 1");
        JSONObject personObject = new JSONObject();
        personObject.put("id", testPerson.getId());
        personObject.put("name", testPerson.getName());

        when(personRepository.existsById(testPerson.getId())).thenReturn(false);
        when(personRepository.save(any(PersonDog.class))).thenReturn(testPerson);

        mockMvc.perform(put("/dog-people")
                        .content(personObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(personRepository, never()).save(testPerson);
    }

    @Test
    void deletePersonTest() throws Exception {
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(getTestPerson(1, "Test 1")));
        doNothing().when(personRepository).delete(any(PersonDog.class));

        mockMvc.perform(delete("/dog-people/" + 1))
                .andExpect(status().isOk());

        verify(personRepository, atLeastOnce()).delete(any(PersonDog.class));
    }

    @Test
    void deletePerson_whenNotFoundTest() throws Exception {
        when(personRepository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(personRepository).delete(any(PersonDog.class));

        mockMvc.perform(delete("/dog-people/" + 1))
                .andExpect(status().isNotFound());

        verify(personRepository, never()).delete(any(PersonDog.class));
    }
}
