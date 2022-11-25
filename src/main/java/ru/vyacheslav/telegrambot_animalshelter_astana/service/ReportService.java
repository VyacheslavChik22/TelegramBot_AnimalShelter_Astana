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

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    private final Logger logger = LoggerFactory.getLogger(ReportService.class);
    private final ReportRepository reportRepository;
    private final PersonRepository personRepository;

    public ReportService(ReportRepository reportRepository, PersonRepository personRepository) {
        this.reportRepository = reportRepository;
        this.personRepository = personRepository;
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

    /**
     * Creates report entity with text and photo file data, and saves it in DB
     *
     * @param chatId chat identification number from the telegram {@link com.pengrad.telegrambot.model.Message} object
     * @param fileMap map which contains key-value pairs with photo file data
     * @param caption text from the telegram message with photo file
     * @return report entity
     * @throws PersonNotFoundException when there is no person with such chatId in DB
     * @throws NoAnimalAdoptedException if person doesn't have adopted animal
     * @throws RuntimeException with specific message when person has already sent report today
     */
    public Report createReportFromMessage(Long chatId, Map<String, Object> fileMap, String caption) {
        logger.info("Request method createReportFromMessage");
        // TODO: 22.11.2022 Инжектить personService вместо PersonRepository???
        // Проверить существует ли пользователь в нашей БД по chatId - если нет, то бросить ошибку
        Person person = personRepository.findPersonByChatId(chatId).orElseThrow(PersonNotFoundException::new);

        // TODO: 22.11.2022 Добавить кастомные эксешены для photo, caption & report
        // Проверить что у пользователя есть животное
        if (person.getAnimal() == null) {
            throw new NoAnimalAdoptedException();
        }
        // Проверить дату последнего отчета у пользователя, если сегодня - бросить ошибку
        if (person.getLastReportDate() != null && person.getLastReportDate().isEqual(LocalDate.now())) {
            throw new RuntimeException("Report has sent");
        }

        // TODO: 21.11.2022 Добавить приватный метод парсинга текста отчета по формату

        Report report = new Report();
        report.setReportDate(LocalDate.now());
        report.setDescription(caption);
        report.setPerson(person);
        report.setMediaType((String) fileMap.get("mediaType"));
        report.setPhotoData((byte[]) fileMap.get("photoData"));
        report.setPhotoSize((Integer) fileMap.get("photoSize"));
        report.setPhotoPath((String) fileMap.get("photoPath"));

        person.setLastReportDate(report.getReportDate());

        personRepository.save(person);

        return addReport(report);
    }

}


