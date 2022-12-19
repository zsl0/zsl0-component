package com.zsl0.component.auth.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.zsl0.component.auth.config.SecurityAdminConfigurationProperties;

import java.util.*;

/**
 * @author zsl0
 * created on 2022/12/12 20:59
 */
public class TokenUtil {

    /**
     * 用户凭证key todo 静态参数提供动态获取
     */
    public static String CERTIFICATE_KEY = "UUID";

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

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("admin");
        list.add("system");
        String token = generateToken("123", new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24), "1", list);
        System.out.println(token);
    }

    /**
     * 生成token
     */
    public static String generateToken(String subject, Date expire, String uuid) {
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            token = JWT.create()
                    .withIssuer(ISSUER)
                    .withClaim(CERTIFICATE_KEY, uuid)
                    .withSubject(subject)
                    .withExpiresAt(expire)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        return token;
    }

    /**
     * 生成token
     * @param subject 主题
     * @param expire 过期时间
     * @param permissions 认证信息json字符串
     * @return
     */
    public static String generateToken(String subject, Date expire, String uuid, List<String> permissions) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CERTIFICATE_KEY, uuid);
        claims.put(PERMISSIONS_KEY, permissions);

        return JwtUtil.generate(SECRET,
                ISSUER,
                subject,
                expire,
                claims);
    }

    /**
     * 获取用户唯一凭证
     */
    public static String getUuid(String token) {
        return JwtUtil.getClaim(token,
                CERTIFICATE_KEY,
                SECRET,
                ISSUER);
    }

    /**
     * 获取权限
     */
    public static List<String> getPermissions(String token) {
        Map<String, Claim> claims = JwtUtil.getClaims(token, SECRET, ISSUER);
        return claims.get(PERMISSIONS_KEY).asList(String.class);
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
