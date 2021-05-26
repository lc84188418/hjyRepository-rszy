package com.hjy.cloud.t_index.dao;

import com.hjy.cloud.t_index.entity.TIndexBwl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TIndexBwl)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-01 15:50:53
 */
public interface TIndexBwlMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TIndexBwl selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tIndexBwl 实例对象
     * @return 对象列表
     */
    List<TIndexBwl> selectAllPage(TIndexBwl tIndexBwl);

    /**
     * 新增数据
     *
     * @param tIndexBwl 实例对象
     * @return 影响行数
     */
    int insertSelective(TIndexBwl tIndexBwl);

    /**
     * 修改数据
     *
     * @param tIndexBwl 实例对象
     * @return 影响行数
     */
    int updateByPkId(TIndexBwl tIndexBwl);

    /**
     * 通过主键删除数据
     *
     * @return 影响行数
     */
    int deleteById(String pkBwlId);
    /**
     * 查询还有效的备忘录
     *
     * @return 影响行数
     */
    List<TIndexBwl> selectAllEffective();
    /**
     * 发送完成后修改备忘录发送状态
     *
     * @return 影响行数
     */
    int updateSendStatusBatch(@Param("bwls")List<TIndexBwl> bwls);
}
