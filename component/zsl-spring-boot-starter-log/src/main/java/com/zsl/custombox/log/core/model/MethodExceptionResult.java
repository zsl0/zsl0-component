package com.zsl.custombox.log.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 方法异常结果
 *
 * @Author zsl
 * @Date 2022/5/23 23:56
 * @Email 249269610@qq.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MethodExceptionResult {
    private boolean success;
    private Throwable throwable;
    private String errorMsg;
}
