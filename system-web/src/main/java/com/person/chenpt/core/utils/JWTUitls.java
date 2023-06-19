package com.person.chenpt.core.utils;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Header（头部）
 * Payload（负载）
 * Signature（签名）
 *
 */
public class JWTUitls {

    private static final String SECRET = "chenpt_test";//在真实开发中密钥更严谨，一定不要泄露
    //指定一个token过期时间（毫秒）
    private static final long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000; //7天
    Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);

    /**
     * 创建token
     *
     * @param userId
     * @return
     */
    public static String createJwtToken(String userId) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        String token = Jwts.builder()
                        .setClaims(claims)
//                        .setExpiration(getExpiresDate())
                        .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_TIME))//这里设置过期时间为10秒
                        .signWith(SignatureAlgorithm.HS512, SECRET)
                        .setHeaderParam("type", "jwt")
                        .compact();
        return token;
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public static Claims parseJwtToken(String token) {

        Jws<Claims> jws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
        String signature = jws.getSignature();//签名、密钥
        Map<String, String> header = jws.getHeader();//头部

        Claims claims = jws.getBody();//用户数据
        return claims;

    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public static String parseUserIdByJwtToken(String token) {
        Claims claims = parseJwtToken(token);//用户数据
        return claims.get("userId").toString();
    }

    /**
     * 判断是否过期
     *
     * @param token
     * @return
     */
    public static boolean isExpire(String token){
        Jws<Claims> jws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
        return jws.getBody().getExpiration().getTime() < System.currentTimeMillis() ;
    }

    /**
     * 设置过期时间
     *
     * @return
     */
    private static Date getExpiresDate() {
        // 获取本地时间
        Calendar nowTime = Calendar.getInstance();
        // 增加30天有效期
        nowTime.add(Calendar.DATE, 30);
        return nowTime.getTime();
    }

}

