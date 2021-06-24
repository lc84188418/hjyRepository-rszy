package com.hjy.cloud.t_staff.service;

import com.hjy.cloud.common.entity.DSalaryRecord;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_staff.entity.TStaffSalary;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * (TStaffSalary)表服务接口
 *
 * @author makejava
 * @since 2021-03-01 15:53:17
 */
public interface TStaffSalaryService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tStaffSalary
     * @return
     */
    CommonResult insert(TStaffSalary tStaffSalary);

    /**
     * 修改数据
     *
     * @param tStaffSalary
     * @return
     */
    CommonResult updateByPkId(TStaffSalary tStaffSalary);

    /**
     * 删除数据
     *
     * @param tStaffSalary
     * @return
     */
    CommonResult delete(TStaffSalary tStaffSalary);

    /**
     * 查询所有数据
     *
     * @param param
     * @return
     */
    CommonResult selectAll(String param);

    /**
     * 获取单个数据
     *
     * @param tStaffSalary 实体对象
     * @return
     */
    CommonResult selectById(TStaffSalary tStaffSalary);
    /**
     * 发送工资明细页面
     *
     * @param tStaffSalary 实体对象
     */
    CommonResult sendPage(TStaffSalary tStaffSalary);

    CommonResult send(DSalaryRecord tStaffSalary, HttpServletRequest request);
    /**
     * 工资明细发送记录
     * 管理员
     */
    CommonResult adminSendRecord(String param) throws ParseException;
    /**
     * 工资明细发送记录
     * 员工
     */
    CommonResult staffSendRecord(String param,HttpServletRequest request) throws ParseException;
    /**
     * 工资明细记录获取详情
     *
     * @param salaryRecord 实体对象
     */
    CommonResult selectRecordById(DSalaryRecord salaryRecord);
    /**
     * 工资明细记录员工确认
     *
     * @param salaryRecord 实体对象
     */
    CommonResult recordConfirm(DSalaryRecord salaryRecord);
}
