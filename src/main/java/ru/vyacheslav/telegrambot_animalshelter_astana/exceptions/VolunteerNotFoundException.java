package ru.vyacheslav.telegrambot_animalshelter_astana.exceptions;

public class VolunteerNotFoundException extends RuntimeException  {
    public VolunteerNotFoundException() {
    }

    public VolunteerNotFoundException(String message) {
        super(message);
    }

    public VolunteerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VolunteerNotFoundException(Throwable cause) {
        super(cause);
    }

    public VolunteerNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
