package com.zsl0.log.core.service.operator;

/**
 * @author zsl0
 * create on 2022/5/29 13:09
 * email 249269610@qq.com
 */
public interface IOperatorGetService {
    /**
     * 可以在里面外部的获取当前登陆的用户，比如 UserContext.getCurrentUser()
     *
     * @return 转换成Operator返回
     */
    String getUserId();
}
