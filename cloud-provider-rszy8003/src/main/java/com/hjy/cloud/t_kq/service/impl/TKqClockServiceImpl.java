package com.hjy.cloud.t_kq.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.common.task.ObjectAsyncTask;
import com.hjy.cloud.t_kq.dao.TKqBcMapper;
import com.hjy.cloud.t_kq.dao.TKqClockMapper;
import com.hjy.cloud.t_kq.dao.TKqGroupMapper;
import com.hjy.cloud.t_kq.entity.*;
import com.hjy.cloud.t_kq.service.TKqClockService;
import com.hjy.cloud.t_outfit.entity.TOutfitWorkaddress;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.utils.DateUtil;
import com.hjy.cloud.utils.TokenUtil;
import com.hjy.cloud.utils.page.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.JsonUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

/**
 * (TKqClock)表服务实现类
 *
 * @author makejava
 * @since 2021-03-19 17:46:25
 */
@Service("tKqClockService")
public class TKqClockServiceImpl implements TKqClockService {

    @Resource
    private TKqClockMapper tKqClockMapper;
    @Resource
    private TKqGroupMapper tKqGroupMapper;
    @Resource
    private TKqBcMapper tKqBcMapper;
    /**
     * 添加前获取数据
     *
     * @return
     */
    @Override
    public CommonResult insertPage(HttpServletRequest request) throws ParseException {
        SysToken sysToken = ObjectAsyncTask.getSysToken(request);
        JSONObject jsonObject = new JSONObject();
        TKqClock selectTKqCloc = new TKqClock();
        TKqClock resultClock = new TKqClock();
        StringBuffer stringBuffer = new StringBuffer();
        int isDkr = 1;
        boolean bxdkBoolean = false;
        resultClock.setIsDkr(isDkr);
        Date nowDate = new Date();
        /**
         * 获取当前用户打卡相关信息
         * 一、当前用户考勤组信息
         */
        ReGroupStaff reGroupStaff = tKqClockMapper.selectGroupStaffByStaffId(sysToken.getFkUserId());
        if(reGroupStaff == null){
            isDkr = 0;
            resultClock.setIsDkr(isDkr);
            stringBuffer.append("该用户还未分配考勤组，请联系管理员！");
            jsonObject.put("clock", resultClock);
            return new CommonResult(201, "success", stringBuffer.toString(), jsonObject);
        }
        //判断是否需要考勤
        if(reGroupStaff.getIsKq()==0){
            //说明该员工无需考勤
            isDkr = 0;
            resultClock.setIsDkr(isDkr);
            stringBuffer.append("非必须考勤用户，可无需考勤！");
        }
        /**
         * 二、查询该员工所在考勤组的信息
         */
        TKqGroup tKqGroup = tKqGroupMapper.selectByPkId(reGroupStaff.getFkGroupId());
        if(tKqGroup == null){
            //说明该考勤组违规操作删除
            isDkr = 0;
            resultClock.setIsDkr(isDkr);
            stringBuffer.append("该考勤组已被违规删除，请联系管理员！");
            jsonObject.put("clock", resultClock);
            return new CommonResult(445, "error", stringBuffer.toString(), jsonObject);
        }
        jsonObject.put("group", tKqGroup);
        //判断是否为无需打卡日期
        Date wxdkTime = tKqGroup.getWxdkTime();
        if(wxdkTime != null){
            if(DateUtil.isSameDay(wxdkTime,nowDate)){
                //说明为同一天时间，则无需打卡
                isDkr = 0;
                resultClock.setIsDkr(isDkr);
                stringBuffer.append("今日为无需打卡日期！");
                jsonObject.put("clock", resultClock);
                return new CommonResult(202, "success", stringBuffer.toString(), jsonObject);
            }
        }
        if(tKqGroup.getBxdkTime() != null){
            if(DateUtil.isSameDay(tKqGroup.getBxdkTime(),nowDate)){
                //说明为同一天时间，则必须打卡
                isDkr = 1;
                bxdkBoolean = true;
                resultClock.setIsDkr(isDkr);
                stringBuffer.append("今日为必须打卡日期！");
            }
        }
        /**
         * 三、判断班制后进行不同的逻辑处理
         * 1、固定班制，从班次列表中选择
         * 2、排班制，
         * 3、自由工时，设置每天工时
         */
        //1 工作地信息
        TOutfitWorkaddress tOutfitWorkaddress = tKqClockMapper.selectWorkAddressByGroupId(tKqGroup.getPkGroupId());
        jsonObject.put("workAddress", tOutfitWorkaddress);
        //2 今日该考勤组的班次信息
        TKqBc tKqBc = null;
        ReGroupWorkingdays selectReGroupWorkingdays = new ReGroupWorkingdays();
        //displayName今日是周几
        String displayName = DateUtil.todayIs();
        selectReGroupWorkingdays.setFkGroupId(tKqGroup.getPkGroupId());
        selectReGroupWorkingdays.setWorkingDays(displayName);
        selectReGroupWorkingdays.setKqType(tKqGroup.getKqType());
        List<ReGroupWorkingdays> resultGroupWorkingdays = tKqClockMapper.selectGroupWorkingDaysByEntity(selectReGroupWorkingdays);
        if(tKqGroup.getKqType() == 1){
            /**
             * 1.说明为固定班次
             */
            if(resultGroupWorkingdays == null || resultGroupWorkingdays.size() == 0){
                //非必须考勤日期
                if(!bxdkBoolean){
                    //说明当前工作日未设置考勤，
                    isDkr = 0;
                    resultClock.setIsDkr(isDkr);
                    stringBuffer.append("今日非工作时间，无需考勤");
                    jsonObject.put("clock", resultClock);
                    return new CommonResult(204, "success", stringBuffer.toString(), jsonObject);
                }else {
                    //必须考勤日期，就使用默认的班次设置
                    tKqBc = tKqBcMapper.selectDefaultBc();
                }
            }else {
                tKqBc = tKqBcMapper.selectByPkId(resultGroupWorkingdays.get(0).getFkBcId());
                if(tKqBc == null){
                    //说明该班次被违规操作删除
                    isDkr = 0;
                    resultClock.setIsDkr(isDkr);
                    stringBuffer.append("该班次已被违规删除！请尽快联系管理员处理！");
                    jsonObject.put("clock", resultClock);
                    return new CommonResult(446, "error", stringBuffer.toString(), jsonObject);
                }
            }
            jsonObject.put("todayBc", tKqBc);
        }else if(tKqGroup.getKqType() == 2){
            //排班制

        }else {
            //自由工时,打卡时间任意


        }
        /**
         * 四、查询该考勤组其他相关信息，班制、工作地，班次。
         */


        /**
         * 五、判断今日是否有打卡记录，若无打卡记录，则此次打卡为上班，反之为下班打卡
         */
        //打卡类型，1代表上班打卡，2代表下班打卡
        int dkType = 1;
        selectTKqCloc.setFkStaffId(sysToken.getFkUserId());
        selectTKqCloc.setTodayDate(nowDate);
        List<TKqClock> kqClockList = tKqClockMapper.selectAllPage(selectTKqCloc);
        resultClock.setTodayDate(nowDate);
        if(kqClockList != null && kqClockList.size() >0){
            //说明为下班打卡
            dkType = 2;
            resultClock.setOffDutyTime(nowDate);
            //----------还要计算是否早退
        }else {
            //说明为上班打卡
            resultClock.setOnDutyTime(nowDate);
        }
        /**
         * 六、当前班次下的上下班时间计算，是否在上下班时间段内
         */
        Map<String,Object> timeSlotMap = new HashMap<>();
        String timeSlot = tKqBc.getTimeSlot();
        String[] timeSlots = timeSlot.split("&");
        if(timeSlots != null && timeSlots.length > 0){
            for (String s:timeSlots) {
                timeSlotMap = DateUtil.belongCalendar2(nowDate,s,dkType);
            }
        }
        //若不在上下班时间段，说明迟到卡，在判断是否有弹性打卡规则
        if((timeSlotMap.get("isTimeSlot")).equals("1")){
            if(dkType == 2){
                //说明为早退卡,还没到上班时间就已经打了下班卡了
                //根据打卡规则，判断是否为早退
//                resultClock.setZtMinute((String) timeSlotMap.get("cd_ztMinute"));
            }
        }else if((timeSlotMap.get("isTimeSlot")).equals("2")){
            if(dkType == 1){
                //说明为迟到卡
                resultClock.setCdMinute((String) timeSlotMap.get("cd_ztMinute"));
                //根据打卡规则，判断是否为迟到
            }else {
                //说明为早退卡
                resultClock.setZtMinute((String) timeSlotMap.get("cd_ztMinute"));
                //根据打卡规则，判断是否为早退
            }
        }else  if((timeSlotMap.get("isTimeSlot")).equals("3")){
            if(dkType == 1){
                //说明为迟到卡，今天一次都没有打卡
                resultClock.setCdMinute((String) timeSlotMap.get("cd_ztMinute"));
                //根据打卡规则，判断是否为迟到
            }else {
                //说明该员工还在努力的加班中
                resultClock.setZtMinute((String) timeSlotMap.get("cd_ztMinute"));
                //根据加班规则，计算加班
            }
        }
        jsonObject.put("isTimeSlot", timeSlotMap);
        jsonObject.put("clock", resultClock);
        return new CommonResult(200, "success", stringBuffer.toString(), jsonObject);
    }

    /**
     * 添加数据
     *
     * @param tKqClock
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TKqClock tKqClock) {
        int i = this.tKqClockMapper.insertSelective(tKqClock);
        if (i > 0) {
            return new CommonResult(200, "success", "添加数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tKqClock
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TKqClock tKqClock) {
        int i = this.tKqClockMapper.updateByPkId(tKqClock);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tKqClock
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TKqClock tKqClock) {
        int i = this.tKqClockMapper.deleteById(tKqClock);
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
        TKqClock entity = new TKqClock();

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
        List<TKqClock> list = this.tKqClockMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TKqClock>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tKqClock 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TKqClock tKqClock) {
        String pkId = tKqClock.getPkClockId();
        TKqClock entity = this.tKqClockMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TKqClock entity = new TKqClock();
        List<TKqClock> list = this.tKqClockMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TKqClock>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    
