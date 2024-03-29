package com.hjy.cloud.t_system.dao;

import com.hjy.cloud.common.entity.User;
import com.hjy.cloud.t_system.entity.ReUserRole;
import com.hjy.cloud.t_system.entity.TSysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TSysUser)表数据库访问层
 *
 * @author liuchun
 * @since 2020-07-24 17:05:59
 */
public interface TSysUserMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pkUserId 主键
     * @return 实例对象
     */
    TSysUser selectById(@Param("pkUserId") String pkUserId);

    TSysUser selectByUsername(@Param("username") String username);

    /**
     * 新增数据
     *
     * @param tSysUser 实例对象
     * @return 影响行数
     */
    int insertSelective(TSysUser tSysUser);

    /**
     * 修改数据
     *
     * @param tSysUser 实例对象
     * @return 影响行数
     */
    int updateById(TSysUser tSysUser);

    /**
     * 通过主键删除数据
     *
     * @param pkUserId 主键
     * @return 影响行数
     */
    int deleteById(@Param("pkUserId") String pkUserId);

    /**
     * 查询所有行数据
     * @return 对象列表
     */
    List<TSysUser> selectAll();
     /**
     * 通过实体作为筛选条件查询
     * @param tSysUser 实例对象
     * @return 对象列表
     */
    List<TSysUser> selectAllByEntity(TSysUser tSysUser);
    List<User> selectAllId_NameByEntity(TSysUser tSysUser);
    /**
     * 通过userid查询用户角色信息
     * @param fk_user_id
     * @return role_id
     */
    String selectUserRoleByUserId(@Param("fkUserId") String fk_user_id);
    /**
     * 通过user_id删除已分配角色数据
     * @param fk_user_id
     */
    int deleteUserRoleByUserId(@Param("fkUserId") String fk_user_id);
    /**
     * 通过用户名查询用户信息
     * @return TSysUser
     */
    TSysUser selectUserByUsername(@Param("username") String username);

    int selectSize(TSysUser user);
    /**
     * 分页查询所有行数据
     * @return 对象列表
     */
    List<TSysUser> selectAllPage(TSysUser user);

    List<String> selectAllUsername();

    int addUserRoleByUserRole(ReUserRole userRole);

    List<User> selectId_NameByIds(@Param("ids")String[] ids);
}