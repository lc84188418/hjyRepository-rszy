package com.hjy.cloud.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liuchun
 * @Package com.hjy.cloud.common.entity
 * @date 2021/5/18 9:32
 * description:
 */
@Data
@ApiModel("通用用户实体类")
public class User {
    @ApiModelProperty(value = "用户ID",example = "5464564564564",required = false)
    private String userId;
    @ApiModelProperty(value = "用户姓名",example = "张三",required = false)
    private String fullName;
}
