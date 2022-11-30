package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.request.GetFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.NoAnimalAdoptedException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.ReportNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Person;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Report;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.PersonRepository;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.ReportRepository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    private final Logger logger = LoggerFactory.getLogger(ReportService.class);
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    /**
     * adding a report
     */
    public Report addReport(Report report) {
        logger.debug("Request method addReport {}:", report);
        return reportRepository.save(report);
    }

    /**
     * By id. we can find the report
     */
    public Report findReport(Long id) {
        logger.debug("Request method findReport {}:", id);
        return reportRepository.findById(id).orElseThrow(ReportNotFoundException::new);
    }

    /**
     * We receive all reports
     */
    public List<Report> findAllReports() {
        logger.debug("Request method findAllReports:");
        return reportRepository.findAll();
    }



    /**
     * Editing and saving report
     */
    public Report editReport(Long id, Report report) {
        logger.debug("Request method editReport {}:", report);
        return reportRepository.save(report);
    }

    /**
     * By id. delete report
     */
    public void deleteById(Long id) {
        logger.debug("Request method deleteById {}:", id);
        reportRepository.deleteById(id);
    }



}


