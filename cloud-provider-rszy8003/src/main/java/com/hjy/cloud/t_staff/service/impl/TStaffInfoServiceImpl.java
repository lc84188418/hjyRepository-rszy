package com.hjy.cloud.t_staff.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_dictionary.dao.TDictionaryEducationMapper;
import com.hjy.cloud.t_dictionary.dao.TDictionaryHtlxMapper;
import com.hjy.cloud.t_dictionary.dao.TDictionaryNationMapper;
import com.hjy.cloud.t_dictionary.dao.TDictionaryPositionMapper;
import com.hjy.cloud.t_dictionary.entity.TDictionaryEducation;
import com.hjy.cloud.t_dictionary.entity.TDictionaryHtlx;
import com.hjy.cloud.t_dictionary.entity.TDictionaryNation;
import com.hjy.cloud.t_outfit.dao.TOutfitDeptMapper;
import com.hjy.cloud.t_staff.dao.TStaffInfoMapper;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.t_staff.service.TStaffInfoService;
import com.hjy.cloud.t_system.dao.TSysUserMapper;
import com.hjy.cloud.t_system.entity.TSysUser;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.JsonUtil;
import com.hjy.cloud.utils.PasswordEncryptUtils;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.utils.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (TStaffInfo)表服务实现类
 *
 * @author makejava
 * @since 2021-02-25 17:06:47
 */
@Service("tStaffInfoService")
public class TStaffInfoServiceImpl implements TStaffInfoService {

    @Resource
    private TStaffInfoMapper tStaffInfoMapper;
    @Resource
    private TSysUserMapper tSysUserMapper;
    @Resource
    private TOutfitDeptMapper tOutfitDeptMapper;
    @Resource
    private TDictionaryPositionMapper tDictionaryPositionMapper;
    @Resource
    private TDictionaryHtlxMapper tDictionaryHtlxMapper;
    @Resource
    private TDictionaryNationMapper tDictionaryNationMapper;
    @Resource
    private TDictionaryEducationMapper tDictionaryEducationMapper;
//    /**
//     * 添加前获取数据
//     *
//     * @return
//     */
//    @Override
//    public CommonResult insertPage() {
//        JSONObject jsonObject = new JSONObject();
//        //部门
//        List<TOutfitDept> depts = tOutfitDeptMapper.selectAllIdAndName();
//        //职位
//        List<TDictionaryPosition> positions = tDictionaryPositionMapper.selectAllId_Name();
//        //合同类型
//        List<TDictionaryHtlx> htlxes = tDictionaryHtlxMapper.selectAllId_Name();
//        //民族
//        List<TDictionaryNation> nations = tDictionaryNationMapper.selectAllId_Name();
//        //学历
//        List<TDictionaryEducation> educations = tDictionaryEducationMapper.selectAllId_Name();
//        //员工
//        List<TStaffInfo> staffInfos = tStaffInfoMapper.selectAllId_Name();
//        jsonObject.put("depts", depts);
//        jsonObject.put("positions", positions);
//        jsonObject.put("htlxes", htlxes);
//        jsonObject.put("nations", nations);
//        jsonObject.put("educations", educations);
//        jsonObject.put("staffInfos", staffInfos);
//        return new CommonResult(200, "success", "获取数据成功", jsonObject);
//    }

    /**
     * 添加数据
     *
     * @param tStaffInfo
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TStaffInfo tStaffInfo) {
        //必填项的判断
        if(StringUtils.isEmpty(tStaffInfo.getFkDeptId())
                || StringUtils.isEmpty(tStaffInfo.getFkPositionId())
                || StringUtils.isEmpty(tStaffInfo.getFkWorkaddressId())
        ){
            return new CommonResult().ErrorResult("部门、职位、工作地信息不能为空！",null);
        }
        tStaffInfo.setPkStaffId(IDUtils.getUUID());
        tStaffInfo.setStaffStatus(1);
        tStaffInfo.setEntryTime(new Date());
        int i = this.tStaffInfoMapper.insertSelective(tStaffInfo);
        if (i > 0) {
            //添加成功后添加该员工的系统用户
            /**
             * 添加系统用户
             */
            TSysUser tSysUser = new TSysUser();
            tSysUser.setPkUserId(tStaffInfo.getPkStaffId());
            tSysUser.setUsername(tStaffInfo.getStaffName());
            String password = PasswordEncryptUtils.MyPasswordEncryptUtil(null,"123456");
            tSysUser.setPassword(password);
            tSysUser.setEmail(tStaffInfo.getStaffEmail());
            tSysUser.setTel(tStaffInfo.getStaffTel());
            tSysUser.setIdcard(tStaffInfo.getIdCard());
            tSysUser.setFullName(tStaffInfo.getStaffName());
            tSysUser.setWorkPosition(tStaffInfo.getFkPositionId());
            tSysUser.setEnableStatus("1");
            tSysUser.setCreateTime(new Date());
            tSysUser.setModifyTime(new Date());
            int j = tSysUserMapper.insertSelective(tSysUser);
            return new CommonResult(200, "success", "添加员工成功！已创建系统账户！",null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }



    /**
     * 修改数据
     *
     * @param tStaffInfo
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TStaffInfo tStaffInfo) {
        //不能直接修改部门,岗位，工作地
        tStaffInfo.setFkDeptId(null);
        tStaffInfo.setFkPositionId(null);
        tStaffInfo.setFkWorkaddressId(null);
        int i = this.tStaffInfoMapper.updateByPkId(tStaffInfo);
        if (i > 0) {
            JSONObject jsonObject = this.getListInfo();
            return new CommonResult(200, "success", "修改数据成功", jsonObject);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffInfo
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TStaffInfo tStaffInfo) {
        int i = this.tStaffInfoMapper.deleteById(tStaffInfo);
        if (i > 0) {
            JSONObject jsonObject = this.getListInfo();
            return new CommonResult(200, "success", "删除数据成功", jsonObject);
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
        String staffStatusStr = JsonUtil.getStringParam(json, "staffStatus");
        TStaffInfo entity = new TStaffInfo();
        //分页记录条数
        int pageNum = 1;
        int pageSize = 10;
        int staffStatus = 1;
        if (pageNumStr != null) {
            pageNum = Integer.parseInt(pageNumStr);
        }
        if (pageSizeStr != null) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if (staffStatusStr != null) {
            staffStatus = Integer.parseInt(staffStatusStr);
        }
        entity.setStaffStatus(staffStatus);
        PageHelper.startPage(pageNum, pageSize);
        List<TStaffInfo> list = this.tStaffInfoMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffInfo>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    @Override
    public List<TStaffInfo> selectAll() {
        return tStaffInfoMapper.selectAll();
    }

    /**
     * 获取单个数据
     *
     * @param tStaffInfo 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TStaffInfo tStaffInfo) {
        TStaffInfo entity = this.tStaffInfoMapper.selectByPkId2(tStaffInfo);
        JSONObject resultJson = new JSONObject();
        resultJson.put("staffInfo", entity);
        //合同类型
        List<TDictionaryHtlx> htlxList = tDictionaryHtlxMapper.selectAllId_Name();
        resultJson.put("htlxList", htlxList);
        //民族
        List<TDictionaryNation> nationList = tDictionaryNationMapper.selectAllId_Name();
        resultJson.put("nationList", nationList);
        //学历
        List<TDictionaryEducation> educationList = tDictionaryEducationMapper.selectAllId_Name();
        resultJson.put("educationList", educationList);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    @Override
    public String selectDeptIdByPkId(String pkStaffId) {
        return tStaffInfoMapper.selectDeptIdByPkId(pkStaffId);
    }

    @Override
    public TStaffInfo selectDeptLeader(String fkUserId) {
        return tStaffInfoMapper.selectDeptLeader(fkUserId);
    }

    @Override
    public TStaffInfo selectLeaderByPosition(String s) {
        return tStaffInfoMapper.selectLeaderByPosition(s);
    }

    @Override
    public TStaffInfo selectByPkId2(String currentSourceId) {
        TStaffInfo info = new TStaffInfo();
        info.setPkStaffId(currentSourceId);
        return tStaffInfoMapper.selectByPkId2(info);
    }

    private JSONObject getListInfo() {
        TStaffInfo entity = new TStaffInfo();

        PageHelper.startPage(1, 10);
        List<TStaffInfo> list = this.tStaffInfoMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffInfo>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    
