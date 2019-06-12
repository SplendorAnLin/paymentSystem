package com.yl.pay.pos.task;

import com.pay.common.util.DateUtil;
import com.pay.common.util.StringUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.BankChannelRouteReturnBean;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.dao.*;
import com.yl.pay.pos.entity.*;
import com.yl.pay.pos.enums.*;
import com.yl.pay.pos.interfaces.BankTransactionFactory;
import com.yl.pay.pos.interfaces.IBankTransaction;
import com.yl.pay.pos.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Title: 银行冲正任务管理
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */

public class BankReversalTaskManage {
	private static final Log log=LogFactory.getLog(BankReversalTaskManage.class);
	private IBankReversalTaskService bankReversalTaskService;
	private IBankChannelRouteService bankChannelRouteService;
	private IBankChannelService bankChannelService;
	private BankChannelCustomerConfigDao bankChannelCustomerConfigDao;
	private IBankInterfaceTerminalService bankInterfaceTerminalService;	
	private CustomerDao customerDao;
	private CardInfoDao cardInfoDao;
	private SequenceDao sequenceDao;
	private IPaymentService paymentService;
	private IBankRequestService bankRequestService;
	private BankTransactionFactory bankTransactionFactory;
	private BankResponseDictDao bankResponseDictDao;
	private PaymentDao paymentDao;
	private OrderDao orderDao;
	private IOrderService orderService;
	private PosDao posDao;
	private ShopDao shopDao;
	private BankInterfaceTerminalDao bankInterfaceTerminalDao;
	
	public void execute(){
		log.info("---------BankReversalTaskManage start on="+DateUtil.formatDate(new Date()));
		
		List<BankReversalTask> bankReversalTasks=bankReversalTaskService.findByClaimAndStatus(YesNo.N, BankReversalTaskStatus.INIT);
		List<BankReversalTask> finalBankReversalTasks=new LinkedList<BankReversalTask>();
		
		if(bankReversalTasks!=null&&!bankReversalTasks.isEmpty()){
			for(BankReversalTask bankReversalTask:bankReversalTasks){
				if(ReversalType.PURCHASE_VOID.equals(bankReversalTask.getReversalType())){
					continue;
				}
				//任务认领
				bankReversalTask.setClaim(YesNo.Y);
				try {
					bankReversalTask=bankReversalTaskService.update(bankReversalTask);
				} catch (Throwable e) {
					e.printStackTrace();
					continue;
				}
				finalBankReversalTasks.add(bankReversalTask);
			}
		}
		
		for(BankReversalTask bankReversalTask:finalBankReversalTasks){
			try {
				bankReversalTask=bankReversal(bankReversalTask);
			} catch (Throwable e) {
				log.info("-----BankReversalTaskManage bankReversal id="+bankReversalTask.getId(),e);
				continue;
			}finally{
				bankReversalTask.setClaim(YesNo.N);
				bankReversalTaskService.update(bankReversalTask);
			}
		}
		
		log.info("---------BankReversalTaskManage end on="+DateUtil.formatDate(new Date()));
	}
	
