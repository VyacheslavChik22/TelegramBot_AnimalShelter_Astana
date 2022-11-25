package ru.vyacheslav.telegrambot_animalshelter_astana.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "Report_cat")
public class ReportCat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String photoPath;
    private Integer photoSize;
    private String mediaType;
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] photoData;
    private String description;
    private LocalDate reportDate;

    public ReportCat() {
    }

    @ManyToOne
    //@JoinColumn(name = "person_cat_id")
    private PersonCat personCat;

    public PersonCat getPersonCat() {
        return personCat;
    }

    public void setPersonCat(PersonCat personCat) {
        this.personCat = personCat;
    }

    public ReportCat(Long id, String photoPath, Integer photoSize, String mediaType, byte[] photoData, String description, LocalDate reportDate, PersonCat personCat) {
        this.id = id;
        this.photoPath = photoPath;
        this.photoSize = photoSize;
        this.mediaType = mediaType;
        this.photoData = photoData;
        this.description = description;
        this.reportDate = reportDate;
        this.personCat = personCat;
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
        ReportCat reportCat = (ReportCat) o;
        return id.equals(reportCat.id) && photoPath.equals(reportCat.photoPath) && photoSize.equals(reportCat.photoSize) && mediaType.equals(reportCat.mediaType) && Arrays.equals(photoData, reportCat.photoData) && description.equals(reportCat.description) && reportDate.equals(reportCat.reportDate) && personCat.equals(reportCat.personCat);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, photoPath, photoSize, mediaType, description, reportDate, personCat);
        result = 31 * result + Arrays.hashCode(photoData);
        return result;
    }

    @Override
    public String toString() {
        return "ReportCat{" +
                "id=" + id +
                ", photoPath='" + photoPath + '\'' +
                ", photoSize=" + photoSize +
                ", mediaType='" + mediaType + '\'' +
                ", photoData=" + Arrays.toString(photoData) +
                ", description='" + description + '\'' +
                ", reportDate=" + reportDate +
                ", personCat=" + personCat +
                '}';
    }
}
