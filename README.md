# component

zsl0-component，zsl0组件简单封装日常开发常用一些功能，实现插拔式组件，以导入依赖即可开箱即用，重写抽象类、yaml指定配置参数实现自定义功能。

| 组件名称                        | 功能     |
|-----------------------------|--------|
| common-spring-boot-starter  | 公共组件   |
| swagger-spring-boot-starter | Api文档  |
| auth-spring-boot-starter    | 认证组件   |
| log-spring-boot-starter     | 日志组件   |

## 描述

实现一款具备绝大部分技术应用程序，经过不断迭代、经验的增长不断完善此项目。

## 使用技术及版本

| name | version     |
| ---|-------------|
| JDK | 8           |
| springboot| 2.5.6       |
| redis | --          |
| mail | --          |
| freemarker | --          |
| validation | --          |
| lombok | --          |
| mapstrct | 1.4.2.Final |
| mysql | 8.0.25      |
| swagger | 3.0.0       |
| JWT | 3.18.2      |
| hutool | 5.8.8       |

## 架构

todo 架构图

# Quick Start
## swagger-spring-boot-starter（API文档）

1. 添加依赖：
```xml
        <!--        API文档组件-->
        <dependency>
            <groupId>com.zsl0</groupId>
            <artifactId>swagger-spring-boot-starter</artifactId>
            <version>#{latest.version}</version>
        </dependency>
```

2. 编写yaml配置：
```yaml
# swagger组件配置
swagger:
  # 是否开启
  enable: true
  # 应用程序名称
  application-name: 后台管理项目
  # 应用程序版本
  application-version: v0.0.1
  # 应用程序描述
  application-description: 功能：后台管理接口文档，用户、角色、菜单、认证、授权等接口
  # controller扫描包
  controller-base-package: com.zsl0.cloud.admin.controller
  # 分组名称
  group-name: ${spring.application.name}
  # 获取token url
  password-token-url: http://${server.ip}:${server.port}
  # 服务url
  service-url: http://${server.ip}:${port.gateway}/${spring.application.name}
```

3. 使用swagger相关注解；

## log-spring-boot-starter（日志）
1. 配置yaml：
```yaml
# 日志存储位置
log:
  storage-path: /Users/zsl0/IdeaProjects/gitProjects/zsl0-component/logs
```

2. 重写接口`ILogRecordService`，实现日志存储方式(默认使用logback打印)。
```java
package com.zsl0.auth.config.auth.log;

import com.zsl0.component.auth.core.model.Authentication;
import com.zsl0.component.auth.core.util.SecurityContextHolder;
import com.zsl0.component.log.core.service.record.ILogRecordService;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author zsl0
 * created on 2022/12/19 19:51
 */
@Component
public class MyLogRecordServiceImpl implements ILogRecordService {
    @Override
    public void record(String logRecord) {
        // todo 实现自定义存储日志方式 默认 log.info(logRecord);
        System.out.println(logRecord);
    }

    @Override
    public String getUserId() {
        // todo 自定义获取当前用户唯一凭证
        return Optional.ofNullable(SecurityContextHolder.getAuth())
                .map(Authentication::getUserId)
                .map(Object::toString)
                .orElse("0");
    }
}
```

3. 使用@LogRecord注解记录日志
```java
public class UserServiceImpl implements UserService {
    // 省略...
    @Override
    @LogRecord(value = "#{name}修改用户信息为#{param}", bizNo = "1")
    public void modifyUser(UserModifyParam param) {
        // 日志记录添加变量
        LogRecordContext.setVariable("name", "zsl0");
        
        User user = UserConverter.INSTANCE.toPojo(param);

        // md5加密
        if (Objects.nonNull(user.getPassword())) {
            user.setPassword(CryptoUtil.md5Hex(user.getPassword()));
        }

        userMapper.updateUserInfo(user);
    }
}
```

## auth-spring-boot-starter（鉴权）
1. 重写接口`PermissionProvide`，实现自定义权限认证方式，默认放行;
- ACL
- RBAC

