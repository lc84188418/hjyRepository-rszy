package com.hjy.cloud.t_system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.cloud.common.entity.User;
import com.hjy.cloud.common.task.ObjectAsyncTask;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_outfit.service.TOutfitDeptService;
import com.hjy.cloud.t_system.entity.ReUserRole;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.t_system.entity.TSysRole;
import com.hjy.cloud.t_system.entity.TSysUser;
import com.hjy.cloud.t_system.service.TSysPermsService;
import com.hjy.cloud.t_system.service.TSysRoleService;
import com.hjy.cloud.t_system.service.TSysUserService;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.page.PageRequest;
import com.hjy.cloud.utils.page.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
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
    //@RequiresPermissions({"user:add"})
    @PostMapping("/system/user/add")
    public Map<String,Object> tSysUserAdd(@RequestBody String param) throws FebsException{
        try {
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
    @ApiOperation(value = "用户列表-已完成", notes = "查询所有用户列表,支持条件username、enableStatus、idcard、fullName")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页码",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "pageSize",value = "条数",required = false,dataType = "int",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "param",value = "查询参数里面才是放的条件",required = false,dataType = "object",paramType = "body",example = "param:{\"username\":\"admin\"}")
    })
    //@RequiresPermissions({"user:view"})
    @PostMapping("/system/user/list")
    public CommonResult<PageResult<TSysUser>> tSysUserList(PageRequest<TSysUser> pageInfo) throws FebsException{
        try {
            return tSysUserService.tSysUserList(pageInfo);
        } catch (Exception e) {
            String message = "查询数据失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @ApiOperation(value = "模糊查询所有用户-已完成", notes = "可通过姓名进行查询，未分页，仅返回姓名和id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fullName", value = "姓名",required = false, dataType = "string", paramType = "body", example = "1"),
    })
    @PostMapping("/system/user")
    public CommonResult<List<User>> tSysUserList2(@ApiIgnore()@RequestBody TSysUser param) throws FebsException{
        try {
            //
            List<User> list = tSysUserService.selectAllId_NameByEntity(param);
            return new CommonResult(200,"success","查询数据成功!",list);
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
//    @ApiOperation(value = "删除-已完成", notes = "")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "pkUserId", value = "用户id",required = true, dataType = "string", paramType = "path", example = "1"),
//    })
//    //@RequiresPermissions({"user:del"})
//    @DeleteMapping("/system/user/del/{pkUserId}")
//    public CommonResult tSysUserDel(@PathVariable("pkUserId")String pkUserId) throws FebsException{
//        try {
//            CommonResult commonResult = tSysUserService.tSysUserDel(pkUserId);
//            return commonResult;
//        } catch (Exception e) {
//            String message = "数据删除失败";
//            log.error(message, e);
//            throw new FebsException(message);
//        }
//    }

    /**
     * 4 通过主键查询单条数据
     * @return 单条数据
     */
    @ApiOperation(value = "获取单个用户-已完成", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pkUserId", value = "用户id",required = true, dataType = "string", paramType = "path", example = "1"),
    })
    @GetMapping("/system/user/getOne/{pkUserId}")
    public CommonResult tSysUsergetOne(@PathVariable("pkUserId")String pkUserId) throws FebsException{
        try {
            //
            TSysUser tSysUser = tSysUserService.selectById(pkUserId);
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("tSysUser",tSysUser);
            List<TSysRole> roles = tSysRoleService.selectAll();
            jsonObject2.put("roles",roles);
            String role = tSysRoleService.selectRoleIdByUserId(pkUserId);
            jsonObject2.put("roleId",role);
//            List<TOutfitDept> depts = tOutfitDeptService.selectAll(param);
//            jsonObject2.put("depts",depts);
            String deptId = tOutfitDeptService.selectDeptIdByUserId(pkUserId);
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
    //@RequiresPermissions({"user:update"})
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
     * 4 修改用户名
     * @param tSysUser 实体对象
     * @return 修改结果
     */
    @ApiOperation(value = "个人修改用户名-已完成", notes = "用户修改个人账户名信息username")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "页码",required = false,dataType = "string",paramType = "body",example = "1"),
    })
    @PutMapping("/system/user/update")
    public CommonResult tSysUserUpdate(@RequestBody TSysUser tSysUser, HttpServletRequest request) throws FebsException{
        try {
            //
            return tSysUserService.updateById(tSysUser,request);
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
    //@RequiresPermissions({"user:distributeRole"})
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
     * @return 修改结果
     */
    @ApiOperation(value = "获取单个用户-已完成", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pkUserId", value = "用户id",required = true, dataType = "string", paramType = "path", example = "1"),
    })
    //@RequiresPermissions({"user:resetPassword"})
    @PutMapping("/system/user/resetPassword")
    public CommonResult resetPassword(@RequestBody TSysUser tSysUser) throws FebsException{
        try {
            return tSysUserService.resetPassword(tSysUser);
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
    @ApiOperation(value = "修改密码-已完成", notes = "测试完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "旧密码",required = true, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "newPassword", value = "新密码",required = true, dataType = "string", paramType = "body", example = "1"),
    })
    @PutMapping("/system/user/updatePassword")
    public CommonResult updatePassword(HttpServletRequest request,@ApiIgnore() @RequestBody String parm) throws FebsException{
        SysToken token = ObjectAsyncTask.getSysToken(request);
        try {
            //
            int i = tSysUserService.updatePassword(parm,token);
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