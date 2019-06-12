package com.yl.payinterface.core.remote.hessian.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Resource;

import com.yl.payinterface.core.bean.*;
import com.yl.payinterface.core.model.AliPayCollectionCode;
import com.yl.payinterface.core.service.*;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.hessian.bean.AuthBean;
import com.yl.payinterface.core.enums.CardType;
import com.yl.payinterface.core.enums.InterfaceType;
import com.yl.payinterface.core.exception.ExceptionMessages;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.model.InterfaceInfo;
import com.yl.payinterface.core.model.InterfaceProvider;
import com.yl.payinterface.core.model.InterfaceRequest;

/**
 * 支付接口服务Hessian接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月22日
 * @version V1.0.0
 */
@Service("payInterfaceHessianService")
public class PayInterfaceHessianServiceImpl implements PayInterfaceHessianService {

	@Resource
	private InterfaceProviderService interfaceProviderService;

	@Resource
	private InterfaceInfoService interfaceInfoService;

	@Resource
	private InterfaceRequestService interfaceRequestService;

	@Resource
	private AuthSaleInfoService authSaleInfoService;

	@Resource
    private AliPayCollectionCodeService aliPayCollectionCodeService;

	@Override
	public void interfaceProviderSave(AuthBean auth,InterfaceProviderBean interfaceProviderBean) {
		InterfaceProvider interfaceProvider = new InterfaceProvider();
		interfaceProvider.setCode(interfaceProviderBean.getCode());
		interfaceProvider.setFullName(interfaceProviderBean.getFullName());
		interfaceProvider.setShortName(interfaceProviderBean.getShortName());
		interfaceProviderService.save(interfaceProvider, auth.getInvokeSystem(), auth.getOperator());
	}

	@Override
	public void interfaceProviderModify(AuthBean auth,InterfaceProviderBean interfaceProviderBean) {
		InterfaceProvider interfaceProvider = new InterfaceProvider();
		interfaceProvider.setCode(interfaceProviderBean.getCode());
		interfaceProvider.setFullName(interfaceProviderBean.getFullName());
		interfaceProvider.setShortName(interfaceProviderBean.getShortName());
		interfaceProviderService.modify(interfaceProvider, auth.getInvokeSystem(), auth.getOperator());

	}

	@Override
	public void interfaceInfoSave(AuthBean auth,InterfaceInfoBean interfaceInfoBean) {
		tradeConfigSort(interfaceInfoBean);
		InterfaceInfo interfaceInfo = JsonUtils.toJsonToObject(interfaceInfoBean, InterfaceInfo.class);
		interfaceInfoService.save(interfaceInfo, auth.getInvokeSystem(), auth.getOperator());

	}

	@Override
	public void interfaceInfoModify(AuthBean auth,InterfaceInfoBean interfaceInfoBean) {
		tradeConfigSort(interfaceInfoBean);
		InterfaceInfo interfaceInfo = JsonUtils.toJsonToObject(interfaceInfoBean, InterfaceInfo.class);
		interfaceInfoService.modify(interfaceInfo, auth.getInvokeSystem(), auth.getOperator());

	}

