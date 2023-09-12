package com.zsl0.log.core.interceptor;

import com.zsl0.log.core.model.log.SystemLogContext;
import com.zsl0.log.core.service.record.ILogRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 访问记录(系统日志)
 *  实现@LogRecord注解实现操作日志
 *
 * @author zsl0
 * create on 2022/5/22 14:55
 * email 249269610@qq.com
 */
public class AccessLogInterceptor implements HandlerInterceptor, ApplicationContextAware {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 全局日志上下文
     */
    private static final ThreadLocal<SystemLogContext> SYSTEM_LOG_CONTEXT = new ThreadLocal<>();

    ILogRecordService logRecordService;

    ApplicationContext applicationContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (Objects.isNull(logRecordService)) logRecordService = applicationContext.getBean(ILogRecordService.class);

        // 创建全局日志记录上下文 todo 获取数据(实现工具类)
        SystemLogContext systemLogContext = new SystemLogContext()
                .setUserId(logRecordService.getUserId())
                .setRequestNo(0L)// 可以使用雪花算法获取64位唯一id
                .setHost(request.getHeader("host"))
                .setUri(request.getRequestURI())
                .setMethod(request.getMethod())
                .setStartTime(new Date(System.currentTimeMillis()));
        // 存储全局日志记录上下文
        set(systemLogContext);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SystemLogContext systemLogContext = get();
        systemLogContext.setRespTime(System.currentTimeMillis() - systemLogContext.getStartTime().getTime());

        // format log
        StringBuilder requestStr = new StringBuilder();
        List<Object> requestArgs = new ArrayList<>();
        printLine(requestStr, requestArgs);

        if (Objects.isNull(logRecordService)) logRecordService = applicationContext.getBean(ILogRecordService.class);
        logRecordService.record(String.format(requestStr.toString(), requestArgs.toArray()));

        // 清理ThreadLocal
        clear();
    }

    /**
     * 多行输出
     */
    private void printLines(StringBuilder requestStr, List<Object> requestArgs) {
        SystemLogContext systemLogContext = get();

        requestStr.append("\n=========================== AccessLog ===========================\n");
        requestStr.append(String.format("       %-10s: {}\n", "userId"));
        requestArgs.add(systemLogContext.getUserId());
        requestStr.append(String.format("       %-10s: {}\n", "requestNo"));
        requestArgs.add(systemLogContext.getRequestNo());
        requestStr.append(String.format("       %-10s: {}\n", "ip"));
        requestArgs.add(systemLogContext.getHost());
        requestStr.append(String.format("       %-10s: {}\n", "uri"));
        requestArgs.add(systemLogContext.getUri());
        requestStr.append(String.format("       %-10s: {}\n", "method"));
        requestArgs.add(systemLogContext.getMethod());
        requestStr.append(String.format("       %-10s: {}\n", "startTime"));
        requestArgs.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(systemLogContext.getStartTime()));
        requestStr.append(String.format("       %-10s: {} ms\n", "respTime"));
        requestArgs.add(systemLogContext.getRespTime());
        requestStr.append(String.format("       %-10s: {}\n", "respCode"));
        requestArgs.add(systemLogContext.getRespCode());
        requestStr.append(String.format("       %-10s: {}\n", "respMsg"));
        requestArgs.add(systemLogContext.getRespMsg());
        requestStr.append("=================================================================\n");
    }

    /**
     * 单行输出
     */
    private void printLine(StringBuilder requestStr, List<Object> requestArgs) {
        SystemLogContext systemLogContext = get();

        requestStr.append("[AccessLog] ");
        requestStr.append("userId: %s, ");
        requestArgs.add(systemLogContext.getUserId());
        requestStr.append("requestNo: %s, ");
        requestArgs.add(systemLogContext.getRequestNo());
        requestStr.append("host: %s, ");
        requestArgs.add(systemLogContext.getHost());
        requestStr.append("uri: %s, ");
        requestArgs.add(systemLogContext.getUri());
        requestStr.append("method: %s, ");
        requestArgs.add(systemLogContext.getMethod());
        requestStr.append("startTime: %s, ");
        requestArgs.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(systemLogContext.getStartTime()));
        requestStr.append("respTime: %s ms, ");
        requestArgs.add(systemLogContext.getRespTime());
        requestStr.append("respCode: %s, ");
        requestArgs.add(systemLogContext.getRespCode());
        requestStr.append("respMsg: %s");
        requestArgs.add(systemLogContext.getRespMsg());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

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
