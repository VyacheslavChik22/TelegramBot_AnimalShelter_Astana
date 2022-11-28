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
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Report;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.PersonRepository;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.ReportRepository;
import ru.vyacheslav.telegrambot_animalshelter_astana.service.ReportService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.vyacheslav.telegrambot_animalshelter_astana.service.ReportServiceTest.addTestReport;

@WebMvcTest(ReportController.class)
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportRepository reportRepository;

    @MockBean
    private PersonRepository personRepository;

    @SpyBean
    private ReportService reportService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private ReportController reportController;


    @Test
    void getAllReportsTest() throws Exception {

        Report testReport = addTestReport(1L, "test_text_report");
        when(reportRepository.findAll()).thenReturn(List.of(testReport));

        mockMvc.perform(get("/reports"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(testReport))));
    }

    @Test
    void getReportByIdTest() throws Exception {
        Report testReport = addTestReport(1L, "test_text_report");
        when(reportRepository.findById(anyLong())).thenReturn(Optional.of(testReport));

        mockMvc.perform(get("/reports/" + testReport.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testReport)));
    }


    @Test
    void getReportById_whenNotFoundTest() throws Exception {
        when(reportRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/report" + 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void updateReportTest() throws Exception {
        Report testReport = addTestReport(1L, "test_text_report");
        JSONObject reportObject = new JSONObject();
        reportObject.put("id", testReport.getId());
        reportObject.put("description", testReport.getDescription());

        when(reportRepository.save(any(Report.class))).thenReturn(testReport);

        mockMvc.perform(put("/reports/" + 1)
                        .content(reportObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testReport)));

    }

    @Test
    void deleteReportTest() throws Exception {
        doNothing().when(reportRepository).deleteById(anyLong());
        mockMvc.perform(delete("/reports/" + 1))
                .andExpect(status().isOk());

        verify(reportRepository, atLeastOnce()).deleteById(anyLong());


    }
}

