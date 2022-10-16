package com.zsl.custombox.common.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * http工具
 *
 * @Author zsl
 * @Date 2022/6/1 22:34
 * @Email 249269610@qq.com
 */
public class HttpUtil {

    /**
     * 获取请求头中存储token
     */
    public static String authentication(HttpServletRequest request) {
        String token = null;
        String head = request.getHeader("Authentication");
        if (Objects.nonNull(head) && head.startsWith("Bearer ")) {
            token = head.replace("Bearer ", "").trim();
        }
        return token;
    }

    /**
     * 设置响应头信息
     */
    public static void setResponseHeader(String key, String value) {
        ServletUtil.getResponse().setHeader(key, value);
    }
}
