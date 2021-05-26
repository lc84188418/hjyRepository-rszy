package com.hjy.cloud.common.auth;

import com.hjy.cloud.t_system.entity.*;
import com.hjy.cloud.t_system.service.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Shiro自定义Realm
 */
@Component
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private ShiroService shiroService;

    /**
     * 认证 判断token的有效性
     *@param  token
     *@return org.apache.shiro.authc.AuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取token，既前端传入的token
        String accessToken = (String) token.getPrincipal();
        //1. 根据accessToken，查询用户信息
        SysToken tokenEntity = shiroService.findByToken(accessToken);
        Date date = new Date();
        //2. token失效
        if (tokenEntity == null || tokenEntity.getExpireTime().getTime()<date.getTime()) {
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }
        //3. 调用数据库的方法, 从数据库中查询 username 对应的用户记录
        TSysUser user = shiroService.selectUserByUsername(tokenEntity.getUsername());
        //4. 若用户不存在, 则可以抛出 UnknownAccountException 异常
        if (user == null) {
            throw new UnknownAccountException("用户不存在!");
        }else {
            //验证是否用户过期
            Date expireTime = user.getExpireTime();
            if(expireTime != null && expireTime.getTime()<date.getTime()){
                throw new IncorrectCredentialsException("用户已过期！");
            }
        }
        //5. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回. 通常使用的实现类为: SimpleAuthenticationInfo
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, this.getName());
        //将信息放入session中
        ActiveUser activeUser = new ActiveUser();
        activeUser.setUserId(user.getPkUserId());
        activeUser.setUsername(user.getUsername());
        activeUser.setPassword(user.getPassword());
        activeUser.setTel(user.getTel());
        activeUser.setIDcard(user.getIdcard());
        activeUser.setFullName(user.getFullName());
        activeUser.setTokenId(accessToken);
        SecurityUtils.getSubject().getSession().setAttribute("activeUser",activeUser);
        return info;
    }
    /**
     * 授权 获取用户的角色和权限
     *@param  principals
     *@return org.apache.shiro.authz.AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //1. 从 PrincipalCollection 中来获取登录用户的信息
        TSysUser user = (TSysUser) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //从数据库获取角色信息
        TSysRole role = shiroService.selectRoleByUserId(user.getPkUserId());
        if(role == null){
            TSysRole newRole = new TSysRole();
            //创建一个默认角色
            newRole.setPkRoleId("2");
            newRole.setRoleName("普通角色");
            newRole.setRoleDesc("系统默认角色");
            newRole.setEnableStatus(1);
            role = newRole;
        }
        //2.添加角色
        simpleAuthorizationInfo.addRole(role.getRoleName());
        //判断部门→职位是否属于系统管理→系统管理员
        /**
         * 该用户是否属于超级管理员
         */
        //3.添加菜单
        List<TSysPerms> permsList = new ArrayList<TSysPerms>();
        //4.添加权限码
        List<String> percodes = new ArrayList<>();
        if ("1".equals(role.getPkRoleId())) {
            //代表未超级管理员
            //直接查询所有的
            permsList = shiroService.selectAllperms();
            percodes = shiroService.selectAllPermsCode();
        } else {
            permsList = shiroService.selectPermsByRole(role.getPkRoleId());
            percodes = shiroService.selectPermsCodeByRole(role.getPkRoleId());
        }
        //所有用户都要添加index权限
        percodes.add("index");
        simpleAuthorizationInfo.addStringPermissions(percodes);
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getSession().getAttribute("activeUser");
        activeUser.setRoleName(role.getRoleName());
        //权限类型,1代表角色权限，0代表自由权限
        if(role != null){
            activeUser.setPermsType("0");
        }
        activeUser.setPermsType("1");
        //设置菜单和权限
        activeUser.setMenu(permsList);
        activeUser.setPermsCode(percodes);
        SecurityUtils.getSubject().getSession().setAttribute("activeUser",activeUser);
        return simpleAuthorizationInfo;
    }
}
