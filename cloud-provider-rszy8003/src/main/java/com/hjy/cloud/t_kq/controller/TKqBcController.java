package com.hjy.cloud.t_kq.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_kq.entity.TKqBc;
import com.hjy.cloud.t_kq.service.TKqBcService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TKqBc)表控制层
 *
 * @author makejava
 * @since 2021-03-16 14:50:58
 */
@RestController
public class TKqBcController {
    /**
     * 服务对象
     */
    @Resource
    private TKqBcService tKqBcService;
    @Value("${server.port}")
    private String serverPort;
    @GetMapping(value = "/provider/rszy/bc")
    public CommonResult providerRszyBc() throws FebsException {
        try {
            return new CommonResult(200, "success", "这里是提供者8003-bc", serverPort);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }
    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/tKqBc/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tKqBcService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tKqBc 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/tKqBc/add")
    public CommonResult insert(@RequestBody TKqBc tKqBc) throws FebsException {
        try {
            return tKqBcService.insert(tKqBc);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tKqBc 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/tKqBc/del")
    public CommonResult delete(@RequestBody TKqBc tKqBc) throws FebsException {
        try {
            return tKqBcService.delete(tKqBc);
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
    @PostMapping(value = "/tKqBc/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tKqBcService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tKqBc 实体对象
     */
    @PostMapping(value = "/tKqBc/get")
    public CommonResult selectOne(@RequestBody TKqBc tKqBc) throws FebsException {
        try {
            return tKqBcService.selectById(tKqBc);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tKqBc 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/tKqBc/update")
    public CommonResult update(@RequestBody TKqBc tKqBc) throws FebsException {
        try {
            return tKqBcService.updateByPkId(tKqBc);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
