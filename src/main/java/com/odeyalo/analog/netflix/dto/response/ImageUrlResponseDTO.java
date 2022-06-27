package com.odeyalo.analog.netflix.dto.response;

public class ImageUrlResponseDTO {
    private String imageUrl;


    public ImageUrlResponseDTO() {
    }

    public ImageUrlResponseDTO(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
