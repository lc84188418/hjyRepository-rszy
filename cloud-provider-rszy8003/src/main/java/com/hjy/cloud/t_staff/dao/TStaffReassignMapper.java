package com.hjy.cloud.t_staff.dao;

import com.hjy.cloud.t_staff.entity.TStaffReassign;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TStaffReassign)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-01 15:53:16
 */
public interface TStaffReassignMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pkReassignId 主键
     * @return 实例对象
     */
    TStaffReassign selectByPkId(@Param("pkReassignId")String pkReassignId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tStaffReassign 实例对象
     * @return 对象列表
     */
    List<TStaffReassign> selectAllPage(TStaffReassign tStaffReassign);

    /**
     * 新增数据
     *
     * @param tStaffReassign 实例对象
     * @return 影响行数
     */
    int insertSelective(TStaffReassign tStaffReassign);

    /**
     * 修改数据
     *
     * @param tStaffReassign 实例对象
     * @return 影响行数
     */
    int updateByPkId(TStaffReassign tStaffReassign);

    /**
     * 通过主键删除数据
     *
     * @param tStaffReassign 实例对象
     * @return 影响行数
     */
    int deleteById(TStaffReassign tStaffReassign);
    /**
     * 当前员工调动前信息
     */
    TStaffReassign selectStaffOldInfoByStaffId(@Param("fkStaffId") String fkStaffId);

    String selectStaff(@Param("pkReassignId")String sourceId);
}
