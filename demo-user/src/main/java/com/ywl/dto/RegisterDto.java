package com.ywl.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 用户注册dto
 * @author Administrator
 */
@Data
public class RegisterDto {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @Length(min = 8, max = 20, message = "密码长度必须在8位至16位之间")
    private String password;

}
