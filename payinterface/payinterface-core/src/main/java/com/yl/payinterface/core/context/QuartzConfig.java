package com.yl.payinterface.core.context;

/**
 * Quartz定时配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月11日
 * @version V1.0.0
 */
//@Configuration
public class QuartzConfig {

	/*private static SchedulerFactory sf = new StdSchedulerFactory();
	private static final String JOB_GROUP_NAME = "group_query";
	private static final String TRIGGER_GROUP_NAME = "trigger_query";
	private static final String jobName = "queryTask";
	private static final String queryTaskCron = "0 0/1 * * * ?";

	@SuppressWarnings("deprecation")
	@Bean
	public void initQuartz() throws Exception {
		System.out.println("---------------");
		Scheduler sched = sf.getScheduler();
		System.out.println("---------------");
		JobDetail jobDetail = new JobDetailImpl(jobName, JOB_GROUP_NAME, QueryTask.class);// 任务名，任务组，任务执行类
		System.out.println("---------------");
		// 触发器
		CronTrigger trigger = new CronTriggerImpl(jobName, TRIGGER_GROUP_NAME,queryTaskCron);// 触发器名,触发器组
		sched.scheduleJob(jobDetail, trigger);
		// 启动
		if (!sched.isShutdown())
			sched.start();
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		System.out.println("---------------");
		Scheduler sched = sf.getScheduler();
		JobDetail jobDetail = new JobDetailImpl(jobName, JOB_GROUP_NAME, QueryTask.class);// 任务名，任务组，任务执行类
		// 触发器
		CronTrigger trigger = new CronTriggerImpl(jobName, TRIGGER_GROUP_NAME,queryTaskCron);// 触发器名,触发器组
		sched.scheduleJob(jobDetail, trigger);
		// 启动
		if (!sched.isShutdown())
			sched.start();
	}*/

}
