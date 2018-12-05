package com.foyoedu.springsecurity.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {
    /**
     *  秒   分   时   日   月   周几
     *  0    *    *    *    *   MON-SUN(1-7)
     *  0   0/5 14,18  *    *   ?             每天14点整和18点整，每隔5分钟执行一次
     *  0    15   10   ?    *   1-6           每个月的周一到周六10:15分执行一次
     *  0    0     2   ?    *   6L            每个月的最后一个周六凌晨2点执行一次
     *  0    0     2   LW   *   ?             每个月的最后一个工作日凌晨2点执行一次
     *  0    0   2-4   ?    *   2#3           每个月的第三个星期二凌晨2点到4点，每个整点执行一次
     */
    @Scheduled(cron = "0 0 2 ? * 1-7")
    public void scheduledClearData() {
        System.out.print("hello");
    }
}
