package ru.vyacheslav.telegrambot_animalshelter_astana.model;

import javax.persistence.Entity;
import javax.annotation.processing.Generated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Report {
    @Id
    @GeneratedValue
    private Long id;
}
