package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.NoAnimalAdoptedException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonAlreadyExistsException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.TextPatternDoesNotMatchException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Person;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.PersonRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**Unit tests for {@link PersonService} class with {@link PersonRepository} mock.
 *
 * @author Oleg Alekseenko
 */

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService out;

    @Test
    void shouldCreateNewPerson() {
        Person testPerson = getTestPerson(1, "Test 1");

        when(personRepository.save(any(Person.class))).thenReturn(testPerson);
        Person result = out.createPerson(testPerson);

        assertThat(result).isEqualTo(testPerson);
        assertThat(result.getId()).isEqualTo(testPerson.getId());
    }

    @Test
    void shouldReturnAllPeople() {
        when(personRepository.findAll()).thenReturn(List.of(new Person(), new Person()));
        Collection<Person> result = out.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldReturnPersonById() {
        Person testPerson = getTestPerson(1, "Test 1");

        when(personRepository.findById(anyLong())).thenReturn(Optional.of(testPerson));
        Person result = out.findPerson(testPerson.getId());

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(testPerson);
    }

    @Test
    void shouldThrowPersonNotFoundException_whenPersonByIdNotFoundInDB() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> out.findPerson(anyLong())).isInstanceOf(PersonNotFoundException.class);
    }

    @Test
    void shouldUpdatePerson() {
        Person testPerson = getTestPerson(1, "Test 1");

        when(personRepository.existsById(testPerson.getId())).thenReturn(true);
        when(personRepository.save(any(Person.class))).thenReturn(testPerson);
        Person result = out.updatePerson(testPerson);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(testPerson);
    }

    @Test
    void shouldThrowPersonNotFoundException_whenUpdatePersonWithWrongId() {
        Person testPerson = getTestPerson(1, "Test 1");

        when(personRepository.existsById(testPerson.getId())).thenReturn(false);

        assertThatThrownBy(() -> out.updatePerson(testPerson)).isInstanceOf(PersonNotFoundException.class);
        verify(personRepository, never()).save(testPerson);
    }

    @Test
    void shouldThrowPersonNotFoundException_whenDeleteByWrongId() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> out.deletePerson(anyLong())).isInstanceOf(PersonNotFoundException.class);
    }

    @Test
    void shouldCreateNewPerson_whenContactDataReceived() {
        Person testPerson = new Person();
        testPerson.setName("Test");
        testPerson.setPhone("79031234567");
        testPerson.setEmail("test@gmail.com");
        testPerson.setAddress("City");
        testPerson.setChatId(1L);

        String testMessage = "Имя: Test;\n" +
                "Телефон: +79031234567;\n" +
                "Email: test@gmail.com;\n" +
                "Адрес: City";

        when(personRepository.findPersonByChatId(anyLong())).thenReturn(Optional.empty());
        when(personRepository.save(any(Person.class))).thenReturn(testPerson);

        Person result = out.createPersonFromMessage(1L, testMessage);

        assertThat(result).isEqualTo(testPerson);
        verify(personRepository, atLeastOnce()).save(testPerson);
    }

    @Test
    void shouldThrowPersonAlreadyExistsException_whenCreateNewPersonFromContactData() {
        when(personRepository.findPersonByChatId(anyLong())).thenReturn(Optional.of(getTestPerson(1L, "Test")));

        assertThatThrownBy(() -> out.createPersonFromMessage(anyLong(), "anyString()")).isInstanceOf(PersonAlreadyExistsException.class);
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    void shouldThrowTextPatternDoesNotMatchException_whenCreateNewPersonFromWrongMessage() {
        String testMessage = "Имя: Test;\n" +
                "Телефон: +79;\n" +
                "Почта: test";

        when(personRepository.findPersonByChatId(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> out.createPersonFromMessage(anyLong(), testMessage)).isInstanceOf(TextPatternDoesNotMatchException.class);
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    void shouldThrowNoAnimalException_whenCountDaysFromAdoptionForPersonWithoutAnimal() {
        Person testPerson = getTestPerson(1L, "Test");

        when(personRepository.findPersonByChatId(anyLong())).thenReturn(Optional.of(testPerson));

        assertThatThrownBy(() -> out.countDaysFromAdoption(anyLong())).isInstanceOf(NoAnimalAdoptedException.class);
    }

    public static Person getTestPerson(long id, String name) {
        Person testPerson = new Person();
        testPerson.setId(id);
        testPerson.setName(name);
        return testPerson;
    }
}
