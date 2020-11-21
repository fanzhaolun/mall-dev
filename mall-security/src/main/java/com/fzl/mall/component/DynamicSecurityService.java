package com.fzl.mall.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * 动态权限相关类
 */
public interface DynamicSecurityService {
    /**
     * 加载安源ANT通配符和资源对应MAP
     */
    Map<String, ConfigAttribute> loadDataSource();
}
