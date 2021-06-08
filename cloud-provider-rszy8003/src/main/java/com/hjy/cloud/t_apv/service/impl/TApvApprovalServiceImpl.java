package com.hjy.cloud.t_apv.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.common.entity.DApvRecord;
import com.hjy.cloud.common.utils.ApvUtil;
import com.hjy.cloud.common.utils.UserShiroUtil;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_apv.dao.DCcRecordMapper;
import com.hjy.cloud.t_apv.dao.TApvApprovalMapper;
import com.hjy.cloud.t_apv.dao.TApvApvtypeMapper;
import com.hjy.cloud.t_apv.dao.TApvGroupMapper;
import com.hjy.cloud.t_apv.entity.*;
import com.hjy.cloud.t_apv.result.ApprovalAddResult;
import com.hjy.cloud.t_apv.service.TApvApprovalService;
import com.hjy.cloud.t_staff.dao.*;
import com.hjy.cloud.t_staff.entity.*;
import com.hjy.cloud.t_system.dao.TSysTokenMapper;
import com.hjy.cloud.t_system.dao.TSysUserMapper;
import com.hjy.cloud.t_system.entity.ActiveUser;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.t_system.entity.TSysUser;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.JsonUtil;
import com.hjy.cloud.utils.PasswordEncryptUtils;
import com.hjy.cloud.utils.TokenUtil;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.utils.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * (TApvApproval)表服务实现类
 *
 * @author makejava
 * @since 2021-02-26 14:50:48
 */
@Service("tApvApprovalService")
public class TApvApprovalServiceImpl implements TApvApprovalService {

    @Resource
    private TApvApprovalMapper tApvApprovalMapper;
    @Resource
    private DCcRecordMapper dCcRecordMapper;
    @Resource
    private TApvApvtypeMapper tApvApvtypeMapper;
    @Resource
    private TApvGroupMapper tApvGroupMapper;
    @Resource
    private TStaffInfoMapper tStaffInfoMapper;
    @Resource
    private TStaffEntryMapper tStaffEntryMapper;
    @Resource
    private TStaffZzMapper tStaffZzMapper;
    @Resource
    private TStaffQuitMapper tStaffQuitMapper;
    @Resource
    private TStaffReassignMapper tStaffReassignMapper;
    @Resource
    private TSysUserMapper tSysUserMapper;
    @Resource
    private TSysTokenMapper tSysTokenMapper;
    @Value("${server.port}")
    private String serverPort;
    @Value("${spring.cloud.application.ip}")
    private String webIp;
    /**
     * 添加前获取数据
     *
     * @return
     */
    @Override
    public CommonResult<ApprovalAddResult> insertPage() {
        ApprovalAddResult addResult = new ApprovalAddResult();
        //审批类型
        List<TApvApvtype> apvtypes = tApvApvtypeMapper.selectAllId_Name();
        addResult.setApvtypes(apvtypes);
        //员工信息
        List<TStaffInfo> staffInfos = tStaffInfoMapper.selectAllId_Name();
        addResult.setStaffInfos(staffInfos);
        //审批人按岗位指定
        /**
         * 若为自定义，apvStation值指定为zdy,
         * 若为部门主管，apvStation值指定为deptLeader,
         * 若为财务主管，apvStation值指定为financeLeader,
         * 若为人力资源主管，apvStation值指定为humanResources
         * 若为总经理，apvStation值指定为generalManager
         */
        List<TApvApproval> apvApprovals = ApvUtil.getApprovalProgress();
        addResult.setPositions(apvApprovals);
        return new CommonResult(200, "success", "获取数据成功", addResult);
    }

