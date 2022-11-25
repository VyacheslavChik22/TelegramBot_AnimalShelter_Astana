package ru.vyacheslav.telegrambot_animalshelter_astana.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Person")
public class Person {
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
    @JoinColumn(name = "animal_form")
    private Animal animal;

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
    @JsonIgnore
    @OneToMany(mappedBy = "person")
    private Set <Report> reports;

    public Set<Report> getReports() {
        return reports;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }

    public Person() {

    }

    public Person(Long id, String name, String email, String phone, String address, Long chatId, LocalDate animalAdoptDate, LocalDate lastReportDate, Animal animal, Set<Report> reports) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.chatId = chatId;
        this.animalAdoptDate = animalAdoptDate;
        this.lastReportDate = lastReportDate;
        this.animal = animal;
        this.reports = reports;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(email, person.email) && Objects.equals(phone, person.phone) && Objects.equals(address, person.address) && Objects.equals(chatId, person.chatId) && Objects.equals(animalAdoptDate, person.animalAdoptDate) && Objects.equals(lastReportDate, person.lastReportDate) && Objects.equals(animal, person.animal) && Objects.equals(reports, person.reports);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, phone, address, chatId, animalAdoptDate, lastReportDate, animal, reports);
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
