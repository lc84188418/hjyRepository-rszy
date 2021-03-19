package com.hjy.cloud.t_kq.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.t_apv.entity.TApvApproval;
import com.hjy.cloud.t_kq.dao.TKqBcMapper;
import com.hjy.cloud.t_kq.dao.TKqGroupMapper;
import com.hjy.cloud.t_kq.dao.TKqJbMapper;
import com.hjy.cloud.t_kq.entity.*;
import com.hjy.cloud.t_kq.service.TKqGroupService;
import com.hjy.cloud.t_outfit.dao.TOutfitWorkaddressMapper;
import com.hjy.cloud.t_outfit.entity.TOutfitWorkaddress;
import com.hjy.cloud.t_staff.dao.TStaffInfoMapper;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.page.PageUtil;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.JsonUtil;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * (TKqGroup)表服务实现类
 *
 * @author makejava
 * @since 2021-03-16 14:50:59
 */
@Service("tKqGroupService")
public class TKqGroupServiceImpl implements TKqGroupService {

    @Resource
    private TKqGroupMapper tKqGroupMapper;
    @Resource
    private TStaffInfoMapper tStaffInfoMapper;
    @Resource
    private TOutfitWorkaddressMapper tOutfitWorkaddressMapper;
    @Resource
    private TKqBcMapper tKqBcMapper;
    @Resource
    private TKqJbMapper tKqJbMapper;
    /**
     * 添加前获取数据
     *
     * @return
     */
    @Override
    public CommonResult insertPage() {
        //返回员工列表
        List<TStaffInfo> staffInfos = tStaffInfoMapper.selectAllId_Name();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("staffList", staffInfos);
        //班次列表
        TKqBc selectBc = new TKqBc();
        selectBc.setTurnOn(1);
        List<TKqBc> bcList = tKqBcMapper.selectAllPage(selectBc);
        jsonObject.put("bcList", bcList);
        //办公地
        List<TOutfitWorkaddress> workaddressList = tOutfitWorkaddressMapper.selectAllId_Name();
        jsonObject.put("workaddressList", workaddressList);
        //加班规则
        TKqJb selectJb = new TKqJb();
        List<TKqJb> jbList = tKqJbMapper.selectAllPage(selectJb);
        jsonObject.put("jbList", jbList);
        return new CommonResult(200, "success", "获取数据成功", jsonObject);
    }

