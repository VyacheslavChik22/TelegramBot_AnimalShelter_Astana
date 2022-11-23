package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.ReportNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Person;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Report;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.PersonRepository;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.ReportRepository;

import java.time.LocalDate;
import java.util.List;

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

    public Report createReportFromMessage(Long chatId, PhotoSize[] photo, String caption) {
        // TODO: 22.11.2022 Инжектить personService вместо PersonRepository???
        // Проверить существует ли пользователь в нашей БД по chatId - если нет, то бросить ошибку
        Person person = personRepository.findPersonByChatId(chatId);
        if (person == null) {
            logger.info("User with {} not found in DB", chatId);
            throw new PersonNotFoundException();
        }
        // TODO: 22.11.2022 Добавить кастомные эксешены для photo, caption, animal & report
        // Проверить дату последнего отчета у пользователя, если сегодня - бросить ошибку
        if (person.getLastReportDate() != null && person.getLastReportDate().isEqual(LocalDate.now())) {
            throw new RuntimeException("Report has sent");
        }

        // Проверить что у пользователя есть животное (person.getAnimal() != null) - бросить ошибку
        if (person.getAnimal() == null) {
            throw new RuntimeException("No animal");
        }
        if (photo == null) {
            throw new RuntimeException("No photo");
        }
        if (caption == null) {
            throw new RuntimeException("No text");
        }

        // TODO: 22.11.2022 Проверка что 30 дней с момента взятия животного еще не прошло (animalAdoptDate + 30 дней)
        // TODO: 21.11.2022 Добавить приватный метод парсинга текста отчета по формату
        // TODO: 21.11.2022 Установить все поля к сущности report
        Report report = new Report();
        report.setReportDate(LocalDate.now());
        report.setDescription(caption);
        report.setPerson(person);
        // TODO: 23.11.2022 Следующие строчки надо заменить реальным фото
        report.setMediaType(MediaType.MULTIPART_FORM_DATA_VALUE);
        report.setPhotoData(new byte[1]);
        report.setPhotoSize(1);
        report.setPhotoPath("test");
        // TODO: 22.11.2022 Установить у сущности person поле lastReportDate
        person.setLastReportDate(report.getReportDate());
        // TODO: 22.11.2022 Сохранить person в базу
        personRepository.save(person);
        // TODO: 21.11.2022 Сохранить report в базу
        addReport(report);
        return null;
    }

}


