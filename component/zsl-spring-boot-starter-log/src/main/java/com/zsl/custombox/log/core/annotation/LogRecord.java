package com.zsl.custombox.log.core.annotation;

import java.lang.annotation.*;

/**
 * @Author zsl
 * @Date 2022/5/23 23:21
 * @Email 249269610@qq.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogRecord {
    /**
     * 操作日志默认模板内容
     */
    String content();

    /**
     * 失败时输出内容
     */
    String failed() default "";

    /**
     * 操作人(当前用户 userNo)
     */
    String operator() default "";

    /**
     * 操作日志绑定的业务对象标识
     */
    String bizNo();

    /**
     * 操作日志种类
     */
    String category() default "";

    /**
     * 扩展参数，记录操作日志的修改详情
     */
    String detail() default "";

    /**
     * 记录日志的条件
     */
    String condition() default "";
}
