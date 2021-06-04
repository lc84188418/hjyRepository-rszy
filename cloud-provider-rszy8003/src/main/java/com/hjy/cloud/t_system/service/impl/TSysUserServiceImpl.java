package com.hjy.cloud.t_system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.common.entity.User;
import com.hjy.cloud.common.task.ObjectAsyncTask;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_outfit.dao.TOutfitDeptMapper;
import com.hjy.cloud.t_system.dao.TSysRoleMapper;
import com.hjy.cloud.t_system.dao.TSysUserMapper;
import com.hjy.cloud.t_system.entity.ReDeptUser;
import com.hjy.cloud.t_system.entity.ReUserRole;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.t_system.entity.TSysUser;
import com.hjy.cloud.t_system.service.TSysUserService;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.JsonUtil;
import com.hjy.cloud.utils.PasswordEncryptUtils;
import com.hjy.cloud.utils.StringUtil;
import com.hjy.cloud.utils.page.PageRequest;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.utils.page.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (TSysUser)表服务实现类
 *
 * @author liuchun
 * @since 2020-07-24 17:05:59
 */
@Service
public class TSysUserServiceImpl implements TSysUserService {
    @Resource
    private TSysUserMapper tSysUserMapper;
    @Resource
    private TSysRoleMapper tSysRoleMapper;
    @Resource
    private TOutfitDeptMapper tOutfitDeptMapper;

    /**
     * 通过ID查询单条数据
     *
     * @return 实例对象
     */
    @Override
    public TSysUser selectById(String pkUserId) throws Exception {
        return this.tSysUserMapper.selectById(pkUserId);
    }

    /**
     * 新增数据
     *
     * @param tSysUser 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(TSysUser tSysUser) throws Exception {
        tSysUser.setPkUserId(IDUtils.getUUID());
        //加密
        //默认密码
        String password = "123456";
        String passwordMd5 = PasswordEncryptUtils.MyPasswordEncryptUtil(tSysUser.getUsername(), password);
        tSysUser.setPassword(passwordMd5);
        tSysUser.setCreateTime(new Date());
        tSysUser.setModifyTime(new Date());
        return tSysUserMapper.insertSelective(tSysUser);
    }

    /**
     * 修改数据
     *
     * @param tSysUser 实例对象
     * @return 实例对象
     */
    @Override
    public int updateById(TSysUser tSysUser) throws Exception {
        return tSysUserMapper.updateById(tSysUser);
    }

    /**
     * 通过主键删除数据
     *
     * @param pkUserId 主键
     * @return 是否成功
     */
    @Override
    public int deleteById(String pkUserId) {
        return tSysUserMapper.deleteById(pkUserId);
    }

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    @Override
    public List<TSysUser> selectAll(){
        return this.tSysUserMapper.selectAll();
    }

    /**
     * 通过实体查询多条数据
     *
     * @return 对象列表
     */
    @Override
    public List<TSysUser> selectAllByEntity(TSysUser tSysUser) throws Exception {
        return this.tSysUserMapper.selectAllByEntity(tSysUser);
    }
    @Override
    public List<User> selectAllId_NameByEntity(TSysUser tSysUser) throws Exception {
        return this.tSysUserMapper.selectAllId_NameByEntity(tSysUser);
    }
    @Override
    public String selectUserRoleByUserId(String fk_user_id) {
        return tSysUserMapper.selectUserRoleByUserId(fk_user_id);
    }

    @Override
    public int deleteUserRoleByUserId(String fk_user_id) {
        return tSysUserMapper.deleteUserRoleByUserId(fk_user_id);
    }


    @Override
    public int updatePassword(String parm, SysToken token) throws Exception {
        //用户名
        String username = token.getUsername();
        //数据库旧密码
        String oldPasswordMd5 = token.getPassword();
        JSONObject json = JSON.parseObject(parm);
        //输入的旧密码
        String inputOldPassword = String.valueOf(json.get("oldPassword"));
        //输入的旧密码加密
        String inputOldPasswordMd5 = PasswordEncryptUtils.MyPasswordEncryptUtil(username, inputOldPassword);
        if (!inputOldPasswordMd5.equals(oldPasswordMd5)) {
            return 2;
        }
        //输入的新密码
        String inputNewPassword = String.valueOf(json.get("newPassword"));
        //输入的新密码加密
        String inputNewPasswordMd5 = PasswordEncryptUtils.MyPasswordEncryptUtil(username, inputNewPassword);
        TSysUser user = new TSysUser();
        user.setPkUserId(token.getFkUserId());
        user.setPassword(inputNewPasswordMd5);
        user.setModifyTime(new Date());
        return tSysUserMapper.updateById(user);
    }

