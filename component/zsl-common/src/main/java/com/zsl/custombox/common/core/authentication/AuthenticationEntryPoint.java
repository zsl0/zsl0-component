package com.zsl.custombox.common.core.authentication;

import com.zsl.custombox.common.core.http.ResponseResult;
import com.zsl.custombox.common.core.http.ResponseResultStatus;
import com.zsl.custombox.common.util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证切入点,认证失败时调用
 *
 * @Author zsl
 * @Date 2022/5/16 22:04
 * @Email 249269610@qq.com
 */
public class AuthenticationEntryPoint {

    public void failed(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JsonUtil.obj2Str(ResponseResult.custom(ResponseResultStatus.UNAUTHORIZED, null)));
    }
}
