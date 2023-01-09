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
  application-name: 登陆模块
  # 应用程序版本
  application-version: v0.0.1
  # 应用程序描述
  application-description: 功能：登录案例，整合zsl0组件
  # controller扫描包
  controller-base-package: com.zsl0.auth.controller
  # 接口调试地址
#  try-host: 
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

import com.zsl0.component.auth.core.model.AuthInfo;
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
                .map(AuthInfo::getUuid)
                .orElse("0");
    }
}
```


## auth-spring-boot-starter（鉴权）
1. 重写接口`PermissionProvide`，实现自定义权限认证方式，默认放行;
- ACL
- RBAC


2. 实现登陆逻辑：
```java

```

3. 通过请求头实现携带用户凭证token；
4. 配置yaml，修改默认参数：
```yaml
# 认证组件配置
security:
  admin:
    # 发行人
    issuer: zsl0
    # 请求头
    request-head: Authentication
    # 忽略路径
    ignore-path: /api/login/*
    # 用户凭证key
    certificate-key: UUID
    # 权限存储key
    permissions-key: PERMISSIONS
    # 密钥
    secret: secret:abc:zsl0
```
## common（公共依赖）
