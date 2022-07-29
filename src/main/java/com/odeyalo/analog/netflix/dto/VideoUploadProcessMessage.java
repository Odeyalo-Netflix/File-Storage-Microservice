package com.odeyalo.analog.netflix.dto;

import com.odeyalo.analog.netflix.dto.response.SuccessUploadVideoResponseDTO;

public class VideoUploadProcessMessage {
    private UploadStatus status;
    private SuccessUploadVideoResponseDTO dto;
    private String message;

    public VideoUploadProcessMessage(UploadStatus status) {
        this.status = status;
    }

    public VideoUploadProcessMessage(UploadStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public VideoUploadProcessMessage(UploadStatus status, SuccessUploadVideoResponseDTO dto) {
        this.status = status;
        this.dto = dto;
    }

    public VideoUploadProcessMessage(UploadStatus status, SuccessUploadVideoResponseDTO dto, String message) {
        this.status = status;
        this.dto = dto;
        this.message = message;
    }

    public VideoUploadProcessMessage() {
    }

    public UploadStatus getStatus() {
        return status;
    }

    public void setStatus(UploadStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SuccessUploadVideoResponseDTO getDto() {
        return dto;
    }

    public void setDto(SuccessUploadVideoResponseDTO dto) {
        this.dto = dto;
    }
}
