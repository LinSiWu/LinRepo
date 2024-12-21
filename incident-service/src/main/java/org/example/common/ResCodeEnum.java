package org.example.common;

public enum ResCodeEnum {
    SUCCESS(0, "success"),
    FAILURE(1, "failed"),
    RESPONSE_CODE_PARAM_ERROR(1002, "param error"),
    RESPONSE_CODE_REQ_INVALID(1004, "request invalid"),
    SERVICE_ERROR(1006, "server error");

    private int code;
    private String message;

    private ResCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
