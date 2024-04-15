package org.example.timecoinweb.schedule;


import lombok.extern.slf4j.Slf4j;
import org.example.timecoinweb.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Task {
    @Autowired
    private ActivityService activityService;

    @Scheduled(fixedRate = 60000)//每隔一分钟执行一次
    public void checkActivityStatus(){
        log.info("一分钟到了，将更新过期活动");

        activityService.updateExpired();
    }


}
