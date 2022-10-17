package com.zsl.custombox.common.util;


import com.zsl.custombox.common.model.log.SystemLogContext;

/**
 * 线程安全日志记录
 *
 * @author zsl0
 * create on 2022/5/22 16:57
 * email 249269610@qq.com
 */
public class SystemLogContextHolder {
    private static final ThreadLocal<SystemLogContext> SYSTEM_LOG_CONTEXT = new ThreadLocal<>();

    public static SystemLogContext get() {
        return SYSTEM_LOG_CONTEXT.get();
    }

    public static void set(SystemLogContext systemLogContext) {
        SYSTEM_LOG_CONTEXT.set(systemLogContext);
    }

    public static void clear() {
        SYSTEM_LOG_CONTEXT.remove();
    }
}
