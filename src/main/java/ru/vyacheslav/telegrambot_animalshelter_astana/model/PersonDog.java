package ru.vyacheslav.telegrambot_animalshelter_astana.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
//@Table(name = "Person")
public class PersonDog extends AbstractPerson{
    @JsonIgnore
    @OneToMany(mappedBy = "personDog")
    private Set <Report> reports;

    public PersonDog() {
    }

    public PersonDog(Long id, String name, String email, String phone, String address, Long chatId, LocalDate animalAdoptDate, LocalDate lastReportDate, Animal animal, Set<Report> reports) {
        super(id, name, email, phone, address, chatId, animalAdoptDate, lastReportDate, animal);
        this.reports = reports;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }
}
