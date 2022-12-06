package ru.vyacheslav.telegrambot_animalshelter_astana.dto;

public class FotoObjectDto {
    private String mediaType;
    private String photoPath;
    private byte[] photoData;
    private Integer photoSize;

    public FotoObjectDto() {
    }

    public FotoObjectDto(String mediaType, String photoPath, byte[] photoData, Integer photoSize) {
        this.mediaType = mediaType;
        this.photoPath = photoPath;
        this.photoData = photoData;
        this.photoSize = photoSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public byte[] getPhotoData() {
        return photoData;
    }

    public void setPhotoData(byte[] photoData) {
        this.photoData = photoData;
    }

    public Integer getPhotoSize() {
        return photoSize;
    }

    public void setPhotoSize(Integer photoSize) {
        this.photoSize = photoSize;
    }
}
