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
public class OrderInterfaceReconTask {


	@Resource
	private ReconHandler orderInterfaceReconHandler;

	private static final Logger logger = LoggerFactory.getLogger(OrderInterfaceReconTask.class);


	/**
	 * 接口和订单对账
	 */
	//@PostConstruct
	public void execute() {
		try {
			orderInterfaceReconHandler.executeByDb(MyDateUtils.moveDate(new Date(), -1));
		} catch (Exception e) {
			logger.error("OrderInterfaceReconTask is error,{}", e);
		}
	}

}