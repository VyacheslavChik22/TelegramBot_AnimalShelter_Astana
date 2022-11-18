package ru.vyacheslav.telegrambot_animalshelter_astana.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Animal;
import ru.vyacheslav.telegrambot_animalshelter_astana.service.AnimalService;

import java.util.List;

@RestController
@RequestMapping("animals")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @ApiResponses ({
            @ApiResponse (
                    responseCode = "200",
                    description = "Information about new animal created in DB"
            )
    })
    @PostMapping
    public Animal createAnimal (@RequestBody Animal animal) {
        return animalService.createAnimal(animal);
    }

    @ApiResponses ({
            @ApiResponse (
                    responseCode = "200",
                    description = "Found animal by id"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimalInfoById (@PathVariable Long id){
        Animal animal = animalService.getAnimalInfoById(id);
        if (animal==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animal);
    }

    @ApiResponses ({
            @ApiResponse (
                    responseCode = "200",
                    description = "Found the total number of animals in the database"
            )
    })
    @GetMapping("/count_animal")
    public  int totalAmountOfAnimal(){
        return animalService.totalAmountOfAnimal();
    }

    @ApiResponses ({
            @ApiResponse (
                    responseCode = "200",
                    description = "Complete list of animals by page"
            )
    })
    @GetMapping(value = "/all_animal")
    public ResponseEntity<List<Animal>> allAnimalWithPagination(@RequestParam("page") Integer pageNumber,
                                                                 @RequestParam("size") Integer pageSize) {
        List<Animal> animals = animalService.allAnimalWithPagination(pageNumber, pageSize);
        return ResponseEntity.ok(animals);
    }
}
