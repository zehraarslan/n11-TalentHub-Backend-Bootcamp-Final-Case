package com.zehraarslan.restaurantservice.exception;

public enum GeneralErrorMessage implements BaseErrorMessage{
    ITEM_NOT_FOUND("The requested item could not be found."),
    ILLEGAL_ARGUMENT_EXCEPTION("Fields cannot be null"),
    ILLEGAL_SCORE_ARGUMENT_EXCEPTION("Invalid score range. Score should be between 1 and 5."),
    INVALID_DATA("The data provided is invalid."),
    INTERNAL_SERVER_ERROR("An internal server error occurred. Please try again later."),
    INVALID_LATITUDE("INVALID_LATITUDE"),
    INVALID_LONGITUDE("INVALID_LONGITUDE"),
    DUPLICATE_EMAIL("Email is already in use"),
    DUPLICATE_PHONE_NUMBER("Phone number is already in use"),
    INVALID_OLD_PASSWORD("Invalid old password"),
    NEW_PASSWORDS_DID_NOT_MATCH("New passwords did not match");

    private final String message;

    GeneralErrorMessage(String message ) {
        this.message = message;
    }

    public String toString() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
