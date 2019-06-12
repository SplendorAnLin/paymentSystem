package com.yl.pay.pos.interfaces.impl;

import com.yl.pay.pos.entity.TransRouteConfig;
import com.yl.pay.pos.enums.*;
import com.yl.pay.pos.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yl.pay.pos.api.bean.Customer;
import com.yl.pay.pos.api.bean.CustomerFee;
import com.yl.pay.pos.api.bean.MccInfo;
import com.yl.pay.pos.api.bean.Pos;
import com.yl.pay.pos.api.bean.SecretKey;
import com.yl.pay.pos.api.bean.Shop;
import com.yl.pay.pos.api.interfaces.InfoSync;
import com.yl.pay.pos.util.CodeBuilder;

import java.util.Date;

public class InfoSyncImpl implements InfoSync{
	private static final Log log = LogFactory.getLog(InfoSyncImpl.class);
	
	CustomerService customerService;
	OrganizationService organizationService;
	PosService posService;
	ShopService shopService;
	SecretKeyService secretKeyService;
	MccInfoService mccInfoService;
	ICustomerFeeService customerFeeService;
	ITransRouteConfigService iTransRouteConfigService;
	
	@Override
	public boolean customerInfoSync(Customer customer) {
		boolean isUpdate=true;
		com.yl.pay.pos.entity.Customer oldCustomer=customerService.findByCustomerNo(customer.getCustomerNo());
		if (oldCustomer==null) {
			isUpdate=false;
			oldCustomer=new com.yl.pay.pos.entity.Customer();
		}
		oldCustomer.setBankMcc(customer.getMcc());
		oldCustomer.setCreateTime(customer.getCreateTime());
		oldCustomer.setFullName(customer.getFullName());
		oldCustomer.setMcc(customer.getMcc());
		oldCustomer.setOpenTime(customer.getOpenTime());
		oldCustomer.setOrganization(organizationService.findByCode(customer.getOrganization()));
		oldCustomer.setShortName(customer.getShortName());
		oldCustomer.setStatus(Status.valueOf(customer.getStatus()));
		oldCustomer.setCustomerType(CustomerType.valueOf(customer.getCustomerType()));
		if (customer.getUseCustomPermission()==null) {
			oldCustomer.setUseCustomPermission(YesNo.N);
		}else {
			oldCustomer.setUseCustomPermission(YesNo.valueOf(customer.getUseCustomPermission()));
		}
		if (isUpdate) {
			return customerService.update(oldCustomer);
		}else {
			try{
				posRoute(customer.getCustomerNo(),"");
			}catch (Exception e){
				log.error("路由模版指定异常:{}",e);
			}
			oldCustomer.setCustomerNo(customer.getCustomerNo());
			return customerService.create(oldCustomer);
		}
	}

	public void posRoute(String customerNo, String transType) {
		TransRouteConfig transRouteConfig = new TransRouteConfig();
		transRouteConfig.setCode("CR000001");
		transRouteConfig.setCreateTime(new Date());
		transRouteConfig.setCustomerNo(customerNo);
		transRouteConfig.setStatus(Status.TRUE);
		transRouteConfig.setTransRouteCode("RT0041");
		transRouteConfig.setRouteType(RouteType.NORMAL);
		iTransRouteConfigService.save(transRouteConfig);
	}
	
	@Override
	public boolean posInfoSync(Pos pos) {
		boolean isUpdate=true;
		com.yl.pay.pos.entity.Pos oldPos=posService.findByPosCati(pos.getPosCati());
		if (oldPos==null) {
			isUpdate=false;
			oldPos=new com.yl.pay.pos.entity.Pos();
		}
		oldPos.setBatchNo(pos.getBatchNo());
		oldPos.setCreateTime(pos.getCreateTime());
		com.yl.pay.pos.entity.Customer customer=customerService.findByCustomerNo(pos.getCustomerNo());
		if (customer==null) {
			log.info("POS同步:商户信息未同步!");
			return false;
		}
		oldPos.setCustomer(customer);
		oldPos.setLastSigninTime(pos.getLastSigninTime());
		oldPos.setMKey(pos.getMKey());
		oldPos.setOperator(pos.getOperator());
		oldPos.setParamVersion(pos.getParamVersion());
		oldPos.setPosBrand(pos.getPosBrand());
		oldPos.setPosSn(pos.getPosSn());
		oldPos.setRouteType(RouteType.valueOf(pos.getRouteType()));
		oldPos.setPosType(pos.getPosType());
		oldPos.setRunStatus(RunStatus.valueOf(pos.getRunStatus()));
		com.yl.pay.pos.entity.Shop shop=shopService.findByShopNo(pos.getShopNo());
		if (shop==null) {
			log.info("POS同步:网点信息未同步!");
			return false;
		}
		oldPos.setShop(shop);
		oldPos.setSoftVersion(pos.getSoftVersion());
		oldPos.setStatus(Status.valueOf(pos.getStatus()));
		oldPos.setType(pos.getType());
		if (isUpdate) {
			return posService.update(oldPos);
		}else {
			oldPos.setPosCati(pos.getPosCati());
			return posService.createPos(oldPos);
		}
	}
	
