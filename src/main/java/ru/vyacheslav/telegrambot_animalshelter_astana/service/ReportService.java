package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.ReportNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Report;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.ReportRepository;

import java.util.List;

@Service
public class ReportService {
    private final Logger logger = LoggerFactory.getLogger(ReportService.class);
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    /**
     * adding a report in DB
     */
    public Report addReport(Report report) {
        logger.debug("Request method addReport {}:", report);
        return reportRepository.save(report);
    }

    /**
     * By id. we can find the report
     *
     * @param id identification number for report
     * @throws ReportNotFoundException if no entry was found in DB
     */
    public Report findReport(Long id) {
        logger.debug("Request method findReport {}:", id);
        return reportRepository.findById(id).orElseThrow(ReportNotFoundException::new);
    }

    /**
     * Finds all reports entries in DB.
     */
    public List<Report> findAllReports() {
        logger.debug("Request method findAllReports:");
        return reportRepository.findAll();
    }


    /**
     * Editing and saving report in DB
     *
     * @param id identification number for report
     */
    public Report editReport(Long id, Report report) {
        logger.debug("Request method editReport {}:", report);
        return reportRepository.save(report);
    }

    /**
     * By id delete report
     *
     * @param id identification number for report
     */
    public void deleteById(Long id) {
        logger.debug("Request method deleteById {}:", id);
        reportRepository.deleteById(id);
    }

}


