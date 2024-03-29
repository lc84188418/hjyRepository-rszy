package com.hjy.cloud.t_apv.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.common.task.ObjectAsyncTask;
import com.hjy.cloud.common.utils.ApvUtil;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_apv.dao.*;
import com.hjy.cloud.t_apv.entity.*;
import com.hjy.cloud.t_apv.enums.ApprovaltypeEnum;
import com.hjy.cloud.t_apv.result.ApprovalAddResult;
import com.hjy.cloud.t_apv.result.ApprovalSource;
import com.hjy.cloud.t_apv.result.SourceDetailResult;
import com.hjy.cloud.t_apv.service.TApvApprovalService;
import com.hjy.cloud.t_staff.dao.*;
import com.hjy.cloud.t_staff.entity.*;
import com.hjy.cloud.t_system.dao.TSysTokenMapper;
import com.hjy.cloud.t_system.dao.TSysUserMapper;
import com.hjy.cloud.t_system.entity.ActiveUser;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.t_system.entity.TSysUser;
import com.hjy.cloud.utils.*;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.utils.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
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
//@CacheConfig(cacheNames = "TApvApprovalServiceImpl_CacheConfig")
public class TApvApprovalServiceImpl implements TApvApprovalService {

    @Resource
    private TApvApprovalMapper tApvApprovalMapper;
    @Resource
    private DCcRecordMapper dCcRecordMapper;
    @Resource
    private DApvRecordMapper apvRecordMapper;
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

