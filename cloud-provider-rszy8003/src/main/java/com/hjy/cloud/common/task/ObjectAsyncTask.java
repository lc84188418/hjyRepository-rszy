package com.hjy.cloud.common.task;

import com.alibaba.fastjson.JSONObject;
import com.hjy.cloud.common.entity.DApvRecord;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_apv.entity.TApvApproval;
import com.hjy.cloud.t_apv.entity.TApvApvtype;
import com.hjy.cloud.t_apv.service.TApvApprovalService;
import com.hjy.cloud.t_apv.service.TApvApvtypeService;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_outfit.service.TOutfitDeptService;
import com.hjy.cloud.t_staff.entity.TStaffEntry;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.t_staff.service.TStaffEntryService;
import com.hjy.cloud.t_staff.service.TStaffInfoService;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.t_system.service.TSysTokenService;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.t_system.entity.ReDeptUser;
import com.hjy.cloud.t_system.entity.ReUserRole;
import com.hjy.cloud.t_system.service.TSysParamService;
import com.hjy.cloud.t_system.service.TSysRoleService;
import com.hjy.cloud.t_system.service.TSysUserService;
import com.hjy.cloud.utils.TokenUtil;
import javafx.beans.binding.StringBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    //添加角色默认的权限-即主页的3个
    public static void addDefultRoelPerms(String fk_role_id) {
        List<String> idList = new ArrayList<String>();
        idList.add("1596706636946");
        idList.add("1596706882298");
        idList.add("1596707062416");
        ntClient.tSysRoleService.distributeMenu(fk_role_id,idList);
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
     * @param request
     * @param apvName 审批类型名称，如：入职审批
     * @param currentSourceId 当前来源，如该入职申请信息主键
     * @param dataType
     * @return
     */
    public static JSONObject handleApproval(HttpServletRequest request, String apvName, int dataType, String currentSourceId) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("msg","入职审批获取数据成功！");
        String token = TokenUtil.getRequestToken(request);
        SysToken sysToken = ntClient.tSysTokenService.selectPkId(token);
        /**
         * 查询入职审批流程
         */
        //先查询入职申请的PK_ID
        TApvApvtype entityApv = ntClient.tApvApvtypeService.selectByName(apvName);
        if (entityApv != null) {
            //12
            String approvalType = entityApv.getPkApvtypeId();
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
                TApvApproval apv = ntClient.tApvApprovalService.selectApvSet(pk_apv_id, approvalType,dataType,isStart);
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
                    }else if(apv.getApvStation().equals("deptLeader")){
                        if(deptLeader == null){
                            //说明为部门主管审批人，需查询该操作用户所在部门，再查其领导
                            deptLeader = ntClient.tStaffInfoService.selectDeptLeader(sysToken.getFkUserId());
                            resultJson.put("deptLeader",deptLeader);
                        }
                        apv.setApprovalPeople(deptLeader.getPkStaffId());
                    }else if(apv.getApvStation().equals("financeLeader")){
                        if(financeLeader == null){
                            //说明为财务主管审批人，财务主管positionID = 671039a4d14d41918f086fac72a8cad6
                            financeLeader = ntClient.tStaffInfoService.selectLeaderByPosition("671039a4d14d41918f086fac72a8cad6");
                            resultJson.put("financeLeader",financeLeader);
                        }
                        apv.setApprovalPeople(financeLeader.getPkStaffId());
                    }else if(apv.getApvStation().equals("humanResources")){
                        if(humanResources == null){
                            //说明为人力资源主管审批人，人力资源主管positionID = 3d5857d429c640f896d5be97bdb45976
                            humanResources = ntClient.tStaffInfoService.selectLeaderByPosition("3d5857d429c640f896d5be97bdb45976");
                            resultJson.put("humanResources",humanResources);
                        }
                        apv.setApprovalPeople(humanResources.getPkStaffId());
                    }else if(apv.getApvStation().equals("generalManager")){
                        if(generalManager == null){
                            //说明为总经理审批人，总经理positionID = 91c51aade3854305926095f7ab36b541
                            generalManager = ntClient.tStaffInfoService.selectLeaderByPosition("91c51aade3854305926095f7ab36b541");
                            resultJson.put("generalManager",generalManager);
                        }
                        apv.setApprovalPeople(generalManager.getPkStaffId());
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
                    resultJson.put("msg","入职申请未设置审批人，确认后可直接通过！");
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
            resultJson.put("msg","未有入职申请审批流程，确认后可直接通过！");
        }
        //当前来源的信息
        if("入职申请".equals(apvName)){
            TStaffEntry tStaffEntry = ntClient.tStaffEntryService.selectByPkId2(currentSourceId);
            Map<String,Object> currentSourceMap = ObjectAsyncTask.handleJsonData(tStaffEntry);
            resultJson.put("currentSource",currentSourceMap);
        }
        return resultJson;
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
    }
}