    @Override
    @Transactional()
    public Map<String, Object> insertUserAndRoleAndDept(String param) {
        Map<String, Object> result = new HashMap<String, Object>();
        JSONObject json = JSON.parseObject(param);
        String idcard = JsonUtil.getStringParam(json, "idcard");
        String username = JsonUtil.getStringParam(json, "username");
        TSysUser tSysUserExist = tSysUserMapper.selectByUsername(username);
        if (tSysUserExist != null) {
            result.put("code", 445);
            result.put("status", "error");
            result.put("message", "该用户名已存在，请重新输入！");
            return result;
        }
        TSysUser tSysUser = new TSysUser();
        String pkUserId = IDUtils.getUUID();
        tSysUser.setPkUserId(pkUserId);
        String fullName = JsonUtil.getStringParam(json, "fullName");
        String email = JsonUtil.getStringParam(json, "email");
        String tel = JsonUtil.getStringParam(json, "tel");
        String enableStatus = JsonUtil.getStringParam(json, "enableStatus");
        String address = JsonUtil.getStringParam(json, "address");
        String workPosition = JsonUtil.getStringParam(json, "workPosition");
        String workContent = JsonUtil.getStringParam(json, "workContent");
        tSysUser.setUsername(username);
        tSysUser.setIdcard(idcard);
        tSysUser.setFullName(fullName);
        tSysUser.setEmail(email);
        tSysUser.setTel(tel);
        if(StringUtil.isNotEmptyAndNull(enableStatus)){
            tSysUser.setEnableStatus(enableStatus);
        }else{
            tSysUser.setEnableStatus("0");
        }
        tSysUser.setAddress(address);
        tSysUser.setWorkPosition(workPosition);
        tSysUser.setWorkContent(workContent);
        //加密
        //默认密码
        String password = "123456";
        String passwordMd5 = PasswordEncryptUtils.MyPasswordEncryptUtil(tSysUser.getUsername(), password);
        tSysUser.setPassword(passwordMd5);
        tSysUser.setCreateTime(new Date());
        tSysUser.setModifyTime(new Date());
        tSysUserMapper.insertSelective(tSysUser);
        //是否直接分配角色
        String roleId = JsonUtil.getStringParam(json, "roleId");
        //是否直接分配部门
        String deptId = JsonUtil.getStringParam(json, "deptId");
        if (roleId == null && deptId == null) {
            result.put("code", 201);
            result.put("status", "success");
            result.put("message", "添加用户成功,但暂未分配角色，无法使用！");
        } else if (roleId == null && deptId != null) {
            this.addDeptUserByDeptUser(pkUserId, deptId);
            result.put("code", 202);
            result.put("status", "success");
            result.put("message", "添加用户成功,分配部门成功，但暂未分配角色，无法使用！");
        } else if (roleId != null && deptId == null) {
            this.addUserRoleByUserRole(pkUserId, roleId);
            result.put("code", 203);
            result.put("status", "success");
            result.put("message", "添加用户与分配角色成功,暂未分配部门！");
        } else if (roleId != null && deptId != null) {
            this.addUserRoleByUserRole(pkUserId, roleId);
            this.addDeptUserByDeptUser(pkUserId, deptId);
            result.put("code", 200);
            result.put("status", "success");
            result.put("message", "添加用户、分配角色与分配部门成功！");
        }
        return result;
    }

