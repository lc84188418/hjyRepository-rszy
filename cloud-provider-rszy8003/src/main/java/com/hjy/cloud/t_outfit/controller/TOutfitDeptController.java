package com.hjy.cloud.t_outfit.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_outfit.service.TOutfitDeptService;
import com.hjy.cloud.t_system.entity.TSysUser;
import com.hjy.cloud.t_system.service.TSysUserService;
import com.hjy.cloud.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TOutfitDept)表控制层
 *
 * @author makejava
 * @since 2021-02-23 00:07:07
 */
@Slf4j
@RestController
public class TOutfitDeptController {
    /**
     * 服务对象
     */
    @Resource
    private TOutfitDeptService tOutfitDeptService;
    @Autowired
    private TSysUserService tSysUserService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/system/dept/addPage")
    public CommonResult tOutfitDeptAddPage() throws FebsException {
        try {
            //
            List<TOutfitDept> list = tOutfitDeptService.selectAllIdAndName();
            return new CommonResult(200,"success","成功!",list);
        } catch (Exception e) {
            String message = "失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 1 新增数据
     * @param tOutfitDept 实体对象
     * @return 新增结果
     */
    @RequiresPermissions({"dept:add"})
    @PostMapping("/system/dept/add")
    public CommonResult tOutfitDeptAdd(@RequestBody TOutfitDept tOutfitDept) throws FebsException{
        try {
            //
            tOutfitDeptService.insert(tOutfitDept);
            return new CommonResult(200,"success","数据添加成功!",null);
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
    @RequiresPermissions({"dept:view"})
    @PostMapping("/system/dept/list")
    public CommonResult tOutfitDeptList() throws FebsException{
        try {
            //
            List<TOutfitDept> tOutfitDeptList = tOutfitDeptService.selectAll();
            return new CommonResult(200,"success","查询数据成功!",tOutfitDeptList);
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
    @RequiresPermissions({"dept:del"})
    @DeleteMapping("/system/dept/del")
    public CommonResult tOutfitDeptDel(@RequestBody String param) throws FebsException{
        JSONObject jsonObject = JSON.parseObject(param);
        String idStr=String.valueOf(jsonObject.get("pk_id"));
        try {
            //
            tOutfitDeptService.deleteById(idStr);
            return new CommonResult(200,"success","数据删除成功!",null);
        } catch (Exception e) {
            String message = "数据删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 6 给部门下发用户
     */
    @PostMapping("/system/dept/addUserUI")
    public CommonResult systemRoleAddUserUI(@RequestBody String parm) throws FebsException{
        JSONObject json = JSON.parseObject(parm);
        String deptIdStr=String.valueOf(json.get("fk_dept_id"));
        JSONObject jsonObject = new JSONObject();
        try {
            //通过deptIdStr查找部门
            TOutfitDept tOutfitDept = tOutfitDeptService.selectById(deptIdStr);
            jsonObject.put("dept",tOutfitDept);
            //查找所有用户
            List<TSysUser> tSysUserList = tSysUserService.selectAll();
            jsonObject.put("userList",tSysUserList);
            //查询已分配的用户部门并进行回显
            List<String> deptUserList = tOutfitDeptService.selectDeptUser_userIded();
            List<String> deptUserList2 = tOutfitDeptService.selectDeptUserByDept(deptIdStr);
            jsonObject.put("ids",deptUserList);
            jsonObject.put("idsFP",deptUserList2);
            return new CommonResult(200,"success","获取部门已分配用户成功!",jsonObject);
        } catch (Exception e) {
            String message = "获取部门已分配用户失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 6 给部门下发用户
     */
    @RequiresPermissions({"dept:addUser"})
    @PostMapping("/system/dept/addUser")
    public CommonResult systemRoleAddUser(@RequestBody String param) throws FebsException{
        try {
            return tOutfitDeptService.addUser(param);
        } catch (Exception e) {
            String message = "部门添加用户失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 4 通过主键查询单条数据
     * @return 单条数据
     */
    @PostMapping("/system/dept/getOne")
    public CommonResult tOutfitDeptgetOne(@RequestBody String param) throws FebsException{
        JSONObject json = JSON.parseObject(param);
        String idStr= JsonUtil.getStringParam(json,"pk_id");
        try {
            //
            JSONObject jsonObject = new JSONObject();
            TOutfitDept tOutfitDept = tOutfitDeptService.selectById(idStr);
            jsonObject.put("dept",tOutfitDept);
            List<TOutfitDept> list = tOutfitDeptService.selectAllIdAndName();
            jsonObject.put("depts",list);
            return new CommonResult(200,"success","数据获取成功!",jsonObject);
        } catch (Exception e) {
            String message = "数据获取失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 4 修改数据
     * @param tOutfitDept 实体对象
     * @return 修改结果
     */
    @RequiresPermissions({"dept:update"})
    @PutMapping("/system/dept/update")
    public CommonResult tOutfitDeptUpdate(@RequestBody TOutfitDept tOutfitDept) throws FebsException{
        try {
            //
            tOutfitDeptService.updateById(tOutfitDept);
            TOutfitDept tOutfitDept2 = tOutfitDeptService.selectById(tOutfitDept.getPkDeptId());
            return new CommonResult(200,"success","修改成功!",tOutfitDept2);
        } catch (Exception e) {
            String message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

}