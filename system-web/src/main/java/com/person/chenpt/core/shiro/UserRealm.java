package com.person.chenpt.core.shiro;

import com.person.chenpt.server.bus.user.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 认证和授权的入口。可以自定义realm，
 * 对认证的方式进行自定义处理。
 * 只要调用了subject.login(token)方法，就会进入到realm的doGetAuthenticationInfo内。
 *
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2023-06-16 16:14
 * @Modified By:
 */
public class UserRealm extends AuthorizingRealm {

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 设置角色
        authorizationInfo.setRoles(null);
        // 设置资源
        authorizationInfo.setStringPermissions(null);
        return new SimpleAuthorizationInfo();
    }

    /**
     * 认证
     *
     * @param authenticationToken shiro登录时生成的UsernamePasswordToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 根据用户去数据库读取
        User user = new User("chen", "123456909");
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        System.out.println("认证：" + userToken.getUsername());
        System.out.println("认证密码：" + userToken.getPassword() + "");
        return new SimpleAuthenticationInfo("", user.getPasswd(), "");
    }
}
