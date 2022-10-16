package com.zsl.custombox.security.auth.core.annotation;

import java.lang.annotation.*;

/**
 * 任何人都可以访问（特定接口放行）
 *
 * @Author zsl
 * @Date 2022/6/1 22:24
 * @Email 249269610@qq.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireAuthentication {
}
