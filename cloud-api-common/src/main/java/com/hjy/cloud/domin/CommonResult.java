package com.hjy.cloud.domin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * json封装体
 * @param <T>
 */
@Data
@AllArgsConstructor//全参
@NoArgsConstructor//空参
public class CommonResult<T>{
    //状态码
    @ApiModelProperty("状态码")
    private int code;
    //请求状态，只有成功或失败（success,error）
    @ApiModelProperty("请求状态，只有成功或失败（success,error）")
    private String status;
//    private String timestamp = DateUtil.formatFullTime(LocalDateTime.now(),"yyyy-MM-dd HH:mm:ss");
    //提示信息
    @ApiModelProperty("提示信息")
    private String msg;
    //返回数据
    @ApiModelProperty("返回数据")
    private T data;

    //,String timestamp
    public CommonResult(int code, String status,String msg) {
        this(code,status,msg,null);
    }
    public CommonResult SuccessResult(String msg,T data) {
        return new CommonResult(200,"success",msg,data);
    }
    public CommonResult ErrorResult(String msg,T data) {
        return new CommonResult(444,"error",msg,data);
    }
}
