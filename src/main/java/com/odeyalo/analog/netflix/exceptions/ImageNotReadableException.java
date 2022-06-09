package com.odeyalo.analog.netflix.exceptions;

public class ImageNotReadableException extends Exception {
    public ImageNotReadableException() {
        super();
    }

    public ImageNotReadableException(String message) {
        super(message);
    }

    public ImageNotReadableException(String message, Throwable cause) {
        super(message, cause);
    }
}
