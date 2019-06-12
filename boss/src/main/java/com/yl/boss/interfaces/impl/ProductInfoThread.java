package com.yl.boss.interfaces.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.codehaus.jackson.type.TypeReference;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.entity.CustomerFee;
import com.yl.online.trade.hessian.bean.InterfaceInfoForRouterBean;
import com.yl.online.trade.hessian.bean.InterfacePolicyProfileBean;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;

import net.sf.json.JSONObject;

public class ProductInfoThread implements Callable<JSONObject>{
	private static final Logger logger = LoggerFactory.getLogger(ProductInfoThread.class);
	CustomerFee customerFee;
	Map<String, List<InterfacePolicyProfileBean>> interfacePolicyProfileBeans;
	
	private PayInterfaceHessianService payInterfaceHessianService;
	
	ProductInfoThread(CustomerFee customerFee,Map<String, List<InterfacePolicyProfileBean>> interfacePolicyProfileBeans,PayInterfaceHessianService payInterfaceHessianService){
		this.customerFee=customerFee;
		this.interfacePolicyProfileBeans=interfacePolicyProfileBeans;
		this.payInterfaceHessianService=payInterfaceHessianService;
	}

	@Override
	public JSONObject call() throws Exception {
		JSONObject temp=JSONObject.fromObject(customerFee);
		if ("REMIT".equals(customerFee.getProductType().toString())||"RECEIVE".equals(customerFee.getProductType().toString())|| 
				"POS".equals(customerFee.getProductType().toString())|| "MPOS".equals(customerFee.getProductType().toString())||
				"HOLIDAY_REMIT".equals(customerFee.getProductType().toString())||"BINDCARD_AUTH".equals(customerFee.getProductType().toString())) {
			temp.put("remark","");
		}else {
			try {
				String interfaceCode=getInterfaceCode(interfacePolicyProfileBeans,customerFee.getProductType().toString());//接口编号
				if (StringUtils.isBlank(interfaceCode)) {
					temp.put("remark","");
				}else {
					//匹配交易信息加上产品描述
		    		try {
		    			String info = payInterfaceHessianService.queryByCode(interfaceCode);
		    			InterfaceInfoBean interfaceInfoBean = JsonUtils.toObject(info, new TypeReference<InterfaceInfoBean>() {});
		    			if (interfaceInfoBean!=null) {
		    				temp.put("remark", interfaceInfoBean.getDescription());
						}else {
							temp.put("remark","");
						}
					} catch (Exception e) {
						temp.put("remark","");
						logger.error("接口备注信息查询失败,{}",e.getMessage());
					}
				}
			} catch (Exception e) {
				logger.error("接口编号查询失败,{}",e.getMessage());
				temp.put("remark","");
			}
		}
		return temp;
	}
	
	private String getInterfaceCode(Map<String, List<InterfacePolicyProfileBean>> interfacePolicyProfileBeans, String payType) {
        String interfaceCode = "";
        int index = 99;
        for (Map.Entry<String, List<InterfacePolicyProfileBean>> entry : interfacePolicyProfileBeans.entrySet()) {
            if (payType.equals(entry.getKey())) {
                for (InterfacePolicyProfileBean interfacePolicyProfileBean : entry.getValue()) {
                    List<InterfaceInfoForRouterBean> routeBeans = interfacePolicyProfileBean.getInterfaceInfos();
                    for (InterfaceInfoForRouterBean route : routeBeans) {
                        if (route.getScale() < index) {
                            index = route.getScale();
                            interfaceCode = route.getInterfaceCode();
                        }
                    }
                }
            }
        }
        return interfaceCode;
    }
	
}
