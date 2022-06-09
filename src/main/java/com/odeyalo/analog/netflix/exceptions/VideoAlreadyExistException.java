package com.odeyalo.analog.netflix.exceptions;

public class VideoAlreadyExistException extends Exception {
    public VideoAlreadyExistException() {
    }

    public VideoAlreadyExistException(String message) {
        super(message);
    }

    public VideoAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
