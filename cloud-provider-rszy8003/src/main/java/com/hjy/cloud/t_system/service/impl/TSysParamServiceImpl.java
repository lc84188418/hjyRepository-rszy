package com.hjy.cloud.t_system.service.impl;

import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_system.entity.ActiveUser;
import com.hjy.cloud.t_system.entity.TSysParam;
import com.hjy.cloud.t_system.dao.TSysParamMapper;
import com.hjy.cloud.t_system.service.TSysParamService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * (TSysParam)表服务实现类
 *
 * @author liuchun
 * @since 2020-08-11 15:51:59
 */
@Service
public class TSysParamServiceImpl implements TSysParamService {
    @Autowired
    private TSysParamMapper tSysParamMapper;


    /**
     * 修改数据
     *
     * @param tSysParam 实例对象
     * @return 实例对象
     */
    @Transactional()
    @Override
    public CommonResult updateById(TSysParam tSysParam, HttpSession session) throws Exception{
        ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
        tSysParam.setOperatorPeople(activeUser.getFullName());
        tSysParam.setOperatorTime(new Date());
        if(tSysParam.getPkParamId().equals("YJXXSCSJD")){
            //时间段正则  8-18
            //0-24整数正则 ^1[0-9]$|^2[0-4]$|^[0-9]$
            String pattern = ".*-.*";
            boolean isMatch = Pattern.matches(pattern, tSysParam.getParamValue());
            if(isMatch == false){
                return new CommonResult(445,"error","该值不合规范，请重新输入再试!",null);
            }
        }else {
            //纯数字整数
            String pattern = "^([1-9][0-9]*)$";
            boolean isMatch = Pattern.matches(pattern, tSysParam.getParamValue());
            if(isMatch == false){
                return new CommonResult(445,"error","该值不合规范，请重新输入再试!",null);
            }
        }

        int i = tSysParamMapper.updateById(tSysParam);
        if(i>0){
            return new CommonResult(200,"success","修改成功!",null);
        }else return new CommonResult(444,"error","修改失败!",null);
    }


    /**
     * 查询多条数据
     * @return 对象列表
     */
    @Override
    public List<TSysParam> selectAll() throws Exception{
        return this.tSysParamMapper.selectAll();
    }

    /**
     * 查询单条条数据
     * @return 对象
     */
    @Override
    public TSysParam selectById(String pkParamId) throws Exception{
        return this.tSysParamMapper.selectById(pkParamId);
    }

    @Override
    public String selectParamById(String pkParamId) {
        return tSysParamMapper.selectParamById(pkParamId);
    }
}