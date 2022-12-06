package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.AnimalType;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.PersonContext;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.PersonContextRepository;

import java.util.Optional;

@Service
public class PersonContextService {

    private final Logger logger = LoggerFactory.getLogger(PersonContextService.class);

    private final PersonContextRepository personContextRepository;

    public PersonContextService(PersonContextRepository personContextRepository) {
        this.personContextRepository = personContextRepository;
    }

    /**
     * Creates new {@link PersonContext} with chatId and animalType.
     *
     * @param chatId telegram chat identification number
     * @param animalType one of {@link AnimalType} enum values or <b>null</b>
     * @return {@link PersonContext} entity
     */
    public PersonContext createPersonContext(Long chatId, AnimalType animalType) {
        logger.info("Was invoked method to create person context");
        PersonContext context = new PersonContext();
        context.setChatId(chatId);
        context.setAnimalType(animalType);
        return personContextRepository.save(context);
    }

    /**
     * Updates animal type for existing person context found by chatId
     * or creates new {@link PersonContext} if no entries were found in DB.
     *
     * @param chatId telegram chat identification number
     * @param animalType one of {@link AnimalType} enum values or <b>null</b>
     * @return {@link PersonContext} entity
     */
    public PersonContext updatePersonContext(Long chatId, AnimalType animalType) {
        logger.info("Was invoked method to update person context");
        Optional<PersonContext> contextOptional = personContextRepository.findByChatId(chatId);
        PersonContext currentContext;
        if (contextOptional.isEmpty()) {
            return createPersonContext(chatId, animalType);
        } else {
            currentContext = contextOptional.get();
            currentContext.setAnimalType(animalType);
        }
        return personContextRepository.save(currentContext);
    }

    /**
     * Returns animal type by chatId.
     *
     * @param chatId telegram chat identification number
     * @return {@link AnimalType} for entry with chatId
     * @throws RuntimeException if no entries with chatId were found in DB
     */
    public AnimalType getAnimalType(Long chatId) {
        logger.info("Was invoked method to get animal type from person's context");
        PersonContext context = personContextRepository.findByChatId(chatId)
                .orElseThrow(() -> new RuntimeException("There is no chatId '" + chatId + "' in DB"));

        return context.getAnimalType();
    }
}
