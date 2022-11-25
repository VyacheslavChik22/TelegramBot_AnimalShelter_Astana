package ru.vyacheslav.telegrambot_animalshelter_astana.service;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.NoAnimalAdoptedException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.ReportNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Animal;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Person;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Report;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.PersonRepository;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.ReportRepository;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    ReportRepository reportRepository;
    @Mock
    PersonRepository personRepository;

    @InjectMocks
    private ReportService out;

    @Test
    void shouldAddNewReport() {
        Report testReport = addTestReport(1, "description_1");

        when(reportRepository.save(any(Report.class))).thenReturn(testReport);
        Report result = out.addReport(testReport);

        assertThat(result).isEqualTo(testReport);
        assertThat(result.getId()).isEqualTo(testReport.getId());
    }

    @Test
    void shouldFindAllReports() {
        when(reportRepository.findAll()).thenReturn(List.of(new Report(), new Report(), new Report()));
        Collection<Report> result = out.findAllReports();

        assertThat(result).hasSize(3);
    }

    @Test
    void shouldFindReportById() {
        Report reportTest = addTestReport(5, "description_5");

        when(reportRepository.findById(anyLong())).thenReturn(Optional.of(reportTest));
        Report result = out.findReport(reportTest.getId());

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(reportTest);

    }


    @Test
    void shouldThrowPersonNotFoundExceptionIfRequestedIdNotFound() {
        when(reportRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> out.findReport(anyLong())).isInstanceOf(ReportNotFoundException.class);
    }

    @Test
    void shouldEditNewReport() {
        Report testReport = addTestReport(1, "description_1");

        when(reportRepository.save(any(Report.class))).thenReturn(testReport);
        Report result = out.editReport(1L, testReport);

        assertThat(result).isEqualTo(testReport);
        assertThat(result.getId()).isEqualTo(testReport.getId());
    }

    @Test
    void shouldThrowPersonNotFoundException_whenCreateNewReportForUserNotInDB() {
        when(personRepository.findPersonByChatId(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> out.createReportFromMessage(anyLong(), null, null)).isInstanceOf(PersonNotFoundException.class);
        verify(personRepository, never()).save(any(Person.class));
        verify(reportRepository, never()).save(any(Report.class));
    }

    @Test
    void shouldThrowException_whenCreateReportWithoutAnimal() {
        Person testPerson = new Person();
        testPerson.setId(1L);
        testPerson.setChatId(1L);

        when(personRepository.findPersonByChatId(anyLong())).thenReturn(Optional.of(testPerson));

        assertThatThrownBy(() -> out.createReportFromMessage(testPerson.getChatId(), new HashMap<>(), "null"))
                .isInstanceOf(NoAnimalAdoptedException.class);

        verify(personRepository, never()).save(any(Person.class));
        verify(reportRepository, never()).save(any(Report.class));
    }

    @Test
    void shouldThrowException_whenCreateReportWithTheSameDate() {
        Person testPerson = new Person();
        testPerson.setId(1L);
        testPerson.setChatId(1L);
        testPerson.setAnimal(new Animal(1L, "Test cat"));
        testPerson.setLastReportDate(LocalDate.now());

        when(personRepository.findPersonByChatId(anyLong())).thenReturn(Optional.of(testPerson));

        assertThatThrownBy(() -> out.createReportFromMessage(testPerson.getChatId(), new HashMap<>(), "null"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Report has sent");

        verify(personRepository, never()).save(any(Person.class));
        verify(reportRepository, never()).save(any(Report.class));
    }

    @Test
    void shouldCreateNewReportFromMessage() {
        Person testPerson = new Person();
        testPerson.setId(1L);
        testPerson.setChatId(1L);
        testPerson.setAnimal(new Animal(1L, "Test cat"));

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

        when(personRepository.findPersonByChatId(anyLong())).thenReturn(Optional.of(testPerson));
        when(reportRepository.save(any(Report.class))).thenReturn(testReport);

        Report result = out.createReportFromMessage(testPerson.getChatId(), fileFields, caption);

        assertThat(result).isNotNull()
                .isInstanceOf(Report.class)
                .isEqualTo(testReport);

    }

    public static Report addTestReport(long id, String description) {
        Report testReport = new Report();
        testReport.setId(id);
        testReport.setDescription(description);
        return testReport;
    }

}
