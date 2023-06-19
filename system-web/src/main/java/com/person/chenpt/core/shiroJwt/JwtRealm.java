package com.person.chenpt.core.shiroJwt;

import com.person.chenpt.core.utils.JWTUitls;
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
public class JwtRealm extends AuthorizingRealm {

    /**
     * 限定这个realm只能处理JwtToken（不加的话会报错）
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

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
     * 默认使用此方法认证用户名正确与否验证，错误抛出异常即可。
     *
     * @param authenticationToken shiro登录时生成的UsernamePasswordToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 根据用户去数据库读取
        User user = new User("chen", "123456909");

        String token = (String) authenticationToken.getCredentials();  //JwtToken中重写了这个方法了
//        String userId = JWTUitls.parseUserIdByJwtToken(token);

        return new SimpleAuthenticationInfo(user, token, user.getName());
    }
}
