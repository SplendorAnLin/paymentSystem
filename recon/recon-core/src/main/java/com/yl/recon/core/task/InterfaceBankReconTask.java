package com.yl.recon.core.task;

import com.yl.recon.core.handler.ReconHandler;
import com.yl.utils.date.MyDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

/**
 * 聚合支付有限公司
 *
 * @author 邱健
 * @version V1.0.0
 * @create 2018年01月09
 * @desc 接口和通道对账
 **/
public class InterfaceBankReconTask {


	@Resource
	private ReconHandler interfaceBankReconHandler;

	private static final Logger logger = LoggerFactory.getLogger(OrderAccountReconTask.class);


	/**
	 * 接口和通道对账
	 */
	//@PostConstruct
	public void execute() {
		try {
			interfaceBankReconHandler.executeByDb(MyDateUtils.moveDate(new Date(), -1));
		} catch (Exception e) {
			logger.error("interfaceBankReconHandler is error:[{}]", e);
		}
	}
}
