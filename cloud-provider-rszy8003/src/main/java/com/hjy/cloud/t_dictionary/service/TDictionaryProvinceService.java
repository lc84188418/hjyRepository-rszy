package com.hjy.cloud.t_dictionary.service;

import com.hjy.cloud.t_dictionary.entity.TDictionaryCity;
import com.hjy.cloud.t_dictionary.entity.TDictionaryProvince;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TDictionaryProvince)表服务接口
 *
 * @author makejava
 * @since 2021-02-25 17:21:54
 */
public interface TDictionaryProvinceService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tDictionaryProvince
     * @return
     */
    CommonResult insert(TDictionaryProvince tDictionaryProvince);

    /**
     * 修改数据
     *
     * @param tDictionaryProvince
     * @return
     */
    CommonResult updateByPkId(TDictionaryProvince tDictionaryProvince);

    /**
     * 删除数据
     *
     * @param tDictionaryProvince
     * @return
     */
    CommonResult delete(TDictionaryProvince tDictionaryProvince);

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
     * @param tDictionaryProvince 实体对象
     * @return
     */
    CommonResult selectById(TDictionaryProvince tDictionaryProvince);
    /**
     * 给该省份添加城市信息
     *
     * @param city 实体对象
     * @return
     */
    CommonResult addCity(TDictionaryCity city);
}
