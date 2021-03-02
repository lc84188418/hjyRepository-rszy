package com.hjy.cloud.t_staff.service;

import com.hjy.cloud.t_staff.entity.TStaffReassign;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TStaffReassign)表服务接口
 *
 * @author makejava
 * @since 2021-03-01 15:53:16
 */
public interface TStaffReassignService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tStaffReassign
     * @return
     */
    CommonResult insert(TStaffReassign tStaffReassign);

    /**
     * 修改数据
     *
     * @param tStaffReassign
     * @return
     */
    CommonResult updateByPkId(TStaffReassign tStaffReassign);

    /**
     * 删除数据
     *
     * @param tStaffReassign
     * @return
     */
    CommonResult delete(TStaffReassign tStaffReassign);

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
     * @param tStaffReassign 实体对象
     * @return
     */
    CommonResult selectById(TStaffReassign tStaffReassign);
}