	@Override
	public boolean shopInfoSync(Shop shop) {
		boolean isUpdate=true;
		com.yl.pay.pos.entity.Shop oldShop=shopService.findByShopNo(shop.getShopNo());
		if (oldShop==null) {
			isUpdate=false;
			oldShop=new com.yl.pay.pos.entity.Shop();
		}
		oldShop.setBindPhoneNo(shop.getBindPhoneNo());
		oldShop.setCreateTime(shop.getCreateTime());
		com.yl.pay.pos.entity.Customer customer=customerService.findByCustomerNo(shop.getCustomerNo());
		if (customer==null) {
			log.info("网点同步:商户信息未同步!");
			return false;
		}
		oldShop.setCustomer(customer);
		oldShop.setPrintName(shop.getPrintName());
		oldShop.setShopName(shop.getShopName());
		oldShop.setStatus(Status.valueOf(shop.getStatus()));
		try {
			if (isUpdate) {
				shopService.update(oldShop);
				return true;
			}else {
				oldShop.setShopNo(shop.getShopNo());
				shopService.create(oldShop);
				return true;
			}
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}
	
	@Override
	public boolean secretKeySync(SecretKey secretKey) {
		boolean isUpdate=true;
		com.yl.pay.pos.entity.SecretKey oldSecretKey=secretKeyService.findByKey(secretKey.getKeyName());
		if (oldSecretKey==null) {
			isUpdate=false;
			oldSecretKey=new com.yl.pay.pos.entity.SecretKey();
		}
		oldSecretKey.setKey(secretKey.getKey());
		oldSecretKey.setCheckValue(secretKey.getCheckValue());
		try {
			if (isUpdate) {
				secretKeyService.update(oldSecretKey);
				return true;
			}else {
				oldSecretKey.setKeyName(secretKey.getKeyName());
				secretKeyService.create(oldSecretKey);
				return true;
			}
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}

	@Override
	public boolean mccInfoSync(MccInfo mccInfo) {
		boolean isUpdate=true;
		com.yl.pay.pos.entity.MccInfo oldMccInfo=mccInfoService.findByMcc(mccInfo.getMcc());
		if (oldMccInfo==null) {
			isUpdate=false;
			oldMccInfo=new com.yl.pay.pos.entity.MccInfo();
		}
		oldMccInfo.setName(mccInfo.getName());
		oldMccInfo.setCategory(mccInfo.getCategory());
		oldMccInfo.setDescription(mccInfo.getDescription());
		oldMccInfo.setRate(mccInfo.getRate());
		try {
			if (isUpdate) {
				mccInfoService.update(oldMccInfo);
				return true;
			}else {
				oldMccInfo.setMcc(mccInfo.getMcc());
				mccInfoService.create(oldMccInfo);
				return true;
			}
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}

	@Override
	public boolean customerFeeSync(CustomerFee customerFee) {
		boolean isUpdate=true;
		com.yl.pay.pos.entity.CustomerFee oldCustomerFee=customerFeeService.findByCustNo(customerFee.getCustomerNo());
		if (oldCustomerFee==null) {
			isUpdate=false;
			oldCustomerFee=new com.yl.pay.pos.entity.CustomerFee();
		}
		oldCustomerFee.setCustomerNo(customerFee.getCustomerNo());
		oldCustomerFee.setMcc(customerFee.getMcc());
		oldCustomerFee.setComputeMode(ComputeFeeMode.valueOf(customerFee.getComputeMode()));
		oldCustomerFee.setRate(customerFee.getRate());
		oldCustomerFee.setFixedFee(customerFee.getFixedFee());
		oldCustomerFee.setUpperLimit(customerFee.getUpperLimit());
		oldCustomerFee.setLowerLimit(customerFee.getLowerLimit());
		oldCustomerFee.setUpperLimitFee(customerFee.getUpperLimitFee());
		oldCustomerFee.setLowerLimitFee(customerFee.getLowerLimitFee());
		oldCustomerFee.setEffectTime(customerFee.getEffectTime());
		oldCustomerFee.setExpireTime(customerFee.getExpireTime());
		oldCustomerFee.setCreateTime(new Date());
		oldCustomerFee.setIssuer("DEFAULT");
		oldCustomerFee.setCardType("DEFAULT");
		oldCustomerFee.setStatus(Status.valueOf(customerFee.getStatus()));
		try {
			if (isUpdate) {
				customerFeeService.updateCustomerFee(oldCustomerFee);
				return true;
			}else {
				oldCustomerFee.setCode(CodeBuilder.getOrderIdByUUId());//编号要查询生成
				oldCustomerFee.setCustomerNo(customerFee.getCustomerNo());
				customerFeeService.addCustomerFee(oldCustomerFee);
				return true;
			}
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}
	
	public OrganizationService getOrganizationService() {
		return organizationService;
	}
	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	public CustomerService getCustomerService() {
		return customerService;
	}
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	public PosService getPosService() {
		return posService;
	}
	public void setPosService(PosService posService) {
		this.posService = posService;
	}
	public ShopService getShopService() {
		return shopService;
	}
	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}
	public SecretKeyService getSecretKeyService() {
		return secretKeyService;
	}
	public MccInfoService getMccInfoService() {
		return mccInfoService;
	}
	public void setSecretKeyService(SecretKeyService secretKeyService) {
		this.secretKeyService = secretKeyService;
	}

	public void setMccInfoService(MccInfoService mccInfoService) {
		this.mccInfoService = mccInfoService;
	}

	public ICustomerFeeService getCustomerFeeService() {
		return customerFeeService;
	}

	public void setCustomerFeeService(ICustomerFeeService customerFeeService) {
		this.customerFeeService = customerFeeService;
	}

	public ITransRouteConfigService getiTransRouteConfigService() {
		return iTransRouteConfigService;
	}

	public void setiTransRouteConfigService(ITransRouteConfigService iTransRouteConfigService) {
		this.iTransRouteConfigService = iTransRouteConfigService;
	}
}