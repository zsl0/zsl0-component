package com.zsl.custombox.log.core.model.logrecord;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 操作日志上下文
 *
 * @Author zsl
 * @Date 2022/5/25 21:13
 * @Email 249269610@qq.com
 */
public class LogRecordContext {
    private static final InheritableThreadLocal<Stack<Map<String, Object>>> variableMapStack = new InheritableThreadLocal<>();

    //  ==================================== 栈操作 ====================================

    /**
     * 压栈
     */
    private static void push(Map<String, Object> map) {
        Stack<Map<String, Object>> maps = variableMapStack.get();
        if (Objects.isNull(maps)) {
            maps = new Stack<>();
            variableMapStack.set(maps);
        }
        maps.push(map);
    }

    /**
     * 出栈
     */
    private static Map<String, Object> pop() {
        return variableMapStack.get().pop();
    }

    private static Map<String, Object> peek() {
        return variableMapStack.get().peek();
    }


    //  ==================================== Map操作 ====================================

    /**
     * 获取map存储值
     */
    private static Object get(String key) {
        return variableMapStack.get().peek().get(key);
    }

    /**
     * map存储K-V
     */
    private static Object put(String key, Object value) {
        variableMapStack.get().peek().put(key, value);
        return value;
    }

    private static void clearStack() {
        variableMapStack.remove();
    }

    private static boolean isEmpty() {
        return variableMapStack.get().isEmpty();
    }

    //  ==================================== 向外暴露Context操作 ====================================

    /**
     * 获取上下文数据
     */
    public static Map<String, Object> getVariables() {
        return peek();
    }

    /**
     * 存储变量到操作日志上下文
     */
    public static void setVariable(String key, Object value) {
        peek().put(key, value);
    }

    /**
     * 创建当前方法上下文
     */
    public static void putEmptySpan() {
        push(new ConcurrentHashMap<>());
    }

    /**
     * 清除上下文
     */
    public static void clear() {
        pop();
        // 若是最后一个，则清理栈
        if (isEmpty()) {
            clearStack();
        }
    }


}
