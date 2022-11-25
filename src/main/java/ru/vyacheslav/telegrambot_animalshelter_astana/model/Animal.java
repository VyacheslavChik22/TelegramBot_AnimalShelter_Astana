package ru.vyacheslav.telegrambot_animalshelter_astana.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "Animal")
public class Animal {

    public enum AnimalForm {
        DOG,
        CAT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private AnimalForm form;

    public Animal() {
    }

    public Animal(Long id, String name,  AnimalForm form) {
        this.id = id;
        this.name = name;
        this.form = form;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
       return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnimalForm getForm() {
        return form;
    }

    public void setForm(AnimalForm form) {
        this.form = form;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return id.equals(animal.id) && form == animal.form;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, form);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", form=" + form +
                '}';
    }
}
