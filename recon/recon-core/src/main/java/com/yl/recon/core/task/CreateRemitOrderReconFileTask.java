package com.yl.recon.core.task;

import com.yl.recon.core.service.CreateReconFileService;
import com.yl.utils.date.MyDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 聚合支付有限公司
 *
 * @author 邱健
 * @version V1.0.0
 * @create 2018年01月23
 * @desc
 **/
public class CreateRemitOrderReconFileTask {

	private static Logger logger = LoggerFactory.getLogger(CreateRemitOrderReconFileTask.class);


	@Resource
	private CreateReconFileService createReconFileService;

	/**
	 * 生成dpay订单对账文件
	 */
	//@PostConstruct
	public void createRemitOrderReconFileTask() {
		try {
			logger.info("---------createRemitOrderReconFileTask start!---------");
			long startTime = System.currentTimeMillis();
			this.createReconFileService.createRemitOrderReconFileTask(MyDateUtils.moveDate(new Date(), -1));
			long endTime = System.currentTimeMillis();
			long runTime = endTime - startTime;
			logger.info("---------createRemitOrderReconFileTask end 消耗时间:[{}]", runTime + "ms");
		} catch (Exception e) {
			logger.error("生成dpay对账文件失败:[{}]", e);
		}
	}
}
