package com.hjy.cloud.t_staff.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_staff.entity.TStaffContract;
import com.hjy.cloud.t_staff.service.TStaffContractService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TStaffContract)表控制层
 *
 * @author makejava
 * @since 2021-03-01 15:53:14
 */
@RestController
public class TStaffContractController {
    /**
     * 服务对象
     */
    @Resource
    private TStaffContractService tStaffContractService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/staff/contract/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tStaffContractService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tStaffContract 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/staff/contract/add")
    public CommonResult insert(@RequestBody TStaffContract tStaffContract) throws FebsException {
        try {
            return tStaffContractService.insert(tStaffContract);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffContract 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/staff/contract/del")
    public CommonResult delete(@RequestBody TStaffContract tStaffContract) throws FebsException {
        try {
            return tStaffContractService.delete(tStaffContract);
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
    @PostMapping(value = "/staff/contract/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tStaffContractService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tStaffContract 实体对象
     */
    @PostMapping(value = "/staff/contract/get")
    public CommonResult selectOne(@RequestBody TStaffContract tStaffContract) throws FebsException {
        try {
            return tStaffContractService.selectById(tStaffContract);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffContract 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/staff/contract/update")
    public CommonResult update(@RequestBody TStaffContract tStaffContract) throws FebsException {
        try {
            return tStaffContractService.updateByPkId(tStaffContract);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
