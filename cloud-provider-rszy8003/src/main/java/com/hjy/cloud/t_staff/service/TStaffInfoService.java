package com.hjy.cloud.t_staff.service;

import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.t_staff.result.StaffInfos;

import java.util.List;

/**
 * (TStaffInfo)表服务接口
 *
 * @author makejava
 * @since 2021-02-25 17:06:47
 */
public interface TStaffInfoService {
    /**
     * 直接添加
     * @param tStaffInfo
     * @return
     */
    CommonResult insert(TStaffInfo tStaffInfo);


    /**
     * 修改数据
     *
     * @param tStaffInfo
     * @return
     */
    CommonResult updateByPkId(TStaffInfo tStaffInfo);

    /**
     * 删除数据
     *
     * @param tStaffInfo
     * @return
     */
    CommonResult delete(TStaffInfo tStaffInfo);

    /**
     * 查询所有数据
     *
     * @param param
     * @return
     */
    CommonResult selectAll(String param);
    List<StaffInfos> selectAll();

    /**
     * 获取单个数据
     *
     * @param tStaffInfo 实体对象
     * @return
     */
    CommonResult selectById(TStaffInfo tStaffInfo);

    int updateById(TStaffInfo tStaffInfo);

    TStaffInfo selectDeptLeader(String fkUserId);

    TStaffInfo selectLeaderByPosition(String s);

    TStaffInfo selectByPkId2(String currentSourceId);
}