    /**
     * 添加数据
     *
     * @param param
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(String param) throws ParseException {
        JSONObject json = JSON.parseObject(param);
        String pkGroupId = IDUtils.getUUID();
        //实体基本数据
        String groupName = JsonUtil.getStringParam(json, "groupName");
        String groupStewards = JsonUtil.getStringParam(json, "groupStewards");
        int kqType = JsonUtil.getIntegerParam(json, "kqType");
        int isPaixiu = JsonUtil.getIntegerParam(json, "isPaixiu");
        Date bxdkTime = JsonUtil.getDateParam(json, "yyyy-MM-dd","bxdkTime");
        Date wxdkTime = JsonUtil.getDateParam(json, "yyyy-MM-dd","wxdkTime");
        int kqWay = JsonUtil.getIntegerParam(json, "kqWay");
        int isWq = JsonUtil.getIntegerParam(json, "isWq");
        StringBuffer stringBuffer = new StringBuffer();
        //开始添加
        TKqGroup tKqGroup = new TKqGroup();
        tKqGroup.setPkGroupId(pkGroupId);
        tKqGroup.setTurnOn(1);
        tKqGroup.setGroupName(groupName);
        tKqGroup.setGroupStewards(groupStewards);
        tKqGroup.setKqType(kqType);
        tKqGroup.setIsPaixiu(isPaixiu);
        tKqGroup.setBxdkTime(bxdkTime);
        tKqGroup.setWxdkTime(wxdkTime);
        tKqGroup.setKqWay(kqWay);
        tKqGroup.setIsWq(isWq);
        int i = this.tKqGroupMapper.insertSelective(tKqGroup);
        if (i > 0) {
            stringBuffer.append("考勤组数据添加成功！");
        } else {
            stringBuffer.append("考勤组数据添加失败！");
        }
        stringBuffer = this.handleGroupData(stringBuffer,json,pkGroupId,"add");
        JSONObject listInfo = this.getListInfo();
        return new CommonResult(200, "success", stringBuffer.toString(), listInfo);
    }



    /**
     * 修改数据
     *
     * @param param
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(String param) throws ParseException {
        JSONObject json = JSON.parseObject(param);
        //参数
        String pkGroupId = JsonUtil.getStringParam(json, "pkGroupId");
        //实体基本数据
        String groupName = JsonUtil.getStringParam(json, "groupName");
        String groupStewards = JsonUtil.getStringParam(json, "groupStewards");
        int kqType = JsonUtil.getIntegerParam(json, "kqType");
        int isPaixiu = JsonUtil.getIntegerParam(json, "isPaixiu");
        Date bxdkTime = JsonUtil.getDateParam(json, "yyyy-MM-dd","bxdkTime");
        Date wxdkTime = JsonUtil.getDateParam(json, "yyyy-MM-dd","wxdkTime");
        int kqWay = JsonUtil.getIntegerParam(json, "kqWay");
        int isWq = JsonUtil.getIntegerParam(json, "isWq");
        StringBuffer stringBuffer = new StringBuffer();
        //开始添加实体数据
        TKqGroup tKqGroup = new TKqGroup();
        tKqGroup.setPkGroupId(pkGroupId);
        tKqGroup.setTurnOn(1);
        tKqGroup.setGroupName(groupName);
        tKqGroup.setGroupStewards(groupStewards);
        tKqGroup.setKqType(kqType);
        tKqGroup.setIsPaixiu(isPaixiu);
        tKqGroup.setBxdkTime(bxdkTime);
        tKqGroup.setWxdkTime(wxdkTime);
        tKqGroup.setKqWay(kqWay);
        tKqGroup.setIsWq(isWq);
        int i = this.tKqGroupMapper.updateByPkId(tKqGroup);
        if (i > 0) {
            stringBuffer.append("数据提交成功！");
        } else {
            stringBuffer.append("数据提交失败！");
        }
        stringBuffer = this.handleGroupData(stringBuffer,json,pkGroupId,"update");
        JSONObject listInfo = this.getListInfo();
        return new CommonResult(200, "success", stringBuffer.toString(), listInfo);
    }

    /**
     * 删除数据
     *
     * @param tKqGroup
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TKqGroup tKqGroup) {
        //删除考勤分组基本信息
        int i = this.tKqGroupMapper.deleteById(tKqGroup);
        //删除补卡-group
        int j = this.tKqGroupMapper.deleteBkGroupByGroupId(tKqGroup.getPkGroupId());
        //删除group-staff
        int k = this.tKqGroupMapper.deleteGroupStaffByGroupId(tKqGroup.getPkGroupId());
        //删除group-workaddress
        int l = this.tKqGroupMapper.deleteGroupWorkaddressByGroupId(tKqGroup.getPkGroupId());
        //删除group-workingdays
        int m = this.tKqGroupMapper.deleteGroupBcByGroupId(tKqGroup.getPkGroupId());
        //删除jb-group
        int n = this.tKqGroupMapper.deleteJbGroupByGroupId(tKqGroup.getPkGroupId());
        if (i > 0) {
            JSONObject listInfo = this.getListInfo();
            return new CommonResult(200, "success", "删除数据成功", listInfo);
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
        //分页参数
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        //查询条件
        TKqGroup entity = new TKqGroup();
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
        List<TKqGroup> list = this.tKqGroupMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TKqGroup>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tKqGroup 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TKqGroup tKqGroup) {
        String pkId = tKqGroup.getPkGroupId();
        JSONObject resultJson = new JSONObject();
        //当前考勤组信息
        TKqGroup entity = this.tKqGroupMapper.selectByPkId(pkId);
        resultJson.put("entity", entity);
        //所有员工列表
        List<TStaffInfo> staffInfos = tStaffInfoMapper.selectAllId_Name();
        resultJson.put("staffList", staffInfos);
        //已选参与考勤人员列表
        List<ReGroupStaff> joins = tKqGroupMapper.select_YX_StaffByGroup_IsKQ(pkId,1);
        resultJson.put("joins", joins);
        //已选无需考勤人员列表
        List<ReGroupStaff> frees = tKqGroupMapper.select_YX_StaffByGroup_IsKQ(pkId,0);
        resultJson.put("frees", frees);
        //所有班次列表
        TKqBc selectBc = new TKqBc();
        selectBc.setTurnOn(1);
        List<TKqBc> bcList = tKqBcMapper.selectAllPage(selectBc);
        resultJson.put("bcList", bcList);
        //已选工作日设置
        List<ReGroupWorkingdays> workingDays = tKqGroupMapper.select_YX_workingdaysByGroup(pkId);
        resultJson.put("workingDays", workingDays);
        //工作地列表
        List<TOutfitWorkaddress> workaddressList = tOutfitWorkaddressMapper.selectAllId_Name();
        resultJson.put("workaddressList", workaddressList);
        //已选工作地
        List<ReGroupWorkaddress> workaddress = tKqGroupMapper.select_YX_workaddressByGroup(pkId);
        resultJson.put("workaddress", workaddress);
        //所有加班规则列表
        TKqJb selectJb = new TKqJb();
        List<TKqJb> jbRuleList = tKqJbMapper.selectAllPage(selectJb);
        resultJson.put("jbRuleList", jbRuleList);
        //已选加班规则
        List<ReJbGroup> jbRules = tKqGroupMapper.select_YX_JbByGroup(pkId);
        resultJson.put("jbRules", jbRules);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    private StringBuffer handleGroupData(StringBuffer stringBuffer, JSONObject json, String pkGroupId,String addOrUpdate) {
        /**
         * 一、考勤人员设置
         */
        //参与考勤人员
        List<ReGroupStaff> joinList = new ArrayList<>();
        JSONArray joinArray = json.getJSONArray("joins");
        if(joinArray == null){
            stringBuffer.append("未选择考勤人！");
        }else{
            String joinStr = joinArray.toString();
            if(joinStr.equals("[]")){
                stringBuffer.append("未选择考勤人！");
            }else {
                joinList = JSONObject.parseArray(joinStr,ReGroupStaff.class);
            }
        }
        if(joinList != null && joinList.size() > 0){
            Iterator<ReGroupStaff> joinIterator = joinList.iterator();
            while (joinIterator.hasNext()){
                ReGroupStaff joinNext = joinIterator.next();
                joinNext.setPkGroupstaffId(IDUtils.getUUID());
                joinNext.setFkGroupId(pkGroupId);
                joinNext.setIsKq(1);
            }
        }
        //无需考勤人员
        List<ReGroupStaff> freeList = new ArrayList<>();
        JSONArray freeArray = json.getJSONArray("frees");
        if(freeArray == null){
            stringBuffer.append("不添加免考勤人！");
        }else{
            String freeStr = freeArray.toString();
            if(freeStr.equals("[]")){
                stringBuffer.append("不添加免考勤人！");
            }else {
                freeList = JSONObject.parseArray(freeStr,ReGroupStaff.class);
            }
        }
        if(freeList != null && freeList.size() > 0){
            Iterator<ReGroupStaff> freeIterator = freeList.iterator();
            while (freeIterator.hasNext()){
                ReGroupStaff freeNext = freeIterator.next();
                freeNext.setPkGroupstaffId(IDUtils.getUUID());
                freeNext.setFkGroupId(pkGroupId);
                freeNext.setIsKq(0);
                //
                joinList.add(freeNext);
            }
        }
        if(joinList != null && joinList.size() > 0){
            if("update".equals(addOrUpdate)){
                //代表修改-先删除
                tKqGroupMapper.deleteGroupStaffByGroupId(pkGroupId);
            }
            int j = tKqGroupMapper.insertGroupStaffBatch(joinList);
            if (j > 0) {
                stringBuffer.append("考勤人员设置添加成功！");
            } else {
                stringBuffer.append("考勤人员设置添加失败！");
            }
        }
        /**
         * 二、工作日设置
         */
        List<ReGroupWorkingdays> workingDaysList = new ArrayList<>();
        JSONArray workingDaysArray = json.getJSONArray("workingDays");
        if(workingDaysArray == null){
            stringBuffer.append("未设置工作日！");
        }else{
            String workingDaysStr = workingDaysArray.toString();
            if(workingDaysStr.equals("[]")){
                stringBuffer.append("未设置工作日！");
            }else {
                workingDaysList = JSONObject.parseArray(workingDaysStr,ReGroupWorkingdays.class);
            }
        }
        if(workingDaysList != null && workingDaysList.size() > 0){
            Iterator<ReGroupWorkingdays> workingdaysIterator = workingDaysList.iterator();
            while (workingdaysIterator.hasNext()){
                ReGroupWorkingdays workingdays = workingdaysIterator.next();
                workingdays.setPkGroupworkingdaysId(IDUtils.getUUID());
                workingdays.setFkGroupId(pkGroupId);
                //
            }
            if("update".equals(addOrUpdate)){
                //代表修改-先删除
                tKqGroupMapper.deleteGroupBcByGroupId(pkGroupId);
            }
            //批量添加分组班次设置
            int k = tKqGroupMapper.insertGroupBcBatch(workingDaysList);
            if (k > 0) {
                stringBuffer.append("班次设置成功！");
            } else {
                stringBuffer.append("班次设置失败！");
            }
        }

