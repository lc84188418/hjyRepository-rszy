package com.hjy.cloud.t_system.controller;

import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_system.entity.ActiveUser;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.utils.*;
import com.hjy.cloud.t_system.entity.TSysUser;
import com.hjy.cloud.t_system.service.ShiroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Map;

@Api(tags = "系统-登录控制层")
@Slf4j
@RestController
public class LoginController {
    @Autowired
    private ShiroService shiroService;
    /**
     * 登录
     */
    @ApiOperation(value = "登陆", notes = "参数:用户名 密码")
    @PostMapping("/login")
    public CommonResult login(@RequestBody TSysUser tSysUser, HttpServletRequest request, HttpSession session) throws UnknownHostException, SocketException {
        String username = tSysUser.getUsername();
        String passwordN0 = tSysUser.getPassword();
        String password = PasswordEncryptUtils.MyPasswordEncryptUtil(username,passwordN0);
        //用户信息
        TSysUser user = shiroService.selectUserByUsername(username);
        //账号不存在、密码错误
        if (user == null) {
            return new CommonResult(444,"error","账号不存在");
        } else if(!user.getPassword().equals(password)) {
            return new CommonResult(445,"error","密码错误");
        }else if(user.getEnableStatus().equals("0")){
            return new CommonResult(446,"error","该账户已被管理员禁用，请联系管理员");
        }else {
            //获取ip
            String ip= IPUtil.getIpAddress(request);
//            String ip= "192.168.2.34";
//            String mac = IPUtil.getMacAddrByIp(ip);
//            System.err.println(mac);
            user.setIp(ip);
            //生成token，并保存到数据库
            Map<String, Object> result = shiroService.createToken(user, session);
            return new CommonResult(200,"success","登陆成功",result);
        }
    }
    /**
     *登录成功
     * @return
     * throws FebsException
     */
    @RequiresPermissions({"index"})
    @PostMapping("/index")
    public CommonResult index(HttpSession session,HttpServletRequest request) throws FebsException, IOException {
        try {
            //获取当前登录用户
            ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
            return new CommonResult(200,"success","获取数据成功!",activeUser);
        }catch (Exception e) {
            String message = "系统内部异常";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     *退出系统
     * @return
     */
    @PostMapping("/logout")
    public CommonResult logout(HttpSession session, HttpServletRequest request) throws FebsException {
        ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
        //清空缓存
        //取出当前验证主体
        Subject subject = SecurityUtils.getSubject();
        //不为空，执行一次logout的操作，将session全部清空
        if (subject != null) {
            subject.logout();
        }
        try{
            //删除token
            String tokenId = TokenUtil.getRequestToken(request);
            shiroService.deleteToken(tokenId);
            if(activeUser != null){
                //更新最后一次登录时间
                shiroService.updateLoginTime(activeUser.getUserId());
            }
            return new CommonResult(200,"success","成功退出系统!",null);
        }catch (Exception e) {
            String message = "系统内部异常";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    /**
     * 时间戳
     */
    @GetMapping("/idtest")
    public CommonResult test(){
        //1--------
        String id = IDUtils.getUUID();
        return new CommonResult(200,"success","成功",id);
    }
}
