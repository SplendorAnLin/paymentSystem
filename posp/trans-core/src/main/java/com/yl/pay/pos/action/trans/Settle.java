package com.yl.pay.pos.action.trans;

import com.pay.acc.remote.beans.AccountFacadeBean;
import com.pay.acc.remote.beans.TransCreditReturnBean;
import com.pay.acc.remote.beans.TransDebitReturnBean;
import com.pay.acc.remote.service.AccountServiceFacade;
import com.pay.common.util.AmountUtil;
import com.pay.common.util.DateUtil;
import com.pay.common.util.StringUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.Order;
import com.yl.pay.pos.entity.Payment;
import com.yl.pay.pos.entity.Pos;
import com.yl.pay.pos.enums.OrderStatus;
import com.yl.pay.pos.enums.TransStatus;
import com.yl.pay.pos.enums.TransType;
import com.yl.pay.pos.enums.YesNo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;

/**
 * Title: 交易处理  - 结算
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class Settle extends BaseTransaction implements ITransaction {
	
	private static final Log log = LogFactory.getLog(Settle.class);
	private AccountServiceFacade accountServiceFacade;
	public ExtendPayBean execute(ExtendPayBean extendPayBean){
		log.info("####### Settle #######");
		UnionPayBean unionPayBean = extendPayBean.getUnionPayBean();
		
		//生成新批次号
		Pos pos = extendPayBean.getPos();		
		
		String batchNo = pos.getBatchNo();
		String batchNoNew = Integer.parseInt(batchNo) + 1 + "";	
		batchNoNew = StringUtil.fillChar(batchNoNew, "0", "L", 6);
		
		pos.setBatchNo(batchNoNew);	//当前批次号		
		posDao.update(pos);
				
		//订单入账
		boolean isCredit=true;
		if(isCredit){
		List<Order> orders=orderDao.findByCustomerNoAndPosCatiAndPosBatchAndStatus(extendPayBean.getCustomer().getCustomerNo(), pos.getPosCati(), extendPayBean.getPosRequest().getPosBatch(), OrderStatus.SUCCESS);
		YesNo settleable=YesNo.Y;
		try {
			if(orders!=null&&!orders.isEmpty()){
				log.info("+++++++ settle success order size="+orders.size());
				for(Order order:orders){
					if(YesNo.N.equals(order.getCreditStatus())){
						TransType transType = order.getTransType();
						if(TransType.PRE_AUTH.equals(transType)){
							transType=TransType.PRE_AUTH_COMP;
						}else if(TransType.PURCHASE.equals(transType)){
							transType=TransType.PURCHASE;
						}else if(TransType.CASH_RECHARGE_QUNCUN.equals(transType)){
							transType=TransType.CASH_RECHARGE_QUNCUN;
						}else if(TransType.OFFLINE_PURCHASE.equals(transType)){
							transType=TransType.OFFLINE_PURCHASE;
						}else{
							continue;
						}
						Payment sucPayment=paymentDao.findLastSourcePayment(order, TransStatus.SUCCESS, transType);
						if(sucPayment==null){
							continue;
						}
						TransCreditReturnBean acountReturnBean = null;
						if(!TransType.CASH_RECHARGE_QUNCUN.equals(transType)){
							order.setStatus(OrderStatus.SETTLED);
							order.setCreditStatus(YesNo.Y);
							order.setSettleTime(new Date());
							order=orderService.update(order);
							double creditAmount=AmountUtil.sub(order.getAmount(), sucPayment.getCustomerFee());
							AccountFacadeBean accountBean=new AccountFacadeBean(order.getCustomerNo(), Constant.USER_ROLE_CUSTOMER, creditAmount,
										Constant.ACCOUNT_BIZ_TYPE_RECEIPT, order.getExternalId(), order.getCompleteTime(),settleable.name());
							acountReturnBean=accountServiceFacade.transCredit(accountBean);		//调用账户系统入账
							if(acountReturnBean.getResult().equals("SUCCESS")){
								order.setCreditTime(acountReturnBean.getAccountOperateTime());
								order=orderService.update(order);
							}
						}else {
							double creditAmount=order.getAmount();
							AccountFacadeBean accountBean=new AccountFacadeBean(order.getCustomerNo(), Constant.USER_ROLE_CUSTOMER, creditAmount,
										Constant.ACCOUNT_BIZ_TYPE_RECEIPT, order.getExternalId(), order.getCompleteTime(),YesNo.Y.name());
							TransDebitReturnBean transDebitReturnBean=accountServiceFacade.transDebit(accountBean);		//调用账户系统入账
							if(transDebitReturnBean.getResult().equals("SUCCESS")){
								order.setCreditStatus(YesNo.Y);
								order.setCreditTime(transDebitReturnBean.getAccountOperateTime());
								order.setSettleTime(new Date());
								order.setStatus(OrderStatus.SETTLED);
							}else if(transDebitReturnBean.getResult().equals("ACC0004")){
								order.setSettleTime(new Date());
								order.setStatus(OrderStatus.SETTLED);
							}
							order=orderService.update(order);
						}
					}else{
						order.setSettleTime(new Date());
						order.setStatus(OrderStatus.SETTLED);
						order=orderService.update(order);
					}
//					extendPayBean.setOrder(order);
//					jmsService.sendOrderMsg(extendPayBean, dataSource,propertyUtil.getProperty("topic").split(",")[1]);
				}
			}
		} catch (Throwable e) {
			log.info("+++++++ settle credit ",e);
		}
		extendPayBean.setTransType(TransType.SETTLE);
		}
		//结算结果(默认对账结果平)
		String additionalDataRequest = unionPayBean.getAdditionalData48();
		StringBuffer additionalDataResponse = new StringBuffer();
		additionalDataResponse.append(additionalDataRequest.substring(0, 30).toString());
		additionalDataResponse.append("1");
		additionalDataResponse.append(additionalDataRequest.substring(31, 61).toString());
		additionalDataResponse.append("1");	
		
		//设置返回报文		
																				//11	受卡方系统跟踪号		
		unionPayBean.setTimeLocalTransaction(DateUtil.formatNow("HHmmss"));		//12	受卡方所在地时间
		unionPayBean.setDateLocalTransaction(DateUtil.formatNow("MMdd"));		//13	受卡方所在地日期
		unionPayBean.setDateSettlement(DateUtil.formatNow("MMdd"));				//15	结算日期
		unionPayBean.setAquiringInstitutionId("00000001");						//32	受理方标识码
																				//37	检参考号
																				//41	终端号不变
																				//42	商户号不变
		unionPayBean.setAdditionalData48(additionalDataResponse.toString());	//48	结算结果
																				//49	货币代码不变
																				//60.2	批次号不变		
		unionPayBean.setMsgTypeCode("00");										//60.1	交易类型码
		unionPayBean.setNetMngInfoCode("201");									//60.3	网络管理信息码	
																				//63.1	操作员不变
		return extendPayBean;
	}
	public AccountServiceFacade getAccountServiceFacade() {
		return accountServiceFacade;
	}
	public void setAccountServiceFacade(AccountServiceFacade accountServiceFacade) {
		this.accountServiceFacade = accountServiceFacade;
	}
}
