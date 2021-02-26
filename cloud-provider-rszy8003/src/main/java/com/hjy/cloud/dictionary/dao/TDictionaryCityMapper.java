package com.hjy.cloud.dictionary.dao;

import com.hjy.cloud.dictionary.entity.TDictionaryArea;
import com.hjy.cloud.dictionary.entity.TDictionaryCity;

import java.util.List;

/**
 * (TDictionaryCity)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-25 17:21:51
 */
public interface TDictionaryCityMapper {

    /**
     * 分页记录条数
     *
     * @param tDictionaryCity 实例对象
     * @return 记录条数
     */
    int selectSize(TDictionaryCity tDictionaryCity);

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TDictionaryCity selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tDictionaryCity 实例对象
     * @return 对象列表
     */
    List<TDictionaryCity> selectAllPage(TDictionaryCity tDictionaryCity);
    /**
     * 查询ID和名称列表
     *
     * @return 对象列表
     */
    List<TDictionaryCity> selectAllId_Name();
    /**
     * 新增数据
     *
     * @param tDictionaryCity 实例对象
     * @return 影响行数
     */
    int insertSelective(TDictionaryCity tDictionaryCity);

    int insert(TDictionaryCity tDictionaryCity);

    /**
     * 修改数据
     *
     * @param tDictionaryCity 实例对象
     * @return 影响行数
     */
    int updateByPkId(TDictionaryCity tDictionaryCity);

    /**
     * 通过主键删除数据
     *
     * @param tDictionaryCity 实例对象
     * @return 影响行数
     */
    int deleteById(TDictionaryCity tDictionaryCity);

}
