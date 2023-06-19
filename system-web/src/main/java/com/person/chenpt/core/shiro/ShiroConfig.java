package com.person.chenpt.core.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2023-06-16 16:26
 * @Modified By:
 */
@Configuration
public class ShiroConfig {

    /**
     * 自定义Realm
     *
     * @return
     */
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * 安全事务管理器
     *
     * @param userRealm
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 自定义realm添加到shiro管理
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 过滤
     *
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        // 添加shiro的内置过滤器
        /**
         * anon:    无需认证就可以访问
         * authc:   必须认证了才能访问
         * user:    必须拥有 记住我功能 才能用
         * perms:   拥有对某个资源的权限才能访问
         * role:    拥有某个角色权限才能访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/login", "anon");
        filterMap.put("/jwt/**", "anon");
        /**swagger接口文档过滤*/
        filterMap.put("/swagger-resources", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/doc.html", "anon");

        filterMap.put("/**", "authc");

        bean.setFilterChainDefinitionMap(filterMap);

        // 设置登录页面的请求
        bean.setLoginUrl("/");
        bean.setUnauthorizedUrl("/unauthorized");

        return bean;
    }

}
