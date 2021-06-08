package com.hjy.cloud.t_apv.dao;

import com.hjy.cloud.t_apv.entity.DApvRecord;

import java.util.List;

/**
 * (DApvRecord)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-08 18:09:44
 */
public interface DApvRecordMapper {
    //通过ID查询单条数据
    DApvRecord selectByPkId(String pk_id);

    //通过ID查询是否有数据
    int selectCountByEntity(DApvRecord dApvRecord);

    //通过实体作为筛选条件查询
    List<DApvRecord> selectAllEntity(DApvRecord dApvRecord);

    //新增数据
    int insertSelective(DApvRecord dApvRecord);

    //通锅主键修改数据
    int updateByPkId(DApvRecord dApvRecord);

    //通过实体数据删除数据
    int deleteByEntity(DApvRecord dApvRecord);

    //通过主键删除数据
    int deleteByPkId(String pkId);
}
