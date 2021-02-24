package com.hjy.cloud.t_outfit.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_outfit.dao.TOutfitCompanyMapper;
import com.hjy.cloud.t_outfit.dao.TOutfitDeptMapper;
import com.hjy.cloud.t_outfit.entity.ReCompanyDept;
import com.hjy.cloud.t_outfit.entity.TOutfitCompany;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_outfit.service.TOutfitCompanyService;
import com.hjy.cloud.t_system.entity.ReDeptUser;
import com.hjy.cloud.t_system.entity.TSysUser;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.JsonUtil;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.utils.page.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (TOutfitCompany)表服务实现类
 *
 * @author makejava
 * @since 2021-02-23 15:33:46
 */
@Service("tOutfitCompanyService")
public class TOutfitCompanyServiceImpl implements TOutfitCompanyService {

    @Resource
    private TOutfitCompanyMapper tOutfitCompanyMapper;
    @Resource
    private TOutfitDeptMapper tOutfitDeptMapper;
    /**
     * 添加前获取数据
     *
     * @return
     */
    @Override
    public CommonResult insertPage() {
        JSONObject resultJson = new JSONObject();
        List<TOutfitCompany> list = tOutfitCompanyMapper.select_PkId_name();
        resultJson.put("list",list);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 添加数据
     *
     * @param tOutfitCompany
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TOutfitCompany tOutfitCompany) {
        tOutfitCompany.setPkCompanyId(IDUtils.getUUID());
        int i = this.tOutfitCompanyMapper.insertSelective(tOutfitCompany);
        if (i > 0) {
            PageHelper.startPage(1, 10);
            TOutfitCompany entity = new TOutfitCompany();
            List<TOutfitCompany> list = this.tOutfitCompanyMapper.selectAllPage(entity);
            PageResult result =PageUtil.getPageResult(new PageInfo<TOutfitCompany>(list));
            JSONObject resultJson = new JSONObject();
            resultJson.put("PageResult", result);
            return new CommonResult(200, "success", "添加数据成功", resultJson);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tOutfitCompany
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TOutfitCompany tOutfitCompany) {
        int i = this.tOutfitCompanyMapper.updateByPkId(tOutfitCompany);
        if (i > 0) {
            TOutfitCompany entity = tOutfitCompanyMapper.selectByPkId(tOutfitCompany.getPkCompanyId());
            JSONObject resultJson = new JSONObject();
            resultJson.put("entity",entity);
            return new CommonResult(200, "success", "修改数据成功", resultJson);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tOutfitCompany
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TOutfitCompany tOutfitCompany) {
        int i = this.tOutfitCompanyMapper.deleteById(tOutfitCompany);
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
        String companyName = JsonUtil.getStringParam(json, "companyName");
        TOutfitCompany entity = new TOutfitCompany();
        entity.setCompanyName(companyName);
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
        List<TOutfitCompany> list = this.tOutfitCompanyMapper.selectAllPage(entity);
        PageResult result =PageUtil.getPageResult(new PageInfo<TOutfitCompany>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tOutfitCompany
     * @return
     */
    @Override
    public CommonResult selectById(TOutfitCompany tOutfitCompany) {
        TOutfitCompany entity = this.tOutfitCompanyMapper.selectByPkId(tOutfitCompany.getPkCompanyId());
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        if(!StringUtils.isEmpty(entity.getSjgs())){
            TOutfitCompany obj2 = tOutfitCompanyMapper.selectByPkId(entity.getSjgs());
            resultJson.put("sjgs", obj2);
        }else {
            resultJson.put("sjgs", null);

        }
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 分配部门UI
     *
     * @param tOutfitCompany
     * @return
     */
    @Override
    public CommonResult distributeDeptUI(TOutfitCompany tOutfitCompany) {
        //当前公司信息
        TOutfitCompany currentEntity = tOutfitCompanyMapper.selectByPkId(tOutfitCompany.getPkCompanyId());
        //获取所有部门列表
        List<TOutfitDept> deptList = tOutfitDeptMapper.selectAllIdAndName();
        //获取该公司已经分配的部门ID
        List<String> ids = tOutfitCompanyMapper.selectFPDeptId(tOutfitCompany.getPkCompanyId());
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", currentEntity);
        resultJson.put("deptList", deptList);
        resultJson.put("ids", ids);
        return new CommonResult(200, "success", "获取数据成功", resultJson);

    }
    /**
     * 分配部门
     *
     * @param param
     * @return
     */
    @Override
    public CommonResult distributeDept(String param) {
        JSONObject jsonObject = JSON.parseObject(param);
        String fkCompanyId=String.valueOf(jsonObject.get("fkCompanyId"));
        //删除原有的公司及部门
        tOutfitCompanyMapper.deleteCompanyByCompanyId(fkCompanyId);
        JSONArray jsonArray = jsonObject.getJSONArray("ids");
        if(jsonArray != null){
            String deptIdsStr = jsonArray.toString();
            List<String> idList = JSONArray.parseArray(deptIdsStr,String.class);
            //添加公司部门
            this.addCompanyDeptByList(fkCompanyId,idList);
        }
        return new CommonResult(200,"success","公司添加部门成功!",null);

    }
    /**
     * 添加公司部门
     *
     * @param fkCompanyId，idList
     * @return
     */
    private int addCompanyDeptByList(String fkCompanyId, List<String> idList) {
        List<ReCompanyDept> companyDepts = new ArrayList<>();
        for (String s:idList){
            ReCompanyDept companyDept = new ReCompanyDept();
            companyDept.setPkCompanydeptId(IDUtils.getUUID());
            companyDept.setFkCompanyId(fkCompanyId);
            companyDept.setFkDeptId(s);
            companyDepts.add(companyDept);
        }
        //批量添加公司部门信息
        return tOutfitCompanyMapper.addCompanyDeptByList(companyDepts);
    }
}