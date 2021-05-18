package com.hjy.cloud.t_train.service;

import com.github.pagehelper.PageInfo;
import com.hjy.cloud.common.entity.User;
import com.hjy.cloud.domin.ActiveResult;
import com.hjy.cloud.t_train.entity.TTrainInfo;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.page.PageRequest;
import com.hjy.cloud.utils.page.PageResult;

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
    CommonResult updateByPkId(TTrainInfo<User> tTrainInfo);

    /**
     * 删除数据
     *
     * @return
     */
    CommonResult delete(String pkInfoId);

    /**
     * 查询所有数据
     *
     * @return
     */
    CommonResult<PageResult<TTrainInfo>> selectAll(PageRequest<TTrainInfo> pageInfo);

    /**
     * 获取单个数据
     *
     * @return
     */
    CommonResult<ActiveResult<TTrainInfo>> selectById(String pkInfoId);
}
