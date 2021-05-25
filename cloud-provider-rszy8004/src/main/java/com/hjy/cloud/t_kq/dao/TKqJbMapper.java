package com.hjy.cloud.t_kq.dao;

import com.hjy.cloud.t_kq.entity.ReJbGroup;
import com.hjy.cloud.t_kq.entity.TKqGroup;
import com.hjy.cloud.t_kq.entity.TKqJb;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TKqJb)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-16 14:51:00
 */
public interface TKqJbMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TKqJb selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tKqJb 实例对象
     * @return 对象列表
     */
    List<TKqJb> selectAllPage(TKqJb tKqJb);

    /**
     * 新增数据
     *
     * @param tKqJb 实例对象
     * @return 影响行数
     */
    int insertSelective(TKqJb tKqJb);

    /**
     * 修改数据
     *
     * @param tKqJb 实例对象
     * @return 影响行数
     */
    int updateByPkId(TKqJb tKqJb);

    /**
     * 通过主键删除数据
     *
     * @param tKqJb 实例对象
     * @return 影响行数
     */
    int deleteById(TKqJb tKqJb);

    int deleteJbGroupByJbId(@Param("fkJbId")String fkJbId);

    int insertJbGroupBatch(@Param("jbGroups")List<ReJbGroup> joinList);

    List<TKqGroup> select_YX_JbGroupByJbId(@Param("fkJbId")String fkJbId);
}
