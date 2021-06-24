package com.hjy.cloud.t_staff.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_dictionary.dao.TDictionaryHtlxMapper;
import com.hjy.cloud.t_dictionary.entity.TDictionaryHtlx;
import com.hjy.cloud.t_outfit.dao.TOutfitCompanyMapper;
import com.hjy.cloud.t_outfit.dao.TOutfitDeptMapper;
import com.hjy.cloud.t_outfit.entity.TOutfitCompany;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_staff.dao.TStaffContractMapper;
import com.hjy.cloud.t_staff.dao.TStaffInfoMapper;
import com.hjy.cloud.t_staff.entity.TStaffContract;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.t_staff.service.TStaffContractService;
import com.hjy.cloud.t_system.entity.ActiveUser;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.JsonUtil;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.utils.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * (TStaffContract)表服务实现类
 *
 * @author makejava
 * @since 2021-03-01 15:53:14
 */
@Service("tStaffContractService")
public class TStaffContractServiceImpl implements TStaffContractService {

    @Resource
    private TStaffContractMapper tStaffContractMapper;
    @Resource
    private TDictionaryHtlxMapper tDictionaryHtlxMapper;
    @Resource
    private TOutfitCompanyMapper tOutfitCompanyMapper;
    @Resource
    private TOutfitDeptMapper tOutfitDeptMapper;
    @Resource
    private TStaffInfoMapper tStaffInfoMapper;
    /**
     * 添加前获取数据
     *
     * @return
     */
    @Override
    public CommonResult insertPage() {
        JSONObject jsonObject = new JSONObject();
        //合同类型
        List<TDictionaryHtlx> htlxList = tDictionaryHtlxMapper.selectAllId_Name();
        jsonObject.put("htlxList", htlxList);
        //公司
        List<TOutfitCompany> companyList = tOutfitCompanyMapper.select_PkId_name();
        jsonObject.put("companyList", companyList);
        //部门
        List<TOutfitDept> deptList = tOutfitDeptMapper.selectAllIdAndName();
        jsonObject.put("deptList", deptList);
        return new CommonResult(200, "success", "获取数据成功", jsonObject);
    }

    /**
     * 添加数据
     *
     * @param tStaffContract
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TStaffContract tStaffContract) {
        if(StringUtils.isEmpty(tStaffContract.getFkStaffId())){
            return new CommonResult().ErrorResult("无员工id信息！",null);
        }
        tStaffContract.setPkContrctId(IDUtils.getUUID());
        //审批状态,0代表未发起审批，2代表审批中，1代表审批完成
        tStaffContract.setApprovalStatus(0);
        //合同状态
        tStaffContract.setContrctStatus(0);
        //合同签订类型，1代表正签，0代表续签
        tStaffContract.setSignStatus(1);
        /**
         * 处理员工相关信息
         */
        TStaffInfo query = new TStaffInfo();
        query.setPkStaffId(tStaffContract.getFkStaffId());
        TStaffInfo tStaffInfo = this.tStaffInfoMapper.selectByPkId2(query);
        if(tStaffInfo == null){
            return new CommonResult().ErrorResult("该员工档案已不存在！",null);
        }
        tStaffContract.setIdcard(tStaffInfo.getIdCard());
        tStaffContract.setStaffName(tStaffInfo.getStaffName());
        tStaffContract.setFkDeptId(tStaffInfo.getFkDeptId());
        //1采用档案中的公司
//        TOutfitDept dept = this.tOutfitDeptMapper.selectByPkId(tStaffInfo.getFkDeptId());
//        if(dept == null){
//            return new CommonResult().ErrorResult("部门信息不存在，请检查！",null);
//        }
//        tStaffContract.setFkContractCompany(dept.getSuperiorDept());

        //2合同公司独立

