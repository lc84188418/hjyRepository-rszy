package com.hjy.cloud.t_kq.dao;

import com.hjy.cloud.t_kq.entity.ReGroupStaff;
import com.hjy.cloud.t_kq.entity.ReGroupWorkingdays;
import com.hjy.cloud.t_kq.entity.TKqClock;
import com.hjy.cloud.t_outfit.entity.TOutfitWorkaddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TKqClock)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-19 17:46:25
 */
public interface TKqClockMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TKqClock selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tKqClock 实例对象
     * @return 对象列表
     */
    List<TKqClock> selectAllPage(TKqClock tKqClock);

    /**
     * 新增数据
     *
     * @param tKqClock 实例对象
     * @return 影响行数
     */
    int insertSelective(TKqClock tKqClock);

    /**
     * 修改数据
     *
     * @param tKqClock 实例对象
     * @return 影响行数
     */
    int updateByPkId(TKqClock tKqClock);

    /**
     * 通过主键删除数据
     *
     * @param tKqClock 实例对象
     * @return 影响行数
     */
    int deleteById(TKqClock tKqClock);
    /**
     * 通过员工id查询考勤组关联信息
     * @param fkStaffId 员工id
     * @return 考勤组关联信息
     */
    ReGroupStaff selectGroupStaffByStaffId(@Param("fkStaffId") String fkStaffId);
    /**
     * 通过分组id查询工作地信息
     * @param fkGroupId 分组id
     * @return 工作地信息
     */
    TOutfitWorkaddress selectWorkAddressByGroupId(@Param("fkGroupId")String fkGroupId);
    /**
     * 通过实体数据查询分组工作日设置信息
     * @param selectReGroupWorkingdays 实体对象
     * @return 分组工作日设置信息
     */
    List<ReGroupWorkingdays> selectGroupWorkingDaysByEntity(ReGroupWorkingdays selectReGroupWorkingdays);
}