自实现RBAC样例：
```java
package com.zsl0.project.webservice.core.auth;

import com.zsl0.component.auth.core.model.Authentication;
import com.zsl0.component.auth.core.model.PermissionProvide;
import com.zsl0.component.auth.core.util.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author zsl0
 * created on 2023/2/13 11:34
 */
@Component
public class AuthPermissionProvide implements PermissionProvide {

    @Override
    public boolean hasPermission(String requirePermission) {
        // 根据角色判断权限
        Authentication auth = SecurityContextHolder.getAuth();
        String[] role = auth.getPermissions();
        List<String> roles = Arrays.asList(role);

        // 系统管理员直接放行
        if (roles.contains("system")) {
            return true;
        }

        return roles.contains(requirePermission);
    }
}
```

2. 实现登陆逻辑样例：
```java
package com.zsl0.project.webservice.service.auth.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.zsl0.component.auth.core.util.TokenUtil;
import com.zsl0.component.common.core.exception.auth.authentication.AuthenticationFailedException;
import com.zsl0.project.repo.mapper.auth.UserMapper;
import com.zsl0.project.repo.pojo.auth.Role;
import com.zsl0.project.repo.pojo.auth.User;
import com.zsl0.project.webservice.manage.email.EmailManage;
import com.zsl0.project.webservice.manage.freemark.FreemarkerManage;
import com.zsl0.project.webservice.model.param.login.LoginEmailParam;
import com.zsl0.project.webservice.model.param.login.LoginPhoneParam;
import com.zsl0.project.webservice.model.param.login.LoginPwdParam;
import com.zsl0.project.webservice.model.vo.login.LoginTokenVo;
import com.zsl0.project.webservice.service.auth.LoginService;
import com.zsl0.util.spring.WebUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zsl0
 * created on 2023/3/8 9:51
 */
@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private static final long EXPIRE_TIME = 60 * 3;

    /**
     * 邮件验证码临时缓存 （非单机情况，可使用redis替换）
     */
    public static Cache<String, String> EMAIL_CACHE = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofSeconds(EXPIRE_TIME))
            .build();

    /**
     * 手机号验证码临时缓存 （非单机情况，可使用redis替换）
     */
    public static Cache<String, String> PHONE_CACHE = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofSeconds(EXPIRE_TIME))
            .build();

    static Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    private UserMapper userMapper;

    private EmailManage emailManage;

    private FreemarkerManage freemarkerManage;

    @Override
    public LoginTokenVo pwdLogin(LoginPwdParam param) {
        String username = param.getUsername();
        String password = param.getPassword();

        // 查询用户
        User user = userMapper.selectUserByUsername(username);

        // 校验(认证)
        authentication(user);

        // 校验密码是否合法
        if (!Objects.equals(user.getPassword(), password)) {
            throw new AuthenticationFailedException("用户或密码错误！");
        }

        // 登录操作记录
        loginRecord(user);

        // 授权
        return authorization(user);
    }


    @Override
    public LoginTokenVo emailLogin(LoginEmailParam param) {
        String email = param.getEmail();
        String code = param.getCode();

        // 查询用户
        User user = userMapper.selectUserByEmail(email);

        // 校验(认证)
        authentication(user);

        // 检查code是否正确
        String realCode = LoginServiceImpl.EMAIL_CACHE.getIfPresent(email);
        if (!Objects.equals(realCode, code)) {
            throw new AuthenticationFailedException("邮箱验证码错误！");
        }
        LoginServiceImpl.EMAIL_CACHE.invalidate(email);


        // 登录操作记录
        loginRecord(user);

        // 授权
        return authorization(user);
    }


    @Override
    public LoginTokenVo phoneLogin(LoginPhoneParam param) {
        String phone = param.getPhone();
        String code = param.getCode();

        // 查询用户
        User user = userMapper.selectUserByPhone(phone);

        // 校验(认证)
        authentication(user);

        // 检查code是否正确
        String realCode = LoginServiceImpl.PHONE_CACHE.getIfPresent(phone);
        if (!Objects.equals(realCode, code)) {
            throw new AuthenticationFailedException("邮箱验证码错误！");
        }
        LoginServiceImpl.EMAIL_CACHE.invalidate(phone);

        // 登录操作记录
        loginRecord(user);

        // 授权
        return authorization(user);
    }

    @Override
    public void sendPhoneCode(String phone) {
        // 检查用户是否存在
        User user = userMapper.selectUserByPhone(phone);
        authentication(user);

        // 生成验证码
        String code = getCode();

        // 保存验证码
        PHONE_CACHE.put(phone, code);

        // 发送验证码 todo 需要发送手机短信服务

    }

    @Override
    public void sendEmailCode(String email) {
        // 检查用户是否存在
        User user = userMapper.selectUserByEmail(email);
        authentication(user);

        // 生成验证码
        String code = getCode();

        // 保存验证码
        EMAIL_CACHE.put(email, code);

        // 发送验证码
        Map<String, String> objectModel = new HashMap<>();
        objectModel.put("name", user.getNickname());
        objectModel.put("code", code);
        objectModel.put("codeExpire", String.format("%d秒", EXPIRE_TIME));

        byte[] bytes = freemarkerManage.rendering("emailCode.ftl", objectModel);
        if (!emailManage.sendCode(email, new String(bytes))) {
            EMAIL_CACHE.invalidate(email);
            throw new RuntimeException("邮件发送失败!");
        }
    }

    private String getCode() {
        return UUID.randomUUID().toString().substring(0, 5);
    }

    /**
     * 授权
     * @param user 用户信息对象
     * @return 令牌
     */
    private LoginTokenVo authorization(User user) {
        // 查询用户角色
        Long userId = user.getUserId();
        List<Role> roles = userMapper.selectRoleByUserId(userId);
        String uuid = user.getCertificate();
        List<String> roleNames = roles.stream().map(Role::getRoleKey).collect(Collectors.toList());

        // 处理token中存放user,避免敏感信息
        User tokenUser = User.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .gender(user.getGender())
                .status(user.getStatus())
                .build();

        // 颁发凭证
        String accessToken = TokenUtil.generateToken("access_token",
                LocalDateTime.now().plusDays(1),
                tokenUser,
                uuid,
                userId,
                true,
                roleNames.contains("system") || roleNames.contains("admin"),
                roleNames);

        return LoginTokenVo.builder()
                .accessToken(accessToken)
                .refreshToken("")
                .build();
    }

    /**
     * 认证（校验是否合法）
     * @param user 用户信息对象
     */
    private void authentication(User user) {
        if (Objects.isNull(user)) {
            throw new AuthenticationFailedException("用户不存在！");
        }

        // ...其它操作
    }

    /**
     * 用户登录记录
     * @param user 用户记录
     */
    private void loginRecord(User user) {
        String uuid = UUID.randomUUID().toString().replace("-", "");

        // 每次登录后更新凭证
        user.setCertificate(uuid);
        user.setLastLoginIp(WebUtil.getIp());
        user.setLastLoginTime(LocalDateTime.now());
        userMapper.updateUserInfo(user);

        // todo 记录用户登录操作日志


    }
}

```

