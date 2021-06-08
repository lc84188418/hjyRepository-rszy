package com.hjy.cloud.t_staff.service;

import com.hjy.cloud.t_staff.entity.TStaffContract;
import com.hjy.cloud.domin.CommonResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * (TStaffContract)表服务接口
 *
 * @author makejava
 * @since 2021-03-01 15:53:14
 */
public interface TStaffContractService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tStaffContract
     * @return
     */
    CommonResult insert(TStaffContract tStaffContract);

    /**
     * 修改数据
     *
     * @param tStaffContract
     * @return
     */
    CommonResult updateByPkId(TStaffContract tStaffContract);

    /**
     * 删除数据
     *
     * @param tStaffContract
     * @return
     */
    CommonResult delete(TStaffContract tStaffContract);

    /**
     * 查询所有数据
     *
     * @param param
     * @return
     */
    CommonResult adminList(String param);
    CommonResult userList(HttpSession session, HttpServletRequest request, String param);

    /**
     * 获取单个数据
     *
     * @param tStaffContract 实体对象
     * @return
     */
    CommonResult selectById(TStaffContract tStaffContract);
    /**
     * 发起续签合同页面
     *
     */
    CommonResult renewalPage(TStaffContract tStaffContract);
    /**
     * 续签合同
     *
     */
    CommonResult renewal(TStaffContract tStaffContract);
}