	public BankReversalTask bankReversal(BankReversalTask bankReversalTask){
		Payment sourceReversalPayment=paymentDao.findById(bankReversalTask.getSourceReversalPayment().getId());
		Payment sourcePayment=paymentDao.findById(sourceReversalPayment.getSourcePayment().getId());
		Order sourceOrder=orderDao.findById(sourcePayment.getOrder().getId());
		UnionPayBean bankRequestBean=new UnionPayBean();
		
		Date tempTime=DateUtil.parseToTodayDesignatedDate(new Date(), Constant.SETTLE_END_TIME);
		if(tempTime.compareTo(sourceOrder.getCreateTime())>0||YesNo.Y.equals(sourceOrder.getCreditStatus())){
			bankReversalTask.setStatus(BankReversalTaskStatus.INVALID);
			bankReversalTask=bankReversalTaskService.update(bankReversalTask);
			return bankReversalTask;
		}
		
		TransType transType=sourceReversalPayment.getTransType();
		
		Customer customer=customerDao.findByCustomerNo(sourcePayment.getCustomerNo());
		Pos pos=posDao.findByPosCati(sourcePayment.getPosCati());
		Shop shop=shopDao.findById(sourceOrder.getShop().getId());
		CardInfo cardInfo=cardInfoDao.findById(sourcePayment.getCardInfo().getId());
		PosRequest posRequest=new PosRequest();
		posRequest.setCurrencyType(sourcePayment.getCurrencyType());
		posRequest.setPan(sourcePayment.getPan());
		posRequest.setTransType(transType);
		
		ExtendPayBean extendPayBean=new ExtendPayBean();
		extendPayBean.setTransType(transType);
		extendPayBean.setCustomer(customer);
		extendPayBean.setPos(pos);
		extendPayBean.setCardInfo(cardInfo);
		
		extendPayBean.setUnionPayBean(bankRequestBean);		
		extendPayBean.setSourcePayment(sourcePayment);
		extendPayBean.setOrder(sourceOrder);
		extendPayBean.setShop(shop);
		extendPayBean.setPosRequest(posRequest);
		extendPayBean.setTransAmount(sourcePayment.getAmount());
		
		//判断订单入账情况，交易已入账需要出账才能撤销
//		if(ReversalType.POS_REVERSAL.equals(bankReversalTask.getReversalType())){
//			if(TransType.PURCHASE_REVERSAL.equals(transType)||TransType.PRE_AUTH_COMP_REVERSAL.equals(transType)){
//				accountOpService.accountDebit(sourceOrder, extendPayBean);
//			}
//		}
		
		String interfaceCode=sourcePayment.getBankInterface().getCode();
		//校验银行商户号是否可用
		BankChannelRouteReturnBean returnParam=checkBankChannelAndBankCustomer(posRequest, sourcePayment.getIssuer().getCode(), customer.getMcc(), cardInfo.getCardType(), 
				sourcePayment.getBankChannel().getCode(),customer.getCustomerNo(), sourcePayment.getBankCustomerNo(), interfaceCode);
		if(returnParam==null){
			return bankReversalTask;
		}
		
		//获取可用银行终端
		BankInterfaceTerminal bankTerminal = bankInterfaceTerminalService.loadUseableBankInterfaceTerminal(interfaceCode, sourcePayment.getBankCustomerNo(),sourcePayment.getBankPosCati());			
		if(bankTerminal == null){
			return bankReversalTask;
		}		
		
		
		try {
			String seqName = Constant.BANK_INTERFACE_SEQUENCE_HEAD + interfaceCode;
			bankRequestBean.setSystemsTraceAuditNumber(sequenceDao.generateSequence(seqName));							//银行流水号 11域 
			bankRequestBean.setTimeLocalTransaction(DateUtil.formatDate(sourcePayment.getCreateTime(), "HHmmss"));		//时间 12域
			bankRequestBean.setDateLocalTransaction(DateUtil.formatDate(sourcePayment.getCreateTime(), "MMdd"));		//日期 13域
			bankRequestBean.setSourcePosRequestId(sourcePayment.getBankRequestId());									//原银行流水号
			bankRequestBean.setBatchNo(sourcePayment.getBankBatch());													//批次号
			bankRequestBean.setCardAcceptorTerminalId(sourcePayment.getBankPosCati());									//银行终端号
			bankRequestBean.setCardAcceptorId(sourcePayment.getBankCustomerNo());										//银行商户号
			bankRequestBean.setPan(sourcePayment.getPan());																//卡号
			bankRequestBean.setAmount(bankReversalTask.getAmount());													//金额
			bankRequestBean.setPosEntryModeCode(bankReversalTask.getPosEntryModeCode());								//POS输入方式码
			String sourceTransDate=sourcePayment.getBankDate()+sourcePayment.getBankTime();
			if(StringUtils.isBlank(sourcePayment.getBankDate())&&StringUtils.isBlank(sourcePayment.getBankTime())){
				sourceTransDate=null;
			}
			bankRequestBean.setSourceTranDate(sourceTransDate);															//原银行交易日期+时间
			bankRequestBean.setCardAcceptorName(customer.getFullName());												//受卡方名称地址
			bankRequestBean.setMerchantType(bankTerminal.getMcc());														//银行商户类型MCC
			bankRequestBean.setRetrievalReferenceNumber(sourceOrder.getExternalId());									//检索参考号
			bankRequestBean.setSwitchingData(sourcePayment.getInnerPan());												//转入卡号
			extendPayBean.getBankRequestTerminal().copy(bankTerminal);													//银行终端信息	
			extendPayBean.setBankChannelRouteBean(returnParam);
			extendPayBean = paymentService.create(extendPayBean,transType);							//创建Payment
			extendPayBean = bankRequestService.create(extendPayBean,transType);						//创建BankRequest	
			
			UnionPayBean bankResponseBean = null;
			String beanName = StringUtil.replaceUnderline(transType.name()) + interfaceCode;
			IBankTransaction bankTransaction = bankTransactionFactory.getBankTransaction(beanName);
			//银行接口调用
			bankResponseBean = bankTransaction.execute(extendPayBean);
			
			extendPayBean.setUnionPayBean(bankResponseBean);
			extendPayBean = bankRequestService.complete(extendPayBean);							//完成BankRequest
			
			BankResponseDict bankResponseDict = bankResponseDictDao.findByCode(interfaceCode, bankResponseBean.getResponseCode());	//银行返回码处理
			extendPayBean.setResponseCode(bankResponseDict.getMappingCode());
			extendPayBean = paymentService.complete(extendPayBean);								//完成Payment	
			
			//对于POS端发起的冲正，需要更改订单状态
			if(ReversalType.POS_REVERSAL.equals(bankReversalTask.getReversalType())
					&&Constant.SUCCESS_POS_RESPONSE.equals(bankResponseDict.getMappingCode())){
				if(TransType.PURCHASE_REVERSAL.equals(transType)){
					sourceOrder.setStatus(OrderStatus.REVERSALED);
					sourceOrder=orderService.update(sourceOrder);
				}else if(TransType.PURCHASE_VOID_REVERSAL.equals(transType)){
					sourceOrder.setStatus(OrderStatus.SUCCESS);
					sourceOrder=orderService.update(sourceOrder);
				}else if(TransType.PRE_AUTH_REVERSAL.equals(transType)){
					sourceOrder.setStatus(OrderStatus.REVERSALED);
					sourceOrder=orderService.update(sourceOrder);
				}else if(TransType.PRE_AUTH_VOID_REVERSAL.equals(transType)){
					sourceOrder.setStatus(OrderStatus.AUTHORIZED);
					sourceOrder=orderService.update(sourceOrder);
				}else if(TransType.PRE_AUTH_COMP_REVERSAL.equals(transType)){
					Payment preAuthPayment=paymentDao.findById(sourcePayment.getSourcePayment().getId());
					sourceOrder.setStatus(OrderStatus.AUTHORIZED);
					sourceOrder.setAmount(preAuthPayment.getAmount());
					sourceOrder.setPosBatch(preAuthPayment.getPosBatch());
					sourceOrder.setPosRequestId(preAuthPayment.getPosRequestId());
					sourceOrder=orderService.update(sourceOrder);
				}else if(TransType.PRE_AUTH_COMP_VOID_REVERSAL.equals(transType)){
					Payment preAuthCompPayment=paymentDao.findById(sourcePayment.getSourcePayment().getId());
					sourceOrder.setStatus(OrderStatus.SUCCESS);
					sourceOrder.setAmount(preAuthCompPayment.getAmount());
					sourceOrder.setPosBatch(preAuthCompPayment.getPosBatch());
					sourceOrder.setPosRequestId(preAuthCompPayment.getPosRequestId());
					sourceOrder=orderService.update(sourceOrder);
				}
				extendPayBean.setOrder(sourceOrder);
			}
			
		} catch (Throwable e) {
			log.info("------- BankReversalTask send bank handle fail ",e);
			return bankReversalTask;
		}finally{
			bankInterfaceTerminalService.terminalRecover(bankTerminal);	//银行终端回收	
			
		}
		

		
		bankReversalTask.setCompleteTime(new Date());
		bankReversalTask.setStatus(BankReversalTaskStatus.SUCCESS);
		bankReversalTask=bankReversalTaskService.update(bankReversalTask);
		return bankReversalTask;
	}
	
