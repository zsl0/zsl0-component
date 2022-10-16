package com.zsl.custombox.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Request、Response相关操作
 *
 * @Author zsl
 * @Date 2022/5/16 21:28
 * @Email 249269610@qq.com
 */
public class ServletUtil {

    /**
     * 获取ServletRequest
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取ServletResponse
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    /**
     * 获取ip地址
     */
    public static String getIp() {
        HttpServletRequest request = getRequest();
        return request.getHeader("host");
    }
}
