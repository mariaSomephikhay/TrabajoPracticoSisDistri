package com.grupoK.Tp1SistemasDistribuidos.exceptions;

public class UserUsernameAlreadyExistsException extends RuntimeException{
    public UserUsernameAlreadyExistsException(String message) {
        super(message);
    }
}
