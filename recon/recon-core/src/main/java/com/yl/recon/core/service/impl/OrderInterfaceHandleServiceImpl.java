package com.yl.recon.core.service.impl;

import com.yl.recon.core.Constant;
import com.yl.recon.core.entity.ReconException;
import com.yl.recon.core.entity.ReconOrder;
import com.yl.recon.core.entity.order.*;
import com.yl.recon.core.enums.*;
import com.yl.recon.core.mybatis.mapper.CommonReconMapper;
import com.yl.recon.core.mybatis.mapper.ReconExceptionMapper;
import com.yl.recon.core.mybatis.mapper.ReconOrderMapper;
import com.yl.recon.core.service.CommonReconService;
import com.yl.recon.core.service.OrderInterfaceHandleService;
import com.yl.recon.core.service.ReconOrderService;
import com.yl.utils.MyListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2018年01月05
 */
@Service("orderInterfaceHandleService")
public class OrderInterfaceHandleServiceImpl implements OrderInterfaceHandleService {

	private static final Logger logger = LoggerFactory.getLogger(OrderInterfaceHandleServiceImpl.class);

	@Resource
	private CommonReconMapper commonReconMapper;

	@Resource
	private ReconExceptionMapper reconExceptionMapper;

	@Resource
	private ReconOrderMapper reconOrderMapper;

	@Autowired
	private CommonReconService commonReconService;

	@Autowired
	private ReconOrderService reconOrderService;


