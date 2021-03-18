package com.hjy.cloud.t_kq.service;

import com.hjy.cloud.t_kq.entity.TKqGroup;
import com.hjy.cloud.domin.CommonResult;

import java.text.ParseException;

/**
 * (TKqGroup)表服务接口
 *
 * @author makejava
 * @since 2021-03-16 14:50:59
 */
public interface TKqGroupService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param param
     * @return
     */
    CommonResult insert(String param) throws ParseException;

    /**
     * 修改数据
     *
     * @param param
     * @return
     */
    CommonResult updateByPkId(String param) throws ParseException;

    /**
     * 删除数据
     *
     * @param tKqGroup
     * @return
     */
    CommonResult delete(TKqGroup tKqGroup);

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
     * @param tKqGroup 实体对象
     * @return
     */
    CommonResult selectById(TKqGroup tKqGroup);
}
