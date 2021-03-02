package com.hjy.cloud.t_dictionary.dao;

import com.hjy.cloud.t_dictionary.entity.TDictionaryPosition;

import java.util.List;

/**
 * (TDictionaryPosition)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-25 17:21:53
 */
public interface TDictionaryPositionMapper {

    /**
     * 分页记录条数
     *
     * @param tDictionaryPosition 实例对象
     * @return 记录条数
     */
    int selectSize(TDictionaryPosition tDictionaryPosition);

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TDictionaryPosition selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tDictionaryPosition 实例对象
     * @return 对象列表
     */
    List<TDictionaryPosition> selectAllPage(TDictionaryPosition tDictionaryPosition);
    /**
     * 查询ID和名称列表
     *
     * @return 对象列表
     */
    List<TDictionaryPosition> selectAllId_Name();
    /**
     * 新增数据
     *
     * @param tDictionaryPosition 实例对象
     * @return 影响行数
     */
    int insertSelective(TDictionaryPosition tDictionaryPosition);

    int insert(TDictionaryPosition tDictionaryPosition);

    /**
     * 修改数据
     *
     * @param tDictionaryPosition 实例对象
     * @return 影响行数
     */
    int updateByPkId(TDictionaryPosition tDictionaryPosition);

    /**
     * 通过主键删除数据
     *
     * @param tDictionaryPosition 实例对象
     * @return 影响行数
     */
    int deleteById(TDictionaryPosition tDictionaryPosition);
}
