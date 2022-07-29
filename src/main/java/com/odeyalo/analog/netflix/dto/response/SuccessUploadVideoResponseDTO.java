package com.odeyalo.analog.netflix.dto.response;

public class SuccessUploadVideoResponseDTO {
    private String videoId;

    public SuccessUploadVideoResponseDTO() {
    }

    public SuccessUploadVideoResponseDTO(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