        /**
         * 三、工作地设置
         */
        List<ReGroupWorkaddress> workAddressList = new ArrayList<>();
        JSONArray workAddressArray = json.getJSONArray("workAddress");
        if(workAddressArray == null){
            stringBuffer.append("未设置办公地点！");
        }else{
            String workAddressStr = workAddressArray.toString();
            if(workAddressStr.equals("[]")){
                stringBuffer.append("未设置办公地点！");
            }else {
                workAddressList = JSONObject.parseArray(workAddressStr,ReGroupWorkaddress.class);
            }
        }
        if(workAddressList != null && workAddressList.size() > 0){
            Iterator<ReGroupWorkaddress> workAddressIterator = workAddressList.iterator();
            while (workAddressIterator.hasNext()){
                ReGroupWorkaddress workAddress = workAddressIterator.next();
                workAddress.setPkGroupworkaddressId(IDUtils.getUUID());
                workAddress.setFkGroupId(pkGroupId);
                //
            }
            if("update".equals(addOrUpdate)){
                //代表修改-先删除
                tKqGroupMapper.deleteGroupWorkaddressByGroupId(pkGroupId);
            }
            //批量添加分组工作地设置
            int m = tKqGroupMapper.insertGroupWorkaddressBatch(workAddressList);
            if (m > 0) {
                stringBuffer.append("工作地设置成功！");
            } else {
                stringBuffer.append("工作地设置失败！");
            }
        }
        /**
         * 四、加班规则设置
         */
        List<ReJbGroup> jbList = new ArrayList<>();
        JSONArray jbRuleArray = json.getJSONArray("jbRule");
        if(jbRuleArray == null){
            stringBuffer.append("未设置加班规则!");
        }else{
            String jbRuleStr = jbRuleArray.toString();
            if(jbRuleStr.equals("[]")){
                stringBuffer.append("未设置加班规则！");
            }else {
                jbList = JSONObject.parseArray(jbRuleStr,ReJbGroup.class);
            }
        }
        if(jbList != null && jbList.size() > 0){
            Iterator<ReJbGroup> jbRuleIterator = jbList.iterator();
            while (jbRuleIterator.hasNext()){
                ReJbGroup jbRule = jbRuleIterator.next();
                jbRule.setPkJbgroupId(IDUtils.getUUID());
                jbRule.setFkGroupId(pkGroupId);
                //
            }
            if("update".equals(addOrUpdate)){
                //代表修改-先删除
                tKqGroupMapper.deleteJbGroupByGroupId(pkGroupId);
            }
            //批量添加分组加班规则设置
            int n = tKqGroupMapper.insertJbGroupBatch(jbList);
            if (n > 0) {
                stringBuffer.append("加班规则设置成功！");
            } else {
                stringBuffer.append("加班规则设置失败！");
            }
        }
        return stringBuffer;
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TKqGroup entity = new TKqGroup();
        List<TKqGroup> list = this.tKqGroupMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TKqGroup>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    
