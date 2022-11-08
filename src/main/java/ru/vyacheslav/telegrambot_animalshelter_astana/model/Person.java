package ru.vyacheslav.telegrambot_animalshelter_astana.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long personId;
    private String email;
    private String phone;
    private String address;
    private Long chatId;
    private Long animalId;
    private String animalAdoptDate;
    private String lastReportDate;



    @OneToOne
    @JoinColumn(name = "animalId")
    private Animal animal;

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    @OneToMany
    @JoinColumn(name = "reportId")
    private List<Report> reportId;

    public List<Report> getReportId() {
        return reportId;
    }

    public void setReportId(List<Report> reportId) {
        this.reportId = reportId;
    }


    public Person() {

    }

    public Person(Long id, String name, Long personId, String email, String phone, String address, Long chatId, Long animalId, String animalAdoptDate, String lastReportDate) {
        this.id = id;
        this.name = name;
        this.personId = personId;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.chatId = chatId;
        this.animalId = animalId;
        this.animalAdoptDate = animalAdoptDate;
        this.lastReportDate = lastReportDate;
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

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
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

    public Long getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Long animalId) {
        this.animalId = animalId;
    }

    public String getAnimalAdoptDate() {
        return animalAdoptDate;
    }

    public void setAnimalAdoptDate(String animalAdoptDate) {
        this.animalAdoptDate = animalAdoptDate;
    }

    public String getLastReportDate() {
        return lastReportDate;
    }

    public void setLastReportDate(String lastReportDate) {
        this.lastReportDate = lastReportDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(personId, person.personId) && Objects.equals(email, person.email) && Objects.equals(phone, person.phone) && Objects.equals(address, person.address) && Objects.equals(chatId, person.chatId) && Objects.equals(animalId, person.animalId) && Objects.equals(animalAdoptDate, person.animalAdoptDate) && Objects.equals(lastReportDate, person.lastReportDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, personId, email, phone, address, chatId, animalId, animalAdoptDate, lastReportDate);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", personId=" + personId +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", chatId=" + chatId +
                ", animalId=" + animalId +
                ", animalAdoptDate='" + animalAdoptDate + '\'' +
                ", lastReportDate='" + lastReportDate + '\'' +
                '}';
    }
}
