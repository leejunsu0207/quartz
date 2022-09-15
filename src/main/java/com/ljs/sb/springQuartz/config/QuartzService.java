package com.ljs.sb.springQuartz.config;

import com.ljs.sb.springQuartz.job.QuartzJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class QuartzService {

    private final Logger logger = LoggerFactory.getLogger(QuartzService.class);

    Scheduler scheduler;

    public QuartzService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void init(){

        try {

            // 스케줄러 초기화 -> db clear
            scheduler.clear();

            // job listener 등록
            scheduler.getListenerManager().addJobListener(new QuartzJobListener());

            // Trigger listener 등록
            scheduler.getListenerManager().addTriggerListener(new QuartzTriggerListener());

            // job parameter 생성
            Map map = new HashMap<>();
            // job 실행 횟수 및 실행 시간
            map.put("executeCount", 1);
            map.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            // job 생성 및 Scheduler에 등록
            addJob(QuartzJob.class, "QuartzJob", "Quartz Job 입니다.", map, "0/5 * * * * ?");

        } catch (Exception e){
            logger.error("addJob ERROR : {}", e);
        }

    }


    // job 추가
    public <T extends Job> void addJob(Class<? extends Job> job, String name, String desc, Map map, String cron) throws SchedulerException{
        JobDetail jobDetail = buildJobDetail(job, name, desc, map);
        Trigger trigger = buildCronTrigger(cron);
        if(scheduler.checkExists(jobDetail.getKey())) {
            scheduler.deleteJob(jobDetail.getKey());
        }
        scheduler.scheduleJob(jobDetail, trigger);
    }

    // job detail 생성
    public <T extends Job> JobDetail buildJobDetail(Class<? extends Job> job, String name, String desc, Map map){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(map);

        return JobBuilder
                .newJob(job)
                .withIdentity(name)
                .withDescription(desc)
                .usingJobData(jobDataMap)
                .build();
    }

    // Trigger 생성
    private Trigger buildCronTrigger(String cron){
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
    }

}
