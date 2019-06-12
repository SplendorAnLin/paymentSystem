package com.yl.pay.pos.action.trans;

import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.BankChannelRouteBean;
import com.yl.pay.pos.bean.BankChannelRouteReturnBean;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.exception.TransExceptionConstant;
import com.yl.pay.pos.exception.TransRunTimeException;
import com.yl.pay.pos.interfaces.BankTransactionFactory;
import com.yl.pay.pos.service.*;
import com.yl.pay.pos.service.impl.BaseService;
import com.yl.pay.pos.util.EsscUtils;

import java.util.List;

/**
 * Title: 交易处理  - 基类
 * Description:  开放给交易处理类
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class BaseTransaction extends BaseService{

	protected ICardMatchService cardMatchService;
	protected ICardInfoService cardInfoService;
	protected IOrderService orderService;
	protected IPaymentService paymentService;	
	protected IExtendPayBeanService extendPayBeanService;
	protected IPermissionCheckService permissionCheckService;
	protected IPosRequestService posRequestService;
	protected IBankRequestService bankRequestService;	
	protected IBankChannelRouteService bankChannelRouteService;
	protected IBankInterfaceTerminalService bankInterfaceTerminalService;
	protected ICustomerFeeService customerFeeService;
	protected BankTransactionFactory bankTransactionFactory;
	protected InternalReversalService internalReversalService;
	protected ITransAmountLimitService transAmountLimitService;
	protected IBankReversalTaskService bankReversalTaskService;
	protected IIcParamsService iIcParamsService;
	protected ISysRouteCustomerConfigService sysRouteCustomerConfigService;
	protected ISysRouteCustomerConfigDetailService sysRouteCustomerConfigDetailService;
	protected ITransCheckProfileService transCheckProfileService;
	protected EsscUtils esscUtils;
	//银行路由，获取可用银行通道集合
	public List<BankChannelRouteReturnBean> getBankChannelRoutes(ExtendPayBean extendPayBean){		
		String cardVerifyCode=extendPayBean.getBankIdNumber()==null?null:extendPayBean.getBankIdNumber().getVerifyCode();
		String cardStyle=extendPayBean.getBankIdNumber()==null?null:extendPayBean.getBankIdNumber().getCardStyle();
	
		BankChannelRouteBean bankChannelRouteBean = new BankChannelRouteBean(extendPayBean.getPosRequest(), extendPayBean.getOrder(), 
				extendPayBean.getCustomer().getCustomerNo(), extendPayBean.getCardInfo().getIssuer().getCode(), 
				extendPayBean.getCardInfo().getCardType(), cardVerifyCode, extendPayBean.getCustomer().getMcc(),
				extendPayBean.getCustomer().getOrganization().getCode(),cardStyle,extendPayBean.getIsIC());
		bankChannelRouteBean.setRouteType(extendPayBean.getPos().getRouteType());
		
		List<BankChannelRouteReturnBean> channelRoutes = bankChannelRouteService.fetchAvailableBankChannel(bankChannelRouteBean);
		if(channelRoutes==null||channelRoutes.isEmpty()){
			throw new TransRunTimeException(TransExceptionConstant.NO_USEABLE_BANKCHANNEL);
		}
		if(channelRoutes.size()>Constant.BANK_MAX_TRX_TIME){
			channelRoutes = channelRoutes.subList(0, Constant.BANK_MAX_TRX_TIME);
		}		
		return channelRoutes;		
	} 	
	public ICardMatchService getCardMatchService() {
		return cardMatchService;
	}
	public void setCardMatchService(ICardMatchService cardMatchService) {
		this.cardMatchService = cardMatchService;
	}
	public ICardInfoService getCardInfoService() {
		return cardInfoService;
	}
	public void setCardInfoService(ICardInfoService cardInfoService) {
		this.cardInfoService = cardInfoService;
	}
	public IOrderService getOrderService() {
		return orderService;
	}
	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}
	public IPaymentService getPaymentService() {
		return paymentService;
	}
	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}
	public IExtendPayBeanService getExtendPayBeanService() {
		return extendPayBeanService;
	}
	public void setExtendPayBeanService(IExtendPayBeanService extendPayBeanService) {
		this.extendPayBeanService = extendPayBeanService;
	}
	public IPermissionCheckService getPermissionCheckService() {
		return permissionCheckService;
	}
	public void setPermissionCheckService(
			IPermissionCheckService permissionCheckService) {
		this.permissionCheckService = permissionCheckService;
	}
	public IPosRequestService getPosRequestService() {
		return posRequestService;
	}
	public void setPosRequestService(IPosRequestService posRequestService) {
		this.posRequestService = posRequestService;
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
	public IBankChannelRouteService getBankChannelRouteService() {
		return bankChannelRouteService;
	}
	public void setBankChannelRouteService(
			IBankChannelRouteService bankChannelRouteService) {
		this.bankChannelRouteService = bankChannelRouteService;
	}
	public IBankInterfaceTerminalService getBankInterfaceTerminalService() {
		return bankInterfaceTerminalService;
	}
	public void setBankInterfaceTerminalService(
			IBankInterfaceTerminalService bankInterfaceTerminalService) {
		this.bankInterfaceTerminalService = bankInterfaceTerminalService;
	}
	public ICustomerFeeService getCustomerFeeService() {
		return customerFeeService;
	}

	public void setCustomerFeeService(ICustomerFeeService customerFeeService) {
		this.customerFeeService = customerFeeService;
	}

	public void setInternalReversalService(
			InternalReversalService internalReversalService) {
		this.internalReversalService = internalReversalService;
	}
	
	public ITransAmountLimitService getTransAmountLimitService() {
		return transAmountLimitService;
	}
	public void setTransAmountLimitService(
			ITransAmountLimitService transAmountLimitService) {
		this.transAmountLimitService = transAmountLimitService;
	}
	public IBankReversalTaskService getBankReversalTaskService() {
		return bankReversalTaskService;
	}
	public void setBankReversalTaskService(
			IBankReversalTaskService bankReversalTaskService) {
		this.bankReversalTaskService = bankReversalTaskService;
	}
	public InternalReversalService getInternalReversalService() {
		return internalReversalService;
	}


	public IIcParamsService getiIcParamsService() {
		return iIcParamsService;
	}
	public void setiIcParamsService(IIcParamsService iIcParamsService) {
		this.iIcParamsService = iIcParamsService;
	}
	public ISysRouteCustomerConfigService getSysRouteCustomerConfigService() {
		return sysRouteCustomerConfigService;
	}
	public void setSysRouteCustomerConfigService(
			ISysRouteCustomerConfigService sysRouteCustomerConfigService) {
		this.sysRouteCustomerConfigService = sysRouteCustomerConfigService;
	}
	public ISysRouteCustomerConfigDetailService getSysRouteCustomerConfigDetailService() {
		return sysRouteCustomerConfigDetailService;
	}
	public void setSysRouteCustomerConfigDetailService(
			ISysRouteCustomerConfigDetailService sysRouteCustomerConfigDetailService) {
		this.sysRouteCustomerConfigDetailService = sysRouteCustomerConfigDetailService;
	}
	public void setTransCheckProfileService(
			ITransCheckProfileService transCheckProfileService) {
		this.transCheckProfileService = transCheckProfileService;
	}
	public void setEsscUtils(EsscUtils esscUtils) {
		this.esscUtils = esscUtils;
	}

}



