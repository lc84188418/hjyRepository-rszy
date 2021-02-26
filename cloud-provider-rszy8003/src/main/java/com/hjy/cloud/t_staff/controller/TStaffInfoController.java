package com.hjy.cloud.t_staff.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.t_staff.service.TStaffInfoService;
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
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/staff/info/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tStaffInfoService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tStaffInfo 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/staff/info/add")
    public CommonResult insert(@RequestBody TStaffInfo tStaffInfo) throws FebsException {
        try {
            return tStaffInfoService.insert(tStaffInfo);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffInfo 实体对象
     * @return 删除结果
     */
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
