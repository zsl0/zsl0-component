package com.zsl.custombox.authentication.core.advice;

import com.zsl.custombox.common.core.http.NotResponseBody;
import com.zsl.custombox.common.core.http.ResponseResult;
import com.zsl.custombox.common.model.log.SystemLogContext;
import com.zsl.custombox.common.util.JsonUtil;
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
 * 全局统一返回处理（为每个web模块配置此advcie,并限制basePackages, 防止拦截其它controller 如：Swagger2Controller）
 *
 * @Author zsl
 * @Date 2022/5/15 19:10
 * @Email 249269610@qq.com
 */
@ControllerAdvice(basePackages = "com.zsl.custombox.authentication.controller")
@Order(Integer.MAX_VALUE - 20)
public class GlobalResponseResultAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // 返回 true则执行 beforeBodyWrite方法,
        // 返回类型不是 ResponseResult，且没有 @NotResponseBody注解，会执行
        return !returnType.getParameterType().equals(ResponseResult.class) && !returnType.hasMethodAnnotation(NotResponseBody.class);
    }


    /**
     * 处理返回信息，并保存访问日志返回code和msg
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 若为 String类型，则直接返回 json字符串
        if (returnType.getGenericParameterType().equals(String.class)) {
            SystemLogContextHolder.get().setRespCode(2000).setRespMsg("");
            return JsonUtil.obj2Str(body);
        }
        ResponseResult<Object> result = ResponseResult.success(body);
        SystemLogContext systemLogContext = SystemLogContextHolder.get();
        // 不走AccessLogInterceptor拦截器，systemLogContext.get为null
        if (Objects.nonNull(systemLogContext)) {
            systemLogContext.setRespCode(result.getCode()).setRespMsg(result.getMsg());
        }
        return result;
    }
}
