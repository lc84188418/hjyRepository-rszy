package com.hjy.cloud.t_staff.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.common.entity.DApvRecord;
import com.hjy.cloud.common.task.ObjectAsyncTask;
import com.hjy.cloud.t_apv.dao.DCcRecordMapper;
import com.hjy.cloud.t_apv.entity.DCcRecord;
import com.hjy.cloud.t_dictionary.dao.TDictionaryEducationMapper;
import com.hjy.cloud.t_dictionary.dao.TDictionaryHtlxMapper;
import com.hjy.cloud.t_dictionary.dao.TDictionaryNationMapper;
import com.hjy.cloud.t_dictionary.dao.TDictionaryPositionMapper;
import com.hjy.cloud.t_dictionary.entity.TDictionaryEducation;
import com.hjy.cloud.t_dictionary.entity.TDictionaryHtlx;
import com.hjy.cloud.t_dictionary.entity.TDictionaryNation;
import com.hjy.cloud.t_dictionary.entity.TDictionaryPosition;
import com.hjy.cloud.t_apv.dao.TApvApprovalMapper;
import com.hjy.cloud.t_apv.dao.TApvApvtypeMapper;
import com.hjy.cloud.t_apv.entity.TApvApproval;
import com.hjy.cloud.t_apv.entity.TApvApvtype;
import com.hjy.cloud.t_outfit.dao.TOutfitDeptMapper;
import com.hjy.cloud.t_outfit.dao.TOutfitWorkaddressMapper;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_outfit.entity.TOutfitWorkaddress;
import com.hjy.cloud.t_staff.dao.TStaffEntryMapper;
import com.hjy.cloud.t_staff.entity.TStaffEntry;
import com.hjy.cloud.t_staff.service.TStaffEntryService;
import com.hjy.cloud.t_system.dao.TSysTokenMapper;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.TokenUtil;
import com.hjy.cloud.utils.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.JsonUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * (TStaffEntry)表服务实现类
 *
 * @author makejava
 * @since 2021-02-26 10:55:26
 */
@Service("tStaffEntryService")
public class TStaffEntryServiceImpl implements TStaffEntryService {

