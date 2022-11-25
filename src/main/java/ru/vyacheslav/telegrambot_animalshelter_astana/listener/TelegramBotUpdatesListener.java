package ru.vyacheslav.telegrambot_animalshelter_astana.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramException;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandScopeDefault;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.ReportRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static ru.vyacheslav.telegrambot_animalshelter_astana.constants.TelegramBotConstants.*;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final ReportRepository reportRepository;

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;

    public TelegramBotUpdatesListener(ReportRepository reportRepository, TelegramBot telegramBot) {
        this.reportRepository = reportRepository;
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
            if (message == null) {
                return;
            }

            Long chatId = message.chat().id();
            switch (message.text()) {
                case "/start":
                    // Send GREETINGS_MSG if START_CMD was found
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, update.message().chat().username() + ", " + GREETING_MSG);
                    break;

                case "/menu":
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, LIST_MENU);
                    break;

                case "/info":
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, INFO_TEXT);
                    break;

                case "/how":
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, HOW_TEXT);
                    break;
                case "/report":
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, "Информация редактируется");
                    break;

                case "/call":
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, CALL_TEXT);
                    break;

                case "/repeat":
                    logger.info("Bot start message was received: {}", message.text());
                    sendMessage(chatId, "Информация редактируется");
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

    //@Scheduled(cron = "0 0 * * *") //здесь должен быть метод для напоминания пользователю предоставить отчет


}