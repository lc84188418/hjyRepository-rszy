package com.hjy.cloud.t_apv.dao;

import com.hjy.cloud.t_apv.entity.TApvApvtype;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TApvApvtype)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-26 14:50:49
 */
public interface TApvApvtypeMapper {

    /**
     * 分页记录条数
     *
     * @param tApvApvtype 实例对象
     * @return 记录条数
     */
    int selectSize(TApvApvtype tApvApvtype);

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TApvApvtype selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tApvApvtype 实例对象
     * @return 对象列表
     */
    List<TApvApvtype> selectAllPage(TApvApvtype tApvApvtype);

    /**
     * 新增数据
     *
     * @param tApvApvtype 实例对象
     * @return 影响行数
     */
    int insertSelective(TApvApvtype tApvApvtype);

    int insert(TApvApvtype tApvApvtype);

    /**
     * 修改数据
     *
     * @param tApvApvtype 实例对象
     * @return 影响行数
     */
    int updateByPkId(TApvApvtype tApvApvtype);

    /**
     * 通过主键删除数据
     *
     * @param tApvApvtype 实例对象
     * @return 影响行数
     */
    int deleteById(TApvApvtype tApvApvtype);

    List<TApvApvtype> selectAllId_Name();
    /**
     * 通过名称查询数据
     *
     * @param param
     * @return 实例对象
     */
    TApvApvtype selectByName(@Param("apvtypeName") String param);
}
