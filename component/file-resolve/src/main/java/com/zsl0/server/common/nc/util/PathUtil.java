package com.zsl0.server.common.nc.util;

import com.zsl0.util.spel.SampleSpelParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zsl0
 * created on 2023/8/30 10:38
 */
public class PathUtil {


    /**
     * 获取NC路径
     *
     * @param format        路径格式
     * @param reportingTime 起报时间
     * @return 路径字符串
     */
    public static String getDir(String format, LocalDateTime reportingTime) {
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
    public static String getFilename(String format, LocalDateTime reportingTime, LocalDateTime predictionTime, Integer fh) {
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
        String yyyy, yyyyMM, yyyyMMdd, yyyyMMddHH, yyyyMMddHHmm, yyyyMMddHHmmss, yyyyMMddHHmmssSSS, yyyyMMdd_HH, yyyyMMdd_HHmm, yyyyMMdd_HHmmss, yyyyMMdd_HHmmssSSS,
                MM, dd, HH, mm, ss, SSS;

        // 年开头
        yyyy = time.format(DateTimeFormatter.ofPattern("yyyy"));
        HH = time.format(DateTimeFormatter.ofPattern("HH"));
        mm = time.format(DateTimeFormatter.ofPattern("mm"));
        ss = time.format(DateTimeFormatter.ofPattern("ss"));
        SSS = time.format(DateTimeFormatter.ofPattern("SSS"));
        yyyyMM = time.format(DateTimeFormatter.ofPattern("yyyyMM"));
        yyyyMMdd = time.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        yyyyMMddHH = time.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
        yyyyMMddHHmm = time.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        yyyyMMddHHmmss = time.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        yyyyMMddHHmmssSSS = time.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        yyyyMMdd_HH = time.format(DateTimeFormatter.ofPattern("yyyyMMdd_HH"));
        yyyyMMdd_HHmm = time.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm"));
        yyyyMMdd_HHmmss = time.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        yyyyMMdd_HHmmssSSS = time.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS"));

        // 月开头
        MM = time.format(DateTimeFormatter.ofPattern("MM"));

        // 天开头
        dd = time.format(DateTimeFormatter.ofPattern("dd"));


        context.put(prev + "yyyy", yyyy);
        context.put(prev + "HH", HH);
        context.put(prev + "mm", mm);
        context.put(prev + "ss", ss);
        context.put(prev + "SSS", SSS);
        context.put(prev + "yyyyMM", yyyyMM);
        context.put(prev + "yyyyMMdd", yyyyMMdd);
        context.put(prev + "yyyyMMddHH", yyyyMMddHH);
        context.put(prev + "yyyyMMddHHmm", yyyyMMddHHmm);
        context.put(prev + "yyyyMMddHHmmss", yyyyMMddHHmmss);
        context.put(prev + "yyyyMMddHHmmssSSS", yyyyMMddHHmmssSSS);
        context.put(prev + "yyyyMMdd_HH", yyyyMMdd_HH);
        context.put(prev + "yyyyMMdd_HHmm", yyyyMMdd_HHmm);
        context.put(prev + "yyyyMMdd_HHmmss", yyyyMMdd_HHmmss);
        context.put(prev + "yyyyMMdd_HHmmssSSS", yyyyMMdd_HHmmssSSS);
        context.put(prev + "MM", MM);
        context.put(prev + "dd", dd);
        return context;
    }




}
