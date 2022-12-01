package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.PersonCat;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.PersonCatRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonCatServiceTest {
    @Mock
    private PersonCatRepository personCatRepository;

    @InjectMocks
    private PersonCatService out;

    @Test
    void shouldCreateNewPerson() {
        PersonCat testPerson = getTestPersonCat(1, "Test 1");

        when(personCatRepository.save(any(PersonCat.class))).thenReturn(testPerson);
        PersonCat result = out.createPerson(testPerson);

        assertThat(result).isEqualTo(testPerson);
        assertThat(result.getId()).isEqualTo(testPerson.getId());
    }

    @Test
    void shouldReturnAllPeople() {
        when(personCatRepository.findAll()).thenReturn(List.of(new PersonCat(), new PersonCat()));
        Collection<PersonCat> result = out.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldReturnPersonById() {
        PersonCat testPerson = getTestPersonCat(1, "Test 1");

        when(personCatRepository.findById(anyLong())).thenReturn(Optional.of(testPerson));
        PersonCat result = out.findPerson(testPerson.getId());

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(testPerson);
    }

    @Test
    void shouldThrowPersonNotFoundException_whenPersonByIdNotFoundInDB() {
        when(personCatRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> out.findPerson(anyLong())).isInstanceOf(PersonNotFoundException.class);
    }

    @Test
    void shouldUpdatePerson() {
        PersonCat testPerson = getTestPersonCat(1, "Test 1");

        when(personCatRepository.existsById(testPerson.getId())).thenReturn(true);
        when(personCatRepository.save(any(PersonCat.class))).thenReturn(testPerson);
        PersonCat result = out.updatePerson(testPerson);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(testPerson);
    }

    @Test
    void shouldThrowPersonNotFoundException_whenUpdatePersonWithWrongId() {
        PersonCat testPerson = getTestPersonCat(1, "Test 1");

        when(personCatRepository.existsById(testPerson.getId())).thenReturn(false);

        assertThatThrownBy(() -> out.updatePerson(testPerson)).isInstanceOf(PersonNotFoundException.class);
        verify(personCatRepository, never()).save(testPerson);
    }

    @Test
    void shouldThrowPersonNotFoundException_whenDeleteByWrongId() {
        when(personCatRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> out.deletePerson(anyLong())).isInstanceOf(PersonNotFoundException.class);
    }

    @Test
    void shouldReturnOptionalWithPerson_whenFindPersonByChatId() {
        Long testChatId = 123L;
        PersonCat testPerson = getTestPersonCat(1L, "Test 1");
        testPerson.setChatId(testChatId);

        when(personCatRepository.findPersonByChatId(anyLong())).thenReturn(Optional.of(testPerson));

        Optional<PersonCat> result = out.findPersonByChatId(testChatId);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testPerson);
    }

    @Test
    void shouldReturnEmptyOptional_whenPersonWithChatIdNotFoundInDB() {
        Long testChatId = 123L;

        when(personCatRepository.findPersonByChatId(anyLong())).thenReturn(Optional.empty());

        Optional<PersonCat> result = out.findPersonByChatId(testChatId);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnAnEmptyList_whenNoPeopleFoundByLastReportDateBefore() {
        LocalDate testDate = LocalDate.now();
        when(personCatRepository.findAllByLastReportDateBeforeOrLastReportDateIsNullAndAnimalIsNotNull(testDate)).thenReturn(Collections.emptyList());

        List<PersonCat> result = out.findAllByLastReportDateBefore(testDate);

        assertThat(result).hasSize(0);
    }

    @Test
    void shouldReturnAListWithOnePerson_whenPeopleFoundByLastReportDateBefore() {
        LocalDate testDate = LocalDate.now();
        PersonCat testPerson = getTestPersonCat(1L, "Test");

        when(personCatRepository.findAllByLastReportDateBeforeOrLastReportDateIsNullAndAnimalIsNotNull(testDate)).thenReturn(List.of(testPerson));

        List<PersonCat> result = out.findAllByLastReportDateBefore(testDate);

        assertThat(result).hasSize(1);
        assertThat(result).contains(testPerson);
    }

    public static PersonCat getTestPersonCat(long id, String name) {
        PersonCat testPerson = new PersonCat();
        testPerson.setId(id);
        testPerson.setName(name);
        return testPerson;
    }
}
