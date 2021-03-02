package com.hjy.cloud.t_train.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_train.entity.TTrainInfo;
import com.hjy.cloud.t_train.service.TTrainInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TTrainInfo)表控制层
 *
 * @author makejava
 * @since 2021-03-01 16:41:43
 */
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
    @GetMapping(value = "/train/info/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tTrainInfoService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tTrainInfo 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/train/info/add")
    public CommonResult insert(@RequestBody TTrainInfo tTrainInfo) throws FebsException {
        try {
            return tTrainInfoService.insert(tTrainInfo);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tTrainInfo 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/train/info/del")
    public CommonResult delete(@RequestBody TTrainInfo tTrainInfo) throws FebsException {
        try {
            return tTrainInfoService.delete(tTrainInfo);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 分页查询所有数据
     *
     * @param param json参数
     * @return 所有数据
     */
    @PostMapping(value = "/train/info/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tTrainInfoService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
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
            throw new FebsException(message);
        }
    }


}
