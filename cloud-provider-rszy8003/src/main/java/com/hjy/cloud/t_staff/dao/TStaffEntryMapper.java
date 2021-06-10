package com.hjy.cloud.t_staff.dao;

import com.hjy.cloud.t_staff.entity.TStaffEntry;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TStaffEntry)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-26 10:55:26
 */
public interface TStaffEntryMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pkEntryId 主键
     * @return 实例对象
     */
    TStaffEntry selectByPkId(@Param("pkEntryId")String pkEntryId);
    TStaffEntry selectByPkId2(@Param("pkEntryId")String pkEntryId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tStaffEntry 实例对象
     * @return 对象列表
     */
    List<TStaffEntry> selectAllPage(TStaffEntry tStaffEntry);

    /**
     * 新增数据
     *
     * @param tStaffEntry 实例对象
     * @return 影响行数
     */
    int insertSelective(TStaffEntry tStaffEntry);


    /**
     * 修改数据
     *
     * @param tStaffEntry 实例对象
     * @return 影响行数
     */
    int updateByPkId(TStaffEntry tStaffEntry);

    /**
     * 通过主键删除数据
     *
     * @param tStaffEntry 实例对象
     * @return 影响行数
     */
    int deleteById(TStaffEntry tStaffEntry);
    int selectCountByEntity(TStaffEntry tStaffEntry);

    String selectApplyPeople(String pkEntryId);
}
