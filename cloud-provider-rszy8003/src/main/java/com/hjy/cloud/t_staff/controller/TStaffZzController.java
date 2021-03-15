package com.hjy.cloud.t_staff.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_staff.entity.TStaffZz;
import com.hjy.cloud.t_staff.service.TStaffZzService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * (TStaffZz)表控制层
 *
 * @author makejava
 * @since 2021-03-01 15:53:18
 */
@RestController
public class TStaffZzController {
    /**
     * 服务对象
     */
    @Resource
    private TStaffZzService tStaffZzService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/staff/Zz/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tStaffZzService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tStaffZz 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/staff/Zz/add")
    public CommonResult insert(@RequestBody TStaffZz tStaffZz) throws FebsException {
        try {
            return tStaffZzService.insert(tStaffZz);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffZz 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/staff/Zz/del")
    public CommonResult delete(@RequestBody TStaffZz tStaffZz) throws FebsException {
        try {
            return tStaffZzService.delete(tStaffZz);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 分页查询所有数据
     *管理员
     * @param param json参数
     * @return 所有数据
     */
    @OperLog(operModul = "人员管理-人员变动",operType = "查看",operDesc = "查看员工转正信息列表")
    @RequiresPermissions({"Zz:adminView"})
    @PostMapping(value = "/staff/Zz/adminList")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tStaffZzService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tStaffZz 实体对象
     */
    @PostMapping(value = "/staff/Zz/get")
    public CommonResult selectOne(@RequestBody TStaffZz tStaffZz) throws FebsException {
        try {
            return tStaffZzService.selectById(tStaffZz);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffZz 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/staff/Zz/update")
    public CommonResult update(@RequestBody TStaffZz tStaffZz) throws FebsException {
        try {
            return tStaffZzService.updateByPkId(tStaffZz);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }
    /**
     * 发起转正审批页面
     * 员工
     * @return 修改结果
     */
    @OperLog(operModul = "人员管理-入职管理",operType = "发起转正",operDesc = "员工发起转正申请")
    @GetMapping(value = "/staff/entry/initiateZzPage")
    public CommonResult initiateZzPage(HttpServletRequest request) throws FebsException {
        try {
            return tStaffZzService.initiateZzPage(request);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }
//    /**
//     * 发起转正审批页面
//     * 管理员
//     * @return 修改结果
//     */
//    @OperLog(operModul = "人员管理-入职管理",operType = "发起转正",operDesc = "员工发起转正申请")
//    @PostMapping(value = "/staff/entry/initiateZzPage2")
//    public CommonResult initiateZzPage2(HttpServletRequest request) throws FebsException {
//        try {
//            return tStaffZzService.initiateZzPage(request);
//        } catch (Exception e) {
//            String message = "失败";
//            throw new FebsException(message);
//        }
//    }
    /**
     * 发起转正审批
     *
     * @return 修改结果
     */
    @OperLog(operModul = "人员管理-入职管理",operType = "发起转正",operDesc = "员工发起转正申请")
    @PostMapping(value = "/staff/entry/initiateZz")
    public CommonResult initiateZz(HttpServletRequest request,@RequestBody String param) throws FebsException {
        try {
            return tStaffZzService.initiateZz(request,param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }
}
