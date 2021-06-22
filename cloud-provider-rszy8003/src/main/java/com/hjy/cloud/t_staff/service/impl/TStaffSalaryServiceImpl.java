package com.hjy.cloud.t_staff.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.common.entity.DSalaryRecord;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_staff.dao.TStaffInfoMapper;
import com.hjy.cloud.t_staff.dao.TStaffSalaryMapper;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.t_staff.entity.TStaffSalary;
import com.hjy.cloud.t_staff.service.TStaffSalaryService;
import com.hjy.cloud.t_system.dao.TSysTokenMapper;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.utils.DateUtil;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.JsonUtil;
import com.hjy.cloud.utils.TokenUtil;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.utils.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * (TStaffSalary)表服务实现类
 *
 * @author makejava
 * @since 2021-03-01 15:53:17
 */
@Service("tStaffSalaryService")
public class TStaffSalaryServiceImpl implements TStaffSalaryService {

    @Resource
    private TStaffSalaryMapper tStaffSalaryMapper;
    @Resource
    private TStaffInfoMapper tStaffInfoMapper;
    @Resource
    private TSysTokenMapper tSysTokenMapper;
    /**
     * 添加前获取数据
     *
     * @return
     */
    @Override
    public CommonResult insertPage() {
        //返回还没添加工资条的员工列表，不包括已经添加过的员工
        List<TStaffInfo> staffInfos = tStaffInfoMapper.selectEnableAddSalary();
        if(staffInfos != null && staffInfos.size() > 0){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("staffInfos", staffInfos);
            return new CommonResult(200, "success", "获取员工数据成功", jsonObject);
        }else {
            return new CommonResult(200, "success", "当前所有员工都已填写工资条，无法新增，可进行修改！", null);
        }

    }