3. 通过请求头实现携带用户凭证token(需要前端将登录成功后的token，设置在请求头中)；
4. 配置yaml，修改默认参数：
```yaml
# 认证组件配置
security:
  admin:
    # 发行人
    issuer: zsl0
    # 请求头
    request-head: Authorization-zsl0
    # 忽略路径
    ignore-path: /api/login/*
    # 用户凭证key
    certificate-key: UUID
    # 权限存储key
    permissions-key: PERMISSIONS
    # 密钥
    secret: secret:abc:zsl0
```

扩展：
1.支持认证后扩展操作，自实现;这里提供一个样例，场景是每次登录后会颁发一个唯一凭证，旧的凭证再登录就会失效，通过扩展方法来实现：
```java
package com.zsl0.project.webservice.core.auth;

import com.zsl0.component.auth.core.function.ExtendFunction;
import com.zsl0.component.auth.core.model.Authentication;
import com.zsl0.component.auth.core.util.SecurityContextHolder;
import com.zsl0.component.common.core.exception.auth.authentication.AuthorizationFailedException;
import com.zsl0.project.repo.mapper.auth.UserMapper;
import com.zsl0.project.repo.pojo.auth.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 检查token凭证是否为最新
 * @author zsl0
 * created on 2023/3/9 15:51
 */
@Component
public class MyExtendFunction extends ExtendFunction {

    static Logger log = LoggerFactory.getLogger(MyExtendFunction.class);

    @Autowired
    UserMapper userMapper;

    @Override
    public void run() {
        // 获取token凭证
        Authentication auth = SecurityContextHolder.getAuth();
        Long userId = auth.getUserId();
        String uuid = auth.getUuid();

        // 检查凭证是否合法
        User user = userMapper.selectUserById(userId);
        String certificate = user.getCertificate();
        if (!Objects.equals(uuid, certificate)) {
            throw new AuthorizationFailedException("身份已失效，请重新登录！");
        }
    }
}

```
## common（公共依赖）
