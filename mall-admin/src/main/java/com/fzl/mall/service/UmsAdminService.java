package com.fzl.mall.service;

import com.fzl.mall.model.UmsAdmin;

/**
 * 后台管理员Service
 */
public interface UmsAdminService {
    /**
     * 根据用户名获取后台管理员
     */
    UmsAdmin getAdminByUsername(String username);
}
