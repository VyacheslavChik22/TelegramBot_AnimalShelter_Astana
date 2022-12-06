package ru.vyacheslav.telegrambot_animalshelter_astana.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Long chatId;
    private LocalDate animalAdoptDate;
    private LocalDate lastReportDate;

    @OneToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    public AbstractPerson() {
    }

    public AbstractPerson(Long id, String name, String email, String phone, String address, Long chatId, LocalDate animalAdoptDate, LocalDate lastReportDate, Animal animal) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.chatId = chatId;
        this.animalAdoptDate = animalAdoptDate;
        this.lastReportDate = lastReportDate;
        this.animal = animal;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public LocalDate getAnimalAdoptDate() {
        return animalAdoptDate;
    }

    public void setAnimalAdoptDate(LocalDate animalAdoptDate) {
        this.animalAdoptDate = animalAdoptDate;
    }

    public LocalDate getLastReportDate() {
        return lastReportDate;
    }

    public void setLastReportDate(LocalDate lastReportDate) {
        this.lastReportDate = lastReportDate;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPerson that = (AbstractPerson) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(address, that.address) && Objects.equals(chatId, that.chatId) && Objects.equals(animalAdoptDate, that.animalAdoptDate) && Objects.equals(lastReportDate, that.lastReportDate) && Objects.equals(animal, that.animal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, phone, address, chatId, animalAdoptDate, lastReportDate, animal);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", chatId=" + chatId +
                ", animalAdoptDate=" + animalAdoptDate +
                ", lastReportDate=" + lastReportDate +
                ", animal=" + animal +
                '}';
    }
}
