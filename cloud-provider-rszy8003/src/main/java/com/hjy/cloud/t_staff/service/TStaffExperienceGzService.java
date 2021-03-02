package com.hjy.cloud.t_staff.service;

import com.hjy.cloud.t_staff.entity.TStaffExperienceGz;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TStaffExperienceGz)表服务接口
 *
 * @author makejava
 * @since 2021-03-01 15:53:14
 */
public interface TStaffExperienceGzService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tStaffExperienceGz
     * @return
     */
    CommonResult insert(TStaffExperienceGz tStaffExperienceGz);

    /**
     * 修改数据
     *
     * @param tStaffExperienceGz
     * @return
     */
    CommonResult updateByPkId(TStaffExperienceGz tStaffExperienceGz);

    /**
     * 删除数据
     *
     * @param tStaffExperienceGz
     * @return
     */
    CommonResult delete(TStaffExperienceGz tStaffExperienceGz);

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
     * @param tStaffExperienceGz 实体对象
     * @return
     */
    CommonResult selectById(TStaffExperienceGz tStaffExperienceGz);
}
