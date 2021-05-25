package com.hjy.cloud.t_dictionary.dao;

import com.hjy.cloud.t_dictionary.entity.TDictionaryArea;

import java.util.List;

/**
 * (TDictionaryArea)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-25 17:21:48
 */
public interface TDictionaryAreaMapper {

    /**
     * 分页记录条数
     *
     * @param tDictionaryArea 实例对象
     * @return 记录条数
     */
    int selectSize(TDictionaryArea tDictionaryArea);

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TDictionaryArea selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tDictionaryArea 实例对象
     * @return 对象列表
     */
    List<TDictionaryArea> selectAllPage(TDictionaryArea tDictionaryArea);
    /**
     * 查询ID和名称列表
     *
     * @return 对象列表
     */
    List<TDictionaryArea> selectAllId_Name();

    /**
     * 新增数据
     *
     * @param tDictionaryArea 实例对象
     * @return 影响行数
     */
    int insertSelective(TDictionaryArea tDictionaryArea);

    int insert(TDictionaryArea tDictionaryArea);

    /**
     * 修改数据
     *
     * @param tDictionaryArea 实例对象
     * @return 影响行数
     */
    int updateByPkId(TDictionaryArea tDictionaryArea);

    /**
     * 通过主键删除数据
     *
     * @param tDictionaryArea 实例对象
     * @return 影响行数
     */
    int deleteById(TDictionaryArea tDictionaryArea);
}
