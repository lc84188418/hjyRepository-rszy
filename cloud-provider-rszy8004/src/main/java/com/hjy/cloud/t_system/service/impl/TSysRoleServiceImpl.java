package com.hjy.cloud.t_system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.common.task.ObjectAsyncTask;
import com.hjy.cloud.t_system.entity.TSysUser;
import com.hjy.cloud.t_system.service.TSysUserService;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.t_system.entity.ReRolePerms;
import com.hjy.cloud.t_system.entity.ReUserRole;
import com.hjy.cloud.t_system.entity.TSysRole;
import com.hjy.cloud.t_system.dao.TSysRoleMapper;
import com.hjy.cloud.t_system.service.TSysRoleService;
import com.hjy.cloud.utils.JsonUtil;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.utils.page.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (TSysRole)表服务实现类
 *
 * @author liuchun
 * @since 2020-07-23 12:59:10
 */
@Service
public class TSysRoleServiceImpl implements TSysRoleService {
    @Resource
    private TSysRoleMapper tSysRoleMapper;
    @Resource
    private TSysUserService tSysUserService;
    /**
     * 通过ID查询单条数据
     *
     * @param pkRoleId 主键
     * @return 实例对象
     */
    @Override
    public TSysRole selectById(String pkRoleId) {
        return this.tSysRoleMapper.selectById(pkRoleId);
    }

