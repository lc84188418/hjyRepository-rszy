package com.hjy.cloud.t_dictionary.dao;

import com.hjy.cloud.t_dictionary.entity.TDictionarySurvey;

import java.util.List;

/**
 * (TDictionarySurvey)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-25 17:21:55
 */
public interface TDictionarySurveyMapper {

    /**
     * 分页记录条数
     *
     * @param tDictionarySurvey 实例对象
     * @return 记录条数
     */
    int selectSize(TDictionarySurvey tDictionarySurvey);

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TDictionarySurvey selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tDictionarySurvey 实例对象
     * @return 对象列表
     */
    List<TDictionarySurvey> selectAllPage(TDictionarySurvey tDictionarySurvey);
    /**
     * 查询ID和名称列表
     *
     * @return 对象列表
     */
    List<TDictionarySurvey> selectAllId_Name();
    /**
     * 新增数据
     *
     * @param tDictionarySurvey 实例对象
     * @return 影响行数
     */
    int insertSelective(TDictionarySurvey tDictionarySurvey);

    int insert(TDictionarySurvey tDictionarySurvey);

    /**
     * 修改数据
     *
     * @param tDictionarySurvey 实例对象
     * @return 影响行数
     */
    int updateByPkId(TDictionarySurvey tDictionarySurvey);

    /**
     * 通过主键删除数据
     *
     * @param tDictionarySurvey 实例对象
     * @return 影响行数
     */
    int deleteById(TDictionarySurvey tDictionarySurvey);
}
