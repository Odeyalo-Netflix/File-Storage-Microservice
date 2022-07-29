package com.odeyalo.analog.netflix.dto;

public class VideoUploadedSuccessMessageDTO {
    private final  String eventId;
    private final String videoId;
    private final String fileId;

    public VideoUploadedSuccessMessageDTO(String eventId, String videoId, String fileId) {
        this.eventId = eventId;
        this.videoId = videoId;
        this.fileId = fileId;
    }

    public String getEventId() {
        return eventId;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getFileId() {
        return fileId;
    }
}
