package com.yl.recon.core.handler.impl;

import com.yl.recon.core.handler.ReconHandler;
import com.yl.recon.core.service.InsertFileToDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 文件数据存到mysql数据库
 *
 * @author AnLin
 * @since 2017/6/23
 */
@Component("insertFileToDbHandler")
public class InsertFileToDbHandlerImpl implements ReconHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ReconHandler.class);
	
	 
	@Resource
	InsertFileToDbService insertFileToDbService;
	

	 
 	@Override
	public void executeByDb(Date reconDate) {
		logger.info("---------InsertFileToDbHandlerImpl start!---------");
    	long startTime=System.currentTimeMillis();
		try {
			insertFileToDbService.executeByDb(reconDate);
		} catch (Exception e) {
			logger.error("InsertFileToDbHandlerImpl异常",e);
		}
		long endTime=System.currentTimeMillis();
     	long runTime = endTime-startTime;
     	logger.info("---------InsertFileToDbHandlerImpl end 消耗时间:[{}]----",runTime+"ms");
	}
	




}