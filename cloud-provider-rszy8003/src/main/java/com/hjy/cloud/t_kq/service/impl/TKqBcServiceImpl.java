package com.hjy.cloud.t_kq.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.t_kq.dao.TKqBcMapper;
import com.hjy.cloud.t_kq.entity.ReGroupStaff;
import com.hjy.cloud.t_kq.entity.TKqBc;
import com.hjy.cloud.t_kq.service.TKqBcService;
import com.hjy.cloud.t_staff.dao.TStaffInfoMapper;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.page.PageUtil;
import org.apache.commons.lang.StringUtils;
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
                timeSlot =timeSlot +"+"+s;
            }
        }
        int isRestDefault = 1;
        int isRest = JsonUtil.getIntegerParam(json, "isRest");
        isRestDefault = isRest;
        String restSlot = JsonUtil.getStringParam(json, "restSlot");
        int txdk = JsonUtil.getIntegerParam(json, "txdk");
        String bcStewards = JsonUtil.getStringParam(json, "bcStewards");
        TKqBc tKqBc = new TKqBc();
        tKqBc.setPkBcId(IDUtils.getUUID());
        tKqBc.setDkNum(timeSlots.size());
        tKqBc.setTimeSlot(timeSlot.substring(1,timeSlot.length()));
        tKqBc.setIsRest(isRestDefault);
        tKqBc.setRestSlot(restSlot);
        tKqBc.setTxdk(txdk);
        tKqBc.setBcStewards(bcStewards);
        tKqBc.setTurnOn(1);
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
     * @param tKqBc
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TKqBc tKqBc) {
        int i = this.tKqBcMapper.updateByPkId(tKqBc);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
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
        int i = this.tKqBcMapper.deleteById(tKqBc);
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
        String pkId = tKqBc.getPkBcId();
        TKqBc entity = this.tKqBcMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
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
    
