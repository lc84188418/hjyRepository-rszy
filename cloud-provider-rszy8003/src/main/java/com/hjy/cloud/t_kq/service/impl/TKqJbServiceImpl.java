package com.hjy.cloud.t_kq.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_kq.dao.TKqGroupMapper;
import com.hjy.cloud.t_kq.dao.TKqJbMapper;
import com.hjy.cloud.t_kq.entity.ReJbGroup;
import com.hjy.cloud.t_kq.entity.TKqGroup;
import com.hjy.cloud.t_kq.entity.TKqJb;
import com.hjy.cloud.t_kq.service.TKqJbService;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.JsonUtil;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.utils.page.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * (TKqJb)表服务实现类
 *
 * @author makejava
 * @since 2021-03-16 14:51:00
 */
@Service("tKqJbService")
public class TKqJbServiceImpl implements TKqJbService {

    @Resource
    private TKqJbMapper tKqJbMapper;
    @Resource
    private TKqGroupMapper tKqGroupMapper;
    /**
     * 添加前获取数据
     *
     * @return
     */
    @Override
    public CommonResult insertPage() {
        //考勤分组的数据
        List<TKqGroup> groupList = tKqGroupMapper.selectAllId_name();
        JSONObject jsonObject = new JSONObject();
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
        TKqJb tKqJb = new TKqJb();
        String pkJbId = IDUtils.getUUID();
        //实体数据
        String jbName = JsonUtil.getStringParam(json, "jbName");
//        String fxyjFrequency = JsonUtil.getStringParam(json, "fxyjFrequency");
//        int fxyjHour = JsonUtil.getIntegerParam(json, "fxyjHour");
        int jsWay = JsonUtil.getIntegerParam(json, "jsWay");
        String jbUnit = JsonUtil.getStringParam(json, "jbUnit");
//        int isTxJbf = JsonUtil.getIntegerParam(json, "isTxJbf");
        String txRule = JsonUtil.getStringParam(json, "txRule");
        String jbfRule = JsonUtil.getStringParam(json, "jbfRule");
//        int turnOn = JsonUtil.getIntegerParam(json, "turnOn");
//        int isDefault = JsonUtil.getIntegerParam(json, "isDefault");
        //如果设置为默认规则，之前的默认规则需要修改isDefault=0
        tKqJb.setPkJbId(pkJbId);
        tKqJb.setJbName(jbName);
        tKqJb.setJsWay(jsWay);
//        tKqJb.setFxyjFrequency(fxyjFrequency);
//        tKqJb.setFxyjHour(fxyjHour);
        tKqJb.setJbUnit(jbUnit);
//        tKqJb.setIsTxJbf(isTxJbf);
        tKqJb.setIsTxJbf(1);
        tKqJb.setTxRule(txRule);
        tKqJb.setJbfRule(jbfRule);
//        tKqJb.setTurnOn(turnOn);
        tKqJb.setTurnOn(1);
//        tKqJb.setIsDefault(isDefault);
        tKqJb.setIsDefault(0);
        int i = this.tKqJbMapper.insertSelective(tKqJb);
        StringBuffer stringBuffer = new StringBuffer();
        if (i > 0) {
            stringBuffer.append("加班规则数据添加成功！");
        }else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
        /**
         * 应用范围
         */
        List<ReJbGroup> joinList = new ArrayList<>();
        JSONArray joinArray = json.getJSONArray("groups");
        if(joinArray == null){
            stringBuffer.append("未选择考勤分组！");
        }else{
            String joinStr = joinArray.toString();
            if(joinStr.equals("[]")){
                stringBuffer.append("未选择考勤分组！");
            }else {
                joinList = JSONObject.parseArray(joinStr,ReJbGroup.class);
            }
        }
        if(joinList != null && joinList.size() > 0){
            //删除分组原来分配的加班规则
            int k = tKqGroupMapper.deleteJbGroupByGroupId_Batch(joinList);
            Iterator<ReJbGroup> joinIterator = joinList.iterator();
            while (joinIterator.hasNext()){
                ReJbGroup joinNext = joinIterator.next();
                joinNext.setPkJbgroupId(IDUtils.getUUID());
                joinNext.setFkJbId(pkJbId);
            }
            int j = this.tKqJbMapper.insertJbGroupBatch(joinList);
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
        TKqJb tKqJb = new TKqJb();
        //实体数据
        String pkJbId = JsonUtil.getStringParam(json, "pkJbId");
        String jbName = JsonUtil.getStringParam(json, "jbName");
//        String fxyjFrequency = JsonUtil.getStringParam(json, "fxyjFrequency");
//        int fxyjHour = JsonUtil.getIntegerParam(json, "fxyjHour");
        int jsWay = JsonUtil.getIntegerParam(json, "jsWay");
        String jbUnit = JsonUtil.getStringParam(json, "jbUnit");
//        int isTxJbf = JsonUtil.getIntegerParam(json, "isTxJbf");
        String txRule = JsonUtil.getStringParam(json, "txRule");
        String jbfRule = JsonUtil.getStringParam(json, "jbfRule");
        int turnOn = JsonUtil.getIntegerParam(json, "turnOn");

        tKqJb.setPkJbId(pkJbId);
        tKqJb.setJbName(jbName);
        tKqJb.setJsWay(jsWay);
//        tKqJb.setFxyjFrequency(fxyjFrequency);
//        tKqJb.setFxyjHour(fxyjHour);
        tKqJb.setJbUnit(jbUnit);
        tKqJb.setIsTxJbf(1);
        tKqJb.setTxRule(txRule);
        tKqJb.setJbfRule(jbfRule);
        tKqJb.setTurnOn(turnOn);
        int i = this.tKqJbMapper.updateByPkId(tKqJb);
        StringBuffer stringBuffer = new StringBuffer();
        if (i > 0) {
            stringBuffer.append("加班规则数据修改成功！");
        }else {
            return new CommonResult(444, "error", "数据修改失败", null);
        }
        /**
         * 应用范围
         */
        int j = this.tKqJbMapper.deleteJbGroupByJbId(pkJbId);
        List<ReJbGroup> joinList = new ArrayList<>();
        JSONArray joinArray = json.getJSONArray("groups");
        if(joinArray == null){
            stringBuffer.append("未选择考勤分组！");
        }else{
            String joinStr = joinArray.toString();
            if(joinStr.equals("[]")){
                stringBuffer.append("未选择考勤分组！");
            }else {
                joinList = JSONObject.parseArray(joinStr,ReJbGroup.class);
            }
        }
        if(joinList != null && joinList.size() > 0){
            Iterator<ReJbGroup> joinIterator = joinList.iterator();
            while (joinIterator.hasNext()){
                ReJbGroup joinNext = joinIterator.next();
                joinNext.setPkJbgroupId(IDUtils.getUUID());
                joinNext.setFkJbId(pkJbId);
            }
            int k = this.tKqJbMapper.insertJbGroupBatch(joinList);
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
     * @param tKqJb
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TKqJb tKqJb) {
        //删除加班规则-分组
        int j = this.tKqJbMapper.deleteJbGroupByJbId(tKqJb.getPkJbId());
        //加班规则基本信息
        int i = this.tKqJbMapper.deleteById(tKqJb);
        if (i > 0) {
            JSONObject listInfo = this.getListInfo();
            return new CommonResult(200, "success", "加班规则删除成功!", listInfo);
        } else {
            return new CommonResult(444, "error", "默认加班规则无法删除！", null);
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
        TKqJb entity = new TKqJb();
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
        List<TKqJb> list = this.tKqJbMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TKqJb>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tKqJb 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TKqJb tKqJb) {
        String pkId = tKqJb.getPkJbId();
        TKqJb entity = this.tKqJbMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        //考勤分组列表
        List<TKqGroup> groupList = tKqGroupMapper.selectAllId_name();
        resultJson.put("groupList", groupList);
        //已选考勤分组
        List<TKqGroup> groups = tKqJbMapper.select_YX_JbGroupByJbId(pkId);
        resultJson.put("groups", groups);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TKqJb entity = new TKqJb();
        List<TKqJb> list = this.tKqJbMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TKqJb>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    
