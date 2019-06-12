package com.yl.pay.pos.dao.impl;

import com.pay.common.util.DateUtil;
import com.pay.common.util.SequenceUtils;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.dao.OrderDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.Order;
import com.yl.pay.pos.entity.OrderPayment;
import com.yl.pay.pos.entity.OrderSendFailRecord;
import com.yl.pay.pos.enums.OrderStatus;
import com.yl.pay.pos.enums.RouteType;
import com.yl.pay.pos.enums.YesNo;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: OrderDaoImpl
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
@SuppressWarnings("unchecked")
public class OrderDaoImpl implements OrderDao{
	
	private EntityDao entityDao;
	
	@Override
	public Order findByCode(String externalId) {
		String hql = "from Order p where p.externalId = ? ";
		return entityDao.findUnique(Order.class, hql, externalId);
	}
	
	public Order create(Order order) {
		entityDao.save(order);
		String externalId = SequenceUtils.createSequence(order.getId(), new int[] {2,7,1,6,3,3,9,8,5,6,0,5}, new int[] {2,12}, new int[] {5,9}, new int[] {3,6}, new int[] {7,9});
		order.setExternalId(externalId);
		if(order.getCustomerOrderCode()==null){
			order.setCustomerOrderCode(externalId);
		}
		return order;
	}

	public Order findById(Long id) {
		return entityDao.findById(Order.class, id);
	}
	
	public Order findSourceOrder(String posCati, String posBatch , String posRequestId){		
		String hql = "from Order p where p.posCati = ? and p.posBatch = ? and p.posRequestId = ? ";
		Order order = entityDao.findUnique(Order.class, hql, posCati, posBatch, posRequestId);
		return order;
	}	
	
	public Order update(Order order) {
		return entityDao.merge(order);
	}

	@Override
	public List<Order> findByCustomerNoAndPosCatiAndPosBatchAndStatus(
			String customerNo, String posCati, String posBatch,
			OrderStatus status) {
		String hql="from Order o where o.customerNo=? and o.posCati=? and o.posBatch=? and o.status=? ";
		return entityDao.find(hql, customerNo,posCati,posBatch,status);
	}

	@Override
	public List<Order> findByCompleteTimeAndStatus(Date start, Date end) {
		String hql="from Order o where o.status=? and o.creditStatus=? and o.completeTime>? and o.completeTime<=? ";
		return entityDao.find(hql, OrderStatus.SUCCESS,YesNo.N,start,end);
	}
	
