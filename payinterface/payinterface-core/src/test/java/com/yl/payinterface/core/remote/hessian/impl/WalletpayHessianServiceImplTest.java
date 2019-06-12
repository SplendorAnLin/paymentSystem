package com.yl.payinterface.core.remote.hessian.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.yl.payinterface.core.BaseTest;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.WalletpayHessianService;

/**
 * 钱包支付远程接口实现测试类
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月13日
 * @version V1.0.0
 */
public class WalletpayHessianServiceImplTest extends BaseTest {
	
	@Resource
	WalletpayHessianService walletpayHessianService;
	
	@Test
	public void complete(){
		Map<String, String> walletPayResponseBean = new HashMap<>();
		walletPayResponseBean.put("interfaceCode", "CMBC584001-WX_NATIVE");
		walletPayResponseBean.put("interfaceRequestID", "TD-20161212-1b3c40b48e6b");
		walletPayResponseBean.put("tranStat", "SUCCESS");
		walletPayResponseBean.put("amount", "0.01");
		walletPayResponseBean.put("responseCode", "00");
		walletPayResponseBean.put("responseMessage", "交易成功");
		walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		walletPayResponseBean.put("completeTime", String.valueOf("20161211220808"));
		walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.toString());
		walletPayResponseBean.put("interfaceOrderID", String.valueOf("WX2016092100001"));
		walletpayHessianService.complete(walletPayResponseBean);
	}

}
