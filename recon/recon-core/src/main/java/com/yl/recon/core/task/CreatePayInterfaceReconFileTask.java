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
public class CreatePayInterfaceReconFileTask {
	private static Logger logger = LoggerFactory.getLogger(CreatePayInterfaceReconFileTask.class);


	@Resource
	private CreateReconFileService createReconFileService;


	/**
	 * 生成payinterface对账文件
	 */
	//@PostConstruct
	public void createPayInterfaceReconFileTask() {
		try {
			logger.info("---------createPayInterfaceReconFileTask start!---------");
			long startTime = System.currentTimeMillis();
			this.createReconFileService.createPayInterfaceReconFileTask(MyDateUtils.moveDate(new Date(), -1));
			long endTime = System.currentTimeMillis();
			long runTime = endTime - startTime;
			logger.info("---------createPayInterfaceReconFileTask end 消耗时间:[{}]", runTime + "ms");
		} catch (Exception e) {
			logger.error("生成PayInterface对账文件失败:[{}]", e);
		}
	}
}
