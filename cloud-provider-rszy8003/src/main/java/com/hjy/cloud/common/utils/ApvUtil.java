package com.hjy.cloud.common.utils;

import com.hjy.cloud.t_apv.entity.TApvApproval;

import java.util.LinkedList;
import java.util.List;

/**
 * @author liuchun
 * @Package com.hjy.cloud.common.utils
 * @date 2021/6/4 15:06
 * description:
 */
public class ApvUtil {
    public static List<TApvApproval> getApprovalProgress(){
        List<TApvApproval> apvApprovals = new LinkedList<>();
        TApvApproval obj1 = new TApvApproval();
        obj1.setApvStation("zdy");
        obj1.setStationName("自定义");
        apvApprovals.add(obj1);
        TApvApproval obj2 = new TApvApproval();
        obj2.setApvStation("deptLeader");
        obj2.setStationName("部门主管");
        apvApprovals.add(obj2);
        TApvApproval obj3 = new TApvApproval();
        obj3.setApvStation("financeLeader");
        obj3.setStationName("财务主管");
        apvApprovals.add(obj3);
        TApvApproval obj4 = new TApvApproval();
        obj4.setApvStation("humanResources");
        obj4.setStationName("人力资源主管");
        apvApprovals.add(obj4);
        TApvApproval obj5 = new TApvApproval();
        obj5.setApvStation("generalManager");
        obj5.setStationName("总经理");
        apvApprovals.add(obj5);
        return apvApprovals;
    }
}
