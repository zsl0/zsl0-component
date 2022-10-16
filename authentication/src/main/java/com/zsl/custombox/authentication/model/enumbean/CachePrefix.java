package com.zsl.custombox.authentication.model.enumbean;

/**
 * 缓存前缀
 *
 * @Author zsl
 * @Date 2022/6/9 17:27
 * @Email 249269610@qq.com
 */
public enum CachePrefix {
    TOKEN("pingan:auth:token:")
    ;


    private String prefix;

    CachePrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
