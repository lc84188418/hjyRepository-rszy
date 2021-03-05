package com.hjy.cloud.t_system.service.impl;


import com.hjy.cloud.common.auth.TokenGenerator;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.DateUtil;
import com.hjy.cloud.t_system.dao.TSysRoleMapper;
import com.hjy.cloud.t_system.dao.TSysTokenMapper;
import com.hjy.cloud.t_system.dao.TSysUserMapper;
import com.hjy.cloud.t_system.entity.*;
import com.hjy.cloud.t_system.service.ShiroService;
import com.hjy.cloud.utils.file.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private TSysTokenMapper tSysTokenMapper;
    /**
     * 根据userid查找角色
     *
     * @return User
     */
    @Override
    public TSysRole selectRoleByUserId(String pkUserId) {
        return tSysRoleMapper.selectRoleByUserId(pkUserId);
    }

    @Override
    public String selectRoleIdByUserId(String fkUserId) {
        return tSysRoleMapper.selectRoleIdByUserId(fkUserId);
    }

    @Override
    public void deleteToken(String tokenId) {
        tSysTokenMapper.deleteToken(tokenId);
    }
    /**
     * 更新最后一次登录时间
     */
    @Override
    public void updateLoginTime(String userId) {
        TSysUser user = new TSysUser();
        user.setPkUserId(userId);
        user.setLastLoginDate(new Date());
        tSysUserMapper.updateById(user);
    }

    @Override
    public Map<String, Object> selectIndexData(HttpServletResponse resp) {
        return null;
    }

    @Override
    public String selectIpByUsername(String username) {
        return tSysTokenMapper.selectIpByUsername(username);
    }

    @Override
    public CommonResult insertFile(String username, MultipartFile[] files) {
        if(files != null){
            String [] strings = FileUtil.FileUtil(username,files);
        }
        return null;
    }

    /**
     * 通过roleid查找权限码
     */
    @Override
    public List<String> selectPermsCodeByRole(String fkRoleId){
        return tSysRoleMapper.selectPermsCodeByRole(fkRoleId);
    }
    @Override
    public List<TSysPerms> selectPermsByRole(String fkRoleId) {
        return tSysRoleMapper.selectPermsByRole(fkRoleId);
    }
    /**
     * 根据username查找用户
     * @return User
     */
    @Override
    public TSysUser selectUserByUsername(String username) {
        return tSysUserMapper.selectUserByUsername(username);
    }

    /**
     * 生成一个token
     *@return Result
     */
    @Override
    public Map<String, Object> createToken(TSysUser tSysUser, HttpSession session) {
        Map<String, Object> result = new HashMap<String, Object>();
        //生成一个token（session）
        String token = TokenGenerator.generateValue();
        //当前时间
        Date now = new Date();
        //过期时间
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

    @Override
    public SysToken findByToken(String accessToken) {
        return tSysTokenMapper.findByToken(accessToken);
    }

}
