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
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Volunteer;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.VolunteerRepository;
import ru.vyacheslav.telegrambot_animalshelter_astana.service.VolunteerService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VolunteerController.class)
public class VolunteerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolunteerRepository volunteerRepository;

    @SpyBean
    private VolunteerService volunteerService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private VolunteerController volunteerController;

    @Test
    public void createVolunteerTest() throws Exception {
        Long id = 1L;
        String name = "Ivan";
        String email = "i@m.ru";
        String phone = "880088";

        JSONObject volunteerObj = new JSONObject();
        volunteerObj.put("id", id);
        volunteerObj.put("name", name);
        volunteerObj.put("email", email);
        volunteerObj.put("phone", phone);

        Volunteer volunteer = new Volunteer();
        volunteer.setId(id);
        volunteer.setName(name);
        volunteer.setEmail(email);
        volunteer.setPhone(phone);

        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(volunteer);
        when(volunteerRepository.findById(id)).thenReturn(Optional.of(volunteer));

        mockMvc.perform(post("/volunteers")
                        .content(volunteerObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(volunteer)));
    }

    @Test
    public void getAnimalTest() throws Exception {
        Long id = 1L;
        String name = "Ivan";
        String email = "i@m.ru";
        String phone = "880088";

        Volunteer volunteer = new Volunteer();
        volunteer.setId(id);
        volunteer.setName(name);
        volunteer.setEmail(email);
        volunteer.setPhone(phone);

        when(volunteerRepository.findById(id)).thenReturn(Optional.of(volunteer));

        mockMvc.perform(get("/volunteers/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(volunteer)));
    }

    @Test
    public void getAllVolunteersTest() throws Exception {
        Long id = 1L;
        String name = "Ivan";
        String email = "i@m.ru";
        String phone = "880088";

        Volunteer volunteer = new Volunteer();
        volunteer.setId(id);
        volunteer.setName(name);
        volunteer.setEmail(email);
        volunteer.setPhone(phone);

        when(volunteerRepository.findAll()).thenReturn(List.of(volunteer));

        mockMvc.perform(get("/volunteers/all_volunteers")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(volunteer))));
    }

    @Test
    public void deleteVolunteerTest() throws Exception {
        Long id = 1L;
        String name = "Ivan";
        String email = "i@m.ru";
        String phone = "880088";

        Volunteer volunteer = new Volunteer();
        volunteer.setId(id);
        volunteer.setName(name);
        volunteer.setEmail(email);
        volunteer.setPhone(phone);

        when(volunteerRepository.findById(id)).thenReturn(Optional.of(volunteer));
        doNothing().when(volunteerRepository).delete(any(Volunteer.class));

        mockMvc.perform(delete("/volunteers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(volunteerRepository, atLeastOnce()).delete(any(Volunteer.class));
    }
}