    public static String webIp = null;
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
        if(!apvList1.isEmpty() && apvList1.size() > 0 ){
            /**
             * 一、审批人先去重
             * 去掉审批id中重复的
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
            //设置末尾节点的isEnding=1和下级审批，0代表无下级审批了
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
                stringBuffer.append("未选择抄送人");
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
        //批量添加
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
//    @Cacheable(key = "'waitApv'",value = {"valueName"})
    @Override
    public CommonResult<PageResult<DApvRecord>> waitApv(int pageNum, int pageSize) {
        DApvRecord select = new DApvRecord();
        select.setIsIng(1);
        select.setIsStart(1);
        PageHelper.startPage(pageNum, pageSize);
        List<DApvRecord> apvRecords = apvRecordMapper.selectAllEntity(select);
        //将第一级审批的后续流程放入到次级流程中
        List<DApvRecord> recordResultList = ObjectAsyncTask.handleApvRecord(apvRecords);
        PageResult<DApvRecord> result = PageUtil.getPageResult(new PageInfo<DApvRecord>(recordResultList));
        return new CommonResult(200, "success", "获取已审批列表数据成功！", result);
    }

//    @Cacheable(key = "'ApvComplete'",value = {"valueName"})
    @Override
    public CommonResult<PageResult<DApvRecord>> ApvComplete(int pageNum, int pageSize) {
        DApvRecord select = new DApvRecord();
        select.setIsIng(0);
        select.setIsStart(1);
        PageHelper.startPage(pageNum, pageSize);
        List<DApvRecord> apvRecords = apvRecordMapper.selectAllEntity(select);
        //将审批数据进行处理
        List<DApvRecord> recordResultList = ObjectAsyncTask.handleApvRecord(apvRecords);
        PageResult<DApvRecord> result = PageUtil.getPageResult(new PageInfo<DApvRecord>(recordResultList));
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
        if(tokenEntity == null){
            return new CommonResult().ErrorResult("token已失效！",null);
        }
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
//        List<DApvRecord> apvRecords = this.handleApvRecord(waitApvRecords);
        JSONObject resultJson = new JSONObject();
        resultJson.put("waitApvRecords", apvRecords);
        return new CommonResult(200, "success", "获取数据成功！", resultJson);

    }

    /**
     * 员工端-我发起的申请
     * @param session
     * @param request
     * @param param
     * @return
     */
    @Override
    public CommonResult<PageResult<DApvRecord>> apvRecordListSponsor(HttpSession session, HttpServletRequest request, String param) {
        SysToken token = tSysTokenMapper.findByToken(TokenUtil.getRequestToken(request));
        if(token == null){
            return new CommonResult(444, "error", "无法验证当前用户信息，请刷新或重新登录后再试", null);
        }
        JSONObject json = JSON.parseObject(param);
        //实体数据
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        //分页记录条数
        int pageNum = 1;
        int pageSize = 10;
        if (pageNumStr != null) {
            pageNum = Integer.parseInt(pageNumStr);
        }
        if (pageSizeStr != null) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        DApvRecord select = new DApvRecord();
        select.setSponsorId(token.getFkUserId());
        select.setIsStart(1);
        PageHelper.startPage(pageNum, pageSize);
        List<DApvRecord> apvRecords = apvRecordMapper.selectAllEntity(select);
        //将审批数据进行处理
        List<DApvRecord> recordResultList = ObjectAsyncTask.handleApvRecord(apvRecords);
        PageResult<DApvRecord> result = PageUtil.getPageResult(new PageInfo<DApvRecord>(recordResultList));
        return new CommonResult(200, "success", "获取我发起的审批列表数据成功！", result);
    }

    /**
     * 员工端-抄送给我的
     * @param session
     * @param request
     * @param param
     * @return
     */
    @Cacheable(key = "'CCToMe'",value = {"valueName"})
    @Override
    public CommonResult<PageResult<DApvRecord>> apvRecordListCCToMe(HttpSession session, HttpServletRequest request, String param) {
        SysToken token = tSysTokenMapper.findByToken(TokenUtil.getRequestToken(request));
        JSONObject json = JSON.parseObject(param);
        //实体数据
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        int pageNum = 1;
        int pageSize = 10;
        if (pageNumStr != null) {
            pageNum = Integer.parseInt(pageNumStr);
        }
        if (pageSizeStr != null) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        PageHelper.startPage(pageNum, pageSize);
        //1.first_record_id
        List<String> first_record_ids = apvRecordMapper.selectFirstRecordIdsByFkStaffId(token.getFkUserId());
        if(first_record_ids != null && first_record_ids.size() > 0){
            List<DApvRecord> dApvRecords = apvRecordMapper.selectAllByIds(first_record_ids);
            //将审批数据进行处理
            List<DApvRecord> recordResultList = ObjectAsyncTask.handleApvRecord(dApvRecords);
            PageResult<DApvRecord> result = PageUtil.getPageResult(new PageInfo<DApvRecord>(recordResultList));
            return new CommonResult(200, "success", "获取抄送记录成功！", result);
        }else {
            return new CommonResult().ErrorResult("你暂未有抄送记录",null);
        }
    }

    /**
     * 将待审批数据进行处理
     * @param dApvRecords
     * @param userId
     * @return
     */
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
     * 处理整个流程的审批状态，前端根据这个好做提示
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
                webIp = SystemUtil.getWebIp(webIp);
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
    public CommonResult<ApprovalSource> waitApvDetail(String param) {
        JSONObject json = JSON.parseObject(param);
        //查询条件
        String approvalType = JsonUtil.getStringParam(json, "approvalType");
        String sourceId = JsonUtil.getStringParam(json, "sourceId");
        ApprovalSource result = new ApprovalSource();
        //空的就是还没写的申请
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
            TStaffReassign query = new TStaffReassign();
            query.setPkReassignId(sourceId);
            List<TStaffReassign> reassigns = this.tStaffReassignMapper.selectAllPage(query);
            TStaffReassign reassign = new TStaffReassign();
            if(reassigns != null && reassigns.size() > 0){
                reassign = reassigns.get(0);
            }
            SourceDetailResult resultBf = ApvUtil.handleData(reassign);
            result.setSource(resultBf);
        }else if("9".equals(approvalType)){
            //招聘申请
        }else if("10".equals(approvalType)){
            //调薪申请
        }else if("11".equals(approvalType)){
            //离职申请
            TStaffQuit query = new TStaffQuit();
            query.setPkQuitId(sourceId);
            List<TStaffQuit> quits = this.tStaffQuitMapper.selectAllPage(query);
            TStaffQuit quit = new TStaffQuit();
            if(quits != null && quits.size() > 0){
                quit = quits.get(0);
            }
            SourceDetailResult resultBf = ApvUtil.handleData(quit);
            result.setSource(resultBf);
        }else if("12".equals(approvalType)){
            //入职申请
            TStaffEntry tStaffEntry = tStaffEntryMapper.selectByPkId2(sourceId);
            SourceDetailResult resultBf = ApvUtil.handleData(tStaffEntry);
            result.setSource(resultBf);
        }else if("13".equals(approvalType)){
            //转正申请
            TStaffZz query = new TStaffZz();
            query.setPkZzId(sourceId);
            List<TStaffZz> zzs = this.tStaffZzMapper.selectAllPage(query);
            TStaffZz zz = new TStaffZz();
            if(zzs != null && zzs.size() > 0){
                zz = zzs.get(0);
            }
            SourceDetailResult resultBf = ApvUtil.handleData(zz);
            result.setSource(resultBf);
        }else if("14".equals(approvalType)){
            //印章申请
        }else if("15".equals(approvalType)){
            //证照申请
        }
        return new CommonResult(200, "success", "获取审批流程来源详情成功", result);

    }

