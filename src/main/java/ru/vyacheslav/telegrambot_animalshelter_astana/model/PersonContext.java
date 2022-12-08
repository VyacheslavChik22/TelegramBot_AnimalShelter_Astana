package ru.vyacheslav.telegrambot_animalshelter_astana.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class PersonContext {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    @Enumerated(value = EnumType.STRING)
    private AnimalType animalType;

    public PersonContext() {
    }

    public PersonContext(Long id, Long chatId, AnimalType animalType) {
        this.id = id;
        this.chatId = chatId;
        this.animalType = animalType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonContext that = (PersonContext) o;
        return Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) && animalType == that.animalType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, animalType);
    }
}
