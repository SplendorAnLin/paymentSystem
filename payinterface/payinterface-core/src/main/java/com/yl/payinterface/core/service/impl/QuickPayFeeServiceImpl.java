package com.yl.payinterface.core.service.impl;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.bean.CustomerFee;
import com.yl.boss.api.bean.TransCardBean;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.payinterface.core.C;
import com.yl.payinterface.core.enums.FeeType;
import com.yl.payinterface.core.exception.BusinessRuntimeException;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.service.QuickPayFeeService;
import com.yl.payinterface.core.utils.FeeUtils;
import com.yl.payinterface.core.utils.HolidayUtils;

@Service("quickPayFeeService")
public class QuickPayFeeServiceImpl implements QuickPayFeeService {
	
	@Resource
	private CustomerInterface customerInterface;

	@Override
	public String getSettleAmountInCents(String ownerId, double amount) {
		String settleAmount = getSettleAmount(ownerId, amount);
		settleAmount = String.valueOf((int)AmountUtils.multiply(Double.valueOf(settleAmount), 100));
		return settleAmount;
	}
	
	@Override
	public String getSettleAmount(String ownerId, double amount) {
		// 获取商户费率 结算手续费
		CustomerFee remitFee;
		if (HolidayUtils.isHoliday()) {
			remitFee = customerInterface.getCustomerFee(ownerId, "HOLIDAY_REMIT");
			if (remitFee == null) {
				throw new BusinessRuntimeException("3005");
			}
		} else {
			remitFee = customerInterface.getCustomerFee(ownerId, "REMIT");
			if (remitFee == null) {
				throw new BusinessRuntimeException("3004");
			}
		}
		CustomerFee authFee = customerInterface.getCustomerFee(ownerId, "QUICKPAY");
		if (authFee == null) {
			throw new BusinessRuntimeException("3006");
		}
		double custFee = FeeUtils.computeFee(amount, FeeType.valueOf(authFee.getFeeType().name()), authFee.getFee(),authFee.getMinFee(),authFee.getMaxFee());
		if (AmountUtils.leq(AmountUtils.subtract(amount, custFee), remitFee.getFee())) {
			throw new BusinessRuntimeException("3007");
		}
		// 获取结算金额（结算金额=交易金额-快捷支付手续费-代付手续费）
		double settleAmount = AmountUtils.subtract(AmountUtils.subtract(amount, custFee), remitFee.getFee());
		// 结算金额保留两位小数
		String settleAmountstr = String.valueOf(AmountUtils.round(settleAmount, 2, RoundingMode.HALF_UP));
		return settleAmountstr;
	}
	
	@Override
	public Double getQuickPaySettleAmount(String ownerId, double amount) {
		
		CustomerFee authFee = customerInterface.getCustomerFee(ownerId, "QUICKPAY");
		if (authFee == null) {
			throw new BusinessRuntimeException("3006");
		}
		// 快捷支付费用
		double custFee = FeeUtils.computeFee(amount, FeeType.valueOf(authFee.getFeeType().name()), authFee.getFee(),authFee.getMinFee(),authFee.getMaxFee());
		// 结算金额
		double settleAmount = AmountUtils.subtract(amount, custFee);
		settleAmount = AmountUtils.round(settleAmount, 2, RoundingMode.HALF_UP);
		return settleAmount;
	}
	
	@Override
	public String getRemitFee(String ownerId, double amount) {
		// 获取商户费率 结算手续费
		CustomerFee remitFee;
		if (HolidayUtils.isHoliday()) {
			remitFee = customerInterface.getCustomerFee(ownerId, "HOLIDAY_REMIT");
			if (remitFee == null) {
				throw new BusinessRuntimeException("3005");
			}
		} else {
			remitFee = customerInterface.getCustomerFee(ownerId, "REMIT");
			if (remitFee == null) {
				throw new BusinessRuntimeException("3004");
			}
		}
		return Double.toString(remitFee.getFee());
	}
	