    /**
     * 用户-审批
     *
     * @return 修改结果
     */
//    @Cacheable(key = "'ApvComplete'",value = {"valueName"})
    @Transactional()
    @Override
    public CommonResult approval(HttpSession session,HttpServletRequest request,String param) {
        String token = TokenUtil.getRequestToken(request);
        if(StringUtils.isEmpty(token)){
            return new CommonResult(444, "error", "请在请求头传入token", null);
        }
        SysToken tokenEntity = tSysTokenMapper.findByToken(token);
        if(tokenEntity == null){
            return new CommonResult(444, "error", "token已失效，请重新登录后再试", null);
        }
        StringBuffer stringBuffer = new StringBuffer();
//        ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
        JSONObject json = JSON.parseObject(param);
        //查询条件
        String sourceId = JsonUtil.getStringParam(json, "sourceId");
        if(StringUtils.isEmpty(sourceId)){
            stringBuffer.append("sourceId为空了请检查！");
        }
        int resultInt = 1;
        String apvResult = JsonUtil.getStringParam(json, "apvResult");
        if(StringUtils.isEmpty(apvResult)){
            stringBuffer.append("前端没传apvResult，出问题别找后端！");
        }else {
            resultInt = Integer.parseInt(apvResult);
        }
        String apvReason = JsonUtil.getStringParam(json, "apvReason");
        String approvalType = JsonUtil.getStringParam(json, "approvalType");
        if(StringUtils.isEmpty(approvalType)){
            stringBuffer.append("前端没传approvalType，出问题别找后端！");
        }
        /**
         * 验证当前审批是否为该自己审批了
         */
        DApvRecord select = new DApvRecord();
        select.setApprovalType(approvalType);
        select.setSourceId(sourceId);
        select.setIsIng(1);
        List<DApvRecord> apvRecords = this.apvRecordMapper.selectAllEntity(select);
        if(apvRecords != null && apvRecords.size() > 0){
            Iterator<DApvRecord> iterator = apvRecords.iterator();
            boolean thisApprovalWhetherArrivalMe = true;
            DApvRecord myapvRecord = new DApvRecord();
            if(apvRecords.size() > 1){
                while (iterator.hasNext()){
                    DApvRecord apvRecord = iterator.next();
                    if(tokenEntity.getFkUserId().equals(apvRecord.getApvApproval())){
                        myapvRecord = apvRecord;
                        //判断该个审批流程是否到自己了
                        for (DApvRecord record : apvRecords) {
                            if(record.getNextApproval().equals(apvRecord.getPkRecordId())){
                                //判断该个审批流程是否到自己了,有结果，且不能是拒绝，若为驳回后续不用再审批
                                if(null == record.getApvResult()){
                                    thisApprovalWhetherArrivalMe = false;
                                    stringBuffer.append("该审批流程还未流转给你，请等待上一级审批过后进行操作！");
                                }else if(2 == record.getApvResult()){
                                    thisApprovalWhetherArrivalMe = false;
                                    stringBuffer.append("该审批流程上一级已驳回，你无需操作！");
                                }
                            }
                        }
                    }
                }
            }else if(apvRecords.size() == 1){
                myapvRecord = apvRecords.get(0);
            }
            if(!thisApprovalWhetherArrivalMe){
                return new CommonResult().ErrorResult(stringBuffer.toString(),null);
            }
            if(myapvRecord.getApvResult() != null && !StringUtils.isEmpty(myapvRecord.getApvResult().toString().trim())){
                //说明此前已审批过了该流程，可更新审批结果
                stringBuffer.append("已审批过该流程！");
                myapvRecord.setApvDate(new Date());
                myapvRecord.setApvResult(resultInt);
                myapvRecord.setApvReason(apvReason);
                int j = tApvApprovalMapper.updateApvRecord(myapvRecord);
                stringBuffer.append("审批结果更新成功!");
            }else {
                //未进行过该审批
                myapvRecord.setApvDate(new Date());
                myapvRecord.setApvResult(resultInt);
                myapvRecord.setApvReason(apvReason);
                int j = tApvApprovalMapper.updateApvRecord(myapvRecord);
                stringBuffer.append("审批成功!");
            }
            if(resultInt == 2){
                //审批驳回，后续审批就不用进行了
                //修改整个审批流程的状态apvStatus=2
                int i = this.apvRecordMapper.updateApvStatusBySourceId(sourceId,2);
                stringBuffer = this.complateAPV(approvalType,sourceId,resultInt);
            }
            if("0".equals(myapvRecord.getNextApproval()) && resultInt == 1){
                //修改整个审批流程的状态apvStatus=1
                int i = this.apvRecordMapper.updateApvStatusBySourceId(sourceId,1);
                stringBuffer = this.complateAPV(approvalType,sourceId,resultInt);
            }
            return new CommonResult(200, "success", stringBuffer.toString(), null);
        }else {
            return new CommonResult(444, "error", "未查询到相关审批流程！", null);
        }
    }

