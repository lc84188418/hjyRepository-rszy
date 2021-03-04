package com.hjy.cloud.t_index.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_index.entity.TIndexBwl;
import com.hjy.cloud.t_index.service.TIndexBwlService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * (TIndexBwl)表控制层
 *
 * @author makejava
 * @since 2021-03-01 15:50:55
 */
@RestController
public class TIndexBwlController {
    /**
     * 服务对象
     */
    @Resource
    private TIndexBwlService tIndexBwlService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/index/bwl/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tIndexBwlService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tIndexBwl 实体对象
     * @return 新增结果
     */
    @OperLog(operModul = "首页-备忘录管理",operType = "添加",operDesc = "新增备忘录信息")
    @RequiresPermissions({"bwl:add"})
    @PostMapping(value = "/index/bwl/add")
    public CommonResult insert(HttpSession session, @RequestBody TIndexBwl tIndexBwl) throws FebsException {
        try {
            return tIndexBwlService.insert(session,tIndexBwl);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tIndexBwl 实体对象
     * @return 删除结果
     */
    @OperLog(operModul = "首页-备忘录管理",operType = "删除",operDesc = "删除备忘录信息")
    @RequiresPermissions({"bwl:del"})
    @DeleteMapping(value = "/index/bwl/del")
    public CommonResult delete(@RequestBody TIndexBwl tIndexBwl) throws FebsException {
        try {
            return tIndexBwlService.delete(tIndexBwl);
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
    @OperLog(operModul = "首页-备忘录管理",operType = "查看",operDesc = "查看备忘录信息列表")
    @RequiresPermissions({"bwl:adminView"})
    @PostMapping(value = "/index/bwl/admin/list")
    public CommonResult selectAllAdmin(@RequestBody String param) throws FebsException {
        try {
            return tIndexBwlService.selectAll(param);
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
    @OperLog(operModul = "首页-备忘录管理",operType = "查看",operDesc = "查看备忘录信息列表")
    @RequiresPermissions({"bwl:view"})
    @PostMapping(value = "/index/bwl/list")
    public CommonResult selectAll(HttpSession session,@RequestBody String param) throws FebsException {
        try {
            return tIndexBwlService.selectAll(session,param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tIndexBwl 实体对象
     */
    @OperLog(operModul = "首页-备忘录管理",operType = "查看",operDesc = "查看单个备忘录信息")
    @RequiresPermissions({"bwl:get"})
    @PostMapping(value = "/index/bwl/get")
    public CommonResult selectOne(@RequestBody TIndexBwl tIndexBwl) throws FebsException {
        try {
            return tIndexBwlService.selectById(tIndexBwl);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tIndexBwl 实体对象
     * @return 修改结果
     */
    @OperLog(operModul = "首页-备忘录管理",operType = "修改",operDesc = "修改备忘录信息")
    @RequiresPermissions({"bwl:update"})
    @PutMapping(value = "/index/bwl/update")
    public CommonResult update(@RequestBody TIndexBwl tIndexBwl) throws FebsException {
        try {
            return tIndexBwlService.updateByPkId(tIndexBwl);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
