package com.IslanaCarvalho.UserHub.infrastructure.exception;

public class EmailAlreadyRegisteredException extends RuntimeException {
    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
}
