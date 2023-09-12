package com.zsl0.common.model.log;

import java.util.Date;

/**
 * 日志记录上下文
 *
 * @author zsl0
 * create on 2022/5/22 16:48
 * email 249269610@qq.com
 */
public class SystemLogContext {
    // =========================== request data ===========================
    // 用户id(匿名为0)
    private Long userId;
    // 请求号码，保证请求调用时请求号不变
    private Long requestNo;
    // host
    private String host;
    // 统一资源接口uri
    private String uri;
    // 参数
//    private String params;
    // 请求方法(GET、POST...)
    private String method;
    // 请求时间
    private Date startTime;

    // =========================== response data ===========================
    // 响应时间
    private Long respTime;
    // 响应码
    private Integer respCode;
    // 响应信息
    private String respMsg;
    // 响应体
//    private String respBody;


    public SystemLogContext() {
    }

    public SystemLogContext(Long userId, Long requestNo, String host, String uri, String method, Date startTime, Long respTime, Integer respCode, String respMsg) {
        this.userId = userId;
        this.requestNo = requestNo;
        this.host = host;
        this.uri = uri;
        this.method = method;
        this.startTime = startTime;
        this.respTime = respTime;
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    public Long getUserId() {
        return userId;
    }

    public SystemLogContext setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getRequestNo() {
        return requestNo;
    }

    public SystemLogContext setRequestNo(Long requestNo) {
        this.requestNo = requestNo;
        return this;
    }

    public String getHost() {
        return host;
    }

    public SystemLogContext setHost(String host) {
        this.host = host;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public SystemLogContext setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public SystemLogContext setMethod(String method) {
        this.method = method;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public SystemLogContext setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Long getRespTime() {
        return respTime;
    }

    public SystemLogContext setRespTime(Long respTime) {
        this.respTime = respTime;
        return this;
    }

    public Integer getRespCode() {
        return respCode;
    }

    public SystemLogContext setRespCode(Integer respCode) {
        this.respCode = respCode;
        return this;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public SystemLogContext setRespMsg(String respMsg) {
        this.respMsg = respMsg;
        return this;
    }
}
