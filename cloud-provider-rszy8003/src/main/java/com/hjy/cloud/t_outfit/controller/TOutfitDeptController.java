package com.hjy.cloud.t_outfit.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_outfit.service.TOutfitDeptService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TOutfitDept)表控制层
 *
 * @author makejava
 * @since 2021-02-24 21:13:39
 */
@Api(tags = "部门控制层")
@Slf4j
@RestController
public class TOutfitDeptController {
    /**
     * 服务对象
     */
    @Resource
    private TOutfitDeptService tOutfitDeptService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/outfit/dept/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tOutfitDeptService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tOutfitDept 实体对象
     * @return 新增结果
     */
    @OperLog(operModul = "机构管理-部门管理",operType = "添加",operDesc = "新增部门基本信息")
    //@RequiresPermissions({"dept:add"})
    @PostMapping(value = "/outfit/dept/add")
    public CommonResult insert(@RequestBody TOutfitDept tOutfitDept) throws FebsException {
        try {
            return tOutfitDeptService.insert(tOutfitDept);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tOutfitDept 实体对象
     * @return 删除结果
     */
    @OperLog(operModul = "机构管理-部门管理",operType = "删除",operDesc = "删除部门基本信息")
    //@RequiresPermissions({"dept:del"})
    @DeleteMapping(value = "/outfit/dept/del")
    public CommonResult delete(@RequestBody TOutfitDept tOutfitDept) throws FebsException {
        try {
            return tOutfitDeptService.delete(tOutfitDept);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @OperLog(operModul = "机构管理-部门管理",operType = "查看",operDesc = "查看部门信息列表")
    //@RequiresPermissions({"dept:view"})
    @PostMapping(value = "/outfit/dept/list")
    public CommonResult selectAll() throws FebsException {
        try {
            return tOutfitDeptService.selectAll();
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tOutfitDept 实体对象
     */
    @OperLog(operModul = "机构管理-部门管理",operType = "查看",operDesc = "查看单个部门信息")
    @PostMapping(value = "/outfit/dept/get")
    public CommonResult selectOne(@RequestBody TOutfitDept tOutfitDept) throws FebsException {
        try {
            return tOutfitDeptService.selectById(tOutfitDept);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tOutfitDept 实体对象
     * @return 修改结果
     */
    @OperLog(operModul = "机构管理-部门管理",operType = "修改",operDesc = "修改部门基本信息")
    //@RequiresPermissions({"dept:update"})
    @PutMapping(value = "/outfit/dept/update")
    public CommonResult update(@RequestBody TOutfitDept tOutfitDept) throws FebsException {
        try {
            return tOutfitDeptService.updateByPkId(tOutfitDept);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    /**
     * 给部门下发用户UI
     *
     * @param param json参数
     * @return 修改结果
     */
    @PostMapping("/outfit/dept/addUserUI")
    public CommonResult deptAddUserUI(@RequestBody String param) throws FebsException{
        try {
            return tOutfitDeptService.addUserUI(param);
        } catch (Exception e) {
            String message = "获取部门已分配用户失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 给部门下发用户
     *
     * @param param json参数
     * @return 修改结果
     */
    @OperLog(operModul = "机构管理-部门管理",operType = "添加员工",operDesc = "为该部门添加员工")
    //@RequiresPermissions({"dept:addUser"})
    @PostMapping("/outfit/dept/addUser")
    public CommonResult deptAddUser(@RequestBody String param) throws FebsException{
        try {
            return tOutfitDeptService.addUser(param);
        } catch (Exception e) {
            String message = "部门添加用户失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 合并部门UI
     *
     * @param param json参数
     * @return 修改结果
     */
    @PostMapping("/outfit/dept/mergeUI")
    public CommonResult deptMergeUI(@RequestBody String param) throws FebsException{
        try {
            return tOutfitDeptService.deptMergeUI(param);
        } catch (Exception e) {
            String message = "获取其他部门数据失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 合并部门
     *
     * @param param json参数
     * @return 修改结果
     */
    @OperLog(operModul = "机构管理-部门管理",operType = "合并部门",operDesc = "将两部门合并为一个部门")
    //@RequiresPermissions({"dept:merge"})
    @PostMapping("/outfit/dept/merge")
    public CommonResult deptMerge(@RequestBody String param) throws FebsException{
        try {
            return tOutfitDeptService.deptMerge(param);
        } catch (Exception e) {
            String message = "部门合并失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

}