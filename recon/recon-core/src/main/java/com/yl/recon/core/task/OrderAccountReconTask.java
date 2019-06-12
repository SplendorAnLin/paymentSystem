package com.yl.recon.core.task;

import com.yl.recon.core.handler.ReconHandler;
import com.yl.utils.date.MyDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

/**
 * 订单和账户对账定时任务
 *
 * @author AnLin
 * @since 2017/6/22
 */
public class OrderAccountReconTask {


	@Resource
	private ReconHandler orderAccountReconHandler;

	private static final Logger logger = LoggerFactory.getLogger(OrderAccountReconTask.class);


	/**
	 * 订单和账户对账
	 */
	//@PostConstruct
	public void execute() {
		try {
			orderAccountReconHandler.executeByDb(MyDateUtils.moveDate(new Date(), -1));
		} catch (Exception e) {
			logger.error("OrderInterfaceReconTask is error,{}", e);
		}
	}


}