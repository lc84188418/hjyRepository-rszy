package com.hjy.cloud.t_dictionary.dao;

import com.hjy.cloud.t_dictionary.entity.TDictionaryHtlx;

import java.util.List;

/**
 * (TDictionaryHtlx)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-25 17:21:52
 */
public interface TDictionaryHtlxMapper {

    /**
     * 分页记录条数
     *
     * @param tDictionaryHtlx 实例对象
     * @return 记录条数
     */
    int selectSize(TDictionaryHtlx tDictionaryHtlx);

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TDictionaryHtlx selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tDictionaryHtlx 实例对象
     * @return 对象列表
     */
    List<TDictionaryHtlx> selectAllPage(TDictionaryHtlx tDictionaryHtlx);
    /**
     * 查询ID和名称列表
     *
     * @return 对象列表
     */
    List<TDictionaryHtlx> selectAllId_Name();
    /**
     * 新增数据
     *
     * @param tDictionaryHtlx 实例对象
     * @return 影响行数
     */
    int insertSelective(TDictionaryHtlx tDictionaryHtlx);

    int insert(TDictionaryHtlx tDictionaryHtlx);

    /**
     * 修改数据
     *
     * @param tDictionaryHtlx 实例对象
     * @return 影响行数
     */
    int updateByPkId(TDictionaryHtlx tDictionaryHtlx);

    /**
     * 通过主键删除数据
     *
     * @param tDictionaryHtlx 实例对象
     * @return 影响行数
     */
    int deleteById(TDictionaryHtlx tDictionaryHtlx);
}
