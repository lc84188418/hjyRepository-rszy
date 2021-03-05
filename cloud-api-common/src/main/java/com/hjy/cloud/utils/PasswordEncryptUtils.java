package com.hjy.cloud.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordEncryptUtils {
    /**
     * 明文的密码加密
     * 此种方式不可逆，并且盐username应设置为不可变，或自定义加盐
     * @param username 用户名
     * @param password 明文密码
     * @return
     */
    public static String MyPasswordEncryptUtil(String username, String password){
        //加密方式
        String algorithmName = "MD5";
        //盐
        Object salt = ByteSource.Util.bytes("username");
        //hash次数
        int hashIterations = 1;
        SimpleHash simpleHash = new SimpleHash(algorithmName, password, salt, hashIterations);
        return simpleHash.toString();
    }
}
