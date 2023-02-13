package com.zsl0.component.auth.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.zsl0.component.auth.config.SecurityAdminConfigurationProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

/**
 * @author zsl0
 * created on 2022/12/12 20:59
 */
public class TokenUtil {

    /**
     * user对象
     */
    public static String DETAILS_KEY = "DETAILS";

    /**
     * 用户凭证key todo 静态参数提供动态获取
     */
    public static String CERTIFICATE_KEY = "UUID";

    /**
     * userId
     */
    public static String USER_ID_KEY = "USER_ID";

    /**
     * 是否官方自定义认证 对象
     */
    public static String AUTHENTICATED_KEY = "AUTHENTICATED";


    /**
     * 是否管理员
     */
    public static String ADMIN_KEY = "ADMIN";

    /**
     * 许可key
     */
    public static String PERMISSIONS_KEY = "PERMISSIONS";


    /**
     * 发行人
     */
    public static String ISSUER = "zsl0";

    /**
     * 密钥
     */
    public static String SECRET = "zsl0:abc123456";

//    public static void main(String[] args) {
//        List<String> list = new ArrayList<>();
//        list.add("admin");
//        list.add("system");
//        String token = generateToken("123", new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24), new User("zsl0", "123456", 18), "1", 2L, true, true, list);
//        String details = TokenUtil.getDetails(token);
//        String uuid = TokenUtil.getUuid(token);
//        Long userId = TokenUtil.getUserId(token);
//        Boolean authenticated = TokenUtil.getAuthenticated(token);
//        Boolean admin = TokenUtil.getAdmin(token);
//        List<String> permissions = TokenUtil.getPermissions(token);
//        System.out.println(token);
//    }
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    static class User {
//        private String username;
//        private String password;
//        private Integer age;
//    }

//    /**
//     * 生成token
//     */
//    public static String generateToken(String subject, Date expire, String uuid) {
//        String token = null;
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(SECRET);
//            token = JWT.create()
//                    .withIssuer(ISSUER)
//                    .withClaim(CERTIFICATE_KEY, uuid)
//                    .withSubject(subject)
//                    .withExpiresAt(expire)
//                    .sign(algorithm);
//        } catch (JWTCreationException exception) {
//            //Invalid Signing configuration / Couldn't convert Claims.
//        }
//        return token;
//    }

//    /**
//     * 生成token
//     * @param subject 主题
//     * @param expire 过期时间
//     * @param uuid id
//     * @param permissions 认证信息json字符串
//     * @return token
//     */
//    public static String generateToken(String subject, Date expire, String uuid, List<String> permissions) {
//        return generateToken(subject, expire, null, uuid, null, null, null,  permissions);
//    }

    /**
     * 生成token
     * @param subject 主题
     * @param expire 过期时间
     * @param details user对象
     * @param uuid uuid
     * @param userId userId
     * @param authenticated 是否官方自定义认证 对象
     * @param admin 是否管理员
     * @param permissions 角色
     * @return token
     */
    public static String generateToken(String subject, Date expire, Object details, String uuid, Long userId, Boolean authenticated, Boolean admin, List<String> permissions) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(DETAILS_KEY, details);
        claims.put(CERTIFICATE_KEY, uuid);
        claims.put(USER_ID_KEY, userId);
        claims.put(AUTHENTICATED_KEY, authenticated);
        claims.put(ADMIN_KEY, admin);
        claims.put(PERMISSIONS_KEY, permissions);

        return JwtUtil.generate(SECRET,
                ISSUER,
                subject,
                expire,
                claims);
    }

    /**
     * 生成token
     * @param subject 主题
     * @param expire 过期时间
     * @param details user对象
     * @param uuid uuid
     * @param userId userId
     * @param authenticated 是否官方自定义认证 对象
     * @param admin 是否管理员
     * @param permissions 角色
     * @return token
     */
    public static String generateToken(String subject, LocalDateTime expire, Object details, String uuid, Long userId, Boolean authenticated, Boolean admin, List<String> permissions) {
        return generateToken(subject, Date.from(expire.atZone(ZoneId.systemDefault()).toInstant()), details, uuid, userId, authenticated, admin, permissions);
    }

    /**
     * 获取用户user对象
     */
    public static String getDetails(String token) {
        return JwtUtil.getClaim(token,
                DETAILS_KEY,
                SECRET,
                ISSUER,
                String.class);
    }

    /**
     * 获取用户唯一凭证
     */
    public static String getUuid(String token) {
        return JwtUtil.getClaim(token,
                CERTIFICATE_KEY,
                SECRET,
                ISSUER,
                String.class);
    }

    /**
     * 获取用户userId
     */
    public static Long getUserId(String token) {
        return JwtUtil.getClaim(token,
                USER_ID_KEY,
                SECRET,
                ISSUER,
                Long.class);
    }

    /**
     * 获取用户 是否官方自定义认证 对象
     */
    public static Boolean getAuthenticated(String token) {
        return JwtUtil.getClaim(token,
                AUTHENTICATED_KEY,
                SECRET,
                ISSUER,
                Boolean.class);
    }

    /**
     * 获取用户 是否管理员
     */
    public static Boolean getAdmin(String token) {
        return JwtUtil.getClaim(token,
                ADMIN_KEY,
                SECRET,
                ISSUER,
                Boolean.class);
    }

    /**
     * 获取权限
     */
    public static List<String> getPermissions(String token) {
        return JwtUtil.getClaims(token, PERMISSIONS_KEY, SECRET, ISSUER, String.class);
    }


    /**
     * 是否过期
     */
    public static boolean isExpire(String token) {
        Long expire = getExpire(token);
        return expire > System.currentTimeMillis();
    }

    /**
     * 获取过期时间
     */
    public static Long getExpire(String token) {
        return JwtUtil.getExpire(token, SECRET, ISSUER);
    }

}
