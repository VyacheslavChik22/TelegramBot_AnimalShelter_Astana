package ru.vyacheslav.telegrambot_animalshelter_astana.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Report;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.service.ReportService;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * We receive all reports
     */
    @GetMapping("/all")
    public List<Report> getAllReports() {
        return reportService.findAllReports();
    }

    /**
     * Getting a report by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportInfo(@PathVariable Long id) {
        Report report = reportService.findReport(id);
        if (report == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(report);
    }

    /**
     * Editing the report
     */
    @PutMapping
    public ResponseEntity<Report> editStudent(@RequestBody Report report) {
        Report foundReport = reportService.editReport(report);
        if (foundReport == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundReport);
    }

    /**
     * Delete the report
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Report> deleteStudent(@PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        reportService.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
