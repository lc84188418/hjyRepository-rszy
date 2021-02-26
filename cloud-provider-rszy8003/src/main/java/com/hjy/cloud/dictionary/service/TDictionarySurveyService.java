package com.hjy.cloud.dictionary.service;

import com.hjy.cloud.dictionary.entity.TDictionarySurvey;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TDictionarySurvey)表服务接口
 *
 * @author makejava
 * @since 2021-02-25 17:21:55
 */
public interface TDictionarySurveyService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tDictionarySurvey
     * @return
     */
    CommonResult insert(TDictionarySurvey tDictionarySurvey);

    /**
     * 修改数据
     *
     * @param tDictionarySurvey
     * @return
     */
    CommonResult updateByPkId(TDictionarySurvey tDictionarySurvey);

    /**
     * 删除数据
     *
     * @param tDictionarySurvey
     * @return
     */
    CommonResult delete(TDictionarySurvey tDictionarySurvey);

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
     * @param tDictionarySurvey 实体对象
     * @return
     */
    CommonResult selectById(TDictionarySurvey tDictionarySurvey);
}
