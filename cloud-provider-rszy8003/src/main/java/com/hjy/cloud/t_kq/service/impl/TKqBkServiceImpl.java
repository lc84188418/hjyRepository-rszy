package com.hjy.cloud.t_kq.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.t_kq.dao.TKqBkMapper;
import com.hjy.cloud.t_kq.dao.TKqGroupMapper;
import com.hjy.cloud.t_kq.entity.*;
import com.hjy.cloud.t_kq.service.TKqBkService;
import com.hjy.cloud.t_staff.dao.TStaffInfoMapper;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.page.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.JsonUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * (TKqBk)表服务实现类
 *
 * @author makejava
 * @since 2021-03-16 14:50:58
 */
@Service("tKqBkService")
public class TKqBkServiceImpl implements TKqBkService {

    @Resource
    private TKqBkMapper tKqBkMapper;
    @Resource
    private TStaffInfoMapper tStaffInfoMapper;
    @Resource
    private TKqGroupMapper tKqGroupMapper;

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
        //考勤组列表
        List<TKqGroup> groupList = tKqGroupMapper.selectAllId_name();
        jsonObject.put("groupList", groupList);
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
    public CommonResult insert(String param) {
        JSONObject json = JSON.parseObject(param);
        TKqBk tKqBk = new TKqBk();
        String pkBkId = IDUtils.getUUID();
        //实体数据
        String bkName = JsonUtil.getStringParam(json, "bkName");
        String bkStewards = JsonUtil.getStringParam(json, "bkStewards");
        int bkNum = JsonUtil.getIntegerParam(json, "bkNum");
        int bkDate = JsonUtil.getIntegerParam(json, "bkDate");
//        int turnOn = JsonUtil.getIntegerParam(json, "turnOn");
//        int isDefault = JsonUtil.getIntegerParam(json, "isDefault");
        //如果设置为默认规则，之前的默认规则需要修改isDefault=0
        tKqBk.setPkBkId(pkBkId);
        tKqBk.setBkName(bkName);
        tKqBk.setBkStewards(bkStewards);
        tKqBk.setBkNum(bkNum);
        tKqBk.setBkDate(bkDate);
//        tKqBk.setTurnOn(turnOn);
        tKqBk.setTurnOn(1);
//        tKqBk.setIsDefault(isDefault);
        tKqBk.setIsDefault(0);
        int i = this.tKqBkMapper.insertSelective(tKqBk);
        StringBuffer stringBuffer = new StringBuffer();
        if (i > 0) {
            stringBuffer.append("补卡规则数据添加成功！");
        }else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
        /**
         * 应用范围
         */
        List<ReBkGroup> joinList = new ArrayList<>();
        JSONArray joinArray = json.getJSONArray("groups");
        if(joinArray == null){
            stringBuffer.append("未选择考勤分组！");
        }else{
            String joinStr = joinArray.toString();
            if(joinStr.equals("[]")){
                stringBuffer.append("未选择考勤分组！");
            }else {
                joinList = JSONObject.parseArray(joinStr,ReBkGroup.class);
            }
        }
        if(joinList != null && joinList.size() > 0){
            Iterator<ReBkGroup> joinIterator = joinList.iterator();
            while (joinIterator.hasNext()){
                ReBkGroup joinNext = joinIterator.next();
                joinNext.setPkBkgroupId(IDUtils.getUUID());
                joinNext.setFkBkId(pkBkId);
            }
            int j = this.tKqBkMapper.insertBkGroupBatch(joinList);
            if (j > 0) {
                stringBuffer.append("考勤分组设置成功！");
            }
        }
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
    public CommonResult updateByPkId(String param) {
        JSONObject json = JSON.parseObject(param);
        TKqBk tKqBk = new TKqBk();
        //实体数据
        String pkBkId = JsonUtil.getStringParam(json, "pkBkId");
        String bkName = JsonUtil.getStringParam(json, "bkName");
        String bkStewards = JsonUtil.getStringParam(json, "bkStewards");
        int bkNum = JsonUtil.getIntegerParam(json, "bkNum");
        int bkDate = JsonUtil.getIntegerParam(json, "bkDate");
//        int turnOn = JsonUtil.getIntegerParam(json, "turnOn");
        tKqBk.setPkBkId(pkBkId);
        tKqBk.setBkName(bkName);
        tKqBk.setBkStewards(bkStewards);
        tKqBk.setBkNum(bkNum);
        tKqBk.setBkDate(bkDate);
//        tKqBk.setTurnOn(turnOn);
        tKqBk.setTurnOn(1);
        int i = this.tKqBkMapper.updateByPkId(tKqBk);
        StringBuffer stringBuffer = new StringBuffer();
        if (i > 0) {
            stringBuffer.append("补卡规则数据修改成功！");
        }else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
        /**
         * 应用范围
         */
        //先删除之前的
        int k = tKqBkMapper.deleteBkGroupByBkId(pkBkId);
        List<ReBkGroup> joinList = new ArrayList<>();
        JSONArray joinArray = json.getJSONArray("groups");
        if(joinArray == null){
            stringBuffer.append("未选择考勤分组！");
        }else{
            String joinStr = joinArray.toString();
            if(joinStr.equals("[]")){
                stringBuffer.append("未选择考勤分组！");
            }else {
                joinList = JSONObject.parseArray(joinStr,ReBkGroup.class);
            }
        }
        if(joinList != null && joinList.size() > 0){
            Iterator<ReBkGroup> joinIterator = joinList.iterator();
            while (joinIterator.hasNext()){
                ReBkGroup joinNext = joinIterator.next();
                joinNext.setPkBkgroupId(IDUtils.getUUID());
                joinNext.setFkBkId(pkBkId);
            }
            int j = this.tKqBkMapper.insertBkGroupBatch(joinList);
            if (j > 0) {
                stringBuffer.append("考勤分组设置成功！");
            }
        }
        JSONObject listInfo = this.getListInfo();
        return new CommonResult(200, "success", stringBuffer.toString(), listInfo);
    }

    /**
     * 删除数据
     *
     * @param tKqBk
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TKqBk tKqBk) {
        //删除补卡分组数据
        int i = this.tKqBkMapper.deleteById(tKqBk);
        StringBuffer stringBuffer = new StringBuffer();
        if(i > 0){
            stringBuffer.append("补卡规则基本信息删除成功！");
        }else {
            return new CommonResult(444, "error", "默认补卡规则无法！", null);
        }
        int j = tKqBkMapper.deleteBkGroupByBkId(tKqBk.getPkBkId());
        stringBuffer.append("已清除补卡规则相关考勤组设置，"+j+"个");
        JSONObject listInfo = this.getListInfo();
        return new CommonResult(200, "success", stringBuffer.toString(), listInfo);
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
        TKqBk entity = new TKqBk();

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
        List<TKqBk> list = this.tKqBkMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TKqBk>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tKqBk 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TKqBk tKqBk) {
        String pkId = tKqBk.getPkBkId();
        TKqBk entity = this.tKqBkMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        //考勤分组列表
        List<TKqGroup> groupList = tKqGroupMapper.selectAllId_name();
        resultJson.put("groupList", groupList);
        //已选考勤分组
        List<TKqGroup> groups = tKqBkMapper.select_YX_BkGroupByBkId(pkId);
        resultJson.put("groups", groups);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TKqBk entity = new TKqBk();
        List<TKqBk> list = this.tKqBkMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TKqBk>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    
