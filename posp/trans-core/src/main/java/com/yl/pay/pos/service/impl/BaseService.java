package com.yl.pay.pos.service.impl;

import com.yl.pay.pos.dao.*;
import com.yl.pay.pos.service.ICustomerProductService;

/**
 * Title: service 基类
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class BaseService {

	protected BankDao bankDao;
	protected BankChannelDao bankChannelDao;
	protected BankRequestDao bankRequestDao;
	protected BankIdNumberDao bankIdNumberDao;	
	protected CardInfoDao cardInfoDao;
	protected CustomerDao customerDao;
	protected CustomerFunctionDao customerFunctionDao;
	protected IndustryFunctionDao industryFunctionDao;
	protected OrderDao orderDao;
	protected PaymentDao paymentDao;
	protected PosDao posDao;
	protected PosRequestDao posRequestDao;
	protected ShopDao shopDao;	
	protected TransResponseDictDao transResponseDictDao;
	protected SequenceDao sequenceDao;
	protected BankInterfaceDao bankInterfaceDao;
	protected BankChannelFunctionDao bankChannelFunctionDao;
	protected BankChannelFeeDao bankChannelFeeDao;
	protected BankChannelCustomerConfigDao bankChannelCustomerConfigDao;
	protected BankInterfaceTerminalDao bankInterfaceTerminalDao;
	protected MccInfoDao mccInfoDao;
	protected BankResponseDictDao bankResponseDictDao;
	protected PosResponseDictDao posResponseDictDao;
	protected PaymentFeeDao paymentFeeDao;
	protected SingleAmountLimitDao singleAmountLimitDao;
	protected AccumulatedAmountLimitDao accumulatedAmountLimitDao;
	protected BankInterfaceSwitchConfigDao bankInterfaceSwitchConfigDao;
	protected BankCustomerConfigDao bankCustomerConfigDao;
	protected TransRouteProfileDao transRouteProfileDao;
	protected SysRespCustomerNoConfigDao sysRespCustomerNoConfigDao;
	protected CardAmountLimitDao cardAmountLimitDao;
	protected LngAndLatDao lngAndLatDao;
	protected EnterpriseCheckInfoDao enterpriseCheckInfoDao;
	
	protected ICustomerProductService customerProductService;
	protected DemotionWhiteBillDao demotionWhiteBillDao;
	protected TransCheckProfileDao transCheckProfileDao;
	protected CustomerTransFunctionDao customerTransFunctionDao;
	protected CustomerTransSortDao customerTransSortDao;
	
	public CustomerTransFunctionDao getCustomerTransFunctionDao() {
		return customerTransFunctionDao;
	}
	public void setCustomerTransFunctionDao(
			CustomerTransFunctionDao customerTransFunctionDao) {
		this.customerTransFunctionDao = customerTransFunctionDao;
	}
	public CustomerTransSortDao getCustomerTransSortDao() {
		return customerTransSortDao;
	}
	public void setCustomerTransSortDao(CustomerTransSortDao customerTransSortDao) {
		this.customerTransSortDao = customerTransSortDao;
	}
	public BankResponseDictDao getBankResponseDictDao() {
		return bankResponseDictDao;
	}
	public void setBankResponseDictDao(BankResponseDictDao bankResponseDictDao) {
		this.bankResponseDictDao = bankResponseDictDao;
	}
	public PosResponseDictDao getPosResponseDictDao() {
		return posResponseDictDao;
	}
	public void setPosResponseDictDao(PosResponseDictDao posResponseDictDao) {
		this.posResponseDictDao = posResponseDictDao;
	}
	public SequenceDao getSequenceDao() {
		return sequenceDao;
	}
	public void setSequenceDao(SequenceDao sequenceDao) {
		this.sequenceDao = sequenceDao;
	}
	public TransResponseDictDao getTransResponseDictDao() {
		return transResponseDictDao;
	}
	public BankIdNumberDao getBankIdNumberDao() {
		return bankIdNumberDao;
	}
	public void setBankIdNumberDao(BankIdNumberDao bankIdNumberDao) {
		this.bankIdNumberDao = bankIdNumberDao;
	}
	public BankDao getBankDao() {
		return bankDao;
	}
	public void setBankDao(BankDao bankDao) {
		this.bankDao = bankDao;
	}
	public BankChannelDao getBankChannelDao() {
		return bankChannelDao;
	}
	public void setBankChannelDao(BankChannelDao bankChannelDao) {
		this.bankChannelDao = bankChannelDao;
	}
	public BankRequestDao getBankRequestDao() {
		return bankRequestDao;
	}
	public void setBankRequestDao(BankRequestDao bankRequestDao) {
		this.bankRequestDao = bankRequestDao;
	}
	public CardInfoDao getCardInfoDao() {
		return cardInfoDao;
	}
	public void setCardInfoDao(CardInfoDao cardInfoDao) {
		this.cardInfoDao = cardInfoDao;
	}
	public CustomerDao getCustomerDao() {
		return customerDao;
	}
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	public OrderDao getOrderDao() {
		return orderDao;
	}
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	public PaymentDao getPaymentDao() {
		return paymentDao;
	}
	public void setPaymentDao(PaymentDao paymentDao) {
		this.paymentDao = paymentDao;
	}
	public PosDao getPosDao() {
		return posDao;
	}
	public void setPosDao(PosDao posDao) {
		this.posDao = posDao;
	}
	public PosRequestDao getPosRequestDao() {
		return posRequestDao;
	}
	public void setPosRequestDao(PosRequestDao posRequestDao) {
		this.posRequestDao = posRequestDao;
	}
	public ShopDao getShopDao() {
		return shopDao;
	}
	public void setShopDao(ShopDao shopDao) {
		this.shopDao = shopDao;
	}
	public CustomerFunctionDao getCustomerFunctionDao() {
		return customerFunctionDao;
	}
	public void setCustomerFunctionDao(CustomerFunctionDao customerFunctionDao) {
		this.customerFunctionDao = customerFunctionDao;
	}
	public IndustryFunctionDao getIndustryFunctionDao() {
		return industryFunctionDao;
	}
	public void setIndustryFunctionDao(IndustryFunctionDao industryFunctionDao) {
		this.industryFunctionDao = industryFunctionDao;
	}
	public void setTransResponseDictDao(TransResponseDictDao transResponseDictDao) {
		this.transResponseDictDao = transResponseDictDao;
	}
	public BankInterfaceDao getBankInterfaceDao() {
		return bankInterfaceDao;
	}
	public void setBankInterfaceDao(BankInterfaceDao bankInterfaceDao) {
		this.bankInterfaceDao = bankInterfaceDao;
	}
	public BankChannelFunctionDao getBankChannelFunctionDao() {
		return bankChannelFunctionDao;
	}
	public void setBankChannelFunctionDao(
			BankChannelFunctionDao bankChannelFunctionDao) {
		this.bankChannelFunctionDao = bankChannelFunctionDao;
	}
	public BankChannelFeeDao getBankChannelFeeDao() {
		return bankChannelFeeDao;
	}
	public void setBankChannelFeeDao(BankChannelFeeDao bankChannelFeeDao) {
		this.bankChannelFeeDao = bankChannelFeeDao;
	}
	public BankInterfaceTerminalDao getBankInterfaceTerminalDao() {
		return bankInterfaceTerminalDao;
	}
	public void setBankInterfaceTerminalDao(
			BankInterfaceTerminalDao bankInterfaceTerminalDao) {
		this.bankInterfaceTerminalDao = bankInterfaceTerminalDao;
	}

	public MccInfoDao getMccInfoDao() {
		return mccInfoDao;
	}
	public void setMccInfoDao(MccInfoDao mccInfoDao) {
		this.mccInfoDao = mccInfoDao;
	}
	public void setPaymentFeeDao(PaymentFeeDao paymentFeeDao) {
		this.paymentFeeDao = paymentFeeDao;
	}
	public SingleAmountLimitDao getSingleAmountLimitDao() {
		return singleAmountLimitDao;
	}
	public void setSingleAmountLimitDao(SingleAmountLimitDao singleAmountLimitDao) {
		this.singleAmountLimitDao = singleAmountLimitDao;
	}
	public AccumulatedAmountLimitDao getAccumulatedAmountLimitDao() {
		return accumulatedAmountLimitDao;
	}
	public void setAccumulatedAmountLimitDao(
			AccumulatedAmountLimitDao accumulatedAmountLimitDao) {
		this.accumulatedAmountLimitDao = accumulatedAmountLimitDao;
	}
	public PaymentFeeDao getPaymentFeeDao() {
		return paymentFeeDao;
	}
	public BankInterfaceSwitchConfigDao getBankInterfaceSwitchConfigDao() {
		return bankInterfaceSwitchConfigDao;
	}
	public void setBankInterfaceSwitchConfigDao(
			BankInterfaceSwitchConfigDao bankInterfaceSwitchConfigDao) {
		this.bankInterfaceSwitchConfigDao = bankInterfaceSwitchConfigDao;
	}
	public BankCustomerConfigDao getBankCustomerConfigDao() {
		return bankCustomerConfigDao;
	}
	public void setBankCustomerConfigDao(BankCustomerConfigDao bankCustomerConfigDao) {
		this.bankCustomerConfigDao = bankCustomerConfigDao;
	}
	public TransRouteProfileDao getTransRouteProfileDao() {
		return transRouteProfileDao;
	}
	public void setTransRouteProfileDao(TransRouteProfileDao transRouteProfileDao) {
		this.transRouteProfileDao = transRouteProfileDao;
	}
	public BankChannelCustomerConfigDao getBankChannelCustomerConfigDao() {
		return bankChannelCustomerConfigDao;
	}
	public void setBankChannelCustomerConfigDao(
			BankChannelCustomerConfigDao bankChannelCustomerConfigDao) {
		this.bankChannelCustomerConfigDao = bankChannelCustomerConfigDao;
	}
	public SysRespCustomerNoConfigDao getSysRespCustomerNoConfigDao() {
		return sysRespCustomerNoConfigDao;
	}
	public void setSysRespCustomerNoConfigDao(
			SysRespCustomerNoConfigDao sysRespCustomerNoConfigDao) {
		this.sysRespCustomerNoConfigDao = sysRespCustomerNoConfigDao;
	}
	public ICustomerProductService getCustomerProductService() {
		return customerProductService;
	}
	public void setCustomerProductService(
			ICustomerProductService customerProductService) {
		this.customerProductService = customerProductService;
	}
	public CardAmountLimitDao getCardAmountLimitDao() {
		return cardAmountLimitDao;
	}
	public void setCardAmountLimitDao(CardAmountLimitDao cardAmountLimitDao) {
		this.cardAmountLimitDao = cardAmountLimitDao;
	}
	public void setLngAndLatDao(LngAndLatDao lngAndLatDao) {
		this.lngAndLatDao = lngAndLatDao;
	}
	
	public DemotionWhiteBillDao getDemotionWhiteBillDao() {
		return demotionWhiteBillDao;
	}
	public void setDemotionWhiteBillDao(DemotionWhiteBillDao demotionWhiteBillDao) {
		this.demotionWhiteBillDao = demotionWhiteBillDao;
	}
	public TransCheckProfileDao getTransCheckProfileDao() {
		return transCheckProfileDao;
	}
	public void setTransCheckProfileDao(TransCheckProfileDao transCheckProfileDao) {
		this.transCheckProfileDao = transCheckProfileDao;
	}
	public void setEnterpriseCheckInfoDao(
			EnterpriseCheckInfoDao enterpriseCheckInfoDao) {
		this.enterpriseCheckInfoDao = enterpriseCheckInfoDao;
	}
	
}
