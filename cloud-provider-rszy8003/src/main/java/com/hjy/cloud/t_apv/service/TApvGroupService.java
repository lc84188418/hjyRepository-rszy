package com.hjy.cloud.t_apv.service;

import com.hjy.cloud.t_apv.entity.TApvGroup;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TApvGroup)表服务接口
 *
 * @author makejava
 * @since 2021-02-26 14:50:50
 */
public interface TApvGroupService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tApvGroup
     * @return
     */
    CommonResult insert(TApvGroup tApvGroup);

    /**
     * 修改数据
     *
     * @param tApvGroup
     * @return
     */
    CommonResult updateByPkId(TApvGroup tApvGroup);

    /**
     * 删除数据
     *
     * @param tApvGroup
     * @return
     */
    CommonResult delete(TApvGroup tApvGroup);

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
     * @param tApvGroup 实体对象
     * @return
     */
    CommonResult selectById(TApvGroup tApvGroup);
}
