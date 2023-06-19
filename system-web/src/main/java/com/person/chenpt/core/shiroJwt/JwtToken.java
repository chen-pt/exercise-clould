package com.person.chenpt.core.shiroJwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 自定义的shiro接口token，可以通过这个类将string的token转型成AuthenticationToken，可供shiro使用
 * 注意：需要重写getPrincipal和getCredentials方法，因为是进行三件套处理的，没有特殊配置shiro无法通过这两个
 * 方法获取到用户名和密码，需要直接返回token，之后交给JwtUtil去解析获取。
 *
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2023-06-19 11:08
 * @Modified By:
 */
public class JwtToken implements AuthenticationToken {

    private String jwtToken;

    public JwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    // 用户名
    @Override
    public Object getPrincipal() {
        return jwtToken;
    }

    // 密码
    @Override
    public Object getCredentials() {
        return jwtToken;
    }
}
