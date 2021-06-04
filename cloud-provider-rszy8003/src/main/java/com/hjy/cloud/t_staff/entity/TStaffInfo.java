package com.hjy.cloud.t_staff.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * (TStaffInfo)表实体类
 *
 * @author makejava
 * @since 2021-06-03 15:42:54
 */
@Data
@ApiModel("员工档案")
public class TStaffInfo {
    @ApiModelProperty(value = "员工主键id", example = "", required = false)
    private String pkStaffId;
    @ApiModelProperty(value = "姓名", example = "", required = false)
    private String staffName;
    @ApiModelProperty(value = "性别", example = "", required = false)
    private Integer staffSex;
    @ApiModelProperty(value = "年龄", example = "", required = false)
    private Integer staffAge;
    @ApiModelProperty(value = "员工状态,1代表在职，0代表离职", example = "", required = false)
    private Integer staffStatus;
    @ApiModelProperty(value = "部门id", example = "", required = false)
    private String fkDeptId;
    @ApiModelProperty(value = "部门名称，只做查询展示用", example = "", hidden = true)
    private String deptName;
    @ApiModelProperty(value = "职位id", example = "", required = false)
    private String fkPositionId;
    @ApiModelProperty(value = "职位名称，只做查询展示用", example = "", hidden = true)
    private String positionName;
    @ApiModelProperty(value = "工作地id", example = "", required = false)
    private String fkWorkaddressId;
    @ApiModelProperty(value = "工作地名称，只做查询展示用", example = "", hidden = true)
    private String workAddressName;
    @ApiModelProperty(value = "入职时间", example = "", required = false)
    private Date entryTime;
    @ApiModelProperty(value = "合同类型id,在字典中有", example = "", required = false)
    private String fkHtlxId;
    @ApiModelProperty(value = "合同类型名称，只做查询展示用", example = "", hidden = true)
    private String htlxName;
    @ApiModelProperty(value = "招聘形式", example = "", required = false)
    private String recruitWay;
    @ApiModelProperty(value = "试用期到期日", example = "", required = false)
    private Date syqdqTime;
    @ApiModelProperty(value = "证件类型", example = "", required = false)
    private String idType;
    @ApiModelProperty(value = "证件号", example = "", required = false)
    private String idCard;
    @ApiModelProperty(value = "员工邮箱", example = "", required = false)
    private String staffEmail;
    @ApiModelProperty(value = "员工电话", example = "", required = false)
    private String staffTel;
    @ApiModelProperty(value = "籍贯", example = "", required = false)
    private String nativePlace;
    @ApiModelProperty(value = "出生日期", example = "", required = false)
    private String birthday;
    @ApiModelProperty(value = "民族id，字典中有", example = "", required = false)
    private String fkNationId;
    @ApiModelProperty(value = "民族名称，只做查询展示用", example = "", hidden = true)
    private String nationName;
    @ApiModelProperty(value = "户口所在地", example = "", required = false)
    private String hkszd;
    @ApiModelProperty(value = "政治面貌", example = "", required = false)
    private String politivalOutlook;
    @ApiModelProperty(value = "现住地址", example = "", required = false)
    private String currentAddress;
    @ApiModelProperty(value = "婚姻状况", example = "", required = false)
    private Integer isMarry;
    @ApiModelProperty(value = "学历id,字典中有", example = "", required = false)
    private String fkEducationId;
    @ApiModelProperty(value = "学历名称，只做查询展示用", example = "", hidden = true)
    private String educationName;
    @ApiModelProperty(value = "最高学历毕业院校", example = "", required = false)
    private String zgxlbyyx;
    @ApiModelProperty(value = "外语等级", example = "", required = false)
    private String wyDj;
    @ApiModelProperty(value = "计算机等级", example = "", required = false)
    private String jsjDj;
    @ApiModelProperty(value = "所学专业", example = "", required = false)
    private String major;
    @ApiModelProperty(value = "工资开银行", example = "", required = false)
    private String bankName;
    @ApiModelProperty(value = "工资卡银行账户", example = "", required = false)
    private String bankId;
    @ApiModelProperty(value = "所属支行", example = "", required = false)
    private String branchBank;
    @ApiModelProperty(value = "所有人", example = "", required = false)
    private String syr;
    @ApiModelProperty(value = "银行卡图片路径", example = "", required = false)
    private String picturePath;
    @ApiModelProperty(value = "汇报类型名称1", example = "", required = false)
    private String hblxName1;
    @ApiModelProperty(value = "汇报对象-业务,放员工ID", example = "", required = false)
    private String object1;
    @ApiModelProperty(value = "汇报类型名称2", example = "", required = false)
    private String hblxName2;
    @ApiModelProperty(value = "汇报对象-行政,放员工ID", example = "", required = false)
    private String object2;
    @ApiModelProperty(value = "汇报类型名称3", example = "", required = false)
    private String hblxName3;
    @ApiModelProperty(value = "汇报对象-人事,放员工ID", example = "", required = false)
    private String object3;
    @ApiModelProperty(value = "汇报类型名称4", example = "", required = false)
    private String hblxName4;
    @ApiModelProperty(value = "汇报对象-财务,放员工ID", example = "", required = false)
    private String object4;
    @ApiModelProperty(value = "汇报类型名称5，备用的", example = "", required = false)
    private String hblxName5;
    @ApiModelProperty(value = "汇报对象-备用,放员工ID", example = "", required = false)
    private String object5;

}
