package com.hjy.cloud.t_staff.service;

import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_staff.entity.TStaffReassign;
import com.hjy.cloud.t_staff.result.ReassignApprovalResult;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
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

    List<TStaffReassign> selectAllPage(TStaffReassign selectEntity);

    //管理员操作调动
    CommonResult initiateApvPage(HttpServletRequest request, TStaffReassign tStaffReassign);
    CommonResult initiateApv(HttpServletRequest request, String param);
    //员工操作调动
    CommonResult<ReassignApprovalResult> userInitiateApvPage(HttpServletRequest request);
    CommonResult userInitiateApv(HttpServletRequest request, String param) throws ParseException;
}
