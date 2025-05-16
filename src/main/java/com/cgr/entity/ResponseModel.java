package com.cgr.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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

    public static <T> ResponseModel<T> success(T data) {
        return new ResponseModel<T>(200, "success", data);
    }

    public static <T> ResponseModel<T> error(T data) {
        return new ResponseModel<T>(401, "error", data);
    }

}
