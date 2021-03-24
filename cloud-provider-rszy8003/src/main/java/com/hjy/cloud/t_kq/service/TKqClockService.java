package com.hjy.cloud.t_kq.service;

import com.hjy.cloud.t_kq.entity.TKqClock;
import com.hjy.cloud.domin.CommonResult;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * (TKqClock)表服务接口
 *
 * @author makejava
 * @since 2021-03-19 17:46:25
 */
public interface TKqClockService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage(HttpServletRequest request) throws ParseException;

    /**
     * 添加数据
     *
     * @param tKqClock
     * @return
     */
    CommonResult insert(TKqClock tKqClock,HttpServletRequest request) throws ParseException;

    /**
     * 修改数据
     *
     * @param tKqClock
     * @return
     */
    CommonResult updateByPkId(TKqClock tKqClock);

    /**
     * 删除数据
     *
     * @param tKqClock
     * @return
     */
    CommonResult delete(TKqClock tKqClock);

    /**
     * 查询所有数据
     *
     * @param param
     * @return
     */
    CommonResult selectAll(String param);

    /**
     * 获取单个数据
     *
     * @param tKqClock 实体对象
     * @return
     */
    CommonResult selectById(TKqClock tKqClock);
}
