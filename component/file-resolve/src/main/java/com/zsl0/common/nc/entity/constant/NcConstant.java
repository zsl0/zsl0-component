package com.zsl0.common.nc.entity.constant;

/**
 * @author yanglb
 * @version 1.0
 * @date 16/5/2023 下午 7:01
 * @description Nc相关常数
 */
public interface NcConstant {

    /**
     * nc文件中的纬度要素
     */
    String LAT = "lat";

    /**
     * nc文件中的经度要素
     */
    String LON = "lon";

    /**
     * nc文件中的高度层要素
     */
    String LEVEL = "level";

    /**
     * 经纬度保留的小数位数
     */
    int DIMENSION_DECIMAL_PLACES = 4;

    /**
     * 要素保留的小数位数
     */
    int ELEMENT_DECIMAL_PLACES = 2;

    /**
     * 单要素文件中要素和时间的分隔符
     */
    String SINGLE_ELEMENT_NC_SPLIT = "-";

    /**
     * 等值线处理时保留经纬度位数
     */
    int ISOLINE_DECIMAL_PLACES = 4;

    /**
     * nc文件缩放的要素名称
     */
    String SCALE_FACTOR_ELEMENT = "scale_factor";

    /**
     * nc文件缩放默认比例
     */
    int DEFAULT_SCALE_VALUE = 1;

    /**
     * nc文件偏移的要素名称
     */
    String ADD_OFFSET_ELEMENT = "add_offset";

    /**
     * nc文件偏移默认值
     */
    int DEFAULT_ADD_OFFSET = 0;

    /**
     * nc文件缺省值的要素名称
     */
    String MISS_VALUE_ELEMENT = "_FillValue";

    /**
     * nc文件缺省值的默认值
     */
    int DEFAULT_MISS_VALUE = 99_9999;

    /**
     * nc读取错误值标识
     */
    int ERROR_VALUE = 99_9999;

    /**
     * 地面的高度层默认值
     */
    String SURFACE_DEFAULT_HIGH_LEVEL = "1000";

    /**
     * 地面的高度层默认值2
     * 注：用于高空数据包含1000高度层的情况
     */
    String SURFACE_DEFAULT_HIGH_LEVEL_2 = "-1000";

    /**
     * 经纬度保留指定小数位（4位）的字符format
     */
    String DIMENSION_DECIMAL_PLACES_STRING_FORMAT = "%.4f";

    /**
     * 经纬度保留指定小数位（2位）的字符format
     */
    String ELEMENT_DECIMAL_PLACES_STRING_FORMAT = "%.2f";

    /**
     * Nc文件读取默认抽稀值
     */
    Integer DEFAULT_CELL = 1;
}
