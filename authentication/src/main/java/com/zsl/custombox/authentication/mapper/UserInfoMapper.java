package com.zsl.custombox.authentication.mapper;

import com.zsl.custombox.authentication.model.pojo.login.UserInfo;

import java.util.List;

/**
 * 用户Mapper
 *
 * @Author zsl
 * @Date 2022/6/8 16:52
 * @Email 249269610@qq.com
 */
public interface UserInfoMapper {
    /**
     * 查询所有用户
     */
    List<UserInfo> queryAll();

    /**
     * 通过id查询
     */
    UserInfo queryById(Long id);

    /**
     * 通过username查询
     */
    UserInfo queryByUsername(String username);

    /**
     * 通过email查询
     */
    UserInfo queryByEmail(String email);

    /**
     * 通过phone查询
     */
    UserInfo queryByPhone(String phone);

    /**
     * 新增用户
     */
    void insert(UserInfo userInfo);

    /**
     * 通过id更新
     */
    void updateById(UserInfo userInfo);

    /**
     * 通过id删除（设置deleted标志位为1）
     */
    void deleteById(Long id);
}
