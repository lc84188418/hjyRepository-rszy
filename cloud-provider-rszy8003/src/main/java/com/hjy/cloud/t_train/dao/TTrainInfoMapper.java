package com.hjy.cloud.t_train.dao;

import com.hjy.cloud.t_train.entity.TTrainInfo;

import java.util.List;

/**
 * (TTrainInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-01 16:41:41
 */
public interface TTrainInfoMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TTrainInfo selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tTrainInfo 实例对象
     * @return 对象列表
     */
    List<TTrainInfo> selectAllPage(TTrainInfo tTrainInfo);

    /**
     * 新增数据
     *
     * @param tTrainInfo 实例对象
     * @return 影响行数
     */
    int insertSelective(TTrainInfo tTrainInfo);

    /**
     * 修改数据
     *
     * @param tTrainInfo 实例对象
     * @return 影响行数
     */
    int updateByPkId(TTrainInfo tTrainInfo);

    /**
     * 通过主键删除数据
     *
     * @param tTrainInfo 实例对象
     * @return 影响行数
     */
    int deleteById(TTrainInfo tTrainInfo);
}
