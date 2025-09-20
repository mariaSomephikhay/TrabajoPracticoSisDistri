package com.grupoK.Tp1SistemasDistribuidos.exceptions;

public class UserEmailAlreadyExistsException extends RuntimeException{
    public UserEmailAlreadyExistsException(String message) {
        super(message);
    }
}
