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
import ru.vyacheslav.telegrambot_animalshelter_astana.model.PersonDog;
import ru.vyacheslav.telegrambot_animalshelter_astana.service.PersonDogService;

import java.util.Collection;

/**
 * REST endpoints for CRUD operations with {@link PersonDog} model.
 * @author Oleg Alekseenko
 */
@RestController
@RequestMapping(path = "/dog-people")
public class PersonDogController {
    private final PersonDogService personDogService;

    public PersonDogController(PersonDogService personDogService) {
        this.personDogService = personDogService;
    }

    @Operation(
            summary = "Get all people from DB",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found people",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = PersonDog.class))
                                            )
                            })
            })
    @GetMapping
    public ResponseEntity<Collection<PersonDog>> getAllPeople() {
        return ResponseEntity.ok(personDogService.findAll());
    }

    @Operation(
            summary = "Get person by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found person by id",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PersonDog.class))
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
    public ResponseEntity<PersonDog> getPersonById(@PathVariable long id) {
        return ResponseEntity.ok(personDogService.findPerson(id));
    }

    @Operation(
            summary = "Save new person to DB",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Person's data to save in DB",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PersonDog.class)
                    )
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "New person was saved in DB",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PersonDog.class))
                    }
            )
    )
    @PostMapping
    public ResponseEntity<PersonDog> createPerson(@RequestBody PersonDog person) {
        PersonDog createdPerson = personDogService.createPerson(person);
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
                            schema = @Schema(implementation = PersonDog.class)
                    )
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Person's data was updated in DB",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PersonDog.class))
                    }
            )
    )
    @PutMapping
    public ResponseEntity<PersonDog> updatePerson(@RequestBody PersonDog person) {
        PersonDog updatedPerson = personDogService.updatePerson(person);
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
        personDogService.deletePerson(id);
        return ResponseEntity.ok().build();
    }
}
