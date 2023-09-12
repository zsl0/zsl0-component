package com.zsl0.common.core.authentication;

import cn.hutool.json.JSONUtil;
import com.zsl0.common.core.http.ResponseResult;
import com.zsl0.common.core.http.ResponseResultStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zsl0
 * create on 2022/5/16 22:12
 * email 249269610@qq.com
 */
public class AccessDeniedHandler {

    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSONUtil.toJsonStr(ResponseResult.custom(ResponseResultStatus.LOGIN_FAILED, null)));
    }
}
