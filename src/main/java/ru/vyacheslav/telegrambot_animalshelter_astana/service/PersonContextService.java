package ru.vyacheslav.telegrambot_animalshelter_astana.service;

import org.springframework.stereotype.Service;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.AnimalType;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.PersonContext;
import ru.vyacheslav.telegrambot_animalshelter_astana.repository.PersonContextRepository;

import java.util.Optional;

@Service
public class PersonContextService {

    private final PersonContextRepository personContextRepository;

    public PersonContextService(PersonContextRepository personContextRepository) {
        this.personContextRepository = personContextRepository;
    }

    public PersonContext createPersonContext(Long chatId, AnimalType animalType) {
        PersonContext context = new PersonContext();
        context.setChatId(chatId);
        context.setAnimalType(animalType);
        return personContextRepository.save(context);
    }

    public PersonContext updatePersonContext(Long chatId, AnimalType animalType) {
        Optional<PersonContext> contextOptional = personContextRepository.findByChatId(chatId);
        PersonContext currentContext;
        if (contextOptional.isEmpty()) {
            currentContext = createPersonContext(chatId, animalType);
        } else {
            currentContext = contextOptional.get();
            currentContext.setAnimalType(animalType);
        }
        return personContextRepository.save(currentContext);
    }

    public AnimalType getAnimalType(Long chatId) {
        PersonContext context = personContextRepository.findByChatId(chatId)
                .orElseThrow(() -> new RuntimeException("There is no chatId '" + chatId + "' in DB"));

        return context.getAnimalType();
    }
}
