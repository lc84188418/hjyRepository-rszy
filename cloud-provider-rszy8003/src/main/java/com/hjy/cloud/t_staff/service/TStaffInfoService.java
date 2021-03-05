package com.hjy.cloud.t_staff.service;

import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TStaffInfo)表服务接口
 *
 * @author makejava
 * @since 2021-02-25 17:06:47
 */
public interface TStaffInfoService {

    /**
     * 修改数据
     *
     * @param tStaffInfo
     * @return
     */
    CommonResult updateByPkId(TStaffInfo tStaffInfo);

    /**
     * 删除数据
     *
     * @param tStaffInfo
     * @return
     */
    CommonResult delete(TStaffInfo tStaffInfo);

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
     * @param tStaffInfo 实体对象
     * @return
     */
    CommonResult selectById(TStaffInfo tStaffInfo);
}
