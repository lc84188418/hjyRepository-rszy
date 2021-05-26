package com.hjy.cloud.t_index.service;

import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_index.entity.TIndexBwl;
import com.hjy.cloud.utils.page.PageRequest;
import com.hjy.cloud.utils.page.PageResult;

import javax.servlet.http.HttpServletRequest;
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
    CommonResult insert(HttpSession session,HttpServletRequest request, TIndexBwl tIndexBwl);

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
     * @return
     */
    CommonResult delete(String param);

    /**
     * 查询所有数据
     *
     * @return
     */
    CommonResult<PageResult<TIndexBwl>> selectAll(PageRequest<TIndexBwl> pageRequest);
    CommonResult<PageResult<TIndexBwl>> selectAll(HttpSession session, HttpServletRequest request,PageRequest<TIndexBwl> pageRequest);

    /**
     * 获取单个数据
     *
     * @return
     */
    CommonResult<TIndexBwl> selectById(String pkBwlId);
}
