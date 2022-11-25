package ru.vyacheslav.telegrambot_animalshelter_astana.exceptions;

public class TextDoesNotMatchPatternException extends RuntimeException {

    public TextDoesNotMatchPatternException() {
        super();
    }

    public TextDoesNotMatchPatternException(String message) {
        super(message);
    }

    public TextDoesNotMatchPatternException(String message, Throwable cause) {
        super(message, cause);
    }

    public TextDoesNotMatchPatternException(Throwable cause) {
        super(cause);
    }

    protected TextDoesNotMatchPatternException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
