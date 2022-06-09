package com.odeyalo.analog.netflix.dto.response;

import java.util.List;

public class UserImagesResponseDTO {
    private List<String> images;
    private String userId;

    public UserImagesResponseDTO() {}

    public UserImagesResponseDTO(List<String> images, String userId) {
        this.images = images;
        this.userId = userId;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
