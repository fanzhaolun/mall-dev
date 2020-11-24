package com.fzl.mall.service;

import com.fzl.mall.dto.UmsAdminParam;
import com.fzl.mall.dto.UpdateAdminPasswordParam;
import com.fzl.mall.model.UmsAdmin;
import com.fzl.mall.model.UmsResource;
import com.fzl.mall.model.UmsRole;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台管理员Service
 */
public interface UmsAdminService {
    /**
     * 根据用户名获取后台管理员
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 注册功能
     */
    UmsAdmin register(UmsAdminParam umsAdminParam);

    /**
     * 登录国能
     * @param username 用户名
     * @param password 密码
     * @return 生成JWT的token
     */
    String login(String username,String password);

    /**
     * 刷新token的功能
     * @param oldToken 旧的token
     * @return
     */
    String refreshToken(String oldToken);

    /**
     * 根据用户Id获取用户
     */
    UmsAdmin getItem(Long id);

    /**
     * 根据用户名或者昵称分页查询用户
     */
    List<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改指定用户的信息
     */
    int update(Long id, UmsAdmin umsAdmin);

    /**
     * 删除指定用户
     */
    int delete(Long id);
    /**
     * 修改用户角色关系
     */
    @Transactional
    int updateRole(Long adminId, List<Long> roleIds);

    /**
     * 获取用户对应的角色
     */
    List<UmsRole> getRoleList(Long adminId);

    /**
     * 获取指定用户可以访问的资源
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 修改用户的+—权限
     */
    int updatePermission(Long adminid, List<Long> permissionIds);

    /**
     * 修改密码
     */
    int updatePassword(UpdateAdminPasswordParam updateAdminPasswordParam);
    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);
}
