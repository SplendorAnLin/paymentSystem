package com.yl.recon.core.task;

import com.yl.recon.core.handler.ReconHandler;
import com.yl.utils.date.MyDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

/**
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2018年01月05
 */
public class InsertFileToDbTask {

	@Resource
	private ReconHandler insertFileToDbHandler;

	private static final Logger logger = LoggerFactory.getLogger(InsertFileToDbTask.class);


	/**
	 * 将对账文件存入数据库的定时任务
	 */
	//@PostConstruct
	public void execute() {
		try {
			insertFileToDbHandler.executeByDb(MyDateUtils.moveDate(new Date(), -1));
		} catch (Exception e) {
			logger.error("insertFileToDbTask is error,{}", e);
		}
	}


}



