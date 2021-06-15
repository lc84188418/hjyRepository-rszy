package com.hjy.cloud.t_staff.dao;

import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.t_staff.result.StaffInfos;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TStaffInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-25 17:06:46
 */
public interface TStaffInfoMapper {

    /**
     * 通过ID查询单条数据
     *  但是该方法的参数，必须能保证该结果的唯一性，否则就是多结果用List接收了
     * @return 实例对象
     */
    TStaffInfo selectByPkId2(TStaffInfo tStaffInfo);
    //通过实体查询是否有数据
    int selectCountByEntity(TStaffInfo tStaffInfo);

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

    List<StaffInfos> selectAll();

    TStaffInfo selectDeptLeader(@Param("pkStaffId")String pkStaffId);

    TStaffInfo selectLeaderByPosition(@Param("fkPositionId")String fkPositionId);
    /**
     * 通过证件号查询员工基本信息
     */
    TStaffInfo selectByIdCard(@Param("idcard")String idcard);
    /**
     * 查询还没添加工资条的员工列表，不包括已经添加过的员工
     */
    List<TStaffInfo> selectEnableAddSalary();
    /**
     * 查询还没添加考勤组的员工列表，不包括已经添加过的员工
     */
    List<TStaffInfo> selectAll_KX_StaffId_Name();

    List<TStaffInfo> selectByIds(@Param("ids") List<String> deptUserList);
    //通过部门查询该部门下的所属员工id
    List<String> selectUserIdByDept(String fkDeptId);
    //
    int deleteDeptUserByDeptId(@Param("fkDeptId")String pkDeptId);
    //
    List<TStaffInfo> selectAllId_NameByName(TStaffInfo tStaffInfo);
}
