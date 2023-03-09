package com.zsl0.component.auth.core.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author zsl0
 * created on 2022/12/12 20:57
 */
public class HttpUtil {

    public static String HEAD = "Authorization-zsl0";

    /**
     * 获取请求头中存储token
     * @param request ServletRequest对象
     * @return token
     */
    public static String authentication(HttpServletRequest request) {
        String token = null;
        String head = request.getHeader(HEAD);
        if (Objects.nonNull(head) && head.startsWith("Bearer ")) {
            token = head.replace("Bearer ", "").trim();
        }
        return token;
    }

    /**
     * 获取客户端ip地址
     * @param request ServletRequest对象
     * @return ip地址
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
