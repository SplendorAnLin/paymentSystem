package com.yl.pay.pos.service.impl;

import com.pay.common.util.AmountUtil;
import com.pay.common.util.StringUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.*;
import com.yl.pay.pos.enums.TransType;
import com.yl.pay.pos.enums.YesNo;
import com.yl.pay.pos.exception.TransExceptionConstant;
import com.yl.pay.pos.exception.TransRunTimeException;
import com.yl.pay.pos.service.IExtendPayBeanService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Map;

/**
 * Title: 交易Bean 服务
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class ExtendPayBeanServiceImpl extends BaseService implements IExtendPayBeanService{

	private Log log = LogFactory.getLog(ExtendPayBeanServiceImpl.class);
	
	public ExtendPayBean loadExtendPayInfo(ExtendPayBean extendPayBean) {

		UnionPayBean unionPayBean=extendPayBean.getUnionPayBean();	

		//加载Pos
		Pos pos = posDao.findByPosCati(unionPayBean.getCardAcceptorTerminalId());	
		if(pos == null){
			throw new TransRunTimeException(TransExceptionConstant.POS_IS_NULL);
		}
		if(!Constant.STATUS_TRUE.equals(pos.getStatus().name())){
			throw new TransRunTimeException(TransExceptionConstant.POS_IS_INACTIVE);
		}
		LngAndLat lngAndLat =lngAndLatDao.findByPosCati(pos.getPosCati());
		extendPayBean.setLngAndLat(lngAndLat);
		extendPayBean.setPos(pos);		
		Map<String,String> field51Map = unionPayBean.getCurrencyCodeCardholderMap();
		if (null != field51Map){
			
			String sn = field51Map.get("D2");
			//校验SN是否一样
			if(null != sn && !pos.getPosSn().equals(sn)){
				throw new TransRunTimeException(TransExceptionConstant.POS_IS_NULL);
			}
		}
		//加载Shop
		Shop shop = shopDao.findById(pos.getShop().getId());						
		if(shop == null){
			throw new TransRunTimeException(TransExceptionConstant.SHOP_IS_NULL);
		}
//		if(!Constant.STATUS_TRUE.equals(shop.getStatus().name())){
//			throw new TransRunTimeException(TransExceptionConstant.SHOP_IS_INACTIVE);
//		}
		/*if(!shop.getShopNo().equals(unionPayBean.getCardAcceptorId())){
			//TransType.SETTLE,TransType.BATCH_UP,TransType.SIGN_OFF,TransType.SIGN_IN可以通过
			if(extendPayBean.getTransType()!=null){
				if(!(Constant.TYPANS_NOT_RESET.indexOf(extendPayBean.getTransType().name())>-1)){
					if(shop.getShopNo().startsWith("838")){
						throw new TransRunTimeException(TransExceptionConstant.SHOPNO_NOT_RESET);
					}else{
						throw new TransRunTimeException(TransExceptionConstant.SHOPNO_NOT_EUQAL);
					}
				}
			}
		}*/
		extendPayBean.setShop(shop);		
		
		//加载Customer
		Customer customer = customerDao.findById(pos.getCustomer().getId());		
		if(customer == null){
			throw new TransRunTimeException(TransExceptionConstant.CUSTOMER_IS_NULL);
		}	
		if(!Constant.STATUS_TRUE.equals(customer.getStatus().name())){
			if(TransType.SIGN_IN.equals(extendPayBean.getTransType())){
				List<TransCheckProfile> transCheckProfileli=transCheckProfileDao.findByBizTypeAndProfileTypeAndProfileValue("RECTIFY", "CUSTOMER", customer.getCustomerNo());
				if(transCheckProfileli==null||transCheckProfileli.size()<=0){
					throw new TransRunTimeException(TransExceptionConstant.CUSTOMER_IS_INACTIVE);	
				}
			}else{
				throw new TransRunTimeException(TransExceptionConstant.CUSTOMER_IS_INACTIVE);	
			}
		}
		extendPayBean.setCustomer(customer);		
			
		//金额单位转换
		if(StringUtil.notNull(unionPayBean.getAmount())){
			Double transAmount =AmountUtil.div(Double.parseDouble(unionPayBean.getAmount()), 100.00);
			extendPayBean.setTransAmount(transAmount);
		}else{
			extendPayBean.setTransAmount(0.0);
		}
		
		//判断是否IC卡插卡交易
		if("051".equals(extendPayBean.getUnionPayBean().getPosEntryModeCode())||
				"052".equals(extendPayBean.getUnionPayBean().getPosEntryModeCode())||
				"072".equals(extendPayBean.getUnionPayBean().getPosEntryModeCode())){
				extendPayBean.setIsIC(YesNo.Y);
			}else{
				extendPayBean.setIsIC(YesNo.N);
			}
		
		//判断是否复合卡
		String track2 = unionPayBean.getTrack2();
		if(StringUtils.isBlank(track2) || !track2.contains("=")){
			if(TransType.PURCHASE.equals(extendPayBean.getTransType())||TransType.PRE_AUTH.equals(extendPayBean.getTransType())){
				throw new TransRunTimeException(TransExceptionConstant.POS_TRACK2_ERROR);
			}else{
				return extendPayBean;
			}
			
		}
		String[] track = track2.split("=");
		String identify =null;
		if(track.length>1&&track[1].length()>5){
			identify = track[1].substring(4,5);
		}else{
			return extendPayBean;
		}
		if(("2".equals(identify) || "6".equals(identify))){ //2/6 代表该交易上送的卡片是复合卡
			extendPayBean.setIsComplexCard(YesNo.Y);
		}else{
			extendPayBean.setIsComplexCard(YesNo.N);
		}
		
		
		return extendPayBean;
	}
}