	public void executePurchaseVoid(){
		log.info("---------BankReversalTaskManage executePurchaseVoid start on="+DateUtil.formatDate(new Date()));
		
		List<BankReversalTask> bankReversalTasks=bankReversalTaskService.findByClaimAndStatus(YesNo.N, BankReversalTaskStatus.INIT);
		List<BankReversalTask> finalBankReversalTasks=new LinkedList<BankReversalTask>();
		
		if(bankReversalTasks!=null&&!bankReversalTasks.isEmpty()){
			for(BankReversalTask bankReversalTask:bankReversalTasks){
				if(!ReversalType.PURCHASE_VOID.equals(bankReversalTask.getReversalType())){
					continue;
				}
				//任务认领
				bankReversalTask.setClaim(YesNo.Y);
				try {
					bankReversalTask=bankReversalTaskService.update(bankReversalTask);
				} catch (Throwable e) {
					e.printStackTrace();
					continue;
				}
				finalBankReversalTasks.add(bankReversalTask);
			}
		}
		
		for(BankReversalTask bankReversalTask:finalBankReversalTasks){
			try {
				bankReversalTask=bankPurchaseVoid(bankReversalTask);
			} catch (Throwable e) {
				log.info("-----BankReversalTaskManage bankReversal id="+bankReversalTask.getId(),e);
				continue;
			}finally{
				bankReversalTask.setClaim(YesNo.N);
				bankReversalTaskService.update(bankReversalTask);
			}
		}
		
		log.info("---------BankReversalTaskManage end on="+DateUtil.formatDate(new Date()));
	}
	
