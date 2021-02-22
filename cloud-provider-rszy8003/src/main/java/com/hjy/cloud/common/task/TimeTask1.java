package com.hjy.cloud.common.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务配置
 */
@Component
public class TimeTask1 {

    //注入mapper
    @Autowired
//    THallTakenumberMapper takenumberMapper;
    /**
     * 执行定时任务1
     * 0 0 0 * * ?
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void task(){
        System.err.println("正在执行定时任务1：");
//        takenumberMapper.deleteAll();
    }
}
