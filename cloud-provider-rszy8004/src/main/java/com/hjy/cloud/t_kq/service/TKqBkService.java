package com.hjy.cloud.t_kq.service;

import com.hjy.cloud.t_kq.entity.TKqBk;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TKqBk)表服务接口
 *
 * @author makejava
 * @since 2021-03-16 14:50:58
 */
public interface TKqBkService {
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
     * @param tKqBk
     * @return
     */
    CommonResult delete(TKqBk tKqBk);

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
     * @param tKqBk 实体对象
     * @return
     */
    CommonResult selectById(TKqBk tKqBk);
}
