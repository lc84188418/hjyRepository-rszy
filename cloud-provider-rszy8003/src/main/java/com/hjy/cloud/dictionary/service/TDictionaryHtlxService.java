package com.hjy.cloud.dictionary.service;

import com.hjy.cloud.dictionary.entity.TDictionaryHtlx;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TDictionaryHtlx)表服务接口
 *
 * @author makejava
 * @since 2021-02-25 17:21:52
 */
public interface TDictionaryHtlxService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tDictionaryHtlx
     * @return
     */
    CommonResult insert(TDictionaryHtlx tDictionaryHtlx);

    /**
     * 修改数据
     *
     * @param tDictionaryHtlx
     * @return
     */
    CommonResult updateByPkId(TDictionaryHtlx tDictionaryHtlx);

    /**
     * 删除数据
     *
     * @param tDictionaryHtlx
     * @return
     */
    CommonResult delete(TDictionaryHtlx tDictionaryHtlx);

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
     * @param tDictionaryHtlx 实体对象
     * @return
     */
    CommonResult selectById(TDictionaryHtlx tDictionaryHtlx);
}
