package org.example.common;

import lombok.Data;

import java.io.Serializable;
@Data
public class ResponseDTO<T> implements Serializable {
    protected int code;
    protected String msg;
    protected long timestamp;
    protected T data;

    public static <T> ResponseDTO<T> success(T data) {
        ResponseDTO<T> apiMessageBean = new ResponseDTO<>();
        apiMessageBean.setData(data);
        apiMessageBean.setCode(ResCodeEnum.SUCCESS.getCode());
        apiMessageBean.setMsg(ResCodeEnum.SUCCESS.getMessage());
        return apiMessageBean;
    }

    public static <T> ResponseDTO<T> error(T data, String msg) {
        ResponseDTO<T> apiMessageBean = new ResponseDTO<>();
        apiMessageBean.setData(data);
        apiMessageBean.setCode(ResCodeEnum.FAILURE.getCode());
        apiMessageBean.setMsg(msg);
        return apiMessageBean;
    }
}