	@Override
	public void saveTransCardHis(String cardNo, InterfaceRequest interfaceRequest) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("customerNo", interfaceRequest.getOwnerID());
		map.put("accountNo", cardNo);
		map.put("amount", interfaceRequest.getAmount());
		map.put("interfaceCode", interfaceRequest.getInterfaceInfoCode());
		map.put("orderId", interfaceRequest.getBussinessOrderID());
		map.put("interfaceRequestId", interfaceRequest.getInterfaceRequestID());
		
		customerInterface.addTransCardHisForTrade(map);
	}
	
	@Override
	public void getSettleInfo(Map<Object, Object> map, String ownerId, String cardNo) {
		TransCardBean transCardBean = customerInterface.findByCustAndAccNo(ownerId, cardNo);
		if (transCardBean == null) {
			throw new BusinessRuntimeException("3003");
		}
		// 结算账户账号
		map.put("cardNo", transCardBean.getAccountNo());
		// / 持卡人姓名
		map.put("realName", transCardBean.getAccountName());
		// 持卡人身份证号
		map.put("identityCard", transCardBean.getIdNumber());
	}
	
	@Override
	public TransCardBean getSettleInfo(String ownerId, String cardNo) {
		TransCardBean transCardBean = customerInterface.findByCustAndAccNo(ownerId, cardNo);
		if (transCardBean == null) {
			throw new BusinessRuntimeException("3003");
		}
		return transCardBean;
	}
	
	@Override
	public TransCardBean getSettleInfoByInterfaceRequestId(String interfaceRequestId) {
		TransCardBean transCardBean  = customerInterface.findByInterfaceRequestId(interfaceRequestId);
		return transCardBean;
	}
	
	@Override
	public TransCardBean getTransCardInfoByInterfaceRequestId(String interfaceRequestId) {
		TransCardBean transCardBean  = customerInterface.findTransCardByInterfaceRequestId(interfaceRequestId);
		return transCardBean;
	}

	@Override
	public Double getPreFreezeAmount(String ownerId, Double amount, String userSettleAmount) {
		if (StringUtils.isNotBlank(userSettleAmount)){
			try {
				// 判断传进来的是不是Json类型
				if (userSettleAmount.contains("settleType")) {
					Map<String, String> map = JsonUtils.toObject(userSettleAmount, new TypeReference<Map<String, String>>(){});
					// 如果类型是金额
					String remitFee = getRemitFee(ownerId, amount);
					if (C.SETTLE_TYPE_AMOUNT.equals(map.get("settleType"))) {
						double freezeAmount = AmountUtils.add(Double.valueOf(remitFee), Double.valueOf(map.get("settleAmount")));
						freezeAmount = AmountUtils.round(freezeAmount, 2, RoundingMode.HALF_UP);
						return freezeAmount;
					} else {
						String quickPayFee = map.get("quickPayFee");
						// 结算金额等于【金额-快捷手续费（传入）-代付手续费（传入）】
						double settleAmount = AmountUtils.subtract(amount, FeeUtils.computeFee(amount, FeeType.RATIO, Double.valueOf(quickPayFee)));
						settleAmount = AmountUtils.subtract(settleAmount, Double.valueOf(map.get("remitFee")));
						// 冻结金额等于【结算金额+代付手续费（商户）】
						double freezeAmount = AmountUtils.add(settleAmount, Double.valueOf(remitFee));
						freezeAmount = AmountUtils.round(freezeAmount, 2, RoundingMode.HALF_UP);
						return freezeAmount;
					}
				} else {
					String remitFee = getRemitFee(ownerId, amount);
					double freezeAmount = AmountUtils.add(Double.valueOf(remitFee), Double.valueOf(userSettleAmount));
					freezeAmount = AmountUtils.round(freezeAmount, 2, RoundingMode.HALF_UP);
					return freezeAmount;
				}
			} catch (Exception e) {
				return getQuickPaySettleAmount(ownerId, amount);
			}
		} else {
			return getQuickPaySettleAmount(ownerId, amount);
		}
	}
	
	public String getQuickPayFee(String ownerId) {
		CustomerFee authFee = customerInterface.getCustomerFee(ownerId, "QUICKPAY");
		return String.valueOf(authFee.getFee());
	}
	
}
