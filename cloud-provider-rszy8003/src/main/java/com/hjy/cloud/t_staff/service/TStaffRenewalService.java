package com.hjy.cloud.t_staff.service;

import com.hjy.cloud.t_staff.entity.TStaffRenewal;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TStaffRenewal)表服务接口
 *
 * @author makejava
 * @since 2021-03-01 15:53:17
 */
public interface TStaffRenewalService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tStaffRenewal
     * @return
     */
    CommonResult insert(TStaffRenewal tStaffRenewal);

    /**
     * 修改数据
     *
     * @param tStaffRenewal
     * @return
     */
    CommonResult updateByPkId(TStaffRenewal tStaffRenewal);

    /**
     * 删除数据
     *
     * @param tStaffRenewal
     * @return
     */
    CommonResult delete(TStaffRenewal tStaffRenewal);

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
     * @param tStaffRenewal 实体对象
     * @return
     */
    CommonResult selectById(TStaffRenewal tStaffRenewal);
}
