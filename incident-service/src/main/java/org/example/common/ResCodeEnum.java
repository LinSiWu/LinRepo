package org.example.common;

public enum ResCodeEnum {
    SUCCESS(0, "success"),
    FAILURE(1, "服务器忙,请稍后再试"),
    RESPONSE_CODE_PARAM_ERROR(1002, "参数格式错误"),
    RESPONSE_CODE_REQ_CANNOT_EMPTY(1004, "必要的请求参数不能为空"),
    SERVICE_ERROR(1006, "服务异常"),
    RESPONSE_CODE_NO_PERMISSION(1403, "无权访问该系统,请提供令牌信息"),
    RESPONSE_CODE_SYSTEM_ERROR(1500, "系统内部异常"),
    TOKEN_EXPIRE(1606, "token已过期，请重新登录");

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
