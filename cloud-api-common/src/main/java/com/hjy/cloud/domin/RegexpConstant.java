package com.hjy.cloud.domin;

import org.apache.commons.lang.StringUtils;

/**
 * 正则常量
 */
public class RegexpConstant {

    //简单手机号正则,只检验是否为11位
    public static final String MOBILE_REGEX = "[1]\\d{10}";
    //复杂手机号正则
    public static final String MOBILE_COMPLEX = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";
    //姓名，长度2-4，只含中文的
    public static final String CHINESE_NAME_REGEX = "^[\\u4e00-\\u9fa5]{2,4}$";
    //姓名，长度2-20，中文或者英文，但不包括中和英同时存在
    public static final String CHINESE_ENGLISH_NAME_REGEX = "^([\\u4e00-\\u9fa5]{1,20}|[a-zA-Z\\.\\s]{1,20})$";
    //电子邮箱
    public static final String EMAIL_REGEX = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    //邮箱验证，只允许英文字母、数字、下划线、英文句号、以及中划线组成，如：zhangsan-001@gmail.com
    public static final String EMAIL2_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    //邮箱验证，名称允许汉字、字母、数字，域名只允许英文域名，如：杨元庆001Abc@lenovo.com.cn
    public static final String EMAIL3_REGEX = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    //身份证号码
    public static final String IDCARD_REGEX = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
    public static final String IDCARD_18_REGEX = "^[1-9]\\d{5}(19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
    public static final String IDCARD_15_REGEX = "^[1-9]\\d{5}\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$";
    //QQ号码
    public static final String QQ_REGEX = "^[1-9][0-9]\\d{4,9}$";
    //匹配纯整数
    public static final String NUM_REGEX = "^([0-9]{1,})$";
    //匹配纯小数
    public static final String FLOAT_REGEX = "^([0-9]{1,}[.][0-9]*)$";
    //匹配整数和小数,如果是在JS中^(([^0][0-9]+|0)\.([0-9]{1,2})$)|^([^0][0-9]+|0)$
    public static final String NUM_FLOAT_REGEX = "^([0-9]{1,})$|^([0-9]{1,}[.][0-9]*)$";

    /**
     * 以下时各项正则的验证方法
     */

    public static boolean validateName(String name){
        if(StringUtils.isEmpty(name)){
            return false;
        }

        return false;
    }

}
