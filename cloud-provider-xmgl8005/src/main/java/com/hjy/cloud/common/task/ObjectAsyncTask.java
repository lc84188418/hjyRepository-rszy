package com.hjy.cloud.common.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hjy.cloud.common.entity.DApvRecord;
import com.hjy.cloud.t_apv.entity.DCcRecord;
import com.hjy.cloud.t_apv.entity.TApvApproval;
import com.hjy.cloud.t_apv.entity.TApvApvtype;
import com.hjy.cloud.t_apv.service.DCcRecordService;
import com.hjy.cloud.t_apv.service.TApvApprovalService;
import com.hjy.cloud.t_apv.service.TApvApvtypeService;
import com.hjy.cloud.t_outfit.service.TOutfitDeptService;
import com.hjy.cloud.t_staff.entity.TStaffEntry;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.t_staff.entity.TStaffReassign;
import com.hjy.cloud.t_staff.service.TStaffEntryService;
import com.hjy.cloud.t_staff.service.TStaffInfoService;
import com.hjy.cloud.t_staff.service.TStaffReassignService;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.t_system.entity.ReDeptUser;
import com.hjy.cloud.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 异步任务
 */
@Component
@Async
public class ObjectAsyncTask {
    @Autowired
    private TOutfitDeptService tOutfitDeptService;
    @Autowired
    private TApvApprovalService tApvApprovalService;
    @Autowired
    private TApvApvtypeService tApvApvtypeService;
    @Autowired
    private TStaffInfoService tStaffInfoService;
    @Autowired
    private TStaffEntryService tStaffEntryService;
    @Autowired
    private TStaffReassignService tStaffReassignService;
    @Resource
    private DCcRecordService dCcRecordService;

    private static ObjectAsyncTask ntClient;

    /**
     * 向用户-角色关联表中添加一条用户角色信息
     * @param pkUserId 用户ID
     * @param roleId 角色ID
     */
//    public static void addUserRoleByUserRole(String pkUserId, String roleId) {
//        ReUserRole userRole = new ReUserRole();
//        userRole.setPk_userRole_id(IDUtils.getUUID());
//        userRole.setFk_user_id(pkUserId);
//        userRole.setFk_role_id(roleId);
//        ntClient.tSysUserService.addUserRoleByUserRole(userRole);
//    }

