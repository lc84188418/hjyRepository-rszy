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
import com.hjy.cloud.t_staff.dao.TStaffInfoMapper;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @Resource
    private TStaffInfoMapper tStaffInfoMapper;
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
        resultClock.setIsCd(0);
        resultClock.setIsZt(0);
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
            stringBuffer.append("非必须考勤用户，无需考勤！");
            jsonObject.put("clock", resultClock);
            return new CommonResult(202, "success", stringBuffer.toString(), jsonObject);
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
        resultClock.setFkGroupId(tKqGroup.getPkGroupId());
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
                return new CommonResult(203, "success", stringBuffer.toString(), jsonObject);
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
             * 1.tKqGroup.getKqType() == 1说明为固定班次
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
                }else {
                    jsonObject.put("tKqBc", tKqBc);
                }
            }
        }else if(tKqGroup.getKqType() == 2){
            /**
             * 2.说明为排班制
             */

        }else {
            /**
             * 2.说明为自由工时,打卡时间任意，无班次信息
             */
            if(resultGroupWorkingdays == null || resultGroupWorkingdays.size() == 0){
                //非必须考勤日期
                if(!bxdkBoolean){
                    //说明当前工作日未设置考勤，
                    isDkr = 0;
                    resultClock.setIsDkr(isDkr);
                    stringBuffer.append("今日非工作时间，无需考勤");
                    jsonObject.put("clock", resultClock);
                    return new CommonResult(205, "success", stringBuffer.toString(), jsonObject);
                }else {
                    stringBuffer.append("自由工时");
                }
            }else {
                stringBuffer.append("自由工时");
            }
        }
        /**
         * 四、判断今日是否有打卡记录，若无打卡记录，则此次打卡为上班，反之为下班打卡
         */
        //打卡类型，1代表上班打卡，2代表下班打卡
        int dkType = 1;
        selectTKqCloc.setFkStaffId(sysToken.getFkUserId());
        selectTKqCloc.setTodayDate(nowDate);
        List<TKqClock> kqClockList = tKqClockMapper.selectAllPage(selectTKqCloc);
        resultClock.setTodayDate(nowDate);
        if(kqClockList != null && kqClockList.size() >0){
            resultClock = kqClockList.get(0);
            //有打卡记录，说明为下班打卡
            dkType = 2;
            resultClock.setOffDutyTime(nowDate);
            //----------还要计算是否早退
        }else {
            //无打卡记录，说明为上班打卡
            resultClock.setOnDutyTime(nowDate);
        }
        /**
         * 五、当前班次下的上下班时间计算，是否在上下班时间段内
         */
        if(tKqBc != null){
            //班次信息不为空，说明为固定班次或排班制，非自由工时
//            Map<String,Object> timeSlotMap = new HashMap<>();
            int isTimeSlot = 0;
            String timeSlot = tKqBc.getTimeSlot();
            String[] timeSlots = timeSlot.split("&");
            if(timeSlots != null && timeSlots.length > 0){
                for (String s:timeSlots) {
                    isTimeSlot = DateUtil.belongCalendar2(nowDate,s);
                }
            }
            /**
             * =1代表，当前时间在上下班时间段之前
             * =2代表，当前时间在上下班时间段之中
             * =3代表，当前时间在上下班时间段之后
             */
            if(isTimeSlot == 1){
                if(dkType == 2){
                    //说明为早退卡,还没到上班时间就已经打了下班卡了
                    //根据打卡规则，判断是否为早退
                    //看是否有打卡时间间隔限制
                    if(tKqGroup.getDkJgsj() == null){
                        stringBuffer.append("还没到上班时间，你就要溜了吗？");
                        isDkr = 1;
                        resultClock.setIsDkr(isDkr);
                        resultClock.setIsZt(1);
                    }else {
                        stringBuffer.append("打卡时间限制内无法进行打卡！");
                        isDkr = 0;
                        resultClock.setIsDkr(isDkr);
                        jsonObject.put("clock", resultClock);
                        return new CommonResult(206, "success", stringBuffer.toString(), jsonObject);
                    }
                }
            }else if(isTimeSlot == 2){
                if(dkType == 1){
                    //说明为迟到卡
                    stringBuffer.append("问题不大，你迟到了哦");
                    resultClock.setIsCd(1);
//                    String minute = DateUtil.getminute(beginTime,nowTime);
//                    resultClock.setCdMinute((String) timeSlotMap.get("cd_ztMinute"));
                    //根据打卡规则，判断是否为迟到
                }else {
                    //说明为早退卡
                    stringBuffer.append("还未到下班时间，确定要溜吗？");
                    resultClock.setIsZt(1);

//                    resultClock.setZtMinute((String) timeSlotMap.get("cd_ztMinute"));
                    //根据打卡规则，判断是否为早退
                }
            }else  if(isTimeSlot == 3){
                if(dkType == 1){
                    //说明为迟到卡，今天一次都没有打卡
                    stringBuffer.append("今日未进行上班打卡，记着联系管理员哦！");
                    resultClock.setIsCd(1);

//                    resultClock.setCdMinute((String) timeSlotMap.get("cd_ztMinute"));
                    //根据打卡规则，判断是否为迟到
                }else {
                    //说明该员工还在努力的加班中
                    stringBuffer.append("加班辛苦了，要不再坚持一下？");
//                    resultClock.setZtMinute((String) timeSlotMap.get("cd_ztMinute"));
                    //根据加班规则，计算加班
                }
            }
            jsonObject.put("isTimeSlot", isTimeSlot);
        }

        jsonObject.put("clock", resultClock);
        return new CommonResult(200, "success", stringBuffer.toString(), jsonObject);
    }

    /**
     * 添加数据
     *
     * @param tKqClockParam
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TKqClock tKqClockParam,HttpServletRequest request) throws ParseException {
        SysToken sysToken = ObjectAsyncTask.getSysToken(request);
        if(sysToken == null){
            return new CommonResult(445, "error", "token不存在或已失效，请重新登录", null);
        }
        JSONObject resultJson = new JSONObject();
        Date nowDate = new Date();
        TStaffInfo info = tStaffInfoMapper.selectByPkId2(sysToken.getFkUserId());
        StringBuffer stringBuffer = new StringBuffer();
        if(!StringUtils.isEmpty(tKqClockParam.getOnClockAddress())){
            //说明上班打卡，新增记录,
            //前端需传入onCloudAddress,onIsWq,isDkr,fkGroupId
            tKqClockParam.setPkClockId(IDUtils.getUUID());
            tKqClockParam.setFkStaffId(sysToken.getFkUserId());
            tKqClockParam.setStaffName(info.getStaffName());
            tKqClockParam.setTodayDate(nowDate);
            tKqClockParam.setOnDutyTime(nowDate);
            tKqClockParam.setOnClockIp(sysToken.getIp());
            //isCd,cdMinute后端在计算
            tKqClockParam = this.calculationCdZt_minute(tKqClockParam,"isCd");
            tKqClockParam.setFkDeptId(info.getFkDeptId());
            tKqClockParam.setDeptName(info.getDeptName());
            tKqClockParam.setFkPositionId(info.getFkPositionId());
            tKqClockParam.setPositionName(info.getPositionName());
            int i = tKqClockMapper.insertSelective(tKqClockParam);
            if (i > 0) {
                stringBuffer.append("上班打卡成功！");
                resultJson.put("clock",tKqClockParam);
            }
        }
        if(!StringUtils.isEmpty(tKqClockParam.getOffClockAddress())){
            //说明下班打卡，修改记录,
            TKqClock selectClock = new TKqClock();
            selectClock.setFkStaffId(sysToken.getFkUserId());
            selectClock.setTodayDate(nowDate);
            List<TKqClock> kqClockList = tKqClockMapper.selectAllPage(selectClock);
            if(kqClockList != null && kqClockList.size() > 0){
                selectClock = kqClockList.get(0);
                if(selectClock.getOffDutyTime() != null){
                    stringBuffer.append("更新打卡");
                }else {
                    stringBuffer.append("下班打卡");
                }
                //前端需传入offCloudAddress，offIsWq
                selectClock.setOffDutyTime(nowDate);
                selectClock.setOffClockAddress(tKqClockParam.getOffClockAddress());
                selectClock.setOffClockIp(sysToken.getIp());
                selectClock.setOffIsWq(tKqClockParam.getOffIsWq());
                selectClock = this.calculationCdZt_minute(selectClock,"isZt");
                int i = tKqClockMapper.updateByPkId(selectClock);
                if (i > 0) {
                    stringBuffer.append("成功！");
                    resultJson.put("clock",selectClock);
                }
            }else {
                stringBuffer.append("下班打卡异常！请联系技术人员！");
            }
        }
        return new CommonResult(200, "success", stringBuffer.toString(), resultJson);
    }

    private TKqClock calculationCdZt_minute(TKqClock tKqClock,String cdOrZt) throws ParseException {
        TKqGroup tKqGroup = tKqGroupMapper.selectByPkId(tKqClock.getFkGroupId());
        tKqClock.setGroupName(tKqGroup.getGroupName());
        //2 今日该考勤组的班次信息
        TKqBc tKqBc = null;
        ReGroupWorkingdays selectReGroupWorkingdays = new ReGroupWorkingdays();
        Date nowDate = new Date();
        // 设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date nowTime = df.parse(df.format(nowDate));
        //displayName今日是周几
        String displayName = DateUtil.todayIs();
        selectReGroupWorkingdays.setFkGroupId(tKqGroup.getPkGroupId());
        selectReGroupWorkingdays.setWorkingDays(displayName);
        selectReGroupWorkingdays.setKqType(tKqGroup.getKqType());
        List<ReGroupWorkingdays> resultGroupWorkingdays = tKqClockMapper.selectGroupWorkingDaysByEntity(selectReGroupWorkingdays);
        if(tKqGroup.getKqType() == 1){
            /**
             * 1.tKqGroup.getKqType() == 1说明为固定班次
             */
            if(resultGroupWorkingdays == null || resultGroupWorkingdays.size() == 0){
                if(tKqClock.getIsDkr() == 1){
                    //代表今日为必须考勤日期，就使用默认的班次设置
                    tKqBc = tKqBcMapper.selectDefaultBc();
                }
            }else {
                tKqBc = tKqBcMapper.selectByPkId(resultGroupWorkingdays.get(0).getFkBcId());
            }
            /**
             * 开始计算是否迟到或早退
             */
            int isCdOrZt = DateUtil.belongCalendar2(nowDate,tKqBc.getTimeSlot());
            //09:00-17:30
            String[] timeSlots = tKqBc.getTimeSlot().split("-");
            if(isCdOrZt == 1){
                if("isZt".equals(cdOrZt)){
                    //说明是下班，已早退，计算早退时间
                    tKqClock.setIsZt(1);
                    Date endTime = df.parse(timeSlots[1]);
                    long total = DateUtil.getMinute(nowTime,endTime);
                    tKqClock.setZtMinute(String.valueOf(total));
                    //计算工时
                    double workHours = this.calculationWorkHours(nowTime,tKqBc);
                    tKqClock.setWorkingHours(workHours);
                }else {
                    //说明是上班班
                    tKqClock.setIsCd(0);
                }
            }else if(isCdOrZt == 2) {
                if("isCd".equals(cdOrZt)){
                    //说明是上班，已迟到，计算迟到时间
                    tKqClock.setIsCd(1);
                    Date beginTime = df.parse(timeSlots[0]);
                    long total = DateUtil.getMinute(beginTime,nowTime);
                    tKqClock.setCdMinute(String.valueOf(total));
                    String cdDesc = DateUtil.minuteDesc((int) total);
                    tKqClock.setCdDesc("迟到"+cdDesc);
                }else {
                    //说明是下班，已早退，计算早退时间
                    tKqClock.setIsZt(1);
                    Date endTime = df.parse(timeSlots[1]);
                    long total = DateUtil.getMinute(nowTime,endTime);
                    tKqClock.setZtMinute(String.valueOf(total));
                    //计算工时
                    double workHours = this.calculationWorkHours(nowTime,tKqBc);
                    tKqClock.setWorkingHours(workHours);
                }
            }else {
                if("isCd".equals(cdOrZt)){
                    //说明是上班，已迟到，计算迟到时间
                    tKqClock.setIsCd(1);
                    Date beginTime = df.parse(timeSlots[0]);
                    long total = DateUtil.getMinute(beginTime,nowTime);
                    tKqClock.setCdMinute(String.valueOf(total));
                    String cdDesc = DateUtil.minuteDesc((int) total);
                    tKqClock.setCdDesc("迟到"+cdDesc);
                }else {
                    tKqClock.setIsZt(0);
                    //计算工时
                    double workHours = this.calculationWorkHours(nowTime,tKqBc);
                    tKqClock.setWorkingHours(workHours);
                }
            }
        }else if(tKqGroup.getKqType() == 2){
            /**
             * 2.说明为排班制
             */
        }else {
            /**
             * 2.说明为自由工时,打卡时间任意，无班次信息
             */
            String typeSet = tKqGroup.getTypeSet();
            if("isCd".equals(cdOrZt)){
                tKqClock.setIsCd(0);
            }else {
                //说明是下班,计算工时，判断是否早退
                Date onDutyDate = tKqClock.getOnDutyTime();
                Date onDutyTime = df.parse(df.format(onDutyDate));
                int total = (int)DateUtil.getMinute(onDutyTime,nowTime);
                //减去休息时间,默认12-14点，2小时120分钟
                int isRestInt = DateUtil.belongCalendar2(nowDate,"12:00-14:00");
                //计算工时
                int workHours = 0;
                if(isRestInt == 1){
                    workHours = total;
                }else if(isRestInt == 2){
                    //重新计算工时
                    Date restTime = df.parse("12:00");
                    int total2 = (int)DateUtil.getMinute(onDutyTime,restTime);
                    workHours = total2;
                }else {
                    workHours = total-120;
                }
                tKqClock.setWorkingHours(workHours);
                long workhoursDefault = DateUtil.getLongMinute(typeSet);
                if(workHours < workhoursDefault){
                    tKqClock.setIsZt(1);
                    tKqClock.setZtMinute(String.valueOf(workhoursDefault-workHours));
                    String ztDesc = DateUtil.minuteDesc((int) (workhoursDefault-workHours));
                    tKqClock.setZtDesc("早退"+ztDesc);
                }
            }
        }
        return tKqClock;
    }

    /**
     * 计算工时
     * @param nowDate
     * @param tKqBc
     * @return
     */
    private double calculationWorkHours(Date nowDate, TKqBc tKqBc) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        //先默认timeSlot只有一个时间段，如09:00-17:30
        //从上班时间开始计算
        String timeSlot = tKqBc.getTimeSlot();
        //09:00-17:30
        String[] timeSlots = timeSlot.split("-");
        //时间段开始的时刻
        Date beginTime = df.parse(timeSlots[0]);
        //时间段结束的时刻
        Date endTime = df.parse(df.format(nowDate));
        long minute = DateUtil.getMinute(beginTime,endTime);
        if(tKqBc.getIsRest() == 1){
            String restSlot = tKqBc.getRestSlot();
            String[] restSlots = restSlot.split("-");
            //时间段开始的时刻
            Date beginTime2 = df.parse(restSlots[0]);
            //时间段结束的时刻
            Date endTime2 = df.parse(restSlots[1]);
            long restminute = DateUtil.getMinute(beginTime2,endTime2);
            minute = minute-restminute;
        }
        return minute;
    }

    /**
     * 修改数据
     *
     * @param tKqClock
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TKqClock tKqClock) throws ParseException {
        //1.是否迟到
//        tKqClockParam = this.calculationCdZt_minute(tKqClock,"isCd");
        int i = this.tKqClockMapper.updateByPkId(tKqClock);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
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
    /**
     * 个人打卡统计
     *
     * @param paramStatistics
     * @return
     */
    @Override
    public CommonResult statisticsUser(ParamStatistics paramStatistics,HttpServletRequest request) throws ParseException {
        SysToken sysToken = ObjectAsyncTask.getSysToken(request);
        //默认查询条件按周次
        String weekOrMonth = "周";
        if(StringUtils.isEmpty(paramStatistics.getWeekOrMonth())){
            paramStatistics.setWeekOrMonth(weekOrMonth);
        }
        paramStatistics.setFkStaffId(sysToken.getFkUserId());
        List<TKqClock> list = new ArrayList<>();
        if("月".equals(paramStatistics.getWeekOrMonth())){
            paramStatistics.setIsWorkingHours("notnull");
            list = this.tKqClockMapper.selectAllByMonth(paramStatistics);
        }else if("周".equals(paramStatistics.getWeekOrMonth())){
            String weekSlot = paramStatistics.getWeekDate();
            if(StringUtils.isEmpty(weekSlot)){
                //默认为当前时间周
                weekSlot = DateUtil.getWeekSlot(0,null);
            }
            String[] weekSlots = weekSlot.split("-");
            paramStatistics.setStartDate(weekSlots[0]);
            paramStatistics.setEndDate(weekSlots[1]);
            list = this.tKqClockMapper.statisticsUser2(paramStatistics);
        }
        int dataSize = list.size();
        int cdNum = 0;
        int cdMinute = 0;
        int ztNum = 0;
        int ztMinute = 0;
        int jbNum = 0;
        int jbMinute = 0;
        int wqNum = 0;
        if(list != null && dataSize > 0){
            StatisticsUser returnEntity= new StatisticsUser();
            TKqClock firstClock = list.get(0);
            returnEntity.setFkStaffId(firstClock.getFkStaffId());
            returnEntity.setStaffName(firstClock.getStaffName());
            returnEntity.setJobId(firstClock.getJobId());
            returnEntity.setFkDeptId(firstClock.getFkDeptId());
            returnEntity.setDeptName(firstClock.getDeptName());
            returnEntity.setFkGroupId(firstClock.getFkGroupId());
            returnEntity.setGroupName(firstClock.getGroupName());
            returnEntity.setFkPositionId(firstClock.getFkPositionId());
            returnEntity.setPositionName(firstClock.getPositionName());
            //
            double workingHourTotal = 0;
            Iterator<TKqClock> clockIterator = list.iterator();
            while (clockIterator.hasNext()){
                TKqClock obj = clockIterator.next();
                workingHourTotal += obj.getWorkingHours();
                if(obj.getIsCd() != null && obj.getIsCd() == 1){
                    cdNum++;
                    cdMinute += Integer.valueOf(obj.getCdMinute());
                }
                if(obj.getIsZt() != null && obj.getIsZt() == 1){
                    ztNum++;
                    ztMinute += Integer.valueOf(obj.getZtMinute());
                }
                if(obj.getIsJb() != null && obj.getIsJb() == 1){
                    jbNum++;
                    jbMinute += Integer.valueOf(obj.getJbMinute());
                }
                //一天只算一次外勤
                if(obj.getOnIsWq() == 1 ||obj.getOffIsWq() == 1){
                    wqNum++;
                }
            }
            double workHourAvg = workingHourTotal / dataSize;
            returnEntity.setWorkingHours(workHourAvg);
            returnEntity.setOnDutyNum(dataSize);
            returnEntity.setCdDesc(cdNum+"次 共"+cdMinute+"分钟");
            returnEntity.setZtDesc(ztNum+"次 共"+ztMinute+"分钟");
            returnEntity.setJbDesc(jbNum+"次 共"+jbMinute+"分钟");
            /**
             * 未计算的
             * 缺卡、旷工、出勤班次、休息天数
             */
            returnEntity.setFieldNum(wqNum);
            JSONObject resultJson = new JSONObject();
            resultJson.put("statistics", returnEntity);
            return new CommonResult(200, "success", "获取数据成功", resultJson);
        }else {
            return new CommonResult(201, "success", "当前条件无数据!", null);
        }
    }
    /**
     * 个人打卡每日记录统计
     * @return 所有数据
     */
    @Override
    public CommonResult userStatisticsEveryDay(ParamStatistics param, HttpServletRequest request) {
        SysToken sysToken = ObjectAsyncTask.getSysToken(request);
        JSONObject resultJson = new JSONObject();
        param.setFkStaffId(sysToken.getFkUserId());
        param.setIsWorkingHours(null);
        //默认先查询当月的数据
        Date date = new Date();
        //默认今日打卡信息
        TKqClock today = null;
        if(param.getTodayDate() == null){
            param.setTodayDate(DateUtil.getDateFormat(date,"yyyy-MM-dd"));
            String monthDate = "";
            if(StringUtils.isEmpty(param.getMonthDate())){
                monthDate = DateUtil.getDateFormat(date,"yyyy-MM");
                param.setMonthDate(monthDate);
            }
            List<TKqClock> monthList = tKqClockMapper.selectAllByMonth(param);
            resultJson.put("statistics", monthList);
        }else {
            //只查询某天的数据
            today = tKqClockMapper.selectOnDayByTodayDate(param);

        }
        today = tKqClockMapper.selectOnDayByTodayDate(param);
        resultJson.put("today", today);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    @Override
    public CommonResult getWeekSlot(ParamStatistics param) throws ParseException {
        //周时间段
        String weekDate = param.getWeekDate();
        Integer lastOrNext = param.getLastOrNext();
        String string = DateUtil.getWeekSlot(lastOrNext,weekDate);
        return new CommonResult(200, "success", "获取数据成功", string);
    }

    @Override
    public CommonResult<PageResult<TKqClock>> adminList(String param) throws ParseException {
        JSONObject json = JSON.parseObject(param);
        //分页参数
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        //查询条件
        TKqClock tKqClock = new TKqClock();
        String staffName = JsonUtil.getStringParam(json, "staffName");
        String todayDate = JsonUtil.getStringParam(json, "todayDate");
        String deptName = JsonUtil.getStringParam(json, "deptName");
        String groupName = JsonUtil.getStringParam(json, "groupName");
        tKqClock.setStaffName(staffName);
        tKqClock.setTodayDate(DateUtil.formatTime(todayDate,"yyyy.MM.dd"));
        tKqClock.setDeptName(deptName);
        tKqClock.setGroupName(groupName);
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
        List<TKqClock> list = this.tKqClockMapper.selectAllPage(tKqClock);
        PageResult<TKqClock> result = PageUtil.getPageResult(new PageInfo<TKqClock>(list));
        return new CommonResult(200, "success", "获取数据成功", result);
    }
}
    
