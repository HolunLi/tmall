package com.holun.tmall.config;

import com.holun.tmall.entity.User;
import com.holun.tmall.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    /**
     * 认证
     * 执行了 subject.login(token); 后，就会自动调用doGetAuthenticationInfo方法对登录的用户进行身份认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("对登录的用户进行身份认证");

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.queryUserByName(token.getUsername());
        if (user == null) //不存在该用户，认证失败（登录失败）
            return null;

        //principal: 认证的实体信息。可以是 username, 也可以是数据表对应的用户的实体类对象.
        Object principal = user;
        //credentials（凭证）: 将用户密码作为凭证
        Object credentials = user.getPwd();
        //使用md5+盐值对密码进行加密
        //计算盐值（盐值需要唯一，一般使用随机字符串）
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getName());
        //调用getName方法（从父类继承而来），可以获取当前realm对象的name
        String realmName = getName();
        return new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
    }

    /**
     * 授权
     * 当登录成功的用户访问已设置权限的资源路径时，就会执行doGetAuthorizationInfo方法，对用户进行授权。
     * 授权成功的用户才能访问这些资源路径
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("对已认证的用户进行授权");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //将当前符合条件的用户设置为管理员
        if (user.getRole() != null)
            info.addRole(user.getRole());

        return info;
    }
}

