package com.hjy.cloud.t_dictionary.service;

import com.hjy.cloud.t_dictionary.entity.TDictionaryNation;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TDictionaryNation)表服务接口
 *
 * @author makejava
 * @since 2021-02-25 17:21:53
 */
public interface TDictionaryNationService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tDictionaryNation
     * @return
     */
    CommonResult insert(TDictionaryNation tDictionaryNation);
    CommonResult addBatch(String param);


    /**
     * 修改数据
     *
     * @param tDictionaryNation
     * @return
     */
    CommonResult updateByPkId(TDictionaryNation tDictionaryNation);

    /**
     * 删除数据
     *
     * @param tDictionaryNation
     * @return
     */
    CommonResult delete(TDictionaryNation tDictionaryNation);

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
     * @param tDictionaryNation 实体对象
     * @return
     */
    CommonResult selectById(TDictionaryNation tDictionaryNation);

}
