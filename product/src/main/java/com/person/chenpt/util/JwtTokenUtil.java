package com.person.chenpt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 *
 * Created by DBC-PC on 2018/10/9
 */
public class JwtTokenUtil {

    private static final String SECRET = "AuthSecret_7201";

    /**
     * 创建token
     * @param userId
     * @return
     */
    public static String createJwtToken(String userId){

        Calendar nowTime = Calendar.getInstance(); // expire time
        nowTime.add(Calendar.DATE, 30); //有10天有效期
        Date expiresDate = nowTime.getTime();

        Claims claims = Jwts.claims();
        //claims.put("name","cy");
        //claims.put("password","cy");
        claims.put("userId", userId);
        //claims.setAudience("cy");
        //claims.setIssuer("cy");
        //        iss: jwt签发者。
        //        sub: jwt所面向的用户。
        //        aud: 接收jwt的一方。
        //        exp: jwt的过期时间，这个过期时间必须要大于签发时间。
        //        nbf: 定义在什么时间之前，该jwt都是不可用的。
        //        iat: jwt的签发时间。
        //        jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
        String token = Jwts.builder().setClaims(claims).setExpiration(expiresDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        return token;
    }



    /**
     * 根据token获取数据
     * @param token
     * @return
     */
    public static Claims parseJwtToken(String token) {
        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            String signature = jws.getSignature();//签名、密钥
            Map<String, String> header = jws.getHeader();//头部
            Claims claims = jws.getBody();//用户数据
            return claims;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//public static void main(String[] args){
//        String token = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFM1MTIifQ.eyJ1c2VySWQiOiJmMGM1YjFkNzYxYTU0MmQ2YjAxNDc4MDJjOTk4NDhhZSIsImV4cCI6MTI4NzM5MDk1Mn0.77hws6DNI9iJNtPToJcoCj8PCISA0VKxnbl-bwk7cRiNwXIZ-q5waUp4K0Y3kjAVGCLRhj6Zbw1fIAWGOs8_Zw";
//    Claims claims  =    JwtTokenUtil.parseJwtToken(token);
////    String token =JwtTokenUtil.createJwtToken("8EC294A0CD094465A8F770292B4AACB1");
////    String token1 =JwtTokenUtil.createJwtToken("C6A3D88C7E73F17BA1AAF4C6A3EF17B0");
////    String token2 =JwtTokenUtil.createJwtToken("07560484B8AD4F2AB6E218E2CC4EE5A0");
////    System.out.println(token);
////    System.out.println(token1);
////    System.out.println(token2);
//
//    System.out.println(JSONObject.toJSONString(claims));
//
//}

}
