package com.hjy.cloud.t_kq.dao;

import com.hjy.cloud.t_kq.entity.ReGroupStaff;
import com.hjy.cloud.t_kq.entity.TKqGroup;
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
     * @param pk_id 主键
     * @return 实例对象
     */
    TKqGroup selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tKqGroup 实例对象
     * @return 对象列表
     */
    List<TKqGroup> selectAllPage(TKqGroup tKqGroup);

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

    int insertGroupStaffBatch(@Param("groupStaffList")List<ReGroupStaff> joinList);
}
