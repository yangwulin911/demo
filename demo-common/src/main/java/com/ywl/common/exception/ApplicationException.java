package com.ywl.common.exception;

import com.ywl.common.response.RespCodeEnum;
import lombok.Getter;

/**
 * @author yangwulin
 */
@Getter
public class ApplicationException extends RuntimeException {

    private RespCodeEnum respCodeEnum;

    public ApplicationException(RespCodeEnum respCodeEnum){
        super(respCodeEnum.getMessage());
        this.respCodeEnum = respCodeEnum;
    }

}
