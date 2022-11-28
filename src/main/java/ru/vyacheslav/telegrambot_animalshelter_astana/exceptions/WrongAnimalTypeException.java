package ru.vyacheslav.telegrambot_animalshelter_astana.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.vyacheslav.telegrambot_animalshelter_astana.model.AnimalType;

import java.util.Arrays;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class WrongAnimalTypeException extends RuntimeException{
    public WrongAnimalTypeException() {
        super("Use one of this animal types: " + Arrays.toString(AnimalType.values()));
    }

    public WrongAnimalTypeException(String message) {
        super(message);
    }

    public WrongAnimalTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongAnimalTypeException(Throwable cause) {
        super(cause);
    }
}
