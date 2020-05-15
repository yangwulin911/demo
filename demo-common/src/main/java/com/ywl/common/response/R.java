package com.ywl.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 请求响应工具类
 * @author yangwulin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class R<T> implements Serializable {

    /**
     * 响应码
     */
    private int code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 请求返回数据
     */
    private T data;

    private R(RespCodeEnum respCodeEnum, String message, T data) {
        this.code = respCodeEnum.getCode();
        this.message = message;
        this.data = data;
    }
    private R(RespCodeEnum respCodeEnum) {
        this.code = respCodeEnum.getCode();
        this.message = respCodeEnum.getMessage();
    }

    private R(String message) {
        this.code = RespCodeEnum.FAIl.getCode();
        this.message = message;
    }

    /**
     * 请求成功构造方法
     * @param data 返回数据
     * @param <T> 返回数据的类型
     * @return 指定结构的数据
     */
    public static <T> R<T> createSuccess(T data) {
        return new R<T>(RespCodeEnum.SUCCESS, null, data);
    }

    public static R createSuccess() {
        return new R<>(RespCodeEnum.SUCCESS, null, null);
    }

    /**
     * 请求失败构造方法
     * @param message 错误信息
     * @return 指定结构的数据
     */
    public static R createError(String message) {
        return new R<>(message);
    }

    public static R createError() {
        return new R<>(RespCodeEnum.FAIl.getMessage());
    }

    /**
     * 请求失败构造方法
     * @param respCodeEnum 错误码
     * @param message 错误信息
     * @return 指定结构的数据
     */
    public static R createError(RespCodeEnum respCodeEnum, String message) {
        return new R<>(respCodeEnum.getCode(), message, null);
    }
    /**
     * 请求失败构造方法
     * @param respCodeEnum 错误码
     * @return 指定结构的数据
     */
    public static R createError(RespCodeEnum respCodeEnum) {
        return new R<>(respCodeEnum);
    }

}
