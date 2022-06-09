package com.odeyalo.analog.netflix.exceptions;

public class VideoNotFoundException extends Exception {

    public VideoNotFoundException() {
        super();
    }

    public VideoNotFoundException(String message) {
        super(message);
    }

    public VideoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
