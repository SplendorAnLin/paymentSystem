package com.yl.payinterface.core.task;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.yl.payinterface.core.bean.InternetbankQueryBean;
import com.yl.payinterface.core.hessian.InternetbankHessianService;
import com.yl.payinterface.core.hessian.QuickPayHessianService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.service.InterfaceRequestReverseService;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.enums.InterfaceType;
import com.yl.payinterface.core.enums.ReverseStatus;
import com.yl.payinterface.core.hessian.RemitHessianService;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.model.InterfaceRequestReverse;

/**
 * 支付接口 自动补单
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月22日
 * @version V1.0.0
 */
public class AutoOutterReverseJob{

	private static final Logger logger = LoggerFactory.getLogger(AutoOutterReverseJob.class);

	/** 最大补单次数 */
	private static final int MAX_COUNT = 100;
	/** 一批次补单量 */
	private static final int BATCH_REVERSE_COUNT = 200;
	
	@Resource
	private InterfaceRequestService interfaceRequestService;
	@Resource
	private RemitHessianService remitHessianService;
	@Resource
	private InternetbankHessianService internetbankHessianService;
	@Resource
	private InterfaceRequestReverseService interfaceRequestReverseService;
	@Resource
	private QuickPayHessianService quickPayHessianService;

	public void execute() {
		long startTime = System.currentTimeMillis();
		logger.info("自动补单（未获取到资金通道明确支付结果） 开始");
		// 查询需补单的交易数据
		List<InterfaceRequestReverse> list = interfaceRequestReverseService.queryNeedReverseOrderInterfaceRequest(MAX_COUNT, BATCH_REVERSE_COUNT);
		if (list != null) {
			Iterator<InterfaceRequestReverse> iterator = list.iterator();
			while (iterator.hasNext()) {
				InterfaceRequestReverse interfaceRequestReverse = iterator.next();
				logger.info("接口系统请求资金通道自动补单数据 : {}", JsonUtils.toJsonString(interfaceRequestReverse));
				
				try {
					InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(interfaceRequestReverse.getInterfaceRequestID());
					if(interfaceRequest != null && interfaceRequest.getInterfaceInfoCode().contains("REMIT")){
						if(interfaceRequest.getStatus() == InterfaceTradeStatus.UNKNOWN){
							Map<String, String> params = new HashMap<>();
							params.put("businessOrderID", interfaceRequestReverse.getBussinessOrderID());
							params = remitHessianService.query(params);
							if(params!= null && !"UNKNOWN".equals(params.get("tranStat"))){
								interfaceRequestReverse.setInterfaceOrderID(params.get("interfaceOrderID"));
								interfaceRequestReverse.setReverseStatus(ReverseStatus.SUCC_REVERSE);
								interfaceRequestReverseService.modifyInterfaceRequestReverse(interfaceRequestReverse);
								logger.info("接口系统通道自动补单完成 : {}", JsonUtils.toJsonString(interfaceRequestReverse));
							}else{
								interfaceRequestReverse.setReverseCount(interfaceRequestReverse.getReverseCount() + 1);
								interfaceRequestReverseService.modifyReverseCount(interfaceRequestReverse);
								logger.info("接口系统通道自动补单完成 : {}", JsonUtils.toJsonString(interfaceRequestReverse));
							}
						}else{
							interfaceRequestReverse.setReverseStatus(ReverseStatus.SUCC_REVERSE);
							interfaceRequestReverseService.modifyInterfaceRequestReverse(interfaceRequestReverse);
							logger.info("接口系统通道自动补单完成 : {}", JsonUtils.toJsonString(interfaceRequestReverse));
						}
					}else if(interfaceRequest != null && interfaceRequest.getInterfaceInfoCode().contains("B2C")){
						if(interfaceRequest.getStatus() == InterfaceTradeStatus.UNKNOWN){
							Map<String, String> params = new HashMap<>();
							params.put("businessOrderID", interfaceRequestReverse.getBussinessOrderID());
							InternetbankQueryBean internetbankQueryBean = new InternetbankQueryBean();
							internetbankQueryBean.setAmount(interfaceRequest.getAmount());
							internetbankQueryBean.setInterfaceInfoCode(interfaceRequest.getInterfaceInfoCode());
							internetbankQueryBean.setInterfaceRequestID(interfaceRequest.getInterfaceRequestID());
							internetbankQueryBean.setOriginalInterfaceRequestID(interfaceRequest.getOriginalInterfaceRequestID());
							internetbankQueryBean.setOriginalInterfaceRequestTime(interfaceRequest.getCreateTime());
							internetbankQueryBean.setBusinessCompleteType("AUTO_REPAIR");
							params = internetbankHessianService.query(null, internetbankQueryBean);
							if(params!= null && "SUCCESS".equals(params.get("tranStat"))){
								interfaceRequestReverse.setInterfaceOrderID(params.get("interfaceOrderID"));
								interfaceRequestReverse.setReverseStatus(ReverseStatus.SUCC_REVERSE);
								interfaceRequestReverseService.modifyInterfaceRequestReverse(interfaceRequestReverse);
								logger.info("接口系统通道自动补单完成 : {}", JsonUtils.toJsonString(interfaceRequestReverse));
							}else{
								interfaceRequestReverse.setReverseCount(interfaceRequestReverse.getReverseCount() + 1);
								interfaceRequestReverseService.modifyReverseCount(interfaceRequestReverse);
								logger.info("接口系统通道自动补单完成 : {}", JsonUtils.toJsonString(interfaceRequestReverse));
							}
						}else{
							interfaceRequestReverse.setReverseStatus(ReverseStatus.SUCC_REVERSE);
							interfaceRequestReverseService.modifyInterfaceRequestReverse(interfaceRequestReverse);
							logger.info("接口系统通道自动补单完成 : {}", JsonUtils.toJsonString(interfaceRequestReverse));
						}
					} else if (interfaceRequestReverse.getPayType() == InterfaceType.QUICKPAY) {
						ReverseStatus status = ReverseStatus.SUCC_REVERSE;
						if (interfaceRequest.getStatus() == InterfaceTradeStatus.UNKNOWN) {
							Map<String, String> map = quickPayHessianService.queryOrder(interfaceRequest.getInterfaceRequestID(), interfaceRequest.getInterfaceInfoCode());
							if (!"SUCCESS".equals(map.get("tranStat"))) {
								status = ReverseStatus.WAIT_REVERSE;
							}
						}
						interfaceRequestReverse.setReverseStatus(status);
						interfaceRequestReverse.setReverseCount(interfaceRequestReverse.getReverseCount() + 1);
						interfaceRequestReverseService.modifyInterfaceRequestReverse(interfaceRequestReverse);
						logger.info("接口系统通道自动补单完成 : {}", JsonUtils.toJsonString(interfaceRequestReverse));
					}else if(interfaceRequestReverse.getPayType() != InterfaceType.ALIPAYMICROPAY && interfaceRequestReverse.getPayType() != InterfaceType.WXMICROPAY) {
						interfaceRequestReverse.setReverseStatus(ReverseStatus.SUCC_REVERSE);
						interfaceRequestReverseService.modifyInterfaceRequestReverse(interfaceRequestReverse);
						logger.info("接口系统通道自动补单完成 : {}", JsonUtils.toJsonString(interfaceRequestReverse));
					}
				} catch (Exception e) {
					logger.error("接口系统 订单号:"+interfaceRequestReverse.getInterfaceRequestID()+" 补单异常: ", e);
					interfaceRequestReverse.setReverseCount(interfaceRequestReverse.getReverseCount() + 1);
					interfaceRequestReverseService.modifyReverseCount(interfaceRequestReverse);
				}
			}
		}
		
		logger.info("自动补单（未获取到资金通道明确支付结果） 结束  " + (System.currentTimeMillis()-startTime) +"ms");
		
	}
}