	public BankReversalTask bankPurchaseVoid(BankReversalTask bankReversalTask){
		Payment sourceReversalPayment=paymentDao.findById(bankReversalTask.getSourceReversalPayment().getId());
		Payment sourcePayment=paymentDao.findById(sourceReversalPayment.getSourcePayment().getId());
		Order sourceOrder=orderDao.findById(sourcePayment.getOrder().getId());
		UnionPayBean bankRequestBean=new UnionPayBean();
		
		Date tempTime=DateUtil.parseToTodayDesignatedDate(new Date(), Constant.SETTLE_END_TIME);
		if(tempTime.compareTo(sourceOrder.getCreateTime())>0){
			bankReversalTask.setStatus(BankReversalTaskStatus.INVALID);
			bankReversalTask=bankReversalTaskService.update(bankReversalTask);
			return bankReversalTask;
		}
		
		TransType transType=sourceReversalPayment.getTransType();
		
		Customer customer=customerDao.findByCustomerNo(sourcePayment.getCustomerNo());
		Pos pos=posDao.findByPosCati(sourcePayment.getPosCati());
		Shop shop=shopDao.findById(sourceOrder.getShop().getId());
		CardInfo cardInfo=cardInfoDao.findById(sourcePayment.getCardInfo().getId());
		PosRequest posRequest=new PosRequest();
		posRequest.setCurrencyType(sourcePayment.getCurrencyType());
		posRequest.setPan(sourcePayment.getPan());
		posRequest.setTransType(transType);
		
		ExtendPayBean extendPayBean=new ExtendPayBean();
		extendPayBean.setTransType(transType);
		extendPayBean.setCustomer(customer);
		extendPayBean.setPos(pos);
		extendPayBean.setCardInfo(cardInfo);
		
		extendPayBean.setSourcePayment(sourcePayment);
		extendPayBean.setOrder(sourceOrder);
		extendPayBean.setShop(shop);
		extendPayBean.setPosRequest(posRequest);
		extendPayBean.setTransAmount(sourcePayment.getAmount());
		
//		//判断订单入账情况，交易已入账需要出账才能撤销
//		if(ReversalType.POS_REVERSAL.equals(bankReversalTask.getReversalType())){
//			if(TransType.PURCHASE_REVERSAL.equals(transType)||TransType.PRE_AUTH_COMP_REVERSAL.equals(transType)){
//				accountOpService.accountDebit(sourceOrder, extendPayBean);
//			}
//		}
		
		String interfaceCode=sourcePayment.getBankInterface().getCode();
		//校验银行商户号是否可用
		BankChannelRouteReturnBean returnParam=checkBankChannelAndBankCustomer(posRequest, sourcePayment.getIssuer().getCode(), customer.getMcc(), cardInfo.getCardType(), 
				sourcePayment.getBankChannel().getCode(),customer.getCustomerNo(), sourcePayment.getBankCustomerNo(), interfaceCode);
		if(returnParam==null){
			return bankReversalTask;
		}
		
		//获取可用银行终端
		BankInterfaceTerminal bankTerminal = bankInterfaceTerminalService.loadUseableBankInterfaceTerminal(interfaceCode, sourcePayment.getBankCustomerNo(),sourcePayment.getBankPosCati());			
		if(bankTerminal == null){
			return bankReversalTask;
		}		
		
		try {
			String seqName = Constant.BANK_INTERFACE_SEQUENCE_HEAD + interfaceCode;
			bankRequestBean.setSystemsTraceAuditNumber(sequenceDao.generateSequence(seqName));							//银行流水号 11域 
			bankRequestBean.setSourcePosRequestId(sourcePayment.getBankRequestId());									//原银行流水号
			bankRequestBean.setTimeLocalTransaction(DateUtil.formatDate(new Date(), "HHmmss"));		//时间 12域
			bankRequestBean.setDateLocalTransaction(DateUtil.formatDate(new Date(), "MMdd"));		//日期 13域
			bankRequestBean.setBatchNo(sourcePayment.getBankBatch());													//批次号
			bankRequestBean.setCardAcceptorTerminalId(sourcePayment.getBankPosCati());									//银行终端号
			bankRequestBean.setCardAcceptorId(sourcePayment.getBankCustomerNo());										//银行商户号
			bankRequestBean.setPan(sourcePayment.getPan());																//卡号
			bankRequestBean.setAmount(bankReversalTask.getAmount());													//金额
			bankRequestBean.setPosEntryModeCode(bankReversalTask.getPosEntryModeCode());								//POS输入方式码
			String sourceTransDate=sourcePayment.getBankDate()+sourcePayment.getBankTime();
			if(StringUtils.isBlank(sourcePayment.getBankDate())&&StringUtils.isBlank(sourcePayment.getBankTime())){
				sourceTransDate=null;
			}
			if(cardInfo!=null&&cardInfo.getTrack2()!=null){
				bankRequestBean.setTrack2(cardInfo.getTrack2());
			}
			bankRequestBean.setSourceTranDate(sourceTransDate);															//原银行交易日期+时间
			bankRequestBean.setCardAcceptorName(customer.getFullName());												//受卡方名称地址
			bankRequestBean.setMerchantType(bankTerminal.getMcc());														//银行商户类型MCC
			bankRequestBean.setRetrievalReferenceNumber(sourceOrder.getExternalId());									//检索参考号
			bankRequestBean.setSwitchingData(sourcePayment.getInnerPan());												//转入卡号
			bankRequestBean.setAuthorizationCode(sourcePayment.getAuthorizationCode());									//授权码
			extendPayBean.getBankRequestTerminal().copy(bankTerminal);													//银行终端信息	
			extendPayBean.setBankChannelRouteBean(returnParam);
			extendPayBean.setUnionPayBean(bankRequestBean);		
			extendPayBean.setUnionPayBean(bankRequestBean);		
			extendPayBean = paymentService.create(extendPayBean,transType);							//创建Payment
			extendPayBean = bankRequestService.create(extendPayBean,transType);						//创建BankRequest	
			
			UnionPayBean bankResponseBean = null;
			String beanName = StringUtil.replaceUnderline(transType.name()) + interfaceCode;
			IBankTransaction bankTransaction = bankTransactionFactory.getBankTransaction(beanName);
			//银行接口调用
			bankResponseBean = bankTransaction.execute(extendPayBean);
			
			extendPayBean.setUnionPayBean(bankResponseBean);
			extendPayBean = bankRequestService.complete(extendPayBean);							//完成BankRequest
			
			BankResponseDict bankResponseDict = bankResponseDictDao.findByCode(interfaceCode, bankResponseBean.getResponseCode());	//银行返回码处理
			extendPayBean.setResponseCode(bankResponseDict.getMappingCode());
			extendPayBean = paymentService.complete(extendPayBean);								//完成Payment	
			
		} catch (Throwable e) {
			log.info("------- BankReversalTask send bank handle fail ",e);
			return bankReversalTask;
		}finally{
			bankInterfaceTerminalService.terminalRecover(bankTerminal);	//银行终端回收	
			
		}
		bankReversalTask.setCompleteTime(new Date());
		bankReversalTask.setStatus(BankReversalTaskStatus.SUCCESS);
		bankReversalTask=bankReversalTaskService.update(bankReversalTask);
		return bankReversalTask;
	}
	
	
	/**
	 * 检查银行通道及银行商户号是否可用 
	 */
	private BankChannelRouteReturnBean checkBankChannelAndBankCustomer(
			PosRequest posRequest, String issuer,String customerMccCode,CardType cardType, String bankChannelCode,
			String customerNo, String bankCustomerNo,String bankInterfaceCode) {
		BankChannelRouteReturnBean returnParam=new BankChannelRouteReturnBean();
		
		//判断此银行通道是否可用
		BankChannel channel=bankChannelService.findByBankChannelCode(bankChannelCode);
		BankChannel bankChannel=bankChannelService.isBankChannelUseable(issuer, bankChannelCode, cardType, posRequest.getTransType(),channel.getIsSupportIc());
		if(bankChannel==null){
			log.info("+++++++++++ bankChannelCode="+bankChannelCode+" bankChannel is null!");
			return null;
		}
		
		//是否有可用终端
		if(!isAvailableBankChannelDetails(bankInterfaceCode,bankCustomerNo)){
			return null;
		}
		
		returnParam.setBankChannel(bankChannel);
		returnParam.setBankCustomerNo(bankCustomerNo);
		
		return returnParam;
	}
	
