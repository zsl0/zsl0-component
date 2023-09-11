package com.zsl0.server.common.nc;

import com.alibaba.fastjson.JSONObject;
import com.pngencoder.PngEncoder;
import com.zsl0.server.common.core.exception.ApiException;
import com.zsl0.server.common.nc.entity.constant.NcConstant;
import com.zsl0.server.common.nc.util.Base64Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import ucar.ma2.Array;
import ucar.ma2.DataType;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author zsl0
 * created on 2023/5/19 17:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class NcFile implements Closeable {

    private static String LON_VARIABLE = "lon";
    private static String LAT_VARIABLE = "lat";

    /**
     * NetCDF文件对象
     */
    private NetcdfFile netcdfFile;

    /**
     * 是否初始化
     */
    private boolean init;

    /**
     * 变量列表
     */
    private List<String> elements;

    /**
     * 经度顺序（true为递增 false为递减）
     */
    private boolean lonIncr;

    private double[] lonArr;

    /**
     * 纬度顺序（true为递增 false为递减）
     */
    private boolean latIncr;

    private double[] latArr;

    /**
     * 经度精度(步长)
     */
    private double lonStep;

    /**
     * 纬度经度(步长)
     */
    private double latStep;

    /**
     * 头部信息
     */
    private String headInfo;

    /**
     * 缩放比例
     */
    private double scaleFactor = 10;
    /**
     * 偏移量
     */
    private double addOffset = 1000;

    /**
     * 是否逆向
     */
    private boolean latDesc=true;

    /**
     * 是否是异常数据
     */
    private boolean NaN=false;

    /**
     * 处理数据对象
     */
    private Item item = new Item();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Item {
        private double[] data;
        private String element;
    }

    /**
     * 初始化方法，第一次调用需要初始化，构建经纬度，缩放比信息
     */
    private void init() {
        if (init) {
            return;
        }

        Variable lonVariable = netcdfFile.findVariable(LON_VARIABLE);
        Variable latVariable = netcdfFile.findVariable(LAT_VARIABLE);

        // 检验经纬度
        if (Objects.isNull(lonVariable) || Objects.isNull(latVariable)) {
            throw new RuntimeException(String.format("变量lon或lat错误，lon=%s,lat=%s", lonVariable, latVariable));
        }

        // 初始化操作
        try {
            Array lonArr = lonVariable.read();
            lonIncr = checkLonLat(lonArr);
            this.lonArr = toDoubleArr(lonArr);

            Array latArr = latVariable.read();
            latIncr = checkLonLat(latArr);
            this.latArr = toDoubleArr(latArr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("init完成， path={}", netcdfFile.getLocation());

        lonStep = (getELon() - getSLon()) / (getWidth() - 1);
        latStep = (getELat() - getSLat()) / (getHeight() - 1);

        scaleFactor = 10;
        addOffset = 1000;
        latDesc=true;
        NaN=false;
        item = new Item();

        init = true;
    }



    /**
     * 检查经或纬度数据
     * @param array 经或纬度数据
     * @return true为递增，反之递减
     */
    private boolean checkLonLat(Array array) {
        int size = (int) array.getSize();

        // 判断格点经纬度长度
        if (size < 2) {
            throw new RuntimeException(String.format("经纬度长度错误，length=%s", size));
        }

        // true为递增，反之递减
        return array.getDouble(0) < array.getDouble(size - 1);
    }

    /**
     * 数据存入PNG图片
     * @param variableName 要素变量
     */
    public byte[] toImage(String variableName){
        byte[] result = null;
        try{
            BufferedImage bufferedImage = getBufferedImage(variableName);
            result = new PngEncoder().withBufferedImage(bufferedImage).toBytes();
        } catch (Exception e) {
            log.error("输出到图片文件出错!!",e);
        }
        return result;
    }

    /**
     * 数据存入PNG图片
     * @param outFileName 输出文件路径
     */
    public boolean toImage(String variableName, String outFileName){

        File outFile = new File(outFileName);
        File parentFile = outFile.getParentFile();
        if(!parentFile.exists()){
            //创建父级目录
            if(parentFile.mkdirs()){
                log.info("父级目录创建成功:{}",parentFile.getAbsolutePath());
            }
        }
        try{
            BufferedImage bufferedImage = getBufferedImage(variableName);
            //输出到图片文件
            // ImageIO.write(bufferedImage, "png", outFile);
            new PngEncoder().withBufferedImage(bufferedImage).toFile(outFile);
        } catch (Exception e) {
            log.error("输出到图片文件出错!!",e);
            return false;
        }

        return true;
    }

    /**
     * 返回宽（经度）
     * @return 宽（经度个数）
     */
    private int getWidth() {
        return lonArr.length;
    }

    /**
     * 返回高度（纬度）
     * @return 高度（纬度个数）
     */
    private int getHeight() {
        return latArr.length;
    }

    public JSONObject getBaseInfo(){
        final JSONObject baseInfo = new JSONObject();
        baseInfo.put("scaleFactor",getScaleFactor());
        baseInfo.put("eLon",getELon());
        baseInfo.put("sLon",getSLon());
        baseInfo.put("eLat",getELat());
        baseInfo.put("lonStep",getLonStep());
        baseInfo.put("width",getWidth());
        baseInfo.put("sLat",getSLat());
        baseInfo.put("latStep",getLatStep());
        baseInfo.put("addOffset",getAddOffset());
        baseInfo.put("height",getHeight());
        baseInfo.put("latDesc",isLatDesc());
        baseInfo.put("NaN",isNaN());
        return baseInfo;
    }



    /**
     * 获取起始经度
     * @return 起始经度值，-999为无效值
     */
    public double getSLon() {
        return lonArr == null || lonArr.length == 0 ? -999 : lonArr[0];
    }

    /**
     * 获取结束经度
     * @return 结束经度值，-999为无效值
     */
    public double getELon() {
        return lonArr == null || lonArr.length == 0 ? 0.0 : lonArr[lonArr.length - 1];
    }


    /**
     * 获取起始纬度
     * @return 起始纬度值，-999为无效值
     */
    public double getSLat() {
        return latArr == null || latArr.length == 0 ? -999 : latArr[0];
    }

    /**
     * 获取结束纬度
     * @return 结束纬度值，-999为无效值
     */
    public double getELat() {
        return latArr == null || latArr.length == 0 ? 0.0 : latArr[latArr.length - 1];
    }

    /**
     * 设置头部信息
     * @param baseObject
     */
    public void setHeadInfo(JSONObject baseObject){
        headInfo=baseObject.toJSONString();
    }

    public NcFile resetOffsetAndScale(){
        double min=99999;
        double max=-99999;
        if(item!=null&&item.data!=null){
            for (int i = 0; i < item.data.length; i++) {
                double d = item.data[i];
                if(Double.isNaN(d)){//异常值跳过
                    continue;
                }
                if(d>90000||d<-9000){
                    d=0;
                    continue;
                }
                if(d<min){
                    min=d;
                }
                if(d>max){
                    max=d;
                }
            }
        }
        //根据min max 设置 偏移量 以及缩放比
        setAddOffset(-min);
        //todo 1.20版本 把 255 调整为254 255用于存放异常值
        setScaleFactor(254.0/(max-min));
        return this;
    }


    /**
     * 转换成BufferedImage对象
     * @return
     */
    private BufferedImage getBufferedImage(String variableName){
        init();
        //图片宽度
        int width = getWidth();
        //图片高度
        int height = getHeight();
        //头部信息
        int[] header = null;
        StopWatch stopWatch = new StopWatch();
        // 获取网格数据
        try {
            stopWatch.start("解析nc文件获取二维数组");
            Double[][] srcArr = get2dScaleOffsetDoubleData(netcdfFile, variableName, getSectionSpec(variableName));
            stopWatch.stop();
            log.info("{}耗时{}ms", stopWatch.getLastTaskName(), stopWatch.getLastTaskTimeMillis());
            stopWatch.start("二维数组转一维数组");
            double[] data = new double[srcArr.length * srcArr[0].length];
            for (int i = 0; i < srcArr.length; i++) {
                for (int j = 0; j < srcArr[i].length; j++) {
                    data[srcArr[i].length * i + j] = Optional.ofNullable(srcArr[i][j]).orElse(0.0);
                }
            }
            stopWatch.stop();
            log.info("{}耗时{}ms", stopWatch.getLastTaskName(), stopWatch.getLastTaskTimeMillis());
            item.setData(data);
        } catch (InvalidRangeException | IOException e) {
            throw new RuntimeException(e);
        }

        stopWatch.start("数组数据转png");
        if(getAddOffset()==1000&&getScaleFactor()==10){//重置头部信息
            resetOffsetAndScale();
            setHeadInfo(getBaseInfo());
        }
        try {
            //base64编码
            String s = Base64.getEncoder().encodeToString(URLEncoder.encode(getBaseInfo().toString()).getBytes("UTF-8"));
            header=new int[s.length()];
            for(int i=0;i<s.length();i++){
                header[i]= Base64Util.getValueByChar(s.charAt(i));
            }
        }catch (Exception e){
            log.error("头部信息编码出错",e);
        }
        //计算头部需要占用的像素个数
        assert header != null;
        int hP=1+header.length/3+(header.length%3==0?0:1);
        //计算头部的行数 第一个像素的 存行数
        int hL=hP/width+(hP%width==0?0:1);
        //创建图片对象
        BufferedImage bi = new BufferedImage(width, height+hL, BufferedImage.TYPE_INT_ARGB);
        //写头部信息
        if(hL>0){
            int length = header.length;
            //写头部的行数信息
            bi.setRGB(0,0,new Color(hL,length%255 , length/255, 255).getRGB());
            //写头部信息
            for (int i=0;i<(length/3+(length%3==0?0:1));i++){
                int r=header[3*i];
                int g=(3*i+1)<length?header[3*i+1]:255;
                int b=(3*i+2)<length?header[3*i+2]:255;
                bi.setRGB((i+1)%width,(i+1)/width,new Color(r,g,b, 255).getRGB());
            }
        }
        BufferedImage bufferedImage = drawData(variableName, bi, hL);
        stopWatch.stop();
        log.info("{}耗时{}ms", stopWatch.getLastTaskName(), stopWatch.getLastTaskTimeMillis());
        //写格点数据
        return bufferedImage;
    }


    /**
     * 写入数据值
     * @param bi 数据实体BufferedImage
     * @param hL 头部高度
     */
    public BufferedImage drawData(String variableName, BufferedImage bi,int hL){
        //图片宽度
        int width = getWidth();
        //图片高度
        int height = getHeight();

        double[] data = getItem().getData();
        if(data!=null){
            for(int i=0;i<height;i++){
                for(int j=0;j<width;j++){
                    //默认 把值+1000放大10倍  相当于前端解析的时候把值/10 -1000 等价于保留1位小数
                    double v = data[i * width + j];
                    int s = (int)((v+getAddOffset())*getScaleFactor());
                    int r = s % 255;
                    int g = (s / 255) % 255;
                    int b = (s / 255 / 255) % 255;
                    int a=255;
                    if(Double.isNaN(v)||v==9999||v==-9999||v==999999||v==-999999){
                        r=255;
                        g=255;
                        b=255;
                        a=0;
                    }
                    Color color = new Color(r, g, b, a);
                    //bi.setRGB(j,i+hL,color.getRGB());
                    bi.setRGB(lonIncr ? j : (width-1-j), latIncr ? (height+hL-1-i) : (i + hL), color.getRGB());
                }
            }
        }
        return bi;
    }


    /**
     * 插值获取最近的左上角数据
     * @param variableName 变量名称
     * @param lon 经度
     * @param lat 纬度
     * @return 插值结果
     */
    public Double reduce(String variableName, double lon, double lat) throws IOException, InvalidRangeException {
        log.info("开始插值 variableName={}, lon={}, lat={}, path={}", variableName, lon, lat, getPath());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("插值：");
        Variable eleVariable = netcdfFile.findVariable(variableName);

        init();

        String sectionSpec = getSectionSpec(variableName, lon, lat);
        double result = eleVariable.read(sectionSpec).reduce().getDouble(0);

        stopWatch.stop();
        log.info("插值结束,消耗 {} ms , result={}, variableName={}, lon={}, lat={}, path={}", stopWatch.getLastTaskTimeMillis(), result, variableName, lon, lat, getPath());
        return result;
    }

    /**
     * 复制全国平台处理逻辑
     * @param variableName 变量名称
     * @param lon 经度
     * @param lat 纬度
     * @return 插值结果
     */
    public Double interpolation(String variableName, double lon, double lat) {
        Double result = null;
        try {
            result = get2dScaleOffsetDoubleData(netcdfFile, variableName, getSectionSpec(variableName, lon, lat))[0][0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取压缩和偏移的2d数据，转换为double[][]
     * 注：由于需要频繁创建BigDecimal对象，该方法效率较低
     * @param netcdfFile        nc文件
     * @param element           要素名称
     * @param sectionSpec       索引，格式为start_index:end_index:cell,...
     * @return  三维数组，double[][]
     * @throws InvalidRangeException    传入的经纬度超界时会抛出该异常
     * @throws IOException              读取NC文件失败时会抛出该异常
     */
    public static Double[][] get2dScaleOffsetDoubleData(NetcdfFile netcdfFile, String element, String sectionSpec) throws InvalidRangeException, IOException {
        // 获取Nc要素
        Variable variable = netcdfFile.findVariable(element);
        if (variable == null){
            throw new ApiException("当前nc文件中无要素" + element);
        }
        // 获取缺省值、缩放和偏移值
        int missValue = netcdfFile.readAttributeInteger(variable, NcConstant.MISS_VALUE_ELEMENT, NcConstant.DEFAULT_MISS_VALUE);
        double scale = netcdfFile.readAttributeDouble(variable, NcConstant.SCALE_FACTOR_ELEMENT, NcConstant.DEFAULT_SCALE_VALUE);
        double offset = netcdfFile.readAttributeDouble(variable, NcConstant.ADD_OFFSET_ELEMENT, NcConstant.DEFAULT_ADD_OFFSET);
        BigDecimal scaleBd = BigDecimal.valueOf(scale);
        BigDecimal offsetBd = BigDecimal.valueOf(offset);
        DataType dataType = getNcElementType(netcdfFile, element);
        Double[][] result;
        switch (dataType){
            case BYTE:{
                byte[][] arrays = (byte[][]) variable.read(sectionSpec).copyToNDJavaArray();
                result = new Double[arrays.length][arrays[0].length];
                for (int i = 0; i < arrays.length; i++) {
                    for (int j = 0; j < arrays[i].length; j++) {
                        if (missValue == arrays[i][j]){
                            result[i][j] = null;
                        } else {
                            BigDecimal bigDecimal = new BigDecimal(arrays[i][j]);
                            result[i][j] = bigDecimal.multiply(scaleBd).add(offsetBd).setScale(NcConstant.ELEMENT_DECIMAL_PLACES, RoundingMode.UP).doubleValue();
                            // help gc
                            bigDecimal = null;
                        }
                    }
                }
                break;
            }

            case SHORT:
            {
                short[][] arrays = (short[][]) variable.read(sectionSpec).copyToNDJavaArray();
                result = new Double[arrays.length][arrays[0].length];
                for (int i = 0; i < arrays.length; i++) {
                    for (int j = 0; j < arrays[i].length; j++) {
                        if (missValue == arrays[i][j]){
                            result[i][j] = null;
                        } else {
                            BigDecimal bigDecimal = new BigDecimal(arrays[i][j]);
                            result[i][j] = bigDecimal.multiply(scaleBd).add(offsetBd).setScale(NcConstant.ELEMENT_DECIMAL_PLACES, RoundingMode.UP).doubleValue();
                            // help gc
                            bigDecimal = null;
                        }
                    }
                }
                break;
            }

            case INT: {
                int[][] arrays = (int[][]) variable.read(sectionSpec).copyToNDJavaArray();
                result = new Double[arrays.length][arrays[0].length];
                for (int i = 0; i < arrays.length; i++) {
                    for (int j = 0; j < arrays[i].length; j++) {
                        if (missValue == arrays[i][j]){
                            result[i][j] = null;
                        } else {
                            BigDecimal bigDecimal = new BigDecimal(arrays[i][j]);
                            result[i][j] = bigDecimal.multiply(scaleBd).add(offsetBd).setScale(NcConstant.ELEMENT_DECIMAL_PLACES, RoundingMode.UP).doubleValue();
                            // help gc
                            bigDecimal = null;
                        }
                    }
                }
                break;
            }

            case LONG: {
                long[][] arrays = (long[][]) variable.read(sectionSpec).copyToNDJavaArray();
                result = new Double[arrays.length][arrays[0].length];
                for (int i = 0; i < arrays.length; i++) {
                    for (int j = 0; j < arrays[i].length; j++) {
                        if (missValue == arrays[i][j]){
                            result[i][j] = null;
                        } else {
                            BigDecimal bigDecimal = new BigDecimal(arrays[i][j]);
                            result[i][j] = bigDecimal.multiply(scaleBd).add(offsetBd).setScale(NcConstant.ELEMENT_DECIMAL_PLACES, RoundingMode.UP).doubleValue();
                            // help gc
                            bigDecimal = null;
                        }
                    }
                }
                break;
            }

            case FLOAT: {
                float[][] arrays = (float[][]) variable.read(sectionSpec).copyToNDJavaArray();
                result = new Double[arrays.length][arrays[0].length];
                for (int i = 0; i < arrays.length; i++) {
                    for (int j = 0; j < arrays[i].length; j++) {
                        if (missValue == arrays[i][j]){
                            result[i][j] = null;
                        } else {
                            BigDecimal bigDecimal = BigDecimal.valueOf(arrays[i][j]);
                            result[i][j] = bigDecimal.multiply(scaleBd).add(offsetBd).setScale(NcConstant.ELEMENT_DECIMAL_PLACES, RoundingMode.UP).doubleValue();
                            // help gc
                            bigDecimal = null;
                        }
                    }
                }
                break;
            }

            case DOUBLE:{
                double[][] arrays = (double[][]) variable.read(sectionSpec).copyToNDJavaArray();
                result = new Double[arrays.length][arrays[0].length];
                for (int i = 0; i < arrays.length; i++) {
                    for (int j = 0; j < arrays[i].length; j++) {
                        if (missValue == arrays[i][j]){
                            result[i][j] = null;
                        } else {
                            BigDecimal bigDecimal = BigDecimal.valueOf(arrays[i][j]);
                            result[i][j] = bigDecimal.multiply(scaleBd).add(offsetBd).setScale(NcConstant.ELEMENT_DECIMAL_PLACES, RoundingMode.UP).doubleValue();
                            // help gc
                            bigDecimal = null;
                        }
                    }
                }
                break;
            }


            default:{
                throw new RuntimeException("要素"+element+"的数据类型"+dataType+"不支持");
            }
        }
        return result;
    }

    /**
     * 获取nc文件指定要素的数据类型
     * @param netcdfFile        nc文件
     * @param element           要素名称
     * @return  数据类型，可能返回{@link DataType}中的任意值
     */
    public static DataType getNcElementType(NetcdfFile netcdfFile, String element){
        if (netcdfFile == null || element == null || element.length() == 0){
            throw new NullPointerException("传入的参数为空");
        }
        Variable variable = netcdfFile.findVariable(element);
        if (variable == null){
            throw new ApiException("当前nc文件中无要素" + element);
        }
        return variable.getDataType();
    }

    public String getSectionSpec(String variableName) {
        init();

        Variable eleVariable = netcdfFile.findVariable(variableName);

        // 检验要素
        if (Objects.isNull(eleVariable)) {
            log.error("Variable变量{}， 不存在ncFile={}中!", variableName, netcdfFile.getLocation());
            return null;
        }

        // 获取经纬度变量顺序
        int lonDimIndex = -1;
        int latDimIndex = -1;
        for (int i = 0; i < eleVariable.getDimensions().size(); i++) {
            if (Objects.equals(eleVariable.getDimension(i).getShortName(), LON_VARIABLE)) {
                lonDimIndex = i;
            } else if (Objects.equals(eleVariable.getDimension(i).getShortName(), LAT_VARIABLE)) {
                latDimIndex = i;
            }
        }

        // 获取经纬度所有范围
        int lonIndex = lonArr.length - 1;
        int latIndex = latArr.length - 1;
        int[] indexArr = lonDimIndex < latDimIndex ? new int[]{lonIndex, latIndex} : new int[]{latIndex, lonIndex};

        StringBuilder sb = new StringBuilder();
        for (int i : indexArr) {
            sb.append("0:");
            sb.append(i);
            sb.append(",");
        }

        return sb.substring(0, sb.length() - 1);
    }

    public String getSectionSpec(String variableName, double lon, double lat) {
        init();

        Variable eleVariable = netcdfFile.findVariable(variableName);

        // 检验要素
        if (Objects.isNull(eleVariable)) {
            log.error("Variable变量{}， 不存在ncFile={}中!", variableName, netcdfFile.getLocation());
            return null;
        }

        // 获取经纬度变量顺序
        int lonDimIndex = -1;
        int latDimIndex = -1;
        for (int i = 0; i < eleVariable.getDimensions().size(); i++) {
            if (Objects.equals(eleVariable.getDimension(i).getShortName(), LON_VARIABLE)) {
                lonDimIndex = i;
            } else if (Objects.equals(eleVariable.getDimension(i).getShortName(), LAT_VARIABLE)) {
                latDimIndex = i;
            }
        }

        // 获取插值经纬度
        int lonIndex = getLonLatIndex(lonArr, lon);
        int latIndex = getLonLatIndex(latArr, lat);
        int[] indexArr = lonDimIndex < latDimIndex ? new int[]{lonIndex, latIndex} : new int[]{latIndex, lonIndex};

        StringBuilder sb = new StringBuilder();
        for (int i : indexArr) {
            sb.append(i);
            sb.append(",");
        }

        return sb.substring(0, sb.length() - 1);
    }

    /**
     * 获取经或纬度索引
     * @param arr 经或纬度数组
     * @param value 值
     * @return 索引
     */
    private int getLonLatIndex(double[] arr, double value) {
        // 判断是否超出网格数据
        double min = Math.min(arr[0], arr[arr.length - 1]);
        double max = Math.max(arr[0], arr[arr.length - 1]);
        if (value < min || value > max) {
            throw new RuntimeException(String.format("经度或纬度 %f 超出取值范围[%f, %f]", value, min, max));
        }

        // 二分查找最近点左侧点
        int left = 0;
        int right = arr.length - 1;
        int curr = arr.length >> 1;
        boolean incr = arr[left] < arr[right];
        while (Math.abs(left - right) != 1) {
            if (arr[curr] <= value) {
                if (incr) {
                    left = curr;
                } else {
                    right = curr;
                }
            } else {
                if (incr) {
                    right = curr;
                } else {
                    left = curr;
                }
            }
            curr = (left + right) >> 1;
//            log.info("curr={}, left={}, right={}", curr, left, right);
        }

        return left;
    }

    /**
     * Array结果集转基础数据类型数组
     * @param array 数组
     * @return 基础数据类型数组
     */
    private double[] toDoubleArr(Array array) {
        int size = (int) array.getSize();
        double[] arr = new double[size];
        for (int i = 0; array.hasNext(); ++i) {
            arr[i] = array.nextDouble();
        }
        return arr;
    }


    @Override
    public void close() throws IOException {
        try {
            if (netcdfFile != null) {
                netcdfFile.close();
                log.info("关闭NetCDF流，path={}", netcdfFile.getLocation());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPath() {
        return Objects.nonNull(netcdfFile) ? netcdfFile.getLocation() : null;
    }
}
