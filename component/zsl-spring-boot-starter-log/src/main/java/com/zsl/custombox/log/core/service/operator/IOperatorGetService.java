package com.zsl.custombox.log.core.service.operator;

import com.zsl.custombox.common.model.authentication.Authentication;
import org.springframework.expression.spel.ast.Operator;

/**
 * @Author zsl
 * @Date 2022/5/29 13:09
 * @Email 249269610@qq.com
 */
public interface IOperatorGetService {
    /**
     * 可以在里面外部的获取当前登陆的用户，比如 UserContext.getCurrentUser()
     *
     * @return 转换成Operator返回
     */
    Authentication getUser();
}
