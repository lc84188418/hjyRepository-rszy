package com.hjy.cloud.t_apv.enums;

/**
 * 审批的类型
 */
public enum ApprovaltypeEnum {
    Type_1("1", "请假申请"),
    Type_2("2", "补卡申请"),
    Type_3("3", "外出申请"),
    Type_4("4", "加班申请"),
    Type_5("5", "调班申请"),
    Type_6("6", "报销申请"),
    Type_7("7", "采购申请"),
    Type_8("8", "调动申请"),
    Type_9("9", "招聘申请"),
    Type_10("10", "调薪申请"),
    Type_11("11", "离职申请"),
    Type_12("12", "入职申请"),
    Type_13("13", "转正申请"),
    Type_14("14", "印章申请"),
    Type_15("15", "证照申请");
    /**
     * code 状态
     */
    private String code;
    /**
     * desc 描述
     */
    private String desc;

    ApprovaltypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    // 普通方法
    public static String getDesc(String code) {
        for (ApprovaltypeEnum d : ApprovaltypeEnum.values()) {
            if (d.getCode().equals(code)) {
                return d.desc;
            }
        }
        return null;
    }
    // 普通方法
    public static String getCode(String desc) {
        for (ApprovaltypeEnum d : ApprovaltypeEnum.values()) {
            if (d.getDesc().equals(desc)) {
                return d.code;
            }
        }
        return null;
    }
    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
