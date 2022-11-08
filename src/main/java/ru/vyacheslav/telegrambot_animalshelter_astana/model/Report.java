package ru.vyacheslav.telegrambot_animalshelter_astana.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String photoPath;
    private Integer photoSize;
    private String description;
    private String reportDate;
    private Long personId;

    public Report() {
    }

    public Report(Long id, String photoPath, Integer photoSize, String description, String reportDate, Long personId) {
        this.id = id;
        this.photoPath = photoPath;
        this.photoSize = photoSize;
        this.description = description;
        this.reportDate = reportDate;
        this.personId = personId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Integer getPhotoSize() {
        return photoSize;
    }

    public void setPhotoSize(Integer photoSize) {
        this.photoSize = photoSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(id, report.id) && Objects.equals(photoPath, report.photoPath) && Objects.equals(photoSize, report.photoSize) && Objects.equals(description, report.description) && Objects.equals(reportDate, report.reportDate) && Objects.equals(personId, report.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, photoPath, photoSize, description, reportDate, personId);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", photoPath='" + photoPath + '\'' +
                ", photoSize=" + photoSize +
                ", description='" + description + '\'' +
                ", reportDate='" + reportDate + '\'' +
                ", personId=" + personId +
                '}';
    }
}
