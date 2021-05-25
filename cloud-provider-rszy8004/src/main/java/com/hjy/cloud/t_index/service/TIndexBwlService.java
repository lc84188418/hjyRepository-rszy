package com.hjy.cloud.t_index.service;

import com.hjy.cloud.t_index.entity.TIndexBwl;
import com.hjy.cloud.domin.CommonResult;

import javax.servlet.http.HttpSession;

/**
 * (TIndexBwl)表服务接口
 *
 * @author makejava
 * @since 2021-03-01 15:50:54
 */
public interface TIndexBwlService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tIndexBwl
     * @return
     */
    CommonResult insert(HttpSession session, TIndexBwl tIndexBwl);

    /**
     * 修改数据
     *
     * @param tIndexBwl
     * @return
     */
    CommonResult updateByPkId(TIndexBwl tIndexBwl);

    /**
     * 删除数据
     *
     * @param tIndexBwl
     * @return
     */
    CommonResult delete(TIndexBwl tIndexBwl);

    /**
     * 查询所有数据
     *
     * @param param
     * @return
     */
    CommonResult selectAll(String param);
    CommonResult selectAll(HttpSession session,String param);

    /**
     * 获取单个数据
     *
     * @param tIndexBwl 实体对象
     * @return
     */
    CommonResult selectById(TIndexBwl tIndexBwl);
}
