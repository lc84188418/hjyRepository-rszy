package com.hjy.cloud.t_kq.service;

import com.hjy.cloud.t_kq.entity.TKqBc;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TKqBc)表服务接口
 *
 * @author makejava
 * @since 2021-03-16 14:50:57
 */
public interface TKqBcService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param param
     * @return
     */
    CommonResult insert(String param);

    /**
     * 修改数据
     *
     * @param param
     * @return
     */
    CommonResult updateByPkId(String param);

    /**
     * 删除数据
     *
     * @param tKqBc
     * @return
     */
    CommonResult delete(TKqBc tKqBc);

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
     * @param tKqBc 实体对象
     * @return
     */
    CommonResult selectById(TKqBc tKqBc);
}
