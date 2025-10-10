package com.grupoK.connector.database.exceptions;

public class UserUsernameAlreadyExistsException extends RuntimeException{
    public UserUsernameAlreadyExistsException(String message) {
        super(message);
    }
}
