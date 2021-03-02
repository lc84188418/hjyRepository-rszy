package com.hjy.cloud.t_apv.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_apv.entity.TApvApproval;
import com.hjy.cloud.t_apv.service.TApvApprovalService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TApvApproval)表控制层
 * 审批流程设置
 * @author makejava
 * @since 2021-02-26 14:50:48
 */
@RestController
public class TApvApprovalController {
    /**
     * 服务对象
     */
    @Resource
    private TApvApprovalService tApvApprovalService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/apv/approval/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tApvApprovalService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tApvApproval 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/apv/approval/add")
    public CommonResult insert(@RequestBody TApvApproval tApvApproval) throws FebsException {
        try {
            return tApvApprovalService.insert(tApvApproval);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tApvApproval 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/apv/approval/del")
    public CommonResult delete(@RequestBody TApvApproval tApvApproval) throws FebsException {
        try {
            return tApvApprovalService.delete(tApvApproval);
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
    @PostMapping(value = "/apv/approval/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tApvApprovalService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tApvApproval 实体对象
     */
    @PostMapping(value = "/apv/approval/get")
    public CommonResult selectOne(@RequestBody TApvApproval tApvApproval) throws FebsException {
        try {
            return tApvApprovalService.selectById(tApvApproval);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tApvApproval 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/apv/approval/update")
    public CommonResult update(@RequestBody TApvApproval tApvApproval) throws FebsException {
        try {
            return tApvApprovalService.updateByPkId(tApvApproval);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 审批设置
     *
     * @return 修改结果
     */
    @GetMapping(value = "/apv/approval/set")
    public CommonResult approvalSet() throws FebsException {
        try {
            return tApvApprovalService.approvalSet();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }
    /**
     * 待审批,是指所有没有审批完成的记录，非操作用户自己的
     *
     * @return 修改结果
     */
    @GetMapping(value = "/apv/approval/waitApv")
    public CommonResult waitApv() throws FebsException {
        try {
            return tApvApprovalService.waitApv();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }
}
