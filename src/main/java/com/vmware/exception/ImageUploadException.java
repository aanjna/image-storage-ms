package com.vmware.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImageUploadException extends RuntimeException {
    private String message;

    public ImageUploadException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

