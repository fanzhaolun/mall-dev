package com.fzl.mall.controller;

import cn.hutool.core.collection.CollUtil;
import com.fzl.mall.common.api.CommonPage;
import com.fzl.mall.common.api.CommonResult;
import com.fzl.mall.dto.UmsAdminLoginParam;
import com.fzl.mall.dto.UmsAdminParam;
import com.fzl.mall.dto.UpdateAdminPasswordParam;
import com.fzl.mall.model.UmsAdmin;
import com.fzl.mall.model.UmsRole;
import com.fzl.mall.service.UmsAdminService;
import com.fzl.mall.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 后台用户管理
 */
@Controller
@Api(tags = "UmsAdminController", description = "后台用户管理")
@RequestMapping("/admin")
public class UmsAdminController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsAdminService umsAdminService;
    @Autowired
    private UmsRoleService roleService;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<UmsAdmin> register(@Validated @RequestBody UmsAdminParam umsAdminParam){
        UmsAdmin umsAdmin = umsAdminService.register(umsAdminParam);
        if(umsAdmin == null){
            return CommonResult.failed();
        }
        return CommonResult.success(umsAdmin);
    }

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@Validated @RequestBody UmsAdminLoginParam umsAdminLoginParam){
        String token = umsAdminService.login(umsAdminLoginParam.getUsername(),umsAdminLoginParam.getPassword());
        if(token == null){
            return CommonResult.failed("用户名或密码错误");
        }
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/refreshToken",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult refreshToken(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String refreshToken = umsAdminService.refreshToken(token);
        if(refreshToken == null){
            return CommonResult.failed("token已过期");
        }
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",refreshToken);
        tokenMap.put("tokenHead",tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getAdminInfo(Principal principal){
        if(principal == null){
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        UmsAdmin umsAdmin = umsAdminService.getAdminByUsername(username);
        Map<String,Object> data = new HashMap<>();
        data.put("username",umsAdmin.getUsername());
        data.put("menus",roleService.getMenuList(umsAdmin.getId()));
        data.put("icon",umsAdmin.getIcon());
        List<UmsRole> roleList = umsAdminService.getRoleList(umsAdmin.getId());
        if(CollUtil.isNotEmpty(roleList)){
            List<String> roles = roleList.stream().map(UmsRole::getName).collect(Collectors.toList());
            data.put("roles",roles);
        }
        return CommonResult.success(data);
    }

    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult logout(){
        return CommonResult.success(null);
    }

    @ApiOperation(value = "根据用户名或者姓名分页获取用户列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<UmsAdmin>> list(@RequestParam(value = "keyword",required = false) String keyword,
                                                   @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum){
        List<UmsAdmin> adminList = umsAdminService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(adminList));
    }

    @ApiOperation(value = "获取指定用户信息")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<UmsAdmin> getItem(@PathVariable(value = "id") Long id){
        UmsAdmin admin = umsAdminService.getItem(id);
        return CommonResult.success(admin);
    }

    @ApiOperation(value = "修改指定用户信息")
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable(value = "id") Long id,@RequestBody UmsAdmin umsAdmin){
        int count = umsAdminService.update(id, umsAdmin);
        if(count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation(value = "修改指定用户密码")
    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updatePassword(@Validated @RequestBody UpdateAdminPasswordParam updateAdminPasswordParam){
        int status = umsAdminService.updatePassword(updateAdminPasswordParam);
        if(status > 0){
            return CommonResult.success(status);
        }else if(status == -1){
            return CommonResult.failed("提交参数不合法");
        }else if(status == -2){
            return CommonResult.failed("找不到该用户");
        }else if(status == -3){
            return CommonResult.failed("旧密码错误");
        }else{
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "删除指定用户信息")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@PathVariable(value = "id") Long id){
        int count = umsAdminService.delete(id);
        if(count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation(value = "修改账号状态")
    @RequestMapping(value = "/updateStatus/{id}",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateStatus(@PathVariable Long id,@RequestParam(value = "status") Integer status){
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setStatus(status);
        int count = umsAdminService.update(id, umsAdmin);
        if(count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();

    }

    @ApiOperation(value = "获取指定用户的角色")
    @RequestMapping(value = "/role/{adminId}",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<UmsRole>> getRoleList(@PathVariable Long adminId){
        List<UmsRole> roleList = umsAdminService.getRoleList(adminId);
        return CommonResult.success(roleList);
    }
}