    @Resource
    private TStaffEntryMapper tStaffEntryMapper;
    @Resource
    private TSysTokenMapper tSysTokenMapper;
    @Resource
    private TOutfitDeptMapper tOutfitDeptMapper;
    @Resource
    private TDictionaryPositionMapper tDictionaryPositionMapper;
    @Resource
    private TOutfitWorkaddressMapper tOutfitWorkaddressMapper;
    @Resource
    private TDictionaryHtlxMapper tDictionaryHtlxMapper;
    @Resource
    private TDictionaryNationMapper tDictionaryNationMapper;
    @Resource
    private TDictionaryEducationMapper tDictionaryEducationMapper;
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
        //部门
        List<TOutfitDept> depts = tOutfitDeptMapper.selectAllIdAndName();
        //职位
        List<TDictionaryPosition> positions = tDictionaryPositionMapper.selectAllId_Name();
        //工作地
        List<TOutfitWorkaddress> workaddresses = tOutfitWorkaddressMapper.selectAllId_Name();
        //合同类型
        List<TDictionaryHtlx> htlxes = tDictionaryHtlxMapper.selectAllId_Name();
        //民族
        List<TDictionaryNation> nations = tDictionaryNationMapper.selectAllId_Name();
        //学历
        List<TDictionaryEducation> educations = tDictionaryEducationMapper.selectAllId_Name();
        jsonObject.put("depts", depts);
        jsonObject.put("positions", positions);
        jsonObject.put("workaddresses", workaddresses);
        jsonObject.put("htlxes", htlxes);
        jsonObject.put("nations", nations);
        jsonObject.put("educations", educations);
        return new CommonResult(200, "success", "获取数据成功", jsonObject);
    }

    /**
     * 添加数据
     *
     * @param tStaffEntry
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TStaffEntry tStaffEntry) {
        StringBuffer sb = new StringBuffer();
        String pkId = IDUtils.getUUID();
        tStaffEntry.setPkEntryId(pkId);
        tStaffEntry.setStatus(0);
        //入职时间
        //操作人
        int i = this.tStaffEntryMapper.insertSelective(tStaffEntry);
        if (i > 0) {
            JSONObject resultJson = this.getListInfo();
            return new CommonResult(200, "success", "入职信息添加成功！", resultJson);
        } else {
            return new CommonResult(444, "error", "添加数据失败！", null);
        }
    }


    /**
     * 修改数据
     *
     * @param tStaffEntry
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TStaffEntry tStaffEntry) {
        int i = this.tStaffEntryMapper.updateByPkId(tStaffEntry);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffEntry
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TStaffEntry tStaffEntry) {
        int i = this.tStaffEntryMapper.deleteById(tStaffEntry);
        if (i > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("入职基本数据删除成功！");
            /**
             * 删除与一级审批相关联的审批数据
             */
            String resultMsg = ObjectAsyncTask.deleteApprovalRecord(tStaffEntry.getApvId());
            stringBuffer.append(resultMsg);
            return new CommonResult(200, "success", stringBuffer.toString(), null);
        } else {
            return new CommonResult(444, "error", "入职基本数据删除失败！", null);
        }
    }

    /**
     * 查询所有数据
     *
     * @param param
     * @return
     */
    @Override
    public CommonResult adminList(String param) {
        JSONObject json = JSON.parseObject(param);
        //查询条件
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        TStaffEntry entity = new TStaffEntry();

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
        List<TStaffEntry> list = this.tStaffEntryMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffEntry>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    @Override
    public CommonResult userGet(HttpServletRequest request) {
        SysToken sysToken = ObjectAsyncTask.getSysToken(request);
        String pkId = sysToken.getFkUserId();
        TStaffEntry entity = this.tStaffEntryMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tStaffEntry 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TStaffEntry tStaffEntry) {
        String pkId = tStaffEntry.getPkEntryId();
        TStaffEntry entity = this.tStaffEntryMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
    /**
     * 弃职
     *
     * @param tStaffEntry 实体对象
     * @return 修改结果
     */
    @Override
    public CommonResult giveUp(TStaffEntry tStaffEntry) {
        tStaffEntry.setIsAbandon(1);
        /**
         * 修改入职信息
         */
        tStaffEntry.setStatus(1);
        int i = tStaffEntryMapper.updateByPkId(tStaffEntry);
        StringBuffer stringBuffer = new StringBuffer();
        if(i > 0){
            stringBuffer.append("离职成功！");
            /**
             * 删除与一级审批相关联的审批数据
             */
            String resultMsg = ObjectAsyncTask.deleteApprovalRecord(tStaffEntry.getApvId());
            stringBuffer.append(resultMsg);
        }
        return new CommonResult(200, "success", stringBuffer.toString(), null);
    }
    /**
     * 发起入职审批页面
     *
     * pkEntryId,操作用户的ID
     *
     * @return 修改结果
     */
    @Override
    public CommonResult approvalPage(HttpServletRequest request,TStaffEntry tStaffEntry) {
        String token = TokenUtil.getRequestToken(request);
        if (StringUtils.isEmpty(token)){
            return new CommonResult(444, "error", "请在请求头中传入token", null);

        }
        SysToken sysToken = tSysTokenMapper.findByToken(token);
        if(sysToken == null){
            return new CommonResult(444, "error", "token已失效，请重新登录后再试", null);
        }
        JSONObject resultJson = ObjectAsyncTask.handleApproval(sysToken,tStaffEntry.getPkEntryId(),"入职申请",1);
        String msg = (String) resultJson.get("msg");
        resultJson.remove("msg");
        return new CommonResult(200, "success", msg, resultJson);

    }
    /**
     * 发起入职审批
     *
     * @return 修改结果
     */
    @Transactional()
    @Override
    public CommonResult approval(HttpServletRequest request,String param) {
        SysToken sysToken = ObjectAsyncTask.getSysToken(request);
        String newPkId = IDUtils.getUUID();
        JSONObject jsonObject = JSON.parseObject(param);
        //入职申请信息主键
        String pkEntryId = JsonUtil.getStringParam(jsonObject, "pkEntryId");
        //审批类型
        String approvalType = JsonUtil.getStringParam(jsonObject, "approvalType");
        /**
         * 修改入职信息表中的apvId
         */
        TStaffEntry entry = new TStaffEntry();
        entry.setPkEntryId(pkEntryId);
        entry.setApvId(newPkId);
        //状态,0代表刚添加完成入职信息，2代表已发起入职审批，正在审批中，1代表审批完成
        entry.setStatus(2);
        int i = tStaffEntryMapper.updateByPkId(entry);
        StringBuffer stringBuffer = new StringBuffer();
        if(i > 0){
            stringBuffer.append("入职审批流程记录添加成功！");
        }else {
            stringBuffer.append("入职审批流程记录添加失败！");
        }
        stringBuffer = ObjectAsyncTask.addApprovalRecord(stringBuffer,jsonObject,sysToken,approvalType,pkEntryId);
        return new CommonResult(200, "success", stringBuffer.toString(), null);
    }

    @Override
    public TStaffEntry selectByPkId(String pkEntryId) {
        return tStaffEntryMapper.selectByPkId(pkEntryId);
    }
    @Override
    public TStaffEntry selectByPkId2(String pkEntryId) {
        TStaffEntry entry = new TStaffEntry();
        entry.setPkEntryId(pkEntryId);
        List<TStaffEntry> tStaffEntryList = tStaffEntryMapper.selectAllPage(entry);
        return tStaffEntryList.get(0);
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TStaffEntry entity = new TStaffEntry();
        List<TStaffEntry> list = this.tStaffEntryMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffEntry>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    
