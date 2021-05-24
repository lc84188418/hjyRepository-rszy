package com.hjy.cloud.common.enums;

/**
 * 审批结果枚举
 */
public enum ApprovalResultStatus {
    Result1("全部", ""),
    Result2("待处理", "0"),
    Result3("已处理", "1");
    /**
     * status 状态
     */
    private String status;
    /**
     * desc 描述
     */
    private String desc;

    ApprovalResultStatus(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }
    // 普通方法
    public static String getDesc(String status) {
        for (ApprovalResultStatus d : ApprovalResultStatus.values()) {
            if (d.getStatus().equals(status) ) {
                return d.desc;
            }
        }
        return null;
    }
    // 普通方法
    public static String getStatus(String desc) {
        for (ApprovalResultStatus d : ApprovalResultStatus.values()) {
            if (d.getDesc().equals(desc)) {
                return d.status;
            }
        }
        return null;
    }
    public String getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
