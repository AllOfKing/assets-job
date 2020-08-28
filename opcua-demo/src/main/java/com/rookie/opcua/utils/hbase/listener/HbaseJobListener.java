package com.rookie.opcua.utils.hbase.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 系统应用的监听类
 * 
 * @author xdh
 * @since
 * @version
 */
public class HbaseJobListener implements ServletContextListener {

	// 日志
	private static final Log log = LogFactory.getLog(HbaseJobListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		if (log.isDebugEnabled()) {
			log.debug("同步用户信息任务启动。");
		}
//		ImpUserDataCronScheduler cronScheduler = new ImpUserDataCronScheduler();
//		cronScheduler.init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent env) {
	}
}
