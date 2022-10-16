package com.zsl.custombox.common.core.advice;

import com.zsl.custombox.common.core.exception.*;
import com.zsl.custombox.common.core.http.ResponseResult;
import com.zsl.custombox.common.core.http.ResponseResultStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zsl
 * @Date 2022/5/15 19:26
 * @Email 249269610@qq.com
 */
@RestControllerAdvice
//@ControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 全局异常处理（兜底捕捉）
     */
    @ExceptionHandler(Throwable.class)
    public ResponseResult<String> global(Throwable t) {
        // 特殊处理
        if (t instanceof ApiException) {
            return ResponseResult.custom(ResponseResultStatus.BAD_REQUEST.getCode(), t.getMessage(), null);
        } else if (t instanceof GlobalException) {
            return ResponseResult.custom(((GlobalException) t).getCode(), t.getMessage(), null);
        }

        // 打印错误信息
        t.printStackTrace();
        return ResponseResult.failed(t.getMessage());
    }


    /**
     * 捕捉认证异常
     */
    @ExceptionHandler(AbstractAuthenticationException.class)
    public ResponseResult<Void> auth(AbstractAuthenticationException e) {
        ResponseResultStatus status = null;
        if (e instanceof AuthenticationFailedException) {
            status = ResponseResultStatus.UNAUTHORIZED;
        } else if (e instanceof NotLoginException) {
            status = ResponseResultStatus.NOT_LOGIN;
        } else {
            status = ResponseResultStatus.UNAUTHORIZED;
        }

        return ResponseResult.custom(status, null);
    }

    /**
     * 捕捉 Validator参数异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<Map<String, String>> methodArgumentNotValid(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        // 收集message
        Map<String, String> map = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseResult.custom(ResponseResultStatus.BAD_REQUEST, map);
    }
}
