package com.rookie.opcua.job;

import com.rookie.opcua.service.ProfitAndLossCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;

/**
 * @Description: 盘亏资产处理情况表及资产清单
 * @Author: jxj
 * @Date: 2020/9/10 15:52
 */
@Slf4j
public class StatisticsLossCountJob implements ApplicationRunner {
    @Autowired
    private ProfitAndLossCountService profitAndLossCountService;

    @Async
    @Scheduled(cron = "0 0 1 * * ?")
    public void statistics() {
        log.info("-----盘亏资产处理任务开始-----");
        //获取年份
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        //常态化盘点
        profitAndLossCountService.statisticsLossCount(year);
        //专项盘点
        profitAndLossCountService.statisticsBatchLossCount(year);
        log.info("-----盘亏资产处理任务结束-----");

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        statistics();
    }
}
