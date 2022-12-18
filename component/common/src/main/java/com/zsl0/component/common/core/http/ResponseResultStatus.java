package com.zsl0.component.common.core.http;

/**
 * 自定义返回状态码
 *
 * @author zsl0
 * create on 2022/5/15 18:58
 * email 249269610@qq.com
 */
public enum ResponseResultStatus {
    SUCCESS(200, "操作成功!"),
    BAD_REQUEST(400, "操作失败!"),
    LOGIN_FAILED(401, "登录失败，请确认信息是否正确!"),
    NOT_LOGIN(401, "登录信息无效，请先进行登陆!"),
    UNAUTHORIZED(403, "未授权，禁止访问!"),
    AUTHORIZED_FAILED(401, "授权失败，请确认信息是否正确!"),
    FORBIDDEN(403, "没有权限，禁止访问!"),

    // 适应com.cqkj.rs.pojo.ResponserCode
    SUCCESS_CQKJ(301, "成功"),

    ;
    private final Integer code;
    private final String msg;

    ResponseResultStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }
}
