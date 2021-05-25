package com.hjy.cloud.t_dictionary.service;

import com.hjy.cloud.t_dictionary.entity.TDictionaryCity;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TDictionaryCity)表服务接口
 *
 * @author makejava
 * @since 2021-02-25 17:21:51
 */
public interface TDictionaryCityService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tDictionaryCity
     * @return
     */
    CommonResult insert(TDictionaryCity tDictionaryCity);

    /**
     * 修改数据
     *
     * @param tDictionaryCity
     * @return
     */
    CommonResult updateByPkId(TDictionaryCity tDictionaryCity);

    /**
     * 删除数据
     *
     * @param tDictionaryCity
     * @return
     */
    CommonResult delete(TDictionaryCity tDictionaryCity);

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
     * @param tDictionaryCity 实体对象
     * @return
     */
    CommonResult selectById(TDictionaryCity tDictionaryCity);
}
