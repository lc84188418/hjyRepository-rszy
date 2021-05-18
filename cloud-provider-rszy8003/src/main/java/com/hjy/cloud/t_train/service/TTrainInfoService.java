package com.hjy.cloud.t_train.service;

import com.hjy.cloud.common.entity.User;
import com.hjy.cloud.t_train.entity.TTrainInfo;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TTrainInfo)表服务接口
 *
 * @author makejava
 * @since 2021-03-01 16:41:42
 */
public interface TTrainInfoService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tTrainInfo
     * @return
     */
    CommonResult insert(TTrainInfo<User> tTrainInfo);

    /**
     * 修改数据
     *
     * @param tTrainInfo
     * @return
     */
    CommonResult updateByPkId(TTrainInfo tTrainInfo);

    /**
     * 删除数据
     *
     * @return
     */
    CommonResult delete(String pkInfoId);

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
     * @param tTrainInfo 实体对象
     * @return
     */
    CommonResult selectById(TTrainInfo tTrainInfo);
}
