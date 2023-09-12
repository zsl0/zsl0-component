package com.zsl0.common.nc.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author zsl0
 * created on 2023/7/17 17:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StationBO {

    /**
     * 站点id
     */
    private String id;

    /**
     * 经度
     */
    private BigDecimal lon;

    /**
     * 纬度
     */
    private BigDecimal lat;


}
