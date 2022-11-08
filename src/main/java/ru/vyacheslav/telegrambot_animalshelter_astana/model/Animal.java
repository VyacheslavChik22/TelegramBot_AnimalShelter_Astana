package ru.vyacheslav.telegrambot_animalshelter_astana.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Animal {
    @Id
    @GeneratedValue
    private Long id;

}
