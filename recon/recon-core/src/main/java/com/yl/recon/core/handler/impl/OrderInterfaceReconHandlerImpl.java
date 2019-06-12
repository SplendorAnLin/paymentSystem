package com.yl.recon.core.handler.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yl.recon.core.handler.ReconHandler;
import com.yl.recon.core.service.OrderInterfaceHandleService;

/**
 * 线上订单和账户对账业务处理
 *
 * @author AnLin
 * @since 2017/6/23
 */
@Component("orderInterfaceReconHandler")
public class OrderInterfaceReconHandlerImpl implements ReconHandler {

	private static final Logger logger = LoggerFactory.getLogger(ReconHandler.class);


	@Resource
	OrderInterfaceHandleService orderInterfaceHandleService;


	/**
	 * 线上订单与接口
	 * @param reconDate
	 */
	@Override
	public void executeByDb(Date reconDate) {
		logger.info("---------OrderInterfaceReconHandler start!---------");
		long startTime = System.currentTimeMillis();
		orderInterfaceHandleService.executeByDb(reconDate);
		long endTime = System.currentTimeMillis();
		long runTime = endTime - startTime;
		logger.info("---------OrderInterfaceReconHandler end 消耗时间:[{}]", runTime + "ms");
	}




}