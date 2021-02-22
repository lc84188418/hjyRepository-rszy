package com.hjy.cloud.common.task;

import com.hjy.cloud.t_outfit.service.TOutfitDeptService;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.t_system.entity.ReDeptUser;
import com.hjy.cloud.t_system.entity.ReUserRole;
import com.hjy.cloud.t_system.service.TSysParamService;
import com.hjy.cloud.t_system.service.TSysRoleService;
import com.hjy.cloud.t_system.service.TSysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 异步任务
 */
@Component
@Async
public class ObjectAsyncTask {
    @Autowired
    private TSysParamService tSysParamService;
    @Autowired
    private TSysUserService tSysUserService;
    @Autowired
    private TSysRoleService tSysRoleService;
    @Autowired
    private TOutfitDeptService tOutfitDeptService;

    private static ObjectAsyncTask ntClient;

    /**
     * 向用户-角色关联表中添加一条用户角色信息
     * @param pkUserId 用户ID
     * @param roleId 角色ID
     */
    public static void addUserRoleByUserRole(String pkUserId, String roleId) {
        ReUserRole userRole = new ReUserRole();
        userRole.setPk_userRole_id(IDUtils.getUUID());
        userRole.setFk_user_id(pkUserId);
        userRole.setFk_role_id(roleId);
        ntClient.tSysUserService.addUserRoleByUserRole(userRole);
    }

    /**
     * 删除角色的所有权限
     * @param fk_role_id 角色ID
     */
    public static void deleteRolePermsByRoleId(String fk_role_id) {
        ntClient.tSysRoleService.deleteRolePermsByRoleId(fk_role_id);
    }

    //添加角色默认的权限-即主页的3个
    public static void addDefultRoelPerms(String fk_role_id) {
        List<String> idList = new ArrayList<String>();
        idList.add("1596706636946");
        idList.add("1596706882298");
        idList.add("1596707062416");
        ntClient.tSysRoleService.distributeMenu(fk_role_id,idList);
    }

    /**
     * 向部门-用户关联表中添加一条部门-用户信息
     * @param pkUserId 用户ID
     * @param deptId 部门ID
     */
    public static void addDeptUserByDeptUser(String pkUserId, String deptId) {
        ReDeptUser deptUser = new ReDeptUser();
        deptUser.setPk_deptUser_id(IDUtils.getUUID());
        deptUser.setFk_user_id(pkUserId);
        deptUser.setFk_dept_id(deptId);
        ntClient.tOutfitDeptService.addDeptUserByDeptUser(deptUser);
    }

    //初始化所有服务
    @PostConstruct
    public void init() {
        ntClient = this;
        ntClient.tSysParamService = this.tSysParamService;
        ntClient.tSysUserService = this.tSysUserService;
        ntClient.tSysRoleService = this.tSysRoleService;
        ntClient.tOutfitDeptService = this.tOutfitDeptService;
    }
}
