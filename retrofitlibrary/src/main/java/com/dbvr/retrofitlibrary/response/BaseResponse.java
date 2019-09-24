package com.dbvr.retrofitlibrary.response;

/**
 * 统一响应
 * @param <T>
 */
public class BaseResponse<T> {
    private int errno;
    private int code;//500的情况下
    private String msg;//500的情况下
    private String errmsg;
    private T data;

    public int getErrno() {
        return errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }
}
