package com.ilpalazzo.errors;

public class LoginInfoNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LoginInfoNotFoundException(Long username) {
        super("LoginInfo not found with userId: " + username);
    }

    public LoginInfoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginInfoNotFoundException(String message) {
        super(message);
    }
}
