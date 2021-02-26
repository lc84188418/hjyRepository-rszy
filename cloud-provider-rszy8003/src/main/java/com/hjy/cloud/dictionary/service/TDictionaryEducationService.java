package com.hjy.cloud.dictionary.service;

import com.hjy.cloud.dictionary.entity.TDictionaryEducation;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TDictionaryEducation)表服务接口
 *
 * @author makejava
 * @since 2021-02-25 17:21:51
 */
public interface TDictionaryEducationService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tDictionaryEducation
     * @return
     */
    CommonResult insert(TDictionaryEducation tDictionaryEducation);

    /**
     * 修改数据
     *
     * @param tDictionaryEducation
     * @return
     */
    CommonResult updateByPkId(TDictionaryEducation tDictionaryEducation);

    /**
     * 删除数据
     *
     * @param tDictionaryEducation
     * @return
     */
    CommonResult delete(TDictionaryEducation tDictionaryEducation);

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
     * @param tDictionaryEducation 实体对象
     * @return
     */
    CommonResult selectById(TDictionaryEducation tDictionaryEducation);
}
