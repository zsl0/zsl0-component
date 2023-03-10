package com.zsl0.demo.authentication.service.user;

import com.zsl0.demo.authentication.model.pojo.login.UserInfo;

import java.util.List;

/**
 * @author zsl0
 * create on 2022/6/10 17:29
 * email 249269610@qq.com
 */
public interface UserInfoService {

    /**
     * 查询所有用户
     */
    List<UserInfo> queryAll();

    /**
     * 根据id查询
     */
    UserInfo queryById(Long userId);

    /**
     * 新增用户
     * @return 1,执行成功
     */
    int add(UserInfo userInfo);

    /**
     * 更新用户
     * @return 1,执行成功
     */
    int update(UserInfo userInfo);

    /**
     * 删除用户
     * @return 1,执行成功
     */
    int remove(Long userId);
}
