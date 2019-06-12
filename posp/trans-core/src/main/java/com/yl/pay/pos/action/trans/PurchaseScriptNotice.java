package com.yl.pay.pos.action.trans;

import com.pay.common.util.DateUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.ExtendPayBean;
import org.apache.log4j.Logger;

public class PurchaseScriptNotice  extends BaseTransaction implements ITransaction{
	private static final Logger logger = Logger.getLogger(PurchaseScriptNotice.class);

	/*
	 * (non-Javadoc)
	 * @see com.com.yl.pay.pos.action.trans.ITransaction#execute(com.com.yl.pay.pos.bean.ExtendPayBean)
	 */
	@Override
	public ExtendPayBean execute(ExtendPayBean extendPayBean) throws Exception {
		logger.info("######## PurchaseScriptNotice ########");
		extendPayBean.getUnionPayBean().setResponseCode(Constant.SUCCESS_POS_RESPONSE);
		extendPayBean.getUnionPayBean().setDateLocalTransaction(DateUtil.formatNow("MMdd"));
		extendPayBean.getUnionPayBean().setTimeLocalTransaction(DateUtil.formatNow("HHmmss"));
		extendPayBean.getUnionPayBean().setDateSettlement(DateUtil.formatNow("MMdd"));
		return extendPayBean;
//		PosRequest posRequest = extendPayBean.getPosRequest();
//		// 原Order及原Payment加载及校验
//		Order sourceOrder = orderDao.findByExtrId(extendPayBean.getUnionPayBean().getRetrievalReferenceNumber(), OrderStatus.SUCCESS);
//		if (sourceOrder == null||!OrderStatus.SUCCESS.equals(sourceOrder.getStatus())) { 
//			extendPayBean.getUnionPayBean().setResponseCode(Constant.SUCCESS_POS_RESPONSE);
//			extendPayBean.getUnionPayBean().setDateLocalTransaction(DateUtil.formatNow("MMdd"));
//			extendPayBean.getUnionPayBean().setTimeLocalTransaction(DateUtil.formatNow("HHmmss"));
//			extendPayBean.getUnionPayBean().setDateSettlement(DateUtil.formatNow("MMdd"));
//			return extendPayBean;
//		}
//		if (posRequest.getAmount() <= 0) { throw new TransRunTimeException(TransExceptionConstant.AMOUNT_INVALID); }
//		if (!sourceOrder.getCustomerNo().equals(extendPayBean.getCustomer().getCustomerNo())) { throw new TransRunTimeException(TransExceptionConstant.CUSTOMER_INVALID); }
//		extendPayBean.setOrder(sourceOrder);
//
//		Payment sourcePayment = paymentDao.findSourcePayment(sourceOrder, TransStatus.SUCCESS, TransType.PURCHASE);
//		if (sourcePayment == null) { throw new TransRunTimeException(TransExceptionConstant.SOURCE_PAYMENT_IS_NULL); }
//		extendPayBean.setSourcePayment(sourcePayment);
//
//		List<BankChannelRouteReturnBean> channelRoutes = getBankChannelRoutes(extendPayBean); // 获取可用银行通道列表
//		UnionPayBean posRequestBean = extendPayBean.getUnionPayBean().clone();
//		UnionPayBean bankRequestBean = null;
//		BankResponseDict bankResponseDict = null;
//
//		for (int i = 0; i < channelRoutes.size(); i++) {
//			boolean isEnd = false;
//			if (i == channelRoutes.size() - 1) {
//				isEnd = true;
//			}
//			BankChannelRouteReturnBean channelRouteBean = channelRoutes.get(i);
//			bankRequestBean = posRequestBean.clone(); // POS请求报文数据还原
//			String interfaceCode = channelRouteBean.getBankChannel().getBankInterface().getCode();
//			BankInterfaceTerminal bankTerminal = bankInterfaceTerminalService.loadUseableBankInterfaceTerminal(interfaceCode, channelRouteBean
//					.getBankCustomerNo());
//			if (bankTerminal == null) {
//				if (isEnd) { throw new TransRunTimeException(TransExceptionConstant.NO_USEABLE_BANKTERMINAL); }
//				continue;
//			}
//			extendPayBean.getBankRequestTerminal().copy(bankTerminal);
//
//			extendPayBean.setBankChannelRouteBean(channelRouteBean);
//			String seqName = Constant.BANK_INTERFACE_SEQUENCE_HEAD + interfaceCode;
//			bankRequestBean.setSystemsTraceAuditNumber(sequenceDao.generateSequence(seqName)); // 银行流水号 11域
//			bankRequestBean.setSourcePosRequestId(sourcePayment.getBankRequestId());					//原银行流水号
//			bankRequestBean.setBatchNo(bankTerminal.getBankBatch()); // 批次号
//			bankRequestBean.setCardAcceptorTerminalId(bankTerminal.getBankPosCati()); // 银行终端号
//			bankRequestBean.setCardAcceptorId(bankTerminal.getBankCustomerNo()); // 银行商户号
//			bankRequestBean.setCardAcceptorName(bankTerminal.getBankCustomerName());			//受卡方名称地址
//			bankRequestBean.setMerchantType(bankTerminal.getMcc()); // 银行商户类型MCC
//			bankRequestBean.setRetrievalReferenceNumber(extendPayBean.getOrder().getExternalId()); // 检索参考号
//			extendPayBean.setUnionPayBean(bankRequestBean);
//
//			extendPayBean = paymentService.create(extendPayBean); // 创建订单交易
//			extendPayBean = bankRequestService.create(extendPayBean); // 创建银行交易请求
//
//			// 银行接口调用
//			UnionPayBean bankResponseBean = null;
//			String beanName = StringUtil.replaceUnderline(extendPayBean.getTransType().name()) + interfaceCode;
//			IBankTransaction bankTransaction = bankTransactionFactory.getBankTransaction(beanName);
//			try {
//				bankResponseBean = bankTransaction.execute(extendPayBean);
//			} catch (Exception e) {
//				logger.info("BankNeedReversalException ", e);
//			}
//
//			extendPayBean.setUnionPayBean(bankResponseBean);
//			extendPayBean = bankRequestService.complete(extendPayBean); // 完成银行请求
//			bankResponseDict = bankResponseDictDao.findByCode(interfaceCode, bankResponseBean.getResponseCode()); // 银行返回码处理
//			extendPayBean.setResponseCode(bankResponseDict.getMappingCode());
//			extendPayBean = paymentService.complete(extendPayBean); // 完成支付记录
//
//			if (Constant.SUCCESS_POS_RESPONSE.equals(bankResponseDict.getMappingCode())) {
//				break;
//			} else {
//				bankInterfaceTerminalService.terminalRecover(bankTerminal);
//				List<BankInterfaceSwitchConfig> interfaceSwitchs = bankInterfaceSwitchConfigDao.findByInterfaceCodeAndRespCode(interfaceCode,
//						bankResponseBean.getResponseCode());
//				if (interfaceSwitchs == null || interfaceSwitchs.isEmpty()) {
//					break;
//				}
//			}
//		}
//
//		// 信息转换
//		posRequestBean.setResponseCode(bankResponseDict.getMappingCode()); // 39
//		posRequestBean.setDateLocalTransaction(DateUtil.formatNow("MMdd")); // 13
//		posRequestBean.setTimeLocalTransaction(DateUtil.formatNow("HHmmss")); // 12
//		posRequestBean.setDateSettlement(DateUtil.formatNow("MMdd")); // 14
//		posRequestBean.setAdditionalResponseData(ISO8583Utile.putIssuerandAcquirer(extendPayBean.getBankIdNumber() == null ? "" : extendPayBean
//				.getBankIdNumber().getBin(), "")); // 44
//		posRequestBean.setRetrievalReferenceNumber(extendPayBean.getOrder().getExternalId()); // 37
//		posRequestBean.setAuthorizationCode(extendPayBean.getUnionPayBean().getAuthorizationCode());// 38
//		posRequestBean.setOperator("CUP"); // 63.1
//
//		extendPayBean.setUnionPayBean(posRequestBean);
//		return extendPayBean;

	}
}
