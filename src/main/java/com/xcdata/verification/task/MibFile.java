package com.xcdata.verification.task;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.task verification
 * @Date: create in 2020.9.11 16:12
 */
@Component
@EnableScheduling
public class MibFile {
    /**
     *  定时下载mib文件, 1分钟执行一次
     */
    @Scheduled(cron = "0 */1 * * * *")
    public void downloadMibFile() {
        
    }
}
