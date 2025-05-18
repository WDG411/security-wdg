package com.cgr.entity;

public class ResponseModel<T> {
    /**
     * 响应码
     */
    private int code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    public ResponseModel(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResponseModel<T> success(T data) {
        return new ResponseModel<T>(200, "success", data);
    }

    public static <T> ResponseModel<T> error(T data) {
        return new ResponseModel<T>(401, "error", data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