	@Override
	public List<Order> findLfbByCompleteTimeAndStatus(Date start, Date end) {
		String hql="from Order o where o.status=? and o.creditStatus=? and o.completeTime>? and o.completeTime<=? and o.businessType=?";
		return entityDao.find(hql, OrderStatus.SUCCESS,YesNo.N,start,end,Constant.BUSINESS_TYPE_1);
	}
	@Override
	public List<Order> findByCompleteTimeAndStatusAndBankInterface(Date start,
			Date end, String bankInterfaceCode) {
		String hql="from Order o where (o.status=? or o.status=? or o.status=? or o.status=? or o.status=?) and o.completeTime>=? and o.completeTime<? and o.bankInterface.code=?";
		return entityDao.find(hql, OrderStatus.SUCCESS,OrderStatus.SETTLED,OrderStatus.AUTHORIZED,OrderStatus.REPEAL,OrderStatus.REVERSALED,start,end,bankInterfaceCode);
	}
	@Override
	public List<Order> findSourceOrder(String posCati, String posBatch,
			String posRequestId, String pan, double amount) {
		String hql="from Order p where p.posCati = ? and p.posBatch = ? and p.posRequestId = ? and p.pan=? and p.amount=? and status=? ";
		return entityDao.find(hql, posCati,posBatch,posRequestId,pan,amount,OrderStatus.SUCCESS);
	}
	@Override
	public Order findByExtrId(String exterId, OrderStatus status) {
		String hql = "from Order p where p.externalId = ? and p.status = ? ";
		Order order = entityDao.findUnique(Order.class, hql, exterId,status);
		return order;
	}
	@Override
	public Order findSourceOrder(final String pan, final String authorizationCode, final String transDate, final OrderStatus status) {
		return (Order)entityDao.doInSession(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = "select o.* from POS_ORDER o left join POS_PAYMENT p on o.id=p.order_id where o.pan=? and o.authorization_Code=? and o.status=? and to_char(p.complete_Time, 'mmdd')=?";
				Query query = session.createSQLQuery(sql).addEntity("o", Order.class);
				query.setString(0, pan);
				query.setString(1, authorizationCode);
				query.setString(2, status.name());
				query.setString(3, transDate);
				return query.uniqueResult();
			}
		});
	}
	@Override
	public OrderPayment create(OrderPayment orderPayment) {
		entityDao.save(orderPayment);
		return orderPayment;
	}

	@Override
	public void delete(OrderPayment orderPayment) {
		entityDao.remove(orderPayment);
	}

	@Override
	public List<OrderPayment> findBy() {
		String hql="from OrderPayment t order by t.id asc ";
		return entityDao.find(hql);
	}

	@Override
	public Map<String, String> findByMonitor() {
		Map<String,String> result=new HashMap<String, String>();
		Date start=DateUtil.getDate(new Date(), -15);
		start=DateUtil.parseToTodayDesignatedDate(start, "00:00:00");
		Date end=DateUtil.parseToTodayDesignatedDate(new Date(), "00:00:00");
		String hql="select count(o.id) from Order o where o.status in('SUCCESS','SETTLED') and  o.completeTime>? and o.completeTime<=? ";
	    List<Object> list=entityDao.find(hql,start,end);
	    result.put("allCount", String.valueOf(list.get(0)));
	    hql="select count(o.id) from Order o where o.status=? and o.creditStatus=? and o.completeTime>? and o.completeTime<=? ";
	    list=entityDao.find(hql, OrderStatus.SUCCESS,YesNo.N,start,end);
	    result.put("noCreditCount", String.valueOf(list.get(0)));
	    return result;
	}
	
	@Override
	public OrderSendFailRecord create(OrderSendFailRecord orderSendFailRecord) {
		orderSendFailRecord.setCreateTime(new Date());
		entityDao.save(orderSendFailRecord);
		return orderSendFailRecord;
	}

	@Override
	public List<OrderSendFailRecord> findAll() {
		String hql="from OrderSendFailRecord t order by t.id asc ";
		return entityDao.find(hql);
	}
	@Override
	public void delete(OrderSendFailRecord orderSendFailRecord) {
		entityDao.remove(orderSendFailRecord);
	}
	@Override
	public Order findByExtrId(String externalId) {
		String hql = "from Order p where p.externalId = ? ";
		Order order = entityDao.findUnique(Order.class, hql, externalId);
		return order;
	}
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	
	@Override
	public List<Order> findByUnSycn() {
		String hql="from Order o where o.isSyn=? ";
		return entityDao.find(hql, YesNo.N);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> posOrderSum(String type,String no,Map<String,Object> params) {
		String hql = null;
		List list = null;
		
		switch (type) {
		case "boss":
			hql = "select COUNT(id),SUM(amount),SUM(customerFee),SUM(bankCost) from Order";
			break;
		case "agent":
			hql = "select COUNT(id),SUM(amount),SUM(customerFee) from Order";
			break;
		case "customer":
			hql = "select COUNT(id),SUM(amount),SUM(customerFee) from Order";
//			list = entityDao.find(hql,no);
			break;
		}
//		if(type.equals("boss") || type.equals("agent")){
			
			hql += " where 1 = 1";
			
			if(no != null && !"".equals(no)){
				
				if(type.equals("boss") || type.equals("agent")){
					hql += " and customerNo IN (" + no.replaceAll("\"","\'") + ")";
				}else {
					hql += " and customerNo ='"+ no +"'";
				}
				
			}
			
			if(params.get("external_id") != null && !"".equals(params.get("external_id"))){
				hql += " and externalId = '" + params.get("external_id") + "'";
			}
			
			if(params.get("customer_order_code") != null && !"".equals(params.get("customer_order_code"))){
				hql += " and customerOrderCode = '" + params.get("customer_order_code") + "'";
			}
			
			if(params.get("customer_no") != null && !"".equals(params.get("customer_no"))){
				hql += " and customerNo = '" + params.get("customer_no") + "'";
			}
			
			if(params.get("short_name") != null && !"".equals(params.get("short_name"))){
				hql += " and shortName = '" + params.get("short_name") + "'";
			}
			
			if(params.get("status") != null && !"".equals(params.get("status"))){
				hql += " and status = '" + params.get("status") + "'";
			}
			
			if(params.get("credit_status") != null && !"".equals(params.get("credit_status"))){
				hql += " and creditStatus = '" + params.get("credit_status") + "'";
			}
			
			if(params.get("amount_start") != null && !"".equals(params.get("amount_start"))){
				hql += " and amount >= '" + Double.parseDouble(params.get("amount_start").toString()) + "'";
			}
			
			if(params.get("amount_end") != null && !"".equals(params.get("amount_end"))){
				hql += " and amount <= '" + Double.parseDouble(params.get("amount_end").toString()) + "'";
			}
			
			if(params.get("customer_fee_start") != null && !"".equals(params.get("customer_fee_start"))){
				hql += " and customerFee >= '" + Double.parseDouble(params.get("customer_fee_start").toString()) + "'";
			}
			
			if(params.get("customer_fee_end") != null && !"".equals(params.get("customer_fee_end"))){
				hql += " and customerFee <= '" + Double.parseDouble(params.get("customer_fee_end").toString()) + "'";
			}
			
			if (params.get("create_time_start") != null && !"".equals(params.get("create_time_start"))) {
				hql += (" and createTime >= '" + params.get("create_time_start").toString() + "'");
			}
			
			if (params.get("create_time_end") != null && !"".equals(params.get("create_time_end"))) {
				hql += (" and createTime <= '" + params.get("create_time_end").toString() + "'");
			}
			
			if (params.get("complete_time_start") != null && !"".equals(params.get("complete_time_start"))) {
				hql += (" and completeTime >= '" + params.get("complete_time_start").toString() + "'");
			}
			
			if (params.get("complete_time_end") != null && !"".equals(params.get("complete_time_end"))) {
				hql += (" and completeTime <= '" + params.get("complete_time_end").toString() + "'");
			}
			
			if (params.get("route_type") != null && !"".equals(params.get("route_type"))) {
				hql += (" and routeType = '" + RouteType.valueOf(params.get("route_type").toString()) + "'");
			}
			
			list = entityDao.find(hql);
//		}
		
		if(list != null && list.size() > 0){
			Map<String, Object> result = new HashMap<>();
			Object[] obj = (Object[]) list.get(0);
			if(obj[0] != null){
				result.put("al", obj[0]);
			}else {
				result.put("al", 0);
			}
			
			if(obj[1] != null){
				result.put("am", obj[1]);
			}else {
				result.put("am", 0);
			}
			
			if(obj[2] != null){
				result.put("cf", obj[2]);
			}else {
				result.put("cf", 0);
			}
			
			if(type.equals("boss")){
				
				if(obj[3] != null){
					result.put("bc", obj[3]);
				}else {
					result.put("bc", 0);
				}
				
			}
			return result;
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String orderSum(String orderTimeStart, String orderTimeEnd, String owner) {
		StringBuffer hql = new StringBuffer("SELECT COUNT(id) as COUNTS,"+
			"SUM(amount) as AMOUNT from Order where 1=1");
		if (owner != null && !owner.equals("")) {
			hql.append(" and customerNo in (" + owner + ")");
		}
		if (orderTimeEnd != null && !orderTimeEnd.equals("")) {
			hql.append(" and createTime >= '" + orderTimeEnd + " 00:00:00" + "'");
		}
		if (orderTimeStart != null && !orderTimeStart.equals("")) {
			hql.append(" and createTime <= '" + orderTimeStart + " 23:59:59" + "'");
		}
		List list = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		if(!owner.equals("0")){
			list = entityDao.find(hql.toString());
		}
		int counts = 0;
		double amount = 0;
		for (int j = 0; j < list.size(); j++) {
			Object[] obj = (Object[]) list.get(j);
			if (!obj[0].toString().equals("0")) {
				counts += Integer.parseInt(obj[0].toString());
				amount += Double.parseDouble(obj[1].toString());
			}
		}
		sb.append("{\"counts\": " + counts + ",");
		sb.append("\"amount\": " + amount + "}");
		return sb.toString();
	}
}