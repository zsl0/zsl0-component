package com.zsl0.auth.core.annotation;

import java.lang.annotation.*;

/**
 * 权限注解（todo 后续可新增Spring-EL解析value）
 *
 * @author zsl0
 * create on 2022/6/1 21:50
 * email 249269610@qq.com
 */
@Target({ElementType.METHOD,})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permissions {

    String value() default "";
}
