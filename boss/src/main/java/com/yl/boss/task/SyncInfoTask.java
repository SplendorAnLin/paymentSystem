package com.yl.boss.task;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.pay.pos.api.bean.Customer;
import com.yl.pay.pos.api.bean.CustomerFee;
import com.yl.pay.pos.api.bean.MccInfo;
import com.yl.pay.pos.api.bean.Pos;
import com.yl.pay.pos.api.bean.SecretKey;
import com.yl.pay.pos.api.bean.Shop;
import com.yl.boss.entity.SyncInfo;
import com.yl.boss.enums.Status;
import com.yl.boss.enums.SyncResult;
import com.yl.boss.service.CustomerService;
import com.yl.boss.service.SyncInfoService;
import com.yl.chat.interfaces.WechatInterface;
import com.yl.mail.MailType;
import com.yl.mail.MailUtils;
import com.yl.pay.pos.api.interfaces.InfoSync;

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;


public class SyncInfoTask {
	private static Logger logger = LoggerFactory.getLogger(SyncInfoTask.class);
	
	InfoSync pospInfoSync;
	SyncInfoService syncService;
	CustomerService customerService;
	WechatInterface wechatInterface;
	
	
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(
					new InputStreamReader(SyncInfoTask.class.getClassLoader().getResourceAsStream("system.properties")));
		} catch (IOException e) {
			logger.error("SyncInfoTask load Properties error:", e);
		}
	}
	
	public void infoPospSync(){
		long startTime = System.currentTimeMillis();
		List<SyncInfo> list=syncService.getSyncInfo(); 
		for (SyncInfo syncInfo : list) {
			if (syncInfo.getSyncCount()>Integer.valueOf(prop.getProperty("system.sync.max.count"))){
				syncInfo.setResult(SyncResult.FALSE);
				syncService.syncInfoUpdate(syncInfo);
				try {
					MailUtils.send(MailType.EXCEPTION, "posp信息同步失败【ID:"+syncInfo.getId()+"】", JsonUtils.toJsonString(syncInfo), prop.getProperty("mail.from"));
					wechatInterface.sendEX("BOSS系统", "1", "posp信息同步失败", "ID:"+syncInfo.getId());
				} catch (Exception e) {
					logger.error("发送异常信息错误");
				}
				continue;
			}
			boolean result=false;
			try {
				Date date;
				JSONObject info=JSONObject.fromObject(syncInfo.getInfo());//json对象方便下面的实体赋值
				logger.info("同步信息到posp系统：{}",syncInfo.getInfo());
				switch (syncInfo.getSyncType()) {
					case CUSTOMER_SYNC:
						Customer customer=(Customer)convertMap(Customer.class,JsonUtils.toObject(syncInfo.getInfo(), Map.class));
						date = new Date(info.getLong("createTime"));
						customer.setCreateTime(date);
						if (info.containsKey("openTime")&&info.get("openTime") != JSONNull.getInstance() ) {
							date = new Date(info.getLong("openTime"));
							customer.setOpenTime(date);
						}
						customer.setBankMcc(info.getString("mcc"));
						customer.setMcc(info.getString("mcc"));
						result=pospInfoSync.customerInfoSync(customer);
						break;
					case POS_SYNC:
						Pos pos=(Pos)convertMap(Pos.class,JsonUtils.toObject(syncInfo.getInfo(), Map.class));
						date = new Date(info.getLong("createTime"));
						pos.setCreateTime(date);
						pos.setMKey(info.getString("mkey"));
						if (info.containsKey("lastSigninTime")&&info.get("lastSigninTime") != JSONNull.getInstance() ) {
							date = new Date(info.getLong("lastSigninTime"));
							pos.setLastSigninTime(date);
						}
						result=pospInfoSync.posInfoSync(pos);
						break;
					case SHOP_SYNC:
						Shop shop=(Shop)convertMap(Shop.class,JsonUtils.toObject(syncInfo.getInfo(), Map.class));
						date = new Date(info.getLong("createTime"));
						shop.setCreateTime(date);
						shop.setCustomerNo(info.getJSONObject("customer").getString("customerNo"));
						result=pospInfoSync.shopInfoSync(shop);
						break;
					case MCC_SYNC:
						MccInfo mccInfo=(MccInfo)convertMap(MccInfo.class,JsonUtils.toObject(syncInfo.getInfo(), Map.class));
						result=pospInfoSync.mccInfoSync(mccInfo);
						break;
					case SECRET_KEY_SYNC:
						SecretKey secretKey=(SecretKey)convertMap(SecretKey.class,JsonUtils.toObject(syncInfo.getInfo(), Map.class));
						result=pospInfoSync.secretKeySync(secretKey);
						break;
					case FEE_SYNC:
						CustomerFee customerFee=(CustomerFee)convertMap(CustomerFee.class,JsonUtils.toObject(syncInfo.getInfo(), Map.class));
						customerFee.setMcc(customerService.findByCustNo(customerFee.getCustomerNo()).getMcc());
						customerFee.setComputeMode(info.getString("feeType"));
						customerFee.setRate(info.getString("fee"));
						if ("FIXED".equals(customerFee.getComputeMode())) {
							customerFee.setFixedFee(info.getDouble("fee"));
						}
						customerFee.setUpperLimitFee(info.getDouble("maxFee"));
						customerFee.setLowerLimitFee(info.getDouble("minFee"));
						customerFee.setUpperLimit(true);
						customerFee.setLowerLimit(true);
						date= new Date(info.getLong("createTime"));
						customerFee.setCreateTime(date);
						customerFee.setEffectTime(date);
						Calendar c = Calendar.getInstance();
				       	c.setTime(date);
				        c.add(Calendar.YEAR, 5);
						customerFee.setExpireTime(c.getTime());
						result=pospInfoSync.customerFeeSync(customerFee);
						break;
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			if (!result) {
				logger.info("{}类型信息同步失败,ID:{}",syncInfo.getSyncType(),syncInfo.getId());
			}else {
				syncInfo.setIsSync(Status.TRUE);
				syncInfo.setResult(SyncResult.TRUE);
			}
			syncInfo.setUpdateTime(new Date());
			syncInfo.setSyncCount(syncInfo.getSyncCount()+1);
			syncService.syncInfoUpdate(syncInfo);
		}
		logger.info("同步信息用时:" + (System.currentTimeMillis() - startTime)+ "ms");
	}
	
	public static Object convertMap(Class type, Map map)
			throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		Object obj = type.newInstance();

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();

			if (map.containsKey(propertyName)) {
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
				try {
					Object value = map.get(propertyName);
	
					Object[] args = new Object[1];
					args[0] = value;
	
					descriptor.getWriteMethod().invoke(obj, args);
				} catch (Exception e) {}
			}
		}
		return obj;
	}
	
	public InfoSync getPospInfoSync() {
		return pospInfoSync;
	}
	public void setPospInfoSync(InfoSync pospInfoSync) {
		this.pospInfoSync = pospInfoSync;
	}
	public SyncInfoService getSyncService() {
		return syncService;
	}
	public void setSyncService(SyncInfoService syncService) {
		this.syncService = syncService;
	}
	public CustomerService getCustomerService() {
		return customerService;
	}
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	public WechatInterface getWechatInterface() {
		return wechatInterface;
	}
	public void setWechatInterface(WechatInterface wechatInterface) {
		this.wechatInterface = wechatInterface;
	}
}
