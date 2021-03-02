package com.hjy.cloud.t_outfit.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_outfit.dao.TOutfitDeptMapper;
import com.hjy.cloud.t_outfit.entity.TOutfitCompany;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_outfit.service.TOutfitDeptService;
import com.hjy.cloud.t_system.dao.TSysUserMapper;
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
 * (TOutfitDept)表服务实现类
 *
 * @author makejava
 * @since 2021-02-24 21:15:05
 */
@Service("tOutfitDeptService")
public class TOutfitDeptServiceImpl implements TOutfitDeptService {

    @Resource
    private TOutfitDeptMapper tOutfitDeptMapper;
    @Resource
    private TSysUserMapper tSysUserMapper;
    /**
     * 添加前获取数据
     *
     * @return
     */
    @Override
    public CommonResult insertPage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("entity", null);
        return new CommonResult(200, "success", "获取数据成功", jsonObject);
    }

    /**
     * 添加数据
     *
     * @param tOutfitDept
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TOutfitDept tOutfitDept) {
        tOutfitDept.setPkDeptId(IDUtils.getUUID());
        tOutfitDept.setCreateTime(new Date());
        tOutfitDept.setModifyTime(new Date());
        int i = this.tOutfitDeptMapper.insertSelective(tOutfitDept);
        if (i > 0) {
            //访问列表数据
            JSONObject resultJson = this.getListInfo();
            return new CommonResult(200, "success", "添加数据成功", resultJson);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }



    /**
     * 修改数据
     *
     * @param tOutfitDept
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TOutfitDept tOutfitDept) {
        int i = this.tOutfitDeptMapper.updateByPkId(tOutfitDept);
        if (i > 0) {
            JSONObject resultJson = this.getListInfo();
            return new CommonResult(200, "success", "修改数据成功", resultJson);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tOutfitDept
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TOutfitDept tOutfitDept) {
        int i = this.tOutfitDeptMapper.deleteById(tOutfitDept);
        if (i > 0) {
            /**
             * 再删除公司-部门关联表中数据
             */
            int j = tOutfitDeptMapper.deleteCompanyDeptByDeptId(tOutfitDept.getPkDeptId());
            JSONObject resultJson = this.getListInfo();
            return new CommonResult(200, "success", "删除数据成功", resultJson);
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
//        JSONObject json = JSON.parseObject(param);
        //查询条件
