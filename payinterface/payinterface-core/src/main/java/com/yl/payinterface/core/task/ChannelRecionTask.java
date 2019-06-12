package com.yl.payinterface.core.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.utils.lang.DateFormatUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.yl.payinterface.core.service.InterfaceInfoService;
import com.yl.payinterface.core.handler.ChannelRecionHandler;
import com.yl.payinterface.core.model.InterfaceInfo;

/**
 * 通道下载对账文件定时任务
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月22日
 * @version V1.0.0
 */
public class ChannelRecionTask {
	
	private Logger logger = LoggerFactory.getLogger(ChannelRecionTask.class);
	
	@Resource
	private Map<String, ChannelRecionHandler> channelRecionHandlers;
	@Resource
	private InterfaceInfoService interfaceInfoService;
	
	public void execute(){
		long startTime = System.currentTimeMillis();
		String billDate = DateFormatUtils.format(DateUtils.addDays(new Date(), -1), "yyyyMMdd");
		logger.info("通道下载对账文件定时任务 开始,账单日期: " + billDate);
		List<InterfaceInfo> interfaceInfos = interfaceInfoService.queryInterfaceInfo();
		for(InterfaceInfo interfaceInfo : interfaceInfos){
			ChannelRecionHandler channelRecionHandle = channelRecionHandlers.get(interfaceInfo.getCode());
			if(channelRecionHandle == null){
				logger.warn("通道 :{}未配置对账单下载任务", interfaceInfo.getCode());
				continue;
			}
			Map<String,String> params = new HashMap<>();
			params.put("billDate", billDate);
			params.put("tradeConfigs", interfaceInfo.getTradeConfigs());
			channelRecionHandle.recion(params);
			logger.info("通道下载对账文件定时任务,通道:{}, 下载完成",interfaceInfo.getCode());
		}
		
		logger.info("通道下载对账文件定时任务 完成,总耗时: " + (System.currentTimeMillis() - startTime) + "ms");
	}
	
}
