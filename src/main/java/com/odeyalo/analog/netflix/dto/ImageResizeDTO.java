package com.odeyalo.analog.netflix.dto;

import org.springframework.web.multipart.MultipartFile;

public class ImageResizeDTO {
    private Integer  height;
    private Integer  width;
    private MultipartFile image;

    public ImageResizeDTO() {
    }

    public ImageResizeDTO(Integer height, Integer width, MultipartFile image) {
        this.height = height;
        this.width = width;
        this.image = image;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ImageResizeDTO{" +
                "height=" + height +
                ", width=" + width +
                '}';
    }
}
