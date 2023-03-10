package com.zsl0.demo.authentication.service.user.impl;

import com.zsl0.demo.authentication.mapper.UserInfoMapper;
import com.zsl0.demo.authentication.model.pojo.login.UserInfo;
import com.zsl0.demo.authentication.service.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zsl0
 * create on 2022/6/10 17:32
 * email 249269610@qq.com
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public List<UserInfo> queryAll() {
        return userInfoMapper.queryAll();
    }

    @Override
    public UserInfo queryById(Long userId) {
        return userInfoMapper.queryById(userId);
    }

    @Override
    public int add(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
        return 1;
    }

    @Override
    public int update(UserInfo userInfo) {
        userInfoMapper.updateById(userInfo);
        return 1;
    }

    @Override
    public int remove(Long userId) {
        userInfoMapper.deleteById(userId);
        return 1;
    }
}
