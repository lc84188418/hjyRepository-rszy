package com.hjy.cloud.t_train.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.common.entity.User;
import com.hjy.cloud.domin.ActiveResult;
import com.hjy.cloud.t_system.dao.TSysUserMapper;
import com.hjy.cloud.t_train.dao.TTrainInfoMapper;
import com.hjy.cloud.t_train.entity.TTrainInfo;
import com.hjy.cloud.t_train.service.TTrainInfoService;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.page.PageRequest;
import com.hjy.cloud.utils.page.PageUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.domin.CommonResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * (TTrainInfo)表服务实现类
 *
 * @author makejava
 * @since 2021-03-01 16:41:42
 */
@Service("tTrainInfoService")
public class TTrainInfoServiceImpl implements TTrainInfoService {

    @Resource
    private TTrainInfoMapper tTrainInfoMapper;
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
     * @param tTrainInfo
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TTrainInfo<User> tTrainInfo) {
        tTrainInfo.setPkInfoId(IDUtils.getUUID());
        //1
        StringBuffer stringBuffer = new StringBuffer();
        if(tTrainInfo.getJoin()!= null){
            for (User o : tTrainInfo.getJoin()) {
                stringBuffer.append(o.getUserId()+",");
            }
        }
        tTrainInfo.setOurJoin(stringBuffer.toString());
        //2
//        tTrainInfo.getJoin().toString();
//        tTrainInfo.setOurJoin(tTrainInfo.getJoin().toString());
        int i = this.tTrainInfoMapper.insertSelective(tTrainInfo);
        if (i > 0) {
            return new CommonResult(200, "success", "添加数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tTrainInfo
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TTrainInfo<User> tTrainInfo) {
        StringBuffer stringBuffer = new StringBuffer();
        if(tTrainInfo.getJoin()!= null){
            for (User o : tTrainInfo.getJoin()) {
                stringBuffer.append(o.getUserId()+",");
            }
        }
        tTrainInfo.setOurJoin(stringBuffer.toString());
        int i = this.tTrainInfoMapper.updateByPkId(tTrainInfo);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(String pkInfoId) {
        int i = this.tTrainInfoMapper.deleteByPkId(pkInfoId);
        if (i > 0) {
            return new CommonResult(200, "success", "删除数据成功", null);
        } else {
            return new CommonResult(444, "error", "该数据已被删除，无需再次请求，请刷新后重试！", null);
        }
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    @Override
    public CommonResult<PageResult<TTrainInfo>> selectAll(PageRequest<TTrainInfo> pageInfo) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        List<TTrainInfo> list = this.tTrainInfoMapper.selectAllByEntity(pageInfo.getParam());
        Iterator<TTrainInfo> infoIterator = list.iterator();
        while (infoIterator.hasNext()){
            TTrainInfo next = infoIterator.next();
            String ourJoin = next.getOurJoin();
            if(!StringUtils.isEmpty(ourJoin)){
                ourJoin = ourJoin.substring(0,ourJoin.length()-1);
                String[] ids = ourJoin.split(",");
                //查询
                List<User> userList = this.tSysUserMapper.selectId_NameByIds(ids);
                next.setJoin(userList);
                next.setOurJoin(null);
            }
        }
        PageResult result = PageUtil.getPageResult(new PageInfo<TTrainInfo>(list));
        return new CommonResult(200, "success", "获取数据成功", result);
    }

    /**
     * 获取单个数据
     *
     * @return
     */
    @Override
    public CommonResult<ActiveResult<TTrainInfo>> selectById(String pkInfoId) {
        TTrainInfo entity = this.tTrainInfoMapper.selectByPkId(pkInfoId);
        String ourJoin = entity.getOurJoin();
        if(!StringUtils.isEmpty(ourJoin)){
            ourJoin = ourJoin.substring(0,ourJoin.length()-1);
            String[] ids = ourJoin.split(",");
            //查询
            List<User> userList = this.tSysUserMapper.selectId_NameByIds(ids);
            entity.setJoin(userList);
            entity.setOurJoin(null);
        }
        ActiveResult<TTrainInfo> activeResult = new ActiveResult();
        activeResult.setEntity(entity);
        return new CommonResult(200, "success", "获取数据成功", activeResult);
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TTrainInfo entity = new TTrainInfo();
        List<TTrainInfo> list = this.tTrainInfoMapper.selectAllByEntity(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TTrainInfo>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("pageResult", result);
        return resultJson;
    }
}
    
