package com.hjy.cloud.t_dictionary.dao;

import com.hjy.cloud.t_dictionary.entity.TDictionaryEducation;

import java.util.List;

/**
 * (TDictionaryEducation)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-25 17:21:51
 */
public interface TDictionaryEducationMapper {

    /**
     * 分页记录条数
     *
     * @param tDictionaryEducation 实例对象
     * @return 记录条数
     */
    int selectSize(TDictionaryEducation tDictionaryEducation);

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TDictionaryEducation selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tDictionaryEducation 实例对象
     * @return 对象列表
     */
    List<TDictionaryEducation> selectAllPage(TDictionaryEducation tDictionaryEducation);
    /**
     * 查询ID和名称列表
     *
     * @return 对象列表
     */
    List<TDictionaryEducation> selectAllId_Name();
    /**
     * 新增数据
     *
     * @param tDictionaryEducation 实例对象
     * @return 影响行数
     */
    int insertSelective(TDictionaryEducation tDictionaryEducation);

    int insert(TDictionaryEducation tDictionaryEducation);

    /**
     * 修改数据
     *
     * @param tDictionaryEducation 实例对象
     * @return 影响行数
     */
    int updateByPkId(TDictionaryEducation tDictionaryEducation);

    /**
     * 通过主键删除数据
     *
     * @param tDictionaryEducation 实例对象
     * @return 影响行数
     */
    int deleteById(TDictionaryEducation tDictionaryEducation);
}
