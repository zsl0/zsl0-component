package com.zsl0.common.core.authentication;

import cn.hutool.json.JSONUtil;
import com.zsl0.common.core.http.ResponseResult;
import com.zsl0.common.core.http.ResponseResultStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证切入点,认证失败时调用
 *
 * @author zsl0
 * create on 2022/5/16 22:04
 * email 249269610@qq.com
 */
public class AuthenticationEntryPoint {

    public void failed(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSONUtil.toJsonStr(ResponseResult.custom(ResponseResultStatus.UNAUTHORIZED, null)));
    }
}
