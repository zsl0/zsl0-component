package com.zsl0.auth.core.util;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.zsl0.component.common.core.exception.auth.token.TokenGenerateException;
import com.zsl0.component.common.core.exception.auth.token.TokenUnknownException;
import com.zsl0.component.common.core.exception.auth.token.TokenVerifyFailedException;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zsl0
 * created on 2022/12/12 21:00
 */
public class JwtUtil {

    /**
     * 生成Java Web Token
     * @param secret 盐
     * @param issuer 发行人
     * @param subject 标题
     * @param expire 过期时间
     * @param claims 信息
     * @return token
     */
    public static String generate(String secret, String issuer, String subject, Date expire, Map<String, Object> claims) {
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTCreator.Builder builder = JWT.create()
                    .withIssuer(issuer)
                    .withSubject(subject)
                    .withExpiresAt(expire);
            if (Objects.nonNull(claims)) {
                for (Map.Entry<String, Object> entry : claims.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (value instanceof String) {
                        builder.withClaim(key, (String) value);
                    } else if (value instanceof Boolean) {
                        builder.withClaim(key, (Boolean) value);
                    } else if (value instanceof Integer) {
                        builder.withClaim(key, (Integer) value);
                    } else if (value instanceof Double) {
                        builder.withClaim(key, (Double) value);
                    } else if (value instanceof Long) {
                        builder.withClaim(key, (Long) value);
                    } else if (value instanceof Date) {
                        builder.withClaim(key, (Date) value);
                    } else if (value instanceof Map) {
                        builder.withClaim(key, (Map) value);
                    } else if (value instanceof List) {
                        builder.withClaim(key, (List) value);
                    } else {
                        if (Objects.isNull(value)) {
                            continue;
                        }
                        builder.withClaim(key, JSONUtil.toJsonStr(value));
//                        throw new TokenUnknownException(String.format("unknown value type = %s", value.getClass()));
                    }
                }
            }
            token = builder.sign(algorithm);
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            throw new TokenGenerateException(exception.getCause());
        }
        return token;
    }

    /**
     * 获取Payload信息
     */
    public static <T> T getClaim(String token, String key, String secret, String issuer, Class<T> clazz) {
        return getClaim(token, key, secret, issuer).as(clazz);
    }

    private static Claim getClaim(String token, String key, String secret, String issuer) {
        Map<String, Claim> claims = getClaims(token, secret, issuer);
        return claims.get(key);
    }

    /**
     * 获取Payload信息
     */
    public static <T> List<T> getClaims(String token, String key, String secret, String issuer, Class<T> clazz) {
        return getClaims(token, secret, issuer).get(key).asList(clazz);
    }

    /**
     * 获取Payload信息
     */
    private static Map<String, Claim> getClaims(String token, String secret, String issuer) {
        DecodedJWT decodedJWT = verity(token, secret, issuer);
        return decodedJWT.getClaims();
    }

    /**
     * 获取过期时间
     */
    public static Long getExpire(String token, String secret, String issuer) {
        DecodedJWT decodedJWT = verity(token, secret, issuer);
        return decodedJWT.getExpiresAt().getTime();
    }

    /**
     * 解析token
     */
    private static DecodedJWT verity(String token, String secret, String issuer) {
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
