package ru.vyacheslav.telegrambot_animalshelter_astana.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.vyacheslav.telegrambot_animalshelter_astana.dto.FotoObjectDto;

import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.NoAnimalAdoptedException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonAlreadyExistsException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.TextDoesNotMatchPatternException;

import ru.vyacheslav.telegrambot_animalshelter_astana.model.AnimalType;

import ru.vyacheslav.telegrambot_animalshelter_astana.model.PersonDog;
import ru.vyacheslav.telegrambot_animalshelter_astana.service.TelegramBotUpdatesService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static ru.vyacheslav.telegrambot_animalshelter_astana.constants.TelegramBotConstants.*;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private AnimalType animalType;

    private final TelegramBotUpdatesService telegramBotUpdatesService;

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;

    public TelegramBotUpdatesListener(TelegramBotUpdatesService telegramBotUpdatesService, TelegramBot telegramBot) {
        this.telegramBotUpdatesService = telegramBotUpdatesService;
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            Message message = update.message();

            // If the server connection was lost, then message object can be null
            // So we ignore it in this case

            if (message == null || message.text()==null && message.replyToMessage() == null) {
                return;
            }

            Long chatId = message.chat().id();

            // Если пользователь отвечает на сообщение о подаче репорта
            // можно проверять ключевое слово из сообщения бота (напрмер фото) вместо команды
            if (message.replyToMessage() != null && REPORT_FORM.equals(message.replyToMessage().text())) {
                try {
                    checkIfReportMessageEligible(message.photo(), message.caption());

                    FotoObjectDto fotoObjDto = extractPhotoData(message.photo());
                    telegramBotUpdatesService.createReportFromMessage(chatId, fotoObjDto, message.caption(), animalType);

                    sendMessage(chatId, "Отчет сохранен");
                    sendMessage(chatId, "\n" + START);
                    return;
                } catch (IOException e) {
                    throw new RuntimeException("Проблема с сохранением фото");
                } catch (RuntimeException e) {
                    sendMessage(chatId, "Ошибка в отчете: " + e.getMessage());
                    return;
                }
            }

            // Если пользователь отвечает на сообщение о своих контактных данных
            // можно проверять ключевое слово из сообщения бота (напрмер почта) вместо команды
            if (message.replyToMessage() != null && CONTACT_TEXT.equals(message.replyToMessage().text())) {
                try {
                    telegramBotUpdatesService.createPersonFromMessage(chatId, message.text(), animalType);
                    sendMessage(chatId, "Контактные данные сохранены");
                    sendMessage(chatId, "\n" + START);
                    return;
                } catch (PersonAlreadyExistsException e) {
                    sendMessage(chatId, "Ваши контактные данные уже сохранены");
                    return;
                } catch (TextDoesNotMatchPatternException e) {
                    sendMessage(chatId, "Текст не соответствует шаблону, нажмите " + REPEAT + " и попробуйте еще раз");
                }
            }

            switch (message.text()) {
                case "/start":
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, update.message().chat().username() + ", " + GREETING_MSG);
                    break;

                case "/menu_Dog":
                    logger.info("Bot start message was received: {}", message.text());
                    animalType = AnimalType.DOG;
                    sendMessage(chatId, LIST_MENU_DOG);
                    break;
                case "/menu_Cat":
                    logger.info("Bot start message was received: {}", message.text());
                    animalType = AnimalType.CAT;
                    sendMessage(chatId, LIST_MENU_CAT);
                    break;

                case "/info_shelterDog":
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, INFO_TEXT_DOG);
                    break;
                case "/info_shelterCat":
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, INFO_TEXT_CAT);
                    break;

                case "/how_to_adopt_a_Dog":
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, HOW_TEXT_DOG);
                    break;

                case "/how_to_adopt_a_Cat":
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, HOW_TEXT_CAT);
                    break;

                case "/report":
                    if (animalType == null) {
                        sendMessage(chatId, "Пожалуйста, перед тем как отправить отчет, выберите /menu_Dog или /menu_Cat");
                        return;
                    }
                    logger.info("Bot start message was received: {}", message.text());
                    // Получаем дни с момента получения животного
                    Long daysFromAdoption;
                    try {
                        daysFromAdoption = telegramBotUpdatesService.countDaysFromAdoption(chatId, animalType);
                    } catch (PersonNotFoundException | NoAnimalAdoptedException e) {
                        sendMessage(chatId, e.getMessage());
                        return;
                    }
                    // Если больше 30 дней - отправляем сообщение, что отчеты больше не нужны
                    if (daysFromAdoption > 30) {
                        sendMessage(chatId, NO_MORE_REPORTS);
                    } else {
                        // Если меньше - отправляем форму отчета
                        sendMessage(chatId, REPORT_FORM);
                    }
                    break;

                case "/call":
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, CALL_TEXT);
                    break;

                case "/contact":
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, CONTACT_TEXT);
                    break;


                //*********************--menu2--******************
                case "/consultation_Dog":
                    // Send GREETINGS_MSG if START_CMD was found
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, update.message().chat().username() + ", " + LIST_MENU_CONSULTATION_DOG);
                    break;

                case "/consultation_Cat":
                    // Send GREETINGS_MSG if START_CMD was found
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, update.message().chat().username() + ", " + LIST_MENU_CONSULTATION_CAT);
                    break;

                case "/documents_Dog":
                    // Send GREETINGS_MSG if START_CMD was found
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, DOCUMENTS_TEXT_DOG);
                    break;

                case "/documents_Cat":
                    // Send GREETINGS_MSG if START_CMD was found
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, DOCUMENTS_TEXT_CAT);
                    break;

                case "/transportation_Dog":
                    // Send GREETINGS_MSG if START_CMD was found
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, TRANSPORTATION_TEXT_DOG);
                    break;

                case "/transportation_Cat":
                    // Send GREETINGS_MSG if START_CMD was found
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, TRANSPORTATION_TEXT_CAT);
                    break;

                case "/acquaintance_Dog":
                    // Send GREETINGS_MSG if START_CMD was found
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, ACQUAINTANCE_TEXT_DOG);
                    break;

                case "/acquaintance_Cat":
                    // Send GREETINGS_MSG if START_CMD was found
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, ACQUAINTANCE_TEXT_CAT);
                    break;

                case "/advice_little_Dog":
                    // Send GREETINGS_MSG if START_CMD was found
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, ADVICE_LITTLE_TEXT_DOG);
                    break;

                case "/advice_little_Cat":
                    // Send GREETINGS_MSG if START_CMD was found
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, ADVICE_LITTLE_TEXT_CAT);
                    break;

                case "/advice_big_Dog":
                    // Send GREETINGS_MSG if START_CMD was found
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, ADVICE_BIG_TEXT_DOG);
                    break;

                case "/advice_big_Cat":
                    // Send GREETINGS_MSG if START_CMD was found
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, ADVICE_BIG_TEXT_CAT);
                    break;

                case "/advice_limited_Dog":
                    // Send GREETINGS_MSG if START_CMD was found
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, ADVICE_LIMITED_TEXT_DOG);
                    break;

                case "/advice_limited_Cat":
                    // Send GREETINGS_MSG if START_CMD was found
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, ADVICE_LIMITED_TEXT_CAT);
                    break;

                case "/rejection_reasons":
                    // Send GREETINGS_MSG if START_CMD was found
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, REJECTION_REASONS);
                    break;

                case "/advices_cynologist":
                    // Send GREETINGS_MSG if START_CMD was found
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, ADVICES_TEXT_CYNOLOGIST);
                    break;

                case "/tested_cynologists":
                    // Send GREETINGS_MSG if START_CMD was found
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, TESTED_TEXT_CYNOLOGIST);
                    break;

                default:
                    sendMessage(chatId, DEFAULT_TEXT);
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Creates SendMessage instance for telegram chat with some text
     * and sends it to the chat.
     *
     * @param chatId     index of a telegram chat to which the message is sent
     * @param textToSend string to be sent
     */
    private void sendMessage(Long chatId, String textToSend) {
        // Create message to send and send it to chat defined by id
        SendMessage sendMessage = new SendMessage(chatId, textToSend);
        SendResponse response = telegramBot.execute(sendMessage);

        // Check if msg was not sent and log the error
        if (!response.isOk()) {
            logger.warn("Message was not sent, error code: {}", response.errorCode());
        }
    }

    /**
     * Checks if user's message contains picture and text.
     * If something missed - throw exception.
     *
     * @param photoSizes telegram's object represents photo-file from a message
     * @param caption text field from a message with photo
     * @throws RuntimeException with description which param is null
     */
    private void checkIfReportMessageEligible(PhotoSize[] photoSizes, String caption) {
        if (photoSizes == null) {
            throw new RuntimeException("No photo");
        }
        if (caption == null) {
            throw new RuntimeException("No text");
        }
    }

    /**
     * Extracts data from telegram's object represents photo-file and
     * forms a map object with this data.
     *
     * @param photoSizes telegram's object represents photo-file from a message
     * @return {@link Map} with various data extracted from photo file
     * @throws IOException
     */
    private FotoObjectDto extractPhotoData(PhotoSize[] photoSizes) throws IOException {

        PhotoSize photoObject = photoSizes[1];

        GetFile fileRequest = new GetFile(photoObject.fileId());
        GetFileResponse fileResponse = telegramBot.execute(fileRequest);
        File file = fileResponse.file();
        byte[] fileData = telegramBot.getFileContent(file);

        // Form map to transfer to ReportService.createReportFromMessage method
        FotoObjectDto fotoObjectDto = new FotoObjectDto();
        fotoObjectDto.setPhotoData(fileData);
        fotoObjectDto.setPhotoPath(file.filePath());
        fotoObjectDto.setPhotoSize(file.fileSize());
        fotoObjectDto.setMediaType(fileRequest.getContentType());

        return fotoObjectDto;
    }
    @Scheduled(cron = "0 0 22 * * *")
    public void RemindAboutReports(){
     List<PersonDog> personList =  telegramBotUpdatesService.findPeopleToRemind();
        if(personList.size() > 0){
            personList.forEach(p -> sendMessage(p.getChatId(),  "До сдачи отчета осталось немного времени!"));
        }
    }


    //@Scheduled(cron = "0 0 * * *") //здесь должен быть метод для напоминания пользователю предоставить отчет

}