package com.yl.pay.pos.service.impl;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.BankInterface;
import com.yl.pay.pos.entity.Customer;
import com.yl.pay.pos.entity.Order;
import com.yl.pay.pos.enums.CustomerType;
import com.yl.pay.pos.enums.OrderStatus;
import com.yl.pay.pos.enums.TransType;
import com.yl.pay.pos.enums.YesNo;
import com.yl.pay.pos.exception.TransException;
import com.yl.pay.pos.service.IOemOrderService;
import com.yl.pay.pos.service.IOrderService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.Map;

/**
 * Title: 订单  服务
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public class OrderServiceImpl extends BaseService implements IOrderService{

	private static final Log log = LogFactory.getLog(OrderServiceImpl.class);
	
	private IOemOrderService oemOrderService;

	public ExtendPayBean create(ExtendPayBean extendPayBean) throws Exception {
		
		UnionPayBean unionPayBean=extendPayBean.getUnionPayBean();
		Order order = new Order();
		
		order.setTransType(extendPayBean.getTransType());
		order.setCustomerNo(extendPayBean.getCustomer().getCustomerNo());
		order.setPosCati(unionPayBean.getCardAcceptorTerminalId());
		order.setPosBatch(unionPayBean.getBatchNo());
		order.setPosRequestId(unionPayBean.getSystemsTraceAuditNumber());
		order.setPan(unionPayBean.getPan());
		order.setAmount(extendPayBean.getTransAmount());						//单位元，已转换
		order.setCurrencyType(extendPayBean.getPosRequest().getCurrencyType());
		order.setDescription("");
		order.setCreateTime(new Date());
		order.setStatus(OrderStatus.INIT);
		order.setCreditStatus(YesNo.N);
		order.setShop(extendPayBean.getShop());
		order.setCustomer(extendPayBean.getCustomer());
		order.setIssuer(extendPayBean.getIssuer());
		order.setCardInfo(extendPayBean.getCardInfo());
		//order.setRouteType(extendPayBean.getPos().getRouteType());
		
		if(extendPayBean.getCardInfo()!=null){
			order.setCardType(extendPayBean.getCardInfo().getCardType());
		}
		if(YesNo.Y.equals(extendPayBean.getIsLfb())){
			order.setBusinessType(Constant.BUSINESS_TYPE_1);
		}
		order.setIsIC(extendPayBean.getIsIC());
		
		//判断是否OEM商户
		Customer customer = extendPayBean.getCustomer();
		if(CustomerType.OEM.equals(customer.getCustomerType())){
			Map<String,String> respMap = oemOrderService.create(order);
			if ("SUCCESS".equals(respMap.get("result"))){
				Map<String,String> bussinessMap = JsonUtils.toObject(respMap.get("bussinessBody"), Map.class);
				order.setCustomerOrderCode(bussinessMap.get("orderNo"));
				order = orderDao.create(order);		
			} else {
				throw new TransException(respMap.get("respCode"));
			}
		} else {
			order = orderDao.create(order);		
		}
		extendPayBean.setOrder(order);
		
		return extendPayBean;
	}
	

	public ExtendPayBean complete(ExtendPayBean extendPayBean) {
		
		UnionPayBean unionPayBean = extendPayBean.getUnionPayBean();
		
		Order order = extendPayBean.getOrder();
		if(TransType.PURCHASE == extendPayBean.getTransType() 
				|| TransType.PRE_AUTH_COMP == extendPayBean.getTransType()
				||TransType.SPECIFY_QUANCUN==extendPayBean.getTransType()
				||TransType.NOT_SPECIFY_QUANCUN==extendPayBean.getTransType()
				||TransType.CASH_RECHARGE_QUNCUN==extendPayBean.getTransType()
				||TransType.OFFLINE_PURCHASE==extendPayBean.getTransType()){
			order.setStatus(OrderStatus.SUCCESS);
		}else if(TransType.PRE_AUTH == extendPayBean.getTransType()){
			order.setStatus(OrderStatus.AUTHORIZED);
		}
		order.setCompleteTime(new Date());		
		if(StringUtils.isNotBlank(unionPayBean.getAuthorizationCode())){
			order.setAuthorizationCode(unionPayBean.getAuthorizationCode());
		}
		BankInterface bankInterface = extendPayBean.getBankChannelRouteBean().getBankChannel().getBankInterface();//收单接口
		order.setBankInterface(bankInterface);
		order.setFinalPaymentId(extendPayBean.getPayment().getId());
		//银行成本，手续费，银行商户号
		order.setBankChannelCode(extendPayBean.getPayment().getBankChannel().getCode());
		order.setBankCost(extendPayBean.getPayment().getBankCost());
		order.setCustomerFee(extendPayBean.getPayment().getCustomerFee());
		order.setBankCustomerNo(extendPayBean.getPayment().getBankCustomerNo());

		return extendPayBean;
	}

	@Override
	public Order update(Order order) {
		return orderDao.update(order);
	}
	

	@Override
	public Order findById(Long id) {
		return orderDao.findById(id);
	}


	@Override
	public Map<String, Object> posOrderSum(String type,String no,Map<String,Object> params) {
		return orderDao.posOrderSum(type,no,params);
	}


	@Override
	public Order findByCode(String externalId) {
		return orderDao.findByCode(externalId);
	}

	public IOemOrderService getOemOrderService() {
		return oemOrderService;
	}


	public void setOemOrderService(IOemOrderService oemOrderService) {
		this.oemOrderService = oemOrderService;
	}

	
	@Override
	public String orderSum(String orderTimeStart, String orderTimeEnd, String owner) {
		return orderDao.orderSum(orderTimeStart, orderTimeEnd, owner);
	}
}
