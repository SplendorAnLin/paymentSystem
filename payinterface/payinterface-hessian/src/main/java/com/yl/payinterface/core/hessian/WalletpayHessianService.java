package com.yl.payinterface.core.hessian;

import java.util.Map;

import com.yl.payinterface.core.bean.WalletSalesQueryBean;
import com.yl.payinterface.core.bean.WalletSalesTradeBean;


/**
 * 钱包支付处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月22日
 * @version V1.0.0
 */
public interface WalletpayHessianService {
	
	/**
	 * 钱包支付交易接口
	 * @param walletSalesTradeBean
	 * @return Map<String, String>
	 */
	public Map<String, String> pay(WalletSalesTradeBean walletSalesTradeBean);
	
	/**
	 * 钱包支付查询接口
	 * @param walletSalesQueryBean
	 * @return Map<String, String>
	 */
	public Map<String, String> query(WalletSalesQueryBean walletSalesQueryBean);
	
	/**
	 * 钱包支付完成接口
	 * @param completeMap
	 */
	public void complete(Map<String, String> completeMap);
	
}
