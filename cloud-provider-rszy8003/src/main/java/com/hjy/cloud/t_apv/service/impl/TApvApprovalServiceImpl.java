package com.hjy.cloud.t_apv.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.common.entity.DApvRecord;
import com.hjy.cloud.common.task.ObjectAsyncTask;
import com.hjy.cloud.t_apv.dao.TApvApprovalMapper;
import com.hjy.cloud.t_apv.dao.TApvApvtypeMapper;
import com.hjy.cloud.t_apv.dao.TApvGroupMapper;
import com.hjy.cloud.t_apv.entity.TApvApproval;
import com.hjy.cloud.t_apv.entity.TApvApvtype;
import com.hjy.cloud.t_apv.entity.TApvGroup;
import com.hjy.cloud.t_apv.entity.TempApvEntity;
import com.hjy.cloud.t_apv.service.TApvApprovalService;
import com.hjy.cloud.t_dictionary.entity.TDictionaryFile;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_staff.dao.TStaffEntryMapper;
import com.hjy.cloud.t_staff.dao.TStaffInfoMapper;
import com.hjy.cloud.t_staff.entity.TStaffEntry;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.t_system.dao.TSysTokenMapper;
import com.hjy.cloud.t_system.dao.TSysUserMapper;
import com.hjy.cloud.t_system.entity.ActiveUser;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.t_system.entity.TSysUser;
import com.hjy.cloud.utils.*;
import com.hjy.cloud.utils.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.domin.CommonResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
    private TApvApvtypeMapper tApvApvtypeMapper;
    @Resource
    private TApvGroupMapper tApvGroupMapper;
    @Resource
    private TStaffInfoMapper tStaffInfoMapper;
    @Resource
    private TStaffEntryMapper tStaffEntryMapper;
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
    public CommonResult insertPage() {
        JSONObject jsonObject = new JSONObject();
        //审批类型
        List<TApvApvtype> apvtypes = tApvApvtypeMapper.selectAllId_Name();
        jsonObject.put("apvtypes", apvtypes);
        //员工ID
        List<TStaffInfo> staffInfos = tStaffInfoMapper.selectAllId_Name();
        jsonObject.put("staffInfos", staffInfos);
//        //所有末尾的审批流程,
//        List<TApvApproval> approvals = tApvApprovalMapper.selectAllEnding();
//        jsonObject.put("approvals", approvals);
        return new CommonResult(200, "success", "获取数据成功", jsonObject);
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
        int dataType = 1;
        int isStart = 1;
        //查询该审批类型是否已添加过数据
        TApvApproval lastTypeData = tApvApprovalMapper.selectByType(approvalType);
        if(lastTypeData != null){
            //说明之前已经添加过该数据，则添加新的该审批流程
            dataType = lastTypeData.getDataType()+1;
        }
        String newPkId = IDUtils.getUUID();
        List<TApvApproval> idList = new ArrayList<>();
        //审批人主键
        JSONArray jsonArray = jsonObject.getJSONArray("ids");
        String userIdsStr = jsonArray.toString();
        if(userIdsStr.equals("[]")){
            return new CommonResult(444,"error","未选择员工!",null);
        }else {
            idList = JSONObject.parseArray(userIdsStr,TApvApproval.class);
        }
        if(idList.size() > 0 && !idList.get(0).getApprovalPeople().equals("zdy")){
            //自动加上apvStation值为zdy的审批人
            //代表第一审批人，每次进行审批时可自选审批人
            TApvApproval firstApproval = new TApvApproval();
            firstApproval.setApvStation("zdy");
            firstApproval.setIsStart(1);
            idList.add(0,firstApproval);
        }
        /**
         * 后续审批人，
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
        /**
         * 抄送人
         */
        List<TApvApproval> csrList = new ArrayList<>();
        JSONArray csrArray = jsonObject.getJSONArray("csr");
        String csrStr = csrArray.toString();
        if(csrStr.equals("[]")){
            //未选择抄送人
        }else {
            csrList = JSONObject.parseArray(csrStr,TApvApproval.class);
        }
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
        int i = this.tApvApprovalMapper.insertBatch(idList);
        if (i > 0) {
            if(dataType > 1){
                return new CommonResult(201, "success", "该审批另一流程已新增成功！", null);
            }else {
                return new CommonResult(200, "success", "新审批流程添加成功！", null);
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
            return new CommonResult(444, "error", "删除数据失败", null);
        }
    }

    /**
     * 查询所有数据
     *
     * @param param
     * @return
     */
    @Override
    public CommonResult selectAll(String param) {
        JSONObject json = JSON.parseObject(param);
        //查询条件
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        TApvApproval entity = new TApvApproval();

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
        List<TApvApproval> list = this.tApvApprovalMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TApvApproval>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tApvApproval 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TApvApproval tApvApproval) {
        String pkId = tApvApproval.getPkApprovalId();
        TApvApproval entity = this.tApvApprovalMapper.selectByPkId(pkId);
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
        /**
         *
         */
        List<DApvRecord> waitApvRecords = tApvApprovalMapper.waitApv();
        //将待审批数据进行处理
        List<TempApvEntity> apvRecords = this.optimizeApvRecord(waitApvRecords,null);
        JSONObject resultJson = new JSONObject();
        resultJson.put("waitApvRecords", apvRecords);
        return new CommonResult(200, "success", "获取待审批列表数据成功！", resultJson);

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
        //将待审批数据进行处理
        List<TempApvEntity> apvRecords = this.optimizeApvRecord(waitApvRecords,tokenEntity.getFkUserId());
        JSONObject resultJson = new JSONObject();
        resultJson.put("waitApvRecords", apvRecords);
        return new CommonResult(200, "success", "获取数据成功", resultJson);

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
            if(tempApvEntity.getApprovalPeople2().equals("0")){
                //说明没有下级审批了
                continue;
            }
            Iterator<DApvRecord> secondIterator = dApvRecords.iterator();
            while (secondIterator.hasNext()){
                DApvRecord second = secondIterator.next();
                if(tempApvEntity.getApprovalPeople2().equals(second.getPkRecordId())){
                    tempApvEntity.setApprovalPeople2(second.getApvApproval());
                    tempApvEntity.setApvDate2(second.getApvDate());
                    tempApvEntity.setApvResult2(second.getApvResult());
                    tempApvEntity.setApvReason2(second.getApvReason());
                    tempApvEntity.setApprovalPeople3(second.getNextApproval());
                    //将该审批记录移除
                    secondIterator.remove();
                }
            }
            if(tempApvEntity.getApprovalPeople3().equals("0")){
                //说明没有下级审批了
                continue;
            }
            Iterator<DApvRecord> thirdIterator = dApvRecords.iterator();
            while (thirdIterator.hasNext()){
                DApvRecord third = thirdIterator.next();
                if(tempApvEntity.getApprovalPeople3().equals(third.getPkRecordId())){
                    tempApvEntity.setApprovalPeople3(third.getApvApproval());
                    tempApvEntity.setApvDate3(third.getApvDate());
                    tempApvEntity.setApvResult3(third.getApvResult());
                    tempApvEntity.setApvReason3(third.getApvReason());
                    tempApvEntity.setApprovalPeople4(third.getNextApproval());
                    //将该审批记录移除
                    thirdIterator.remove();
                }
            }
            if(tempApvEntity.getApprovalPeople4().equals("0")){
                //说明没有下级审批了
                continue;
            }
            Iterator<DApvRecord> fourthIterator = dApvRecords.iterator();
            while (fourthIterator.hasNext()){
                DApvRecord fourth = fourthIterator.next();
                if(tempApvEntity.getApprovalPeople4().equals(fourth.getPkRecordId())){
                    tempApvEntity.setApprovalPeople4(fourth.getApvApproval());
                    tempApvEntity.setApvDate4(fourth.getApvDate());
                    tempApvEntity.setApvResult4(fourth.getApvResult());
                    tempApvEntity.setApvReason4(fourth.getApvReason());
                    tempApvEntity.setApprovalPeople5(fourth.getNextApproval());
                    //将该审批记录移除
                    fourthIterator.remove();
                }
            }
            if(tempApvEntity.getApprovalPeople5().equals("0")){
                //说明没有下级审批了
                continue;
            }
            Iterator<DApvRecord> fifthIterator = dApvRecords.iterator();
            while (fifthIterator.hasNext()){
                DApvRecord fifth = fifthIterator.next();
                if(tempApvEntity.getApprovalPeople5().equals(fifth.getPkRecordId())){
                    tempApvEntity.setApprovalPeople5(fifth.getApvApproval());
                    tempApvEntity.setApvDate5(fifth.getApvDate());
                    tempApvEntity.setApvResult5(fifth.getApvResult());
                    tempApvEntity.setApvReason5(fifth.getApvReason());
                    //将该审批记录移除
                    fifthIterator.remove();
                }
            }
        }
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
            TStaffEntry tStaffEntry = tStaffEntryMapper.selectByPkId(sourceId);
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
//        ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
        JSONObject json = JSON.parseObject(param);
        //查询条件
        String sourceId = JsonUtil.getStringParam(json, "sourceId");
        String apvResult = JsonUtil.getStringParam(json, "apvResult");
        String apvReason = JsonUtil.getStringParam(json, "apvReason");
        String approvalType = JsonUtil.getStringParam(json, "approvalType");
        DApvRecord apvRecord = tApvApprovalMapper.selectApvRecordBySourceId_UserId(sourceId,tokenEntity.getFkUserId());
        apvRecord.setApvDate(new Date());
        int resultInt = Integer.parseInt(apvResult);
        apvRecord.setApvResult(resultInt);
        apvRecord.setApvReason(apvReason);
        int j = tApvApprovalMapper.updateApvRecord(apvRecord);
        StringBuffer stringBuffer = new StringBuffer();
        if(j > 0){
            stringBuffer.append("审批成功!");
        }else {
            stringBuffer.append("审批失败!");
            return new CommonResult(444, "error", stringBuffer.toString(), null);
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
    }

    @Override
    public TApvApproval selectApvSet(String pk_apv_id, String approvalType, int dataType,int isStart) {
        return tApvApprovalMapper.selectApvSet(pk_apv_id,approvalType,dataType,isStart);
    }
    /**
     * 查询某审批类型的默认抄送人
     */
    @Override
    public List<TApvApproval> selectCsr(String approvalType, int dataType) {
        return tApvApprovalMapper.selectCsr(approvalType, dataType);
    }

    private StringBuffer complateAPV(String approvalType,String sourceId) {
        StringBuffer stringBuffer = new StringBuffer();
        //如果该审批已完成最后流程并通过，且为入职审批即approvalType=12
        if("12".equals(approvalType)){
            TStaffEntry  tStaffEntry = tStaffEntryMapper.selectByPkId(sourceId);
            /**
             * 添加员工信息
             */
            TStaffInfo tStaffInfo = new TStaffInfo();
            String pkId = IDUtils.getUUID();
            tStaffInfo.setPkStaffId(pkId);
            tStaffInfo.setStaffName(tStaffEntry.getStaffName());
            tStaffInfo.setStaffSex(tStaffEntry.getStaffSex());
            tStaffInfo.setStaffAge(tStaffEntry.getStaffAge());
            tStaffInfo.setFkDeptId(tStaffEntry.getStaffDept());
            tStaffInfo.setFkPositionId(tStaffEntry.getStaffPosition());
            tStaffInfo.setEntryTime(tStaffEntry.getEntryTime());
            tStaffInfo.setRecruitWay(tStaffEntry.getRecruitWay());
            tStaffInfo.setIdType(tStaffEntry.getIdType());
            tStaffInfo.setIdCard(tStaffEntry.getIdCard());
            //其他信息需要后期添加
            int i = tStaffInfoMapper.insertSelective(tStaffInfo);
            if(i > 0){
                stringBuffer.append("审批通过，已录入员工信息！");
            }
            /**
             * 添加系统用户
             */
            TSysUser tSysUser = new TSysUser();
            tSysUser.setPkUserId(pkId);
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
        }else if ("1".equals(approvalType)){

        }
        return stringBuffer;
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TApvApproval entity = new TApvApproval();
        List<TApvApproval> list = this.tApvApprovalMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TApvApproval>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }

    /**
     * 添加数据
     *
     * @param tApvApproval
     * @return
     */
//    @Transactional()
//    @Override
//    public CommonResult insert(TApvApproval tApvApproval) {
//        String newPkId = IDUtils.getUUID();
//        //判断是否为添加末尾审批流程
//        int i = 0;
//        int j = 0;
//        String pkApprovalId = tApvApproval.getPkApprovalId();
//        if(!StringUtils.isEmpty(pkApprovalId)){
//            //先将末尾的审批流程isEnding改为0
//            j = ObjectAsyncTask.updateAPV_isending(pkApprovalId);
//            //再添加末尾审批流程
//            TApvApproval lastTypeData = tApvApprovalMapper.selectByPkId(pkApprovalId);
//            tApvApproval.setPkApprovalId(newPkId);
//            tApvApproval.setApprovalType(lastTypeData.getApprovalType());
//            tApvApproval.setNextApproval("0");
//            tApvApproval.setDataType(lastTypeData.getDataType());
//            tApvApproval.setIsEnding(1);
//            tApvApproval.setIsStart(0);
//            i = tApvApprovalMapper.insertSelective(tApvApproval);
//        }else {
//            //添加新的审批流程
//            //审批类型
//            String apvType = tApvApproval.getApprovalType();
//            //查询该审批类型是否已添加过数据
//            TApvApproval lastTypeData = tApvApprovalMapper.selectByType(apvType);
//            tApvApproval.setIsStart(1);
//            tApvApproval.setIsEnding(1);
//            tApvApproval.setNextApproval("0");
//            tApvApproval.setPkApprovalId(newPkId);
//            if(lastTypeData != null){
//                //说明之前已经添加过该数据，则添加新的该审批流程
//                tApvApproval.setDataType(lastTypeData.getDataType()+1);
//            }else {
//                tApvApproval.setDataType(1);
//            }
//            i = this.tApvApprovalMapper.insertSelective(tApvApproval);
//        }
//        if (i > 0 && j > 0) {
//            return new CommonResult(200, "success", "该审批流程已增加新审批人！", null);
//        } else if(i > 0){
//            return new CommonResult(201, "success", "新审批流程添加成功！", null);
//        }else {
//            return new CommonResult(444, "error", "添加数据失败", null);
//        }
//    }
}
    
