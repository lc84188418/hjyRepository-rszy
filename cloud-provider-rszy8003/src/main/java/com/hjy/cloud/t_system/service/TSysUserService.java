package com.hjy.cloud.t_system.service;

import com.hjy.cloud.common.entity.User;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_system.entity.ReUserRole;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.t_system.entity.TSysUser;
import com.hjy.cloud.utils.page.PageRequest;
import com.hjy.cloud.utils.page.PageResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * (TSysUser)表服务接口
 *
 * @author liuchun
 * @since 2020-07-24 17:05:59
 */
public interface TSysUserService {

    /**
     * 通过ID查询单条数据
     * @return 实例对象
     */
    TSysUser selectById(String pkUserId)throws Exception;


    /**
     * 新增数据
     * @param tSysUser 实例对象
     * @return 实例对象
     */
    int insert(TSysUser tSysUser) throws Exception;

    /**
     * 修改数据
     *
     * @param tSysUser 实例对象
     * @return 实例对象
     */
    CommonResult updateById(TSysUser tSysUser, HttpServletRequest request) throws Exception;

    /**
     * 通过主键删除数据
     *
     * @param pkUserId 主键
     * @return 是否成功
     */
    int deleteById(String pkUserId);

    /**
     * 查询所有数据
     * @return list
     */
    List<TSysUser> selectAll();
    /**
     * 通过实体查询所有数据
     * @return list
     */
    List<TSysUser> selectAllByEntity(TSysUser tSysUser)throws Exception;
    List<User> selectAllId_NameByEntity(TSysUser tSysUser)throws Exception;
    /**
     * 通过userId查询已分配角色数据
     */
    String selectUserRoleByUserId(String fk_user_id);
    /**
     * 通过userId删除已分配角色数据
     */
    int deleteUserRoleByUserId(String fk_user_id);
    /**
     * 分页查询所有数据
     * @return list
     */
    CommonResult<PageResult<TSysUser>> tSysUserList(PageRequest<TSysUser> pageInfo);

    /**
     * 修改密码
     */
    int updatePassword(String parm, SysToken token) throws Exception;
    /**
     * 添加用户且添加角色
     */
    Map<String,Object> insertUserAndRoleAndDept(String param);
    /**
     * 修改用户且修改角色、部门
     */
    int updateUser(String param);
    /**
     * 向用户-角色关联表中添加一条用户角色信息
     * @param userRole 用户角色
     */
    void addUserRoleByUserRole(ReUserRole userRole);
    //删除用户
    CommonResult tSysUserDel(String param);
    //重置密码
    CommonResult resetPassword(TSysUser tSysUser);

//    CommonResult distributeRole(String parm);
//
//    CommonResult userDel(String param);
}