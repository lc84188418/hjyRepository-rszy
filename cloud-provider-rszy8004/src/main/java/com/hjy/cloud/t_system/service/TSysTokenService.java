package com.hjy.cloud.t_system.service;

import com.hjy.cloud.t_system.entity.SysToken;

/**
 * @author liuchun
 * @Package com.hjy.cloud.t_system.service
 * @date 2021/3/8 13:09
 * description:
 */
public interface TSysTokenService {

    SysToken selectPkId(String token);
}
