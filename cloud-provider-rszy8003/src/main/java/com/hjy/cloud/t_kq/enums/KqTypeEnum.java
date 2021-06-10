package com.hjy.cloud.t_kq.enums;

/**
 * 审批结果枚举
 */
public enum KqTypeEnum {
    Type_0(0, "保留"),
    Type_1(1, "固定班次"),
    Type_2(2, "排班制"),
    Type_3(3, "自由工时");
    /**
     * status 状态
     */
    private int status;
    /**
     * desc 描述
     */
    private String desc;

    KqTypeEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }
    // 普通方法
    public static String getDesc(int status) {
        for (KqTypeEnum d : KqTypeEnum.values()) {
            if (d.getStatus() == status) {
                return d.desc;
            }
        }
        return null;
    }
    // 普通方法
    public static int getStatus(String desc) {
        for (KqTypeEnum d : KqTypeEnum.values()) {
            if (d.getDesc().equals(desc)) {
                return d.status;
            }
        }
        return 0;
    }
    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
