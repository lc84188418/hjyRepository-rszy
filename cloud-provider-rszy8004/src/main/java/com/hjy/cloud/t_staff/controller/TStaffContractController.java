package com.hjy.cloud.t_staff.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_staff.entity.TStaffContract;
import com.hjy.cloud.t_staff.service.TStaffContractService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * (TStaffContract)表控制层
 *
 * @author makejava
 * @since 2021-03-01 15:53:14
 */
@Api(tags = "员工管理-合同-控制层")
@Slf4j
@RestController
public class TStaffContractController {
    /**
     * 服务对象
     */
    @Resource
    private TStaffContractService tStaffContractService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/staff/contract/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tStaffContractService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tStaffContract 实体对象
     * @return 新增结果
     */
    @OperLog(operModul = "人员管理-合同管理",operType = "添加",operDesc = "添加员工合同信息")
    @RequiresPermissions({"contract:add"})
    @PostMapping(value = "/staff/contract/add")
    public CommonResult insert(@RequestBody TStaffContract tStaffContract) throws FebsException {
        try {
            return tStaffContractService.insert(tStaffContract);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffContract 实体对象
     * @return 删除结果
     */
    @OperLog(operModul = "人员管理-合同管理",operType = "删除",operDesc = "删除员工合同信息")
    @RequiresPermissions({"contract:del"})
    @DeleteMapping(value = "/staff/contract/del")
    public CommonResult delete(@RequestBody TStaffContract tStaffContract) throws FebsException {
        try {
            return tStaffContractService.delete(tStaffContract);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 管理员分页查询所有数据
     *
     * @param param json参数
     * @return 所有数据
     */
    @OperLog(operModul = "人员管理-合同管理",operType = "查看",operDesc = "管理员查看员工合同信息列表")
    @RequiresPermissions({"contract:adminView"})
    @PostMapping(value = "/staff/contract/admin/list")
    public CommonResult adminList(@RequestBody String param) throws FebsException {
        try {
            return tStaffContractService.adminList(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    /**
     * 用户查询个人合同信息
     * 暂时不要该功能
     * @param param json参数
     * @return 所有数据
     */
    @OperLog(operModul = "人员管理-合同管理",operType = "查看",operDesc = "个人查看自己合同信息")
    @RequiresPermissions({"contract:userView"})
    @PostMapping(value = "/staff/contract/user/list")
    public CommonResult userList(HttpSession session, HttpServletRequest request, @RequestBody String param) throws FebsException {
        try {
            return tStaffContractService.userList(session,request,param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tStaffContract 实体对象
     */
    @OperLog(operModul = "人员管理-合同管理",operType = "查看",operDesc = "查看单条合同信息")
    @RequiresPermissions({"contract:get"})
    @PostMapping(value = "/staff/contract/get")
    public CommonResult selectOne(@RequestBody TStaffContract tStaffContract) throws FebsException {
        try {
            return tStaffContractService.selectById(tStaffContract);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffContract 实体对象
     * @return 修改结果
     */
    @OperLog(operModul = "人员管理-合同管理",operType = "修改",operDesc = "修改单条合同信息")
    @RequiresPermissions({"contract:update"})
    @PutMapping(value = "/staff/contract/update")
    public CommonResult update(@RequestBody TStaffContract tStaffContract) throws FebsException {
        try {
            return tStaffContractService.updateByPkId(tStaffContract);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 发起续签合同页面
     *
     * @param tStaffContract 实体对象
     */
    @PostMapping(value = "/staff/contract/renewalPage")
    public CommonResult renewalPage(@RequestBody TStaffContract tStaffContract) throws FebsException {
        try {
            return tStaffContractService.renewalPage(tStaffContract);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    /**
     * 发起续签合同
     *
     * @param tStaffContract 实体对象
     */
    @PostMapping(value = "/staff/contract/renewal")
    public CommonResult renewal(@RequestBody TStaffContract tStaffContract) throws FebsException {
        try {
            return tStaffContractService.renewal(tStaffContract);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
}
