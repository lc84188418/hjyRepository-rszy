package com.hjy.cloud.t_outfit.dao;

import com.hjy.cloud.t_outfit.entity.ReCompanyDept;
import com.hjy.cloud.t_outfit.entity.TOutfitCompany;

import java.util.List;

/**
 * (TOutfitCompany)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-23 15:33:46
 */
public interface TOutfitCompanyMapper {

    /**
     * 分页记录条数
     *
     * @param tOutfitCompany 实例对象
     * @return 记录条数
     */
    int selectSize(TOutfitCompany tOutfitCompany);


    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TOutfitCompany selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tOutfitCompany 实例对象
     * @return 对象列表
     */
    List<TOutfitCompany> selectAllPage(TOutfitCompany tOutfitCompany);

    /**
     * 新增数据
     *
     * @param tOutfitCompany 实例对象
     * @return 影响行数
     */
    int insertSelective(TOutfitCompany tOutfitCompany);
    int insert(TOutfitCompany tOutfitCompany);

    /**
     * 修改数据
     *
     * @param tOutfitCompany 实例对象
     * @return 影响行数
     */
    int updateByPkId(TOutfitCompany tOutfitCompany);

    /**
     * 通过主键删除数据
     *
     * @param tOutfitCompany 实例对象
     * @return 影响行数
     */
    int deleteById(TOutfitCompany tOutfitCompany);
    /**
     * 只查询名字和I主键
     *
     * @return 对象列表
     */
    List<TOutfitCompany> select_PkId_name();
    /**
     * 获取该公司已经分配的部门ID
     *
     * @return 已分配的部门ID值列表
     */
    List<String> selectFPDeptId(String pkCompanyId);
    /**
     * 删除原有的公司及部门
     *
     * @return 公司ID
     */
    void deleteCompanyByCompanyId(String fkCompanyId);
    /**
     * 批量添加公司部门信息
     *
     * @return 添加数据条数
     */
    int addCompanyDeptByList(List<ReCompanyDept> companyDepts);
}