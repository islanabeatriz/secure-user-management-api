package com.IslanaCarvalho.UserHub.infrastructure.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
