package com.hjy.cloud.t_staff.service;

import com.hjy.cloud.t_staff.entity.TStaffSalary;
import com.hjy.cloud.domin.CommonResult;

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
}
