package com.yl.dpay.core.task;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yl.dpay.core.entity.Request;
import com.yl.dpay.core.enums.RequestStatus;
import com.yl.dpay.core.service.RequestService;

/**
 * 查询定时任务
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月16日
 * @version V1.0.0
 */
public class QueryTask{
	
	private static Logger logger = LoggerFactory.getLogger(QueryTask.class);
	
	@Resource
	private RequestService requestService;

	public void execute(){
		long start = System.currentTimeMillis();
		logger.info("queryTask start!");
		List<Request> list = requestService.findByStatus(RequestStatus.HANDLEDING);
		for(Request request : list){
			try {
//				Map<String,String> params = interfaceRequestService.query(request.getFlowNo(),request.getAccountType().toString(),
//						request.getAccountNo(),request.getAccountName());
//				logger.info("interfaceRequest responseInfo:"+params);
//				
//				if(params.get("tranStat").equals("SUCCESS") || params.get("tranStat").equals("FAILED")){
//					request.setCompleteMsg(params.get("resMsg"));
//					request.setStatus(RequestStatus.valueOf(params.get("tranStat")));
//					request = requestService.updateStatus(request);
//				}
			} catch (Exception e) {
				logger.error("interfaceRequest query error! flowNo :{} exception:{}", request.getFlowNo() , e);
			}
		}
		logger.info("queryTask end! times : " + (System.currentTimeMillis() - start) + "ms");
	}

}
