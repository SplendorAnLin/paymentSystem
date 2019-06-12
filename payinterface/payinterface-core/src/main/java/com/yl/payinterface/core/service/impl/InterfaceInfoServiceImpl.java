package com.yl.payinterface.core.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.service.InterfaceInfoService;
import com.yl.payinterface.core.dao.InterfaceInfoDao;
import com.yl.payinterface.core.dao.InterfaceInfoHistoryDao;
import com.yl.payinterface.core.model.InterfaceInfo;
import com.yl.payinterface.core.model.InterfaceInfoHistory;
import com.yl.payinterface.core.model.InterfaceProvider;

/**
 * 接口信息服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月22日
 * @version V1.0.0
 */
@Service("interfaceInfoService")
public class InterfaceInfoServiceImpl implements InterfaceInfoService {
	private static final Logger logger = LoggerFactory.getLogger(InterfaceProviderServiceImpl.class);
	@Resource
	private InterfaceInfoDao interfaceInfoDao;
	@Resource
	private InterfaceInfoHistoryDao interfaceInfoHistoryDao;

	@Override
	public void save(InterfaceInfo interfaceInfo, String source, String operator) {
		try {
			if (interfaceInfo == null) {
				throw new BusinessException("InterfaceInfoServiceImpl save interfaceInfo is null!");
			}
			interfaceInfo.setCreateTime(new Date());
			interfaceInfo.setVersion(System.currentTimeMillis());
			interfaceInfo.setLastModifyTime(new Date());
			interfaceInfoDao.create(interfaceInfo);

			// 接口信息历史新增
			InterfaceInfoHistory infoHistory = new InterfaceInfoHistory(interfaceInfo, source, operator);
			infoHistory.setCreateTime(interfaceInfo.getLastModifyTime());
			interfaceInfoHistoryDao.create(infoHistory);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Override
	public void modify(InterfaceInfo interfaceInfo, String source, String operator) {
		try {
			if (interfaceInfo == null) {
				throw new BusinessException("InterfaceInfoServiceImpl modify interfaceInfo is null");
			}
			InterfaceInfo interInfo = interfaceInfoDao.findByCode(interfaceInfo.getCode());
			if (interInfo == null) {
				throw new BusinessException("InterfaceInfoServiceImpl modify interInfo is null");
			}
			interInfo.setCode(interfaceInfo.getCode());
			interInfo.setType(interfaceInfo.getType());
			interInfo.setStatus(interfaceInfo.getStatus());
			interInfo.setName(interfaceInfo.getName());
			interInfo.setLastModifyTime(new Date());
			interInfo.setSingleAmountLimit(interfaceInfo.getSingleAmountLimit());
			interInfo.setProvider(interfaceInfo.getProvider());
			interInfo.setTradeConfigs(interfaceInfo.getTradeConfigs());
			interInfo.setReverseSwitch(interfaceInfo.getReverseSwitch());
			interInfo.setHandlerType(interfaceInfo.getHandlerType());
			interInfo.setDescription(interfaceInfo.getDescription());
			if (interfaceInfo.getHandlerType()!=null) {
				interInfo.setIsReal(interfaceInfo.getIsReal());
			}
			if (interfaceInfo.getFeeType()!=null) {
				interInfo.setFeeType(interfaceInfo.getFeeType());
			}
			interInfo.setSingleAmountLimitSmall(interfaceInfo.getSingleAmountLimitSmall());
			interInfo.setStartTime(interfaceInfo.getStartTime());
			interInfo.setEndTime(interfaceInfo.getEndTime());
			interInfo.setFee(interfaceInfo.getFee());
			interfaceInfoDao.update(interInfo, System.currentTimeMillis());

			// 接口信息变更记录
			InterfaceInfoHistory infoHistory = new InterfaceInfoHistory(interInfo, source, operator);
			infoHistory.setCreateTime(interInfo.getLastModifyTime());
			interfaceInfoHistoryDao.create(infoHistory);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Override
	public List<InterfaceInfo> queryInterfaceInfo() {
		return interfaceInfoDao.findInterfaceInfo();
	}

	@Override
	public List<InterfaceInfo> queryEnableByCode(List<String> codeList) {
		try {
			StringBuffer sb = new StringBuffer();
			String code = null;
			if (codeList != null && codeList.size() > 0) {
				for (int i = 0; i < codeList.size(); i++) {
					sb.append(codeList.get(i));
					if (i < codeList.size() - 1) {
						sb.append(",");
					}
				}
				code = sb.toString();
			}
			List<InterfaceInfo> list = interfaceInfoDao.findAllEnable(code);
			return list;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	@Override
	public InterfaceInfo queryByName(String name){
		return interfaceInfoDao.findByName(name);
	}
	
	@Override
	public InterfaceInfo queryByCode(String code) {
		if (StringUtils.isBlank(code)) throw new RuntimeException("InterfaceInfoServiceImpl findByCode code is null");
		return interfaceInfoDao.findByCode(code);
	}
	public InterfaceInfo queryByCodeToShow(String code) {
		if (StringUtils.isBlank(code)) throw new RuntimeException("InterfaceInfoServiceImpl findByCode code is null");
		return interfaceInfoDao.findByCodeToShow(code);
	}

	@Override
	public List<InterfaceProvider> queryInterfaceProviderInfoBy(String interfaceType, String cardType) {
		return interfaceInfoDao.findInterfaceProviderBy(interfaceType, cardType);
	}

	@Override
	public List<InterfaceInfo> queryInterfaceInfoBySupProviderCode(String interfaceType, String supportProviderCode, String cardType) {
		return interfaceInfoDao.findInterfaceInfoBySupProviderCode(interfaceType, supportProviderCode, cardType);
	}

	@Override
	public List<InterfaceProvider> queryAllInterfaceProviderInfoBy(Map<String, Object> queryParams) {
		return interfaceInfoDao.findInterfaceProviderBy(queryParams);
	}

	@Override
	public List<InterfaceInfo> queryAllEnableByInterfaceType(List<String> interfaceTypes) {
		StringBuffer sb = new StringBuffer(200);
		if(interfaceTypes != null && interfaceTypes.size() > 0){
			sb.append(Pattern.compile("\\b([\\w\\W])\\b").matcher(interfaceTypes.toString().substring(1,interfaceTypes.toString().length()-1)).replaceAll("'$1'"));
			if(interfaceTypes.size() < interfaceTypes.size()-1){
				sb.append(",");
			}
		}
		return interfaceInfoDao.queryAllEnableByInterfaceType(sb.toString());
	}
	
	public Page findAll(Page page, Map<String, Object> map){
		List<InterfaceInfo> list = interfaceInfoDao.findAll(page, map);
		page.setObject(list);
		return page;
	}

}
