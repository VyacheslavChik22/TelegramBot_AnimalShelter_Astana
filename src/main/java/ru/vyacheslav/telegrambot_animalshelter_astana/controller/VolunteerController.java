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
import ru.vyacheslav.telegrambot_animalshelter_astana.model.Volunteer;
import ru.vyacheslav.telegrambot_animalshelter_astana.service.VolunteerService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/volunteers")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Operation(
            summary = "Create new Volunteer in DB",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Volunteer's info",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Volunteer.class)
                    )
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "New volunteer was created in DB",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class))
                    }
            )
    )
    @PostMapping
    public Volunteer createVolunteer (@RequestBody Volunteer volunteer) {
        return volunteerService.createVolunteer(volunteer);
    }

    @Operation(
            summary = "Get volunteer by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Volunteer was found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Volunteer was not found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(name = "id",
                            description = "id number of volunteer",
                            example = "1")
            })
    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteerInfoById (@PathVariable Long id){
        Volunteer volunteer = volunteerService.getVolunteerInfoById(id);
        return ResponseEntity.ok(volunteer);
    }

    @Operation(
            summary = "Get all volunteer from DB",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of all volunteers",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                                    )
                            })
            })
    @GetMapping(path = "/all_volunteers")
    public ResponseEntity<Collection<Volunteer>> getAllVolunteer() {
        return ResponseEntity.ok(volunteerService.getAllVolunteers());
    }

    @Operation(
            summary = "Delete volunteer from DB by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Volunteer was deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Volunteer was not found in DB"
                    )
            },
            parameters = {
                    @Parameter(name = "id",
                            description = "id number of volunteer",
                            example = "1")

            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable Long id){
        volunteerService.deleteVolunteer(id);
        return ResponseEntity.ok().build();
    }

}
