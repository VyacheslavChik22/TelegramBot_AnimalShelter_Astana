package ru.vyacheslav.telegrambot_animalshelter_astana.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "Report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String photoPath;
    private Integer photoSize;
    private String mediaType;
    @Lob
    private byte[] photoData;
    private String description;
    private LocalDate reportDate;


    public Report() {
    }

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Report(Long id, String photoPath, Integer photoSize, String mediaType, byte[] photoData, String description, LocalDate reportDate) {
        this.id = id;
        this.photoPath = photoPath;
        this.photoSize = photoSize;
        this.mediaType = mediaType;
        this.photoData = photoData;
        this.description = description;
        this.reportDate = reportDate;
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

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getPhotoData() {
        return photoData;
    }

    public void setPhotoData(byte[] photoData) {
        this.photoData = photoData;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(id, report.id) && Objects.equals(photoPath, report.photoPath) && Objects.equals(photoSize, report.photoSize) && Objects.equals(mediaType, report.mediaType) && Arrays.equals(photoData, report.photoData) && Objects.equals(description, report.description) && Objects.equals(reportDate, report.reportDate);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, photoPath, photoSize, mediaType, description, reportDate);
        result = 31 * result + Arrays.hashCode(photoData);
        return result;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", photoPath='" + photoPath + '\'' +
                ", photoSize=" + photoSize +
                ", mediaType='" + mediaType + '\'' +
                ", photoData=" + Arrays.toString(photoData) +
                ", description='" + description + '\'' +
                ", reportDate=" + reportDate +
                '}';
    }
}
