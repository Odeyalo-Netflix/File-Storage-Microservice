package com.odeyalo.analog.netflix.exceptions;

import com.odeyalo.analog.netflix.dto.response.ExceptionOccurredMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ExceptionOccurredMessageDTO> handleImageNotFoundException(ImageNotFoundException e) {
        return buildResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ImageNotReadableException.class)
    public ResponseEntity<ExceptionOccurredMessageDTO> handleImageNotReadableException(ImageNotReadableException e) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ImageUploadProcessFailedException.class)
    public ResponseEntity<ExceptionOccurredMessageDTO> handleImageUploadProcessFailedException(ImageUploadProcessFailedException e) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServerSideException.class)
    public ResponseEntity<ExceptionOccurredMessageDTO> handleServerSideException(ServerSideException e) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UploadException.class)
    public ResponseEntity<ExceptionOccurredMessageDTO> handleUploadException(UploadException e) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(VideoAlreadyExistException.class)
    public ResponseEntity<ExceptionOccurredMessageDTO> handleVideoAlreadyExistException(VideoAlreadyExistException e) {
        return buildResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VideoNotFoundException.class)
    public ResponseEntity<ExceptionOccurredMessageDTO> handleVideoNotFoundException(VideoNotFoundException e) {
        return buildResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VideoUploadException.class)
    public ResponseEntity<ExceptionOccurredMessageDTO> handleVideoNotFoundException(VideoUploadException e) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ExceptionOccurredMessageDTO> handleFileNotFoundException(FileNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND.toString(), "The requested file is not presented on the server", HttpStatus.NOT_FOUND);
    }


    protected ResponseEntity<ExceptionOccurredMessageDTO> buildResponse(String statusMessage, String message, HttpStatus status) {
        return new ResponseEntity<>(new ExceptionOccurredMessageDTO(statusMessage, message), status);
    }
}
