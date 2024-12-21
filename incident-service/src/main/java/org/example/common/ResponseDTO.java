package org.example.common;

import java.io.Serializable;

public class ResponseDTO<T> implements Serializable {
    protected int code;
    protected String msg;
    protected long timestamp;
    protected T data;

    public ResponseDTO() {
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ResponseDTO<T> success(T data) {
        ResponseDTO<T> apiMessageBean = new ResponseDTO();
        apiMessageBean.setData(data);
        apiMessageBean.setCode(ResCodeEnum.SUCCESS.getCode());
        apiMessageBean.setMsg(ResCodeEnum.SUCCESS.getMessage());
        return apiMessageBean;
    }

    public static <T> ResponseDTO<T> error(T data) {
        ResponseDTO<T> apiMessageBean = new ResponseDTO();
        apiMessageBean.setData(data);
        apiMessageBean.setCode(ResCodeEnum.FAILURE.getCode());
        apiMessageBean.setMsg(ResCodeEnum.FAILURE.getMessage());
        return apiMessageBean;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        return "ResponseDTO(code=" + this.getCode() + ", msg=" + this.getMsg() + ", timestamp=" + this.getTimestamp() + ", data=" + this.getData() + ")";
    }
}
