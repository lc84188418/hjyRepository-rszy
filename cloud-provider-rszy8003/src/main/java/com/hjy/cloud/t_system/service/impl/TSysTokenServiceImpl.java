package com.hjy.cloud.t_system.service.impl;

import com.hjy.cloud.t_system.dao.TSysTokenMapper;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.t_system.service.TSysTokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liuchun
 * @Package com.hjy.cloud.t_system.service.impl
 * @date 2021/3/8 13:09
 * description:
 */
@Service
public class TSysTokenServiceImpl implements TSysTokenService {
    @Resource
    TSysTokenMapper tSysTokenMapper;
    @Override
    public SysToken selectPkId(String token) {
        return tSysTokenMapper.findByToken(token);
    }
}
