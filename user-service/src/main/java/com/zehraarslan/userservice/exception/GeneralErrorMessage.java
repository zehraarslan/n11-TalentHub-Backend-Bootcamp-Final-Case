package com.zehraarslan.userservice.exception;

public enum GeneralErrorMessage implements BaseErrorMessage{
    ITEM_NOT_FOUND("The requested item could not be found."),
    INVALID_DATA("The data provided is invalid."),
    INTERNAL_SERVER_ERROR("An internal server error occurred. Please try again later.");

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
