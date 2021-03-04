package com.hjy.cloud.t_staff.dao;

import com.hjy.cloud.t_staff.entity.TStaffInfo;

import java.util.List;

/**
 * (TStaffInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-25 17:06:46
 */
public interface TStaffInfoMapper {

    /**
     * 分页记录条数
     *
     * @param tStaffInfo 实例对象
     * @return 记录条数
     */
    int selectSize(TStaffInfo tStaffInfo);

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TStaffInfo selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tStaffInfo 实例对象
     * @return 对象列表
     */
    List<TStaffInfo> selectAllPage(TStaffInfo tStaffInfo);
    List<TStaffInfo> selectAllId_Name();

    /**
     * 新增数据
     *
     * @param tStaffInfo 实例对象
     * @return 影响行数
     */
    int insertSelective(TStaffInfo tStaffInfo);

    int insert(TStaffInfo tStaffInfo);

    /**
     * 修改数据
     *
     * @param tStaffInfo 实例对象
     * @return 影响行数
     */
    int updateByPkId(TStaffInfo tStaffInfo);

    /**
     * 通过主键删除数据
     *
     * @param tStaffInfo 实例对象
     * @return 影响行数
     */
    int deleteById(TStaffInfo tStaffInfo);

    List<TStaffInfo> selectAll();
}
