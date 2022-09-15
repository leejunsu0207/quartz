package com.ljs.sb.springQuartz.config;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzJobListener implements JobListener {

    private final Logger logger = LoggerFactory.getLogger(QuartzJobListener.class);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        logger.info(" >>>>>>>>>>>>>> Job 수행 전");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        logger.info(" >>>>>>>>>>>>>> Job 중지");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        logger.info(" >>>>>>>>>>>>>> Job 수행 완료");
    }
}
