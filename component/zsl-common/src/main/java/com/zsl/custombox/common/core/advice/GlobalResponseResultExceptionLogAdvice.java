package com.zsl.custombox.common.core.advice;

import com.zsl.custombox.common.core.http.ResponseResult;
import com.zsl.custombox.common.util.SystemLogContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * 全局统一返回异常日志处理
 *  出现异常时，返回ResponseResult进行捕捉,设置AccessLogContext响应码
 *
 * @Author zsl
 * @Date 2022/5/15 19:10
 * @Email 249269610@qq.com
 */
@ControllerAdvice
@Order(Integer.MAX_VALUE - 20)
public class GlobalResponseResultExceptionLogAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // 返回 true则执行 beforeBodyWrite方法,
        // 返回类型是 ResponseResult，会执行
        return returnType.getParameterType().equals(ResponseResult.class);
    }


    /**
     * 保存访问日志返回code和msg
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof ResponseResult) {
            ResponseResult  result = ((ResponseResult) body);
            if (Objects.nonNull(SystemLogContextHolder.get())) {
                SystemLogContextHolder.get().setRespCode(result.getCode()).setRespMsg(result.getMsg());
            }
        }
        return body;
    }
}
