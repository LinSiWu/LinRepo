package org.example.common;

public enum IncidentLevelEnum {
    LOW(0, "low"),
    HIGH(1, "high");

    private int code;
    private String message;

    private IncidentLevelEnum(int code, String message) {
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
        for (IncidentLevelEnum levelEnum : IncidentLevelEnum.values()) {
            if (levelEnum.code == target) {
                return true;
            }
        }
        return false;
    }
}
