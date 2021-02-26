package com.hjy.cloud.dictionary.service;

import com.hjy.cloud.dictionary.entity.TDictionaryPosition;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TDictionaryPosition)表服务接口
 *
 * @author makejava
 * @since 2021-02-25 17:21:53
 */
public interface TDictionaryPositionService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tDictionaryPosition
     * @return
     */
    CommonResult insert(TDictionaryPosition tDictionaryPosition);

    /**
     * 修改数据
     *
     * @param tDictionaryPosition
     * @return
     */
    CommonResult updateByPkId(TDictionaryPosition tDictionaryPosition);

    /**
     * 删除数据
     *
     * @param tDictionaryPosition
     * @return
     */
    CommonResult delete(TDictionaryPosition tDictionaryPosition);

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
     * @param tDictionaryPosition 实体对象
     * @return
     */
    CommonResult selectById(TDictionaryPosition tDictionaryPosition);
}
