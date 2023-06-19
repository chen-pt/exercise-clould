package com.person.chenpt.core.shiroJwt;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.person.chenpt.core.base.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt过滤器，作为shiro的过滤器，对请求进行拦截并处理
 * 代码的执行流程 preHandle -> isAccessAllowed -> isLoginAttempt -> executeLogin
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2023-06-19 14:02
 * @Modified By:
 */
@Slf4j
@Component
public class JwtFilter extends BasicHttpAuthenticationFilter {
    String access_token = "jwt_token";
    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含access_token字段即可(和前端约定放入header)
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
//        String authorization = req.getHeader(access_token); //请求头获取
        String authorization = getAccessToken(req); //cookie获取
        return authorization != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        String authorization = httpServletRequest.getHeader(access_token);
        String authorization = getAccessToken(httpServletRequest);

        if(authorization == null || "".equals(authorization)){
            Result<Object> res = new Result<>();
            res.setCode(HttpStatus.FORBIDDEN.getReasonPhrase());
            res.setMsg("无token，无权访问，请先登录");
            out(response, res);
            return false;
        }
        // token存在，进行验证
        JwtToken token = new JwtToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 过滤器拦截请求的入口方法
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            return executeLogin(request, response);
        } catch (Exception e) {
            response401(request, response);
        }
        return false;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 默认返回401未认证,可以进行修改
     * 解决访问未授权的url会出现shiro内置登录弹窗
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {

        return false;
    }

    /**
     * 将非法请求跳转到 /401
     */
    private void response401(ServletRequest req, ServletResponse resp) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            httpServletResponse.sendRedirect("/401");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * json形式返回结果token验证失败信息，无需转发
     */
    private void out(ServletResponse response, Result res) throws IOException {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        ObjectMapper mapper = new ObjectMapper();
        String jsonRes = mapper.writeValueAsString(res);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.getOutputStream().write(jsonRes.getBytes());
    }


    private String getAccessToken(HttpServletRequest request){
        String key = access_token;
        String from = "head";
        String accessToken = request.getHeader(key);
        if (StrUtil.isBlank(accessToken)){
            from = "参数";
            accessToken = request.getParameter(key);
            if (StrUtil.isBlank(accessToken)){
                from = "cookie";
                Cookie[] cookies = request.getCookies();
                if (cookies != null){
                    for (int i = 0; i < cookies.length; i++) {
                        if (key.equals(cookies[i].getName())) {
                            accessToken = cookies[i].getValue();
                            break;
                        }
                    }
                }
            }
        }
        return accessToken;
    }
}
