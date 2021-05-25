package com.hjy.cloud.t_outfit.dao;

import com.hjy.cloud.t_outfit.entity.TOutfitWorkaddress;

import java.util.List;

/**
 * (TOutfitWorkaddress)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-24 10:59:42
 */
public interface TOutfitWorkaddressMapper {

    /**
     * 分页记录条数
     *
     * @param tOutfitWorkaddress 实例对象
     * @return 记录条数
     */
    int selectSize(TOutfitWorkaddress tOutfitWorkaddress);

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TOutfitWorkaddress selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tOutfitWorkaddress 实例对象
     * @return 对象列表
     */
    List<TOutfitWorkaddress> selectAllPage(TOutfitWorkaddress tOutfitWorkaddress);

    /**
     * 新增数据
     *
     * @param tOutfitWorkaddress 实例对象
     * @return 影响行数
     */
    int insertSelective(TOutfitWorkaddress tOutfitWorkaddress);

    int insert(TOutfitWorkaddress tOutfitWorkaddress);

    /**
     * 修改数据
     *
     * @param tOutfitWorkaddress 实例对象
     * @return 影响行数
     */
    int updateByPkId(TOutfitWorkaddress tOutfitWorkaddress);

    /**
     * 通过主键删除数据
     *
     * @param tOutfitWorkaddress 实例对象
     * @return 影响行数
     */
    int deleteById(TOutfitWorkaddress tOutfitWorkaddress);

    List<TOutfitWorkaddress> selectAllId_Name();
}
