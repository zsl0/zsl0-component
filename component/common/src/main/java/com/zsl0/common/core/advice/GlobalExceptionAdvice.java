package com.zsl0.common.core.advice;

import com.zsl0.common.core.exception.ApiException;
import com.zsl0.common.core.exception.GlobalException;
import com.zsl0.common.core.exception.auth.authentication.NotAuthenticationException;
import com.zsl0.common.core.exception.collector.ErrorCollector;
import com.zsl0.common.core.http.ResponseResult;
import com.zsl0.common.core.http.ResponseResultStatus;
import com.zsl0.common.core.exception.auth.authentication.AuthenticationFailedException;
import com.zsl0.common.core.exception.auth.authentication.AuthorizationFailedException;
import com.zsl0.common.core.exception.auth.authentication.NotAuthorizationException;
import com.zsl0.common.core.exception.auth.base.AuthCustomException;
import com.zsl0.common.core.exception.auth.token.TokenExpireException;
import com.zsl0.common.core.exception.auth.token.TokenGenerateException;
import com.zsl0.common.core.exception.auth.token.TokenUnknownException;
import com.zsl0.common.core.exception.auth.token.TokenVerifyFailedException;
import com.zsl0.component.common.core.exception.*;
import com.zsl0.util.log.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zsl0
 * create on 2022/5/15 19:26
 * email 249269610@qq.com
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    static Logger log = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    @Autowired(required = false)
    ErrorCollector errorCollector;

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
        log.info("[Throwable] 发生异常，message={}", LogUtil.getLogStackTrace(t));

        // 收集错误信息
        if (Objects.nonNull(errorCollector))
            errorCollector.collectionError(t);

        return ResponseResult.failed(t.getMessage());
    }


    /**
     * 捕捉认证异常
     */
    @ExceptionHandler(AuthCustomException.class)
    public ResponseResult<Void> auth(AuthCustomException e) {
        ResponseResultStatus status;
        if (e instanceof NotAuthenticationException) {
            status = ResponseResultStatus.NOT_LOGIN;
        } else if (e instanceof NotAuthorizationException) {
            status = ResponseResultStatus.FORBIDDEN;
        } else if (e instanceof TokenExpireException) {
            status = ResponseResultStatus.NOT_LOGIN;
        } else if (e instanceof TokenGenerateException) {
            status = ResponseResultStatus.BAD_REQUEST;
        } else if (e instanceof TokenUnknownException) {
            status = ResponseResultStatus.NOT_LOGIN;
        } else if (e instanceof TokenVerifyFailedException) {
            status = ResponseResultStatus.NOT_LOGIN;
        } else if (e instanceof AuthenticationFailedException) {
            status = ResponseResultStatus.LOGIN_FAILED;
        } else if (e instanceof AuthorizationFailedException) {
            status = ResponseResultStatus.AUTHORIZED_FAILED;
        } else {
            status = ResponseResultStatus.BAD_REQUEST;
        }

        String msg = Objects.isNull(e.getMessage()) ? status.getMsg() : e.getMessage();

        log.info("[AuthCustomException] 认证异常，ResponseResultStatus={}，message={}", status, LogUtil.getLogStackTrace(e));
        return ResponseResult.custom(status.getCode(),
                msg
                , null);
    }

    /**
     * 捕捉 Validator参数异常
     */
    @ExceptionHandler(BindException.class)
    public ResponseResult<Map<String, String>> bindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        // 收集message
        Map<String, String> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            String message = fieldError.getDefaultMessage();
            map.put(fieldError.getField(), message);
            sb.append(message);
            sb.append(";");
        }

        log.info("[MethodArgumentNotValidException] 参数异常，message={}", sb);
        return ResponseResult.custom(ResponseResultStatus.BAD_REQUEST, map);
    }
}
