package ru.vyacheslav.telegrambot_animalshelter_astana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TelegramBotAnimalShelterAstanaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotAnimalShelterAstanaApplication.class, args);
    }

}
