package com.yl.online.trade.remote.hessian.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.yl.online.trade.hessian.OnlinePartnerRouterHessianService;
import com.yl.online.trade.hessian.bean.InterfacePolicyProfileBean;
import com.yl.online.trade.hessian.bean.PartnerRouterBean;
import com.yl.online.trade.service.PartnerRouterService;

/**
 * 商户路由服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月13日
 * @version V1.0.0
 */
@Service("onlinePartnerRouterHessianService")
public class OnlinePartnerRouterHessianServiceImpl implements OnlinePartnerRouterHessianService {
	
	@Resource
	private PartnerRouterService partnerRouterService;

	public void createPartnerRouter(PartnerRouterBean partnerRouterBean) {
		partnerRouterService.createPartnerRouter(partnerRouterBean);
	}

	public Page queryPartnerRouterByPage(Page page) {
		return partnerRouterService.queryPartnerRouterByPage(page);
	}

	public PartnerRouterBean queryPartnerRouterByCode(String code) {
		return partnerRouterService.queryPartnerRouterByCode(code);
	}

	public void updatePartnerRouterByPage(PartnerRouterBean partnerRouterBean) {
		partnerRouterService.updatePartnerRouter(partnerRouterBean);
	}

	public Map<String, List<InterfacePolicyProfileBean>> queryPartnerRouterBy(String partnerRole, String partnerCode) {
		return partnerRouterService.queryPartnerRouterByPartner(partnerRole, partnerCode);
	}

	 
	public Object  findAllPartnerRouter(Page page, Map<String, Object> params) {
		if(params!=null&&params.size()>1){
			if(null!=params.get("createStartTime")&&!"".equals(params.get("createStartTime"))){
				params.put("createStartTime", ((Date) params.get("createStartTime")).getTime()/1000);
			}
			if(null!=params.get("createEndTime")&&!"".equals(params.get("createEndTime"))){
				params.put("createEndTime", ((Date) params.get("createEndTime")).getTime()/1000);
			}
		}
		return partnerRouterService.findAllPartnerRouter(page, params);
	}

	@Override
	public List<Map<String, Object>> queryCustRouterByCustomerNo(String customerNo) {
		return partnerRouterService.queryCustRouterByCustomerNo(customerNo);
	}
	

}
