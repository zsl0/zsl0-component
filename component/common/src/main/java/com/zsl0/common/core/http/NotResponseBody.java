package com.zsl0.common.core.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 拒绝经过全局统一处理注解
 *
 * @author zsl0
 * create on 2022/5/15 19:17
 * email 249269610@qq.com
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotResponseBody {
}
