package com.hjy.cloud.t_staff.controller;


import com.hjy.cloud.common.entity.DSalaryRecord;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_staff.entity.TStaffSalary;
import com.hjy.cloud.t_staff.service.TStaffSalaryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * (TStaffSalary)表控制层
 * 工资条管理
 * @author makejava
 * @since 2021-03-01 15:53:17
 */
@RestController
public class TStaffSalaryController {
    /**
     * 服务对象
     */
    @Resource
    private TStaffSalaryService tStaffSalaryService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/staff/salary/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tStaffSalaryService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tStaffSalary 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/staff/salary/add")
    public CommonResult insert(@RequestBody TStaffSalary tStaffSalary) throws FebsException {
        try {
            return tStaffSalaryService.insert(tStaffSalary);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffSalary 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/staff/salary/del")
    public CommonResult delete(@RequestBody TStaffSalary tStaffSalary) throws FebsException {
        try {
            return tStaffSalaryService.delete(tStaffSalary);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 分页查询所有数据
     * 只有相应管理员可查看
     * @param param json参数
     * @return 所有数据
     */
    @PostMapping(value = "/staff/salary/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tStaffSalaryService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tStaffSalary 实体对象
     */
    @PostMapping(value = "/staff/salary/get")
    public CommonResult selectOne(@RequestBody TStaffSalary tStaffSalary) throws FebsException {
        try {
            return tStaffSalaryService.selectById(tStaffSalary);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffSalary 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/staff/salary/update")
    public CommonResult update(@RequestBody TStaffSalary tStaffSalary) throws FebsException {
        try {
            return tStaffSalaryService.updateByPkId(tStaffSalary);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 发送工资明细页面
     *
     * @param tStaffSalary 实体对象
     */
    @PostMapping(value = "/staff/salary/sendPage")
    public CommonResult sendPage(@RequestBody TStaffSalary tStaffSalary) throws FebsException {
        try {
            return tStaffSalaryService.sendPage(tStaffSalary);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }
    /**
     * 发送工资明细
     * @param tStaffSalary 实体对象
     */
    @PostMapping(value = "/staff/salary/send")
    public CommonResult send(@RequestBody DSalaryRecord tStaffSalary, HttpServletRequest request) throws FebsException {
        try {
            return tStaffSalaryService.send(tStaffSalary,request);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }
    /**
     * 发送工资明细
     * 管理员
     */
    @PostMapping(value = "/staff/salary/admin/sendRecord")
    public CommonResult adminSendRecord(@RequestBody String param) throws FebsException {
        try {
            return tStaffSalaryService.adminSendRecord(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }
    /**
     * 发送工资明细
     * 员工
     */
    @PostMapping(value = "/staff/salary/staff/sendRecord")
    public CommonResult staffSendRecord(@RequestBody String param,HttpServletRequest request) throws FebsException {
        try {
            return tStaffSalaryService.staffSendRecord(param,request);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }
}