	private boolean isAvailableBankChannelDetails(String bankInterfaceCode,String bankCustomerNo){
		List<BankInterfaceTerminal> details=bankInterfaceTerminalDao.findByBankInterfaceAndBankCustomerNoAndStatusAndBankPosRunStatus(bankInterfaceCode, bankCustomerNo, Status.TRUE, BankPosRunStatus.SIGNIN);
		if(details!=null&&details.size()>0){
			for(int j=0;j<details.size();j++){
				 if(!BankPosUseStatus.SIGNING.equals(details.get(j).getBankPosUseStatus())){
					 return true;
				 }
			}
		}
		return false;//没有可用银行终端
	}

	public void setBankReversalTaskService(
			IBankReversalTaskService bankReversalTaskService) {
		this.bankReversalTaskService = bankReversalTaskService;
	}

	public IBankChannelRouteService getBankChannelRouteService() {
		return bankChannelRouteService;
	}

	public void setBankChannelRouteService(
			IBankChannelRouteService bankChannelRouteService) {
		this.bankChannelRouteService = bankChannelRouteService;
	}

	public IBankChannelService getBankChannelService() {
		return bankChannelService;
	}

	public void setBankChannelService(IBankChannelService bankChannelService) {
		this.bankChannelService = bankChannelService;
	}

