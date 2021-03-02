package com.hjy.cloud.common.task;

import com.hjy.cloud.common.config.rabbitmq.Sender;
import com.hjy.cloud.t_index.dao.TIndexBwlMapper;
import com.hjy.cloud.t_index.entity.TIndexBwl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务配置
 */
@Component
public class TimeTask1 {

    @Resource
    private Sender sender;
    @Resource
    private TIndexBwlMapper tIndexBwlMapper;
    /**
     * 执行定时任务1
     * 0 0 0 * * ?
     */
    @Scheduled(cron = "0 * * * * ?")
    public void task(){
        System.err.println("正在执行定时任务1：");
        List<TIndexBwl> bwls = tIndexBwlMapper.selectAllEffective();
        if(bwls.size() > 0){
            for(TIndexBwl obj:bwls){
                Map<String,Object> rabbitMap = new HashMap<>();
                rabbitMap.put("data",obj);
                sender.send(obj.getFkUserId(),rabbitMap);
            }
            /**
             * 发送完成后修改备忘录发送状态
             */
            int i = tIndexBwlMapper.updateSendStatusBatch(bwls);
        }
    }
}
