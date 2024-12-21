package org.example.common;

public enum IncidentStatusEnum {
    PROCESSING(0, "processing"),
    FINISH(1, "finish");

    private int code;
    private String message;

    private IncidentStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public static boolean existCode(int target) {
        for (IncidentStatusEnum levelEnum : IncidentStatusEnum.values()) {
            if (levelEnum.code == target) {
                return true;
            }
        }
        return false;
    }
}
