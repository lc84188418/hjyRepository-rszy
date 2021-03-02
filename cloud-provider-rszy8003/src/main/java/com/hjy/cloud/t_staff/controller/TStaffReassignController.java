package com.hjy.cloud.t_staff.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_staff.entity.TStaffReassign;
import com.hjy.cloud.t_staff.service.TStaffReassignService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TStaffReassign)表控制层
 *
 * @author makejava
 * @since 2021-03-01 15:53:16
 */
@RestController
public class TStaffReassignController {
    /**
     * 服务对象
     */
    @Resource
    private TStaffReassignService tStaffReassignService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/staff/reassign/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tStaffReassignService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tStaffReassign 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/staff/reassign/add")
    public CommonResult insert(@RequestBody TStaffReassign tStaffReassign) throws FebsException {
        try {
            return tStaffReassignService.insert(tStaffReassign);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffReassign 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/staff/reassign/del")
    public CommonResult delete(@RequestBody TStaffReassign tStaffReassign) throws FebsException {
        try {
            return tStaffReassignService.delete(tStaffReassign);
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
    @PostMapping(value = "/staff/reassign/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tStaffReassignService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tStaffReassign 实体对象
     */
    @PostMapping(value = "/staff/reassign/get")
    public CommonResult selectOne(@RequestBody TStaffReassign tStaffReassign) throws FebsException {
        try {
            return tStaffReassignService.selectById(tStaffReassign);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffReassign 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/staff/reassign/update")
    public CommonResult update(@RequestBody TStaffReassign tStaffReassign) throws FebsException {
        try {
            return tStaffReassignService.updateByPkId(tStaffReassign);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
