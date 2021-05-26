package com.hjy.cloud.t_system.dao;

import com.hjy.cloud.t_system.entity.TSysPerms;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TSysPerms)表数据库访问层
 *
 * @author liuchun
 * @since 2020-07-24 09:55:41
 */
public interface TSysPermsMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pkPermsId 主键
     * @return 实例对象
     */
    TSysPerms selectById(Object pkPermsId);

    /**
     * 新增数据
     *
     * @param tSysPerms 实例对象
     * @return 影响行数
     */
    int insertSelective(TSysPerms tSysPerms);

    /**
     * 修改数据
     *
     * @param tSysPerms 实例对象
     * @return 影响行数
     */
    int updateById(TSysPerms tSysPerms);

    /**
     * 通过主键删除数据
     *
     * @return 影响行数
     */
    int deleteById(String pk_perms_id);

    /**
     * 查询所有行数据
     * @return 对象列表
     */
    List<TSysPerms> selectAll();
    List<TSysPerms> selectAllIdAndName();
    //获取所有权限码
    List<String> selectAllPermsCode();
     /**
     * 通过实体作为筛选条件查询
     *
     * @param tSysPerms 实例对象
     * @return 对象列表
     */
    List<TSysPerms> selectAllByEntity(TSysPerms tSysPerms);

    List<String> selectDistributeByrole_id(@Param("fk_role_id") String fk_role_id);

    List<TSysPerms> selectAllPage(TSysPerms tSysPerms);

    //获取所有一级菜单
    List<TSysPerms> selectAllFirstLevel();
    //通过pid查询所有菜单
    List<TSysPerms> selectByPId(@Param("pkPermsId")String pkPermsId);
}