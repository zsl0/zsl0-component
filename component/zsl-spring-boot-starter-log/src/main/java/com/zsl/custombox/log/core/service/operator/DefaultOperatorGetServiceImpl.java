package com.zsl.custombox.log.core.service.operator;

import com.zsl.custombox.common.model.authentication.Authentication;
import com.zsl.custombox.common.util.SecurityContextHolder;

import java.util.Optional;

/**
 * @Author zsl
 * @Date 2022/5/29 13:10
 * @Email 249269610@qq.com
 */
public class DefaultOperatorGetServiceImpl implements IOperatorGetService {
    @Override
    public Authentication getUser() {
        // SecurityContextHolder获取用户上下文的方法
        return Optional.ofNullable(SecurityContextHolder.getAuth())
                .orElseThrow(()->new IllegalArgumentException("user is null"));
    }
}
