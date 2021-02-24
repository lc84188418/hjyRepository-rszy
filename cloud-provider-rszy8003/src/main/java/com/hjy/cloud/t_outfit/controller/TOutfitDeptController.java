package com.hjy.cloud.t_outfit.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_outfit.service.TOutfitDeptService;
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
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tOutfitDept 实体对象
     * @return 新增结果
     */
    @RequiresPermissions({"dept:add"})
    @PostMapping(value = "/outfit/dept/add")
    public CommonResult insert(@RequestBody TOutfitDept tOutfitDept) throws FebsException {
        try {
            return tOutfitDeptService.insert(tOutfitDept);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tOutfitDept 实体对象
     * @return 删除结果
     */
    @RequiresPermissions({"dept:del"})
    @DeleteMapping(value = "/outfit/dept/del")
    public CommonResult delete(@RequestBody TOutfitDept tOutfitDept) throws FebsException {
        try {
            return tOutfitDeptService.delete(tOutfitDept);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 分页查询所有数据
     *
     * @param param json参数
     * @return 所有数据
     */
    @RequiresPermissions({"dept:view"})
    @PostMapping(value = "/outfit/dept/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tOutfitDeptService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tOutfitDept 实体对象
     */
    @PostMapping(value = "/outfit/dept/get")
    public CommonResult selectOne(@RequestBody TOutfitDept tOutfitDept) throws FebsException {
        try {
            return tOutfitDeptService.selectById(tOutfitDept);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tOutfitDept 实体对象
     * @return 修改结果
     */
    @RequiresPermissions({"dept:update"})
    @PutMapping(value = "/outfit/dept/update")
    public CommonResult update(@RequestBody TOutfitDept tOutfitDept) throws FebsException {
        try {
            return tOutfitDeptService.updateByPkId(tOutfitDept);
        } catch (Exception e) {
            String message = "失败";
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
    @RequiresPermissions({"dept:addUser"})
    @PostMapping("/system/dept/addUser")
    public CommonResult deptAddUser(@RequestBody String param) throws FebsException{
        try {
            return tOutfitDeptService.addUser(param);
        } catch (Exception e) {
            String message = "部门添加用户失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

}