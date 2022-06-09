package com.odeyalo.analog.netflix.exceptions;

public class VideoUploadException extends UploadException {

    public VideoUploadException() {
    }

    public VideoUploadException(String message) {
        super(message);
    }

    public VideoUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
