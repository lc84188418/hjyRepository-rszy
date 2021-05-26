package com.hjy.cloud.t_system.dao;

import com.hjy.cloud.t_system.entity.SysToken;
import org.apache.ibatis.annotations.Param;

public interface TSysTokenMapper {
    //通过tokenId获取token信息
    SysToken findByToken(@Param("accessToken") String accessToken);

    SysToken selectByUserId(@Param("fkUserId") String fkUserId);

    int insertToken(SysToken tokenEntity);

    int updateToken(SysToken tokenEntity);

    void deleteAll();
    //删除token
    void deleteToken(@Param("tokenId") String tokenId);

    String selectIpByUsername(@Param("username") String username);

    int deleteTokenByIp(@Param("ip")String ip);
}
