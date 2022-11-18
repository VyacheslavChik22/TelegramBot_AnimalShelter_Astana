package ru.vyacheslav.telegrambot_animalshelter_astana;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import ru.vyacheslav.telegrambot_animalshelter_astana.controller.PersonController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TelegramBotAnimalShelterAstanaApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private PersonController personController;

	@Test
	void contextLoads() {
		assertThat(personController).isNotNull();
	}

}
