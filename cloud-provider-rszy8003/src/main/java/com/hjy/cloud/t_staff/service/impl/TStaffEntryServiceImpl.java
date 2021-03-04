package com.hjy.cloud.t_staff.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.common.entity.DApvRecord;
import com.hjy.cloud.common.task.ObjectAsyncTask;
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
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.page.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.JsonUtil;

import javax.annotation.Resource;
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
    private TApvApvtypeMapper tApvApvtypeMapper;
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
            sb.append("入职申请添加成功！");
            //再添加审批记录
            Map<String,Object> resultMap = this.addApvRecord("入职申请",tStaffEntry);
            String apvId = (String) resultMap.get("firstApvId");
            String msg = (String) resultMap.get("msg");
            sb.append(msg);
            //修改数据
            TStaffEntry updateEntity = new TStaffEntry();
            updateEntity.setPkEntryId(tStaffEntry.getPkEntryId());
            updateEntity.setApvId(apvId);
            int j = tStaffEntryMapper.updateByPkId(updateEntity);
            JSONObject resultJson = this.getListInfo();
            return new CommonResult(200, "success", sb.toString(), resultJson);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    private Map<String,Object> addApvRecord(String param,TStaffEntry tStaffEntry) {
        Map<String, Object> map = new HashMap<>();
        List<DApvRecord> apvRecords = new ArrayList<>();
        //先查询入职申请的PK_ID
        TApvApvtype entity = tApvApvtypeMapper.selectByName(param);
        if (entity != null) {
            //12
            String pk_apv_id = null;
            String approvalType = entity.getPkApvtypeId();
            int isStart = 1;
            boolean flag = true;
            String newPkId = IDUtils.getUUID();
            map.put("firstApvId", newPkId);
            while (flag) {
                TApvApproval apv = tApvApprovalMapper.selectApvSet(pk_apv_id, approvalType, isStart);
                if (apv != null) {
                    String nextPkId = IDUtils.getUUID();
                    DApvRecord apvRecord = new DApvRecord();
                    apvRecord.setPkRecordId(newPkId);
                    apvRecord.setApprovalType(approvalType);
                    apvRecord.setSponsor(tStaffEntry.getStaffName());
                    apvRecord.setStartTime(new Date());
                    apvRecord.setApvApproval(apv.getApprovalPeople());
                    apvRecord.setSourceId(tStaffEntry.getPkEntryId());
                    apvRecord.setIsStart(isStart);
                    apvRecord.setIsIng(1);
                    if (!apv.getNextApproval().equals("0")) {
                        /**
                         * 说明还有下级审批
                         */
                        pk_apv_id = apv.getNextApproval();
                        //添加审批记录
                        apvRecord.setNextApproval(nextPkId);
                        newPkId = nextPkId;
                    } else {
                        /**
                         * 该审批只有一个流程
                         */
                        //添加审批记录
                        apvRecord.setNextApproval("0");
                        flag = false;
                    }
                    apvRecords.add(apvRecord);
                    isStart = 0;
                } else {
                    map.put("msg", "入职申请未设置审批人，直接通过！");
                    map.put("firstApvId", null);
                }
            }
            //批量添加审批记录
            int i = tApvApprovalMapper.insertApvRecordBatch(apvRecords);
            if(i > 0){
                map.put("msg", "入职审批流程记录添加成功！");
            }else {

            }
        } else {
            map.put("msg", "未有入职申请审批流程，直接通过！");
            map.put("firstApvId", null);
        }
        return map;

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
    public CommonResult selectAll(String param) {
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
    
