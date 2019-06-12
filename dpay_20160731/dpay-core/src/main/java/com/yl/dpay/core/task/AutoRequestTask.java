package com.yl.dpay.core.task;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yl.dpay.core.Utils.HolidayUtils;
import com.yl.dpay.core.entity.AutoRequest;
import com.yl.dpay.core.entity.ServiceConfig;
import com.yl.dpay.core.enums.OwnerRole;
import com.yl.dpay.core.enums.Status;
import com.yl.dpay.core.service.AutoRequestService;
import com.yl.dpay.core.service.ServiceConfigService;
import com.lefu.commons.utils.lang.DateFormatUtils;
import com.lefu.commons.web.spring.ApplicationContextUtils;

/**
 * T+1结算商户 自动代付任务
 *
 * @author AnLin
 * @since 2017年4月25日
 */
public class AutoRequestTask {

	private static final Logger logger = LoggerFactory.getLogger(AutoRequestTask.class);
	private static final int MAX_THREAD_NUM = 100;
	private static final int CORE_THREAD_NUM = 50;
	private static final int QUEUE_NUM = 2000;
	private static final long KEEP_ALIVE_TIME = 500L;

	private ServiceConfigService serviceConfigService;
	private AutoRequestService autoRequestService;

	public void execute() {
		autoRequestService = (AutoRequestService) ApplicationContextUtils.getApplicationContext()
				.getBean("autoRequestService");
		serviceConfigService = (ServiceConfigService) ApplicationContextUtils.getApplicationContext()
				.getBean("serviceConfigService");
		
		long startTime = System.currentTimeMillis();
		logger.info("DPAY-CORE 自动结算定时任务开始");
		
		List<Map<String, String>> owners = autoRequestService.findAllWait();
		
		ThreadPoolExecutor executorService = new ThreadPoolExecutor(CORE_THREAD_NUM, MAX_THREAD_NUM * 2, KEEP_ALIVE_TIME,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(QUEUE_NUM));
		
		for (Map<String, String> owner : owners) {
			executorService.execute(new WorkThread(serviceConfigService, autoRequestService, owner.get("ownerId"), owner.get("ownerRole")));
		}
		
		logger.info("DPAY-CORE 自动结算定时任务完成,结算:" + (owners != null ? +owners.size()
				: 0) + "个用户,耗时间:" + (System.currentTimeMillis() - startTime) + "ms");
	}

}

/**
 * 自动代付发起工作线程
 * 
 * @author AnLin
 * @since 2017年4月25日
 */
class WorkThread implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(WorkThread.class);
	private ServiceConfigService serviceConfigService;
	private AutoRequestService autoRequestService;
	private String ownerId;
	private String ownerRole;
	
	public WorkThread(ServiceConfigService serviceConfigService, AutoRequestService autoRequestService, 
			String ownerId, String ownerRole){
		this.autoRequestService = autoRequestService;
		this.serviceConfigService = serviceConfigService;
		this.ownerId = ownerId;
		this.ownerRole = ownerRole;
	}
	
	@Override
	public void run() {
		String settleDate = DateFormatUtils.format(new Date(), "yyyyMMdd");
		String threadName = ownerRole + "_" + ownerId + "_" + settleDate;
		
		logger.info("自动结算工作线程:[{}],用户编号:[{}],用户角色:[{}],结算日期:[{}]", threadName,ownerId,ownerRole,settleDate);
		
		if (HolidayUtils.isHoliday()) {
			ServiceConfig serviceConfig = serviceConfigService.find(ownerId, "TRUE");
			if(serviceConfig.getHolidayStatus() != Status.TRUE){
				logger.info("自动结算工作线程:[{}],用户编号:[{}],用户角色:[{}],该用户未开通假日付", threadName,ownerId,ownerRole);
				return;
			}
		}
		
		List<AutoRequest> autoRequests = autoRequestService.findWaitByOwner(ownerId, OwnerRole.valueOf(ownerRole));
		if(autoRequests == null || autoRequests.size() == 0){
			logger.info("自动结算工作线程:[{}],用户编号:[{}],用户角色:[{}],该用户已清算完成", threadName,ownerId,ownerRole);
			return;
		}
		try {
			autoRequestService.apply(autoRequests);
		} catch (Exception e) {
			logger.info("自动结算工作线程:[{}],用户编号:[{}],用户角色:[{}],处理异常:[{]]", threadName,ownerId,ownerRole,e);
		}
		
	}

}
