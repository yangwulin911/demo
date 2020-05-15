package com.ywl.common.response;

import lombok.Getter;

/**
 * 响应状态码
 * @author yangwulin
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
    FAIl(5000, "请求失败"),
    USER_NAME_EXIST(4010, "用户名已存在"),
    ACCESS_TOKEN_INVALID(4001, "token失效"),
    UNAUTHORIZED(4002, "资源未授权"),
    INSUFFICIENT_PERMISSIONS(4003, "权限不足")
    ;

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
