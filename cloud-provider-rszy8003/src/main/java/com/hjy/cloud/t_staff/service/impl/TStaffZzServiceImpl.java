package com.hjy.cloud.t_staff.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.common.entity.DApvRecord;
import com.hjy.cloud.common.task.ObjectAsyncTask;
import com.hjy.cloud.t_apv.dao.DCcRecordMapper;
import com.hjy.cloud.t_apv.dao.TApvApprovalMapper;
import com.hjy.cloud.t_apv.entity.DCcRecord;
import com.hjy.cloud.t_apv.entity.TApvApproval;
import com.hjy.cloud.t_staff.dao.TStaffEntryMapper;
import com.hjy.cloud.t_staff.dao.TStaffZzMapper;
import com.hjy.cloud.t_staff.entity.TStaffEntry;
import com.hjy.cloud.t_staff.entity.TStaffZz;
import com.hjy.cloud.t_staff.service.TStaffZzService;
import com.hjy.cloud.t_system.dao.TSysParamMapper;
import com.hjy.cloud.t_system.dao.TSysTokenMapper;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.utils.DateUtil;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.TokenUtil;
import com.hjy.cloud.utils.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.JsonUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * (TStaffZz)表服务实现类
 *
 * @author makejava
 * @since 2021-03-01 15:53:18
 */
@Service("tStaffZzService")
public class TStaffZzServiceImpl implements TStaffZzService {

    @Resource
    private TStaffZzMapper tStaffZzMapper;
    @Resource
    private TStaffEntryMapper tStaffEntryMapper;
    @Resource
    private TSysTokenMapper tSysTokenMapper;
    @Resource
    private TSysParamMapper tSysParamMapper;
    @Resource
    private DCcRecordMapper dCcRecordMapper;
    @Resource
    private TApvApprovalMapper tApvApprovalMapper;
    /**
     * 添加前获取数据
     *
     * @return
     */
    @Override
    public CommonResult insertPage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("entity", null);
        return new CommonResult(200, "success", "获取数据成功", jsonObject);
    }

    /**
     * 添加数据
     *
     * @param tStaffZz
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TStaffZz tStaffZz) {
        int i = this.tStaffZzMapper.insertSelective(tStaffZz);
        if (i > 0) {
            return new CommonResult(200, "success", "添加数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffZz
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TStaffZz tStaffZz) {
        int i = this.tStaffZzMapper.updateByPkId(tStaffZz);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffZz
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TStaffZz tStaffZz) {
        int i = this.tStaffZzMapper.deleteById(tStaffZz);
        if (i > 0) {
            return new CommonResult(200, "success", "删除数据成功", null);
        } else {
            return new CommonResult(444, "error", "删除数据失败", null);
        }
    }

    /**
     * 查询所有数据
     *
     * @param param
     * @return
     */
    @Override
    public CommonResult selectAll(String param) {
        JSONObject json = JSON.parseObject(param);
        //查询条件
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        TStaffZz entity = new TStaffZz();
        //分页记录条数
        int pageNum = 1;
        int pageSize = 10;
        if (pageNumStr != null) {
            pageNum = Integer.parseInt(pageNumStr);
        }
        if (pageSizeStr != null) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<TStaffZz> list = this.tStaffZzMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffZz>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tStaffZz 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TStaffZz tStaffZz) {
        String pkId = tStaffZz.getPkZzId();
        TStaffZz entity = this.tStaffZzMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TStaffZz entity = new TStaffZz();
        List<TStaffZz> list = this.tStaffZzMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffZz>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
    /**
     * 发起转正审批页面
     *
     * @return 修改结果
     */
    @Override
    public CommonResult initiateZzPage(HttpServletRequest request) {
        String token = TokenUtil.getRequestToken(request);
        if (StringUtils.isEmpty(token)){
            return new CommonResult(444, "error", "请在请求头中传入token", null);
        }
        SysToken sysToken = tSysTokenMapper.findByToken(token);
        if(sysToken == null){
            return new CommonResult(444, "error", "token已失效，请重新登录后再试", null);
        }
        JSONObject resultJson = ObjectAsyncTask.handleApproval(sysToken,sysToken.getFkUserId(),"转正申请",1);
        String msg = (String) resultJson.get("msg");
        resultJson.remove("msg");
        return new CommonResult(200, "success", msg, resultJson);
    }
    /**
     * 发起转正审批页面
     *
     * @return 修改结果
     */
    @Transactional()
    @Override
    public CommonResult initiateZz(HttpServletRequest request,String param)throws Exception {
        String token = TokenUtil.getRequestToken(request);
        SysToken sysToken = tSysTokenMapper.findByToken(token);
        if(sysToken == null){
            return new CommonResult(444, "error", "未传入token或已失效", null);
        }
        JSONObject jsonObject = JSON.parseObject(param);
        //入职申请信息主键
        String pkEntryId = sysToken.getFkUserId();
        //查询是否已添加过转正申请
        TStaffZz selectStaffZz = tStaffZzMapper.selectByPkId(pkEntryId);
        if(selectStaffZz != null){
            return new CommonResult(445, "error", "已提交过转正申请，不可再次提交", null);
        }
        String newPkId = IDUtils.getUUID();
        String firstApvrecordId = newPkId;
        /**
         * 添加转正信息到数据库表中
         */
        TStaffEntry entry = tStaffEntryMapper.selectByPkId(pkEntryId);
        TStaffZz staffZz = new TStaffZz();
        staffZz.setPkZzId(pkEntryId);
        staffZz.setFkStaffId(pkEntryId);
        staffZz.setFkWordaddressId(entry.getWorkAddress());
        staffZz.setEntryTime(entry.getEntryTime());
        //试用期到期日
        String syqsj = "3";
        String value = tSysParamMapper.selectParamById("SYQSJ");
        if(!StringUtils.isEmpty(value)){
            syqsj = value;
        }
        Date syqsjdate = DateUtil.addSYQTime(entry.getEntryTime(),syqsj);
        staffZz.setSyqdqTime(syqsjdate);
        //转正日期
        String zzsj = "3";
        String value2 = tSysParamMapper.selectParamById("ZZSJ");
        if(!StringUtils.isEmpty(value2)){
            zzsj = value2;
        }
        Date zzsjdate = DateUtil.addSYQTime(entry.getEntryTime(),zzsj);
        staffZz.setZzTime(zzsjdate);
        //实际转正日期在审批通过后进行修改
        staffZz.setStatus(0);
        //转正审批状态,0代表正在审批中，1通过
        staffZz.setApvStatus(0);
        staffZz.setFirstApvrecordId(firstApvrecordId);
        int i = tStaffZzMapper.insertSelective(staffZz);
        StringBuffer stringBuffer = new StringBuffer();
        if(i > 0){
            stringBuffer.append("转正申请已发起成功！");
            //审批类型
            String approvalType = "13";
            stringBuffer = ObjectAsyncTask.addApprovalRecord(stringBuffer,jsonObject,sysToken,approvalType,sysToken.getFkUserId());
            return new CommonResult(200, "success", stringBuffer.toString(), null);
        }else {
            return new CommonResult(444, "error","转正申请发起失败！", null);
        }
    }
}
    
