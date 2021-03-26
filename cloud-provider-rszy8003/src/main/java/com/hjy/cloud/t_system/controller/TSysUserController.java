package com.hjy.cloud.t_system.controller;

import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_outfit.service.TOutfitDeptService;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.PasswordEncryptUtils;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.t_system.entity.*;
import com.hjy.cloud.t_system.service.TSysPermsService;
import com.hjy.cloud.t_system.service.TSysRoleService;
import com.hjy.cloud.t_system.service.TSysUserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * (TSysUser)表控制层
 *
 * @author liuchun
 * @since 2020-07-24 17:05:59
 */
@Api(tags = "系统-用户-控制层")
@Slf4j
@RestController
public class TSysUserController {
    /**
     * 服务对象
     */
    @Autowired
    private TSysUserService tSysUserService;
    @Autowired
    private TSysRoleService tSysRoleService;
    @Autowired
    private TOutfitDeptService tOutfitDeptService;
    @Autowired
    private TSysPermsService tSysPermsService;
    @Value("${server.port}")
    private String serverPort;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/system/user/addPage")
    public CommonResult tSysUserAddPage() throws FebsException {
        try {
            //查询所有单位列表
            List<TOutfitDept> depts = tOutfitDeptService.selectAllIdAndName();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("deptList",depts);
            //所有角色信息
            List<TSysRole> roleList = tSysRoleService.selectAll();
            jsonObject.put("roleList",roleList);
            return new CommonResult(200,"success","成功!",jsonObject);
        } catch (Exception e) {
            String message = "失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 1 新增数据，连带角色
     * @param param
     * @return 新增结果
     */
    @RequiresPermissions({"user:add"})
    @PostMapping("/system/user/add")
    public Map<String,Object> tSysUserAdd(@RequestBody String param) throws FebsException{
        try {
            //
            Map<String,Object> result = tSysUserService.insertUserAndRoleAndDept(param);
            return result;
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
    @RequiresPermissions({"user:view"})
    @PostMapping("/system/user/list")
    public CommonResult tSysUserList(@RequestBody String param) throws FebsException{
        try {
            //
            PageResult pageResult= tSysUserService.selectAllPage(param);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("PageResult",pageResult);
            //部门
            List<String> deptName = tOutfitDeptService.selectAllDeptName();
            jsonObject.put("depts",deptName);
            return new CommonResult(200,"success",serverPort+"查询数据成功!",jsonObject);
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
    @RequiresPermissions({"user:del"})
    @DeleteMapping("/system/user/del")
    public CommonResult tSysUserDel(@RequestBody String param) throws FebsException{
        try {
            CommonResult commonResult = tSysUserService.tSysUserDel(param);
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
    @PostMapping("/system/user/getOne")
    public CommonResult tSysUsergetOne(@RequestBody String param) throws FebsException{
        JSONObject jsonObject = JSON.parseObject(param);
        String idStr=String.valueOf(jsonObject.get("pk_id"));
        try {
            //
            TSysUser tSysUser = tSysUserService.selectById(idStr);
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("tSysUser",tSysUser);
            List<TSysRole> roles = tSysRoleService.selectAll();
            jsonObject2.put("roles",roles);
            String role = tSysRoleService.selectRoleIdByUserId(idStr);
            jsonObject2.put("roleId",role);
//            List<TOutfitDept> depts = tOutfitDeptService.selectAll(param);
//            jsonObject2.put("depts",depts);
            String deptId = tOutfitDeptService.selectDeptIdByUserId(idStr);
            jsonObject2.put("deptId",deptId);
            return new CommonResult(200,"success","数据获取成功!",jsonObject2);
        } catch (Exception e) {
            String message = "数据获取失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 4 修改数据
     * @param param 实体对象
     * @return 修改结果
     */
    @RequiresPermissions({"user:update"})
    @PutMapping("/system/user/admin/update")
    public CommonResult tSysUserAdminUpdate(@RequestBody String param) throws FebsException{
        try {
            //
            int i = tSysUserService.updateUser(param);
            if(i >= 1){
                return new CommonResult(200,"success","修改成功!",null);
            }else {
                return new CommonResult(445,"error","未分配角色，无法修改!",null);
            }
        } catch (Exception e) {
            String message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 4 完善个人信息
     * @param tSysUser 实体对象
     * @return 修改结果
     */
    @PutMapping("/system/user/update")
    public CommonResult tSysUserUpdate(@RequestBody TSysUser tSysUser) throws FebsException{
        try {
            //
            tSysUserService.updateById(tSysUser);
            return new CommonResult(200,"success","修改成功!",null);
        } catch (Exception e) {
            String message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 5 分配角色UI
     */
    @PostMapping(value = "/system/user/distributeRoleUI")
    public CommonResult roleDistributePage(@RequestBody String parm)throws FebsException{
        JSONObject json = JSON.parseObject(parm);
        String fkUserId=String.valueOf(json.get("pk_id"));
        JSONObject jsonObject = new JSONObject();
        try {
            //用户信息
            TSysUser user = tSysUserService.selectById(fkUserId);
            jsonObject.put("FPUser",user);
            //所有角色信息
            List<TSysRole> roleList = tSysRoleService.selectAll();
            jsonObject.put("roleList",roleList);
            //已分配角色信息
            String fk_role_id = tSysUserService.selectUserRoleByUserId(fkUserId);
            jsonObject.put("FPRoleId",fk_role_id);
            return new CommonResult(200,"success","角色数据获取成功!",jsonObject);
        } catch (Exception e) {
            String message = "用户或角色数据获取失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 5 分配角色
     */
    @RequiresPermissions({"user:distributeRole"})
    @PostMapping(value = "/system/user/distributeRole")
    public CommonResult roleDistribute(@RequestBody String parm) throws FebsException{
        JSONObject json = JSON.parseObject(parm);
        String fk_user_id=String.valueOf(json.get("fk_user_id"));
        String fk_role_id=String.valueOf(json.get("fk_role_id"));
        ReUserRole userRole  = new ReUserRole();
        userRole.setPk_userRole_id(IDUtils.getUUID());
        userRole.setFk_user_id(fk_user_id);
        userRole.setFk_role_id(fk_role_id);
        try {
            //删除原有用户角色数据
            tSysUserService.deleteUserRoleByUserId(fk_user_id);
            //添加用户角色信息
            tSysRoleService.addUserRoleByUserRole(userRole);
            return new CommonResult(200,"success","角色分配成功!",null);
        } catch (Exception e) {
            String message = "角色分配失败！";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 6 重置密码
     * @param parm 参数
     * @return 修改结果
     */
    @RequiresPermissions({"user:resetPassword"})
    @PutMapping("/system/user/resetPassword")
    public CommonResult resetPassword(@RequestBody String parm) throws FebsException{
        JSONObject json = JSON.parseObject(parm);
        String pkUserId=String.valueOf(json.get("pk_id"));
        String username=String.valueOf(json.get("username"));
        try {
            //
            TSysUser tSysUser = new TSysUser();
            tSysUser.setPkUserId(pkUserId);
            tSysUser.setModifyTime(new Date());
            tSysUser.setPassword(PasswordEncryptUtils.MyPasswordEncryptUtil(username,"123456"));
            tSysUserService.updateById(tSysUser);
            return new CommonResult(200,"success","重置密码成功!",null);
        } catch (Exception e) {
            String message = "重置密码失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 7 修改密码
     * @return 修改结果
     */
    @PutMapping("/system/user/updatePassword")
    public CommonResult updatePassword(HttpSession session,@RequestBody String parm) throws FebsException{
        ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
        try {
            //
            int i = tSysUserService.updatePassword(parm,activeUser);
            if (i==2) {
                return new CommonResult(444,"error","旧密码错误!",null);
            }else {
                return new CommonResult(200,"success","修改密码成功!",null);
            }
        } catch (Exception e) {
            String message = "修改密码失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}