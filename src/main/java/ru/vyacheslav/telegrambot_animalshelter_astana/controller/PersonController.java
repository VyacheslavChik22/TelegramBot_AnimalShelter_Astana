package ru.vyacheslav.telegrambot_animalshelter_astana.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Person;
import ru.vyacheslav.telegrambot_animalshelter_astana.service.PersonService;

import java.util.Collection;

/**
 * @author Oleg Alekseenko
 */
@RestController
@RequestMapping("/people")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<Collection<Person>> getAllPeople() {
        return ResponseEntity.ok(personService.findAll());
    }
}
