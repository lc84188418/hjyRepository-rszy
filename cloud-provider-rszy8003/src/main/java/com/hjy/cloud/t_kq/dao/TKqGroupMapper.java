package com.hjy.cloud.t_kq.dao;

import com.hjy.cloud.t_kq.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TKqGroup)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-16 14:50:59
 */
public interface TKqGroupMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pkGroupId 主键
     * @return 实例对象
     */
    TKqGroup selectByPkId(@Param("pkGroupId")String pkGroupId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tKqGroup 实例对象
     * @return 对象列表
     */
    List<TKqGroup> selectAllPage(TKqGroup tKqGroup);
    List<TKqGroup> selectAllId_name();

    /**
     * 新增数据
     *
     * @param tKqGroup 实例对象
     * @return 影响行数
     */
    int insertSelective(TKqGroup tKqGroup);

    /**
     * 修改数据
     *
     * @param tKqGroup 实例对象
     * @return 影响行数
     */
    int updateByPkId(TKqGroup tKqGroup);

    /**
     * 通过主键删除数据
     *
     * @param tKqGroup 实例对象
     * @return 影响行数
     */
    int deleteById(TKqGroup tKqGroup);
    //批量添加考勤分组-员工
    int insertGroupStaffBatch(@Param("groupStaffList")List<ReGroupStaff> joinList);
    //批量添加考勤分组-班次
    int insertGroupBcBatch(@Param("groupBcList")List<ReGroupWorkingdays> workingDaysList);
    //批量添加考勤分组-工作地
    int insertGroupWorkaddressBatch(@Param("groupWorkaddressList")List<ReGroupWorkaddress> workAddressList);
    //批量添加考勤分组-加班规则
    int insertJbGroupBatch(@Param("jbGroupList")List<ReJbGroup> jbList);

    //批量删除考勤分组-员工
    int deleteGroupStaffByGroupId(@Param("fkGroupId")String fkGroupId);
    //批量删除考勤分组-班次
    int deleteGroupBcByGroupId(@Param("fkGroupId")String fkGroupId);
    //批量删除考勤分组-工作地
    int deleteGroupWorkaddressByGroupId(@Param("fkGroupId")String fkGroupId);
    //批量删除考勤分组-加班规则
    int deleteJbGroupByGroupId(@Param("fkGroupId")String fkGroupId);

    //查询该分组下已选考勤的员工
    List<ReGroupStaff> select_YX_StaffByGroup_IsKQ(@Param("fkGroupId")String fkGroupId, @Param("isKq")int isKq);
    //查询该分组下已选工作日设置
    List<ReGroupWorkingdays> select_YX_workingdaysByGroup(@Param("fkGroupId")String fkGroupId);
    //查询该分组下已选工作地
    List<ReGroupWorkaddress> select_YX_workaddressByGroup(@Param("fkGroupId")String fkGroupId);
    //查询该分组下已选加班规则
    List<ReJbGroup> select_YX_JbByGroup(@Param("fkGroupId")String fkGroupId);
    //删除补卡-group
    int deleteBkGroupByGroupId(@Param("fkGroupId")String fkGroupId);
    //删除加班-group
    int deleteJbGroupByGroupId_Batch(@Param("jbGroupList")List<ReJbGroup> joinList);

    int deleteGroupStaffBatchByStaffId(@Param("joinList")List<ReGroupStaff> joinList);
}
