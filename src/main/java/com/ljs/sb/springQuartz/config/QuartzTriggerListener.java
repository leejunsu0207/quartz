package com.ljs.sb.springQuartz.config;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzTriggerListener implements TriggerListener {

    private final Logger logger = LoggerFactory.getLogger(QuartzTriggerListener.class);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        logger.info(" >>>>>>>>>>>>>> Trigger 실행");
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        // 결과가 true이면 JobListener jobExecutionVetoed(Job 중지) 실행
        logger.info(" >>>>>>>>>>>>>> Trigger 상태 체크");
        JobDataMap map = context.getJobDetail().getJobDataMap();
        int excuteCount = 1;
        if(map.containsKey("executeCount")){
            excuteCount = (int) map.get("executeCount");
        }
        return excuteCount >= 5;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        logger.info(" >>>>>>>>>>>>>> Trigger 성공");
    }
}
