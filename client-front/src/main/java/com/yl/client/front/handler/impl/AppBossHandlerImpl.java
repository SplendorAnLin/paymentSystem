package com.yl.client.front.handler.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.bean.CustomerFee;
import com.yl.boss.api.bean.CustomerSettle;
import com.yl.boss.api.bean.DeviceBean;
import com.yl.boss.api.enums.CustomerSource;
import com.yl.boss.api.interfaces.AdInterface;
import com.yl.boss.api.interfaces.AgentInterface;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.boss.api.interfaces.DeviceInterface;
import com.yl.boss.api.interfaces.ProtocolInterface;
import com.yl.boss.api.interfaces.ShareProfitInterface;
import com.yl.client.front.common.AppRuntimeException;
import com.yl.client.front.enums.AppExceptionEnum;
import com.yl.client.front.handler.AppBossHandler;
import com.yl.client.front.service.FeeInfoService;
import com.yl.client.front.service.VersionService;
import com.yl.online.model.model.CustomerConfig;
import com.yl.online.trade.hessian.OnlineCustomerConfigHessianService;

/**
 * App运营处理实现
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
@Service("bossHandLer")
public class AppBossHandlerImpl implements AppBossHandler {

	private static Logger log = LoggerFactory.getLogger(AppBossHandler.class);
	
	@Resource
	AgentInterface agentInterface;
	
	@Resource
	CustomerInterface customerInterface;
	
	@Resource
	DeviceInterface deviceInterface;
	
	@Resource
	AdInterface adInterface;
	
	@Resource
	ProtocolInterface protocolInterface;
	
	@Resource
	FeeInfoService feeInfoService;
	
	@Resource
	AccountQueryInterface accountQueryInterface;
	
	@Resource
	ShareProfitInterface shareProfitInterface;
	
	@Resource
	VersionService versionService;
	
	@Resource
	OnlineCustomerConfigHessianService onlineCustomerConfigHessianService;
	
	// Map<String,Object> m;
	
	@Override
	public Map<String, Object> initialization(Map<String, Object> param) throws Exception {
		Map<String, Object> map =new HashMap<>();
		map.put("url","http://pay.feiyijj.com/gateway/quickOpen/appOpenCustomer");
		//deviceInterface.getDevice();
//		if (map == null) {
//			map = new HashMap<>();
//			map.put("resCode", "ERROR");
//			map.put("resMsg", "暂无可用设备分配！");
//			return map;
//		}
//		map.put("resCode", "SUCCESS");
//		map.put("resMsg", "获取成功！");
		return map;
	}
	
	
	public Map<String,Object> getAgentFee(Map<String,Object> param) throws Exception{
		try {
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("int", agentInterface.appGetAgentFeeIn(param.get("agentNo").toString()));
			m.put("out", agentInterface.appGetAgentFeeOut(param.get("agentNo").toString()));
			return m;
		} catch (Exception e) {
			log.error("查询服务商费率信息失败！错误原因{}",e);
			throw new AppRuntimeException(AppExceptionEnum.AGENT_FEE_ERR.getCode(),AppExceptionEnum.AGENT_FEE_ERR.getMessage());
		}
	}
	
	@Override
	public Map<String, Object> getCustomerFee(Map<String, Object> param) throws Exception {
		try {
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("in", feeInfoService.getCustomerFeeIn(param.get("customerNo").toString()));
			m.put("out", feeInfoService.getCustomerFeeOut(param.get("customerNo").toString()));
			return m;
		} catch (Exception e) {
			log.error("查询商户费率信息失败！错误原因{}",e);
			throw new AppRuntimeException(AppExceptionEnum.CUSTOMER_FEE_ERR.getCode(),AppExceptionEnum.CUSTOMER_FEE_ERR.getMessage());
		}
	}
	
	@Override
	public Map<String, Object> getCustomer(Map<String, Object> param) throws Exception {
		Map<String,Object> m = new HashMap<String,Object>();
		Customer customer = customerInterface.getCustomer(param.get("customerNo").toString());
		String updateFlag = "FALSE";
		if(customer != null){
			if (customer.getSource() == CustomerSource.APP_QUICK || customer.getSource() == CustomerSource.AGENT_SHARE) {
				boolean falg = customerInterface.findUpdateBaseInfoByCustNo(customer.getCustomerNo());
				if (!falg) {
					updateFlag = "TRUE";
				}
			}
			m.put("updateFlag", updateFlag);
			CustomerSettle customerSettle = customerInterface.getCustomerSettle(param.get("customerNo").toString());
			List<Map<String, Object>> custFellList = feeInfoService.getCustomerAllFee(param.get("customerNo").toString());
			List<CustomerConfig> customerConfigList = onlineCustomerConfigHessianService.queryAllByCustomerNo(param.get("customerNo").toString());
			
			Map<String,Object> accMap = new HashMap<>();
			accMap.put("customerNo", customer.getCustomerNo());
			accMap = accountQueryInterface.queryAccountBalance(accMap);
			
			List<Map<String,Object>> custFeeAll = null;
			if(custFellList != null){
				custFeeAll = new ArrayList<>();
				for (Map<String, Object> map : custFellList) {
					Map<String,Object> ma = new HashMap<>();
					ma.put("productType", map.get("productType_CN"));
					ma.put("fee", map.get("fee"));
					ma.put("minFee", map.get("minFee"));
					ma.put("maxFee", map.get("maxFee"));
					ma.put("feeType", map.get("feeType"));
					
					Boolean custConfigBool = false;
					for (CustomerConfig c : customerConfigList) {
						
						if(map.get("productType").equals(c.getProductType())){
							ma.put("minAmount", c.getMinAmount());
							ma.put("maxAmount", c.getMaxAmount());
							custConfigBool = true;
							break;
						}
					}
					
					if(!custConfigBool){
						ma.put("minAmount", -1);
						ma.put("maxAmount", -1);
					}
					
					custFeeAll.add(ma);
				}
			}
			
			m.put("cust", customer);
			m.put("custSettle", customerSettle);
			m.put("custFeeAll", custFeeAll);
			m.put("acc", accMap);
			m.put("custConfigAll", customerConfigList);
		}else {
			throw new AppRuntimeException(AppExceptionEnum.RETURNEMPTY.getCode(),AppExceptionEnum.RETURNEMPTY.getMessage());
		}
		return m;
	}

	@Override
	public Map<String, Object> getCustomerSettle(Map<String, Object> param) throws Exception {
		Map<String,Object> m = new HashMap<String,Object>();
		CustomerSettle customerSettle = customerInterface.getCustomerSettle(param.get("customerNo").toString());
		if(customerSettle != null){
			m.put("custSettle", customerSettle);
		}else {
			throw new AppRuntimeException(AppExceptionEnum.RETURNEMPTY.getCode(),AppExceptionEnum.RETURNEMPTY.getMessage());
		}
		return m;
	}

	@Override
	public Map<String, Object> getCustomerFeeList(Map<String, Object> param) throws Exception {
		Map<String,Object> m = new HashMap<String,Object>();
		List<CustomerFee> list = customerInterface.getCustomerFeeList(param.get("customerNo").toString());
		if(list != null){
			m.put("custFeeAll", list);
		}else {
			throw new AppRuntimeException(AppExceptionEnum.RETURNEMPTY.getCode(),AppExceptionEnum.RETURNEMPTY.getMessage());
		}
		return m;
	}
	
	@Override
	public Map<String, Object> getQRcode(Map<String, Object> param) throws Exception {
		//根据商户编号获取所属当前商户的所有设备信息
		List<DeviceBean> device=deviceInterface.findDeviceByCustNo(param.get("customerNo").toString());
		if (device!=null&&device.size()>0) {
			DeviceBean db = JsonUtils.toJsonToObject(device.get(0), DeviceBean.class);
			//判断当前获取到的设备是否有值，若返回则返回Null
			if(db != null){
				Map<String,Object> m = new HashMap<String,Object>();
				//根据第一个设备的收款码编号生成收款码并返回
				m.put("QRcodeUrl", db.getQrcodeUrl());
				m.put("customerPayNo", db.getCustomerPayNo());
				m.put("customerFullName", customerInterface.getCustomerFullName(db.getCustomerNo()));
				return m;
			}
		}
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("QRcodeUrl","");
		m.put("customerPayNo", "");
		m.put("customerFullName", "");
		return m;
	}
	
	@Override
	public Map<String, Object> getAdAll(Map<String, Object> param) throws Exception {
		Map<String,Object> m = new HashMap<String,Object>();
		Map<String, Object> map = adInterface.query(param.get("oem").toString());
		if(map != null){
			m.put("AdAll", map);
		}else {
			throw new AppRuntimeException(AppExceptionEnum.RETURNEMPTY.getCode(),AppExceptionEnum.RETURNEMPTY.getMessage());
		}
		return m;
	}
	
	@Override
	public Map<String, Object> getProtolAll(Map<String, Object> param) throws Exception {
		Map<String,Object> m = new HashMap<String,Object>();
		param.put("status", "OPEN");
		Page page = protocolInterface.getProtolList(param);
		if(page != null){
			m.put("ProtolAll", page);
		}else {
			throw new AppRuntimeException(AppExceptionEnum.RETURNEMPTY.getCode(),AppExceptionEnum.RETURNEMPTY.getMessage());
		}
		return m;
	}
	
	@Override
	public Map<String, Object> userFeedbackAdd(Map<String, Object> param) throws Exception {
		Map<String,Object> m = new HashMap<String,Object>();
		String userFeedbackAdd = customerInterface.AddUserFeedBack(param);
		if(userFeedbackAdd != null){
			m.put("userFeedbackAdd", userFeedbackAdd);
			return m;
		}
		throw new AppRuntimeException(AppExceptionEnum.RETURNEMPTY.getCode(),AppExceptionEnum.RETURNEMPTY.getMessage());
	}
	
	@Override
	public Map<String, Object> weeklySales(Map<String, Object> param) throws Exception {
		if(!param.get("ownerId").toString().equals("")){
			return shareProfitInterface.weeklySales(param.get("ownerId").toString());
		}
		throw new AppRuntimeException(AppExceptionEnum.RETURNEMPTY.getCode(),AppExceptionEnum.RETURNEMPTY.getMessage());
	}
	
	@Override
	public Map<String, Object> upApp(Map<String, Object> param) throws Exception{
		String sysType=param.get("sysType").toString();
		String oem=param.get("oem").toString();
		Map<String,Object> appInfo=versionService.checkVersion(sysType, oem);
		if (appInfo!=null) {
			return appInfo;
		}
		throw new AppRuntimeException(AppExceptionEnum.CLIENTTYPEERR.getCode(),AppExceptionEnum.CLIENTTYPEERR.getMessage());
	}
	
	@Override
	public Map<String, Object> addTansCard(Map<String, Object> param) throws Exception {
		if (param != null) {
			return customerInterface.addTransCard(param);
		}
		throw new AppRuntimeException(AppExceptionEnum.RETURNEMPTY.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
	}
	
	@Override
	public Map<String, Object> unlockTansCard(Map<String, Object> param) throws Exception {
		if (param.get("customerNo") != null && param.get("accountNo") != null && param.get("cardAttr") != null) {
			return customerInterface.unlockTansCard(param.get("customerNo").toString(), param.get("accountNo").toString(), param.get("cardAttr").toString());
		}
		throw new AppRuntimeException(AppExceptionEnum.RETURNEMPTY.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
	}

	@Override
	public Map<String, Object> findByCustNo(Map<String, Object> param) throws Exception {
		if (!param.get("customerNo").equals("") && param.get("customerNo") != null) {
			List<Map<String, Object>> result = customerInterface.findByCustNo(param.get("customerNo").toString());
			Map<String, Object> map = new HashMap<>();
			map.put("cardList", result);
			return map;
		}
		throw new AppRuntimeException(AppExceptionEnum.RETURNEMPTY.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
	}
	
	@Override
	public Map<String, Object> checkTransCard(Map<String, Object> param) throws Exception {
		if (param.get("customerNo") != null && param.get("accountNo") != null) {//重复验证打开注释
//			return customerInterface.checkTransCard(param);
		}
		throw new AppRuntimeException(AppExceptionEnum.RETURNEMPTY.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
	}
	
	public Map<String, Object> getWelcomeAd(Map<String, Object> param) throws Exception{
		String oem=param.get("oem").toString();
		List info=adInterface.findAdByType("WELCOME",oem);
		if (info.size()<1) {
			info=adInterface.findAdByType("WELCOME","ylzf");
		}
		Random rand = new Random();
		Map<String,Object> m = new HashMap<String,Object>();
		if (info.size()>0) {
			m.put("welcome", info.get(rand.nextInt(info.size())));
		}else {
			m.put("welcome",null);
		}
		List index=adInterface.findAdByType("INDEX",oem);
		if (info.size()<1) {
			index=adInterface.findAdByType("INDEX","ylzf");
		}
		if (index.size()>0) {
			m.put("index", index.get(rand.nextInt(index.size())));
		}else {
			m.put("index",null);
		}
		return m;
	}

}