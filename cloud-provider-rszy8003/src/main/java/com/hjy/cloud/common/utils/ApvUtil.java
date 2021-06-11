package com.hjy.cloud.common.utils;

import com.hjy.cloud.t_apv.entity.TApvApproval;
import com.hjy.cloud.t_apv.result.SourceDetailResult;
import com.hjy.cloud.t_staff.entity.TStaffEntry;
import com.hjy.cloud.t_staff.entity.TStaffQuit;
import com.hjy.cloud.t_staff.entity.TStaffReassign;
import com.hjy.cloud.t_staff.entity.TStaffZz;

import java.util.LinkedList;
import java.util.List;

/**
 * @author liuchun
 * @Package com.hjy.cloud.common.utils
 * @date 2021/6/4 15:06
 * description:
 */
public class ApvUtil {
    /**
     * 添加5个默认的审批岗位
     * @return
     */
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

    /**
     *
     * @param apvList 需要去重的审批流
     * @return
     */
    public static List<TApvApproval> removeDuplicate(List<TApvApproval> apvList){
        for (int m = 0; m < apvList.size()-1; m++) {
            for (int n = apvList.size()-1; n > m; n--) {
                if(apvList.get(n).getApprovalPeople()!= null && apvList.get(m).getApprovalPeople()!= null){
                    if (apvList.get(n).getApprovalPeople().equals(apvList.get(m).getApprovalPeople())) {
                        apvList.remove(n);
                    }
                }
            }
        }
        return apvList;
    }

    public static SourceDetailResult handleData(Object object) {
        SourceDetailResult result = new SourceDetailResult();
        if(object == null){
            return result;
        }
        if(object.getClass().equals(TStaffReassign.class)){
            //调动
            TStaffReassign obj = (TStaffReassign) object;
            result.setSourceId(obj.getFkStaffId());
            result.setFkStaffId(obj.getFkStaffId());
            result.setStaffName(obj.getStaffName());
            result.setStaffDept(obj.getOldDeptName());
            result.setStaffPosition(obj.getOldPositionName());
            result.setApvId(obj.getFirstApvrecordId());
            //
            result.setReassignTime(obj.getReassignTime());
            result.setReassignType(obj.getReassignType());
            result.setReassignReason(obj.getReassignReason());
            result.setOldDeptName(obj.getOldDeptName());
            result.setReassignDeptName(obj.getReassignDeptName());
            result.setOldPositionName(obj.getOldPositionName());
            result.setReassignPositionName(obj.getReassignPositionName());
            result.setOldWorkAddressName(obj.getOldWorkAddressName());
            result.setReassignWorkAddressName(obj.getReassignWorkAddressName());
        }else if(object.getClass().equals(TStaffQuit.class)){
            //离职
            TStaffQuit obj = (TStaffQuit) object;
            result.setSourceId(obj.getFkStaffId());
            result.setFkStaffId(obj.getFkStaffId());
            result.setStaffName(obj.getStaffName());
            result.setStaffDept(obj.getDeptName());
            result.setStaffPosition(obj.getPositionName());
            result.setApvId(obj.getApvId());
            //
            result.setQuitTime(obj.getQuitTime());
            result.setQuitType(obj.getQuitType());
            result.setQuitReason(obj.getQuitReason());
        }else if(object.getClass().equals(TStaffEntry.class)){
            //入职
            TStaffEntry obj = (TStaffEntry) object;
            result.setSourceId(obj.getPkEntryId());
            result.setFkStaffId(obj.getPkEntryId());
            result.setStaffName(obj.getStaffName());
            result.setStaffDept(obj.getStaffDept());
            result.setStaffPosition(obj.getStaffPosition());
            result.setApvId(obj.getApvId());
            //
            result.setEntryTime(obj.getEntryTime());
        }else if(object.getClass().equals(TStaffZz.class)){
            //转正
            TStaffZz obj = (TStaffZz) object;
            result.setSourceId(obj.getFkStaffId());
            result.setFkStaffId(obj.getFkStaffId());
            result.setStaffName(obj.getStaffName());
            result.setStaffDept(obj.getDeptName());
            result.setStaffPosition(obj.getPositionName());
            result.setApvId(obj.getFirstApvrecordId());
            //
            result.setZzTime(obj.getZzTime());
            result.setEntryTime(obj.getEntryTime());
        }
        return result;
    }
}
