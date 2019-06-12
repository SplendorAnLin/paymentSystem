package com.yl.pay.pos.interfaces.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.pay.pos.api.interfaces.PosOrderHessianService;
import com.yl.pay.pos.entity.Order;
import com.yl.pay.pos.entity.Payment;
import com.yl.pay.pos.entity.TransRouteConfig;
import com.yl.pay.pos.enums.RouteType;
import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.service.IOrderService;
import com.yl.pay.pos.service.IPaymentService;
import com.yl.pay.pos.service.ITransRouteConfigService;

public class PosOrderHessianServiceImpl implements PosOrderHessianService{
	private static final Log log = LogFactory.getLog(PosOrderHessianServiceImpl.class);
	IOrderService orderService;
	IPaymentService paymentService;
	@Resource
	ITransRouteConfigService iTransRouteConfigService;
	
	public Map<String, Object> findOrderByCode(String externalId){
		Order order=orderService.findByCode(externalId);
		if (order!=null) {
			Map<String, Object> info=JsonUtils.toJsonToObject(order,Map.class);
			return info;
		}
		return null;
	}
	
	@Override
	public void posRoute(String customerNo, String transType) {
		TransRouteConfig transRouteConfig = new TransRouteConfig();
		transRouteConfig.setCode("CR000001");
		transRouteConfig.setCreateTime(new Date());
		transRouteConfig.setCustomerNo(customerNo);
		transRouteConfig.setStatus(Status.TRUE);
		transRouteConfig.setTransRouteCode("RT0041");
		iTransRouteConfigService.save(transRouteConfig);
	}
	
	@Override
	public Map<String, Object> findOrderById(Long id,String currentPage) {
		Order order=orderService.findById(id);
		if (order!=null) {
			Map<String, Object> info=new HashMap<String, Object>();
			com.yl.pay.pos.api.bean.Order orderApi=null;
			try {
				orderApi = (com.yl.pay.pos.api.bean.Order)convertMap(com.yl.pay.pos.api.bean.Order.class,JsonUtils.toObject(JsonUtils.toJsonString(order),Map.class));
			} catch (Exception e) {
				log.error(e);
			}
			if (orderApi!=null) {
				if (order.getBankInterface()!=null) {
					orderApi.setBankInterfaceName(order.getBankInterface().getName());
				}
				info.put("detail",orderApi);
				List<Payment> infoList=paymentService.findByOrder(order, currentPage);
				List<com.yl.pay.pos.api.bean.Payment> list=new ArrayList<>();
				for (Payment payment : infoList) {
					com.yl.pay.pos.api.bean.Payment paymentInfo=new com.yl.pay.pos.api.bean.Payment();
					if (payment.getPaymentFee()!=null) {
						paymentInfo.setBankChannelRate(payment.getPaymentFee().getBankChannelRate());
						paymentInfo.setCustomerRate(payment.getPaymentFee().getCustomerRate());
					}
					if (payment.getBankInterface()!=null) {
						paymentInfo.setInterfaceName(payment.getBankInterface().getName());
					}
					if (payment.getSourcePayment()!=null) {
						paymentInfo.setSourcePaymentNo(payment.getSourcePayment().getOrder().getExternalId());
					}
					paymentInfo.setAmount(payment.getAmount());
					paymentInfo.setBankBatch(payment.getBankBatch());
					paymentInfo.setBankCost(payment.getBankCost());
					paymentInfo.setBankCustomerNo(payment.getBankCustomerNo());
					paymentInfo.setBankExternalId(payment.getBankExternalId());
					paymentInfo.setBankPosCati(payment.getBankPosCati());
					paymentInfo.setBankRequestId(payment.getBankRequestId());
					paymentInfo.setCompleteTime(payment.getCompleteTime());
					paymentInfo.setCreateTime(payment.getCreateTime());
					paymentInfo.setCurrencyType(payment.getCurrencyType().toString());
					paymentInfo.setCustomerFee(payment.getCustomerFee());
					paymentInfo.setCustomerNo(payment.getCustomerNo());
					paymentInfo.setId(payment.getId());
					paymentInfo.setPan(payment.getPan());
					paymentInfo.setPosBatch(payment.getPosBatch());
					paymentInfo.setPosCati(payment.getPosCati());
					paymentInfo.setPosRequestId(payment.getPosRequestId());
					paymentInfo.setStatus(payment.getStatus().toString());
					paymentInfo.setTransType(payment.getTransType().toString());
					list.add(paymentInfo);
				}
				info.put("list", list);
				info.put("pageCount", list.size()%10>list.size()/10?list.size():list.size()/10+1);
				info.put("currentPage", currentPage);
				return info;
			}
		}
		return null;
	}
	
	public static Object convertMap(Class type, Map map)
			throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		Object obj = type.newInstance();
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (map.containsKey(propertyName)) {
				try {
					Object value = map.get(propertyName);
					Object[] args = new Object[1];
					args[0] = value;
					descriptor.getWriteMethod().invoke(obj, args);
				} catch (Exception e) {
					log.error(e);
				}
				
			}
		}
		return obj;
	}

	public IOrderService getOrderService() {
		return orderService;
	}

	public IPaymentService getPaymentService() {
		return paymentService;
	}

	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@Override
	public Map<String, Object> posOrderSum(String type,String no,Map<String,Object> params) {
		return orderService.posOrderSum(type,no,params);
	}

	@Override
	public String orderSum(Date orderTimeStart, Date orderTimeEnd, String owner) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return orderService.orderSum(sdf.format(orderTimeStart), sdf.format(orderTimeEnd), owner);
	}
}