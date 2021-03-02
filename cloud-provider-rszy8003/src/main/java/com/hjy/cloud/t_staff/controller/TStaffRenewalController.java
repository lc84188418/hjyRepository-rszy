package com.hjy.cloud.t_staff.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_staff.entity.TStaffRenewal;
import com.hjy.cloud.t_staff.service.TStaffRenewalService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TStaffRenewal)表控制层
 *
 * @author makejava
 * @since 2021-03-01 15:53:17
 */
@RestController
public class TStaffRenewalController {
    /**
     * 服务对象
     */
    @Resource
    private TStaffRenewalService tStaffRenewalService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/staff/renewal/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tStaffRenewalService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tStaffRenewal 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/staff/renewal/add")
    public CommonResult insert(@RequestBody TStaffRenewal tStaffRenewal) throws FebsException {
        try {
            return tStaffRenewalService.insert(tStaffRenewal);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffRenewal 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/staff/renewal/del")
    public CommonResult delete(@RequestBody TStaffRenewal tStaffRenewal) throws FebsException {
        try {
            return tStaffRenewalService.delete(tStaffRenewal);
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
    @PostMapping(value = "/staff/renewal/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tStaffRenewalService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tStaffRenewal 实体对象
     */
    @PostMapping(value = "/staff/renewal/get")
    public CommonResult selectOne(@RequestBody TStaffRenewal tStaffRenewal) throws FebsException {
        try {
            return tStaffRenewalService.selectById(tStaffRenewal);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffRenewal 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/staff/renewal/update")
    public CommonResult update(@RequestBody TStaffRenewal tStaffRenewal) throws FebsException {
        try {
            return tStaffRenewalService.updateByPkId(tStaffRenewal);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
