package com.hjy.cloud.t_staff.service;

import com.hjy.cloud.t_staff.entity.TStaffEntry;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TStaffEntry)表服务接口
 *
 * @author makejava
 * @since 2021-02-26 10:55:26
 */
public interface TStaffEntryService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tStaffEntry
     * @return
     */
    CommonResult insert(TStaffEntry tStaffEntry);

    /**
     * 修改数据
     *
     * @param tStaffEntry
     * @return
     */
    CommonResult updateByPkId(TStaffEntry tStaffEntry);

    /**
     * 删除数据
     *
     * @param tStaffEntry
     * @return
     */
    CommonResult delete(TStaffEntry tStaffEntry);

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
     * @param tStaffEntry 实体对象
     * @return
     */
    CommonResult selectById(TStaffEntry tStaffEntry);
}
