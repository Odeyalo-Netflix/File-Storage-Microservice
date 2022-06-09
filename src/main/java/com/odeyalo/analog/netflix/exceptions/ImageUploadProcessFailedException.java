package com.odeyalo.analog.netflix.exceptions;

public class ImageUploadProcessFailedException extends UploadException {

    public ImageUploadProcessFailedException() {
    }

    public ImageUploadProcessFailedException(String message) {
        super(message);
    }

    public ImageUploadProcessFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
