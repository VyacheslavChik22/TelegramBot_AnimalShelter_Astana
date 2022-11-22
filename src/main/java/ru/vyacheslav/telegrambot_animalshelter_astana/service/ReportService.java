package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public void createReportFromMessage(Long chatId, PhotoSize[] photo, String caption) {
        // Проверить существует ли пользователь в нашей БД по chatId - если нет, то бросить ошибку
        Person person = personRepository.findPersonByChatId(chatId);
        if (person == null) {
            logger.info("User with {} not found in DB", chatId);
            throw new PersonNotFoundException();
        }
        // TODO: 22.11.2022 Добавить кастомные эксешены для photo и caption
        if (photo == null) {
            throw new RuntimeException("No photo");
        }
        if (caption == null) {
            throw new RuntimeException("No text");
        }
        // Проверить что у пользователя есть животное (person.getAnimal() != null) - бросить ошибку
        // Эту проверку можно сделать в Листенере после получения команды ботом
        if (person.getAnimal() == null) {
            throw new RuntimeException("No animal");
        }
        // Проверить дату последнего отчета у пользователя, если сегодня - бросить ошибку
        // Эту проверку можно сделать в Листенере после получения команды ботом
        if (person.getLastReportDate().isEqual(LocalDate.now())) {
            throw new RuntimeException("Report has sent");
        }
        // TODO: 22.11.2022 Проверка что 30 дней с момента взятия животного еще не прошло (animalAdoptDate + 30 дней)
        // TODO: 21.11.2022 Добавить приватный метод парсинга текста отчета по формату
        Report report = new Report();
        // TODO: 21.11.2022 Установить все поля к сущности report
        // TODO: 22.11.2022 Установить у сущности person поле lastReportDate
        // TODO: 22.11.2022 Сохранить person в базу
        // TODO: 21.11.2022 Сохранить report в базу
    }

}


