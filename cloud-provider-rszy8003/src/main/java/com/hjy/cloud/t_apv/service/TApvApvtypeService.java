package com.hjy.cloud.t_apv.service;

import com.hjy.cloud.t_apv.entity.TApvApvtype;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TApvApvtype)表服务接口
 *
 * @author makejava
 * @since 2021-02-26 14:50:49
 */
public interface TApvApvtypeService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tApvApvtype
     * @return
     */
    CommonResult insert(TApvApvtype tApvApvtype);

    /**
     * 修改数据
     *
     * @param tApvApvtype
     * @return
     */
    CommonResult updateByPkId(TApvApvtype tApvApvtype);

    /**
     * 删除数据
     *
     * @param tApvApvtype
     * @return
     */
    CommonResult delete(TApvApvtype tApvApvtype);

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
     * @param tApvApvtype 实体对象
     * @return
     */
    CommonResult selectById(TApvApvtype tApvApvtype);
}
