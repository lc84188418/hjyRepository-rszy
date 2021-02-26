package com.hjy.cloud.t_staff.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_staff.entity.TStaffEntry;
import com.hjy.cloud.t_staff.service.TStaffEntryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TStaffEntry)表控制层
 *
 * @author makejava
 * @since 2021-02-26 10:55:27
 */
@RestController
public class TStaffEntryController {
    /**
     * 服务对象
     */
    @Resource
    private TStaffEntryService tStaffEntryService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/staff/entry/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tStaffEntryService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tStaffEntry 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/staff/entry/add")
    public CommonResult insert(@RequestBody TStaffEntry tStaffEntry) throws FebsException {
        try {
            return tStaffEntryService.insert(tStaffEntry);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffEntry 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/staff/entry/del")
    public CommonResult delete(@RequestBody TStaffEntry tStaffEntry) throws FebsException {
        try {
            return tStaffEntryService.delete(tStaffEntry);
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
    @PostMapping(value = "/staff/entry/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tStaffEntryService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tStaffEntry 实体对象
     */
    @PostMapping(value = "/staff/entry/get")
    public CommonResult selectOne(@RequestBody TStaffEntry tStaffEntry) throws FebsException {
        try {
            return tStaffEntryService.selectById(tStaffEntry);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffEntry 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/staff/entry/update")
    public CommonResult update(@RequestBody TStaffEntry tStaffEntry) throws FebsException {
        try {
            return tStaffEntryService.updateByPkId(tStaffEntry);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
