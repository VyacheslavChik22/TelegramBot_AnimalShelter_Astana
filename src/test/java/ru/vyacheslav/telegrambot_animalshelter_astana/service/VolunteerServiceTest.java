package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.VolunteerNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Volunteer;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.VolunteerRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VolunteerServiceTest {

    @Mock
    private VolunteerRepository volunteerRepository;

    @InjectMocks
    private VolunteerService volunteerService;

    @Test
    public void createVolunteerTest() {
        Volunteer testVolunteer = new Volunteer(1L, "Ivan", " i@m.ru", " 880088");

        when(volunteerRepository.save(testVolunteer)).thenReturn(testVolunteer);
        assertThat(volunteerRepository.save(testVolunteer))
                .isEqualTo(volunteerService.createVolunteer(testVolunteer));
    }

    @Test
    public void getVolunteerTest () {
        Volunteer firstVolunteer = new Volunteer(1L, "Ivan", " i@m.ru", " 880088");
        Volunteer secondVolunteer = new Volunteer(2L, "Petr", " p@m.ru", " 770077");
        volunteerService.createVolunteer(firstVolunteer);
        volunteerService.createVolunteer(secondVolunteer);

        when(volunteerRepository.findById(2L)).thenReturn(Optional.of(secondVolunteer));
        assertThat(volunteerRepository.findById(2L))
                .contains(volunteerService.getVolunteerInfoById(2L));
    }

    @Test
    public void getAllVolunteer() {
        Volunteer firstVolunteer = new Volunteer(1L, "Ivan", " i@m.ru", " 880088");
        Volunteer secondVolunteer = new Volunteer(2L, "Petr", " p@m.ru", " 770077");
        volunteerService.createVolunteer(firstVolunteer);
        volunteerService.createVolunteer(secondVolunteer);

        when(volunteerRepository.findAll()).thenReturn(List.of(firstVolunteer, secondVolunteer));
        Assertions.assertThat(volunteerService.getAllVolunteers()).hasSize(2);
    }

    @Test
    public void deleteVolunteerTest() {
        Volunteer firstVolunteer = new Volunteer(1L, "Ivan", " i@m.ru", " 880088");
        volunteerService.createVolunteer(firstVolunteer);

        when(volunteerRepository.findById(1L)).thenReturn(Optional.of(firstVolunteer));
        volunteerService.deleteVolunteer(1L);
        Assertions.assertThat(volunteerService.getAllVolunteers()).isEmpty();
    }

    @Test
    void getVolunteerExceptionTest() {
        when(volunteerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> volunteerService.getVolunteerInfoById(anyLong()))
                .isInstanceOf(VolunteerNotFoundException.class);
    }

    @Test
    void deleteVolunteerExceptionTest() {
        when(volunteerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> volunteerService.deleteVolunteer(anyLong()))
                .isInstanceOf(VolunteerNotFoundException.class);
    }
}
