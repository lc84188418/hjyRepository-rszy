package com.hjy.cloud.t_dictionary.dao;

import com.hjy.cloud.t_dictionary.entity.TDictionaryFile;

import java.util.List;

/**
 * (TDictionaryFile)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-02 15:16:30
 */
public interface TDictionaryFileMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TDictionaryFile selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tDictionaryFile 实例对象
     * @return 对象列表
     */
    List<TDictionaryFile> selectAllPage(TDictionaryFile tDictionaryFile);
    List<TDictionaryFile> selectAll();


    /**
     * 新增数据
     *
     * @param tDictionaryFile 实例对象
     * @return 影响行数
     */
    int insertSelective(TDictionaryFile tDictionaryFile);

    /**
     * 修改数据
     *
     * @param tDictionaryFile 实例对象
     * @return 影响行数
     */
    int updateByPkId(TDictionaryFile tDictionaryFile);

    /**
     * 通过主键删除数据
     *
     * @param tDictionaryFile 实例对象
     * @return 影响行数
     */
    int deleteById(TDictionaryFile tDictionaryFile);

}
