package com.hjy.cloud.t_staff.service;

import com.hjy.cloud.t_staff.entity.TStaffReassign;
import com.hjy.cloud.domin.CommonResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * (TStaffReassign)表服务接口
 *
 * @author makejava
 * @since 2021-03-01 15:53:16
 */
public interface TStaffReassignService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage(TStaffReassign tStaffReassign);

    /**
     * 添加数据
     *
     * @param tStaffReassign
     * @return
     */
    CommonResult insert(TStaffReassign tStaffReassign);

    /**
     * 修改数据
     *
     * @param tStaffReassign
     * @return
     */
    CommonResult updateByPkId(TStaffReassign tStaffReassign);

    /**
     * 删除数据
     *
     * @param tStaffReassign
     * @return
     */
    CommonResult delete(TStaffReassign tStaffReassign);

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
     * @param tStaffReassign 实体对象
     * @return
     */
    CommonResult selectById(TStaffReassign tStaffReassign);
    /**
     * 发起调动审批页面
     * 管理员
     * @return 修改结果
     */
    CommonResult initiateApvPage(HttpServletRequest request, TStaffReassign tStaffReassign);

    CommonResult initiateApv(HttpServletRequest request, String param);

    List<TStaffReassign> selectAllPage(TStaffReassign selectEntity);
}
