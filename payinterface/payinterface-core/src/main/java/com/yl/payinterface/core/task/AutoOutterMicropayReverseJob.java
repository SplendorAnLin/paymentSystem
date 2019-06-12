package com.yl.payinterface.core.task;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.service.InterfaceRequestReverseService;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.bean.WalletSalesQueryBean;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.enums.ReverseStatus;
import com.yl.payinterface.core.hessian.WalletpayHessianService;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.model.InterfaceRequestReverse;

/**
 * 支付接口 自动补单
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月22日
 * @version V1.0.0
 */
public class AutoOutterMicropayReverseJob {

	private static final Logger logger = LoggerFactory.getLogger(AutoOutterMicropayReverseJob.class);

	/** 最大补单次数 */
	private static final int MAX_COUNT = 100;
	/** 一批次补单量 */
	private static final int BATCH_REVERSE_COUNT = 1000;

	@Resource
	private InterfaceRequestService interfaceRequestService;
	@Resource
	private WalletpayHessianService walletpayHessianService;
	@Resource
	private InterfaceRequestReverseService interfaceRequestReverseService;

	public void execute() {
		long startTime = System.currentTimeMillis();
		logger.info("自动补单（条码支付） 开始");
		// 查询需补单的交易数据
		List<InterfaceRequestReverse> list = interfaceRequestReverseService.queryMicropayReverseOrderInterfaceRequest(MAX_COUNT, BATCH_REVERSE_COUNT);
		if (list != null) {
			Iterator<InterfaceRequestReverse> iterator = list.iterator();
			while (iterator.hasNext()) {
				InterfaceRequestReverse interfaceRequestReverse = iterator.next();
				logger.info("条码支付-接口系统请求资金通道自动补单数据 : {}", JsonUtils.toJsonString(interfaceRequestReverse));

				try {
					InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(interfaceRequestReverse.getInterfaceRequestID());
					if (interfaceRequest.getStatus() == InterfaceTradeStatus.UNKNOWN) {
						Map<String, String> params = new HashMap<>();
						WalletSalesQueryBean walletSalesQueryBean = new WalletSalesQueryBean();
						walletSalesQueryBean.setBusinessOrderID(interfaceRequestReverse.getBussinessOrderID());
						params = walletpayHessianService.query(walletSalesQueryBean);
						if (params != null && !"UNKNOWN".equals(params.get("tranStat"))) {
							interfaceRequestReverse.setInterfaceOrderID(params.get("interfaceOrderID"));
							interfaceRequestReverse.setReverseStatus(ReverseStatus.SUCC_REVERSE);
							interfaceRequestReverseService.modifyInterfaceRequestReverse(interfaceRequestReverse);
							logger.info("条码支付-接口系统通道自动补单完成 : {}", JsonUtils.toJsonString(interfaceRequestReverse));
						} else {
							interfaceRequestReverse.setReverseCount(interfaceRequestReverse.getReverseCount() + 1);
							interfaceRequestReverseService.modifyReverseCount(interfaceRequestReverse);
							logger.info("条码支付-接口系统通道自动补单完成 : {}", JsonUtils.toJsonString(interfaceRequestReverse));
						}
					} else {
						interfaceRequestReverse.setReverseStatus(ReverseStatus.SUCC_REVERSE);
						interfaceRequestReverseService.modifyInterfaceRequestReverse(interfaceRequestReverse);
						logger.info("条码支付-接口系统通道自动补单完成 : {}", JsonUtils.toJsonString(interfaceRequestReverse));
					}
				} catch (Exception e) {
					logger.error("条码支付-接口系统 订单号:" + interfaceRequestReverse.getInterfaceRequestID() + " 补单异常: ", e);
					interfaceRequestReverse.setReverseCount(interfaceRequestReverse.getReverseCount() + 1);
					interfaceRequestReverseService.modifyReverseCount(interfaceRequestReverse);
				}
			}
		}

		logger.info("自动补单（条码支付） 结束  " + (System.currentTimeMillis() - startTime) + "ms");

	}
}