	public IBankReversalTaskService getBankReversalTaskService() {
		return bankReversalTaskService;
	}


	public BankChannelCustomerConfigDao getBankChannelCustomerConfigDao() {
		return bankChannelCustomerConfigDao;
	}

	public void setBankChannelCustomerConfigDao(
			BankChannelCustomerConfigDao bankChannelCustomerConfigDao) {
		this.bankChannelCustomerConfigDao = bankChannelCustomerConfigDao;
	}

	public IBankInterfaceTerminalService getBankInterfaceTerminalService() {
		return bankInterfaceTerminalService;
	}

	public void setBankInterfaceTerminalService(
			IBankInterfaceTerminalService bankInterfaceTerminalService) {
		this.bankInterfaceTerminalService = bankInterfaceTerminalService;
	}

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public CardInfoDao getCardInfoDao() {
		return cardInfoDao;
	}

	public void setCardInfoDao(CardInfoDao cardInfoDao) {
		this.cardInfoDao = cardInfoDao;
	}

	public SequenceDao getSequenceDao() {
		return sequenceDao;
	}

	public void setSequenceDao(SequenceDao sequenceDao) {
		this.sequenceDao = sequenceDao;
	}

	public IPaymentService getPaymentService() {
		return paymentService;
	}

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public IBankRequestService getBankRequestService() {
		return bankRequestService;
	}

	public void setBankRequestService(IBankRequestService bankRequestService) {
		this.bankRequestService = bankRequestService;
	}

	public BankTransactionFactory getBankTransactionFactory() {
		return bankTransactionFactory;
	}

	public void setBankTransactionFactory(
			BankTransactionFactory bankTransactionFactory) {
		this.bankTransactionFactory = bankTransactionFactory;
	}

	public BankResponseDictDao getBankResponseDictDao() {
		return bankResponseDictDao;
	}

	public void setBankResponseDictDao(BankResponseDictDao bankResponseDictDao) {
		this.bankResponseDictDao = bankResponseDictDao;
	}

	public PaymentDao getPaymentDao() {
		return paymentDao;
	}

	public void setPaymentDao(PaymentDao paymentDao) {
		this.paymentDao = paymentDao;
	}

	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public PosDao getPosDao() {
		return posDao;
	}

	public void setPosDao(PosDao posDao) {
		this.posDao = posDao;
	}

	public BankInterfaceTerminalDao getBankInterfaceTerminalDao() {
		return bankInterfaceTerminalDao;
	}

	public void setBankInterfaceTerminalDao(
			BankInterfaceTerminalDao bankInterfaceTerminalDao) {
		this.bankInterfaceTerminalDao = bankInterfaceTerminalDao;
	}

	public IOrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}



	public void setShopDao(ShopDao shopDao) {
		this.shopDao = shopDao;
	}

	
	
}
