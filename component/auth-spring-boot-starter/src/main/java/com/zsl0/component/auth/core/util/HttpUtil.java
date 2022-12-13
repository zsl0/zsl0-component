package com.zsl0.component.auth.core.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author zsl0
 * created on 2022/12/12 20:57
 */
public class HttpUtil {

    public static String HEAD = "Authentication-zsl0";

    /**
     * 获取请求头中存储token
     */
    public static String authentication(HttpServletRequest request) {
        String token = null;
        String head = request.getHeader(HEAD);
        if (Objects.nonNull(head) && head.startsWith("Bearer ")) {
            token = head.replace("Bearer ", "").trim();
        }
        return token;
    }
}
