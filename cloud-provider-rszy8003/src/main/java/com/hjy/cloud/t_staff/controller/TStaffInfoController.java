package com.hjy.cloud.t_staff.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.t_staff.service.TStaffInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TStaffInfo)表控制层
 *
 * @author makejava
 * @since 2021-02-25 17:06:48
 */
@RestController
public class TStaffInfoController {
    /**
     * 服务对象
     */
    @Resource
    private TStaffInfoService tStaffInfoService;

    /**
     * 删除数据
     *
     * @param tStaffInfo 实体对象
     * @return 删除结果
     */
    @OperLog(operModul = "人员管理-名单管理",operType = "删除",operDesc = "删除入职员工信息记录")
    @RequiresPermissions({"staffInfo:del"})
    @DeleteMapping(value = "/staff/info/del")
    public CommonResult delete(@RequestBody TStaffInfo tStaffInfo) throws FebsException {
        try {
            return tStaffInfoService.delete(tStaffInfo);
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
    @OperLog(operModul = "人员管理-名单管理",operType = "查看",operDesc = "查看入职员工信息列表")
    @RequiresPermissions({"staffInfo:view"})
    @PostMapping(value = "/staff/info/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tStaffInfoService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tStaffInfo 实体对象
     */
    @OperLog(operModul = "人员管理-名单管理",operType = "查看",operDesc = "查看单个入职员工信息详情")
    @RequiresPermissions({"staffInfo:get"})
    @PostMapping(value = "/staff/info/get")
    public CommonResult selectOne(@RequestBody TStaffInfo tStaffInfo) throws FebsException {
        try {
            return tStaffInfoService.selectById(tStaffInfo);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffInfo 实体对象
     * @return 修改结果
     */
    @OperLog(operModul = "人员管理-名单管理",operType = "修改",operDesc = "修改入职员工信息")
    @RequiresPermissions({"staffInfo:update"})
    @PutMapping(value = "/staff/info/update")
    public CommonResult update(@RequestBody TStaffInfo tStaffInfo) throws FebsException {
        try {
            return tStaffInfoService.updateByPkId(tStaffInfo);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
