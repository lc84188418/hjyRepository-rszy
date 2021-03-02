package com.hjy.cloud.t_dictionary.dao;

import com.hjy.cloud.t_dictionary.entity.TDictionaryProvince;

import java.util.List;

/**
 * (TDictionaryProvince)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-25 17:21:54
 */
public interface TDictionaryProvinceMapper {

    /**
     * 分页记录条数
     *
     * @param tDictionaryProvince 实例对象
     * @return 记录条数
     */
    int selectSize(TDictionaryProvince tDictionaryProvince);

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TDictionaryProvince selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tDictionaryProvince 实例对象
     * @return 对象列表
     */
    List<TDictionaryProvince> selectAllPage(TDictionaryProvince tDictionaryProvince);
    /**
     * 查询ID和名称列表
     *
     * @return 对象列表
     */
    List<TDictionaryProvince> selectAllId_Name();
    /**
     * 新增数据
     *
     * @param tDictionaryProvince 实例对象
     * @return 影响行数
     */
    int insertSelective(TDictionaryProvince tDictionaryProvince);

    int insert(TDictionaryProvince tDictionaryProvince);

    /**
     * 修改数据
     *
     * @param tDictionaryProvince 实例对象
     * @return 影响行数
     */
    int updateByPkId(TDictionaryProvince tDictionaryProvince);

    /**
     * 通过主键删除数据
     *
     * @param tDictionaryProvince 实例对象
     * @return 影响行数
     */
    int deleteById(TDictionaryProvince tDictionaryProvince);

}
