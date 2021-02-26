package com.hjy.cloud.dictionary.service;

import com.hjy.cloud.dictionary.entity.TDictionaryArea;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TDictionaryArea)表服务接口
 *
 * @author makejava
 * @since 2021-02-25 17:21:49
 */
public interface TDictionaryAreaService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tDictionaryArea
     * @return
     */
    CommonResult insert(TDictionaryArea tDictionaryArea);

    /**
     * 修改数据
     *
     * @param tDictionaryArea
     * @return
     */
    CommonResult updateByPkId(TDictionaryArea tDictionaryArea);

    /**
     * 删除数据
     *
     * @param tDictionaryArea
     * @return
     */
    CommonResult delete(TDictionaryArea tDictionaryArea);

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
     * @param tDictionaryArea 实体对象
     * @return
     */
    CommonResult selectById(TDictionaryArea tDictionaryArea);
}
