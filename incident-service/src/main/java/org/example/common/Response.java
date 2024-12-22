package org.example.common;

import lombok.Data;

import java.io.Serializable;
@Data
public class Response<T> implements Serializable {
    protected int code;
    protected String msg;
    protected T data;

    public static <T> Response<T> success(T data) {
        Response<T> apiMessageBean = new Response<>();
        apiMessageBean.setData(data);
        apiMessageBean.setCode(ResCodeEnum.SUCCESS.getCode());
        apiMessageBean.setMsg(ResCodeEnum.SUCCESS.getMessage());
        return apiMessageBean;
    }

    public static <T> Response<T> error(T data, String msg) {
        Response<T> apiMessageBean = new Response<>();
        apiMessageBean.setData(data);
        apiMessageBean.setCode(ResCodeEnum.FAILURE.getCode());
        apiMessageBean.setMsg(msg);
        return apiMessageBean;
    }
}