	// 交易配置排序
	public void tradeConfigSort(InterfaceInfoBean interfaceInfoBean) {
		Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(),new TypeReference<Properties>() {});
		List<String> paramNames = new ArrayList<String>();
		for (Object key : properties.keySet()) {
			paramNames.add(key.toString());
		}
		Collections.sort(paramNames, String.CASE_INSENSITIVE_ORDER);
		Properties properties2 = new Properties();
		for (String key : paramNames) {
			properties2.put(key, properties.get(key));
		}
		interfaceInfoBean.setTradeConfigs(JsonUtils.toJsonString(properties2));
	}

	@Override
	public List<SimplifyInterfaceInfoBean> interfaceInfoQueryEnableByCode(List<String> codeList) {
		List<InterfaceInfo> interfaceInfos = interfaceInfoService.queryEnableByCode(codeList);
		List<SimplifyInterfaceInfoBean> simplifyInterfaceInfoBeans = new ArrayList<SimplifyInterfaceInfoBean>();

		for (InterfaceInfo interfaceInfo : interfaceInfos) {
			SimplifyInterfaceInfoBean simplifyInterfaceInfoBean = new SimplifyInterfaceInfoBean();
			simplifyInterfaceInfoBean.setCode(interfaceInfo.getCode());
			simplifyInterfaceInfoBean.setType(com.yl.payinterface.core.enums.InterfaceType.valueOf(interfaceInfo.getType().toString()));
			if (interfaceInfo.getCardType() != null) {
				@SuppressWarnings("unchecked")
				List<String> list = JsonUtils.toJsonToObject(interfaceInfo.getCardType(), List.class);
				List<CardType> cardTypeList = new ArrayList<>();
				for(int i = 0; i< list.size();i++){
					cardTypeList.add(com.yl.payinterface.core.enums.CardType.valueOf(list.get(i)));
				}
				simplifyInterfaceInfoBean.setCardType(cardTypeList);
			}
			simplifyInterfaceInfoBeans.add(simplifyInterfaceInfoBean);
		}
		return simplifyInterfaceInfoBeans;
	}

	@Override
	public Properties interfaceInfoQueryTradeConfigByCode(String code) {
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(code);
		if (interfaceInfo == null) throw new RuntimeException(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS);
		return JsonUtils.toObject(interfaceInfo.getTradeConfigs(), Properties.class);
	}

	@Override
	public List<InterfaceProviderBean> interfaceInfoQueryProvier(String interfaceType, String cardType) {
		List<InterfaceProvider> interfaceProviders = interfaceInfoService.queryInterfaceProviderInfoBy(interfaceType, cardType);
		if (null == interfaceProviders || interfaceProviders.isEmpty()) return null;
		return JsonUtils.toObject(JsonUtils.toJsonString(interfaceProviders), new TypeReference<List<InterfaceProviderBean>>() {});
	}

	@Override
	public List<InterfaceInfoBean> interfaceInfoQueryBySupProviderCode(String interfaceType, String supportProviderCode, String cardType) {
		if (StringUtils.isBlank(supportProviderCode)) throw new RuntimeException(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS);
		if (StringUtils.isBlank(cardType)) throw new RuntimeException(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS);
		List<InterfaceInfo> InterfaceInfos = interfaceInfoService.queryInterfaceInfoBySupProviderCode(interfaceType, supportProviderCode, cardType);
		if (null == InterfaceInfos || InterfaceInfos.isEmpty()) return null;
		return JsonUtils.toObject(JsonUtils.toJsonString(InterfaceInfos), new TypeReference<List<InterfaceInfoBean>>() {});
	}

	@Override
	public InterfaceInfoBean interfaceInfoQueryByCode(String interfaceCode) {
		return JsonUtils.toObject(JsonUtils.toJsonString(interfaceInfoService.queryByCode(interfaceCode)), InterfaceInfoBean.class);
	}

	@Override
	public List<InterfaceProviderBean> interfaceInfoQueryAllProvier(Map<String, Object> queryParams) {
		List<InterfaceProvider> interfaceProviders = interfaceInfoService.queryAllInterfaceProviderInfoBy(queryParams);
		return JsonUtils.toObject(JsonUtils.toJsonString(interfaceProviders), new TypeReference<List<InterfaceProviderBean>>() {});
	}

	@Override
	public List<InterfaceInfoBean> interfaceInfoQueryAllBy(Map<String, Object> queryParams) {
		List<InterfaceInfo> interfaceInfos = interfaceInfoService.queryInterfaceInfo();
		return JsonUtils.toObject(JsonUtils.toJsonString(interfaceInfos), new TypeReference<List<InterfaceInfoBean>>() {});
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.payinterface.core.remote.hessian.PayInterfaceHessianService#interfaceInfoCodeQueryByRemit(com.lefu.payinterface.core.remote.enums.InterfaceType)
	 */
	@Override
	public List<String> interfaceInfoCodeQueryBy(List<String> interfaceTypes) {
		List<String> interfaceInfoCode = new ArrayList<String>();
		List<InterfaceInfo> interfaceInfos = interfaceInfoService.queryAllEnableByInterfaceType(interfaceTypes);
		for (InterfaceInfo interfaceInfo : interfaceInfos) {
			interfaceInfoCode.add(interfaceInfo.getCode()+":"+interfaceInfo.getName());
		}
		return interfaceInfoCode;
	}


	@Override
	public String findByInterfaceRequestId(String interfaceRequestId) {
		InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(interfaceRequestId);
		if(interfaceRequest != null){
			return interfaceRequest.getInterfaceInfoCode();
		}
		return null;
	}

	@Override
	public AuthSaleBean findCardholderInfo(String interfaceRequestId) {
		return authSaleInfoService.findByInterfaceRequestId(interfaceRequestId);
	}

	@Override
	public Page queryAll(Page page, Map<String, Object> map) {
		page = interfaceInfoService.findAll(page, map);
		page.setObject(JsonUtils.toJsonString(page.getObject()));
		return page;
	}

	@Override
	public String queryByCode(String code) {
		InterfaceInfo interfaceInfo= interfaceInfoService.queryByCodeToShow(code);
		return JsonUtils.toJsonString(interfaceInfo);
	}
	public InterfaceInfoBean queryByName(String interfaceName){
		return JsonUtils.toObject(JsonUtils.toJsonString(interfaceInfoService.queryByName(interfaceName)), InterfaceInfoBean.class);
	}

	@Override
	public List<InterfaceInfoBean> queryInterfaceInfo() {
		List<InterfaceInfo> list = interfaceInfoService.queryInterfaceInfo();
		List<InterfaceInfoBean> beans = new ArrayList<InterfaceInfoBean>();
		for(InterfaceInfo interfaceInfo : list) {
			InterfaceInfoBean bean = new InterfaceInfoBean();
			bean.setCode(interfaceInfo.getCode());
			if (interfaceInfo.getDescription() != null) {
				bean.setDescription(interfaceInfo.getDescription());
			}
			bean.setName(interfaceInfo.getName());
			bean.setType(InterfaceType.valueOf(InterfaceType.class, interfaceInfo.getType().toString()));
			beans.add(bean);
		}
		return beans;
	}

	@Override
	public String queryByProviderCode(String providerCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateAliPayCollection(AliPayCollectionCodeBean aliPayCollectionCodeBean) {
	    Map<String, Object> info = JsonUtils.toObject(JsonUtils.toJsonString(aliPayCollectionCodeBean), new TypeReference<Map<String, Object>>() {
        });
	    info.remove("interfaceRequestID");
        aliPayCollectionCodeService.update(JsonUtils.toObject(JsonUtils.toJsonString(info), new TypeReference<AliPayCollectionCode>() {
        }));
	}

    @Override
    public void updateAliPayStatus(String aliPayAcc, String status) {
        aliPayCollectionCodeService.updateAliPayStatus(aliPayAcc, status);
    }
}