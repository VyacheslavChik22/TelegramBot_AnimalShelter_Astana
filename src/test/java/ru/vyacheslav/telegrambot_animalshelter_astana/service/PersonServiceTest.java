package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Person;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.PersonRepository;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService out;

    @Test
    void shouldCreateNewPerson() {
        Person testPerson = new Person();
        testPerson.setId(1L);
        testPerson.setName("Test 1");

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
}
