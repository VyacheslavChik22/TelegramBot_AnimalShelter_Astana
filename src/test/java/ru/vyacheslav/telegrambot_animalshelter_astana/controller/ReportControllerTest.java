package ru.vyacheslav.telegrambot_animalshelter_astana.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Report;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.ReportRepository;
import static org.mockito.ArgumentMatchers.any;
import java.time.LocalDate;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReportController.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportController reportController;


    @Test
    public void saveReportsTest() throws Exception {
        byte[] photoData = {10};
        LocalDate reportDate = LocalDate.of(2022, 11, 15);
        JSONObject reportObject = new JSONObject();
        reportObject.put("Report1", new Report(1L, "path1", 320, "jpg", photoData, "test", reportDate));

        Report report = new Report();
        report.setId(1l);
        report.setPhotoPath("path1");
        report.setPhotoSize(320);
        report.setMediaType("jpg");
        report.setPhotoData(photoData);
        report.setDescription("test");
        report.setReportDate(reportDate);

        when(reportRepository.save(any(Report.class))).thenReturn(report);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/report")
                        .content(reportObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk());
        /*        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.PhotoPath").value("path1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.PhotoSize").value(320))
                .andExpect(MockMvcResultMatchers.jsonPath("$.MediaType").value("jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.PhotoData").value(photoData))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Description").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ReportDate").value(reportDate));*/


    }
}
















/*
    @Test
    void getReportInfo() {
    }

    @Test
    void editReport() {
    }

    @Test
    void deleteReport() {
    }
}*/