    /**
     * 添加数据
     *
     * @param tStaffSalary
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TStaffSalary tStaffSalary) {
        tStaffSalary.setPkSalaryId(IDUtils.getUUID());
        int i = this.tStaffSalaryMapper.insertSelective(tStaffSalary);
        if (i > 0) {
            JSONObject listInfo = this.getListInfo();
            return new CommonResult(200, "success", tStaffSalary.getStaffName()+" 工资条数据添加成功!", listInfo);
        } else {
            return new CommonResult(444, "error", "工资条数据添加失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffSalary
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TStaffSalary tStaffSalary) {
        int i = this.tStaffSalaryMapper.updateByPkId(tStaffSalary);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffSalary
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TStaffSalary tStaffSalary) {
        int i = this.tStaffSalaryMapper.deleteById(tStaffSalary);
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
        String staffName = JsonUtil.getStringParam(json, "staffName");
        TStaffSalary entity = new TStaffSalary();
        entity.setStaffName(staffName);
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
        List<TStaffSalary> list = this.tStaffSalaryMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffSalary>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tStaffSalary 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TStaffSalary tStaffSalary) {
        String pkId = tStaffSalary.getPkSalaryId();
        TStaffSalary entity = this.tStaffSalaryMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
    /**
     * 发送工资明细页面
     *
     * @param tStaffSalary 实体对象，pkSalaryId
     */
    @Override
    public CommonResult sendPage(TStaffSalary tStaffSalary) {
        TStaffSalary entity = new TStaffSalary();
        entity.setPkSalaryId(tStaffSalary.getPkSalaryId());
        //最多只有一条数据
        List<TStaffSalary> list = this.tStaffSalaryMapper.selectAllPage(entity);
        JSONObject resultJson = new JSONObject();
        String msg = "";
        if(list != null && list.size() == 1){
            TStaffSalary resultEntity = list.get(0);
            double add = resultEntity.getJbXz()+resultEntity.getGwXz()+resultEntity.getJxXz()
                +resultEntity.getCsBt()+resultEntity.getJtBt()+resultEntity.getQqBt();
            double delete = resultEntity.getCdZtKq() +resultEntity.getQkBKq()+resultEntity.getSjBjKq()
                +resultEntity.getQtKq()+resultEntity.getSbKq()+resultEntity.getGjjKq()+resultEntity.getGrsdsKq();
            resultEntity.setDueSalary(add);
            resultEntity.setTakeHomePay(add-delete);
            resultJson.put("entity", resultEntity);
            if(list.get(0).getIsSend() == 1){
                //说明当月已发工资
                msg = "当前员工当月已发工资，不可再发！";
                return new CommonResult(201, "error", msg, resultJson);
            }else {
                msg = "获取当前员工工资条数据成功！";
                return new CommonResult(200, "success", msg, resultJson);
            }
        }
        return new CommonResult(444, "success", "获取工资条数据失败", null);
    }
    /**
     * 发送工资明细
     * @param tStaffSalary 实体对象，pkSalaryId
     */
    @Transactional()
    @Override
    public CommonResult send(DSalaryRecord tStaffSalary, HttpServletRequest request) {
        tStaffSalary.setPkSalaryrecordId(IDUtils.getUUID());
        //工资说明，判断当前月份
        Date currentDate = new Date();
        String currentDateStr = DateUtil.getDateFormat(currentDate,"yyyy-MM");
        String[] currentDateArray = currentDateStr.split("-");
        StringBuffer salaryDesc = new StringBuffer();
        if(currentDateArray[1].equals("01") ||currentDateArray[1].equals("1")){
            //说明当前时间为1月，工资应为上一年12月
            int lastYearInt = Integer.parseInt(currentDateArray[0]) - 1;
            salaryDesc.append(lastYearInt+"年12月工资");
        }else {
            int lastMonthInt = Integer.parseInt(currentDateArray[1]) - 1;
            salaryDesc.append(currentDateArray[0]+"年"+lastMonthInt+"月份工资");
        }
        tStaffSalary.setSalaryDesc(salaryDesc.toString());
        tStaffSalary.setSendStatus(1);
        tStaffSalary.setCheckStatus(0);
        tStaffSalary.setConfirmStatus(0);
        SysToken token = tSysTokenMapper.findByToken(TokenUtil.getRequestToken(request));
        if(token != null){
            tStaffSalary.setOepratePeople(token.getFullName());
        }
        tStaffSalary.setSendTime(new Date());
        /**
         * 开始计算薪资，如果前端做了数据动态展现，可直接将值传回，就无需再计算
         * 也可运用反射
         */
        //工资加项
        double add = tStaffSalary.getJbXz()+tStaffSalary.getGwXz()+tStaffSalary.getJxXz()
                +tStaffSalary.getCsBt()+tStaffSalary.getJtBt()+tStaffSalary.getQqBt();
        double delete = tStaffSalary.getCdZtKq() +tStaffSalary.getQkBKq()+tStaffSalary.getSjBjKq()
                +tStaffSalary.getQtKq()+tStaffSalary.getSbKq()+tStaffSalary.getGjjKq()+tStaffSalary.getGrsdsKq();
        tStaffSalary.setDueSalary(add);
        tStaffSalary.setTakeHomePay(add-delete);
        int i = this.tStaffSalaryMapper.insertSalaryRecord(tStaffSalary);
        if(i > 0){
            JSONObject listInfo = this.getListInfo();
            return new CommonResult(200, "success", tStaffSalary.getStaffName()+" 的工资明细已发送成功！", listInfo);
        }else {
            return new CommonResult(444, "error", tStaffSalary.getStaffName()+" 的工资明细发送失败！", null);
        }
    }
    /**
     * 工资明细发送记录
     * 管理员
     */
    @Override
    public CommonResult adminSendRecord(String param) throws ParseException {
        JSONObject json = JSON.parseObject(param);
        //查询条件
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        String staffName = JsonUtil.getStringParam(json, "staffName");
        //只需要传年月
        String sendTime = JsonUtil.getStringParam(json, "sendTime");
        DSalaryRecord entity = new DSalaryRecord();
        entity.setStaffName(staffName);
        if(!StringUtils.isEmpty(sendTime)){
            entity.setSendTime(DateUtil.formatTime2(sendTime));
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
        List<DSalaryRecord> list = this.tStaffSalaryMapper.selectRecordAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<DSalaryRecord>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取员工工资发送记录数据成功！", resultJson);
    }
    /**
     * 工资明细发送记录
     * 员工
     */
    @Override
    public CommonResult staffSendRecord(String param,HttpServletRequest request) throws ParseException {
        SysToken token = tSysTokenMapper.findByToken(TokenUtil.getRequestToken(request));
        JSONObject json = JSON.parseObject(param);
        //查询条件
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        //只需要传年月
        String sendTime = JsonUtil.getStringParam(json, "sendTime");
        DSalaryRecord entity = new DSalaryRecord();
        entity.setStaffName(token.getFullName());
        entity.setFkStaffId(token.getFkUserId());
        if(!StringUtils.isEmpty(sendTime)){
            entity.setSendTime(DateUtil.formatTime2(sendTime));
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
        List<DSalaryRecord> list = this.tStaffSalaryMapper.selectRecordAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<DSalaryRecord>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取工资发送记录数据成功！", resultJson);
    }
    @Transactional()
    @Override
    public CommonResult selectRecordById(DSalaryRecord salaryRecord) {
        String pkId = salaryRecord.getPkSalaryrecordId();
        //修改查看的状态
        DSalaryRecord updateEntity = new DSalaryRecord();
        updateEntity.setPkSalaryrecordId(pkId);
        updateEntity.setCheckStatus(1);
        int i = tStaffSalaryMapper.updateRecordByPkId(updateEntity);
        DSalaryRecord entity = this.tStaffSalaryMapper.selectRecordById(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
    @Transactional()
    @Override
    public CommonResult recordConfirm(DSalaryRecord salaryRecord) {
        //修改查看的状态
        salaryRecord.setConfirmStatus(1);
        int i = tStaffSalaryMapper.updateRecordByPkId(salaryRecord);
        if(i > 0){
            JSONObject listInfo = this.getListInfo();
            return new CommonResult(200, "success", "工资明细确认成功", listInfo);
        }else {
            return new CommonResult(444, "error", "工资明细确认失败", null);

        }
    }
    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TStaffSalary entity = new TStaffSalary();
        List<TStaffSalary> list = this.tStaffSalaryMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffSalary>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    
