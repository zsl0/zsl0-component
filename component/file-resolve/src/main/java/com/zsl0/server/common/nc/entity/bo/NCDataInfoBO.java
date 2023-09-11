package com.zsl0.server.common.nc.entity.bo;

import com.zsl0.component.common.core.exception.ApiException;
import com.zsl0.util.cron.CronUtil;
import com.zsl0.util.spel.SampleSpelParser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NCDataInfoBO {

    private Integer id;
    private String element;
    private String description;
    private String dirFormat;
    private String filenameFormat;
    private Integer batchCount;
    private String batchReportingCron;
    private String batchPredictionInterval;
    private Integer batchContainFirst;
    private java.time.LocalDateTime createTime;
    private String createBy;
    private java.time.LocalDateTime modifyTime;
    private String modifyBy;

    /**
     * 获取NC路径
     *
     * @param format        路径格式
     * @param reportingTime 起报时间
     * @return 路径字符串
     */
    public static String getNCDir(String format, LocalDateTime reportingTime) {
        Map<String, Object> context = getContext(reportingTime, "");

        return new SampleSpelParser().parser(format, context);
    }


    /**
     * 获取NC路径
     *
     * @param format         路径格式
     * @param predictionTime 起报时间
     * @param fh             间隔
     * @return 路径字符串
     */
    public static String getNCFilename(String format, LocalDateTime reportingTime, LocalDateTime predictionTime, Integer fh) {
        Map<String, Object> context = new HashMap<>();
        context.putAll(getContext(predictionTime, "prediction_time_"));
        context.putAll(getContext(reportingTime, "reporting_time_"));
        context.put("fh", fh);

        // filename中，若使用%05d则格式化
        return String.format(new SampleSpelParser().parser(format, context), fh);
    }


    /**
     * 获取公共时间格式化map集合
     *
     * @param time 时间
     * @param prev key前缀
     * @return map集合
     */
    private static Map<String, Object> getContext(LocalDateTime time, String prev) {
        if (Objects.isNull(prev)) {
            prev = "";
        }

        Map<String, Object> context = new HashMap<>();
        String yyyy, yyyyMM, yyyyMMdd, yyyyMMddHH, yyyyMMddHHmm, yyyyMMdd_HH, yyyyMMdd_HHmm,
                MM, dd;

        // 年开头
        yyyy = time.format(DateTimeFormatter.ofPattern("yyyy"));
        yyyyMM = time.format(DateTimeFormatter.ofPattern("yyyyMM"));
        yyyyMMdd = time.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        yyyyMMddHH = time.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
        yyyyMMddHHmm = time.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        yyyyMMdd_HH = time.format(DateTimeFormatter.ofPattern("yyyyMMdd_HH"));
        yyyyMMdd_HHmm = time.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm"));

        // 月开头
        MM = time.format(DateTimeFormatter.ofPattern("MM"));

        // 天开头
        dd = time.format(DateTimeFormatter.ofPattern("dd"));


        context.put(prev + "yyyy", yyyy);
        context.put(prev + "yyyyMM", yyyyMM);
        context.put(prev + "yyyyMMdd", yyyyMMdd);
        context.put(prev + "yyyyMMddHH", yyyyMMddHH);
        context.put(prev + "yyyyMMddHHmm", yyyyMMddHHmm);
        context.put(prev + "yyyyMMdd_HH", yyyyMMdd_HH);
        context.put(prev + "yyyyMMdd_HHmm", yyyyMMdd_HHmm);
        context.put(prev + "MM", MM);
        context.put(prev + "dd", dd);
        return context;
    }


    /**
     * 获取时间范围内所有批次起报时间的nc文件信息
     * @param startTime 起始时间（起报）
     * @param endTime 结束时间（起报）
     * @return nc文件信息对象列表
     */
    public List<NCFileBO> getNCFiles(LocalDateTime startTime, LocalDateTime endTime) {
        List<NCFileBO> result = new ArrayList<>();

        // 获取起报时间列表
        List<LocalDateTime> reportingList = CronUtil.getTimes(batchReportingCron, startTime, endTime);

        for (LocalDateTime reportingTime : reportingList) {
            result.addAll(getNCFiles(reportingTime));
        }

        return result;
    }


    /**
     * 获取单个批次所有预测
     *
     * @param reportingTime 起报时间
     * @return nc文件信息对象列表
     */
    public List<NCFileBO> getNCFiles(LocalDateTime reportingTime) {
        List<NCFileBO> result = new ArrayList<>();

        // 获取起报时间列表
        List<LocalDateTime> predictions = getPredictions(reportingTime, batchPredictionInterval, Objects.equals(1, batchContainFirst));
        for (LocalDateTime prediction : predictions) {
            // 获取文件路径
            String dir = NCDataInfoBO.getNCDir(dirFormat, reportingTime);
            String filename = NCDataInfoBO.getNCFilename(filenameFormat, reportingTime, prediction, null);

            // 构建对象
            result.add(NCFileBO.builder()
                    .observeTime(reportingTime)
                    .predictionTime(prediction)
                    .filename(filename)
                    .dir(dir)
                    .build());
        }

        return result;
    }


    /**
     * 根据起报时间获取预测时间
     *
     * @param reportingTime 起报时间
     * @param expression    表达式 minute1:count1-minute2:count2....
     * @return 预测时间列表
     */
    private static List<LocalDateTime> getPredictions(LocalDateTime reportingTime, String expression, boolean first) {
        List<LocalDateTime> result = new ArrayList<>();
        LocalDateTime curr = reportingTime;

        // 检验表达式是否合法
        if (Objects.isNull(expression) || Objects.equals("", expression)) {
            throw new ApiException(String.format("解析expression '%s' 出错，请确认表达式格式（minute1:count1-minute2:count2....）是否正确！", expression));
        }

        String[] split = expression.split("-");

        // 获取预测时间
        for (String spot : split) {
            String[] interval = spot.split(":");
            if (interval.length != 2) {
                throw new ApiException(String.format("解析expression '%s' -> '%s' 出错，请确认表达式格式（minute1:count1-minute2:count2....）是否正确！", expression, spot));
            }

            int minute = Integer.parseInt(interval[0]);
            int count = Integer.parseInt(interval[1]);

            // 判断是否包含第一个，包含则向前偏移
            if (first) {
                curr = curr.minusMinutes(minute);
                first = false; // false时，只会执行一次
            }

            for (int i = 0; i < count; i++) {
                curr = curr.plusMinutes(minute);
                result.add(curr);
            }
        }

        return result;
    }

}
