package com.holun.tmall.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 创建realm对象，realm需要自定义
     */
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * 创建安全管理对象
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(UserRealm userRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //设置realm
        defaultWebSecurityManager.setRealm(userRealm);

        return defaultWebSecurityManager;
    }

    /**
     * shiro过滤器
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        /**
         * 添加shiro内置的过滤器:
         * anon 无需认证就可以访问
         * authc 必须认证了才能访问
         * user 必须拥有 “记住我” 功能才能用
         * perms 拥有某个资源的权限才能访问
         * roles 拥有某个角色的权限才能访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();

        //认证成功的用户（登录成功的用户），才能访问这些前台资源路径
        filterMap.put("/foreCart", "authc");
        filterMap.put("/foreMyOrder", "authc");
        filterMap.put("/foreAddCart", "authc");
        filterMap.put("/foreDeleteOrder", "authc");
        filterMap.put("/foreDeleteOrderItem", "authc");
        filterMap.put("/foreChangeOrderItem", "authc");
        filterMap.put("/foreBuyFromProductPage", "authc");
        filterMap.put("/foreBuyFromCart", "authc");
        filterMap.put("/foreToConfirmOrderPage", "authc");
        filterMap.put("/foreCreateOrder", "authc");
        filterMap.put("/foreToConfirmPaymentPage", "authc");
        filterMap.put("/forePaymentSuccess", "authc");
        filterMap.put("/foreConfirmReceipt", "authc");
        filterMap.put("/foreReceivedSuccess", "authc");
        filterMap.put("/foreReview", "authc");
        filterMap.put("/foreDoReview", "authc");
        //管理员用户，才能进入后台
        filterMap.put("/admin*", "roles[administrator]");
        filterMap.put("/admin*/*", "roles[administrator]");
        //登录后，才能访问后台所有的资源路径
        filterMap.put("/admin**/*", "authc");

        //设置过滤器链
        bean.setFilterChainDefinitionMap(filterMap);
        //设置登录页面。未登录就会跳转到登录页面（SpringSecurity默认提供给一个登录页面（可以使用默认的，也可以使用自定义的），shiro不提供）
        bean.setLoginUrl("/foreToLoginPage");
        //没有权限，就跳转到指定的页面
        bean.setUnauthorizedUrl("/403");

        return bean;
    }


}