	@Override
	public void executeByDb(Date reconDate) {
		try {
			//对账：接口与交易
			this.reconInterfaceAndOnline(reconDate);
			//对账：接口与代付
			this.reconInterfaceAndRemit(reconDate);
			//对账：接口与实名认证
			this.reconInterfaceAndRealAuth(reconDate);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 对账：接口与交易
	 *
	 * @param reconDate
	 */
	@Override
	public void reconInterfaceAndOnline(Date reconDate) {
		try {
			Map <String, Object> params = new HashMap <String, Object>(3);
			params.put(Constant.RECON_TYPE, ReconType.INTERFACE_TRADE);
			params.put(Constant.ORDER_TABLE_NAME, "trade_order");
			params.put(Constant.RECON_DATE, reconDate);
			//接口和订单对账
			interfaceAndOrderRecon(params);
		} catch (Exception e) {
			logger.info("对账出错:", e);
		}
	}

	/**
	 * 对账：接口与代付
	 *
	 * @param reconDate
	 */
	@Override
	public void reconInterfaceAndRemit(Date reconDate) {
		try {
			Map <String, Object> params = new HashMap <String, Object>(3);
			params.put(Constant.RECON_TYPE, ReconType.INTERFACE_REMIT);
			params.put(Constant.ORDER_TABLE_NAME, "remit_order");
			params.put(Constant.RECON_DATE, reconDate);
			//接口和订单对账
			interfaceAndOrderRecon(params);
		} catch (Exception e) {
			logger.info("对账出错:", e);
		}

	}

	/**
	 * 对账：接口与实名认证
	 *
	 * @param reconDate
	 */
	@Override
	public void reconInterfaceAndRealAuth(Date reconDate) {
		try {
			Map <String, Object> params = new HashMap <String, Object>(3);
			params.put(Constant.RECON_TYPE, ReconType.INTERFACE_REAL_AUTH);
			params.put(Constant.ORDER_TABLE_NAME, "real_auth_order");
			params.put(Constant.RECON_DATE, reconDate);
			//接口和订单对账
			interfaceAndOrderRecon(params);
		} catch (Exception e) {
			logger.info("对账出错:", e);
		}

	}


	/**
	 * 对账:A接口与B订单
	 */
	private void interfaceAndOrderRecon(Map <String, Object> params) throws Exception {
		List <ReconException> reconExceptions = new ArrayList <>();
		try {
			if (null == params.get(Constant.RECON_TYPE)) {
				throw new RuntimeException("对账类型不能空");
			}
			ReconOrder reconOrder = reconOrderService.initReconOrder(params);
			//接口和交易
			if (params.get(Constant.RECON_TYPE).equals(ReconType.INTERFACE_TRADE)) {
				reconOrder.setReconType(ReconType.INTERFACE_TRADE);
				reconOrder.setMsg(ReconType.INTERFACE_TRADE.getRemark());
				reconOrder.setMatchA(ReconFileType.PAYINTERFACE.name());
				reconOrder.setMatchB(ReconFileType.ONLINE.name());
				//接口订单单边
				List <PayinterfaceOrder> payinterfaceOrders = commonReconMapper.findInterfaceAndOrderInterfaceSingleErr(params);
				//交易订单单边
				List <TradeOrder> tradeOrders = commonReconMapper.findInterfaceAndOrderTradeSingleErr(params);
				//金额错误
				List <PayinterfaceOrder> amountErrs = commonReconMapper.findInterfaceAndOrderAmountErr(params);
				TotalOrder a = commonReconService.getTotalOrdersByType(params, ReconFileType.PAYINTERFACE);
				reconOrder.setAmountA(Double.parseDouble(a.getTotalAmount()));
				reconOrder.setNumsA(Integer.parseInt(a.getTotalNum()));
				TotalOrder b = commonReconService.getTotalOrdersByType(params, ReconFileType.ONLINE);
				reconOrder.setAmountB(Double.parseDouble(b.getTotalAmount()));
				reconOrder.setNumsB(Integer.parseInt(b.getTotalNum()));
				//默认系统平账
				reconOrder.setReconStatus(ReconStatus.SYS_FLAT_ACC);
				reconOrder.setReconDate((Date) params.get(Constant.RECON_DATE));
				//接口订单单边
				interfaceAndOrderInterfaceSingle(params, reconExceptions, reconOrder, payinterfaceOrders, amountErrs);
				//交易订单单边
				if (null != tradeOrders && tradeOrders.size() > 0) {
					reconOrder.setReconStatus(ReconStatus.ERR_HANDLING);
					commonReconService.installFailReason(reconOrder, ReconExceptionType.ONLINE);
					//交易订单单边
					for (TradeOrder tradeOrder : tradeOrders) {
						if (null != tradeOrder) {
							ReconException reconException = new ReconException();
							reconException.setVersion(tradeOrder.getVersion());
							reconException.setReconOrderId(reconOrder.getCode());
							reconException.setCreateTime(new Date());
							reconException.setReconType(ReconType.INTERFACE_TRADE);
							reconException.setAmount(tradeOrder.getAmount());
							commonReconService.installFailAmount(reconOrder, tradeOrder.getAmount(), Constant.B);
							reconException.setTradeOrderCode(tradeOrder.getTradeOrderCode());
							reconException.setInterfaceOrderCode(tradeOrder.getInterfaceOrderCode());
							reconException.setReconExceptionType(ReconExceptionType.ONLINE);
							reconException.setHandleRemark(ReconExceptionType.ONLINE.getRemark());
							reconException.setHandleStatus(HandleStatus.HANDLING);
							reconException.setOper(Constant.SYSTEM_CODE);
							reconException.setReconDate((Date) params.get(Constant.RECON_DATE));
							//保存明细
							reconExceptions.add(reconException);
						}
					}
				}
				if (null != reconExceptions && reconExceptions.size() > 0) {
					List <List <ReconException>> lists = MyListUtils.split(reconExceptions, Constant.BATCH_INSERT_MAX);
					for (List <ReconException> list : lists) {
						reconExceptionMapper.insertList(list);
					}
				}
				reconOrderMapper.update(reconOrder);
			}


			//接口和代付
			if (params.get(Constant.RECON_TYPE).equals(ReconType.INTERFACE_REMIT)) {
				reconOrder.setReconType(ReconType.INTERFACE_REMIT);
				reconOrder.setMsg(ReconType.INTERFACE_REMIT.getRemark());
				reconOrder.setMatchA(ReconFileType.PAYINTERFACE.name());
				reconOrder.setMatchB(ReconFileType.DPAY.name());
				//接口订单单边
				List <PayinterfaceOrder> payinterfaceOrders = commonReconMapper.findInterfaceAndOrderInterfaceSingleErr(params);
				//交易订单单边
				List <RemitOrder> remitOrders = commonReconMapper.findInterfaceAndOrderRemitSingleErr(params);
				//金额错误
				List <PayinterfaceOrder> amountErrs = commonReconMapper.findInterfaceAndOrderAmountErr(params);
				TotalOrder a = commonReconService.getTotalOrdersByType(params, ReconFileType.PAYINTERFACE);
				reconOrder.setAmountA(Double.parseDouble(a.getTotalAmount()));
				reconOrder.setNumsA(Integer.parseInt(a.getTotalNum()));
				TotalOrder b = commonReconService.getTotalOrdersByType(params, ReconFileType.DPAY);
				reconOrder.setAmountB(Double.parseDouble(b.getTotalAmount()));
				reconOrder.setNumsB(Integer.parseInt(b.getTotalNum()));
				//默认系统平账
				reconOrder.setReconStatus(ReconStatus.SYS_FLAT_ACC);
				reconOrder.setReconDate((Date) params.get(Constant.RECON_DATE));
				//接口订单单边
				interfaceAndOrderInterfaceSingle(params, reconExceptions, reconOrder, payinterfaceOrders, amountErrs);
				//交易订单单边
				if (null != remitOrders && remitOrders.size() > 0) {
					reconOrder.setReconStatus(ReconStatus.ERR_HANDLING);
					commonReconService.installFailReason(reconOrder, ReconExceptionType.DPAY);
					//交易订单单边
					for (RemitOrder remitOrder : remitOrders) {
						if (null != remitOrder) {
							ReconException reconException = new ReconException();
							reconException.setVersion(remitOrder.getVersion());
							reconException.setReconOrderId(reconOrder.getCode());
							reconException.setCreateTime(new Date());
							reconException.setReconType(ReconType.INTERFACE_REMIT);
							reconException.setAmount(remitOrder.getAmount());
							commonReconService.installFailAmount(reconOrder, remitOrder.getAmount(), Constant.B);
							reconException.setTradeOrderCode(remitOrder.getTradeOrderCode());
							reconException.setInterfaceOrderCode(remitOrder.getInterfaceOrderCode());
							reconException.setReconExceptionType(ReconExceptionType.DPAY);
							reconException.setHandleRemark(ReconExceptionType.DPAY.getRemark());
							reconException.setHandleStatus(HandleStatus.HANDLING);
							reconException.setOper(Constant.SYSTEM_CODE);
							reconException.setReconDate((Date) params.get(Constant.RECON_DATE));
							//保存明细
							reconExceptions.add(reconException);
						}
					}
				}
				if (null != reconExceptions && reconExceptions.size() > 0) {
					List <List <ReconException>> lists = MyListUtils.split(reconExceptions, Constant.BATCH_INSERT_MAX);
					for (List <ReconException> list : lists) {
						reconExceptionMapper.insertList(list);
					}
				}
				reconOrderMapper.update(reconOrder);
			}


			//接口和实名认证
			if (params.get(Constant.RECON_TYPE).equals(ReconType.INTERFACE_REAL_AUTH)) {
				reconOrder.setReconType(ReconType.INTERFACE_REAL_AUTH);
				reconOrder.setMsg(ReconType.INTERFACE_REAL_AUTH.getRemark());
				reconOrder.setMatchA(ReconFileType.PAYINTERFACE.name());
				reconOrder.setMatchB(ReconFileType.REAL_AUTH.name());
				//接口订单单边
				List <PayinterfaceOrder> payinterfaceOrders = commonReconMapper.findInterfaceAndOrderInterfaceSingleErr(params);
				//实名认证单边
				List <RealAuthOrder> realAuthOrders = commonReconMapper.findInterfaceAndOrderAuthOrderSingleErr(params);
				//金额错误
				List <PayinterfaceOrder> amountErrs = commonReconMapper.findInterfaceAndOrderAmountErr(params);
				TotalOrder a = commonReconService.getTotalOrdersByType(params, ReconFileType.PAYINTERFACE);
				reconOrder.setAmountA(Double.parseDouble(a.getTotalAmount()));
				reconOrder.setNumsA(Integer.parseInt(a.getTotalNum()));
				TotalOrder b = commonReconService.getTotalOrdersByType(params, ReconFileType.REAL_AUTH);
				reconOrder.setAmountB(Double.parseDouble(b.getTotalAmount()));
				reconOrder.setNumsB(Integer.parseInt(b.getTotalNum()));
				//默认系统平账
				reconOrder.setReconStatus(ReconStatus.SYS_FLAT_ACC);
				reconOrder.setReconDate((Date) params.get(Constant.RECON_DATE));
				//接口订单单边
				interfaceAndOrderInterfaceSingle(params, reconExceptions, reconOrder, payinterfaceOrders, amountErrs);
				//实名认证单边
				if (null != realAuthOrders && realAuthOrders.size() > 0) {
					reconOrder.setReconStatus(ReconStatus.ERR_HANDLING);
					commonReconService.installFailReason(reconOrder, ReconExceptionType.REAL_AUTH);
					for (RealAuthOrder realAuthOrder : realAuthOrders) {
						if (null != realAuthOrder) {
							ReconException reconException = new ReconException();
							reconException.setVersion(realAuthOrder.getVersion());
							reconException.setReconOrderId(reconOrder.getCode());
							reconException.setCreateTime(new Date());
							reconException.setReconType(ReconType.INTERFACE_REAL_AUTH);
							reconException.setAmount(realAuthOrder.getAmount());
							commonReconService.installFailAmount(reconOrder, realAuthOrder.getAmount(), Constant.B);
							reconException.setTradeOrderCode(realAuthOrder.getTradeOrderCode());
							reconException.setInterfaceOrderCode(realAuthOrder.getInterfaceOrderCode());
							reconException.setReconExceptionType(ReconExceptionType.REAL_AUTH);
							reconException.setHandleRemark(ReconExceptionType.REAL_AUTH.getRemark());
							reconException.setHandleStatus(HandleStatus.HANDLING);
							reconException.setOper(Constant.SYSTEM_CODE);
							reconException.setReconDate((Date) params.get(Constant.RECON_DATE));
							//保存明细
							reconExceptions.add(reconException);
						}
					}
				}
				if (null != reconExceptions && reconExceptions.size() > 0) {
					List <List <ReconException>> lists = MyListUtils.split(reconExceptions, Constant.BATCH_INSERT_MAX);
					for (List <ReconException> list : lists) {
						reconExceptionMapper.insertList(list);
					}
				}
				reconOrderMapper.update(reconOrder);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * 接口与订单：接口订单单边
	 *
	 * @param params
	 * @param reconExceptions
	 * @param reconOrder
	 * @param payinterfaceOrders
	 */
	private void interfaceAndOrderInterfaceSingle(Map <String, Object> params, List <ReconException> reconExceptions, ReconOrder reconOrder, List <PayinterfaceOrder> payinterfaceOrders, List <PayinterfaceOrder> amountErrs) {
		if (null != payinterfaceOrders && payinterfaceOrders.size() > 0) {
			reconOrder.setReconStatus(ReconStatus.ERR_HANDLING);
			commonReconService.installFailReason(reconOrder, ReconExceptionType.PAYINTERFACE);
			//接口订单单边
			for (PayinterfaceOrder payinterfaceOrder : payinterfaceOrders) {
				if (null != payinterfaceOrder) {
					ReconException reconException = new ReconException();
					reconException.setVersion(payinterfaceOrder.getVersion());
					reconException.setReconOrderId(reconOrder.getCode());
					reconException.setCreateTime(new Date());
					reconException.setReconType(reconOrder.getReconType());
					reconException.setAmount(payinterfaceOrder.getAmount());
					commonReconService.installFailAmount(reconOrder, payinterfaceOrder.getAmount(), Constant.A);
					reconException.setTradeOrderCode(payinterfaceOrder.getTradeOrderCode());
					reconException.setInterfaceOrderCode(payinterfaceOrder.getInterfaceOrderCode());
					reconException.setReconExceptionType(ReconExceptionType.PAYINTERFACE);
					reconException.setHandleRemark(ReconExceptionType.PAYINTERFACE.getRemark());
					reconException.setHandleStatus(HandleStatus.HANDLING);
					reconException.setOper(Constant.SYSTEM_CODE);
					reconException.setReconDate((Date) params.get(Constant.RECON_DATE));
					//保存明细
					reconExceptions.add(reconException);
				}
			}
		}
		//金额错误
		if (null != amountErrs && amountErrs.size() > 0) {
			reconOrder.setReconStatus(ReconStatus.ERR_HANDLING);
			commonReconService.installFailReason(reconOrder, ReconExceptionType.AMOUNT_ERR);
			reconOrder.setAmountErrNum(amountErrs.size());
			//金额错误
			for (PayinterfaceOrder payinterfaceOrder : amountErrs) {
				if (null != payinterfaceOrder) {
					ReconException reconException = new ReconException();
					reconException.setVersion(payinterfaceOrder.getVersion());
					reconException.setReconOrderId(reconOrder.getCode());
					reconException.setCreateTime(new Date());
					reconException.setReconType(reconOrder.getReconType());
					reconException.setAmount(payinterfaceOrder.getAmount());
					reconException.setTradeOrderCode(payinterfaceOrder.getTradeOrderCode());
					reconException.setInterfaceOrderCode(payinterfaceOrder.getInterfaceOrderCode());
					reconException.setReconExceptionType(ReconExceptionType.AMOUNT_ERR);
					reconException.setHandleRemark(payinterfaceOrder.getRemark());
					reconException.setHandleStatus(HandleStatus.HANDLING);
					reconException.setOper(Constant.SYSTEM_CODE);
					reconException.setReconDate((Date) params.get(Constant.RECON_DATE));
					//保存明细
					reconExceptions.add(reconException);
				}
			}
		}
	}


}