package com.zehraarslan.userservice.exception;

public enum UserErrorMessage implements BaseErrorMessage{

    DUPLICATE_USERNAME("Username is already in use"),
    DUPLICATE_EMAIL("Email is already in use"),
    DUPLICATE_PHONE_NUMBER("Phone number is already in use"),
    INVALID_LATITUDE("INVALID_LATITUDE"),
    INVALID_LONGITUDE("INVALID_LONGITUDE"),
    INVALID_OLD_PASSWORD("Invalid old password"),
    NEW_PASSWORDS_DID_NOT_MATCH("New passwords did not match");

    private final String message;

    UserErrorMessage(String message ) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return message;
    }
}
