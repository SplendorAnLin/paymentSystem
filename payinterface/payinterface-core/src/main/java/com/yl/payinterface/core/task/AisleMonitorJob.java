package com.yl.payinterface.core.task;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yl.chat.interfaces.WechatInterface;
import com.yl.payinterface.core.service.InterfaceInfoService;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.enums.InterfaceInfoStatus;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.model.InterfaceInfo;
import com.yl.payinterface.core.model.InterfaceRequest;

public class AisleMonitorJob {
	private static final Logger logger = LoggerFactory.getLogger(AisleMonitorJob.class);
	
	@Resource
	InterfaceRequestService interfaceRequestService;
	@Resource
	WechatInterface wechatInterface;
	@Resource
	InterfaceInfoService interfaceInfoService;
	
	public void execute() {
		long startTime = System.currentTimeMillis();
		Properties prop = new Properties();
		try {
			prop.load(AisleMonitorJob.class.getClassLoader().getResourceAsStream("serverHost.properties"));
		} catch (IOException e) {
			logger.error("配置文件读取错误");
		}
		String[] interfaces=prop.getProperty("monitor.interfaceCode").split(",");
		if (new Date().getTime()-converTime(prop.getProperty("monitor.start.time")).getTime()>0&&converTime(prop.getProperty("monitor.end.time")).getTime()-new Date().getTime()>0) {
			for (String code : interfaces) {
				InterfaceInfo interfaceInfo=interfaceInfoService.queryByCode(code);
				if (interfaceInfo!=null&&interfaceInfo.getStatus()==InterfaceInfoStatus.TRUE) {
					InterfaceRequest interfaceReq=interfaceRequestService.queryOneInterfaceRequestByInterfaceCode(code, InterfaceTradeStatus.SUCCESS);
					if (interfaceReq==null||compare_date(new Date(),interfaceReq.getCompleteTime())>Long.valueOf(prop.getProperty("monitor.time"))*1000) {
						wechatInterface.sendEX("payinterface", "1","通道无订单","无","["+interfaceInfo.getName()+"]通道已超过"+Integer.valueOf(prop.getProperty("monitor.time"))/60+"分没有完成订单","请处理!");
					}
				}
			}
			logger.info("扫描接口通信状况用时:" + (System.currentTimeMillis() - startTime) + "ms");
		}
	}
	
	public static Date converTime(String time) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		SimpleDateFormat formatDay = new SimpleDateFormat("yyyyMMdd ");
		String date=formatDay.format(new Date())+time;
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			logger.error("日期转换错误");
		}
		return null;
	}
	
	public static long compare_date(Date DATE1, Date DATE2) {
        return DATE1.getTime()-DATE2.getTime();
    }
}
