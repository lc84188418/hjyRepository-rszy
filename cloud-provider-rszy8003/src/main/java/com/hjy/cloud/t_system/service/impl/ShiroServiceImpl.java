package com.hjy.cloud.t_system.service.impl;


import com.hjy.cloud.common.auth.TokenGenerator;
import com.hjy.cloud.t_system.dao.TSysPermsMapper;
import com.hjy.cloud.t_system.dao.TSysRoleMapper;
import com.hjy.cloud.t_system.dao.TSysTokenMapper;
import com.hjy.cloud.t_system.dao.TSysUserMapper;
import com.hjy.cloud.t_system.entity.*;
import com.hjy.cloud.t_system.service.ShiroService;
import com.hjy.cloud.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @Author liuchun
 * @Date 2019/3/30 22:18
 * @Version 1.0
 */
@Service
public class ShiroServiceImpl implements ShiroService {
    //24小时后失效
    private final static int EXPIRE = 24;

    @Resource
    private TSysUserMapper tSysUserMapper;
    @Resource
    private TSysRoleMapper tSysRoleMapper;
    @Resource
    private TSysPermsMapper tSysPermsMapper;
    @Resource
    private TSysTokenMapper tSysTokenMapper;

    /**
     * 通过用户id库获取角色信息
     * @param pkUserId
     * @return
     */
    @Override
    public TSysRole selectRoleByUserId(String pkUserId) {
        return tSysRoleMapper.selectRoleByUserId(pkUserId);
    }

    /**
     * 删除token
     * @param tokenId
     */
    @Override
    public void deleteToken(String tokenId) {
        tSysTokenMapper.deleteToken(tokenId);
    }

    /**
     * 更新最后一次登录时间
     * @param userId
     */
    @Override
    public void updateLoginTime(String userId) {
        TSysUser user = new TSysUser();
        user.setPkUserId(userId);
        user.setLastLoginDate(new Date());
        tSysUserMapper.updateById(user);
    }

    @Override
    public String selectIpByUsername(String username) {
        return tSysTokenMapper.selectIpByUsername(username);
    }

    /**
     * 获取所有菜单
     * @return
     */
    @Override
    public List<TSysPerms> selectAllperms() {
        List<TSysPerms> firstLevelList = tSysPermsMapper.selectAllFirstLevel();
        //二级菜单
        Iterator<TSysPerms> firstIterator = firstLevelList.iterator();
        while (firstIterator.hasNext()){
            TSysPerms first = firstIterator.next();
            List<TSysPerms> secondLevelList = tSysPermsMapper.selectByPId(first.getPkPermsId());
            //三级菜单
            Iterator<TSysPerms> secondIterator = secondLevelList.iterator();
            while (secondIterator.hasNext()) {
                TSysPerms second = secondIterator.next();
                List<TSysPerms> thirdLevelList = tSysPermsMapper.selectByPId(second.getPkPermsId());
                second.setChild(thirdLevelList);
            }
            first.setChild(secondLevelList);
        }
        return firstLevelList;
    }

    /**
     * 通过角色id从数据库获取权限码
     * @param fkRoleId
     * @return
     */
    @Override
    public List<String> selectPermsCodeByRole(String fkRoleId){
        return tSysRoleMapper.selectPermsCodeByRole(fkRoleId);
    }

    /**
     * 获取所有权限码
     * @return
     */
    @Override
    public List<String> selectAllPermsCode() {
        return tSysPermsMapper.selectAllPermsCode();
    }

    /**
     * 通过角色id查询菜单
     * @param fkRoleId
     * @return
     */
    @Override
    public List<TSysPerms> selectPermsByRole(String fkRoleId) {
        return tSysRoleMapper.selectPermsByRole(fkRoleId);
    }

    /**
     * find user by username
     * @param username
     * @return
     */
    @Override
    public TSysUser selectUserByUsername(String username) {
        return tSysUserMapper.selectUserByUsername(username);
    }

    /**
     * 登录时创建一个token，并保持数据库
     * @param tSysUser
     * @param session
     * @return
     */
    @Override
    public Map<String, Object> createToken(TSysUser tSysUser, HttpSession session) {
        Map<String, Object> result = new HashMap<String, Object>();
        //生成一个token（session）
        String token = TokenGenerator.generateValue();
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = DateUtil.addTime(now,1);
        //先删除
        tSysTokenMapper.deleteTokenByIp(tSysUser.getIp());
        //再添加
        SysToken tokenEntity = new SysToken();
        tokenEntity.setFkUserId(tSysUser.getPkUserId());
        //保存token
        tokenEntity.setPkTokenId(token);
        tokenEntity.setUpdateTime(now);
        tokenEntity.setExpireTime(expireTime);
        tokenEntity.setUsername(tSysUser.getUsername());
        tokenEntity.setPassword(tSysUser.getPassword());
        tokenEntity.setIp(tSysUser.getIp());
        tokenEntity.setFullName(tSysUser.getFullName());
        tokenEntity.setIdcard(tSysUser.getIdcard());
        tSysTokenMapper.insertToken(tokenEntity);
        result.put("token", token);
        result.put("expire", expireTime);
        /**
         * 暂时手动添加ActiveUser信息
         */
        ActiveUser activeUser = new ActiveUser();
        activeUser.setUserId(tSysUser.getPkUserId());
        activeUser.setTokenId(token);
        activeUser.setUsername(tSysUser.getUsername());
        activeUser.setPassword(tSysUser.getPassword());
        activeUser.setTel(tSysUser.getTel());
        activeUser.setIDcard(tSysUser.getIdcard());
        activeUser.setFullName(tSysUser.getFullName());
        session.setAttribute("activeUser",activeUser);
        result.put("data",tSysUser);
        return result;
    }

    /**
     * find token by tokenid
     * @param accessToken
     * @return
     */
    @Override
    public SysToken findByToken(String accessToken) {
        return tSysTokenMapper.findByToken(accessToken);
    }

}
