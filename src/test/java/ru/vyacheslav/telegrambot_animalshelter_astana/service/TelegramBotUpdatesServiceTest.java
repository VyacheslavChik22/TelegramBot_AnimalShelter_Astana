package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.NoAnimalAdoptedException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonAlreadyExistsException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.TextDoesNotMatchPatternException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static ru.vyacheslav.telegrambot_animalshelter_astana.service.PersonServiceTest.getTestPerson;
import static ru.vyacheslav.telegrambot_animalshelter_astana.service.ReportServiceTest.addTestReport;

/**Unit tests for {@link TelegramBotUpdatesService} class with {@link PersonService} mock.
 *
 * @author Oleg Alekseenko
 */

@ExtendWith(MockitoExtension.class)
public class TelegramBotUpdatesServiceTest {

    @Mock
    private PersonService personService;
    @Mock
    private PersonCatService personCatService;
    @Mock
    private ReportService reportService;

    @InjectMocks
    private TelegramBotUpdatesService out;

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

        when(personService.findPersonByChatId(anyLong())).thenReturn(Optional.empty());
        when(personService.createPerson(any(Person.class))).thenReturn(testPerson);

        out.createPersonFromMessage(1L, testMessage, AnimalType.DOG);

        verify(personService, atLeastOnce()).createPerson(any(Person.class));
        verify(personCatService, never()).createPerson(any(PersonCat.class));
    }

    @Test
    void shouldThrowPersonAlreadyExistsException_whenCreateNewPersonFromContactData() {
        when(personService.findPersonByChatId(anyLong())).thenReturn(Optional.of(getTestPerson(1L, "Test")));

        assertThatThrownBy(() -> out.createPersonFromMessage(anyLong(), "anyString()", AnimalType.DOG)).isInstanceOf(PersonAlreadyExistsException.class);
        verify(personService, never()).createPerson(any(Person.class));
    }

    @Test
    void shouldThrowTextPatternDoesNotMatchException_whenCreateNewPersonFromWrongMessage() {
        String testMessage = "Имя: Test;\n" +
                "Телефон: +79;\n" +
                "Почта: test";

        when(personService.findPersonByChatId(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> out.createPersonFromMessage(anyLong(), testMessage, AnimalType.DOG)).isInstanceOf(TextDoesNotMatchPatternException.class);
        verify(personService, never()).createPerson(any(Person.class));
    }

    @Test
    void shouldThrowNoAnimalException_whenCountDaysFromAdoptionForPersonWithoutAnimal() {
        Person testPerson = getTestPerson(1L, "Test");

        when(personService.findPersonByChatId(anyLong())).thenReturn(Optional.of(testPerson));

        assertThatThrownBy(() -> out.countDaysFromAdoption(anyLong(), AnimalType.DOG)).isInstanceOf(NoAnimalAdoptedException.class);
    }

    @Test
    void shouldThrowPersonNotFoundException_whenCreateNewReportForUserNotInDB() {
        when(personService.findPersonByChatId(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> out.createReportFromMessage(anyLong(), null, null, AnimalType.DOG)).isInstanceOf(PersonNotFoundException.class);
        verify(personService, never()).updatePerson(any(Person.class));
        verify(reportService, never()).addReport(any(Report.class));
    }

    @Test
    void shouldThrowException_whenCreateReportWithoutAnimal() {
        Person testPerson = new Person();
        testPerson.setId(1L);
        testPerson.setChatId(1L);

        when(personService.findPersonByChatId(anyLong())).thenReturn(Optional.of(testPerson));

        assertThatThrownBy(() -> out.createReportFromMessage(testPerson.getChatId(), new HashMap<>(), "null", AnimalType.DOG))
                .isInstanceOf(NoAnimalAdoptedException.class);

        verify(personService, never()).updatePerson(any(Person.class));
        verify(reportService, never()).addReport(any(Report.class));
    }

    @Test
    void shouldThrowException_whenCreateReportWithTheSameDate() {
        Person testPerson = new Person();
        testPerson.setId(1L);
        testPerson.setChatId(1L);
        testPerson.setAnimal(new Animal(1L, "Test cat", AnimalType.DOG));
        testPerson.setLastReportDate(LocalDate.now());

        when(personService.findPersonByChatId(anyLong())).thenReturn(Optional.of(testPerson));

        assertThatThrownBy(() -> out.createReportFromMessage(testPerson.getChatId(), new HashMap<>(), "null", AnimalType.DOG))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Report has sent");

        verify(personService, never()).updatePerson(any(Person.class));
        verify(reportService, never()).addReport(any(Report.class));
    }

    @Test
    void shouldCreateNewReportFromMessage() {
        Person testPerson = new Person();
        testPerson.setId(1L);
        testPerson.setChatId(1L);
        testPerson.setAnimal(new Animal(1L, "Test cat", AnimalType.DOG));

        String caption = "text";

        Map<String, Object> fileFields = new HashMap<>();
        fileFields.put("mediaType", "type");
        fileFields.put("photoData", new byte[1]);
        fileFields.put("photoSize", 1);
        fileFields.put("photoPath", "path");

        Report testReport = addTestReport(1L, caption);
        testReport.setReportDate(LocalDate.now());
        testReport.setPhotoPath((String) fileFields.get("photoPath"));
        testReport.setPhotoSize((Integer) fileFields.get("photoSize"));
        testReport.setPhotoData((byte[]) fileFields.get("photoData"));
        testReport.setMediaType((String) fileFields.get("mediaType"));
        testReport.setPerson(testPerson);

        when(personService.findPersonByChatId(anyLong())).thenReturn(Optional.of(testPerson));
        when(reportService.addReport(any(Report.class))).thenReturn(testReport);

        Report result = out.createReportFromMessage(testPerson.getChatId(), fileFields, caption, AnimalType.DOG);

        assertThat(result).isNotNull()
                .isInstanceOf(Report.class)
                .isEqualTo(testReport);

    }


}
