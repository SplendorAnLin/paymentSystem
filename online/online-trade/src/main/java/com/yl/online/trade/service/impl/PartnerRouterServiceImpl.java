package com.yl.online.trade.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.online.model.bean.InterfacePolicyProfile;
import com.yl.online.model.bean.PartnerRouterProfile;
import com.yl.online.model.enums.PartnerRole;
import com.yl.online.model.enums.PartnerRouterPolicySelectType;
import com.yl.online.model.enums.PartnerRouterStatus;
import com.yl.online.model.model.InterfacePolicy;
import com.yl.online.model.model.PartnerRouter;
import com.yl.online.trade.ExceptionMessages;
import com.yl.online.trade.dao.InterfacePolicyDao;
import com.yl.online.trade.dao.PartnerRouterDao;
import com.yl.online.trade.exception.BusinessRuntimeException;
import com.yl.online.trade.hessian.bean.InterfacePolicyProfileBean;
import com.yl.online.trade.hessian.bean.PartnerRouterBean;
import com.yl.online.trade.hessian.bean.PartnerRouterProfileBean;
import com.yl.online.trade.service.PartnerRouterService;

/**
 * 商戶路由服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
@Service("partnerRouterService")
public class PartnerRouterServiceImpl implements PartnerRouterService {
	private static final Logger logger = LoggerFactory.getLogger(PartnerRouterServiceImpl.class);

	@Resource
	private PartnerRouterDao partnerRouterDao;

	@Resource
	private InterfacePolicyDao interfacePolicyDao;

	@Override
	public void createPartnerRouter(PartnerRouterBean partnerRouterBean) {
		PartnerRouter partnerRouter = new PartnerRouter();
		partnerRouter.setPartnerCode(partnerRouterBean.getPartnerCode());
		partnerRouter.setPartnerRole(PartnerRole.valueOf(partnerRouterBean.getPartnerRole()));
		partnerRouter.setProfiles(JsonUtils.toJsonString(partnerRouterBean.getProfiles()));
		partnerRouter.setStatus(PartnerRouterStatus.valueOf(partnerRouterBean.getStatus()));
		partnerRouterDao.createPartnerRouter(partnerRouter);
	}

	@Override
	public Page queryPartnerRouterByPage(Page page) {
		return partnerRouterDao.queryPartnerRouterByPage(page);
	}

	@Override
	public PartnerRouterBean queryPartnerRouterByCode(String code) {
		PartnerRouter partnerRouter = partnerRouterDao.queryPartnerRouterByCode(code);
		if (partnerRouter == null) return null;
		PartnerRouterBean partnerRouterBean = new PartnerRouterBean();
		partnerRouterBean.setCode(partnerRouter.getCode());
		partnerRouterBean.setPartnerCode(partnerRouter.getPartnerCode());
		partnerRouterBean.setCreateTime(partnerRouter.getCreateTime());
		partnerRouterBean.setPartnerRole(partnerRouter.getPartnerRole().toString());
		partnerRouterBean.setProfiles(JsonUtils.toObject(partnerRouter.getProfiles(), new TypeReference<List<PartnerRouterProfileBean>>(){}));
		partnerRouterBean.setStatus(partnerRouter.getStatus().toString());
		return JsonUtils.toJsonToObject(partnerRouterBean, PartnerRouterBean.class);
//		return JsonUtils.toJsonToObject(partnerRouter, PartnerRouterBean.class);
	}

	@Override
	public void updatePartnerRouter(PartnerRouterBean partnerRouterBean) {
		PartnerRouter partnerRouter = new PartnerRouter();
		partnerRouter.setCode(partnerRouterBean.getCode());
		partnerRouter.setPartnerCode(partnerRouterBean.getPartnerCode());
		partnerRouter.setPartnerRole(PartnerRole.valueOf(partnerRouterBean.getPartnerRole()));
		partnerRouter.setProfiles(JsonUtils.toJsonString(partnerRouterBean.getProfiles()));
		partnerRouter.setStatus(PartnerRouterStatus.valueOf(partnerRouterBean.getStatus()));
		
		PartnerRouter originalPartnerRouter = partnerRouterDao.queryPartnerRouterByCode(partnerRouter.getCode());
		partnerRouter.setId(originalPartnerRouter.getId());
		partnerRouter.setCreateTime(originalPartnerRouter.getCreateTime());
		partnerRouterDao.updatePartnerRouter(partnerRouter);
	}

	@Override
	public InterfacePolicyProfile queryPartnerRouterBy(String partnerRole, String partnerCode, String interfaceType, String cardType, String interfaceProviderCode) {
		// 校验参数
		if (StringUtils.isBlank(partnerRole) || StringUtils.isBlank(partnerCode) || StringUtils.isBlank(interfaceType) || StringUtils.isBlank(cardType)) throw new BusinessRuntimeException(
				ExceptionMessages.PARAM_NOT_EXISTS);
		// 查询商户当前需要使用的规则
		PartnerRouter partnerRouter = partnerRouterDao.queryPartnerRouterBy(partnerRole, partnerCode);
		if (partnerRouter == null) throw new BusinessRuntimeException(ExceptionMessages.PARTNER_ROUTER_NOT_EXISTS);
		// 根据接口类型确认商户路由的选择方式
		List<PartnerRouterProfile> partnerRouterProfiles = JsonUtils.toObject(partnerRouter.getProfiles(), new TypeReference<List<PartnerRouterProfile>>(){});
		if (partnerRouterProfiles == null || partnerRouterProfiles.size() == 0) throw new BusinessRuntimeException(ExceptionMessages.PARTNER_ROUTER_NOT_EXISTS);

		Date validateDate = new Date();
		Iterator<PartnerRouterProfile> routerProfileIterator = partnerRouterProfiles.iterator();
		while (routerProfileIterator.hasNext()) {
			PartnerRouterProfile partnerRouterProfile = routerProfileIterator.next();
			if (interfaceType.equals(partnerRouterProfile.getInterfaceType().name())) return queryInterfacePolicyProfileBy(cardType, interfaceProviderCode, partnerRouterProfile, validateDate);
		}

		return null;
	}

	/**
	 * 查询接口策略配置
	 * @param cardType 卡种
	 * @param interfaceProviderCode 接口提供方编码
	 * @param partnerRouterProfile 合作方路由配置
	 * @param validateDate 当前系统时间
	 * @return InterfacePolicyProfile
	 */
	private InterfacePolicyProfile queryInterfacePolicyProfileBy(String cardType, String interfaceProviderCode, PartnerRouterProfile partnerRouterProfile, Date validateDate) {
		List<InterfacePolicyProfile> specifiedPolicyProfiles = null;
		// 查询参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", partnerRouterProfile.getTemplateInterfacePolicy());
		params.put("status", "TRUE");
		// 接口策略类型
		if (PartnerRouterPolicySelectType.SPECIFIED.equals(partnerRouterProfile.getPolicySelectType())) specifiedPolicyProfiles = partnerRouterProfile.getSpecifiedPolicyProfiles();
		else specifiedPolicyProfiles = JsonUtils.toObject(interfacePolicyDao.queryInterfacePolicyBy(params).get(0).getProfiles(), new TypeReference<List<InterfacePolicyProfile>>(){});
		if (specifiedPolicyProfiles == null) throw new BusinessRuntimeException(ExceptionMessages.INTERFACE_POLICY_PROFILE_NOT_EXISTS);
		
		for (InterfacePolicyProfile interfacePolicyProfile : specifiedPolicyProfiles) {
			if (cardType.equals(interfacePolicyProfile.getCardType().name()) && interfaceProviderCode.equals(interfacePolicyProfile.getInterfaceProviderCode())
					&& (validateDate.compareTo(interfacePolicyProfile.getEffectTime()) > 0 && validateDate.compareTo(interfacePolicyProfile.getExpireTime()) < 0)) {
				return interfacePolicyProfile;
			}
		}
		return null;
	}

	@Override
	public Map<String, List<InterfacePolicyProfileBean>> queryPartnerRouterByPartner(String partnerRole, String partnerCode) {
		long time=System.currentTimeMillis();
		PartnerRouter partnerRouter = partnerRouterDao.queryPartnerRouterBy(partnerRole, partnerCode);
		
		Map<String, List<InterfacePolicyProfileBean>> gatewayInterfaces = new HashMap<String, List<InterfacePolicyProfileBean>>();
		List<PartnerRouterProfile> partnerRouterProfiles = JsonUtils.toObject(partnerRouter.getProfiles(), new TypeReference<List<PartnerRouterProfile>>() {});
		// 接口类型
		String interfaceType = "";
		List<InterfacePolicyProfile> interfacePolicyProfiles = null;
		List<String> templateInterfacePolicyCodes = new ArrayList<>();
		for (PartnerRouterProfile partnerRouterProfile : partnerRouterProfiles) {
			// 指定
			if (PartnerRouterPolicySelectType.SPECIFIED.equals(partnerRouterProfile.getPolicySelectType())) {
				interfaceType = partnerRouterProfile.getInterfaceType().name();
				interfacePolicyProfiles = partnerRouterProfile.getSpecifiedPolicyProfiles();
			}
			else { // 模板
                templateInterfacePolicyCodes.add(partnerRouterProfile.getTemplateInterfacePolicy());
			}
			if(interfacePolicyProfiles != null){
                extractValidateInterfacePolicy(interfaceType, interfacePolicyProfiles, gatewayInterfaces);
            }
		}
		List<InterfacePolicy> list = interfacePolicyDao.queryInterfacePolicyByCodes(templateInterfacePolicyCodes);
		for (InterfacePolicy interfacePolicy : list){
            if (interfacePolicy == null) throw new BusinessRuntimeException(ExceptionMessages.ROUTER_MAPPER_ERROR);
            interfaceType = interfacePolicy.getInterfaceType().name();
            interfacePolicyProfiles = JsonUtils.toObject(interfacePolicy.getProfiles(), new TypeReference<List<InterfacePolicyProfile>>() {});
            extractValidateInterfacePolicy(interfaceType, interfacePolicyProfiles, gatewayInterfaces);
        }
        logger.info("路由用时:"+(System.currentTimeMillis()-time));
		return gatewayInterfaces;
	}

	private void extractValidateInterfacePolicy(String interfaceType, List<InterfacePolicyProfile> 
		interfacePolicyProfiles, Map<String, List<InterfacePolicyProfileBean>> gatewayInterfaces) {
		Date currentDate = new Date();
		List<InterfacePolicyProfileBean> interfacePolicyProfileBeans = null;
		if (gatewayInterfaces.containsKey(interfaceType))
			for (InterfacePolicyProfile interfacePolicyProfile : interfacePolicyProfiles) {
				// 在有效期内
				if (interfacePolicyProfile.getEffectTime().compareTo(currentDate) <= 0 && interfacePolicyProfile.getExpireTime().compareTo(currentDate) >= 0)
					for (InterfacePolicyProfileBean interfacePolicyProfileBean : gatewayInterfaces.get(interfaceType)) {
						if (interfacePolicyProfile.getCardType().equals(interfacePolicyProfileBean.getCardType()) && interfacePolicyProfile.getInterfaceProviderCode().equals(interfacePolicyProfile.getInterfaceProviderCode())) continue;
						gatewayInterfaces.get(interfaceType).add(JsonUtils.toJsonToObject(interfacePolicyProfile, InterfacePolicyProfileBean.class));
					}
			}
		else {
			interfacePolicyProfileBeans = new ArrayList<InterfacePolicyProfileBean>();
			for (InterfacePolicyProfile interfacePolicyProfile : interfacePolicyProfiles) {
				// 在有效期内
				if (interfacePolicyProfile.getEffectTime().compareTo(currentDate) <= 0 && interfacePolicyProfile.getExpireTime().compareTo(currentDate) >= 0)
					interfacePolicyProfileBeans.add(JsonUtils.toJsonToObject(interfacePolicyProfile, InterfacePolicyProfileBean.class));
			}
			gatewayInterfaces.put(interfaceType, interfacePolicyProfileBeans);
		}
	}

	@Override
	public Object findAllPartnerRouter(Page page, Map<String, Object> params) {
		String resultStr = "";
		List<PartnerRouter> partnerRouters = partnerRouterDao.findAllPartnerRouter(page,params);
		try {
			page.setObject(partnerRouters);
			return page;
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return resultStr;
	
	}

	@Override
	public List<Map<String, Object>> queryCustRouterByCustomerNo(String customerNo) {
		return partnerRouterDao.queryCustRouterByCustomerNo(customerNo);
	}
}
