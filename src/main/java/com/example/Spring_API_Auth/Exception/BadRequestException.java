package com.example.Spring_API_Auth.Exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException{
    private final String field;
    public BadRequestException(String message, String field){
        super(message);
        this.field = field;
    }
}
