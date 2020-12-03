package com.fzl.mall.dao;

import com.fzl.mall.model.UmsMenu;
import com.fzl.mall.model.UmsResource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 自定义后台角色管理DAO
 */
@Repository
public interface UmsRoleDao {
    /**
     * 根据用户ID获取菜单
     */
    List<UmsMenu> getMenuList(@Param("adminId") Long adminId);

    /**
     * 根据角色ID获取菜单
     */
    List<UmsMenu> getMenuListByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色ID获取资源
     */
    List<UmsResource> getResourceByRoleId(@Param("roleId") Long roleId);
}
