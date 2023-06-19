package com.person.chenpt.server.bus.sys.controller;

import com.person.chenpt.core.base.model.Result;
import com.person.chenpt.core.shiroJwt.JwtToken;
import com.person.chenpt.core.utils.JWTUitls;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2023-06-16 14:26
 * @Modified By:
 */
@RestController
@Api(tags = "登录")
public class LoginController {

    @GetMapping("/login")
    @ApiOperation("登录")
    public Result login(String userName, String passwd, HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        // 需要对密码进行加密 或者 自定义密码比较器
        UsernamePasswordToken token = new UsernamePasswordToken(userName, passwd);
        subject.login(token);

        Cookie cookie = new Cookie("c_id",request.getSession().getId());
        cookie.setMaxAge(60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return Result.success();
    }

    @GetMapping("/loginJwt")
    @ApiOperation("登录")
    public Result loginJwt(String userName, String passwd, HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        String jwtTokenStr = JWTUitls.createJwtToken("123456909");
        JwtToken jwtToken = new JwtToken(jwtTokenStr);
        subject.login(jwtToken);

        Cookie cookie = new Cookie("jwt_token",jwtTokenStr);
        cookie.setMaxAge(60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return Result.success();
    }

    @GetMapping("/logout")
    @ApiOperation("登出")
    public Result logout(String userName, String passwd) {
        SecurityUtils.getSubject().logout();
        return Result.success();
    }

}
