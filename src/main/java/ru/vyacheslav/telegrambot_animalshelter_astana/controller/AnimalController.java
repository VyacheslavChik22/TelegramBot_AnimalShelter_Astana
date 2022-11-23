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
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Animal;
import ru.vyacheslav.telegrambot_animalshelter_astana.service.AnimalService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/animals")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @Operation(
            summary = "Create new animal in DB",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Animal's info",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Animal.class)
                    )
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "New animal was created in DB",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Animal.class))
                    }
            )
    )
    @PostMapping
    public Animal createAnimal (@RequestBody Animal animal) {
        return animalService.createAnimal(animal);
    }

    @Operation(
            summary = "Get animal by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Animal was found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Animal.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Animal was not found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Animal.class)
                            )
                    )
                            },
            parameters = {
                    @Parameter(name = "id",
                    description = "id number of the animal",
                    example = "1")
            })
    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimalInfoById (@PathVariable Long id){
        Animal animal = animalService.getAnimalInfoById(id);
        return ResponseEntity.ok(animal);
    }

    @Operation(
            summary = "Get amount of animals in DB",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Received number of animals in the shelter"
                            )
            })
    @GetMapping("/count_animals")
    public  int totalAmountOfAnimal(){
        return animalService.totalAmountOfAnimal();
    }

    @Operation(
            summary = "Get all animals from DB",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of all animals",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = Animal.class))
                                    )
                            })
            })
    @GetMapping(path = "/all_animals")
    public ResponseEntity<Collection<Animal>> getAllAnimals() {
        return ResponseEntity.ok(animalService.getAllAnimals());
    }

    @Operation(
            summary = "Delete animal by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Animal was deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Animal was not found in DB"
                    )
            },
            parameters = {
                    @Parameter(name = "id",
                            description = "id number of the animal",
                            example = "1")

            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id){
        animalService.deleteAnimal(id);
        return ResponseEntity.ok().build();
    }

}
