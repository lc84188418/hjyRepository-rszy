package com.hjy.cloud.t_staff.dao;

import com.hjy.cloud.t_staff.entity.TStaffRenewal;

import java.util.List;

/**
 * (TStaffRenewal)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-01 15:53:17
 */
public interface TStaffRenewalMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TStaffRenewal selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tStaffRenewal 实例对象
     * @return 对象列表
     */
    List<TStaffRenewal> selectAllPage(TStaffRenewal tStaffRenewal);

    /**
     * 新增数据
     *
     * @param tStaffRenewal 实例对象
     * @return 影响行数
     */
    int insertSelective(TStaffRenewal tStaffRenewal);

    /**
     * 修改数据
     *
     * @param tStaffRenewal 实例对象
     * @return 影响行数
     */
    int updateByPkId(TStaffRenewal tStaffRenewal);

    /**
     * 通过主键删除数据
     *
     * @param tStaffRenewal 实例对象
     * @return 影响行数
     */
    int deleteById(TStaffRenewal tStaffRenewal);
}
