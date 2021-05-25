package com.hjy.cloud.common.utils;

import com.hjy.cloud.common.task.ObjectAsyncTask;
import com.hjy.cloud.t_system.entity.ActiveUser;
import com.hjy.cloud.t_system.entity.SysToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserShiroUtil {

    public static String getCurrentUserName(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
        return activeUser.getFullName();
    }
    public static String getCurrentUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
        return activeUser.getUserId();
    }
    public static ActiveUser getActiveUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
        return activeUser;
    }
    public static String getCurrentUserId(HttpSession session,HttpServletRequest request) {
        ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
        String userId = "";
        if(activeUser == null){
            SysToken sysToken = ObjectAsyncTask.getSysToken(request);
            if(sysToken != null){
                userId = sysToken.getFkUserId();
            }else {
                userId = null;
            }
        }else {
            userId = activeUser.getUserId();
        }
        return userId;
    }
    public static String getCurrentFullName(HttpSession session,HttpServletRequest request) {
        ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
        String fullName = "";
        if(activeUser == null){
            SysToken sysToken = ObjectAsyncTask.getSysToken(request);
            if(sysToken != null){
                fullName = sysToken.getFullName();
            }else {
                fullName = null;
            }
        }else {
            fullName = activeUser.getFullName();
        }
        return fullName;
    }
}
