package com.rookie.opcua.utils.hbase.job;

import com.rookie.opcua.utils.hbase.ConstProperties;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class SubTableJobCronScheduler {

	/** log */
	private static Log log = LogFactory.getLog(SubTableJobCronScheduler.class);

	// private static String cron = "* * * * * *";
	/**
	 * 构造函数
	 */
	public SubTableJobCronScheduler() {
	}

	/**
	 * 初始化任务
	 */
//	public void init() {
//		try {
//			if (StringUtils.isEmpty(ConstProperties
//					.findPropertiesByKey("sub_table_time"))) {
//				log.info("不启动分表定时任务调度器。");
//				return;
//			}
//			System.setProperty("org.terracotta.quartz.skipUpdateCheck", "true");
//			JobDetail jobDetail = JobBuilder.newJob(SubTableJob.class)
//					.withIdentity("SubTableJob", "SubTableJobGroup").build();
//			CronTrigger cronTrigger = TriggerBuilder
//					.newTrigger()
//					.withIdentity("SubTableJobCronTrigger",
//							"SubTableJobCronTriggerGroup")
//					.withSchedule(
//							CronScheduleBuilder.cronSchedule(ConstProperties
//									.findPropertiesByKey("sub_table_time")))
//					.build();
//			SchedulerFactory sFactory = new StdSchedulerFactory();
//			Scheduler scheduler = sFactory.getScheduler();
//			scheduler.scheduleJob(jobDetail, cronTrigger);
//			scheduler.start();
//			log.info("启动hbase调度器");
//		} catch (SchedulerException e) {
//			log.error("启动hbase调度器失败！", e);
//		}
//	}

}
