package com.example.config;

import cn.hutool.core.map.MapUtil;
import com.example.shiro.AccountRealm;
import com.example.shiro.AuthFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class ShiroConfig {

    @Bean
    public SecurityManager securityManager(AccountRealm accountRealm){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(accountRealm);

        log.info("------------------>securityManager注入成功");

        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        // 配置登录的url和登录成功的url
        filterFactoryBean.setLoginUrl("/login");
        filterFactoryBean.setSuccessUrl("/user/center");
        // 配置未授权跳转页面
        filterFactoryBean.setUnauthorizedUrl("/error/403");

        //filterFactoryBean.setFilters(MapUtil.of("auth", authFilter()));

        Map<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("/user/home", "authc");
        hashMap.put("/user/set", "authc");
        hashMap.put("/user/upload", "authc");
        hashMap.put("/login", "anon");

        hashMap.put("/res/**", "anon");
        hashMap.put("/user/index", "authc");
        hashMap.put("/user/public", "authc");
        hashMap.put("/user/collection", "authc");
        hashMap.put("/user/mess", "authc");
        hashMap.put("/msg/remove/", "authc");
        hashMap.put("/message/nums/", "authc");
        hashMap.put("/collection/remove/", "authc");
        hashMap.put("/collection/find/", "authc");
        hashMap.put("/collection/add/", "authc");
        hashMap.put("/post/edit", "authc");
        hashMap.put("/post/submit", "authc");
        hashMap.put("/post/delete", "authc");
        hashMap.put("/post/reply/", "authc");
        hashMap.put("/websocket", "anon");
        filterFactoryBean.setFilterChainDefinitionMap(hashMap);

        return filterFactoryBean;

    }

    @Bean
    public AuthFilter authFilter() {
        return new AuthFilter();
    }
}