    /**
     * 添加数据
     *
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(String param) {
        JSONObject jsonObject = JSON.parseObject(param);
        //审批类型
        String approvalType = String.valueOf(jsonObject.get("approvalType"));
        if(StringUtils.isEmpty(approvalType)){
            return new CommonResult(444, "error", "未传入审批类型", null);
        }else {
            //删除原有审批
            int z = tApvApprovalMapper.deleteByType(approvalType);
        }
        int dataType = 1;
        int isStart = 1;
        String newPkId = IDUtils.getUUID();
        List<TApvApproval> apvList1 = new ArrayList<>();
        List<TApvApproval> idList = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        //审批人主键
        JSONArray jsonArray = jsonObject.getJSONArray("ids");
        if(jsonArray == null){
            stringBuffer.append("未选择审批人！确认后可直接通过！");
        }else{
            String userIdsStr = jsonArray.toString();
            if(userIdsStr.equals("[]")){
                stringBuffer.append("未选择审批人！确认后可直接通过！");
            }else {
                apvList1 = JSONObject.parseArray(userIdsStr,TApvApproval.class);
            }
        }
        if(apvList1.size() > 0 ){
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
             * 二、再做排序，按照isStart从低到高
             */
            Collections.sort(apvList2, new Comparator<TApvApproval>() {
                @Override
                public int compare(TApvApproval o1, TApvApproval o2) {
                    //升序
                    return String.valueOf(o1.getIsStart()).compareTo(String.valueOf(o2.getIsStart()));
                }
            });
            idList = apvList2;
            /**
             * 若为自定义，apvStation值指定为zdy,
             * 若为部门主管，apvStation值指定为deptLeader,
             * 若为财务主管，apvStation值指定为financeLeader,
             * 若为人力资源主管，apvStation值指定为humanResources
             * 若为总经理，apvStation值指定为generalManager
             */
            //保存该审批流程设置记录
            Iterator<TApvApproval> iterator = idList.iterator();
            while (iterator.hasNext()){
                TApvApproval next = iterator.next();
                String nextPkId = IDUtils.getUUID();
                next.setPkApprovalId(newPkId);
                next.setApprovalType(approvalType);
                next.setNextApproval(nextPkId);
                next.setDataType(dataType);
                next.setIsEnding(0);
                next.setIsStart(isStart);
                //改变主键
                newPkId = nextPkId;
                isStart = 0;
            }
            idList.get(idList.size()-1).setIsEnding(1);
            idList.get(idList.size()-1).setNextApproval("0");
        }
        /**
         * 抄送人
         */
        List<TApvApproval> csrList = new ArrayList<>();
        JSONArray csrArray = jsonObject.getJSONArray("csr");
        if(csrArray == null){
            stringBuffer.append("未选择抄送人");
        }else{
            String csrStr = csrArray.toString();
            if(csrStr.equals("[]")){
                stringBuffer.append("未选择审批人");
            }else {
                csrList = JSONObject.parseArray(csrStr,TApvApproval.class);
            }
        }
        int i = 0;
        if(csrList != null && csrList.size() > 0){
            Iterator<TApvApproval> csrIterator = csrList.iterator();
            while (csrIterator.hasNext()){
                String csrPkId = IDUtils.getUUID();
                TApvApproval next = csrIterator.next();
                next.setPkApprovalId(csrPkId);
                next.setApprovalType(approvalType);
                next.setNextApproval("csr");
                next.setDataType(dataType);
                next.setApvStation("csr");
                next.setIsEnding(0);
                next.setIsStart(0);
            }
            idList.addAll(idList.size(),csrList);
        }
        if(idList != null && idList.size() > 0){
            i = this.tApvApprovalMapper.insertBatch(idList);
        }
        if (i > 0) {
            if(dataType > 1){
                stringBuffer.append("该审批另一流程已新增成功！");
                return new CommonResult(201, "success", stringBuffer.toString(), null);
            }else {
                stringBuffer.append("新审批流程添加成功！");
                return new CommonResult(200, "success", stringBuffer.toString(), null);
            }
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }

    }

    /**
     * 修改数据
     *
     * @param tApvApproval
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TApvApproval tApvApproval) {
        int i = this.tApvApprovalMapper.updateByPkId(tApvApproval);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tApvApproval
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TApvApproval tApvApproval) {
        int i = this.tApvApprovalMapper.deleteById(tApvApproval);
        if (i > 0) {
            return new CommonResult(200, "success", "删除数据成功", null);
        } else {
            return new CommonResult().ErrorResult("数据已被删除，无需再次请求",null);
        }
    }

    /**
     * 获取单个数据
     *
     * @param tApvApproval 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TApvApproval tApvApproval) {
        TApvApproval entity = this.tApvApprovalMapper.selectByPkId(tApvApproval.getPkApprovalId());
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
    @Override
    public DApvRecord selectApvRecordById(String pkRecordId) {
        return this.tApvApprovalMapper.selectApvRecordById(pkRecordId);
    }
    @Transactional()
    @Override
    public int updateAPV_isending(TApvApproval entity) {
        return tApvApprovalMapper.updateByPkId(entity);
    }

    /**
     * 删除与一级审批相关联的审批数据
     * @param sourceId
     * @return
     */
    @Transactional()
    @Override
    public int deleteApvRecordBySourceId(String sourceId) {
        return tApvApprovalMapper.deleteApvRecordBySourceId(sourceId);

    }
    /**
     * 待审批,是指所有没有审批完成的记录，非操作用户自己的
     *
     */
    @Override
    public CommonResult waitApv() {
        List<DApvRecord> waitApvRecords = tApvApprovalMapper.waitApv();
        //将待审批数据进行处理
        List<TempApvEntity> apvRecords = this.optimizeApvRecord(waitApvRecords,null);
        JSONObject resultJson = new JSONObject();
        resultJson.put("waitApvRecords", apvRecords);
        return new CommonResult(200, "success", "获取待审批列表数据成功！", resultJson);

    }
    @Override
    public CommonResult<PageResult<DApvRecord>> ApvComplete(int pageNum, int pageSize) {
        //这里的分页会有问题，没时间改了
        PageHelper.startPage(pageNum, pageSize);
        List<DApvRecord> completeApvRecords = tApvApprovalMapper.ApvComplete();
        //将审批数据进行处理
        List<DApvRecord> recordResultList = this.handleApvRecord(completeApvRecords);
        PageResult<DApvRecord> result = PageUtil.getPageResult(new PageInfo<DApvRecord>(recordResultList));
        result.setTotal(recordResultList.size());
        return new CommonResult(200, "success", "获取已审批列表数据成功！", result);

    }

    /**
     *
     * @param dApvRecords
     * @return
     */
    private List<DApvRecord> handleApvRecord(List<DApvRecord> dApvRecords) {
        if(dApvRecords == null && dApvRecords.size() == 0){
            return null;
        }
        //第一级的
        Iterator<DApvRecord> iterator = dApvRecords.iterator();
        List<DApvRecord> secondRecords = dApvRecords;
        while (iterator.hasNext()) {
            DApvRecord first = iterator.next();
            if (first.getIsStart() == 1){
               if(!"0".equals(first.getNextApproval())){
                   Iterator<DApvRecord> secondIterator = secondRecords.iterator();
                   List<DApvRecord> thirdRecords = secondRecords;
                   while (secondIterator.hasNext()) {
                       DApvRecord second = secondIterator.next();
                       if (second.getPkRecordId().equals(first.getNextApproval())) {
                           first.setNextRecord(second);
                       }
                       if(!"0".equals(second.getNextApproval())){
                           Iterator<DApvRecord> thirdIterator = thirdRecords.iterator();
                           List<DApvRecord> fourthRecords = thirdRecords;
                           while (thirdIterator.hasNext()) {
                               DApvRecord third = thirdIterator.next();
                               if (third.getPkRecordId().equals(second.getNextApproval())) {
                                   second.setNextRecord(third);
                               }
                               if(!"0".equals(third.getNextApproval())){
                                   Iterator<DApvRecord> fourthIterator = fourthRecords.iterator();
                                   List<DApvRecord> fiveRecords = fourthRecords;
                                   while (fourthIterator.hasNext()) {
                                       DApvRecord fourth = fourthIterator.next();
                                       if (fourth.getPkRecordId().equals(third.getNextApproval())) {
                                           third.setNextRecord(fourth);
                                       }
                                       if(!"0".equals(fourth.getNextApproval())){
                                           //第五级的审批
                                           Iterator<DApvRecord> fiveIterator = fiveRecords.iterator();
                                           List<DApvRecord> sixthRecords = fiveRecords;
                                           while (fiveIterator.hasNext()) {
                                               DApvRecord five = fiveIterator.next();
                                               if (five.getPkRecordId().equals(fourth.getNextApproval())) {
                                                   fourth.setNextRecord(five);
                                               }
                                               if(!"0".equals(five.getNextApproval())){

                                               }
                                           }
                                       }
                                   }
                               }
                           }
                       }
                   }
               }
            }
//            else {
//                secondRecords.add(first);
//            }
        }
        //去除第一级不是开始的审批
        Iterator<DApvRecord> lastIterator = dApvRecords.iterator();
        while (lastIterator.hasNext()) {
            DApvRecord next = lastIterator.next();
            if(next.getIsStart() != 1){
                lastIterator.remove();
            }
        }
        return dApvRecords;
    }

    /**
     * 待审批,操作用户自己的
     *
     */
    @Override
    public CommonResult waitApvUser(HttpSession session,HttpServletRequest request) {
        String token = TokenUtil.getRequestToken(request);
        if(StringUtils.isEmpty(token)){
            return new CommonResult(444, "error", "请在请求头传入token", null);
        }
        SysToken tokenEntity = tSysTokenMapper.findByToken(token);
        ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
        /**
         *
         */
        List<DApvRecord> waitApvRecords = tApvApprovalMapper.waitApvUser(tokenEntity.getFkUserId());
        if(waitApvRecords == null || waitApvRecords.size() == 0){
            return new CommonResult(201, "success", "暂未有待审批流程！", null);
        }
        //将待审批数据进行处理
        List<TempApvEntity> apvRecords = this.optimizeApvRecord(waitApvRecords,tokenEntity.getFkUserId());
        JSONObject resultJson = new JSONObject();
        resultJson.put("waitApvRecords", apvRecords);
        return new CommonResult(200, "success", "获取数据成功！", resultJson);

    }
    @Override
    public CommonResult<PageResult<TempApvEntity>> apvRecordListSponsor(HttpSession session, HttpServletRequest request, String param) {
        String fullName = UserShiroUtil.getCurrentFullName(session,request);
        if(StringUtils.isEmpty(fullName)){
            return new CommonResult(444, "error", "无法验证当前用户信息，请刷新或重新登录后再试", null);
        }
        JSONObject json = JSON.parseObject(param);
        //实体数据
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        String apvResult = "全部";
        String apvResult2 = JsonUtil.getStringParam(json, "apvResult");
        if(!StringUtils.isEmpty(apvResult2)){
            apvResult = apvResult2;
        }
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
        List<DApvRecord> waitApvRecords = tApvApprovalMapper.apvRecordListSponsor(fullName);
        if(waitApvRecords == null || waitApvRecords.size() == 0){
            return new CommonResult(200, "success", "暂未有你发起的审批流程！", null);
        }
        //将审批数据进行处理
        List<TempApvEntity> apvRecords = this.optimizeApvRecord(waitApvRecords,null);
//        //最后进行筛选，已处理，未处理，全部
//        Iterator<TempApvEntity> iterator = apvRecords.iterator();
//        while (iterator.hasNext()) {
//            TempApvEntity next = iterator.next();
//        }
        PageResult<TempApvEntity> result = PageUtil.getPageResult(new PageInfo<TempApvEntity>(apvRecords));
        return new CommonResult(200, "success", "获取数据成功！", result);

    }
    @Override
    public CommonResult<PageResult<TempApvEntity>> apvRecordListCCToMe(HttpSession session, HttpServletRequest request, String param) {
        String userId = UserShiroUtil.getCurrentUserId(session,request);
        if(StringUtils.isEmpty(userId)){
            return new CommonResult(444, "error", "无法验证当前用户信息，请刷新或重新登录后再试", null);
        }
        JSONObject json = JSON.parseObject(param);
        //实体数据
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        String apvResult = "全部";
        String apvResult2 = JsonUtil.getStringParam(json, "apvResult");
        if(!StringUtils.isEmpty(apvResult2)){
            apvResult = apvResult2;
        }
        //1.first_record_id
        String first_record_id = tApvApprovalMapper.selectFirstRecordIdsByFkStaffId(userId);
        if(StringUtils.isEmpty(first_record_id)){
            return new CommonResult().ErrorResult("你暂未有抄送记录",null);
        }
        //2.source_id
        String source_id = tApvApprovalMapper.selectSourceIdsByPkRecordId(first_record_id);
        if(StringUtils.isEmpty(source_id)){
            return new CommonResult().ErrorResult("抄送来源记录已被移除",null);
        }
        //3.结果集

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
        List<DApvRecord> waitApvRecords = tApvApprovalMapper.apvRecordListCCToMe(source_id);
        if(waitApvRecords == null || waitApvRecords.size() == 0){
            return new CommonResult(200, "success", "抄送来源记录已被移除！", null);
        }
        //将审批数据进行处理
        List<TempApvEntity> apvRecords = this.optimizeApvRecord(waitApvRecords,null);
//        //最后进行筛选，已处理，未处理，全部
//        Iterator<TempApvEntity> iterator = apvRecords.iterator();
//        while (iterator.hasNext()) {
//            TempApvEntity next = iterator.next();
//        }
        PageResult<TempApvEntity> result = PageUtil.getPageResult(new PageInfo<TempApvEntity>(apvRecords));
        return new CommonResult(200, "success", "获取数据成功！", result);

    }
    //将待审批数据进行处理
    private List<TempApvEntity> optimizeApvRecord(List<DApvRecord> dApvRecords,String userId) {
        if(dApvRecords == null && dApvRecords.size() == 0){
            return null;
        }
        List<TempApvEntity> apvRecords = new ArrayList<>();
        Iterator<DApvRecord> iterator = dApvRecords.iterator();
        while (iterator.hasNext()){
            DApvRecord first = iterator.next();
            if(first.getIsStart() == 1){
                //第一级审批人
                TempApvEntity tempApvEntity = new TempApvEntity();
                tempApvEntity.setPkApprovalId(first.getPkRecordId());
                tempApvEntity.setApprovalType(first.getApprovalType());
                tempApvEntity.setApvtypeName(first.getApvtypeName());
                tempApvEntity.setApplyPeople(first.getApplyPeople());
                tempApvEntity.setSponsor(first.getSponsor());
                tempApvEntity.setStartTime(first.getStartTime());
                tempApvEntity.setApprovalPeople1(first.getApvApproval());
                tempApvEntity.setApvDate1(first.getApvDate());
                tempApvEntity.setApvResult1(first.getApvResult());
                tempApvEntity.setApvReason1(first.getApvReason());
                //来源ID
                tempApvEntity.setSourceId(first.getSourceId());
                tempApvEntity.setApprovalPeople2(first.getNextApproval());
                //将该审批记录移除
                iterator.remove();
                apvRecords.add(tempApvEntity);
            }
        }
        //二级审批人
        Iterator<TempApvEntity> resultIterator = apvRecords.iterator();
        while (resultIterator.hasNext()){
            TempApvEntity tempApvEntity = resultIterator.next();
            if("0".equals(tempApvEntity.getApprovalPeople2())){
                //说明没有下级审批了
                continue;
            }
            Iterator<DApvRecord> secondIterator = dApvRecords.iterator();
            while (secondIterator.hasNext()){
                DApvRecord second = secondIterator.next();
                if(second.getPkRecordId().equals(tempApvEntity.getApprovalPeople2())){
                    tempApvEntity.setApprovalPeople2(second.getApvApproval());
                    tempApvEntity.setApvDate2(second.getApvDate());
                    tempApvEntity.setApvResult2(second.getApvResult());
                    tempApvEntity.setApvReason2(second.getApvReason());
                    tempApvEntity.setApprovalPeople3(second.getNextApproval());
                    //将该审批记录移除
                    secondIterator.remove();
                }
            }
            if("0".equals(tempApvEntity.getApprovalPeople3())){
                //说明没有下级审批了
                continue;
            }
            Iterator<DApvRecord> thirdIterator = dApvRecords.iterator();
            while (thirdIterator.hasNext()){
                DApvRecord third = thirdIterator.next();
                if(third.getPkRecordId().equals(tempApvEntity.getApprovalPeople3())){
                    tempApvEntity.setApprovalPeople3(third.getApvApproval());
                    tempApvEntity.setApvDate3(third.getApvDate());
                    tempApvEntity.setApvResult3(third.getApvResult());
                    tempApvEntity.setApvReason3(third.getApvReason());
                    tempApvEntity.setApprovalPeople4(third.getNextApproval());
                    //将该审批记录移除
                    thirdIterator.remove();
                }
            }
            if("0".equals(tempApvEntity.getApprovalPeople4())){
                //说明没有下级审批了
                continue;
            }
            Iterator<DApvRecord> fourthIterator = dApvRecords.iterator();
            while (fourthIterator.hasNext()){
                DApvRecord fourth = fourthIterator.next();
                if(fourth.getPkRecordId().equals(tempApvEntity.getApprovalPeople4())){
                    tempApvEntity.setApprovalPeople4(fourth.getApvApproval());
                    tempApvEntity.setApvDate4(fourth.getApvDate());
                    tempApvEntity.setApvResult4(fourth.getApvResult());
                    tempApvEntity.setApvReason4(fourth.getApvReason());
                    tempApvEntity.setApprovalPeople5(fourth.getNextApproval());
                    //将该审批记录移除
                    fourthIterator.remove();
                }
            }
            if("0".equals(tempApvEntity.getApprovalPeople5())){
                //说明没有下级审批了
                continue;
            }
            Iterator<DApvRecord> fifthIterator = dApvRecords.iterator();
            while (fifthIterator.hasNext()){
                DApvRecord fifth = fifthIterator.next();
                if(fifth.getPkRecordId().equals(tempApvEntity.getApprovalPeople5())){
                    tempApvEntity.setApprovalPeople5(fifth.getApvApproval());
                    tempApvEntity.setApvDate5(fifth.getApvDate());
                    tempApvEntity.setApvResult5(fifth.getApvResult());
                    tempApvEntity.setApvReason5(fifth.getApvReason());
                    //将该审批记录移除
                    fifthIterator.remove();
                }
            }
        }
        if(!StringUtils.isEmpty(userId)){
            Iterator<TempApvEntity> lastIterator = apvRecords.iterator();
            while (lastIterator.hasNext()){
                TempApvEntity last = lastIterator.next();
                int prompt = 0;
                //判断该流程审批是否到操作用户
                if(userId.equals(last.getApprovalPeople1())
                        && last.getApvDate1() == null){
                    prompt = 1;
                }else if(userId.equals(last.getApprovalPeople2())
                        && last.getApvDate2() == null
                        && last.getApvDate1() != null){
                    prompt = 1;
                }else if(userId.equals(last.getApprovalPeople3())
                        && last.getApvDate3() == null
                        && last.getApvDate2() != null){
                    prompt = 1;
                }else if(userId.equals(last.getApprovalPeople4())
                        && last.getApvDate4() == null
                        && last.getApvDate3() != null){
                    prompt = 1;
                }else if(userId.equals(last.getApprovalPeople5())
                        && last.getApvDate5() == null
                        && last.getApvDate4() != null){
                    prompt = 1;

                }
                last.setPrompt(prompt);
            }
        }
        //整个流程审批状态
        apvRecords = this.handleApvStatus(apvRecords);
        return apvRecords;
    }

    /**
     *
     * @param apvRecords
     * @return
     */
    private List<TempApvEntity> handleApvStatus(List<TempApvEntity> apvRecords) {
        Iterator<TempApvEntity> iterator = apvRecords.iterator();
        //0代表未完成审批的
        while (iterator.hasNext()) {
            TempApvEntity next = iterator.next();
            if(!StringUtils.isEmpty(next.getApprovalPeople1())){
                if(next.getApvDate1()!= null){
                    if(!StringUtils.isEmpty(next.getApprovalPeople2())){
                        if(next.getApvDate2()!= null){
                            if(!StringUtils.isEmpty(next.getApprovalPeople3())){
                                if(next.getApvDate3()!= null){
                                    if(!StringUtils.isEmpty(next.getApprovalPeople4())){
                                        if(next.getApvDate4()!= null){
                                            if(!StringUtils.isEmpty(next.getApprovalPeople5())){
                                                if(next.getApvDate5()!= null){
                                                    next.setApvStatus(1);
                                                }else {
                                                    next.setApvStatus(0);
                                                }
                                            }else {
                                                next.setApvStatus(1);
                                            }
                                        }else {
                                            next.setApvStatus(0);
                                        }
                                    }else {
                                        next.setApvStatus(1);
                                    }
                                }else {
                                    next.setApvStatus(0);
                                }
                            }else {
                                next.setApvStatus(1);
                            }
                        }else {
                            next.setApvStatus(0);
                        }
                    }else {
                        next.setApvStatus(1);
                    }
                }else {
                    next.setApvStatus(0);
                }
            }else {
                next.setApvStatus(1);
            }
        }
        return apvRecords;
    }

    /**
     * 审批设置
     *
     * @return 修改结果
     */
    @Override
    public CommonResult approvalSet() {
        /**
         * 审批分组
         */
        TApvGroup group = new TApvGroup();
        List<TApvGroup> groupList = tApvGroupMapper.selectAllPage(group);
        /**
         * 审批类型
         */
        TApvApvtype tApvApvtype = new TApvApvtype();
        List<TApvApvtype> tApvTypeList = tApvApvtypeMapper.selectAllPage(tApvApvtype);
        if(tApvTypeList.size() > 0){
            /**
             * 处理文件路径
             */
            Iterator<TApvApvtype> it = tApvTypeList.iterator();
            while(it.hasNext()){
                StringBuffer filePath = new StringBuffer();
                TApvApvtype obj = it.next();
                filePath.append("http://"+webIp+":"+serverPort+"/img/"+obj.getIconPath());
                obj.setIconPath(filePath.toString());
            }
        }
        JSONObject resultJson = new JSONObject();
        resultJson.put("groupList", groupList);
        resultJson.put("typeList", tApvTypeList);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
    /**
     * 该审批流程的详情
     *
     * @return 修改结果
     */
    @Override
    public CommonResult waitApvDetail(String param) {
        JSONObject json = JSON.parseObject(param);
        //查询条件
        String approvalType = JsonUtil.getStringParam(json, "approvalType");
        String sourceId = JsonUtil.getStringParam(json, "sourceId");
        JSONObject resultJson = new JSONObject();
        if("1".equals(approvalType)){
            //请假申请
        }else if("2".equals(approvalType)){
            //补卡申请
        }else if("3".equals(approvalType)){
            //外出申请
        }else if("4".equals(approvalType)){
            //加班申请
        }else if("5".equals(approvalType)){
            //调班申请
        }else if("6".equals(approvalType)){
            //报销申请
        }else if("7".equals(approvalType)){
            //采购申请
        }else if("8".equals(approvalType)){
            //调动申请
        }else if("9".equals(approvalType)){
            //招聘申请
        }else if("10".equals(approvalType)){
            //调薪申请
        }else if("11".equals(approvalType)){
            //离职申请
        }else if("12".equals(approvalType)){
            //入职申请
            TStaffEntry tStaffEntry = tStaffEntryMapper.selectByPkId2(sourceId);
            resultJson.put("source", tStaffEntry);
        }else if("13".equals(approvalType)){
            //转正申请
        }else if("14".equals(approvalType)){
            //印章申请
        }else if("15".equals(approvalType)){
            //证照申请
        }
        return new CommonResult(200, "success", "获取审批流程来源详情成功", resultJson);

    }
    /**
     * 审批
     *
     * @return 修改结果
     */
    @Transactional()
    @Override
    public CommonResult approval(HttpSession session,HttpServletRequest request,String param) {
        String token = TokenUtil.getRequestToken(request);
        if(StringUtils.isEmpty(token)){
            return new CommonResult(444, "error", "请在请求头传入token", null);
        }
        SysToken tokenEntity = tSysTokenMapper.findByToken(token);
        if(tokenEntity == null){
            return new CommonResult(445, "error", "token已失效，请重新登录后再试", null);
        }
//        ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
        JSONObject json = JSON.parseObject(param);
        //查询条件
        String sourceId = JsonUtil.getStringParam(json, "sourceId");
        String apvResult = JsonUtil.getStringParam(json, "apvResult");
        int resultInt = Integer.parseInt(apvResult);
        String apvReason = JsonUtil.getStringParam(json, "apvReason");
        String approvalType = JsonUtil.getStringParam(json, "approvalType");
        List<DApvRecord> apvRecords = tApvApprovalMapper.selectApvRecordBySourceId_UserId(approvalType,sourceId,tokenEntity.getFkUserId());
        if(apvRecords != null && apvRecords.size() > 0){
            StringBuffer stringBuffer = new StringBuffer();
            DApvRecord apvRecord = apvRecords.get(0);
            if(apvRecord.getApvResult() != null && !StringUtils.isEmpty(apvRecord.getApvResult().toString().trim())){
                //说明此前已审批过了该流程，可更新审批结果
                stringBuffer.append("已审批过该流程！");
                apvRecord.setApvDate(new Date());
                apvRecord.setApvResult(resultInt);
                apvRecord.setApvReason(apvReason);
                int j = tApvApprovalMapper.updateApvRecord(apvRecord);
                if(j > 0){
                    stringBuffer.append("审批结果更新成功!");
                }else {
                    stringBuffer.append("审批结果更新失败!");
                    return new CommonResult(444, "error", stringBuffer.toString(), null);
                }
            }else {
                //未进行过该审批
                apvRecord.setApvDate(new Date());
                apvRecord.setApvResult(resultInt);
                apvRecord.setApvReason(apvReason);
                int j = tApvApprovalMapper.updateApvRecord(apvRecord);
                if(j > 0){
                    stringBuffer.append("审批成功!");
                }else {
                    stringBuffer.append("审批失败!");
                    return new CommonResult(444, "error", stringBuffer.toString(), null);
                }
            }
            if(resultInt == 0){
                //审批驳回，后续审批就不用进行了
                //将该审批流程的所有审批记录的is_ing该为0
                int i = tApvApprovalMapper.updateIsIngBySourceId(sourceId);
            }
            if("0".equals(apvRecord.getNextApproval()) && resultInt == 1){
                //将该审批流程的所有审批记录的is_ing该为0
                int i = tApvApprovalMapper.updateIsIngBySourceId(sourceId);
                stringBuffer = this.complateAPV(approvalType,sourceId);
            }
            return new CommonResult(200, "success", stringBuffer.toString(), null);
        }else {
            return new CommonResult(445, "error", "未查询到您所需的审批流程！", null);
        }
    }

    @Override
    public TApvApproval selectApvSet(String pk_apv_id, String approvalType, int dataType,int isStart) {
        return tApvApprovalMapper.selectApvSet(pk_apv_id,approvalType,dataType,isStart);
    }
    /**
     * 查询某审批类型的默认抄送人
     */
    @Override
    public List<TApvApproval> selectAllPage(TApvApproval tApvApproval) {
        return tApvApprovalMapper.selectAllPage(tApvApproval);
    }
    /**
     * 审批流程的详情
     *
     * @return 修改结果
     */
    @Override
    public CommonResult apvProcessDetail(String param) {
        JSONObject jsonObject = JSON.parseObject(param);
        JSONObject resultJson = new JSONObject();
        String firstApvRecordId = String.valueOf(jsonObject.get("apvId"));
        List<DApvRecord> dApvRecordList = this.optimizeApvProcessDetail(firstApvRecordId);
        resultJson.put("apvList", dApvRecordList);
        /**
         * 处理抄送人,firstApvRecordId
         */
        DCcRecord selectEntity = new DCcRecord();
        selectEntity.setFirstApvrecordId(firstApvRecordId);
        List<DCcRecord> dCcRecords = dCcRecordMapper.selectAllPage(selectEntity);
        resultJson.put("csrList", dCcRecords);
        return new CommonResult(200, "success", "获取审批流程信息成功", resultJson);
    }

    /**
     * 根据第一级审批id查询出后续流程，并进行处理后返回给前端
     * @param firstApvRecordId
     * @return
     */
    private List<DApvRecord> optimizeApvProcessDetail(String firstApvRecordId) {
        List<DApvRecord> resultList = new ArrayList<>();
        String pk_apv_id = firstApvRecordId;
        int num = 1;
        boolean flag = true;
        while (flag) {
            DApvRecord apvRecord = tApvApprovalMapper.selectApvRecordByPkId(pk_apv_id);
            if (apvRecord != null) {
                apvRecord.setIsStart(num);
                //循环次数加1
                num ++;
                //
                pk_apv_id = apvRecord.getNextApproval();
                /**
                 * 二、再判断下级审批
                 */
                if (pk_apv_id.equals("0")) {
                    /**
                     * 说明为最后一级审批，跳出循环
                     */
                    flag = false;
                }
                resultList.add(apvRecord);
            }else {
                flag = false;
            }
        }
        return resultList;
    }

    @Override
    public int insertApvRecordBatch(List<DApvRecord> apvRecordList) {
        return tApvApprovalMapper.insertApvRecordBatch(apvRecordList);
    }

    private StringBuffer complateAPV(String approvalType,String sourceId) {
        StringBuffer stringBuffer = new StringBuffer();
        //如果该审批已完成最后流程并通过，
        if("12".equals(approvalType)){
            //且为入职审批即approvalType=12
            TStaffEntry  tStaffEntry = tStaffEntryMapper.selectByPkId(sourceId);
            //先查询该员工是否已被录入过
            TStaffInfo queryInfo = new TStaffInfo();
            queryInfo.setPkStaffId(sourceId);
            TStaffInfo selectInfo = tStaffInfoMapper.selectByPkId2(queryInfo);
            if(selectInfo == null){
                /**
                 * 添加员工信息
                 */
                TStaffInfo tStaffInfo = new TStaffInfo();
                tStaffInfo.setPkStaffId(tStaffEntry.getPkEntryId());
                tStaffInfo.setStaffName(tStaffEntry.getStaffName());
                tStaffInfo.setStaffSex(tStaffEntry.getStaffSex());
                tStaffInfo.setStaffAge(tStaffEntry.getStaffAge());
                tStaffInfo.setStaffStatus(1);
                tStaffInfo.setFkDeptId(tStaffEntry.getStaffDept());
                tStaffInfo.setFkPositionId(tStaffEntry.getStaffPosition());
                tStaffInfo.setFkWorkaddressId(tStaffEntry.getWorkAddress());
                tStaffInfo.setEntryTime(tStaffEntry.getEntryTime());
                tStaffInfo.setRecruitWay(tStaffEntry.getRecruitWay());
                tStaffInfo.setIdType(tStaffEntry.getIdType());
                tStaffInfo.setIdCard(tStaffEntry.getIdCard());
                tStaffInfo.setStaffEmail(tStaffEntry.getEmail());
                tStaffInfo.setStaffTel(tStaffEntry.getStaffTel());
                //其他信息需要后期添加
                int i = tStaffInfoMapper.insertSelective(tStaffInfo);
                if(i > 0){
                    stringBuffer.append("入职审批通过，已录入员工信息！");
                }
            }
            //先查询该员工是否已有系统账户
            TSysUser selectUser = tSysUserMapper.selectById(tStaffEntry.getPkEntryId());
            if(selectUser == null){
                /**
                 * 添加系统用户
                 */
                TSysUser tSysUser = new TSysUser();
                tSysUser.setPkUserId(tStaffEntry.getPkEntryId());
                tSysUser.setUsername(tStaffEntry.getStaffName());
                String password = PasswordEncryptUtils.MyPasswordEncryptUtil(null,"123456");
                tSysUser.setPassword(password);
                tSysUser.setEmail(tStaffEntry.getEmail());
                tSysUser.setTel(tStaffEntry.getStaffTel());
                tSysUser.setIdcard(tStaffEntry.getIdCard());
                tSysUser.setFullName(tStaffEntry.getStaffName());
                tSysUser.setWorkPosition(tStaffEntry.getStaffPosition());
                tSysUser.setEnableStatus("1");
                tSysUser.setCreateTime(new Date());
                tSysUser.setModifyTime(new Date());
                int j = tSysUserMapper.insertSelective(tSysUser);
                if(j > 0){
                    stringBuffer.append("已创建系统账户！");
                }
            }
        }else if ("13".equals(approvalType)){
            //转正申请
            //修改审批状态
            TStaffZz tStaffZz = new TStaffZz();
            tStaffZz.setPkZzId(sourceId);
            tStaffZz.setSjzzTime(new Date());
            tStaffZz.setApvStatus(1);
            int i = tStaffZzMapper.updateByPkId(tStaffZz);
            if(i > 0){
                stringBuffer.append("转正审批通过，已录入员工转正信息！");
            }
        }else if ("11".equals(approvalType)){
            //离职申请
            //修改审批状态-离职信息和员工信息里
            TStaffQuit tStaffQuit = new TStaffQuit();
            tStaffQuit.setPkQuitId(sourceId);
            tStaffQuit.setApvTime(new Date());
            tStaffQuit.setQuitStatus(1);
            int i = tStaffQuitMapper.updateByPkId(tStaffQuit);
            TStaffInfo tStaffInfo = new TStaffInfo();
            tStaffInfo.setPkStaffId(sourceId);
            tStaffInfo.setStaffStatus(0);
            int j = tStaffInfoMapper.updateByPkId(tStaffInfo);
            if(j > 0){
                stringBuffer.append("离职审批通过，已保存员工离职信息！");
            }
        } else if ("8".equals(approvalType)){
            //调动申请
            //修改审批状态-调动信息
            TStaffReassign updateEntity = new TStaffReassign();
            updateEntity.setPkReassignId(sourceId);
            updateEntity.setEndTime(new Date());
            updateEntity.setApvStatus(1);
            int i = tStaffReassignMapper.updateByPkId(updateEntity);
            //查询出调动信息
            TStaffReassign resultEntity = tStaffReassignMapper.selectByPkId(sourceId);
            //将员工名单信息改为调动后的
            TStaffInfo tStaffInfo = new TStaffInfo();
            tStaffInfo.setPkStaffId(resultEntity.getFkStaffId());
            tStaffInfo.setFkDeptId(resultEntity.getReassignDeptId());
            tStaffInfo.setFkPositionId(resultEntity.getReassignPosition());
            tStaffInfo.setFkWorkaddressId(resultEntity.getReassignAddress());
            int j = tStaffInfoMapper.updateByPkId(tStaffInfo);
            if(j > 0){
                stringBuffer.append("调动审批通过，已更新员工档案信息！");
            }
        }
        return stringBuffer;
    }

}
    
