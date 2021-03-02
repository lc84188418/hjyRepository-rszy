package com.hjy.cloud.t_dictionary.controller;


import com.hjy.cloud.t_dictionary.entity.TDictionarySurvey;
import com.hjy.cloud.t_dictionary.service.TDictionarySurveyService;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TDictionarySurvey)表控制层
 *
 * @author makejava
 * @since 2021-02-25 17:21:55
 */
@RestController
public class TDictionarySurveyController {
    /**
     * 服务对象
     */
    @Resource
    private TDictionarySurveyService tDictionarySurveyService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/dictionary/survey/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tDictionarySurveyService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tDictionarySurvey 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/dictionary/survey/add")
    public CommonResult insert(@RequestBody TDictionarySurvey tDictionarySurvey) throws FebsException {
        try {
            return tDictionarySurveyService.insert(tDictionarySurvey);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionarySurvey 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/dictionary/survey/del")
    public CommonResult delete(@RequestBody TDictionarySurvey tDictionarySurvey) throws FebsException {
        try {
            return tDictionarySurveyService.delete(tDictionarySurvey);
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
    @PostMapping(value = "/dictionary/survey/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tDictionarySurveyService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tDictionarySurvey 实体对象
     */
    @PostMapping(value = "/dictionary/survey/get")
    public CommonResult selectOne(@RequestBody TDictionarySurvey tDictionarySurvey) throws FebsException {
        try {
            return tDictionarySurveyService.selectById(tDictionarySurvey);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionarySurvey 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/dictionary/survey/update")
    public CommonResult update(@RequestBody TDictionarySurvey tDictionarySurvey) throws FebsException {
        try {
            return tDictionarySurveyService.updateByPkId(tDictionarySurvey);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
