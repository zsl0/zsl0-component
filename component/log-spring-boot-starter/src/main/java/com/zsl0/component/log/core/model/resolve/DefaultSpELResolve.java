package com.zsl0.component.log.core.model.resolve;

import java.util.Objects;

/**
 * @author zsl0
 * created on 2022/12/18 12:05
 */
public class DefaultSpELResolve implements SpELResolve {

    @Override
    public String resolve(String content) {
        return resolve0(content);
    }

    /**
     * 解析
     * @param content 注解待解析内容
     * @return 解析结果spEL
     */
    private String resolve0(String content) {
        StringBuilder sb = new StringBuilder();
        char[] chars = content.toCharArray();
        boolean param = false;

        // 开头添加 '
        append(sb, "'");
        for (int i = 0; i < chars.length; i++) {
            String info = String.valueOf(chars[i]);

            // 判断是否是参数
            if (Objects.equals(chars[i], '#')) {
                if (i + 1 < chars.length && Objects.equals(chars[i + 1], '{')) {
                    if (param) {
                        throw new RuntimeException(String.format("解析失败，请确认信息是否符合解析规则，参数使用 #{example.name} !! spEL=%s", content));
                    }
                    param = true;
                    // 添加 '+#
                    info = "'+#";
                }
            }

            // 判断{是否时参数开头
            if (Objects.equals(chars[i], '{') && param) {
                info = "";
            }

            // 判断是否是参数结尾
            if (Objects.equals(chars[i], '}') && param) {
                param = false;
                // 添加 +'
                info = "+'";

                // todo #{xxx}参数出现在最后时,会出现末尾#{xxx}+''现象
            }

            append(sb, info);
        }

        // 结束添加 '
        append(sb, "'");

        if (param) {
            throw new RuntimeException(String.format("解析失败，请确认信息是否符合解析规则，参数使用 #{example.name} !! spEL=%s", content));
        }

        return sb.toString();
    }

    /**
     * 追加
     * @param sb
     * @param info
     */
    private void append(StringBuilder sb, String info) {
        sb.append(info);
    }

}
