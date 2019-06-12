package com.yl.pay.pos.task;

import com.lefu.commons.cache.util.CacheUtils;
import com.pay.common.util.DateUtil;
import com.yl.pay.pos.bean.ProxyPayQueyrResponseBean;
import com.yl.pay.pos.constant.ProxyPayConstant;
import com.yl.pay.pos.entity.ProxyPayInfo;
import com.yl.pay.pos.enums.ProxyPayStatus;
import com.yl.pay.pos.service.ProxyPayInfoService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;

/**
 * Title: 银行终端资源回收器
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class ProxyPayRepairService {

	private static final Log log = LogFactory.getLog(ProxyPayRepairService.class);	

	private ProxyPayInfoService proxyPayInfoService;
	public void execute(){
		String taskFlag = CacheUtils.get("task-proxypay",String.class);
		if(null == taskFlag || taskFlag.isEmpty()){
			long startTime = System.currentTimeMillis();
			log.info("proxypay repair execute!");
			
			CacheUtils.setEx("task-proxypay", "TRUE",60*30);
			Date date = new Date();
			Date start = DateUtil.parseToTodayDesignatedDate(date, "00:00:00");
			Date end= new Date(date.getTime() - (60 * 10 * 1000));
			if (start.getTime() < end.getTime()){
				List<ProxyPayInfo> proxyPayInfoList = proxyPayInfoService.findListByStatusAndCreateTime(start,end);
				if(null != proxyPayInfoList && !proxyPayInfoList.isEmpty()){
					for(ProxyPayInfo proxyPayInfo : proxyPayInfoList){
						
						ProxyPayQueyrResponseBean proxyPayQueyrResponseBean = proxyPayInfoService.proxyPayQuery(proxyPayInfo.getBankExternalId());
						if(ProxyPayConstant.PROXY_PAY_SUCC.equals(proxyPayQueyrResponseBean.getCode())){
							proxyPayInfo.setStatus(ProxyPayStatus.PAY_RESP_SUCC);
							proxyPayInfo.setRspMsg(proxyPayQueyrResponseBean.getMsg());
							proxyPayInfo.setRemark("补单成功");
							proxyPayInfoService.update(proxyPayInfo);
						}
					}	
				}
			}
			CacheUtils.del("task-proxypay");
			long endTime = System.currentTimeMillis();
			log.info("proxypay repair execute end! time:"+(endTime-startTime)/1000+"s");
		}
	}
	
	public ProxyPayInfoService getProxyPayInfoService() {
		return proxyPayInfoService;
	}
	public void setProxyPayInfoService(ProxyPayInfoService proxyPayInfoService) {
		this.proxyPayInfoService = proxyPayInfoService;
	}



}
