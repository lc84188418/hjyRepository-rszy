package com.hjy.cloud.t_kq.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_kq.dao.TKqBcMapper;
import com.hjy.cloud.t_kq.entity.TKqBc;
import com.hjy.cloud.t_kq.service.TKqBcService;
import com.hjy.cloud.t_staff.dao.TStaffInfoMapper;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.JsonUtil;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.utils.page.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (TKqBc)表服务实现类
 *
 * @author makejava
 * @since 2021-03-16 14:50:57
 */
@Service("tKqBcService")
public class TKqBcServiceImpl implements TKqBcService {

    @Resource
    private TKqBcMapper tKqBcMapper;
    @Resource
    private TStaffInfoMapper tStaffInfoMapper;
    /**
     * 添加前获取数据
     *
     * @return
     */
    @Override
    public CommonResult insertPage() {
        //返回员工列表
        List<TStaffInfo> staffList = tStaffInfoMapper.selectAllId_Name();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("staffList", staffList);
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
        StringBuffer stringBuffer = new StringBuffer();
        //分页参数
        List<String> timeSlots = new ArrayList<>();
        JSONArray timeSlotArray = json.getJSONArray("timeSlots");
        if(timeSlotArray == null){
            stringBuffer.append("未输入上下班时间段，已设置为默认时段09:00-17:30");
        }else{
            String timeSlotStr = timeSlotArray.toString();
            if(timeSlotStr.equals("[]")){
                stringBuffer.append("未输入上下班时间段，已设置为默认时段09:00-17:30");
            }else {
                timeSlots = JSONObject.parseArray(timeSlotStr,String.class);
            }
        }
        String timeSlot = "09:00-17:30";
        if(timeSlots != null && timeSlots.size() > 0){
            timeSlot = "";
            for (String s : timeSlots) {
                timeSlot =timeSlot +"&"+s;
            }
        }
        int isRestDefault = 1;
//        int isRest = JsonUtil.getIntegerParam(json, "isRest");
//        isRestDefault = isRest;
        String restSlot = JsonUtil.getStringParam(json, "restSlot");
        if(restSlot == null){
            isRestDefault = 0;
        }else {
            String restSlotStr = restSlot.toString();
            if(restSlotStr.equals("[]")){
                isRestDefault = 0;
            }
        }
        int txdk = JsonUtil.getIntegerParam(json, "txdk");
        String bcStewards = JsonUtil.getStringParam(json, "bcStewards");
//        int turnOn = JsonUtil.getIntegerParam(json, "turnOn");
//        int isDefault = JsonUtil.getIntegerParam(json, "isDefault");
        //如果设置为默认规则isDefault=1，之前的默认规则需要修改isDefault=0
        TKqBc tKqBc = new TKqBc();
        tKqBc.setPkBcId(IDUtils.getUUID());
        tKqBc.setDkNum(timeSlots.size());
        tKqBc.setTimeSlot(timeSlot.substring(1,timeSlot.length()));
        tKqBc.setIsRest(isRestDefault);
        tKqBc.setRestSlot(restSlot);
        tKqBc.setTxdk(txdk);
        tKqBc.setBcStewards(bcStewards);
//        tKqBc.setTurnOn(turnOn);
        tKqBc.setTurnOn(1);
//        tKqBc.setIsDefault(isDefault);
        tKqBc.setIsDefault(0);
        int i = this.tKqBcMapper.insertSelective(tKqBc);
        if (i > 0) {
            stringBuffer.append("班次数据添加成功！");
            JSONObject listInfo = this.getListInfo();
            return new CommonResult(200, "success", stringBuffer.toString(), listInfo);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
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
        String pkBcId = JsonUtil.getStringParam(json, "pkBcId");
        StringBuffer stringBuffer = new StringBuffer();
        //分页参数
        List<String> timeSlots = new ArrayList<>();
        JSONArray timeSlotArray = json.getJSONArray("timeSlots");
        if(timeSlotArray == null){
            stringBuffer.append("未输入上下班时间段，已设置为默认时段09:00-17:30");
        }else{
            String timeSlotStr = timeSlotArray.toString();
            if(timeSlotStr.equals("[]")){
                stringBuffer.append("未输入上下班时间段，已设置为默认时段09:00-17:30");
            }else {
                timeSlots = JSONObject.parseArray(timeSlotStr,String.class);
            }
        }
        String timeSlot = "09:00-17:30";
        if(timeSlots != null && timeSlots.size() > 0){
            timeSlot = "";
            for (String s : timeSlots) {
                timeSlot =timeSlot +"&"+s;
            }
        }
        int isRestDefault = 1;
//        int isRest = JsonUtil.getIntegerParam(json, "isRest");
//        isRestDefault = isRest;
        String restSlot = JsonUtil.getStringParam(json, "restSlot");
        if(restSlot == null){
            isRestDefault = 0;
        }else {
            String restSlotStr = restSlot.toString();
            if(restSlotStr.equals("[]")){
                isRestDefault = 0;
            }
        }
        int txdk = JsonUtil.getIntegerParam(json, "txdk");
        String bcStewards = JsonUtil.getStringParam(json, "bcStewards");
        int turnOn = JsonUtil.getIntegerParam(json, "turnOn");
        TKqBc tKqBc = new TKqBc();
        tKqBc.setPkBcId(pkBcId);
        tKqBc.setDkNum(timeSlots.size());
        tKqBc.setTimeSlot(timeSlot.substring(1,timeSlot.length()));
        tKqBc.setIsRest(isRestDefault);
        tKqBc.setRestSlot(restSlot);
        tKqBc.setTxdk(txdk);
        tKqBc.setBcStewards(bcStewards);
        tKqBc.setTurnOn(turnOn);
        int i = this.tKqBcMapper.updateByPkId(tKqBc);
        if (i > 0) {
            stringBuffer.append("班次数据修改成功！");
            JSONObject listInfo = this.getListInfo();
            return new CommonResult(200, "success", stringBuffer.toString(), listInfo);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tKqBc
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TKqBc tKqBc) {
        //班次基本信息
        int i = this.tKqBcMapper.deleteById(tKqBc);
        StringBuffer stringBuffer = new StringBuffer();
        if(i > 0){
            stringBuffer.append("班次基本信息删除成功！");
        }else {
            return new CommonResult(444, "error", "默认班次无法删除！", null);
        }
        //删除group-bc信息
        int j = tKqBcMapper.deleteGroupBcByBcId(tKqBc.getPkBcId());
        stringBuffer.append("已清除班次相关考勤组设置，"+j+"个");
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
        TKqBc entity = new TKqBc();

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
        List<TKqBc> list = this.tKqBcMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TKqBc>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tKqBc 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TKqBc tKqBc) {
        //实体基本信息
        String pkId = tKqBc.getPkBcId();
        TKqBc entity = this.tKqBcMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        //所有员工
        List<TStaffInfo> infoList = tStaffInfoMapper.selectAllId_Name();
        resultJson.put("infoList", infoList);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TKqBc entity = new TKqBc();
        List<TKqBc> list = this.tKqBcMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TKqBc>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    
