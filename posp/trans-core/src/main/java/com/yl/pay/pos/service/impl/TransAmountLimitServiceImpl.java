package com.yl.pay.pos.service.impl;

import com.pay.common.util.AmountUtil;
import com.pay.common.util.DateUtil;
import com.pay.common.util.Day;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.entity.AccumulatedAmountLimit;
import com.yl.pay.pos.entity.CardAmountLimit;
import com.yl.pay.pos.entity.SingleAmountLimit;
import com.yl.pay.pos.enums.*;
import com.yl.pay.pos.exception.TransExceptionConstant;
import com.yl.pay.pos.exception.TransRunTimeException;
import com.yl.pay.pos.service.ITransAmountLimitService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;


/**
 * Title: 交易限额处理服务实现
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public class TransAmountLimitServiceImpl extends BaseService implements ITransAmountLimitService {
	private static final Log log=LogFactory.getLog(TransAmountLimitServiceImpl.class);
	
	private static Map<TransType, TransType> transTypeMap=new HashMap<TransType, TransType>(); 
	private static List<TransType> addAmountTransTypes=new LinkedList<TransType>();
	static{
		transTypeMap.put(TransType.PURCHASE, TransType.PURCHASE);
		transTypeMap.put(TransType.PURCHASE_REVERSAL, TransType.PURCHASE);
		transTypeMap.put(TransType.PURCHASE_VOID, TransType.PURCHASE);
		transTypeMap.put(TransType.PURCHASE_VOID_REVERSAL, TransType.PURCHASE);
		transTypeMap.put(TransType.PRE_AUTH, TransType.PRE_AUTH);
		transTypeMap.put(TransType.PRE_AUTH_REVERSAL, TransType.PRE_AUTH);
		transTypeMap.put(TransType.PRE_AUTH_VOID, TransType.PRE_AUTH);
		transTypeMap.put(TransType.PRE_AUTH_VOID_REVERSAL, TransType.PRE_AUTH);
		
		addAmountTransTypes.add(TransType.PURCHASE);
		addAmountTransTypes.add(TransType.PURCHASE_VOID_REVERSAL);
		addAmountTransTypes.add(TransType.PRE_AUTH);
		addAmountTransTypes.add(TransType.PRE_AUTH_VOID_REVERSAL);
	}
	
	
	@Override
	public ExtendPayBean execute(ExtendPayBean extendPayBean) {
		if(!TransType.PURCHASE.equals(extendPayBean.getTransType())&&!TransType.PRE_AUTH.equals(extendPayBean.getTransType())){
			return extendPayBean;
		}
		
		//1、POS终端级别校验
		checkSingleAmountLimit(extendPayBean, extendPayBean.getPos().getPosCati(), LimitAmountControlRole.POS);
		checkAccumulatedAmountLimit(extendPayBean, extendPayBean.getPos().getPosCati(), LimitAmountControlRole.POS);
		//2、网点级别校验
		checkSingleAmountLimit(extendPayBean, extendPayBean.getShop().getShopNo(), LimitAmountControlRole.SHOP);
		checkAccumulatedAmountLimit(extendPayBean, extendPayBean.getShop().getShopNo(), LimitAmountControlRole.SHOP);
		//3、商户级别校验
		checkSingleAmountLimit(extendPayBean, extendPayBean.getCustomer().getCustomerNo(), LimitAmountControlRole.CUSTOMER);
		checkAccumulatedAmountLimit(extendPayBean, extendPayBean.getCustomer().getCustomerNo(), LimitAmountControlRole.CUSTOMER);
		//4、系统级别校验
		checkSingleAmountLimit(extendPayBean, Constant.SYSTEM, LimitAmountControlRole.SYSTEM);
		checkAccumulatedAmountLimit(extendPayBean, Constant.SYSTEM, LimitAmountControlRole.SYSTEM);
		
		return extendPayBean;
	}
	
	/**  
	 * 单笔限额处理   
	 */
	private void checkSingleAmountLimit(ExtendPayBean extendPayBean,String ownerId,LimitAmountControlRole controlRole){
		
		List<SingleAmountLimit> posLimits=singleAmountLimitDao.findByOwnerIdAndControlRoleAndStatus(ownerId, controlRole);
		Date now=new Date();
		String bizType=extendPayBean.getTransType().name();
		String mcc=extendPayBean.getCustomer().getMcc();
		String bankCode=extendPayBean.getIssuer()==null?"":extendPayBean.getIssuer().getCode();
		String cardType=extendPayBean.getCardInfo()==null?"":extendPayBean.getCardInfo().getCardType().name();
		String cardForm="MAGNETIC_STRIPE";  //磁条卡标识
		if(YesNo.Y.equals(extendPayBean.getIsIC())){
			cardForm="IC_CARD";  //IC芯片卡标识
		}		
		if(posLimits!=null&&!posLimits.isEmpty()){
			for(SingleAmountLimit limit:posLimits){
				if(limit.getExpireTime()==null||now.compareTo(limit.getExpireTime())<=0){
					boolean matchFlag=true;
					String entryCombine=limit.getEntryCombine();
					if(StringUtils.isNotBlank(entryCombine)){
						String[] entrys=entryCombine.split("\\|");
						for(String entry:entrys){
							if(("A".equals(entry)&&!bizType.equals(limit.getBizType()))||("!A".equals(entry)&&bizType.equals(limit.getBizType()))){
								matchFlag=false; break;
							}else if(("B".equals(entry)&&!mcc.equals(limit.getMcc()))||("!B".equals(entry)&&mcc.equals(limit.getMcc()))){
								matchFlag=false; break;
							}else if(("C".equals(entry)&&!bankCode.equals(limit.getBankCode()))||("!C".equals(entry)&&bankCode.equals(limit.getBankCode()))){
								matchFlag=false; break;
							}else if(("D".equals(entry)&&!cardType.equals(limit.getCardType()))||("!D".equals(entry)&&cardType.equals(limit.getCardType()))){
								matchFlag=false; break;
							}else if(("E".equals(entry)&&!cardForm.equals(limit.getCardForm()))||("!E".equals(entry)&&cardForm.equals(limit.getCardForm()))){
								matchFlag=false; break;
							}
						}
					}
					if(matchFlag){
						//如果以上条件都符合，校验金额
						if(AmountUtil.bigger(extendPayBean.getTransAmount(), limit.getLimitAmount())){
							throw new TransRunTimeException(TransExceptionConstant.SINGLE_AMOUNT_LIMIT);
						}
					}
				}
			}
		}
		
	}
	
	/**
	 *	累计限额处理 
	 */
	private void checkAccumulatedAmountLimit(ExtendPayBean extendPayBean,String ownerId,LimitAmountControlRole controlRole){
		
		List<AccumulatedAmountLimit> posLimits=accumulatedAmountLimitDao.findByOwnerIdAndControlRoleAndStatus(ownerId, controlRole);
		Date now=new Date();
		String bizType=extendPayBean.getTransType().name();
		String mcc=extendPayBean.getCustomer().getMcc();
		String bankCode=extendPayBean.getIssuer()==null?"":extendPayBean.getIssuer().getCode();
		String cardType=extendPayBean.getCardInfo()==null?"":extendPayBean.getCardInfo().getCardType().name();
		String cardForm="MAGNETIC_STRIPE";
		if(YesNo.Y.equals(extendPayBean.getIsIC())){
			cardForm="IC_CARD";
		}
		boolean matchCard=false;
		double limitAmount=0.00;
		if(posLimits!=null&&!posLimits.isEmpty()){
			for(AccumulatedAmountLimit limit:posLimits){
				if(limit.getExpireTime()==null||now.compareTo(limit.getExpireTime())<=0){
					boolean matchFlag=true;
					String entryCombine=limit.getEntryCombine();
					if(StringUtils.isNotBlank(entryCombine)){
						String[] entrys=entryCombine.split("\\|");
						for(String entry:entrys){
							if(("A".equals(entry)&&!bizType.equals(limit.getBizType()))||("!A".equals(entry)&&bizType.equals(limit.getBizType()))){
								matchFlag=false; break;
							}else if(("B".equals(entry)&&!mcc.equals(limit.getMcc()))||("!B".equals(entry)&&mcc.equals(limit.getMcc()))){
								matchFlag=false; break;
							}else if(("C".equals(entry)&&!bankCode.equals(limit.getBankCode()))||("!C".equals(entry)&&bankCode.equals(limit.getBankCode()))){
								matchFlag=false; break;
							}else if(("D".equals(entry)&&!cardType.equals(limit.getCardType()))||("!D".equals(entry)&&cardType.equals(limit.getCardType()))){
								matchFlag=false; break;
							}else if("E".equals(entry)||"!E".equals(entry)){
								if(("E".equals(entry)&&cardForm.equals(limit.getCardForm()))||("!E".equals(entry)&&!cardForm.equals(limit.getCardForm()))){
									matchCard=true;//有对单卡交易金额限制，特殊处理
									limitAmount=limit.getLimitAmount();	
								}
								matchFlag=false; break;
							}
						}
					}
					if(matchFlag){
						//如果以上条件都符合，校验金额
						TimeScopeType timeType=limit.getTimeScopeType();
						double accumulatedAmount=limit.getAccumulatedAmount();
						Date todayFlag=DateUtil.parseToTodayDesignatedDate(new Date(), "00:00:00");
						int weekday=new Day(new Date()).getDayNumberOfWeek();
						int month=new Day(new Date()).getMonth();
						if(TimeScopeType.DAY.equals(timeType)){
							if(limit.getLastTransTime()!=null&&limit.getLastTransTime().compareTo(todayFlag)<0){
								accumulatedAmount=0.00;
							}
						}else if(TimeScopeType.WEEK.equals(timeType)&&weekday==1){
							if(limit.getLastTransTime()!=null&&limit.getLastTransTime().compareTo(todayFlag)<0){
								accumulatedAmount=0.00;
							}
						}else if(TimeScopeType.MONTH.equals(timeType)){
							if(limit.getLastTransTime()!=null&&limit.getLastTransTime().compareTo(todayFlag)<0){
								int lastMonth=new Day(limit.getLastTransTime()).getMonth();
								if(month!=lastMonth){
									accumulatedAmount=0.00;
								}
							}
						}
						
						double accumulatedAmountTemp=AmountUtil.add(accumulatedAmount, extendPayBean.getTransAmount());
						
						if(AmountUtil.bigger(accumulatedAmountTemp, limit.getLimitAmount())){
							throw new TransRunTimeException(TransExceptionConstant.ACCUMULATED_AMOUNT_LIMIT);
						}
					}
				}
			}
			//对单卡单日限额进行判断
			if(matchCard){
				//1.根据卡查找记录，若不存在则新增
				CardAmountLimit cardAmountLimit=cardAmountLimitDao.findByPan(extendPayBean.getUnionPayBean().getPan());
				if(cardAmountLimit==null){
					double accumulatedAmountTemp=extendPayBean.getTransAmount();
					if(AmountUtil.bigger(accumulatedAmountTemp, limitAmount)){
						throw new TransRunTimeException(TransExceptionConstant.CARD_AMOUNT_LIMIT);
					}
					cardAmountLimit=new CardAmountLimit();
					cardAmountLimit.setLimitAmount(limitAmount);
					cardAmountLimit.setPan(extendPayBean.getUnionPayBean().getPan());
					cardAmountLimit.setControlRole(LimitAmountControlRole.CARD);
					cardAmountLimit.setTimeScopeType(TimeScopeType.DAY);
					cardAmountLimit.setCreateTime(new Date());
					cardAmountLimit.setAccumulatedAmount(0.00);
					cardAmountLimit.setStatus(Status.TRUE);
					cardAmountLimit=cardAmountLimitDao.create(cardAmountLimit);
				}else{
					//2.查到记录后，根据日期范围进行判断，若超则返回不允许交易
					TimeScopeType timeType=cardAmountLimit.getTimeScopeType();
					if(AmountUtil.compare(limitAmount, cardAmountLimit.getLimitAmount())){
						cardAmountLimit.setLimitAmount(limitAmount);
						cardAmountLimit=cardAmountLimitDao.update(cardAmountLimit);
					}
					double accumulatedAmount=cardAmountLimit.getAccumulatedAmount();
					Date todayFlag=DateUtil.parseToTodayDesignatedDate(new Date(), "00:00:00");
					if(TimeScopeType.DAY.equals(timeType)){
						if(cardAmountLimit.getLastTransTime()!=null&&cardAmountLimit.getLastTransTime().compareTo(todayFlag)<0){
							accumulatedAmount=0.00;
						}
					}
					double accumulatedAmountTemp=AmountUtil.add(accumulatedAmount, extendPayBean.getTransAmount());
					if(AmountUtil.bigger(accumulatedAmountTemp, cardAmountLimit.getLimitAmount())){
						throw new TransRunTimeException(TransExceptionConstant.CARD_AMOUNT_LIMIT);
					}
				}
				
				
			}
		}
		
		
	}

	@Override
	public ExtendPayBean completeAccumulatedAmount(ExtendPayBean extendPayBean) {
		try {
			//1、POS终端级别校验
			completeAccumulatedAmountLimit(extendPayBean, extendPayBean.getPos().getPosCati(), LimitAmountControlRole.POS);
			//2、网点级别校验
			completeAccumulatedAmountLimit(extendPayBean, extendPayBean.getShop().getShopNo(), LimitAmountControlRole.SHOP);
			//3、商户级别校验
			completeAccumulatedAmountLimit(extendPayBean, extendPayBean.getCustomer().getCustomerNo(), LimitAmountControlRole.CUSTOMER);
			//4、系统级别校验
			completeAccumulatedAmountLimit(extendPayBean, Constant.SYSTEM, LimitAmountControlRole.SYSTEM);
		} catch (Throwable e) {
			log.info("-+-+-+-+-+completeAccumulatedAmount ",e);	
		}
		
		return extendPayBean;
	}
	
	private void completeAccumulatedAmountLimit(ExtendPayBean extendPayBean,String ownerId,LimitAmountControlRole controlRole){
		TransType transTypeTemp=transTypeMap.get(extendPayBean.getTransType());
		if(transTypeTemp==null||!Constant.SUCCESS_POS_RESPONSE.equals(extendPayBean.getResponseCode())){
			return;
		}
		boolean addAmountFlag=addAmountTransTypes.contains(extendPayBean.getTransType());
		List<AccumulatedAmountLimit> posLimits=accumulatedAmountLimitDao.findByOwnerIdAndControlRoleAndStatus(ownerId, controlRole);
		Date now=new Date();
		String bizType=transTypeTemp.name();  //
		String mcc=extendPayBean.getCustomer().getMcc();
		String bankCode=extendPayBean.getIssuer()==null?"":extendPayBean.getIssuer().getCode();
		String cardType=extendPayBean.getCardInfo()==null?"":extendPayBean.getCardInfo().getCardType().name();
		String cardForm="MAGNETIC_STRIPE";
		if(YesNo.Y.equals(extendPayBean.getIsIC())){
			cardForm="IC_CARD";
		}
		if(posLimits!=null&&!posLimits.isEmpty()){
			boolean matchCard=false;
			for(AccumulatedAmountLimit limit:posLimits){
				if(limit.getExpireTime()==null||now.compareTo(limit.getExpireTime())<=0){
					boolean matchFlag=true;
					String entryCombine=limit.getEntryCombine();
					if(StringUtils.isNotBlank(entryCombine)){
						String[] entrys=entryCombine.split("\\|");
						for(String entry:entrys){
							if(("A".equals(entry)&&!bizType.equals(limit.getBizType()))||("!A".equals(entry)&&bizType.equals(limit.getBizType()))){
								matchFlag=false; break;
							}else if(("B".equals(entry)&&!mcc.equals(limit.getMcc()))||("!B".equals(entry)&&mcc.equals(limit.getMcc()))){
								matchFlag=false; break;
							}else if(("C".equals(entry)&&!bankCode.equals(limit.getBankCode()))||("!C".equals(entry)&&bankCode.equals(limit.getBankCode()))){
								matchFlag=false; break;
							}else if(("D".equals(entry)&&!cardType.equals(limit.getCardType()))||("!D".equals(entry)&&cardType.equals(limit.getCardType()))){
								matchFlag=false; break;
							}else if("E".equals(entry)||"!E".equals(entry)){
								if(("E".equals(entry)&&cardForm.equals(limit.getCardForm()))||("!E".equals(entry)&&!cardForm.equals(limit.getCardForm()))){
									matchCard=true;
								}
								matchFlag=false; break;
							}
						}
					}
					if(matchFlag){
						//如果以上条件都符合，校验金额
						TimeScopeType timeType=limit.getTimeScopeType();
						double accumulatedAmount=limit.getAccumulatedAmount();
						Date todayFlag=DateUtil.parseToTodayDesignatedDate(new Date(), "00:00:00");
						int weekday=new Day(new Date()).getDayNumberOfWeek();
						int monthDay=new Day(new Date()).getDayOfMonth();
						if(TimeScopeType.DAY.equals(timeType)){
							if(limit.getLastTransTime()!=null&&limit.getLastTransTime().compareTo(todayFlag)<0){
								accumulatedAmount=0.00;
							}
							if(addAmountFlag){
								accumulatedAmount=AmountUtil.add(accumulatedAmount, extendPayBean.getTransAmount());
							}else{
								accumulatedAmount=AmountUtil.sub(accumulatedAmount, extendPayBean.getTransAmount());
							}
							limit.setAccumulatedAmount(accumulatedAmount);
							limit.setLastTransTime(new Date());
							accumulatedAmountLimitDao.update(limit);
						}else if(TimeScopeType.WEEK.equals(timeType)){
							if(weekday==1&&(limit.getLastTransTime()!=null&&limit.getLastTransTime().compareTo(todayFlag)<0)){
								accumulatedAmount=0.00;
							}
							if(addAmountFlag){
								accumulatedAmount=AmountUtil.add(accumulatedAmount, extendPayBean.getTransAmount());
							}else{
								accumulatedAmount=AmountUtil.sub(accumulatedAmount, extendPayBean.getTransAmount());
							}
							limit.setAccumulatedAmount(accumulatedAmount);
							limit.setLastTransTime(new Date());
							accumulatedAmountLimitDao.update(limit);
						}else if(TimeScopeType.MONTH.equals(timeType)){
							if(monthDay==1 && (limit.getLastTransTime()!=null&&limit.getLastTransTime().compareTo(todayFlag)<0)){
								accumulatedAmount=0.00;
							}
							if(addAmountFlag){
								accumulatedAmount=AmountUtil.add(accumulatedAmount, extendPayBean.getTransAmount());
							}else{
								accumulatedAmount=AmountUtil.sub(accumulatedAmount, extendPayBean.getTransAmount());
							}
							limit.setAccumulatedAmount(accumulatedAmount);
							limit.setLastTransTime(new Date());
							accumulatedAmountLimitDao.update(limit);
						}
						
					}
				}
			}
			//对单卡进行金额累计
			if(matchCard){
				//1.根据卡查找记录
				CardAmountLimit cardAmountLimit=cardAmountLimitDao.findByPan(extendPayBean.getUnionPayBean().getPan());
				if(cardAmountLimit!=null){
					TimeScopeType timeType=cardAmountLimit.getTimeScopeType();
					Date todayFlag=DateUtil.parseToTodayDesignatedDate(new Date(), "00:00:00");
					double accumulatedAmount=cardAmountLimit.getAccumulatedAmount();
					if(TimeScopeType.DAY.equals(timeType)){
						if(cardAmountLimit.getLastTransTime()!=null&&cardAmountLimit.getLastTransTime().compareTo(todayFlag)<0){
							accumulatedAmount=0.00;
						}
						if(addAmountFlag){
							accumulatedAmount=AmountUtil.add(accumulatedAmount, extendPayBean.getTransAmount());
						}else{
							accumulatedAmount=AmountUtil.sub(accumulatedAmount, extendPayBean.getTransAmount());
						}
						cardAmountLimit.setAccumulatedAmount(accumulatedAmount);
						cardAmountLimit.setLastTransTime(new Date());
						cardAmountLimitDao.update(cardAmountLimit);
					}
				}
			}
			
		}
		
	}
	

}


