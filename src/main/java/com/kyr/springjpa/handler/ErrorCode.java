package com.kyr.springjpa.handler;

public enum ErrorCode {
    INVALID_PARAMETER(400, null, "Invalid Request Data"),
    EXPIRATION(410, "410", "It Was Expired"),
    NOT_FOUND(404, "404", "Not Found"),
    GIS_ERROR(500,"500","GIS ERROR");

    private final String code;
    private final String message;
    private final int status;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}

