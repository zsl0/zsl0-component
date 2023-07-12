package com.zsl0.component.common.core.annotation.valid;


import com.zsl0.component.common.model.constant.date.DateConstant;
import org.springframework.validation.annotation.Validated;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 验证时间字符串是否为指定格式
 * @author yanglb
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidTime.DateTimeHandle.class)
public @interface ValidTime {

    // 提示内容
    String message() default "时间格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String format() default DateConstant.ymdh;

    /**
     * 自定义验证处理类
     */
    class  DateTimeHandle implements ConstraintValidator<ValidTime, Object> {

        private ValidTime validTime;

        @Override
        public void initialize(ValidTime validTime) {
            this.validTime = validTime;
        }
        @Override
        public boolean isValid(@Validated @NotNull Object value, ConstraintValidatorContext context) {
            if (value == null){
                return false;
            }
            // 验证时间格式
            return !isEmpty(this.validTime.format()) && isValidDate(value.toString(), this.validTime.format());
        }

        private static boolean isEmpty(String str){
            return str == null || "".equals(str) || str.length() == 0 || "null".equals(str);
        }

        private static boolean isValidDate(String date,String format) {
            if (date.length() != format.length()){
                return false;
            }
            DateFormat fmt = new SimpleDateFormat(format);
            try {
                fmt.parse(date);
                return true;
            } catch (Exception e) {
                // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 获取有时间校验的字段
         * @param valueClass 获取字段提供的类
         * @return 是否成立
         */
        private List<Field> getDateTimeAnnotationField(Class<?> valueClass){
            Field[] fields = valueClass.getDeclaredFields();
            if (fields.length > 0){
                return  Arrays.stream(fields).filter(fls -> fls.getAnnotation(ValidTime.class)!=null).collect(Collectors.toList());
            }
            return new ArrayList<>();
        }
    }
}