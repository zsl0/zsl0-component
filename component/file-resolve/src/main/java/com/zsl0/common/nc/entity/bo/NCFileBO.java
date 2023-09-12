package com.zsl0.common.nc.entity.bo;

import com.zsl0.common.nc.NcFile;
import com.zsl0.common.nc.util.MeteoUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author zsl0
 * created on 2023/6/21 14:29
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NCFileBO {

    /**
     * 路径
     */
    private String dir;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 观测时间
     */
    private LocalDateTime observeTime;

    /**
     * 预测时间
     */
    private LocalDateTime predictionTime;

    /**
     * 气象变量要素
     */
    private String element;

    /**
     * 检查nc文件是否存在
     *
     * @return true存在，反之不存在
     */
    public boolean fileExists() {
        if (Objects.isNull(dir) || Objects.isNull(filename)) {
            log.warn("NCFile目录或者文件名为空，dir={}, filename={}", dir, filename);
            return false;
        }

        return Files.exists(Paths.get(getPath()));
    }

    /**
     * 获取nc文件路径
     *
     * @return nc文件路径
     */
    public String getPath() {
        return dir + File.separator + filename;
    }

    /**
     * 获取nc文件插值气象数据
     *
     * @param ele 要素
     * @param lon 经度
     * @param lat 纬度
     * @return 插值结果，若参数不合法或文件异常返回null
     */
    public Double interpolation(String ele, BigDecimal lon, BigDecimal lat) {
        Double result = null;
        try (NcFile ncFile = MeteoUtil.readNc(getPath())) {
            result = ncFile.interpolation(ele, lon.doubleValue(), lat.doubleValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取nc文件插值气象数据
     *
     * @param ncFile ncFile对象
     * @param ele 要素
     * @param lon 经度
     * @param lat 纬度
     * @return 插值结果，若参数不合法或文件异常返回null
     */
    public Double interpolation(NcFile ncFile, String ele, BigDecimal lon, BigDecimal lat) {
        return ncFile.interpolation(ele, lon.doubleValue(), lat.doubleValue());
    }

    /**
     * 获取nc文件插值气象数据
     *
     * @param ncFile ncFile对象
     * @param lon 经度
     * @param lat 纬度
     * @return 插值结果，若参数不合法或文件异常返回null
     */
    public Double interpolation(NcFile ncFile, BigDecimal lon, BigDecimal lat) {
        return ncFile.interpolation(element, lon.doubleValue(), lat.doubleValue());
    }




}
