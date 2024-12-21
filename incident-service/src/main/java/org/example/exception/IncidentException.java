package org.example.exception;

import lombok.Data;

@Data
public class IncidentException extends RuntimeException{
    private final int code;

    public IncidentException(int code, String message) {
        super(message);
        this.code = code;
    }
}
