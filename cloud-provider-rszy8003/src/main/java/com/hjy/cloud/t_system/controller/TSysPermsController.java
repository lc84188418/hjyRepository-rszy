package com.hjy.cloud.t_system.controller;

import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_system.entity.ActiveUser;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.t_system.entity.TSysPerms;
import com.hjy.cloud.t_system.service.TSysPermsService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * (TSysPerms)表控制层
 *
 * @author liuchun
 * @since 2020-07-24 09:55:41
 */
@Api(tags = "系统-权限-控制层")
@Slf4j
@RestController
public class TSysPermsController {
    /**
     * 服务对象
     */
    @Autowired
    private TSysPermsService tSysPermsService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/system/perms/addPage")
    public CommonResult tSysPermsAddPage() throws FebsException {
        try {
            //查找所有权限
            List<TSysPerms> permsList = tSysPermsService.selectAllIdAndName();
            return new CommonResult(200,"success","成功!",permsList);
        } catch (Exception e) {
            String message = "失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 1 新增数据
     * @param tSysPerms 实体对象
     * @return 新增结果
     */
    //@RequiresPermissions({"perms:add"})
    @PostMapping("/system/perms/add")
    public CommonResult tSysPermsAdd(@RequestBody TSysPerms tSysPerms, HttpSession session) throws FebsException{

        try {
            //
            tSysPermsService.insert(session,tSysPerms);
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
    //@RequiresPermissions({"perms:view"})
    @PostMapping("/system/perms/list")
    public CommonResult tSysPermsList(@RequestBody String param) throws FebsException{
        try {
            //
            PageResult pageResult= tSysPermsService.selectAllPage(param);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("PageResult",pageResult);
            return new CommonResult(200,"success","查询数据成功!",jsonObject);
        } catch (Exception e) {
            String message = "查询数据失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 2 通过实体查询所有数据
     * @return 所有数据
     */
    @GetMapping("/system/perms/listByEntity")
    public CommonResult tSysPermsListByEntity(@RequestBody TSysPerms tSysPerms) throws FebsException{
        try {
            //
            List<TSysPerms> tSysPermsList = tSysPermsService.selectAllByEntity(tSysPerms);
            return new CommonResult(200,"success","查询数据成功!",tSysPermsList);
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
    //@RequiresPermissions({"perms:del"})
    @DeleteMapping("/system/perms/del")
    public CommonResult tSysPermsDel(@RequestBody String parm) throws FebsException{
        JSONObject jsonObject = JSON.parseObject(parm);
        String idStr=String.valueOf(jsonObject.get("pk_id"));
        try {
            //
            tSysPermsService.deleteById(idStr);
            return new CommonResult(200,"success","数据删除成功!",null);
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
    @PostMapping("/system/perms/getOne")
    public CommonResult tSysPermsgetOne(@RequestBody String parm) throws FebsException{
        JSONObject jsonObject = JSON.parseObject(parm);
        String idStr=String.valueOf(jsonObject.get("pk_id"));
        try {
            //
            JSONObject jsonResult = new JSONObject();
            TSysPerms tSysPerms = tSysPermsService.selectById(idStr);
            jsonResult.put("perms",tSysPerms);
            //查找所有权限
            List<TSysPerms> permsList = tSysPermsService.selectAllIdAndName();
            jsonResult.put("permsList",permsList);
            return new CommonResult(200,"success","数据获取成功!",jsonResult);
        } catch (Exception e) {
            String message = "数据获取失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 4 修改数据
     * @param tSysPerms 实体对象
     * @return 修改结果
     */
    //@RequiresPermissions({"perms:update"})
    @PutMapping("/system/perms/update")
    public CommonResult tSysPermsUpdate(@RequestBody TSysPerms tSysPerms,HttpSession session) throws FebsException{
        ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
        try {
            //
            tSysPermsService.updateById(tSysPerms,activeUser);
            return new CommonResult(200,"success","修改成功!",null);
        } catch (Exception e) {
            String message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

}