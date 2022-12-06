package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.AnimalType;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.PersonContext;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.PersonContextRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonContextServiceTest {
    @Mock
    private PersonContextRepository personContextRepository;

    @InjectMocks
    private PersonContextService out;

    @Test
    void shouldCreatePersonContext() {
        PersonContext testContext = new PersonContext(1L, 123L, AnimalType.DOG);
        when(personContextRepository.save(any(PersonContext.class))).thenReturn(testContext);
        PersonContext result = out.createPersonContext(testContext.getChatId(), testContext.getAnimalType());

        assertThat(result).isNotNull();
        verify(personContextRepository, atLeastOnce()).save(any(PersonContext.class));
    }

    @Test
    void shouldCallCreatePersonContext_whenUpdatePersonContextForChatIdNotInDB() {
        PersonContext testContext = new PersonContext(1L, 123L, AnimalType.DOG);
        when(personContextRepository.save(any(PersonContext.class))).thenReturn(testContext);
        when(personContextRepository.findByChatId(anyLong())).thenReturn(Optional.empty());

        PersonContext result = out.updatePersonContext(anyLong(), null);

        verify(personContextRepository, atLeastOnce()).findByChatId(anyLong());
        verify(personContextRepository, atLeastOnce()).save(any(PersonContext.class));
    }

    @Test
    void shouldCallCreatePersonContext_whenUpdatePersonContextForChatIdFromDB() {
        PersonContext testContext = new PersonContext(1L, 123L, AnimalType.DOG);
        when(personContextRepository.save(any(PersonContext.class))).thenReturn(testContext);
        when(personContextRepository.findByChatId(anyLong())).thenReturn(Optional.of(testContext));

        PersonContext result = out.updatePersonContext(testContext.getChatId(), null);

        verify(personContextRepository, atLeastOnce()).findByChatId(anyLong());
        verify(personContextRepository, atLeastOnce()).save(any(PersonContext.class));
    }

    @Test
    void shouldReturnAnimalTypeByChatId() {
        PersonContext testContext = new PersonContext(1L, 123L, AnimalType.DOG);
        when(personContextRepository.findByChatId(anyLong())).thenReturn(Optional.of(testContext));

        AnimalType result = out.getAnimalType(testContext.getChatId());
        assertThat(result).isEqualTo(testContext.getAnimalType());
        verify(personContextRepository, atLeastOnce()).findByChatId(anyLong());
    }

    @Test
    void shouldThrowRuntimeException_whenReturnAnimalTypeByChatIdNotFoundInDB() {
        PersonContext testContext = new PersonContext(1L, 123L, AnimalType.DOG);
        when(personContextRepository.findByChatId(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> out.getAnimalType(anyLong())).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("There is no chatId");
    }
}
