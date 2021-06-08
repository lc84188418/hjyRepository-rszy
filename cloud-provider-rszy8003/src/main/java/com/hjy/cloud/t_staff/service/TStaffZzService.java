package com.hjy.cloud.t_staff.service;

import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_staff.entity.TStaffZz;

import javax.servlet.http.HttpServletRequest;

/**
 * (TStaffZz)表服务接口
 *
 * @author makejava
 * @since 2021-03-01 15:53:18
 */
public interface TStaffZzService {

    /**
     * 修改数据
     *
     * @param tStaffZz
     * @return
     */
    CommonResult updateByPkId(TStaffZz tStaffZz);

    /**
     * 删除数据
     *
     * @param tStaffZz
     * @return
     */
    CommonResult delete(TStaffZz tStaffZz);

    /**
     * 查询所有数据
     *
     * @param param
     * @return
     */
    CommonResult selectAllEd(String param);
    CommonResult selectAllIng(String param);

    /**
     * 获取单个数据
     *
     * @param tStaffZz 实体对象
     * @return
     */
    CommonResult selectById(TStaffZz tStaffZz);
    /**
     * 发起转正审批页面
     *
     */
    CommonResult initiateZzPage(HttpServletRequest request);
    CommonResult initiateZz(HttpServletRequest request,String param)throws Exception;
}
