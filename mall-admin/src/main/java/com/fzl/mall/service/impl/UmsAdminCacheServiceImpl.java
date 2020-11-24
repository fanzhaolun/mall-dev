package com.fzl.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.fzl.mall.dao.UmsAdminRoleRelationDao;
import com.fzl.mall.mapper.UmsAdminRoleRelationMapper;
import com.fzl.mall.model.UmsAdmin;
import com.fzl.mall.model.UmsAdminRoleRelation;
import com.fzl.mall.model.UmsAdminRoleRelationExample;
import com.fzl.mall.model.UmsResource;
import com.fzl.mall.service.RedisService;
import com.fzl.mall.service.UmsAdminCacheService;
import com.fzl.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UmsAdminCacheService实现类
 */
@Service
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {
    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;
    @Autowired
    private UmsAdminRoleRelationMapper adminRoleRelationMapper;
    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.admin}")
    private String REDIS_KEY_ADMIN;
    @Value("${redis.key.resourceList}")
    private String REDIS_KEY_RESOUNCE_LIST;
    @Override
    public void delAdmin(Long adminId) {
        UmsAdmin admin = adminService.getItem(adminId);
        if(admin != null){
            String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
            redisService.del(key);
        }
    }

    @Override
    public void delResourceList(Long adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOUNCE_LIST + ":" + adminId;
        redisService.del(key);

    }

    @Override
    public void delResourceListByRole(Long roleId) {
        UmsAdminRoleRelationExample example = new UmsAdminRoleRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<UmsAdminRoleRelation> list = adminRoleRelationMapper.selectByExample(example);
        if(CollUtil.isNotEmpty(list)){
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOUNCE_LIST + ":";
            List<String> keys = list.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public void delResourceListByRoleIds(List<Long> roleIds) {

    }

    @Override
    public void delResourceListByResource(Long resourceId) {

    }

    @Override
    public UmsAdmin getAdmin(String username) {
        return null;
    }

    @Override
    public void setAdmin(UmsAdmin admin) {

    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        return null;
    }

    @Override
    public void setResourceList(Long adminid, List<UmsResource> resourceList) {

    }
}
