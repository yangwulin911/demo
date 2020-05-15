package com.ywl.common.handler;

import com.ywl.common.exception.ApplicationException;
import com.ywl.common.response.R;
import com.ywl.common.response.RespCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 * 如果一个异常能匹配多个 @ExceptionHandler 时，选择匹配深度最小的Exception(即最匹配的Exception)
 * @author yangwulin
 */
@RestControllerAdvice
public class ApplicationExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    /**
     * ApplicationException 异常处理
     * @param e 异常对象
     * @return 通用返回模型
     */
    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.OK)
    public R handlerApplicationException(ApplicationException e){
        return R.createError(e.getRespCodeEnum());
    }

    /**
     * 其它运行异常处理
     * @param e 异常对象
     * @return 通用返回模型
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    public R handlerRuntimeException(RuntimeException e) {
        logger.error("程序出错", e);
        return R.createError();
    }
}
