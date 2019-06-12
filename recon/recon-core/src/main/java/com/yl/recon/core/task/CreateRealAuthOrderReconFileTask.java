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
public class CreateRealAuthOrderReconFileTask {

	private static Logger logger = LoggerFactory.getLogger(CreateRealAuthOrderReconFileTask.class);


	@Resource
	private CreateReconFileService createReconFileService;

	/**
	 * 生成real_auth对账文件
	 */
	//@PostConstruct
	public void createRealAuthOrderReconFileTask() {
		try {
			logger.info("---------createrealAuthOrderReconFileTask start!---------");
			long startTime = System.currentTimeMillis();
			this.createReconFileService.createRealAuthOrderReconFileTask(MyDateUtils.moveDate(new Date(), -1));
			long endTime = System.currentTimeMillis();
			long runTime = endTime - startTime;
			logger.info("---------createrealAuthOrderReconFileTask end 消耗时间:[{}]", runTime + "ms");
		} catch (Exception e) {
			logger.error("生成RealAuth对账文件失败:[{}]", e);
		}
	}
}
