package com.hjy.cloud.t_staff.service;

import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.t_staff.entity.TStaffQuit;
import com.hjy.cloud.domin.CommonResult;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * (TStaffQuit)表服务接口
 *
 * @author makejava
 * @since 2021-03-01 15:53:15
 */
public interface TStaffQuitService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage(HttpServletRequest request);

    /**
     * 添加数据
     *
     * @return
     */
    CommonResult insert(HttpServletRequest request,String param) throws ParseException;

    /**
     * 修改数据
     *
     * @param tStaffQuit
     * @return
     */
    CommonResult updateByPkId(TStaffQuit tStaffQuit);

    /**
     * 删除数据
     *
     * @param tStaffQuit
     * @return
     */
    CommonResult delete(TStaffQuit tStaffQuit);

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
     * @param tStaffQuit 实体对象
     * @return
     */
    CommonResult selectById(TStaffQuit tStaffQuit);
}
