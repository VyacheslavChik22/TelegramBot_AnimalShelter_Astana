package ru.vyacheslav.telegrambot_animalshelter_astana.exceptions;

public class ReportNotFoundException extends RuntimeException{

    public ReportNotFoundException(){super();}

    public ReportNotFoundException(String message){super(message);}
}
