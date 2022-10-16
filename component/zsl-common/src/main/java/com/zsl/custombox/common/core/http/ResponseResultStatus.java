package com.zsl.custombox.common.core.http;

/**
 * 自定义返回状态码
 *
 * @Author zsl
 * @Date 2022/5/15 18:58
 * @Email 249269610@qq.com
 */
public enum ResponseResultStatus {
    SUCCESS(200, "操作成功!"),
    BAD_REQUEST(400, "请求失败!"),
    NOT_LOGIN(401, "登录失败!"),
    UNAUTHORIZED(401, "认证失败!"),
    FORBIDDEN(403, "没有权限，禁止访问!"),

    // 适应com.cqkj.rs.pojo.ResponserCode
    SUCCESS_CQKJ(301, "成功"),

    ;
    private Integer code;
    private String msg;

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