    /**
     * 新增数据
     *
     * @param tSysRole 实例对象
     * @return 实例对象
     */
    @Transactional()
    @Override
    public CommonResult insert(TSysRole tSysRole) {
        //通过工具类IDUtils获取主键
        String uuid= IDUtils.getUUID();
        tSysRole.setPkRoleId(uuid);
        //设置创建时间和更改时间
        tSysRole.setCreateDate(new Date());
        tSysRole.setModifyTime(new Date());
        int i = tSysRoleMapper.insertSelective(tSysRole);
        if (i > 0) {
            JSONObject resultJson = this.getListInfo();
            return new CommonResult(200, "success", "添加数据成功", resultJson);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
        //为新角色默认添加主页权限
//        ObjectAsyncTask.addDefultRoelPerms(uuid);
    }



    /**
     * 修改数据
     *
     * @param tSysRole 实例对象
     * @return 实例对象
     */
    @Transactional()
    @Override
    public int updateById(TSysRole tSysRole) {
        tSysRole.setModifyTime(new Date());
        return tSysRoleMapper.updateById(tSysRole);
    }

    /**
     * 通过主键删除数据
     *
     * @param pkRoleId 主键
     * @return 是否成功
     */
    @Transactional()
    @Override
    public int deleteById(String pkRoleId) {
        return tSysRoleMapper.deleteById(pkRoleId);
    }

    /**
     * 查询多条数据
     * @return 对象列表
     */
    @Override
    public CommonResult selectAll(String param) {
        JSONObject json = JSON.parseObject(param);
        //查询条件
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        TSysRole entity = new TSysRole();

        //分页记录条数
        int pageNum = 1;
        int pageSize = 10;
        if (pageNumStr != null) {
            pageNum = Integer.parseInt(pageNumStr);
        }
        if (pageSizeStr != null) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<TSysRole> list = this.tSysRoleMapper.selectAllByEntity(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TSysRole>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
    @Override
    public List<TSysRole> selectAll() {
        return tSysRoleMapper.selectAll();
    }
    /**
     * 通过实体查询多条数据
     * @return 对象列表
     */
    @Override
    public List<TSysRole> selectAllByEntity(TSysRole tSysRole) {
        return this.tSysRoleMapper.selectAllByEntity(tSysRole);
    }
    @Transactional()
    @Override
    public int deleteRolePermsByRoleId(String fk_role_id) {
        return tSysRoleMapper.deleteRolePermsByRoleId(fk_role_id);
    }
    @Transactional()
    @Override
    public int addRoleMenu(String fk_role_id,String str1) {
        //此处字符串比正常数组多出一对[],要先去除；
        String str2 = str1.substring(1);
        String idsStr = str2.substring(0,str2.length()-1);
        //此时得到的数组还有双引号
        String[] idsStrArray = idsStr.split(",");
        String[] ids = new String[idsStrArray.length];
        for(int i=0; i < idsStrArray.length; i++)
        {
            ids[i] = String.valueOf(idsStrArray[i]);
        }
        int i=0;
        for (String str : ids) {
            String fk_perms_id= str.replace("\"", "");
            String pk_rolePerms_id = IDUtils.getUUID();
            i = tSysRoleMapper.addRoleMenu(pk_rolePerms_id,fk_role_id,fk_perms_id);
        }
        return i;
    }
    @Transactional()
    @Override
    public int addRoleMenuByList(String fk_role_id, List<String> idList) {
        List<ReRolePerms> rolePermsList = new ArrayList<ReRolePerms>();
        for (String s:idList){
            ReRolePerms rolePerms = new ReRolePerms();
            rolePerms.setPk_rolePerms_id(IDUtils.getUUID());
            rolePerms.setFk_role_id(fk_role_id);
            rolePerms.setFk_perms_id(s);
            rolePermsList.add(rolePerms);
        }
        return tSysRoleMapper.addRoleMenuByList(rolePermsList);
    }

    @Override
    public List<String> selectUserRoleByrole_id(String fk_role_id) {
        return tSysRoleMapper.selectUserRoleByrole_id(fk_role_id);
    }

    @Transactional()
    @Override
    public int deleteUserRoleByRoleId(String fk_role_id) {
        return tSysRoleMapper.deleteUserRoleByRoleId(fk_role_id);
    }

    @Transactional()
    @Override
    public int addUserRole(String fk_role_id, String userIds) {
        //此处字符串比正常数组多出一对[],要先去除；
        String str2 = userIds.substring(1);
        String idsStr = str2.substring(0,str2.length()-1);
        //此时得到的数组还有双引号
        String[] idsStrArray = idsStr.split(",");
        String[] ids = new String[idsStrArray.length];
        for(int i=0; i < idsStrArray.length; i++)
        {
            ids[i] = String.valueOf(idsStrArray[i]);
        }
        int i=0;
        //普通逐条插入
        for (String str : ids) {
            String fk_user_id= str.replace("\"", "");
            String pk_userRole_id = IDUtils.getUUID();
            i = tSysRoleMapper.addUserRole(pk_userRole_id,fk_user_id,fk_role_id);
        }
        return i;
    }

//    @Transactional()
//    @Override
    public int addUserRoleByList(String fk_role_id, List<String> idList) {
        List<ReUserRole> userRoles = new ArrayList<ReUserRole>();
        for (String s:idList){
            ReUserRole userRole = new ReUserRole();
            userRole.setPk_userRole_id(IDUtils.getUUID());
            userRole.setFk_user_id(s);
            userRole.setFk_role_id(fk_role_id);
            userRoles.add(userRole);
        }
        return tSysRoleMapper.addUserRoleByList(userRoles);
    }

    @Transactional()
    @Override
    public int addUserRoleByUserRole(ReUserRole userRole) {
        return tSysRoleMapper.addUserRoleByUserRole(userRole);
    }

    @Override
    public List<String> selectUserRole_userIded() {
        return tSysRoleMapper.selectUserRole_userIded();
    }

    @Override
    public TSysRole selectRoleByUserId(String pkUserId) {
        return tSysRoleMapper.selectRoleByUserId(pkUserId);
    }

    @Override
    public String selectRoleIdByUserId(String idStr) {
        return tSysRoleMapper.selectRoleIdByUserId(idStr);
    }

    @Transactional()
    @Override
    public void distributeMenu(String fk_role_id, List<String> idList) {
        if(idList != null){
            //通过role_id删除原有的perms
            ObjectAsyncTask.deleteRolePermsByRoleId(fk_role_id);
        }
        //添加选中的权限菜单
        List<ReRolePerms> rolePermsList = new ArrayList<ReRolePerms>();
        for (String s:idList){
            ReRolePerms rolePerms = new ReRolePerms();
            rolePerms.setPk_rolePerms_id(IDUtils.getUUID());
            rolePerms.setFk_role_id(fk_role_id);
            rolePerms.setFk_perms_id(s);
            rolePermsList.add(rolePerms);
        }
        tSysRoleMapper.addRoleMenuByList(rolePermsList);
    }

    @Transactional()
    @Override
    public CommonResult systemRoleAddUser(String param) {
        JSONObject jsonObject = JSON.parseObject(param);
        String pkRoleId=String.valueOf(jsonObject.get("pkRoleId"));
        //删除原有的用户角色
        int i = tSysRoleMapper.deleteUserRoleByRoleId(pkRoleId);
        int code = 200;
        String msg = "该角色已成功添加0个用户!";
        JSONArray jsonArray = jsonObject.getJSONArray("ids");
        String userIdsStr = jsonArray.toString();
        if(userIdsStr.equals("[]")){
            code = 201;
            msg = "未选择用户，请选择后添加！";
        }else {
            List<String> idList = JSONArray.parseArray(userIdsStr,String.class);
            //添加用户角色
            int j = this.addUserRoleByList(pkRoleId,idList);
            msg = msg.replace("0",String.valueOf(idList.size()));
        }
        if(i>0){
            code = 200;
        }else {
            code = 203;
        }
        return new CommonResult(code,"success",msg,null);
    }

    @Transactional()
    @Override
    public CommonResult roleDel(String param) {
        JSONObject jsonObject = JSON.parseObject(param);
        String idStr=String.valueOf(jsonObject.get("pkRoleId"));
        if(idStr.equals("1595564064909") || idStr.equals("1598010216782")){
            return new CommonResult(445,"error","超级管理员和普通用户不可删除!",null);
        }
        //删除角色表里的数据
        int i = tSysRoleMapper.deleteById(idStr);
        //删除用户角色表里的数据
        int j = tSysRoleMapper.deleteUserRoleByRoleId(idStr);
        //删除角色权限表里的数据
        int k = tSysRoleMapper.deleteRolePermsByRoleId(idStr);
        // && k > 0
        if(i > 0 && j >= 0){
            JSONObject resultJson = this.getListInfo();
            return new CommonResult(200,"success","角色删除成功!",resultJson);
        }else {
            return new CommonResult(444,"error","角色删除失败!",null);
        }
    }

    @Override
    public CommonResult selectByPkId(TSysRole tSysRole) {
        TSysRole entity= tSysRoleMapper.selectById(tSysRole.getPkRoleId());
        if(entity != null){
            JSONObject resultJson = new JSONObject();
            resultJson.put("entity", entity);
            return new CommonResult(200,"success","数据获取成功!!",resultJson);
        }else {
            return new CommonResult(444,"error","数据获取失败!",null);
        }

    }

    @Override
    public CommonResult tSysRoleUpdate(TSysRole tSysRole) {
        tSysRole.setModifyTime(new Date());
        int i = tSysRoleMapper.updateById(tSysRole);
        if(i > 0){
            JSONObject resultJson = this.getListInfo();
            return new CommonResult(200,"success","角色修改成功!",resultJson);
        }else {
            return new CommonResult(444,"error","角色修改失败!",null);
        }
    }

    @Override
    public CommonResult systemRoleAddUserUI(String param) {
        JSONObject json = JSON.parseObject(param);
        String pkRoleId=String.valueOf(json.get("pkRoleId"));
        JSONObject jsonObject = new JSONObject();
        //通过角色id查找角色
        TSysRole role = tSysRoleMapper.selectById(pkRoleId);
        jsonObject.put("role",role);
        //查找所有用户
        List<TSysUser> tSysUserList = tSysUserService.selectAll();
        jsonObject.put("userList",tSysUserList);
        //查询已分配的用户角色并进行回显
        List<String> userRoleList = tSysRoleMapper.selectUserRole_userIded();
        List<String> userRoleList2 = tSysRoleMapper.selectUserRoleByrole_id(pkRoleId);
        jsonObject.put("ids",userRoleList);
        jsonObject.put("idsFP",userRoleList2);
        return new CommonResult(200,"success","获取角色已分配用户成功!",jsonObject);
    }

    private JSONObject getListInfo() {
        TSysRole entity = new TSysRole();
        //分页记录条数
        PageHelper.startPage(1, 10);
        List<TSysRole> list = this.tSysRoleMapper.selectAllByEntity(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TSysRole>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}