    @Override
    public int updateUser(String param) {
        JSONObject json = JSON.parseObject(param);
        TSysUser user = new TSysUser();
        //用户基本信息
        String pkUserId = JsonUtil.getStringParam(json, "pkUserId");
        user.setPkUserId(pkUserId);
        String username = JsonUtil.getStringParam(json, "username");
        user.setUsername(username);
        String fkDeptId = JsonUtil.getStringParam(json, "fkDeptId");
        String email = JsonUtil.getStringParam(json, "email");
        user.setEmail(email);
        String tel = JsonUtil.getStringParam(json, "tel");
        user.setTel(tel);
        String IDcard = JsonUtil.getStringParam(json, "idcard");
        user.setIdcard(IDcard);
        String fullName = JsonUtil.getStringParam(json, "fullName");
        user.setFullName(fullName);
        String workPosition = JsonUtil.getStringParam(json, "workPosition");
        user.setWorkPosition(workPosition);
        String workContent = JsonUtil.getStringParam(json, "workContent");
        user.setWorkContent(workContent);
        String ip = JsonUtil.getStringParam(json, "ip");
        user.setIp(ip);
        String address = JsonUtil.getStringParam(json, "address");
        user.setAddress(address);
        String enableStatus = JsonUtil.getStringParam(json, "enableStatus");
        user.setEnableStatus(enableStatus);
        user.setModifyTime(new Date());
        String fkRoleId = JsonUtil.getStringParam(json, "roleId");
        //修改用户信息
        if (fkRoleId != null && fkDeptId == null) {
            tSysUserMapper.updateById(user);
            //删除原有角色
            tSysUserMapper.deleteUserRoleByUserId(pkUserId);
            ObjectAsyncTask.addUserRoleByUserRole(pkUserId, fkRoleId);
            return 2;
        } else if (fkRoleId != null && fkDeptId != null) {
            tSysUserMapper.updateById(user);
            //删除原有角色
            tSysUserMapper.deleteUserRoleByUserId(pkUserId);
            ObjectAsyncTask.addUserRoleByUserRole(pkUserId, fkRoleId);
            //删除原有部门信息
            tOutfitDeptMapper.deleteDeptUserByUserId(pkUserId);
            ObjectAsyncTask.addDeptUserByDeptUser(pkUserId, fkDeptId);
            return 3;
        } else if (fkRoleId == null && fkDeptId != null) {
            //删除原有部门信息
            tOutfitDeptMapper.deleteDeptUserByUserId(pkUserId);
            ObjectAsyncTask.addDeptUserByDeptUser(pkUserId, fkDeptId);
            return 2;
        } else {
            return 1;
        }
    }

    @Override
    public void addUserRoleByUserRole(ReUserRole userRole) {
        tSysRoleMapper.addUserRoleByUserRole(userRole);
    }

    @Override
    public CommonResult tSysUserDel(String pkUserId) {
        if ("1597387976992".equals(pkUserId)) {
            return new CommonResult(445, "error", "超级管理员不可删除!", null);
        }
        //删除用户表里的用户
        int i = tSysUserMapper.deleteById(pkUserId);
        //删除用户角色表里的用户
        int j = tSysUserMapper.deleteUserRoleByUserId(pkUserId);
        //删除用户部门表里的用户
        int k = tOutfitDeptMapper.deleteDeptUserByUserId(pkUserId);
        if (i > 0) {
            return new CommonResult(200, "success", "数据删除成功!", null);
        } else {
            return new CommonResult(444, "error", "数据已被删除，无需再次请求!", null);
        }
    }

    /**
     *
     * @param tSysUser
     * @return
     */
    @Override
    public CommonResult resetPassword(TSysUser tSysUser) {
        tSysUser.setModifyTime(new Date());
        tSysUser.setPassword(PasswordEncryptUtils.MyPasswordEncryptUtil(null,"123456"));
        tSysUserMapper.updateById(tSysUser);
        return new CommonResult(200,"success","重置密码成功!",null);
    }

    @Override
    public CommonResult<PageResult<TSysUser>> tSysUserList(PageRequest<TSysUser> pageInfo) {
        if(pageInfo.getPageNum() == 0){
            pageInfo.setPageNum(1);
        }if(pageInfo.getPageSize() == 0){
            pageInfo.setPageNum(10);
        }
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        List<TSysUser> list = tSysUserMapper.selectAllByEntity(pageInfo.getParam());
        PageResult<TSysUser> result = PageUtil.getPageResult(new PageInfo<TSysUser>(list));
        return new CommonResult(200,"success","查询用户数据成功!",result);
    }

    public int addDeptUserByDeptUser(String pkUserId, String deptId) {
        ReDeptUser deptUser = new ReDeptUser();
        deptUser.setPk_deptUser_id(IDUtils.getUUID());
        deptUser.setFk_user_id(pkUserId);
        deptUser.setFk_dept_id(deptId);
        return tOutfitDeptMapper.addDeptUserByDeptUser(deptUser);
    }

    public int addUserRoleByUserRole(String pkUserId, String roleId) {
        ReUserRole userRole = new ReUserRole();
        userRole.setPk_userRole_id(IDUtils.getUUID());
        userRole.setFk_user_id(pkUserId);
        userRole.setFk_role_id(roleId);
        return tSysUserMapper.addUserRoleByUserRole(userRole);
    }
}