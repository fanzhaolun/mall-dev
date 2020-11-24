package com.fzl.mall.dao;

import com.fzl.mall.model.UmsAdminRoleRelation;
import com.fzl.mall.model.UmsPermission;
import com.fzl.mall.model.UmsResource;
import com.fzl.mall.model.UmsRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 后台用户和角色自定义DAO
 */
@Repository
public interface UmsAdminRoleRelationDao {
    /**
     * 批量插入用户角色关系
     */
    int insertList(@Param("list")List<UmsAdminRoleRelation> adminRoleRelationList);

    /**
     * 获取用于所有角色
     */
    List<UmsRole> getRoleList(@Param("adminID") Long adminId);

    /**
     * 获取用户所有角色权限
     */
    List<UmsPermission> getRolePermissionList(@Param("adminId") Long adminId);

    /**
     * 获取用户所有权限（包括+-权限)
     */
    List<UmsPermission> getPermissionList(@Param("adminId") Long adminId);

    /**
     * 获取用户所有可访问资源
     */
    List<UmsResource> getResourceList(@Param("adminId") Long adminId);

    /**
     * 获取资源相关用户ID列表
     */
    List<Long> getAdminList(@Param("resourceId") Long resourceId);
}
