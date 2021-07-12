package com.hjy.cloud.t_outfit.utils;

import com.hjy.cloud.t_outfit.dao.TOutfitCompanyMapper;
import com.hjy.cloud.t_outfit.dao.TOutfitDeptMapper;
import com.hjy.cloud.t_outfit.entity.RCompany;
import com.hjy.cloud.t_outfit.entity.RDept;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * @author liuchun
 * @Package com.hjy.cloud.t_outfit.utils
 * @date 2021/7/12 15:44
 * description:
 */
@Component
public class CompanyUtil {


    @Resource
    private TOutfitCompanyMapper outfitCompanyMapper;
    @Resource
    private TOutfitDeptMapper outfitDeptMapper;
    public List<RCompany> getCompanyTree(){
        //获取公司
        List<RCompany> companies = outfitCompanyMapper.select_Id_name();
        if(companies != null && companies.size() > 0){
            //再查部门
            Iterator<RCompany> iterator = companies.iterator();
            while (iterator.hasNext()){
                RCompany next = iterator.next();
                List<RDept> depts = outfitDeptMapper.select_Id_nameByCompanyId(next.getPkCompanyId());
                next.setDepts(depts);
            }
            return companies;
        }
        return null;
    }
}