    /**
     * 删除角色的所有权限
     * @param fk_role_id 角色ID
     */
//    public static void deleteRolePermsByRoleId(String fk_role_id) {
//        ntClient.tSysRoleService.deleteRolePermsByRoleId(fk_role_id);
//    }
//    //从请求中获取token
//    public static SysToken getSysToken(HttpServletRequest request) {
//        String tokenId = TokenUtil.getRequestToken(request);
//        if(StringUtils.isEmpty(tokenId)){
//            return null;
//        }else {
//            return ntClient.tSysTokenService.selectPkId(tokenId);
//        }
//    }
    /**
     * 向部门-用户关联表中添加一条部门-用户信息
     * @param pkUserId 用户ID
     * @param deptId 部门ID
     */
    public static void addDeptUserByDeptUser(String pkUserId, String deptId) {
        ReDeptUser deptUser = new ReDeptUser();
        deptUser.setPk_deptUser_id(IDUtils.getUUID());
        deptUser.setFk_user_id(pkUserId);
        deptUser.setFk_dept_id(deptId);
        ntClient.tOutfitDeptService.addDeptUserByDeptUser(deptUser);
    }
    /**
     * 将末尾的审批流程isEnding改为0
     * @param pkApprovalId 用户ID
     */
    public static int updateAPV_isending(String pkApprovalId) {
        TApvApproval entity = new TApvApproval();
        entity.setPkApprovalId(pkApprovalId);
        entity.setIsEnding(0);
        return ntClient.tApvApprovalService.updateAPV_isending(entity);
    }
    /**
     * 删除与一级审批相关联的审批数据
     * @param pkRecordId 一级审批记录的ID
     * @return
     */
    public static String deleteApprovalRecord(String pkRecordId) {
        DApvRecord entity = ntClient.tApvApprovalService.selectApvRecordById(pkRecordId);
        int i = ntClient.tApvApprovalService.deleteApvRecordBySourceId(entity.getSourceId());
        if(i > 0){
            return "该入职审批数据删除成功！";
        }else {
            return "该入职审批数据删除失败！";

        }
    }
    public static Map<String,Object> handleJsonData(TStaffEntry tStaffEntry) {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("sourceId",tStaffEntry.getPkEntryId());
        resultMap.put("name",tStaffEntry.getStaffName());
        StringBuffer stringBuffer = new StringBuffer();
        String sex = tStaffEntry.getStaffSex()==1?"男":"女";
        stringBuffer.append("性别："+sex+",");
        stringBuffer.append("年龄："+tStaffEntry.getStaffAge()+",");
        stringBuffer.append("入职部门："+tStaffEntry.getStaffDept()+",");
        stringBuffer.append("入职岗位："+tStaffEntry.getStaffPosition()+",");
        stringBuffer.append("联系电话："+tStaffEntry.getStaffTel()+",");
        stringBuffer.append("工作地："+tStaffEntry.getWorkAddress()+",");
        stringBuffer.append("招聘方式："+tStaffEntry.getRecruitWay()+",");
        stringBuffer.append("证件类型："+tStaffEntry.getIdType()+",");
        stringBuffer.append("证件号："+tStaffEntry.getIdCard()+",");
        //处理入职时间
        Date entryDate = tStaffEntry.getEntryTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String entryTime = entryDate == null?"无入职时间":simpleDateFormat.format(entryDate);
        stringBuffer.append("入职时间："+entryTime+",");
        stringBuffer.append("个人邮箱："+tStaffEntry.getEmail()+",");
        stringBuffer.append("备注："+tStaffEntry.getRemarks()+",");
        stringBuffer.append("入职说明："+tStaffEntry.getEntryDesc()+"。");
        resultMap.put("other",stringBuffer);
        return resultMap;
    }
    /**
     *
     * @param sysToken
     * @param apvName 审批类型名称，如：入职审批
     * @param dataType
     * @return
     */
    public static JSONObject handleApproval(SysToken sysToken,String currentSourceId, String apvName, int dataType) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("msg","获取"+apvName+"数据成功");
        /**
         * 查询审批流程
         */
        //先查询apvName的PK_ID
        TApvApvtype entityApv = ntClient.tApvApvtypeService.selectByName(apvName);
        if (entityApv != null) {
            String approvalType = entityApv.getPkApvtypeId();
            resultJson.put("approvalType",approvalType);
            List<TApvApproval> resultList = new ArrayList<>();
            String pk_apv_id = null;
            TStaffInfo deptLeader = null;
            TStaffInfo financeLeader = null;
            TStaffInfo humanResources = null;
            TStaffInfo generalManager = null;
            List<TStaffInfo> staffInfos = null;
            int isStart = 1;
            int num = 1;
            boolean flag = true;
            while (flag) {
                TApvApproval apv = ntClient.tApvApprovalService.selectApvSet(pk_apv_id,approvalType,dataType,isStart);
                if (apv != null) {
                    /**
                     * 一、先处理职位以及审批人
                     */
                    if(apv.getApvStation().equals("zdy")){
                        //说明为自定义审批人，只需返回员工列表即可
                        if(staffInfos == null){
                            staffInfos = ntClient.tStaffInfoService.selectAll();
                        }
                        resultJson.put("staffInfos",staffInfos);
                        apv.setStationName("自定义");
                    }else if(apv.getApvStation().equals("deptLeader")){
                        if(deptLeader == null){
                            //说明为部门主管审批人，需查询该操作用户所在部门，再查其领导
                            deptLeader = ntClient.tStaffInfoService.selectDeptLeader(sysToken.getFkUserId());
                        }
                        if(deptLeader != null){
                            apv.setApprovalPeople(deptLeader.getPkStaffId());
                            apv.setApprovalPeopleName(deptLeader.getStaffName());
                        }
                        apv.setStationName("部门主管");
                    }else if(apv.getApvStation().equals("financeLeader")){
                        if(financeLeader == null){
                            //说明为财务主管审批人，财务主管positionID = 671039a4d14d41918f086fac72a8cad6
                            financeLeader = ntClient.tStaffInfoService.selectLeaderByPosition("671039a4d14d41918f086fac72a8cad6");
                        }
                        if(financeLeader != null){
                            apv.setApprovalPeople(financeLeader.getPkStaffId());
                            apv.setApprovalPeopleName(financeLeader.getStaffName());
                        }
                        apv.setStationName("财务主管");
                    }else if(apv.getApvStation().equals("humanResources")){
                        if(humanResources == null){
                            //说明为人力资源主管审批人，人力资源主管positionID = 3d5857d429c640f896d5be97bdb45976
                            humanResources = ntClient.tStaffInfoService.selectLeaderByPosition("3d5857d429c640f896d5be97bdb45976");
                        }
                        if(humanResources != null){
                            apv.setApprovalPeople(humanResources.getPkStaffId());
                            apv.setApprovalPeopleName(humanResources.getStaffName());
                        }
                        apv.setStationName("人力资源主管");
                    }else if(apv.getApvStation().equals("generalManager")){
                        if(generalManager == null){
                            //说明为总经理审批人，总经理positionID = 91c51aade3854305926095f7ab36b541
                            generalManager = ntClient.tStaffInfoService.selectLeaderByPosition("91c51aade3854305926095f7ab36b541");
                        }
                        if(generalManager != null){
                            apv.setApprovalPeople(generalManager.getPkStaffId());
                            apv.setApprovalPeopleName(generalManager.getStaffName());
                        }
                        apv.setStationName("总经理");
                    }
                    apv.setIsStart(num);
                    //循环次数加1
                    num ++;
                    //
                    isStart = 0;
                    /**
                     * 二、再判断下级审批
                     */
                    if (!apv.getNextApproval().equals("0")) {
                        /**
                         * 说明还有下级审批
                         */
                        pk_apv_id = apv.getNextApproval();
                    } else {
                        /**
                         * 该审批已到最后审批人
                         */
                        flag = false;
                    }
                    resultList.add(apv);
                }else {
                    resultJson.put("msg",apvName+"未设置审批人，确认后可直接通过！");
                    flag = false;
                }
            }
            resultJson.put("apvList",resultList);
            /**
             * 抄送人
             */
            TApvApproval selectEntity = new TApvApproval();
            selectEntity.setDataType(dataType);
            selectEntity.setApvStation("csr");
            selectEntity.setApprovalType(approvalType);
            List<TApvApproval> csrList = ntClient.tApvApprovalService.selectAllPage(selectEntity);
            resultJson.put("csrList",csrList);
        }else {
            resultJson.put("msg","未有"+apvName+"审批流程，确认后可直接通过！");
            resultJson.put("approvalType",null);
        }
        //当前来源的信息
        if("入职申请".equals(apvName)){
            TStaffEntry tStaffEntry = ntClient.tStaffEntryService.selectByPkId2(currentSourceId);
            Map<String,Object> currentSourceMap = ObjectAsyncTask.handleJsonData(tStaffEntry);
            resultJson.put("currentSource",currentSourceMap);
        }else if("转正申请".equals(apvName)){
            TStaffEntry tStaffEntry = ntClient.tStaffEntryService.selectByPkId2(currentSourceId);
            Map<String,Object> currentSourceMap = ObjectAsyncTask.handleJsonData(tStaffEntry);
            resultJson.put("currentSource",currentSourceMap);
        }else if("离职申请".equals(apvName)){
            //员工个人基本信息
            TStaffInfo staffInfo = ntClient.tStaffInfoService.selectByPkId2(currentSourceId);
            resultJson.put("currentSource",staffInfo);
        } else if("调动申请".equals(apvName)){
            //调动信息
            TStaffReassign selectEntity = new TStaffReassign();
            selectEntity.setPkReassignId(currentSourceId);
            List<TStaffReassign> list = ntClient.tStaffReassignService.selectAllPage(selectEntity);
            resultJson.put("currentSource",list.get(0));
        }
        return resultJson;
    }

    /**
     * 添加审批记录
     * @param stringBuffer
     * @param json
     * @param approvalType
     * @return
     */
    public static StringBuffer addApprovalRecord(StringBuffer stringBuffer, JSONObject json,SysToken sysToken, String approvalType,String pkQuitId) {
        /**
         * 抄送人
         */
        String newPkId = IDUtils.getUUID();
        String firstApvrecordId = newPkId;
        JSONArray csrArray = json.getJSONArray("csrList");
        if(csrArray != null){
            //说明选择了抄送人
            String csrIdsStr = csrArray.toString();
            if(!csrIdsStr.equals("[]")){
                //说明选择了抄送人
                List<DCcRecord> csrpxqList = JSONObject.parseArray(csrIdsStr,DCcRecord.class);
                List<DCcRecord> csrList = new ArrayList<>();
                /**
                 * 抄送人去重
                 */
                //创建新集合
                //遍历旧集合，获得每一个元素
                Iterator<DCcRecord> it = csrpxqList.iterator();
                while(it.hasNext()) {
                    DCcRecord s = it.next();
                    //那这个集合去找新集合，看看有没有
                    if(!csrList.contains(s)) {
                        csrList.add(s);
                    }
                }

                List<DCcRecord> ccRecordList = new ArrayList<>();
                //添加抄送记录
                if(csrList != null && csrList.size() > 0){
                    for (DCcRecord ccRecord:csrList) {
                        DCcRecord dCcRecord = new DCcRecord();
                        dCcRecord.setPkCcId(IDUtils.getUUID());
                        dCcRecord.setFkStaffId(ccRecord.getFkStaffId());
                        dCcRecord.setStaffName(ccRecord.getStaffName());
                        dCcRecord.setFirstApvrecordId(firstApvrecordId);
                        ccRecordList.add(dCcRecord);
                    }
                }
                //批量添加抄送记录
                int i = ntClient.dCcRecordService.insertCCRecordBatch(ccRecordList);
                if(i > 0){
                    stringBuffer.append("抄送记录添加成功！");
                }else {
                    stringBuffer.append("抄送记录添加成功！");
                    return stringBuffer;
                }
            }
        }

        /**
         * 审批人
         */
        JSONArray apvArray = json.getJSONArray("apvList");
        if(apvArray != null){
            String apvIdsStr = apvArray.toString();
            if(!apvIdsStr.equals("[]")){
                List<TApvApproval> apvList1 = JSONObject.parseArray(apvIdsStr,TApvApproval.class);
                /**
                 * 先去重
                 */
                List<TApvApproval> apvList2 = new ArrayList<>();
                for (int m = 0; m < apvList1.size()-1; m++) {
                    for (int n = apvList1.size()-1; n > m; n--) {
                        if(apvList1.get(n).getApprovalPeople()!= null && apvList1.get(m).getApprovalPeople()!= null){
                            if (apvList1.get(n).getApprovalPeople().equals(apvList1.get(m).getApprovalPeople())) {
                                apvList1.remove(n);
                            }
                        }
                    }
                }
                apvList2 = apvList1;
                /**
                 * 再排序
                 */
                List<TApvApproval> apvList = new ArrayList<>();
                Collections.sort(apvList2, new Comparator<TApvApproval>() {
                    @Override
                    public int compare(TApvApproval o1, TApvApproval o2) {
                        //升序
                        return String.valueOf(o1.getIsStart()).compareTo(String.valueOf(o2.getIsStart()));
                    }
                });
                apvList = apvList2;
                /**
                 * 开始添加审批记录
                 */
                List<DApvRecord> apvRecordList = new ArrayList<>();
                int isStart = 1;
                int num = 1;
                for (TApvApproval approval: apvList) {
                    String nextPkId = IDUtils.getUUID();
                    DApvRecord dApvRecord = new DApvRecord();
                    dApvRecord.setPkRecordId(newPkId);
                    dApvRecord.setApprovalType(approvalType);
                    dApvRecord.setSponsor(sysToken.getFullName());
                    dApvRecord.setStartTime(new Date());
                    dApvRecord.setApvApproval(approval.getApprovalPeople());
                    if(num == apvList.size()){
                        dApvRecord.setNextApproval("0");
                    }else {
                        dApvRecord.setNextApproval(nextPkId);
                    }
                    dApvRecord.setSourceId(pkQuitId);
                    dApvRecord.setIsStart(isStart);
                    dApvRecord.setIsIng(1);
                    apvRecordList.add(dApvRecord);
                    //改变newPkId、isStart的值
                    newPkId = nextPkId;
                    isStart = 0;
                    num ++;
                }
                //批量添加审批记录
                int j = ntClient.tApvApprovalService.insertApvRecordBatch(apvRecordList);
                if(j > 0){
                    stringBuffer.append("审批记录添加成功！");
                }else {
                    stringBuffer.append("审批记录添加成功！");
                    return stringBuffer;
                }
            }
        }
        return stringBuffer;
    }

    //初始化所有服务
    @PostConstruct
    public void init() {
        ntClient = this;
        ntClient.tOutfitDeptService = this.tOutfitDeptService;
        ntClient.tApvApprovalService = this.tApvApprovalService;
        ntClient.tApvApvtypeService = this.tApvApvtypeService;
        ntClient.tStaffInfoService = this.tStaffInfoService;
        ntClient.tStaffEntryService = this.tStaffEntryService;
        ntClient.dCcRecordService = this.dCcRecordService;
        ntClient.tStaffReassignService = this.tStaffReassignService;
    }
}
