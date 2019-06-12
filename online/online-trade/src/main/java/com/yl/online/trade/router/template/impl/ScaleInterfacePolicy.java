package com.yl.online.trade.router.template.impl;
//package com.lefu.online.trade.router.template.impl;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.apache.commons.lang.math.RandomUtils;
//import org.springframework.stereotype.Component;
//
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
// * 比列处理器
// * 
// * @author 聚合支付有限公司
// * @since 2016年8月3日
// * @version V1.0.0
// */
//@Component("scaleInterfacePolicy")
//public class ScaleInterfacePolicy extends InterfacePolicyTemplate {
//	
//	@Resource
//	private PayInterfaceHessianService payInterfaceHessianService;
//	
//	@Override
//	public String handleSpecifiedRouter(InterfacePolicyProfile interfacePolicyProfile, Double amount) {
//		List<InterfaceInfoForRouter> interfaceInfoForRouters = interfacePolicyProfile.getInterfaceInfos();
//		if (interfaceInfoForRouters.size() == 1) return interfaceInfoForRouters.get(0).getInterfaceCode();
//		
//		AuthBean authBean = new AuthBean();
//		authBean.setTimestamp(System.currentTimeMillis());
//		authBean.setInvokeSystem(Constants.APP_NAME);
//		authBean.setOperator("online-trade");
//		String signInfo = "";
//		Iterator<InterfaceInfoForRouter> iterator = interfaceInfoForRouters.iterator();
//		List<InterfaceInfoForRouter> finalInterfaceInfoForRouters = new ArrayList<InterfaceInfoForRouter>();
//		List<Integer> scales = new ArrayList<Integer>();
//		// 总占比
//		int totalProportion = 0;
//		while (iterator.hasNext()) {
//			InterfaceInfoForRouter interfaceInfoForRouter = iterator.next();
//			signInfo = HessianSignUtils.md5DigestAsHex(StringUtils.concatToSB(authBean.toString(), interfaceInfoForRouter.getInterfaceCode(), "payinterface").toString().getBytes());
//			authBean.setSign(signInfo);
//			// 判断接口是否被禁用
//			InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(authBean, interfaceInfoForRouter.getInterfaceCode());
//			if (interfaceInfoBean.getStatus().equals(InterfaceInfoStatus.FALSE)) continue;
//			finalInterfaceInfoForRouters.add(interfaceInfoForRouter);
//			scales.add(interfaceInfoForRouter.getScale());
//			totalProportion += interfaceInfoForRouter.getScale();
//		}
//		// 找总占比之内的随机数
//		Integer randomProporition = RandomUtils.nextInt(totalProportion);
//		
//		// 第一区间比列
//		Integer scale = scales.get(0);
//		if (randomProporition.compareTo(0) >= 0 && randomProporition.compareTo(scale) < 0) return finalInterfaceInfoForRouters.get(0).getInterfaceCode();
//		for (int cursor = 1; cursor < scales.size(); cursor++) {
//			if (randomProporition.compareTo(scale) >= 0 && randomProporition.compareTo(scales.get(cursor) + scale) < 0) return finalInterfaceInfoForRouters.get(cursor).getInterfaceCode();
//			scale = scales.get(cursor) + scale;
//		}
//		return null;
//	}
//
//}
