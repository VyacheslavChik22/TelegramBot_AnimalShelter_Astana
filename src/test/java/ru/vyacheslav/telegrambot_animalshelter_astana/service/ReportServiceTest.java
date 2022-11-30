package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.ReportNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Report;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.PersonRepository;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.ReportRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

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

    public static Report addTestReport(long id, String description) {
        Report testReport = new Report();
        testReport.setId(id);
        testReport.setDescription(description);
        return testReport;
    }

}
