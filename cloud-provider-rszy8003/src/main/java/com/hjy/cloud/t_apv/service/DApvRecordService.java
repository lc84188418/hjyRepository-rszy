package com.hjy.cloud.t_apv.service;

import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_apv.entity.DApvRecord;

import java.util.List;

/**
 * (DApvRecord)表服务接口
 *
 * @author makejava
 * @since 2021-06-08 18:09:44
 */
public interface DApvRecordService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @return
     */
    CommonResult insert(DApvRecord dApvRecord);

    /**
     * 修改数据
     *
     * @return
     */
    CommonResult update(DApvRecord dApvRecord);

    /**
     * 删除数据
     *
     * @return
     */
    CommonResult delete(String pkId);

    /**
     * 获取单个数据
     *
     * @return
     */
    DApvRecord selectById(String pkId);

    int insertApvRecordBatch(List<DApvRecord> apvRecordList);

    DApvRecord selectSourceIdById(String pkRecordId);
}