        int i = this.tStaffContractMapper.insertSelective(tStaffContract);
        if (i > 0) {
            JSONObject listInfo = this.getListInfo();
            return new CommonResult(200, "success", "添加员工合同记录成功", listInfo);
        } else {
            return new CommonResult(444, "error", "添加员工合同记录失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffContract
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TStaffContract tStaffContract) {
        int i = this.tStaffContractMapper.updateByPkId(tStaffContract);
        if (i > 0) {
            return new CommonResult(200, "success", "修改合同数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改合同数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffContract
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TStaffContract tStaffContract) {
        int i = this.tStaffContractMapper.deleteById(tStaffContract);
        if (i > 0) {
            JSONObject listInfo = this.getListInfo();
            return new CommonResult(200, "success", "删除员工合同记录成功!", listInfo);
        } else {
            return new CommonResult(444, "error", "删除员工合同记录失败!", null);
        }
    }

    /**
     * 查询所有数据
     *
     * @param param
     * @return
     */
    @Override
    public CommonResult adminList(String param) {
        JSONObject json = JSON.parseObject(param);
        //分页数据
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        //查询条件
        String staffName = JsonUtil.getStringParam(json, "staffName");
        String idcard = JsonUtil.getStringParam(json, "idcard");
        TStaffContract entity = new TStaffContract();
        entity.setStaffName(staffName);
        entity.setIdcard(idcard);
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
        List<TStaffContract> list = this.tStaffContractMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffContract>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 查询所有数据
     *
     * @param param
     * @return
     */
    @Override
    public CommonResult userList(HttpSession session, HttpServletRequest request, String param) {
        ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
        JSONObject json = JSON.parseObject(param);
        //查询条件
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        TStaffContract entity = new TStaffContract();
        entity.setIdcard(activeUser.getIDcard());
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
        List<TStaffContract> list = this.tStaffContractMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffContract>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
    /**
     * 获取单个数据
     *
     * @param tStaffContract 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TStaffContract tStaffContract) {
        String pkId = tStaffContract.getPkContrctId();
        TStaffContract entity = this.tStaffContractMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        //个人基本信息
        TStaffInfo staffInfo = tStaffInfoMapper.selectByIdCard(entity.getIdcard());
        resultJson.put("staffInfo", staffInfo);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    @Override
    public CommonResult renewalPage(TStaffContract tStaffContract) {
        String pkId = tStaffContract.getPkContrctId();
        TStaffContract entity = this.tStaffContractMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("oldContract", entity);
        //合同类型
        List<TDictionaryHtlx> htlxList = tDictionaryHtlxMapper.selectAllId_Name();
        resultJson.put("htlxList", htlxList);
        //公司
        List<TOutfitCompany> companyList = tOutfitCompanyMapper.select_PkId_name();
        resultJson.put("companyList", companyList);
        //部门
        List<TOutfitDept> deptList = tOutfitDeptMapper.selectAllIdAndName();
        resultJson.put("deptList", deptList);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
    /**
     * 续签合同
     */
    @Transactional()
    @Override
    public CommonResult renewal(TStaffContract tStaffContract) {
        tStaffContract.setPkContrctId(IDUtils.getUUID());
        //审批状态,0代表未发起审批，2代表审批中，1代表审批完成
        tStaffContract.setApprovalStatus(0);
        //合同状态
        tStaffContract.setContrctStatus(0);
        //合同签订类型，1代表正签，0代表续签
        tStaffContract.setSignStatus(0);
        TStaffInfo query = new TStaffInfo();
        query.setPkStaffId(tStaffContract.getFkStaffId());
        TStaffInfo selectInfo = this.tStaffInfoMapper.selectByPkId2(query);
        if(selectInfo == null){
            return new CommonResult().ErrorResult("续签员工信息已不存在，请刷新后再试！",null);
        }
        tStaffContract.setStaffName(selectInfo.getStaffName());
        tStaffContract.setIdcard(selectInfo.getIdCard());
        int i = this.tStaffContractMapper.insertSelective(tStaffContract);
        if (i > 0) {
            JSONObject listInfo = this.getListInfo();
            return new CommonResult(200, "success", "续签合同成功！", listInfo);
        } else {
            return new CommonResult(444, "error", "续签合同失败！", null);
        }
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TStaffContract entity = new TStaffContract();
        List<TStaffContract> list = this.tStaffContractMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffContract>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    
