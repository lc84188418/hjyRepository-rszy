package com.hjy.cloud.common.utils;

import com.hjy.cloud.t_kq.entity.ReGroupWorkingdays;

import java.util.LinkedList;
import java.util.List;

/**
 * @author liuchun
 * @Package com.hjy.cloud.common.utils
 * @date 2021/6/4 14:30
 * description:
 */
public class KqUtil {
    public static List<ReGroupWorkingdays> getGroupWorkingdays(){
        List<ReGroupWorkingdays> workingdaysList = new LinkedList<>();
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
}
