package ru.vyacheslav.telegrambot_animalshelter_astana.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vyacheslav.telegrambot_animalshelter_astana.exceptions.PersonNotFoundException;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Person;
import ru.vyacheslav.telegrambot_animalshelter_astana.service.PersonService;

import java.util.Collection;

/**
 * REST endpoints for CRUD operations with {@link Person} model.
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

    @Operation(
            summary = "Get person by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found person by id",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Person.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Person was not found in DB",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            },
            parameters = {
                    @Parameter(name = "id",
                    description = "id number of the person",
                    example = "1")

            }
    )
    @GetMapping(path = "/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable long id) {
        return ResponseEntity.ok(personService.findPerson(id));
    }

    @Operation(
            summary = "Save new person to DB",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Person's data to save in DB",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Person.class)
                    )
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "New person was saved in DB",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Person.class))
                    }
            )
    )
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person createdPerson = personService.createPerson(person);
        if (createdPerson == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdPerson);
    }

    @Operation(
            summary = "Update existing person",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Person's data to update",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Person.class)
                    )
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Person's data was updated in DB",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Person.class))
                    }
            )
    )
    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        Person updatedPerson = personService.updatePerson(person);
        if (updatedPerson == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedPerson);
    }

    @Operation(
            summary = "Delete person by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Person was deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Person was not found in DB"
                    )
            },
            parameters = {
                    @Parameter(name = "id",
                            description = "id number of the person",
                            example = "1")

            }
    )
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable long id) {
        personService.deletePerson(id);
        return ResponseEntity.ok().build();
    }
}
