package com.ywl.common.response;

import lombok.Getter;

/**
 * 响应状态码
 * @author Administrator
 */

@Getter
public enum RespCodeEnum {

    /**
     * 请求成功
     */
    SUCCESS(2000,"请求成功"),
    /**
     * 请求失败
     */
    FAIl(5000, "请求失败");

    /**
     * 响应码
     */
    private int code;

    /**
     * 响应信息
     */
    private String message;

    RespCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
