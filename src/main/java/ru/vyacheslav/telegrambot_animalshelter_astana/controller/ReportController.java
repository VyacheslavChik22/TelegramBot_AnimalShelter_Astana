package ru.vyacheslav.telegrambot_animalshelter_astana.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Report;
import ru.vyacheslav.telegrambot_animalshelter_astana.service.ReportService;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


    @Operation(
            summary = "Get all reports from DB",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Каталог найденых отчетов от владельцев за все время",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report[].class)


                            )
                    )
            })

    /**
     * We receive all reports
     */
    @GetMapping("/all")
    public List<Report> getAllReports() {
        return reportService.findAllReports();
    }


    @Operation(
            summary = "Finding a report by its id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Находим отчет по его id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report[].class)


                            )
                    )
            })
    /**
     * Getting a report by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportInfo(@Parameter(description = "Полученный по id отчет") @PathVariable Long id) {
        Report report = reportService.findReport(id);
        if (report == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(report);
    }

    @Operation(
            summary = "Editing a report",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Редактируем отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report[].class)


                            )
                    )
            })
    /**
     * Editing the report
     */
    @PutMapping("/{id}")
    public ResponseEntity<Report> editReport(@Parameter(description = "Редактируемый отчет") @PathVariable Long id, Report report) {
        Report foundReport = reportService.editReport(id, report);
        if (foundReport == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundReport);
    }

    @Operation(
            summary = "Delete a report by its id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаляем отчет по его id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report[].class)


                            )
                    )
            })
    /**
     * Delete the report
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Report> deleteStudent(@Parameter(description = "Удаляемый отчет") @PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        reportService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
