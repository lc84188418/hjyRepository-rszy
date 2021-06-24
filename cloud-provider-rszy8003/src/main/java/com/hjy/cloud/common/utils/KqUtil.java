package com.hjy.cloud.common.utils;

import com.hjy.cloud.t_kq.entity.ReGroupWorkingdays;
import com.hjy.cloud.t_kq.entity.TKqBc;
import com.hjy.cloud.utils.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liuchun
 * @Package com.hjy.cloud.common.utils
 * @date 2021/6/4 14:30
 * description:
 */
public class KqUtil {
    public static List<ReGroupWorkingdays> getGroupWorkingdays(){
        List<ReGroupWorkingdays> workingdaysList = new ArrayList<>(7);
        ReGroupWorkingdays workingdays1 = new ReGroupWorkingdays();
        workingdays1.setWorkingDays("星期一");
        workingdaysList.add(workingdays1);
        ReGroupWorkingdays workingdays2 = new ReGroupWorkingdays();
        workingdays2.setWorkingDays("星期二");
        workingdaysList.add(workingdays2);
        ReGroupWorkingdays workingdays3 = new ReGroupWorkingdays();
        workingdays3.setWorkingDays("星期三");
        workingdaysList.add(workingdays3);
        ReGroupWorkingdays workingdays4 = new ReGroupWorkingdays();
        workingdays4.setWorkingDays("星期四");
        workingdaysList.add(workingdays4);
        ReGroupWorkingdays workingdays5 = new ReGroupWorkingdays();
        workingdays5.setWorkingDays("星期五");
        workingdaysList.add(workingdays5);
        ReGroupWorkingdays workingdays6 = new ReGroupWorkingdays();
        workingdays6.setWorkingDays("星期六");
        workingdaysList.add(workingdays6);
        ReGroupWorkingdays workingdays7 = new ReGroupWorkingdays();
        workingdays7.setWorkingDays("星期日");
        workingdaysList.add(workingdays7);
        return workingdaysList;
    }

    public static int getWorkHours(Date onDutyDate,Date nowDate) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date onDutyTime = df.parse(df.format(onDutyDate));
        Date nowTime = df.parse(df.format(nowDate));
        //两时间点（上班打卡时间与现在时间）相差分钟数
        int total = (int) DateUtil.getMinute(onDutyTime,nowTime);
        //计算工时，按分钟
        int workMinute = 0;
        //减去休息时间,默认12-14点，2小时120分钟
        int onDutyCalendar = DateUtil.belongCalendar2(onDutyDate,"12:00-14:00");
        int offDutyCalendar = DateUtil.belongCalendar2(nowDate,"12:00-14:00");
        if(onDutyCalendar == 1){
            if(offDutyCalendar == 1){
                workMinute = total;
            }else if(offDutyCalendar == 2){
                //重新计算工时
                Date restTime = df.parse("12:00");
                int total2 = (int)DateUtil.getMinute(onDutyTime,restTime);
                workMinute = total2;
            }else {
                workMinute = total-120;
            }
        }else if(onDutyCalendar == 2){
             if(offDutyCalendar == 3){
                 //重新计算工时
                 Date restTime = df.parse("14:00");
                 int total2 = (int)DateUtil.getMinute(restTime,nowTime);
                 workMinute = total2;
            }
        }else {
            if(offDutyCalendar == 3){
                workMinute = total;
            }
        }
        return workMinute;
    }

    public static int calculationWorkHours(TKqBc tKqBc) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        /**
         * 工作时间
         */
        //先默认timeSlot只有一个时间段，如09:00-17:30
        String timeSlot = tKqBc.getTimeSlot();
        //09:00-17:30
        String[] timeSlots = timeSlot.split("-");
        //时间段开始的时刻
        Date beginTime = df.parse(timeSlots[0]);
        Date endTime = df.parse(timeSlots[1]);
        int total = (int)DateUtil.getMinute(beginTime,endTime);
        /**
         * 休息时间
         */
        String restSlot = tKqBc.getRestSlot();
        if(StringUtils.isEmpty(restSlot)){
            restSlot = "12:00-14:00";
        }
        //12:00-14:00
        String[] restSlots = restSlot.split("-");
        //时间段开始的时刻
        Date restbeginTime = df.parse(restSlots[0]);
        Date restendTime = df.parse(restSlots[1]);
        int resttotal = (int)DateUtil.getMinute(restbeginTime,restendTime);
        return total - resttotal;
    }

    public static Date [] getSlot(TKqBc tKqBc) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        /**
         * 工作时间
         */
        //先默认timeSlot只有一个时间段，如09:00-17:30
        String timeSlot = tKqBc.getTimeSlot();
        //09:00-17:30
        String[] timeSlots = timeSlot.split("-");
        //时间段开始的时刻
        Date beginTime = df.parse(timeSlots[0]);
        Date endTime = df.parse(timeSlots[1]);
        Date [] dates = new Date[2];
        dates[0] = beginTime;
        dates[1] = endTime;
        return dates;
    }
}
