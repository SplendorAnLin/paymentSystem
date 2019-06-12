package com.yl.recon.core.handler.impl;

import com.yl.recon.core.handler.ReconHandler;
import com.yl.recon.core.service.InterfaceBankHandleService;
import com.yl.recon.core.service.OrderInterfaceHandleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 接口与银行通道对账业务处理
 *
 * @author AnLin
 * @since 2017/6/23
 */
@Component("interfaceBankReconHandler")
public class InterfaceBankReconHandlerImpl implements ReconHandler {

	private static final Logger logger = LoggerFactory.getLogger(ReconHandler.class);


	@Resource
	private InterfaceBankHandleService interfaceBankHandleService;


	/**
	 * 订单与接口
	 * @param reconDate
	 */
	@Override
	public void executeByDb(Date reconDate) {
		logger.info("---------InterfaceBankReconHandler start!---------");
		long startTime = System.currentTimeMillis();
		interfaceBankHandleService.executeByDb(reconDate);
		long endTime = System.currentTimeMillis();
		long runTime = endTime - startTime;
		logger.info("---------InterfaceBankReconHandler end 消耗时间:[{}]", runTime + "ms");
	}




}