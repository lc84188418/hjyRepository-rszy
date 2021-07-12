package com.hjy.cloud.t_system.service;


import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.t_system.entity.TSysPerms;
import com.hjy.cloud.t_system.entity.TSysRole;
import com.hjy.cloud.t_system.entity.TSysUser;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @Author liuchun
 * @Date 2019/3/30 22:18
 * @Version 1.0
 */
public interface ShiroService {

     //find token by tokenid
     SysToken findByToken(String accessToken);

     //find user by username
     TSysUser selectUserByUsername(String username);

     //登录时创建一个token，并保持数据库
     Map<String ,Object> createToken(TSysUser user, HttpSession session);

     //通过用户id库获取角色信息
     TSysRole selectRoleByUserId(String pkUserId);

     //删除token
     void deleteToken(String tokenId);

     //更新最后一次登录时间
     void updateLoginTime(String userId);

     String selectIpByUsername(String username);
    //获取所有菜单
    List<TSysPerms> selectAllperms();

    //获取所有权限码
    List<String> selectAllPermsCode();

    //通过角色id查询菜单
    List<TSysPerms> selectPermsByRole(String fkRoleId);

    //通过角色id从数据库获取权限码
    List<String> selectPermsCodeByRole(String fkRoleId);
    //直接获取管理员账户
    TSysUser selectAdminUser();
}
