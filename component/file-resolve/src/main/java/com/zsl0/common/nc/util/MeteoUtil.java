package com.zsl0.common.nc.util;

import com.zsl0.common.nc.NcFile;
import com.zsl0.common.nc.entity.bo.NCFileBO;
import com.zsl0.util.cron.CronUtil;
import ucar.nc2.NetcdfFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author zsl0
 * created on 2023/5/19 17:41
 */
public class MeteoUtil {



    public static void main(String[] args) throws InterruptedException {
////        String path = "/Users/zsl0/Desktop/RADI_72H15M_GRB_5KM_BABJ_202306260000_202306261615.nc";
////        String path = "/Users/zsl0/Desktop/RADI_72H15M_GRB_5KM_BABJ_202306260000_202306261630.nc";
////        String path = "/Users/zsl0/Desktop/RADI_72H15M_GRB_5KM_BABJ_202306260000_202306261645.nc";
////        String path = "/Users/zsl0/Desktop/RADI_72H15M_GRB_5KM_BABJ_202306260000_202306261700.nc";
//        List<String> elements = new ArrayList<>();
//        elements.add("GHI");
//        elements.add("DNI");
//        elements.add("DHI");
//        elements.add("GHR");
//        elements.add("DNR");
//        elements.add("DHR");
//
//
//        List<String> paths = new ArrayList<>();
//        paths.add("/Users/zsl0/Desktop/RADI_72H15M_GRB_5KM_BABJ_202306260000_202306261615.nc");
//        paths.add("/Users/zsl0/Desktop/RADI_72H15M_GRB_5KM_BABJ_202306260000_202306261630.nc");
//        paths.add("/Users/zsl0/Desktop/RADI_72H15M_GRB_5KM_BABJ_202306260000_202306261645.nc");
//        paths.add("/Users/zsl0/Desktop/RADI_72H15M_GRB_5KM_BABJ_202306260000_202306261700.nc");
//
//        long start = System.currentTimeMillis();
//
////        for (String path : paths) {
////            try (NcFile ncFile = readNc(path)) {
////                for (String element : elements) {
////                    Double reduce = ncFile.reduce(element, 121.395190, 31.182010);
////                    System.out.println(reduce);
////                }
////            } catch (IOException | InvalidRangeException e) {
////                e.printStackTrace();
////            }
////        }
////        new ThreadPoolExecutor();
//
//
//        CountDownLatch count = new CountDownLatch(paths.size() * elements.size());
//        for (String path : paths) {
//            for (String element : elements) {
//                NCThreadPoolUtil.execute(() -> {
//                    try (NcFile ncFile = readNc(path)) {
//                        Double reduce = ncFile.reduce(element, 121.395190, 31.182010);
//                        System.out.println(reduce);
//                    } catch (IOException | InvalidRangeException e) {
//                        e.printStackTrace();
//                    } finally {
//                        count.countDown();
//                    }
//                });
//            }
//        }
//        count.await();
//
//        System.out.printf("读取次数 '%d', 消耗 '%d' ms", paths.size() * elements.size(), System.currentTimeMillis() - start);
//
////        LocalDateTime time = LocalDateTime.of(2023, 6, 27, 17, 0);
////        String ncFilename = getNCFilename("TEMP_1H_GRB_2KM_BABJ_#{reporting_time_yyyyMMddHHmm}.nc", time, time, null);
////
////        System.out.println(ncFilename);


    }


    /**
     * 构建NetcdfFile装饰类
     *
     * @param file 文件路径
     * @return NcFile对象
     */
    public static NcFile readNc(String file) {
        // 构建装饰类
        try {
            NetcdfFile open = NetcdfFile.openInMemory(file);
            return NcFile.builder()
                    .netcdfFile(open)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    // ======================== 处理文件 ========================
    /**
     * 根据数据类型、预测类型、要素，获取ncFile对象
     * @param startTime 起始时间（起报）
     * @param endTime 结束时间（起报）
     * @return
     */
    public List<NCFileBO> getNCFiles(String cron, String dirFormat, String filenameFormat, String predictionExpression, Integer batchContainFirst, LocalDateTime startTime, LocalDateTime endTime) {
        List<NCFileBO> result = new ArrayList<>();

        // 获取起报时间列表
        List<LocalDateTime> reportingList = CronUtil.getTimes(cron, startTime, endTime);

        for (LocalDateTime reportingTime : reportingList) {
            result.addAll(getNCFiles(dirFormat, filenameFormat, predictionExpression, batchContainFirst, reportingTime));
        }

        return result;
    }


    /**
     * 获取单个批次所有预测
     * @param dirFormat
     * @param filenameFormat
     * @param predictionExpression
     * @param batchContainFirst
     * @param reportingTime 起报时间
     * @return
     */
    public List<NCFileBO> getNCFiles(String dirFormat, String filenameFormat, String predictionExpression, Integer batchContainFirst, LocalDateTime reportingTime) {
        List<NCFileBO> result = new ArrayList<>();

        boolean first = Objects.equals(1, batchContainFirst);

        // 获取起报时间列表
        List<LocalDateTime> predictions = getPredictions(reportingTime, predictionExpression, first);
        for (LocalDateTime prediction : predictions) {
            // 获取文件路径
            String dir = PathUtil.getDir(dirFormat, reportingTime);
            String filename = PathUtil.getFilename(filenameFormat, reportingTime, prediction, null);

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
     * @param reportingTime 起报时间
     * @param expression 表达式 minute1:count1-minute2:count2....
     * @return 预测时间列表
     */
    public List<LocalDateTime> getPredictions(LocalDateTime reportingTime, String expression, boolean first) {
        List<LocalDateTime> result = new ArrayList<>();
        LocalDateTime curr = reportingTime;

        // 检验表达式是否合法
        if (Objects.isNull(expression) || Objects.equals("", expression)) {
            throw new RuntimeException(String.format("解析expression '%s' 出错，请确认表达式格式（minute1:count1-minute2:count2....）是否正确！", expression));
        }

        String[] split = expression.split("-");

        // 获取预测时间
        for (String spot : split) {
            String[] interval = spot.split(":");
            if (interval.length != 2) {
                throw new RuntimeException(String.format("解析expression '%s' -> '%s' 出错，请确认表达式格式（minute1:count1-minute2:count2....）是否正确！", expression, spot));
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
