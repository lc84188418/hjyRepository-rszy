package com.hjy.cloud.t_system.service;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.t_system.entity.TSysPerms;
import com.hjy.cloud.t_system.entity.TSysRole;
import com.hjy.cloud.t_system.entity.TSysUser;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @Author liuchun
 * @Date 2019/3/30 22:18
 * @Version 1.0
 */
public interface ShiroService {

     /**
      * find token by token
      * @param accessToken
      * @return
      */
     SysToken findByToken(String accessToken);

     //通过角色id从数据库获取权限信息,只含权限码
     List<String> selectPermsCodeByRole(String fkRoleId);

     List<TSysPerms> selectPermsByRole(String fkRoleId);
     /**
      * find user by username
      * @param username
      * @return
      */
     TSysUser selectUserByUsername(String username);

     Map<String ,Object> createToken(TSysUser user, HttpSession session);
     //从数据库获取角色信息
     TSysRole selectRoleByUserId(String pkUserId);

     String selectRoleIdByUserId(String pkUserId);
     //删除token
     void deleteToken(String tokenId);
     //更新最后一次登录时间
     void updateLoginTime(String userId);

    Map<String, Object> selectIndexData(HttpServletResponse resp);

     String selectIpByUsername(String username);
     //文件上传
    CommonResult insertFile(String username, MultipartFile[] files);
}
