package com.zsl.custombox.common.core.authentication;

import com.zsl.custombox.common.core.http.ResponseResult;
import com.zsl.custombox.common.core.http.ResponseResultStatus;
import com.zsl.custombox.common.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author zsl
 * @Date 2022/5/16 22:12
 * @Email 249269610@qq.com
 */
public class AccessDeniedHandler {

    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JsonUtil.obj2Str(ResponseResult.custom(ResponseResultStatus.NOT_LOGIN, null)));
    }
}
