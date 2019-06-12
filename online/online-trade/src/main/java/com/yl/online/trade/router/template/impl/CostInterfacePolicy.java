package com.yl.online.trade.router.template.impl;
//package com.lefu.online.trade.router.template.impl;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.apache.commons.lang.math.RandomUtils;
//import org.springframework.stereotype.Component;
//
//import com.lefu.billing.core.remote.hessian.BillingHessianService;
//import com.lefu.billing.core.remote.hessian.bean.PayInterfaceDistinguish;
//import com.lefu.commons.utils.lang.StringUtils;
//import com.lefu.hessian.bean.AuthBean;
//import com.lefu.hessian.util.HessianSignUtils;
//import com.lefu.online.trade.Constants;
//import com.lefu.online.trade.bean.InterfaceInfoForRouter;
//import com.lefu.online.trade.bean.InterfacePolicyProfile;
//import com.lefu.online.trade.router.template.InterfacePolicyTemplate;
//import com.lefu.payinterface.core.remote.bean.InterfaceInfoBean;
//import com.lefu.payinterface.core.remote.enums.InterfaceInfoStatus;
//import com.lefu.payinterface.core.remote.hessian.PayInterfaceHessianService;
//
///**
// * 成本处理器
// *
// * @author 聚合支付有限公司
// * @since 2016年8月3日
// * @version V1.0.0
// */
//@Component("costInterfacePolicy")
//public class CostInterfacePolicy extends InterfacePolicyTemplate {
//
//	@Resource
//	private PayInterfaceHessianService payInterfaceHessianService;
//	
//	@Override
//	public String handleSpecifiedRouter(InterfacePolicyProfile interfacePolicyProfile, Double amount) {
//		List<InterfaceInfoForRouter> interfaceInfoForRouters = interfacePolicyProfile.getInterfaceInfos();
//		if (interfaceInfoForRouters.size() == 1) return interfaceInfoForRouters.get(0).getInterfaceCode();
//		
//		String signInfo = "";
//		List<String> interfaceCodes = new ArrayList<String>();
//		for (InterfaceInfoForRouter interfaceInfoForRouter : interfaceInfoForRouters) {
//			// 判断接口是否被禁用
//			InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(interfaceInfoForRouter.getInterfaceCode());
//			if (interfaceInfoBean.getStatus().equals(InterfaceInfoStatus.FALSE)) continue;
//			interfaceCodes.add(interfaceInfoForRouter.getInterfaceCode());
//		}
//		
//		PayInterfaceDistinguish payInterfaceDistinguish = new PayInterfaceDistinguish();
//		payInterfaceDistinguish.setCardType(interfacePolicyProfile.getCardType().toString());
//		
//		signInfo = StringUtils.concatToSB(authBean.toString(), "PAYINTERFACE", "PAYINTERFACE", 
//				interfaceCodes.toString(), payInterfaceDistinguish.toString(), Double.toString(amount), "billing").toString();
//		authBean.setSign(HessianSignUtils.md5DigestAsHex(signInfo.getBytes()));
//		
//		// 批量计算接口手续费（和接口编码位置一一对应）
//		List<Double> batchFees = billingHessianService.batchPreCompute(authBean, "PAYINTERFACE", "PAYINTERFACE", interfaceCodes, payInterfaceDistinguish, amount);
//		// 第一个成本值
//		Double minFee = batchFees.get(0);
//		// 最小成本索引
//		List<Integer> lowerFeeIndexs = new LinkedList<Integer>();
//		lowerFeeIndexs.add(0);
//		
//		for (int cursor = 1; cursor < batchFees.size(); cursor++) {
//			if (minFee.compareTo(batchFees.get(cursor)) > 0) {
//				minFee = batchFees.get(cursor);
//				lowerFeeIndexs.clear();
//				lowerFeeIndexs.add(cursor);
//			} else if (minFee.compareTo(batchFees.get(cursor)) == 0) lowerFeeIndexs.add(cursor);
//		}
//		if (lowerFeeIndexs.size() == 1) return interfaceCodes.get(lowerFeeIndexs.get(0));
//		// 随机数小于lowerFeeIndexs.size()
//		return interfaceCodes.get(lowerFeeIndexs.get(RandomUtils.nextInt(lowerFeeIndexs.size())));
//	}
//	
//
//}
