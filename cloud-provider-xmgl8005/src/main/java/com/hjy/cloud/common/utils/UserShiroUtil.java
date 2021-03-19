package com.hjy.cloud.common.utils;

import com.hjy.cloud.t_system.entity.ActiveUser;

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
}
