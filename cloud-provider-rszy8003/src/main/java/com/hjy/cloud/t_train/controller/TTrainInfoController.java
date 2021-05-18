package com.hjy.cloud.t_train.controller;


import com.hjy.cloud.common.entity.User;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_train.entity.TTrainInfo;
import com.hjy.cloud.t_train.service.TTrainInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TTrainInfo)表控制层
 *
 * @author makejava
 * @since 2021-03-01 16:41:43
 */
@Api(tags = "培训管理-培训-控制层")
@Slf4j
@RestController
public class TTrainInfoController {
    /**
     * 服务对象
     */
    @Resource
    private TTrainInfoService tTrainInfoService;

    /**
     * 1 跳转到新增页面
     */
    @ApiOperation(value = "1新增培训信息页面-已完成", notes = "")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "pkProjectId", value = "项目信息主键id",required = true, dataType = "int", paramType = "path", example = "1")
//    })
    @GetMapping(value = "/train/info/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tTrainInfoService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tTrainInfo 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "1新增培训信息页面-已完成", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "trainName", value = "培训活动名称",required = false, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "trainContent", value = "培训内容",required = false, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "startTime", value = "培训开始时间",required = false, dataType = "date-time", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "endTime", value = "培训结束时间",required = false, dataType = "date-time", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "trainAddress", value = "培训地点",required = false, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "trainOrganization", value = "培训机构",required = false, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "ourJoin", value = "培训人id",required = false, dataType = "object", paramType = "body", example = "1"),
    })
    @PostMapping(value = "/train/info/add")
    public CommonResult insert(@RequestBody TTrainInfo<User> tTrainInfo) throws FebsException {
        try {
            return tTrainInfoService.insert(tTrainInfo);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param pkInfoId 实体对象主键
     * @return 删除结果
     */
    @ApiOperation(value = "1新增培训信息页面-已完成", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pkInfoId", value = "培训活动主键",required = true, dataType = "string", paramType = "path", example = "1"),
    })
    @DeleteMapping(value = "/train/info/del/{pkInfoId}")
    public CommonResult delete(@PathVariable("pkInfoId") String pkInfoId) throws FebsException {
        try {
            return tTrainInfoService.delete(pkInfoId);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 分页查询所有数据
     *
     * @param param json参数
     * @return 所有数据
     */
    @GetMapping(value = "/train/info/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tTrainInfoService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tTrainInfo 实体对象
     */
    @PostMapping(value = "/train/info/get")
    public CommonResult selectOne(@RequestBody TTrainInfo tTrainInfo) throws FebsException {
        try {
            return tTrainInfoService.selectById(tTrainInfo);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tTrainInfo 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/train/info/update")
    public CommonResult update(@RequestBody TTrainInfo tTrainInfo) throws FebsException {
        try {
            return tTrainInfoService.updateByPkId(tTrainInfo);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


}
