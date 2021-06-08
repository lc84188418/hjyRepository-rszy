package com.hjy.cloud.t_staff.service;

import com.hjy.cloud.t_staff.entity.TStaffExperienceJy;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TStaffExperienceJy)表服务接口
 *
 * @author makejava
 * @since 2021-03-01 15:53:15
 */
public interface TStaffExperienceJyService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tStaffExperienceJy
     * @return
     */
    CommonResult insert(TStaffExperienceJy tStaffExperienceJy);

    /**
     * 修改数据
     *
     * @param tStaffExperienceJy
     * @return
     */
    CommonResult updateByPkId(TStaffExperienceJy tStaffExperienceJy);

    /**
     * 删除数据
     *
     * @param tStaffExperienceJy
     * @return
     */
    CommonResult delete(TStaffExperienceJy tStaffExperienceJy);

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
     * @param tStaffExperienceJy 实体对象
     * @return
     */
    CommonResult selectById(TStaffExperienceJy tStaffExperienceJy);
}
