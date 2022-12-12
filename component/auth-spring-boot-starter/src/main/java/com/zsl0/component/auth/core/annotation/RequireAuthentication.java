package com.zsl0.component.auth.core.annotation;

import java.lang.annotation.*;

/**
 * 任何人都可以访问（特定接口放行）
 *
 * @author zsl0
 * create on 2022/6/1 22:24
 * email 249269610@qq.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireAuthentication {

}
