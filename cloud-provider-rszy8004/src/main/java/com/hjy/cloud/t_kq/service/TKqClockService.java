package com.hjy.cloud.t_kq.service;

import com.hjy.cloud.t_kq.entity.ClockAddPage;
import com.hjy.cloud.t_kq.entity.ClockStatistics;
import com.hjy.cloud.t_kq.entity.ParamStatistics;
import com.hjy.cloud.t_kq.entity.TKqClock;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.page.PageResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    CommonResult<ClockAddPage> insertPage(HttpSession session , HttpServletRequest request) throws ParseException;

    /**
     * 添加数据
     *
     * @param tKqClock
     * @return
     */
    CommonResult<ClockAddPage> insert(TKqClock tKqClock,HttpServletRequest request) throws ParseException;

    /**
     * 修改数据
     *
     * @param tKqClock
     * @return
     */
    CommonResult updateByPkId(TKqClock tKqClock) throws ParseException;

    /**
     * 获取单个数据
     *
     * @param tKqClock 实体对象
     * @return
     */
    CommonResult selectById(TKqClock tKqClock);
    /**
     * 个人打卡汇总统计
     * @return 所有数据
     */
    CommonResult<ClockStatistics> statisticsUser(ParamStatistics param, HttpServletRequest request) throws ParseException;
    /**
     * 个人打卡每日记录统计
     * @return 所有数据
     */
    CommonResult<ClockStatistics> userStatisticsEveryDay(ParamStatistics param, HttpServletRequest request);
    /**
     * 计算周次
     * @return 所有数据
     */
    CommonResult getWeekSlot(ParamStatistics param) throws ParseException;
    /**
     * 管理员查询所有打卡信息
     * @return 所有数据
     */
    CommonResult<PageResult<TKqClock>> adminList(String tKqClock) throws ParseException;
}
