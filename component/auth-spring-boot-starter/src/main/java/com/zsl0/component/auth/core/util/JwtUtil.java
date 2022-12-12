package com.zsl0.component.auth.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.zsl0.component.auth.core.exception.token.TokenGenerateException;
import com.zsl0.component.auth.core.exception.token.TokenVerifyFailedException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zsl0
 * created on 2022/12/12 21:00
 */
public class JwtUtil {

    // todo 参数需要初始化
    // 密钥
    public static String secret = "pacx:zsl:secret:123456789abc";
    // 发行人
    public static String issuer = "zsl0";

    /**
     * 用户凭证key
     */
    public static final String CERTIFICATE_KEY = "UUID";

    public static final String PERMISSIONS_KEY = "PERMISSIONS";

    /**
     * 生成token
     */
    public static String generateToken(String subject, Date expire, String uuid) {
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            token = JWT.create()
                    .withIssuer(issuer)
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
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            token = JWT.create()
                    .withIssuer(issuer)
                    .withClaim(CERTIFICATE_KEY, uuid)
                    .withClaim(PERMISSIONS_KEY, permissions)
                    .withSubject(subject)
                    .withExpiresAt(expire)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            throw new TokenGenerateException(exception.getCause());
        }
        return token;
    }
    /**
     * 获取过期时间
     */
    public static Long getExpire(String token) {
        DecodedJWT decodedJWT = verity(token);
        return decodedJWT.getExpiresAt().getTime();
    }

    /**
     * 是否过期
     */
    public static boolean isExpire(String token) {
        Long expire = getExpire(token);
        return expire < System.currentTimeMillis();
    }


    /**
     * 获取token信息
     * @param token token凭证
     * @param key key值
     */
    public static String getTokenInfo(String token, String key) {
        return getClaim(token, key);
    }

    /**
     * 获取用户唯一凭证
     */
    public static String getUuid(String token) {
        return getClaim(token, CERTIFICATE_KEY);
    }

    /**
     * 获取权限
     */
    public static List<String> getPermissions(String token) {
        Map<String, Claim> claims = getClaims(token);
        return claims.get(PERMISSIONS_KEY).asList(String.class);
    }

    /**
     * 获取Payload信息
     */
    private static String getClaim(String token, String key) {
        Map<String, Claim> claims = getClaims(token);
//        return claims == null ? null : claims.get(key).asString();
        return claims.get(key).asString();
    }

    /**
     * 获取Payload信息
     */
    private static Map<String, Claim> getClaims(String token) {
        DecodedJWT decodedJWT = verity(token);
//        return decodedJWT == null ? null : decodedJWT.getClaims();
        return decodedJWT.getClaims();
    }

    /**
     * 解析token
     */
    private static DecodedJWT verity(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = null;
        try {
            verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build(); //Reusable verifier instance
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            throw new TokenVerifyFailedException(exception.getCause());
        }
        return verifier.verify(token);
    }
}
