package com.hjy.cloud.t_kq.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.t_apv.entity.TApvApproval;
import com.hjy.cloud.t_kq.dao.TKqBcMapper;
import com.hjy.cloud.t_kq.dao.TKqGroupMapper;
import com.hjy.cloud.t_kq.entity.ReGroupStaff;
import com.hjy.cloud.t_kq.entity.TKqBc;
import com.hjy.cloud.t_kq.entity.TKqGroup;
import com.hjy.cloud.t_kq.service.TKqGroupService;
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
    private TKqBcMapper tKqBcMapper;
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
        Date bxdkTime = JsonUtil.getDateParam(json, "yyyy-MM","bxdkTime");
        Date wxdkTime = JsonUtil.getDateParam(json, "yyyy-MM","wxdkTime");
        int kqWay = JsonUtil.getIntegerParam(json, "kqWay");
        int isWq = JsonUtil.getIntegerParam(json, "isWq");
        StringBuffer stringBuffer = new StringBuffer();
        //开始添加
        TKqGroup tKqGroup = new TKqGroup();
        tKqGroup.setPkGroupId(pkGroupId);
        tKqGroup.setTurnOn(1);
        int i = this.tKqGroupMapper.insertSelective(tKqGroup);
        if (i > 0) {
            stringBuffer.append("考勤组数据添加成功");
        } else {
            stringBuffer.append("考勤组数据添加失败");
        }
        //参与考勤人员
        List<ReGroupStaff> joinList = new ArrayList<>();
        JSONArray joinArray = json.getJSONArray("joins");
        if(joinArray == null){
            stringBuffer.append("未选择考勤人");
        }else{
            String joinStr = joinArray.toString();
            if(joinStr.equals("[]")){
                stringBuffer.append("未选择考勤人");
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
            stringBuffer.append("不添加免考勤人");
        }else{
            String freeStr = freeArray.toString();
            if(freeStr.equals("[]")){
                stringBuffer.append("不添加免考勤人");
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
        //批量添加分组
        int j = tKqGroupMapper.insertGroupStaffBatch(joinList);
        return new CommonResult(200, "success", "考勤组数据添加成功", null);
    }

    /**
     * 修改数据
     *
     * @param tKqGroup
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TKqGroup tKqGroup) {
        int i = this.tKqGroupMapper.updateByPkId(tKqGroup);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
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
        int i = this.tKqGroupMapper.deleteById(tKqGroup);
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
        String pkId = JsonUtil.getStringParam(json, "pk_id");
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
        TKqGroup entity = this.tKqGroupMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
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
    
