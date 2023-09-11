package com.zsl0.server.common.nc.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author zsl0
 * created on 2023/7/17 17:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataBO {

    /**
     * 电站id
     */
    private String id;

    /**
     * 观测时间
     */
    private LocalDateTime observerTime;

    /**
     * 预测时间
     */
    private LocalDateTime predictionTime;

    /**
     * 值
     */
    private BigDecimal value;

}