    /**
     * 根据条件查询审批流程中某节点的审批信息
     * @param pk_apv_id 该节点审批id
     * @param approvalType 审批类型
     * @param dataType
     * @param isStart 开始节点的标志
     * @return
     */
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
        /**
         * 两种方式采用一种，目前  前端对接采用的第一种
         */
        //1
        List<DApvRecord> recordResultList = this.optimizeApvProcessDetail(firstApvRecordId);

//        //2后改的方式
//        DApvRecord select = new DApvRecord();
//        select.setPkRecordId(firstApvRecordId);
//        List<DApvRecord> apvRecords = apvRecordMapper.selectAllEntity(select);
//        //将审批数据进行处理
//        List<DApvRecord> recordResultList = ObjectAsyncTask.handleApvRecord(apvRecords);

        resultJson.put("apvList", recordResultList);
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
            DApvRecord apvRecord = apvRecordMapper.selectByPkId(pk_apv_id);
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

    /**
     *
     * @param approvalType 审批类型
     * @param sourceId
     * @param resultInt 审批结果 1通过 0拒绝
     * @return
     */
    private StringBuffer complateAPV(String approvalType,String sourceId,int resultInt) {
        StringBuffer stringBuffer = new StringBuffer();
        //如果该审批已完成最后流程并通过，
        /**
         * 入职审批 即approvalType=12
         */
        if(ApprovaltypeEnum.Type_12.getCode().equals(approvalType)){
            if(1 == resultInt){
                TStaffEntry  tStaffEntry = tStaffEntryMapper.selectByPkId(sourceId);
                //1.修改员工入职的状态
                tStaffEntry.setStatus(1);
                tStaffEntryMapper.updateByPkId(tStaffEntry);

                //2.先查询该员工是否已被录入过,通过证件号
                TStaffInfo queryInfo = new TStaffInfo();
                queryInfo.setIdCard(sourceId);
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
                    //员工状态，0已离职1刚入职2已转正，入职审批通过后状态为1
                    tStaffInfo.setStaffStatus(1);
                    tStaffInfo.setFkDeptId(tStaffEntry.getStaffDept());
                    tStaffInfo.setFkPositionId(tStaffEntry.getStaffPosition());
                    tStaffInfo.setFkWorkaddressId(tStaffEntry.getWorkAddress());
                    tStaffInfo.setFkHtlxId(tStaffEntry.getFkHtlxId());
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
                //3.先查询该员工是否已有系统账户,通过用户名
                TSysUser query = new TSysUser();
                query.setUsername(tStaffEntry.getStaffName());
                int userCount = tSysUserMapper.selectCountByEntity(query);
                /**
                 * 添加系统用户
                 */
                TSysUser tSysUser = new TSysUser();
                tSysUser.setPkUserId(tStaffEntry.getPkEntryId());
                if(userCount == 0){
                    tSysUser.setUsername(tStaffEntry.getStaffName());
                }else {
                    tSysUser.setUsername(tStaffEntry.getStaffName()+userCount);
                }
                String password = PasswordEncryptUtils.MyPasswordEncryptUtil(null,"123456");
                tSysUser.setPassword(password);
                tSysUser.setEmail(tStaffEntry.getEmail());
                tSysUser.setTel(tStaffEntry.getStaffTel());
                tSysUser.setIdcard(tStaffEntry.getIdCard());
                tSysUser.setFullName(tStaffEntry.getStaffName());
                tSysUser.setEnableStatus("1");
                tSysUser.setCreateTime(new Date());
                tSysUser.setModifyTime(new Date());
                int j = tSysUserMapper.insertSelective(tSysUser);
                if(j > 0){
                    stringBuffer.append("已创建系统账户！");
                }
            }
        }else if (ApprovaltypeEnum.Type_13.getCode().equals(approvalType)){
            //修改审批状态
            TStaffZz tStaffZz = new TStaffZz();
            tStaffZz.setPkZzId(sourceId);
            tStaffZz.setSjzzTime(new Date());
            if(1 == resultInt){
                //转正申请
                //0代表正在审批中，1通过2拒绝
                tStaffZz.setApvStatus(1);
                int i = tStaffZzMapper.updateByPkId(tStaffZz);
                if(i > 0){
                    stringBuffer.append("转正审批通过，已录入员工转正信息！");
                }
                //修改员工档案中的员工状态
                TStaffInfo tStaffInfo = new TStaffInfo();
                tStaffInfo.setPkStaffId(sourceId);
                tStaffInfo.setStaffStatus(2);
                int j = tStaffInfoMapper.updateByPkId(tStaffInfo);
            }else {
                tStaffZz.setApvStatus(2);
                int i = tStaffZzMapper.updateByPkId(tStaffZz);
                if(i > 0){
                    stringBuffer.append("转正审批驳回！");
                }
            }
        }else if ("11".equals(approvalType)){
            /**
             * 这里是因为离职通过和不通过后，进行什么样的操作还未确定需求，暂时不用
             */
            if(1 == resultInt){

            }else {

            }
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
            //修改审批状态-调动信息
            TStaffReassign updateEntity = new TStaffReassign();
            updateEntity.setPkReassignId(sourceId);
            updateEntity.setEndTime(new Date());
            if(1 == resultInt){
                //调动申请
                updateEntity.setApvStatus(1);
                //查询出调动信息
                TStaffReassign resultEntity = tStaffReassignMapper.selectByPkId(sourceId);
                //将员工名单信息改为调动后的
                int j = ObjectAsyncTask.updateReassignData(resultEntity);
                if(j > 0){
                    stringBuffer.append("调动审批通过，已更新员工档案信息！");
                }
            }else {
                updateEntity.setApvStatus(2);
            }
            int i = tStaffReassignMapper.updateByPkId(updateEntity);
        }
        return stringBuffer;
    }

}
    