//        String pkId = JsonUtil.getStringParam(json, "pk_id");
        JSONObject resultJson = this.getListInfo();
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tOutfitDept 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TOutfitDept tOutfitDept) {
        String pkId = tOutfitDept.getPkDeptId();
        TOutfitDept entity = this.tOutfitDeptMapper.selectByPkId(pkId);
        int level = 1;
        //判断部门级别
        if(!StringUtils.isEmpty(entity.getSuperiorDept())){
            boolean flag = true;
            while (flag){
                String superiorDeptId = tOutfitDeptMapper.selectSuperiorDeptId(pkId);
                if(!StringUtils.isEmpty(superiorDeptId)){
                    pkId = superiorDeptId;
                    level ++;
                }else {
                    flag = false;
                }
            }
        }
        entity.setDeptLevel(level);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        //获取部门ID和名称
        List<TOutfitDept> id_name = tOutfitDeptMapper.selectAllIdAndName();
        resultJson.put("list", id_name);

        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
    /**
     * 给部门下发用户UI
     *
     * @param param json参数
     * @return
     */
    @Override
    public CommonResult addUserUI(String param) {
        JSONObject json = JSON.parseObject(param);
        String fkDeptId=String.valueOf(json.get("pkDeptId"));
        String fkCompanyId=String.valueOf(json.get("fkCompanyId"));
        JSONObject jsonObject = new JSONObject();
        //通过deptIdStr查找部门
        TOutfitDept tOutfitDept = tOutfitDeptMapper.selectByPkId(fkDeptId);
        jsonObject.put("dept",tOutfitDept);
        //查找所有用户
        List<TSysUser> tSysUserList = tSysUserMapper.selectAll();
        jsonObject.put("userList",tSysUserList);
        //查询已分配的用户部门并进行回显
        List<String> deptUserList = tOutfitDeptMapper.selectDeptUser_userIded(fkCompanyId);
        jsonObject.put("ids",deptUserList);
        List<String> deptUserList2 = tOutfitDeptMapper.selectDeptUserByDept(fkDeptId,fkCompanyId);
        jsonObject.put("idsFP",deptUserList2);
        return new CommonResult(200,"success","获取部门已分配用户成功!",jsonObject);
    }
    /**
     * 给部门下发用户
     *
     * @param param json参数
     * @return
     */
    @Transactional()
    @Override
    public CommonResult addUser(String param) {
        JSONObject jsonObject = JSON.parseObject(param);
        String pkDeptId = String.valueOf(jsonObject.get("pkDeptId"));
        String fkCompanyId = String.valueOf(jsonObject.get("fkCompanyId"));
        //删除原有的部门及用户
        tOutfitDeptMapper.deleteDeptUserByDeptId(pkDeptId,fkCompanyId);
        JSONArray jsonArray = jsonObject.getJSONArray("ids");
        if(jsonArray != null){
            String userIdsStr = jsonArray.toString();
            List<String> idList = JSONArray.parseArray(userIdsStr,String.class);
            //查询该部门基本信息
            TOutfitDept dept = tOutfitDeptMapper.selectByPkId(pkDeptId);
            if(idList.size()>dept.getMaxNum()){
                return new CommonResult(201,"error","该部门已达最大人数!",null);
            }else {
                //添加部门用户
                this.addDeptUserByList(pkDeptId,fkCompanyId,idList);
                return new CommonResult(200,"success","部门添加用户成功!",null);
            }
        }else {
            return new CommonResult(444,"error","未选择员工!",null);

        }
    }
    /**
     * 合并部门前获取所有部门数据
     */
    @Override
    public CommonResult deptMergeUI(String param) {
        JSONObject jsonObject = JSON.parseObject(param);
        String pkDeptId = String.valueOf(jsonObject.get("pkDeptId"));
        List<TOutfitDept> deptList = tOutfitDeptMapper.selectAllIdAndName_BBKDQBM(pkDeptId);
        return new CommonResult(200,"success","获取部门信息成功!",deptList);

    }
    /**
     * 合并部门
     */
    @Override
    public CommonResult deptMerge(String param) {
        JSONObject jsonObject = JSON.parseObject(param);
        String hbDeptId = String.valueOf(jsonObject.get("hbDeptId"));
        String hbdDeptId = String.valueOf(jsonObject.get("hbdDeptId"));
        //删除公司的该部门信息
        tOutfitDeptMapper.deleteCompanyDeptByDeptId(hbDeptId);
        //删除部门基本信息
        TOutfitDept delDept = new TOutfitDept();
        delDept.setPkDeptId(hbDeptId);
        tOutfitDeptMapper.deleteById(delDept);
        //移动该部门的人到目标部门
        tOutfitDeptMapper.updateByDeptId(hbDeptId,hbdDeptId);
        return new CommonResult(200,"success","合并部门成功!",null);

    }

    @Override
    public void addDeptUserByDeptUser(ReDeptUser deptUser) {
        tOutfitDeptMapper.addDeptUserByDeptUser(deptUser);
    }

    @Override
    public List<TOutfitDept> selectAllIdAndName() {
        return tOutfitDeptMapper.selectAllIdAndName();
    }

    @Override
    public List<String> selectAllDeptName() {
        return tOutfitDeptMapper.selectAllDeptName();
    }

    @Override
    public String selectDeptIdByUserId(String idStr) {
        return tOutfitDeptMapper.selectDeptIdByUserId(idStr);
    }



    /**
     * 批量添加部门用户
     * @return
     */
    private void addDeptUserByList(String pkDeptId,String fkCompanyId,List<String> idList) {
        System.err.println(idList);
        List<ReDeptUser> deptUsers = new ArrayList<>();
        for (String s:idList){
            ReDeptUser deptUser = new ReDeptUser();
            deptUser.setPk_deptUser_id(IDUtils.getUUID());
            deptUser.setFk_user_id(s);
            deptUser.setFk_dept_id(pkDeptId);
            deptUser.setFk_company_id(fkCompanyId);
            deptUsers.add(deptUser);
        }
        tOutfitDeptMapper.addDeptUserByList(deptUsers);
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TOutfitDept entity = new TOutfitDept();
        List<TOutfitDept> list = this.tOutfitDeptMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TOutfitDept>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }

}