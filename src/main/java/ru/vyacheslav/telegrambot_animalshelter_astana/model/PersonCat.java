package ru.vyacheslav.telegrambot_animalshelter_astana.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Person_cat")
public class PersonCat {

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
    @JoinColumn(name = "animal_cat_id")
    private AnimalCat animalCat;

    public AnimalCat getAnimalCat() {
        return animalCat;
    }

    public void setAnimalCat(AnimalCat animalCat) {
        this.animalCat = animalCat;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "personCat")
    private Set<ReportCat> reportsCat;

    public Set<ReportCat> getReportsCat() {
        return reportsCat;
    }

    public void setReportsCat(Set<ReportCat> reportsCat) {
        this.reportsCat = reportsCat;
    }

    public PersonCat() {
    }

    public PersonCat(Long id, String name, String email, String phone, String address, Long chatId, LocalDate animalAdoptDate, LocalDate lastReportDate, AnimalCat animalCat, Set<ReportCat> reportsCat) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.chatId = chatId;
        this.animalAdoptDate = animalAdoptDate;
        this.lastReportDate = lastReportDate;
        this.animalCat = animalCat;
        this.reportsCat = reportsCat;
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
        PersonCat personCat = (PersonCat) o;
        return id.equals(personCat.id) && name.equals(personCat.name) && email.equals(personCat.email) && phone.equals(personCat.phone) && address.equals(personCat.address) && chatId.equals(personCat.chatId) && animalAdoptDate.equals(personCat.animalAdoptDate) && lastReportDate.equals(personCat.lastReportDate) && animalCat.equals(personCat.animalCat) && reportsCat.equals(personCat.reportsCat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, phone, address, chatId, animalAdoptDate, lastReportDate, animalCat, reportsCat);
    }

    @Override
    public String toString() {
        return "PersonCat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", chatId=" + chatId +
                ", animalAdoptDate=" + animalAdoptDate +
                ", lastReportDate=" + lastReportDate +
                ", animalCat=" + animalCat +
                ", reportsCat=" + reportsCat +
                '}';
    }
}
