package com.hjy.cloud.t_kq.dao;

import com.hjy.cloud.t_kq.entity.ReBkGroup;
import com.hjy.cloud.t_kq.entity.TKqBk;
import com.hjy.cloud.t_kq.entity.TKqGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TKqBk)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-16 14:50:58
 */
public interface TKqBkMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pkBkId 主键
     * @return 实例对象
     */
    TKqBk selectByPkId(@Param("pkBkId")String pkBkId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tKqBk 实例对象
     * @return 对象列表
     */
    List<TKqBk> selectAllPage(TKqBk tKqBk);

    /**
     * 新增数据
     *
     * @param tKqBk 实例对象
     * @return 影响行数
     */
    int insertSelective(TKqBk tKqBk);

    /**
     * 修改数据
     *
     * @param tKqBk 实例对象
     * @return 影响行数
     */
    int updateByPkId(TKqBk tKqBk);

    /**
     * 通过主键删除数据
     *
     * @param tKqBk 实例对象
     * @return 影响行数
     */
    int deleteById(TKqBk tKqBk);

    int insertBkGroupBatch(@Param("bkGroupList")List<ReBkGroup> joinList);

    int deleteBkGroupByBkId(@Param("fkBkId")String fkBkId);

    List<TKqGroup> select_YX_BkGroupByBkId(@Param("fkBkId")String fkBkId);
}
