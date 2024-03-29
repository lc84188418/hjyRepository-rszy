package com.hjy.cloud.common.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hjy.cloud.common.entity.User;
import com.hjy.cloud.common.utils.ApvUtil;
import com.hjy.cloud.t_apv.entity.DApvRecord;
import com.hjy.cloud.t_apv.entity.DCcRecord;
import com.hjy.cloud.t_apv.entity.TApvApproval;
import com.hjy.cloud.t_apv.entity.TApvApvtype;
import com.hjy.cloud.t_apv.enums.ApprovaltypeEnum;
import com.hjy.cloud.t_apv.result.SourceDetailResult;
import com.hjy.cloud.t_apv.service.DApvRecordService;
import com.hjy.cloud.t_apv.service.DCcRecordService;
import com.hjy.cloud.t_apv.service.TApvApprovalService;
import com.hjy.cloud.t_apv.service.TApvApvtypeService;
import com.hjy.cloud.t_dictionary.entity.TDictionaryHtlx;
import com.hjy.cloud.t_dictionary.service.TDictionaryHtlxService;
import com.hjy.cloud.t_outfit.service.TOutfitDeptService;
import com.hjy.cloud.t_staff.entity.*;
import com.hjy.cloud.t_staff.result.StaffInfos;
import com.hjy.cloud.t_staff.service.*;
import com.hjy.cloud.t_system.entity.ReDeptUser;
import com.hjy.cloud.t_system.entity.ReUserRole;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.t_system.service.TSysParamService;
import com.hjy.cloud.t_system.service.TSysRoleService;
import com.hjy.cloud.t_system.service.TSysTokenService;
import com.hjy.cloud.t_system.service.TSysUserService;
import com.hjy.cloud.utils.IDUtils;
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
    private TSysParamService tSysParamService;
    @Autowired
    private TSysUserService tSysUserService;
    @Autowired
    private TSysRoleService tSysRoleService;
    @Autowired
    private TSysTokenService tSysTokenService;
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
    @Resource
    private DApvRecordService dApvRecordService;
    @Resource
    private TDictionaryHtlxService tDictionaryHtlxService;
    @Resource
    private TStaffZzService tStaffZzService;
    @Resource
    private TStaffQuitService tStaffQuitService;
    private static ObjectAsyncTask ntClient;

    /**
     * 向用户-角色关联表中添加一条用户角色信息
     * @param pkUserId 用户ID
     * @param roleId 角色ID
     */
    public static void addUserRoleByUserRole(String pkUserId, String roleId) {
        ReUserRole userRole = new ReUserRole();
        userRole.setPk_userRole_id(IDUtils.getUUID());
        userRole.setFk_user_id(pkUserId);
        userRole.setFk_role_id(roleId);
        ntClient.tSysUserService.addUserRoleByUserRole(userRole);
    }

    /**
     * 删除角色的所有权限
     * @param fk_role_id 角色ID
     */
    public static void deleteRolePermsByRoleId(String fk_role_id) {
        ntClient.tSysRoleService.deleteRolePermsByRoleId(fk_role_id);
    }

    //从请求中获取token
    public static SysToken getSysToken(HttpServletRequest request) {
        String tokenId = TokenUtil.getRequestToken(request);
        if(StringUtils.isEmpty(tokenId)){
            return null;
        }else {
            return ntClient.tSysTokenService.selectPkId(tokenId);
        }
    }
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
     * 删除与一级审批相关联的审批数据
     * @param pkRecordId 一级审批记录的ID
     * @return
     */
    public static String deleteApprovalRecord(String pkRecordId) {
        DApvRecord entity = ntClient.dApvRecordService.selectSourceIdById(pkRecordId);
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
     * 发起审批页面统一方法
     * @param sysToken
     * @param apvName 审批类型名称，如：入职审批
     * @param dataType
     * @return
     */
    public static JSONObject sponsorApprovalPage(SysToken sysToken,String currentSourceId, String apvName, int dataType) {
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
            List<StaffInfos> staffInfos = null;
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
            List<TDictionaryHtlx> htlxList = ntClient.tDictionaryHtlxService.queryAll();
            resultJson.put("htlx",htlxList);
        }else if("转正申请".equals(apvName)){
            //员工个人基本信息
            TStaffInfo staffInfo = ntClient.tStaffInfoService.selectByPkId2(currentSourceId);
            resultJson.put("currentSource",staffInfo);
        }else if("离职申请".equals(apvName)){
            //员工个人基本信息
            TStaffInfo staffInfo = ntClient.tStaffInfoService.selectByPkId2(currentSourceId);
            resultJson.put("currentSource",staffInfo);
        } else if("调动申请".equals(apvName)){
            if(StringUtils.isEmpty(currentSourceId)){
                //说明是员工自主发起调动
                resultJson.put("currentSource",null);
            }else {
                //调动信息
                TStaffReassign selectEntity = new TStaffReassign();
                selectEntity.setPkReassignId(currentSourceId);
                List<TStaffReassign> list = ntClient.tStaffReassignService.selectAllPage(selectEntity);
                if(list != null && list.size() > 0){
                    resultJson.put("currentSource",list.get(0));
                }else {
                    resultJson.put("currentSource",null);
                }
            }
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
    public static StringBuffer addApprovalRecord(StringBuffer stringBuffer, JSONObject json,
                                                 SysToken sysToken, String approvalType,
                                                 String pkSourceId, User applyPeople,
                                                 String firstApvrecordId,int sponsorNum) {
        /**
         * 抄送人
         */
        String newPkId = IDUtils.getUUID();
        JSONArray csrArray = json.getJSONArray("csrList");
        if(csrArray != null){
            //说明选择了抄送人
            String csrIdsStr = csrArray.toString();
            if(!csrIdsStr.equals("[]")){
                //说明选择了抄送人
                List<TApvApproval> csrpxqList = JSONObject.parseArray(csrIdsStr,TApvApproval.class);
                List<TApvApproval> csrList = new ArrayList<>();
                /**
                 * 抄送人去重
                 */
                //创建新集合
                //遍历旧集合，获得每一个元素
                Iterator<TApvApproval> it = csrpxqList.iterator();
                while(it.hasNext()) {
                    TApvApproval s = it.next();
                    //那这个集合去找新集合，看看有没有
                    if(!csrList.contains(s)) {
                        csrList.add(s);
                    }
                }

                List<DCcRecord> ccRecordList = new ArrayList<>();
                //添加抄送记录
                if(csrList != null && csrList.size() > 0){
                    for (TApvApproval ccRecord:csrList) {
                        DCcRecord dCcRecord = new DCcRecord();
                        dCcRecord.setPkCcId(IDUtils.getUUID());
                        dCcRecord.setFkStaffId(ccRecord.getApprovalPeople());
                        dCcRecord.setStaffName(ccRecord.getApprovalPeopleName());
                        dCcRecord.setFirstApvrecordId(firstApvrecordId);
                        ccRecordList.add(dCcRecord);
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
        }

        /**
         * 审批人
         */
        JSONArray apvArray = json.getJSONArray("apvList");
        int isquchong = 0;
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
                                isquchong = 1;
                                apvList1.remove(n);
                            }
                        }else {
                            throw new RuntimeException("审批人为空了");
                        }
                    }
                }
                apvList2 = apvList1;
                if (isquchong == 1){
                    stringBuffer.append("审批人已自动去重！");
                }
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
                    if(num == 1){
                        dApvRecord.setPkRecordId(firstApvrecordId);
                    }else {
                        dApvRecord.setPkRecordId(newPkId);
                    }
                    dApvRecord.setApprovalType(approvalType);
                    dApvRecord.setApplyPeopleId(applyPeople.getUserId());
                    dApvRecord.setApplyPeople(applyPeople.getFullName());
                    //前端需要传peopleName
                    dApvRecord.setSponsorId(sysToken.getFkUserId());
                    dApvRecord.setSponsor(sysToken.getFullName());
                    dApvRecord.setStartTime(new Date());
                    dApvRecord.setApvApproval(approval.getApprovalPeople());
                    if(num == apvList.size()){
                        dApvRecord.setNextApproval("0");
                    }else {
                        dApvRecord.setNextApproval(nextPkId);
                    }
                    dApvRecord.setSourceId(pkSourceId);
                    dApvRecord.setIsStart(isStart);
                    dApvRecord.setIsIng(1);
                    //添加时初始状态为0，0审批中1通过2拒绝
                    dApvRecord.setApvStatus(0);
                    dApvRecord.setSponsorNum(sponsorNum);
                    apvRecordList.add(dApvRecord);
                    //改变newPkId、isStart的值
                    newPkId = nextPkId;
                    isStart = 0;
                    num ++;
                }
                if(apvRecordList != null && apvRecordList.size() > 0){
                    //批量添加审批记录
                    int j = ntClient.dApvRecordService.insertApvRecordBatch(apvRecordList);
                    if(j > 0){
                        stringBuffer.append("审批记录添加成功！");
                    }else {
                        stringBuffer.append("审批记录添加成功！");
                        return stringBuffer;
                    }
                }
            }
        }
        return stringBuffer;
    }
    /**
     * 处理审批记录
     * @param records
     * @return
     */
    public static List<DApvRecord> handleApvRecord(List<DApvRecord> records) {
        if(records != null && records.size() > 0){
            Iterator<DApvRecord> iterator = records.iterator();
            while (iterator.hasNext()){
                DApvRecord next = iterator.next();
                SourceDetailResult resultBf = new SourceDetailResult();
                //处理详情
                if(ApprovaltypeEnum.Type_12.getCode().equals(next.getApprovalType())){
                    //查询入职详情
                    TStaffEntry tStaffEntry = ntClient.tStaffEntryService.selectByPkId2(next.getSourceId());
                    resultBf = ApvUtil.handleData(tStaffEntry);
                }
                if(ApprovaltypeEnum.Type_13.getCode().equals(next.getApprovalType())){
                    //查询转正详情
                    TStaffZz query = new TStaffZz();
                    query.setPkZzId(next.getSourceId());
                    List<TStaffZz> zzs = ntClient.tStaffZzService.selectAllPage(query);
                    TStaffZz zz = new TStaffZz();
                    if(zzs != null && zzs.size() > 0){
                        zz = zzs.get(0);
                    }
                    resultBf = ApvUtil.handleData(zz);
                }
                if(ApprovaltypeEnum.Type_11.getCode().equals(next.getApprovalType())){
                    //查询离职详情
                    TStaffQuit query = new TStaffQuit();
                    query.setPkQuitId(next.getSourceId());
                    List<TStaffQuit> quits = ntClient.tStaffQuitService.selectAllPage(query);
                    TStaffQuit quit = new TStaffQuit();
                    if(quits != null && quits.size() > 0){
                        quit = quits.get(0);
                    }
                    resultBf = ApvUtil.handleData(quit);
                }
                if(ApprovaltypeEnum.Type_8.getCode().equals(next.getApprovalType())){
                    //查询调动详情
                    TStaffReassign query = new TStaffReassign();
                    query.setPkReassignId(next.getSourceId());
                    List<TStaffReassign> reassigns = ntClient.tStaffReassignService.selectAllPage(query);
                    TStaffReassign reassign = new TStaffReassign();
                    if(reassigns != null && reassigns.size() > 0){
                        reassign = reassigns.get(0);
                    }
                    resultBf = ApvUtil.handleData(reassign);
                }
                next.setSourceDetail(resultBf);
                //处理第二级审批
                DApvRecord second = ntClient.dApvRecordService.selectById(next.getNextApproval());
                if(second != null){
                    //处理第三级审批
                    DApvRecord third = ntClient.dApvRecordService.selectById(second.getNextApproval());
                    if(third != null){
                        //处理第四级审批
                        DApvRecord fourth = ntClient.dApvRecordService.selectById(third.getNextApproval());
                        if(fourth != null){
                            //处理第五级审批
                            DApvRecord five = ntClient.dApvRecordService.selectById(fourth.getNextApproval());
                            fourth.setNextRecord(five);
                        }
                        third.setNextRecord(fourth);
                    }
                    second.setNextRecord(third);
                }
                next.setNextRecord(second);
            }

        }
        return records;
    }

    public static int updateReassignData(TStaffReassign reassign) {
        //将员工名单信息改为调动后的
        TStaffInfo tStaffInfo = new TStaffInfo();
        tStaffInfo.setPkStaffId(reassign.getFkStaffId());
        tStaffInfo.setFkDeptId(reassign.getReassignDeptId());
        tStaffInfo.setFkPositionId(reassign.getReassignPosition());
        tStaffInfo.setFkWorkaddressId(reassign.getReassignAddress());
        return ntClient.tStaffInfoService.updateById(tStaffInfo);
    }

    public static int updateQuitData(TStaffQuit staffQuit) {
        //将员工名单信息改为调动后的
        TStaffInfo tStaffInfo = new TStaffInfo();
        tStaffInfo.setPkStaffId(staffQuit.getFkStaffId());
        tStaffInfo.setStaffStatus(0);
        return ntClient.tStaffInfoService.updateById(tStaffInfo);
    }
    public static int updateZZData(TStaffZz staffZz) {
        //将员工名单信息改为调动后的
        TStaffInfo tStaffInfo = new TStaffInfo();
        tStaffInfo.setPkStaffId(staffZz.getFkStaffId());
        tStaffInfo.setStaffStatus(2);
        return ntClient.tStaffInfoService.updateById(tStaffInfo);
    }
    //初始化所有服务
    @PostConstruct
    public void init() {
        ntClient = this;
        ntClient.tSysParamService = this.tSysParamService;
        ntClient.tSysUserService = this.tSysUserService;
        ntClient.tSysRoleService = this.tSysRoleService;
        ntClient.tSysTokenService = this.tSysTokenService;
        ntClient.tOutfitDeptService = this.tOutfitDeptService;
        ntClient.tApvApprovalService = this.tApvApprovalService;
        ntClient.tApvApvtypeService = this.tApvApvtypeService;
        ntClient.tStaffInfoService = this.tStaffInfoService;
        ntClient.tStaffEntryService = this.tStaffEntryService;
        ntClient.tStaffZzService = this.tStaffZzService;
        ntClient.dCcRecordService = this.dCcRecordService;
        ntClient.dApvRecordService = this.dApvRecordService;
        ntClient.tStaffReassignService = this.tStaffReassignService;
        ntClient.tStaffQuitService = this.tStaffQuitService;
        ntClient.tDictionaryHtlxService = this.tDictionaryHtlxService;
    }
}
