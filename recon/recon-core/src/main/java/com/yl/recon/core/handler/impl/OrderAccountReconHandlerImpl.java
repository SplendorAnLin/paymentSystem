package com.yl.recon.core.handler.impl;

import com.yl.recon.core.handler.ReconHandler;
import com.yl.recon.core.service.OrderAccountHandleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 订单和账户对账业务处理
 *
 * @author AnLin
 * @since 2017/6/23
 */
@Component("orderAccountReconHandler")
public class OrderAccountReconHandlerImpl implements ReconHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ReconHandler.class);
	
	 
	@Resource
	OrderAccountHandleService orderAccountHandleService;

	 
 	@Override
	public void executeByDb(Date date) {
		logger.info("---------OrderAccountReconHandlerImpl start!---------");
    	long startTime=System.currentTimeMillis();
    	orderAccountHandleService.executeByDb(date);
    	long endTime=System.currentTimeMillis();
     	long runTime = endTime-startTime;
     	logger.info("---------OrderAccountReconHandlerImpl end 消耗时间:[{}]",runTime+"ms");
	}


}