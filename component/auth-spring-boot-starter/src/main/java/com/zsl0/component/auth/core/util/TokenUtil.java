package com.zsl0.component.auth.core.util;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @author zsl0
 * created on 2022/12/12 20:59
 */
public class TokenUtil {

//    // todo 参数需要初始化
//    // 访问Token过期分钟
//    public static Integer ACCESS_TOKEN_MINUTE_EXPIRE = 3 * 24 * 60;
//    // 刷新Token过期分钟
//    public static Integer REFRESH_TOKEN_MINUTE_EXPIRE = 3 * 24 * 60;
//
//    /**
//     * 创建唯一Token, 凭借Payload中uuid作为当前用户的唯一凭证，与用户进行绑定
//     */
//    public static String createAccessToken(String uuid) {
//        Date expire = DateUtil.offsetMinute(new Date(), ACCESS_TOKEN_MINUTE_EXPIRE);
//        // 使用 uuid 作为 键值
//        return JwtUtil.generateToken("access_token", expire, uuid);
//    }
//
//    /**
//     * 创建唯一Token, 凭借Payload中uuid作为当前用户的唯一凭证，与用户进行绑定
//     */
//    public static String createAccessToken(String uuid, String authenticationJson) {
//        Date expire = DateUtil.offsetMinute(new Date(), ACCESS_TOKEN_MINUTE_EXPIRE);
//        // 使用 uuid 作为 键值
//        return JwtUtil.generateToken("access_token", expire, uuid, authenticationJson);
//    }
//
//
//    public static String createRefreshToken(String uuid) {
//        Date expire = DateUtil.offsetMinute(new Date(), REFRESH_TOKEN_MINUTE_EXPIRE);
//        // 使用 uuid 作为 键值
//        return JwtUtil.generateToken("refresh_token", expire, uuid);
//    }
//
//    /**
//     * 获取access_token存储uuid
//     */
//    public static String getAccessTokenUuid(String token) {
//        String subject = JwtUtil.getTokenInfo(token, "sub");
//        if (subject == null || !"access_token".equals(subject)) {
//            throw new NotAccessTokenException("token认证失败，不是access_token!");
//        }
//        return JwtUtil.getTokenInfo(token, "uuid");
//    }
//
//    /**
//     * 获取access_token存储Authentication信息
//     */
//    public static String getAccessTokenAuthentication(String token) {
//        String subject = JwtUtil.getTokenInfo(token, "sub");
//        if (!"access_token".equals(subject)) {
//            throw new NotAccessTokenException("token认证失败，不是access_token!");
//        }
//
//        return JwtUtil.getTokenInfo(token, "Authentication");
//    }
//
//    /**
//     * 获取refresh_token存储uuid
//     */
//    public static String getRefreshTokenUuid(String token) {
//        String subject = JwtUtil.getTokenInfo(token, "sub");
//        if (!"refresh_token".equals(subject)) {
//            throw new NotAccessTokenException("token认证失败，refresh_token!");
//        }
//        return JwtUtil.getTokenInfo(token, "uuid");
//    }
//
//    /**
//     * 是否过期
//     */
//    public static boolean isExpire(String token) {
//        Long expire = getExpire(token);
//        return expire == null || expire < System.currentTimeMillis();
//    }
//
//    /**
//     * 获取过期时间
//     */
//    public static Long getExpire(String token) {
//        return JwtUtil.getExpire(token);
//    }

}
