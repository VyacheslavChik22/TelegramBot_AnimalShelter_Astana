package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.PersonDog;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.PersonDogRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**Unit tests for {@link PersonDogService} class with {@link PersonDogRepository} mock.
 *
 * @author Oleg Alekseenko
 */

@ExtendWith(MockitoExtension.class)
public class PersonDogServiceTest {
    @Mock
    private PersonDogRepository personDogRepository;

    @InjectMocks
    private PersonDogService out;

    @Test
    void shouldCreateNewPerson() {
        PersonDog testPerson = getTestPerson(1, "Test 1");

        when(personDogRepository.save(any(PersonDog.class))).thenReturn(testPerson);
        PersonDog result = out.createPerson(testPerson);

        assertThat(result).isEqualTo(testPerson);
        assertThat(result.getId()).isEqualTo(testPerson.getId());
    }

    @Test
    void shouldReturnAllPeople() {
        when(personDogRepository.findAll()).thenReturn(List.of(new PersonDog(), new PersonDog()));
        Collection<PersonDog> result = out.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldReturnPersonById() {
        PersonDog testPerson = getTestPerson(1, "Test 1");

        when(personDogRepository.findById(anyLong())).thenReturn(Optional.of(testPerson));
        PersonDog result = out.findPerson(testPerson.getId());

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(testPerson);
    }

    @Test
    void shouldThrowPersonNotFoundException_whenPersonByIdNotFoundInDB() {
        when(personDogRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> out.findPerson(anyLong())).isInstanceOf(PersonNotFoundException.class);
    }

    @Test
    void shouldUpdatePerson() {
        PersonDog testPerson = getTestPerson(1, "Test 1");

        when(personDogRepository.existsById(testPerson.getId())).thenReturn(true);
        when(personDogRepository.save(any(PersonDog.class))).thenReturn(testPerson);
        PersonDog result = out.updatePerson(testPerson);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(testPerson);
    }

    @Test
    void shouldThrowPersonNotFoundException_whenUpdatePersonWithWrongId() {
        PersonDog testPerson = getTestPerson(1, "Test 1");

        when(personDogRepository.existsById(testPerson.getId())).thenReturn(false);

        assertThatThrownBy(() -> out.updatePerson(testPerson)).isInstanceOf(PersonNotFoundException.class);
        verify(personDogRepository, never()).save(testPerson);
    }

    @Test
    void shouldThrowPersonNotFoundException_whenDeleteByWrongId() {
        when(personDogRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> out.deletePerson(anyLong())).isInstanceOf(PersonNotFoundException.class);
    }

    @Test
    void shouldReturnOptionalWithPerson_whenFindPersonByChatId() {
        Long testChatId = 123L;
        PersonDog testPerson = getTestPerson(1L, "Test 1");
        testPerson.setChatId(testChatId);

        when(personDogRepository.findPersonByChatId(anyLong())).thenReturn(Optional.of(testPerson));

        Optional<PersonDog> result = out.findPersonByChatId(testChatId);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testPerson);
    }

    @Test
    void shouldReturnEmptyOptional_whenPersonWithChatIdNotFoundInDB() {
        Long testChatId = 123L;

        when(personDogRepository.findPersonByChatId(anyLong())).thenReturn(Optional.empty());

        Optional<PersonDog> result = out.findPersonByChatId(testChatId);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnAnEmptyList_whenNoPeopleFoundByLastReportDateBefore() {
        LocalDate testDate = LocalDate.now();
        when(personDogRepository.findAllByLastReportDateBeforeOrLastReportDateIsNullAndAnimalIsNotNull(testDate)).thenReturn(Collections.emptyList());

        List<PersonDog> result = out.findAllByLastReportDateBefore(testDate);

        assertThat(result).hasSize(0);
    }

    @Test
    void shouldReturnAListWithOnePerson_whenPeopleFoundByLastReportDateBefore() {
        LocalDate testDate = LocalDate.now();
        PersonDog testPerson = getTestPerson(1L, "Test");

        when(personDogRepository.findAllByLastReportDateBeforeOrLastReportDateIsNullAndAnimalIsNotNull(testDate)).thenReturn(List.of(testPerson));

        List<PersonDog> result = out.findAllByLastReportDateBefore(testDate);

        assertThat(result).hasSize(1);
        assertThat(result).contains(testPerson);
    }

    public static PersonDog getTestPerson(long id, String name) {
        PersonDog testPerson = new PersonDog();
        testPerson.setId(id);
        testPerson.setName(name);
        return testPerson;
    }
}
