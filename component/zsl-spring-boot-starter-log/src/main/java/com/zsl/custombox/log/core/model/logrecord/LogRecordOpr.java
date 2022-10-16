package com.zsl.custombox.log.core.model.logrecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * todo 待升级为将LogRecord注解信息解析为当前对象
 *
 * @Author zsl
 * @Date 2022/5/29 11:51
 * @Email 249269610@qq.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogRecordOpr {
    private String spEl;
    private boolean function;
}
