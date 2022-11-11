package ru.vyacheslav.telegrambot_animalshelter_astana.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Person;
import ru.vyacheslav.telegrambot_animalshelter_astana.service.PersonService;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Oleg Alekseenko
 */
@RestController
@RequestMapping(path = "/people")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Operation(
            summary = "Get all people from DB",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found people",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = Person.class))
                                            )
                            })
            })
    @GetMapping
    public ResponseEntity<Collection<Person>> getAllPeople() {
        return ResponseEntity.ok(personService.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable long id) {
        return ResponseEntity.ok(personService.findPerson(id));
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person createdPerson = personService.createPerson(person);
        if (createdPerson == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdPerson);
    }

    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        Person updatedPerson = personService.updatePerson(person);
        if (updatedPerson == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable long id) {
        personService.deletePerson(id);
        return ResponseEntity.ok().build();
    }
}
