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
import com.yl.recon.core.service.OrderAccountHandleService;
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
@Service("orderAccountHandleService")
public class OrderAccountHandleServiceImpl implements OrderAccountHandleService {

	private static final Logger logger = LoggerFactory.getLogger(OrderAccountHandleServiceImpl.class);

	@Resource
	private CommonReconMapper commonReconMapper;

	@Resource
	private ReconExceptionMapper reconExceptionMapper;


	@Resource
	private ReconOrderMapper reconOrderMapper;

	@Resource
	private ReconOrderService reconOrderService;

	@Autowired
	private CommonReconService commonReconService;


	/**
	 * 数据库对账
	 *
	 * @param date
	 */
	@Override
	public void executeByDb(Date date) {
		try {
			//Online
			this.reconAccountAndOnline(date);
			//Remit
			this.reconAccountAndRemit(date);
			//RealAuth
			this.reconAccountAndRealAuth(date);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 对账：账户与交易
	 *
	 * @param reconDate
	 */
	@Override
	public void reconAccountAndOnline(Date reconDate) {
		try {
			Map <String, Object> params = new HashMap <String, Object>(3);
			params.put(Constant.RECON_TYPE, ReconType.ACCOUNT_TRADE);
			params.put(Constant.ACC_SYSTEM_CODE, SystemCode.ONLINE);
			params.put(Constant.ORDER_TABLE_NAME, "trade_order");
			params.put(Constant.RECON_DATE, reconDate);
			//账户和订单对账
			accountAndOrderRecon(params);
			//账户手续费和订单手续费对账
			params.put(Constant.RECON_TYPE, ReconType.ACCOUNT_TRADE_FEE);
			accountAndOrderFeeRecon(params);
		} catch (Exception e) {
			logger.info("对账出错:", e);
		}
	}

	/**
	 * 对账：账户与代付
	 *
	 * @param reconDate
	 */
	@Override
	public void reconAccountAndRemit(Date reconDate) {
		try {
			Map <String, Object> params = new HashMap <String, Object>(3);
			params.put(Constant.RECON_TYPE, ReconType.ACCOUNT_REMIT);
			params.put(Constant.ACC_SYSTEM_CODE, SystemCode.DPAY);
			params.put(Constant.ORDER_TABLE_NAME, "remit_order");
			params.put(Constant.RECON_DATE, reconDate);
			//账户与代付对账
			accountAndOrderRecon(params);
			//账户手续费和代付手续费对账
			params.put(Constant.RECON_TYPE, ReconType.ACCOUNT_REMIT_FEE);
			accountAndOrderFeeRecon(params);
		} catch (Exception e) {
			logger.info("对账出错:", e);
		}
	}

	/**
	 * 对账：账户与实名认证
	 *
	 * @param reconDate
	 */
	@Override
	public void reconAccountAndRealAuth(Date reconDate) {
		try {
			Map <String, Object> params = new HashMap <String, Object>(3);
			params.put(Constant.ACC_SYSTEM_CODE, SystemCode.REALAUTH);
			params.put(Constant.ORDER_TABLE_NAME, "real_auth_order");
			params.put(Constant.RECON_DATE, reconDate);
			//账户手续费和实名认证手续费对账
			params.put(Constant.RECON_TYPE, ReconType.ACCOUNT_REAL_AUTH_FEE);
			accountAndOrderFeeRecon(params);
		} catch (Exception e) {
			logger.info("对账出错:", e);
		}

	}


	/**
	 * 对账:A账户与B订单
	 */
	private void accountAndOrderRecon(Map <String, Object> params) throws Exception {
		List <ReconException> reconExceptions = new ArrayList <>();
		try {
			if (null == params.get(Constant.RECON_TYPE)) {
				throw new RuntimeException("对账类型不能空");
			}

			ReconOrder reconOrder = reconOrderService.initReconOrder(params);
			params.put("IS_FEE", Constant.IS_FEE_FALSE);
			//账户和交易
			if (params.get(Constant.RECON_TYPE).equals(ReconType.ACCOUNT_TRADE)) {
				reconOrder.setReconType(ReconType.ACCOUNT_TRADE);
				reconOrder.setMsg(ReconType.ACCOUNT_TRADE.getRemark());
				reconOrder.setMatchA(ReconFileType.ACCOUNT.name());
				reconOrder.setMatchB(ReconFileType.ONLINE.name());
				//账户单边
				List <AccountOrder> accountOrders = commonReconMapper.findAccAndOrderAccSingleErr(params);
				//交易订单单边
				List <TradeOrder> tradeOrders = commonReconMapper.findAccAndOrderTradeSingleErr(params);
				//金额错误
				List <TradeOrder> amountErrs = commonReconMapper.findAccAndOrderAmountErr(params);

				List <AccTotalOrder> accTotalOrders = commonReconMapper.findAccountTotal(params);
				AccTotalOrder accTotalOrder = new AccTotalOrder();
				accTotalOrder.setTotalAmount("0.0");
				accTotalOrder.setTotalNum("0");
				if (null != accTotalOrders && accTotalOrders.size() > 0) {
					accTotalOrder = accTotalOrders.get(0);
				}
				reconOrder.setAmountA(Double.parseDouble(accTotalOrder.getTotalAmount()));
				reconOrder.setNumsA(Integer.parseInt(accTotalOrder.getTotalNum()));
				TotalOrder b = commonReconService.getTotalOrdersByType(params, ReconFileType.ONLINE);
				reconOrder.setAmountB(Double.parseDouble(b.getTotalAmount()));
				reconOrder.setNumsB(Integer.parseInt(b.getTotalNum()));
				//默认系统平账
				reconOrder.setReconStatus(ReconStatus.SYS_FLAT_ACC);
				reconOrder.setReconDate((Date) params.get(Constant.RECON_DATE));
				//账户单边
				installAccSingleErr(params, reconExceptions, reconOrder, accountOrders);
				//交易单边与金额错误
				installReconExForAccAndOrder(params, reconExceptions, reconOrder, tradeOrders, amountErrs);
			}
			//账户和代付
			if (params.get(Constant.RECON_TYPE).equals(ReconType.ACCOUNT_REMIT)) {
				reconOrder.setReconType(ReconType.ACCOUNT_REMIT);
				reconOrder.setMsg(ReconType.ACCOUNT_REMIT.getRemark());
				reconOrder.setMatchA(ReconFileType.ACCOUNT.name());
				reconOrder.setMatchB(ReconFileType.DPAY.name());
				//账户单边
				List <AccountOrder> accountOrders = commonReconMapper.findAccAndOrderAccSingleErr(params);
				//代付单边
				List <RemitOrder> remitOrders = commonReconMapper.findAccAndRemitSingleErr(params);
				//金额错误
				List <RemitOrder> amountErrs = commonReconMapper.findAccAndRemitAmountErr(params);

				List <AccTotalOrder> accTotalOrders = commonReconMapper.findAccountTotal(params);
				AccTotalOrder accTotalOrder = new AccTotalOrder();
				accTotalOrder.setTotalAmount("0.0");
				accTotalOrder.setTotalNum("0");
				if (null != accTotalOrders && accTotalOrders.size() > 0) {
					accTotalOrder = accTotalOrders.get(0);
				}
				reconOrder.setAmountA(Double.parseDouble(accTotalOrder.getTotalAmount()));
				reconOrder.setNumsA(Integer.parseInt(accTotalOrder.getTotalNum()));
				TotalOrder b = commonReconService.getTotalOrdersByType(params, ReconFileType.DPAY);
				reconOrder.setAmountB(Double.parseDouble(b.getTotalAmount()));
				reconOrder.setNumsB(Integer.parseInt(b.getTotalNum()));
				//默认系统平账
				reconOrder.setReconStatus(ReconStatus.SYS_FLAT_ACC);
				reconOrder.setReconDate((Date) params.get(Constant.RECON_DATE));
				//账户单边
				installAccSingleErr(params, reconExceptions, reconOrder, accountOrders);
				//代付单边与金额错误
				installReconExForAccAndRemit(params, reconExceptions, reconOrder, remitOrders, amountErrs);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}


	/**
	 * 账户单边
	 *
	 * @param accountOrders
	 */
	private void installAccSingleErr(Map <String, Object> params, List <ReconException> reconExceptions, ReconOrder reconOrder, List <AccountOrder> accountOrders) {
		//账户订单单边
		if (null != accountOrders && accountOrders.size() > 0) {
			reconOrder.setReconStatus(ReconStatus.ERR_HANDLING);
			commonReconService.installFailReason(reconOrder, ReconExceptionType.ACCOUNT);
			//账户订单单边
			for (AccountOrder accountOrder : accountOrders) {
				if (null != accountOrder) {
					ReconException reconException = new ReconException();
					reconException.setVersion(accountOrder.getVersion());
					reconException.setReconOrderId(reconOrder.getCode());
					reconException.setCreateTime(new Date());
					reconException.setReconType(ReconType.ACCOUNT_TRADE);
					reconException.setAmount(accountOrder.getAmount());
					commonReconService.installFailAmount(reconOrder, accountOrder.getAmount(), Constant.A);
					reconException.setTradeOrderCode(accountOrder.getTradeOrderCode());
					reconException.setReconExceptionType(ReconExceptionType.ACCOUNT);
					reconException.setHandleRemark(ReconExceptionType.ACCOUNT.getRemark());
					reconException.setHandleStatus(HandleStatus.HANDLING);
					reconException.setOper(Constant.SYSTEM_CODE);
					reconException.setReconDate((Date) params.get(Constant.RECON_DATE));
					//保存明细
					reconExceptions.add(reconException);
				}
			}
		}
	}


	/**
	 * 对账:A账户手续费与B订单手续费
	 */
	private void accountAndOrderFeeRecon(Map <String, Object> params) throws Exception {
		List <ReconException> reconExceptions = new ArrayList <>();
		try {
			ReconOrder reconOrder = reconOrderService.initReconOrder(params);
			params.put("IS_FEE", Constant.IS_FEE_TRUE);
			//账户手续费和交易手续费
			if (params.get(Constant.RECON_TYPE).equals(ReconType.ACCOUNT_TRADE_FEE)) {
				reconOrder.setReconType(ReconType.ACCOUNT_TRADE_FEE);
				reconOrder.setMsg(ReconType.ACCOUNT_TRADE_FEE.getRemark());
				reconOrder.setMatchA(ReconFileType.ACCOUNT.name());
				reconOrder.setMatchB(ReconFileType.ONLINE.name());
				//账户单边
				List <AccountOrder> accountOrders = commonReconMapper.findAccAndOrderAccSingleErr(params);
				//交易订单单边
				List <TradeOrder> tradeOrders = commonReconMapper.findAccAndOrderTradeSingleErr(params);
				//金额错误
				List <TradeOrder> amountErrs = commonReconMapper.findAccAndOrderAmountErr(params);

				List <AccTotalOrder> accTotalOrders = commonReconMapper.findAccountTotal(params);
				AccTotalOrder accTotalOrder = new AccTotalOrder();
				accTotalOrder.setTotalAmount("0.0");
				accTotalOrder.setTotalNum("0");
				if (null != accTotalOrders && accTotalOrders.size() > 0) {
					accTotalOrder = accTotalOrders.get(0);
				}
				reconOrder.setAmountA(Double.parseDouble(accTotalOrder.getTotalAmount()));
				reconOrder.setNumsA(Integer.parseInt(accTotalOrder.getTotalNum()));
				TotalOrder b = commonReconService.getTotalOrdersByType(params, ReconFileType.ONLINE);
				reconOrder.setAmountB(Double.parseDouble(b.getTotalAmount()));
				reconOrder.setNumsB(Integer.parseInt(b.getTotalNum()));
				//默认系统平账
				reconOrder.setReconStatus(ReconStatus.SYS_FLAT_ACC);
				reconOrder.setReconDate((Date) params.get(Constant.RECON_DATE));
				//账户单边
				installAccSingleErr(params, reconExceptions, reconOrder, accountOrders);
				//交易单边与金额错误
				installReconExForAccAndOrder(params, reconExceptions, reconOrder, tradeOrders, amountErrs);
			}

			//账户手续费和代付手续费
			if (params.get(Constant.RECON_TYPE).equals(ReconType.ACCOUNT_REMIT_FEE)) {
				reconOrder.setReconType(ReconType.ACCOUNT_REMIT_FEE);
				reconOrder.setMsg(ReconType.ACCOUNT_REMIT_FEE.getRemark());
				reconOrder.setMatchA(ReconFileType.ACCOUNT.name());
				reconOrder.setMatchB(ReconFileType.DPAY.name());
				//账户单边
				List <AccountOrder> accountOrders = commonReconMapper.findAccAndOrderAccSingleErr(params);
				//代付单边
				List <RemitOrder> remitOrders = commonReconMapper.findAccAndRemitSingleErr(params);
				//金额错误
				List <RemitOrder> amountErrs = commonReconMapper.findAccAndRemitAmountErr(params);

				List <AccTotalOrder> accTotalOrders = commonReconMapper.findAccountTotal(params);
				AccTotalOrder accTotalOrder = new AccTotalOrder();
				accTotalOrder.setTotalAmount("0.0");
				accTotalOrder.setTotalNum("0");
				if (null != accTotalOrders && accTotalOrders.size() > 0) {
					accTotalOrder = accTotalOrders.get(0);
				}
				reconOrder.setAmountA(Double.parseDouble(accTotalOrder.getTotalAmount()));
				reconOrder.setNumsA(Integer.parseInt(accTotalOrder.getTotalNum()));
				TotalOrder b = commonReconService.getTotalOrdersByType(params, ReconFileType.ONLINE);
				reconOrder.setAmountB(Double.parseDouble(b.getTotalAmount()));
				reconOrder.setNumsB(Integer.parseInt(b.getTotalNum()));
				//默认系统平账
				reconOrder.setReconStatus(ReconStatus.SYS_FLAT_ACC);
				reconOrder.setReconDate((Date) params.get(Constant.RECON_DATE));
				//账户手续费单边
				installAccSingleErr(params, reconExceptions, reconOrder, accountOrders);
				//代付单边与金额错误
				installReconExForAccAndRemit(params, reconExceptions, reconOrder, remitOrders, amountErrs);
			}

			//账户手续费和实名认证手续费
			if (params.get(Constant.RECON_TYPE).equals(ReconType.ACCOUNT_REAL_AUTH_FEE)) {
				reconOrder.setReconType(ReconType.ACCOUNT_REAL_AUTH_FEE);
				reconOrder.setMsg(ReconType.ACCOUNT_REAL_AUTH_FEE.getRemark());
				reconOrder.setMatchA(ReconFileType.ACCOUNT.name());
				reconOrder.setMatchB(ReconFileType.REAL_AUTH.name());
				//账户单边
				List <AccountOrder> accountOrders = commonReconMapper.findAccAndOrderAccSingleErr(params);
				//代付单边
				List <RealAuthOrder> realAuthOrders = commonReconMapper.findAccAndOrderRealAuthOrderSingleErr(params);
				//金额错误
				List <RealAuthOrder> amountErrs = commonReconMapper.findAccAndOrderRealAuthOrderAmountErr(params);

				List <AccTotalOrder> accTotalOrders = commonReconMapper.findAccountTotal(params);
				AccTotalOrder accTotalOrder = new AccTotalOrder();
				accTotalOrder.setTotalAmount("0.0");
				accTotalOrder.setTotalNum("0");
				if (null != accTotalOrders && accTotalOrders.size() > 0) {
					accTotalOrder = accTotalOrders.get(0);
				}
				reconOrder.setAmountA(Double.parseDouble(accTotalOrder.getTotalAmount()));
				reconOrder.setNumsA(Integer.parseInt(accTotalOrder.getTotalNum()));
				TotalOrder b = commonReconService.getTotalOrdersByType(params, ReconFileType.REAL_AUTH);
				reconOrder.setAmountB(Double.parseDouble(b.getTotalAmount()));
				reconOrder.setNumsB(Integer.parseInt(b.getTotalNum()));
				//默认系统平账
				reconOrder.setReconStatus(ReconStatus.SYS_FLAT_ACC);
				reconOrder.setReconDate((Date) params.get(Constant.RECON_DATE));
				//账户单边
				installAccSingleErr(params, reconExceptions, reconOrder, accountOrders);
				//账户订单单边
				installReconExForAccAndRealAuth(params, reconExceptions, reconOrder, realAuthOrders, amountErrs);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * 账户与交易(交易单边与金额错误)
	 *
	 * @param params
	 * @param reconExceptions
	 * @param reconOrder
	 * @param tradeOrders
	 * @param amountErrs
	 */
	private void installReconExForAccAndOrder(Map <String, Object> params, List <ReconException> reconExceptions, ReconOrder reconOrder, List <TradeOrder> tradeOrders, List <TradeOrder> amountErrs) {
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
					reconException.setReconType(ReconType.ACCOUNT_TRADE_FEE);
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
		//金额错误
		if (null != amountErrs && amountErrs.size() > 0) {
			reconOrder.setReconStatus(ReconStatus.ERR_HANDLING);
			commonReconService.installFailReason(reconOrder, ReconExceptionType.AMOUNT_ERR);
			reconOrder.setAmountErrNum(amountErrs.size());
			//金额错误
			for (TradeOrder tradeOrder : amountErrs) {
				if (null != tradeOrder) {
					ReconException reconException = new ReconException();
					reconException.setVersion(tradeOrder.getVersion());
					reconException.setReconOrderId(reconOrder.getCode());
					reconException.setCreateTime(new Date());
					reconException.setReconType(ReconType.ACCOUNT_TRADE_FEE);
					reconException.setAmount(tradeOrder.getAmount());
					reconException.setTradeOrderCode(tradeOrder.getTradeOrderCode());
					reconException.setInterfaceOrderCode(tradeOrder.getInterfaceOrderCode());
					reconException.setReconExceptionType(ReconExceptionType.AMOUNT_ERR);
					reconException.setHandleRemark(tradeOrder.getRemark());
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


	/**
	 * 代付订单单边(代付单边与金额错误)
	 *
	 * @param params
	 * @param reconExceptions
	 * @param reconOrder
	 * @param remitOrders
	 * @param amountErrs
	 */
	private void installReconExForAccAndRemit(Map <String, Object> params, List <ReconException> reconExceptions, ReconOrder reconOrder, List <RemitOrder> remitOrders, List <RemitOrder> amountErrs) {
		//代付订单单边
		if (null != remitOrders && remitOrders.size() > 0) {
			reconOrder.setReconStatus(ReconStatus.ERR_HANDLING);
			commonReconService.installFailReason(reconOrder, ReconExceptionType.DPAY);
			//代付订单单边
			for (RemitOrder remitOrder : remitOrders) {
				if (null != remitOrder) {
					ReconException reconException = new ReconException();
					reconException.setVersion(remitOrder.getVersion());
					reconException.setReconOrderId(reconOrder.getCode());
					reconException.setCreateTime(new Date());
					reconException.setReconType(ReconType.ACCOUNT_REMIT_FEE);
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
		//金额错误
		if (null != amountErrs && amountErrs.size() > 0) {
			reconOrder.setReconStatus(ReconStatus.ERR_HANDLING);
			commonReconService.installFailReason(reconOrder, ReconExceptionType.AMOUNT_ERR);
			reconOrder.setAmountErrNum(amountErrs.size());
			//金额错误
			for (RemitOrder remitOrder : amountErrs) {
				if (null != remitOrder) {
					ReconException reconException = new ReconException();
					reconException.setVersion(remitOrder.getVersion());
					reconException.setReconOrderId(reconOrder.getCode());
					reconException.setCreateTime(new Date());
					reconException.setReconType(ReconType.ACCOUNT_REMIT_FEE);
					reconException.setAmount(remitOrder.getAmount());
					reconException.setTradeOrderCode(remitOrder.getTradeOrderCode());
					reconException.setInterfaceOrderCode(remitOrder.getInterfaceOrderCode());
					reconException.setReconExceptionType(ReconExceptionType.AMOUNT_ERR);
					reconException.setHandleRemark(remitOrder.getRemark());
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


	/**
	 * 认证支付单边(认证支付单边与金额错误)
	 *
	 * @param params
	 * @param reconExceptions
	 * @param reconOrder
	 * @param amountErrs
	 */
	private void installReconExForAccAndRealAuth(Map <String, Object> params, List <ReconException> reconExceptions, ReconOrder reconOrder, List <RealAuthOrder> realAuthOrders, List <RealAuthOrder> amountErrs) {
		//代付订单单边
		if (null != realAuthOrders && realAuthOrders.size() > 0) {
			reconOrder.setReconStatus(ReconStatus.ERR_HANDLING);
			commonReconService.installFailReason(reconOrder, ReconExceptionType.REAL_AUTH);
			//代付订单单边
			for (RealAuthOrder realAuthOrder : realAuthOrders) {
				if (null != realAuthOrder) {
					ReconException reconException = new ReconException();
					reconException.setVersion(realAuthOrder.getVersion());
					reconException.setReconOrderId(reconOrder.getCode());
					reconException.setCreateTime(new Date());
					reconException.setReconType(ReconType.ACCOUNT_REAL_AUTH_FEE);
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
		//金额错误
		if (null != amountErrs && amountErrs.size() > 0) {
			reconOrder.setReconStatus(ReconStatus.ERR_HANDLING);
			commonReconService.installFailReason(reconOrder, ReconExceptionType.AMOUNT_ERR);
			reconOrder.setAmountErrNum(amountErrs.size());
			//金额错误
			for (RealAuthOrder realAuthOrder : amountErrs) {
				if (null != realAuthOrder) {
					ReconException reconException = new ReconException();
					reconException.setVersion(realAuthOrder.getVersion());
					reconException.setReconOrderId(reconOrder.getCode());
					reconException.setCreateTime(new Date());
					reconException.setReconType(ReconType.ACCOUNT_REAL_AUTH_FEE);
					reconException.setAmount(realAuthOrder.getAmount());
					reconException.setTradeOrderCode(realAuthOrder.getTradeOrderCode());
					reconException.setInterfaceOrderCode(realAuthOrder.getInterfaceOrderCode());
					reconException.setReconExceptionType(ReconExceptionType.AMOUNT_ERR);
					reconException.setHandleRemark(realAuthOrder.getRemark());
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


}