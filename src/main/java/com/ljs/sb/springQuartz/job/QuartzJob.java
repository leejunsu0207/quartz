package com.ljs.sb.springQuartz.job;

import com.ljs.sb.springQuartz.entity.Market;
import com.ljs.sb.springQuartz.repository.MarketRepository;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class QuartzJob implements Job {

    private static Logger logger = LoggerFactory.getLogger(QuartzJob.class);

    @Autowired
    MarketRepository marketRepository;

//    public QuartzJob(MarketRepository marketRepository) {
//        this.marketRepository = marketRepository;
//    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info(" >>>>>>>>>> Quartz Job Executed");

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        logger.info("dataMap date : {}", dataMap.get("date"));
        logger.info("dataMap executeCount : {}", dataMap.get("executeCount"));

        // JobDataMap을 통해 Job의 실행 횟수를 받아 횟수++을 한다.
        int cnt = (int) dataMap.get("executeCount");
        dataMap.put("executeCount", ++cnt);

        // Market 테이블에 ljs_현재시간 데이터 insert
        Market market = new Market();
        market.setName(String.format("ljs_%s", dataMap.get("date")));
        market.setPrice(3000);
//        marketRepository.save(market);
        logger.info("market info : {}", market);
    }
}
