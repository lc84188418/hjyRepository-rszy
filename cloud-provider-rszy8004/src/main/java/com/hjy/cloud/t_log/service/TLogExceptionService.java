package com.hjy.cloud.t_log.service;

import com.hjy.cloud.t_log.entity.TLogException;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TLogException)表服务接口
 *
 * @author makejava
 * @since 2021-03-02 10:01:59
 */
public interface TLogExceptionService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tLogException
     * @return
     */
    CommonResult insert(TLogException tLogException);

    /**
     * 修改数据
     *
     * @param tLogException
     * @return
     */
    CommonResult updateByPkId(TLogException tLogException);

    /**
     * 删除数据
     *
     * @param tLogException
     * @return
     */
    CommonResult delete(TLogException tLogException);

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
     * @param tLogException 实体对象
     * @return
     */
    CommonResult selectById(TLogException tLogException);
}
