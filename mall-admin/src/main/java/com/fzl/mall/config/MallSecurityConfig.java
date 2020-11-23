package com.fzl.mall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MallSecurityConfig extends SecurityConfig {
    @Autowired
    private UmsAdminService adminService;

    @Autowired
    private UmsResourceService resourceService;

    @Bean
    public UserDetailsService userDetailsService(){
        //获取登录用户信息
        return username -> userDetailsService().loadUserByUsername(username);
    }
}
