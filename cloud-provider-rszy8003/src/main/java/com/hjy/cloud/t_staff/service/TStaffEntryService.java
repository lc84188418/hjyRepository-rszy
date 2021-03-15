package com.hjy.cloud.t_staff.service;

import com.hjy.cloud.t_staff.entity.TStaffEntry;
import com.hjy.cloud.domin.CommonResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

/**
 * (TStaffEntry)表服务接口
 *
 * @author makejava
 * @since 2021-02-26 10:55:26
 */
public interface TStaffEntryService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tStaffEntry
     * @return
     */
    CommonResult insert(TStaffEntry tStaffEntry);

    /**
     * 修改数据
     *
     * @param tStaffEntry
     * @return
     */
    CommonResult updateByPkId(TStaffEntry tStaffEntry);

    /**
     * 删除数据
     *
     * @param tStaffEntry
     * @return
     */
    CommonResult delete(TStaffEntry tStaffEntry);

    /**
     * 查询所有数据
     *
     * @param param
     * @return
     */

    CommonResult adminList(String param);

    CommonResult userGet(HttpServletRequest servletRequest);

    /**
     * 获取单个数据
     *
     * @param tStaffEntry 实体对象
     * @return
     */
    CommonResult selectById(TStaffEntry tStaffEntry);
    /**
     * 弃职
     *
     * @param tStaffEntry 实体对象
     * @return 修改结果
     */
    CommonResult giveUp(TStaffEntry tStaffEntry);
    /**
     * 发起入职审批页面
     *
     * @param tStaffEntry 实体对象
     * @return 修改结果
     */
    CommonResult approvalPage(HttpServletRequest request,TStaffEntry tStaffEntry);
    /**
     * 发起入职审批
     *
     */
    CommonResult approval(HttpServletRequest request, String param);

    TStaffEntry selectByPkId(String currentSourceId);
    TStaffEntry selectByPkId2(String currentSourceId);

}
