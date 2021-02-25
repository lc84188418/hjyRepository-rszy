package com.hjy.cloud.t_system.controller;

import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_system.entity.TSysPerms;
import com.hjy.cloud.t_system.entity.TSysRole;
import com.hjy.cloud.t_system.entity.TSysUser;
import com.hjy.cloud.t_system.service.TSysPermsService;
import com.hjy.cloud.t_system.service.TSysRoleService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hjy.cloud.t_system.service.TSysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * (TSysRole)表控制层
 *
 * @author liuchun
 * @since 2020-07-23 12:59:10
 */
@Slf4j
@RestController
public class TSysRoleController {
    /**
     * 服务对象
     */
    @Autowired
    private TSysRoleService tSysRoleService;
    @Autowired
    private TSysPermsService tSysPermsService;
    @Autowired
    private TSysUserService tSysUserService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/outfit/role/addPage")
    public CommonResult tSysRoleAddPage() throws FebsException{
        try {
            //
            return new CommonResult(200,"success","成功!",null);
        } catch (Exception e) {
            String message = "失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 1 新增数据
     * @param tSysRole 实体对象
     * @return 新增结果
     */
    @RequiresPermissions({"role:add"})
    @PostMapping("/outfit/role/add")
    public CommonResult tSysRoleAdd(@RequestBody TSysRole tSysRole) throws FebsException{
        try {
            //
            return tSysRoleService.insert(tSysRole);
        } catch (Exception e) {
            String message = "数据添加失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 2 查询所有数据
     * @return 所有数据
     */
    @RequiresPermissions({"role:view"})
    @PostMapping("/outfit/role/list")
    public CommonResult tSysRoleList(@RequestBody String param) throws FebsException {
        try {
            //
            return tSysRoleService.selectAll(param);
        } catch (Exception e) {
            String message = "查询数据失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 3 删除数据
     * @return 删除结果
     */
    @RequiresPermissions({"role:del"})
    @DeleteMapping("/outfit/role/del")
    public CommonResult tSysRoleDel(@RequestBody String param) throws FebsException{
        try {
            CommonResult commonResult = tSysRoleService.roleDel(param);
            return commonResult;
        } catch (Exception e) {
            String message = "数据删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 4 通过主键查询单条数据
     * @return 单条数据
     */
    @PostMapping("/outfit/role/get")
    public CommonResult tSysRoleGetOne(@RequestBody TSysRole tSysRole) throws FebsException{
        try {
            //
            return tSysRoleService.selectByPkId(tSysRole);
        } catch (Exception e) {
            String message = "数据获取失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 4 修改数据
     * @param tSysRole 实体对象
     * @return 修改结果
     */
    @RequiresPermissions({"role:update"})
    @PutMapping("/outfit/role/update")
    public CommonResult tSysRoleUpdate(@RequestBody TSysRole tSysRole) throws FebsException{
        try {
            //
            return tSysRoleService.tSysRoleUpdate(tSysRole);
        } catch (Exception e) {
            String message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 5 给角色分配菜单权限
     */
    @PostMapping("/outfit/role/distributeUI")
    public CommonResult FPRoleMenuUI(@RequestBody String parm) throws FebsException{
        JSONObject json = JSON.parseObject(parm);
        String roleIdStr=String.valueOf(json.get("pk_id"));
        JSONObject jsonObject = new JSONObject();
        try {
            //通过角色id查找角色
            TSysRole role = tSysRoleService.selectById(roleIdStr);
            jsonObject.put("role",role);
            //查找所有权限
            List<TSysPerms> permsList = tSysPermsService.selectAll();
            jsonObject.put("permsList",permsList);
            //查询已分配的菜单并进行回显
            List<String> permsXZList = tSysPermsService.selectDistributeByrole_id(roleIdStr);
            jsonObject.put("ids",permsXZList);
            return new CommonResult(200,"success","获取已分配菜单权限成功!",jsonObject);
        } catch (Exception e) {
            String message = "获取已分配菜单权限失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 5 给角色分配菜单权限
     */
    @RequiresPermissions({"role:distributePerms"})
    @PostMapping("/outfit/role/distribute")
    public CommonResult FPRoleMenu(@RequestBody String parm) throws FebsException{
        JSONObject json = JSON.parseObject(parm);
        String fk_role_id=String.valueOf(json.get("fk_role_id"));
        if(fk_role_id.equals("1595564064909")){
            return new CommonResult(444,"error","超级管理员的权限不可更改!",null);
        }
        JSONArray jsonArray = json.getJSONArray("ids");
        String permsIdsStr = jsonArray.toString();
        List<String> idList = JSONArray.parseArray(permsIdsStr,String.class);
        try {
            tSysRoleService.distributeMenu(fk_role_id,idList);
            return new CommonResult(200,"success","分配菜单权限成功!",null);
        } catch (Exception e) {
            String message = "分配菜单权限失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 6 给角色下发用户
     */
    @PostMapping("/outfit/role/addUserUI")
    public CommonResult systemRoleAddUserUI(@RequestBody String param) throws FebsException{
        try {
            return tSysRoleService.systemRoleAddUserUI(param);
        } catch (Exception e) {
            String message = "获取角色已分配用户失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 6 给角色下发用户
     */
    @RequiresPermissions({"role:addUser"})
    @PostMapping("/outfit/role/addUser")
    public CommonResult systemRoleAddUser(@RequestBody String parm) throws FebsException{
        try {
            CommonResult commonResult = tSysRoleService.systemRoleAddUser(parm);
            return commonResult;
        } catch (Exception e) {
            String message = "角色添加用户失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

}