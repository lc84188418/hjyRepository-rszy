package com.hjy.cloud.dictionary.dao;

import com.hjy.cloud.dictionary.entity.TDictionaryHtlx;
import com.hjy.cloud.dictionary.entity.TDictionaryNation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TDictionaryNation)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-25 17:21:53
 */
public interface TDictionaryNationMapper {

    /**
     * 分页记录条数
     *
     * @param tDictionaryNation 实例对象
     * @return 记录条数
     */
    int selectSize(TDictionaryNation tDictionaryNation);

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TDictionaryNation selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tDictionaryNation 实例对象
     * @return 对象列表
     */
    List<TDictionaryNation> selectAllPage(TDictionaryNation tDictionaryNation);
    /**
     * 查询ID和名称列表
     *
     * @return 对象列表
     */
    List<TDictionaryNation> selectAllId_Name();
    /**
     * 新增数据
     *
     * @param tDictionaryNation 实例对象
     * @return 影响行数
     */
    int insertSelective(TDictionaryNation tDictionaryNation);

    int insert(TDictionaryNation tDictionaryNation);
    int insertBatch(@Param("nameList")List<TDictionaryNation> list);


    /**
     * 修改数据
     *
     * @param tDictionaryNation 实例对象
     * @return 影响行数
     */
    int updateByPkId(TDictionaryNation tDictionaryNation);

    /**
     * 通过主键删除数据
     *
     * @param tDictionaryNation 实例对象
     * @return 影响行数
     */
    int deleteById(TDictionaryNation tDictionaryNation);

}
