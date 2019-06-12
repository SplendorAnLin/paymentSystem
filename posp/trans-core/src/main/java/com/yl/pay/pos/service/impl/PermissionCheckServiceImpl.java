package com.yl.pay.pos.service.impl;

import com.pay.common.util.PropertyUtil;
import com.pay.common.util.StringUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.LocationQueryBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.*;
import com.yl.pay.pos.enums.CardType;
import com.yl.pay.pos.enums.RunStatus;
import com.yl.pay.pos.enums.TransType;
import com.yl.pay.pos.enums.YesNo;
import com.yl.pay.pos.exception.TransExceptionConstant;
import com.yl.pay.pos.exception.TransRunTimeException;
import com.yl.pay.pos.service.IPermissionCheckService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Title: 交易权限检查
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class PermissionCheckServiceImpl extends BaseService implements IPermissionCheckService{
	private static final Log log=LogFactory.getLog(PermissionCheckServiceImpl.class);
//	private DuplicatePOSRequestDetectService duplicatePOSRequestDetectService;
	private static PropertyUtil propertyUtil =null;
	static{
		propertyUtil = PropertyUtil.getInstance("transConfig");
	}
	
	
	public ExtendPayBean execute(ExtendPayBean extendPayBean){
		
//		long randomKey = (long)(Math.random() * Long.MAX_VALUE);
//		boolean isDuplicateDetect = TransType.PURCHASE.equals(extendPayBean.getTransType()) || TransType.PRE_AUTH.equals(extendPayBean.getTransType());
//		//只检测消费和预授权，检测是根据原始报文检测，因此只有存在原始报文的情况下才检测
//		if(isDuplicateDetect && extendPayBean.getUnionPayBean() !=null && StringUtil.notNull(extendPayBean.getUnionPayBean().getOrgIso8583Msg()) && duplicatePOSRequestDetectService.detectDuplicatePOSRequest(randomKey, extendPayBean.getUnionPayBean())) {
//			try {
//				Thread.sleep(90000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			throw new TransRunTimeException(TransExceptionConstant.DUPLICATE_POS_REQUEST);
//		}
		
		//预付款类交易屏蔽
		String detailInqrng = extendPayBean.getUnionPayBean().getDetailInqrng();
		if(StringUtils.isNotBlank(detailInqrng)){
			throw new TransRunTimeException(TransExceptionConstant.TRANSTYPE_NOT_SUPPORT);
		}
		
		if(extendPayBean.getBankIdNumber()!=null){
			if(Constant.WILD_CARD.equals(extendPayBean.getBankIdNumber().getCardStyle())){
				throw new TransRunTimeException(TransExceptionConstant.WILD_CARD);
			}
		}		
		TransType transType = extendPayBean.getTransType();
		
		//限制消费撤销冲正、预授权完成撤销冲正 ,金额超20000不允许交易
		if(TransType.PURCHASE_VOID_REVERSAL.equals(transType)||TransType.PRE_AUTH_COMP_VOID_REVERSAL.equals(transType)){
			throw new TransRunTimeException(TransExceptionConstant.VOID_REVERSAL_AMOUNT_LIMIT);
		}
		
		//管理类及冲正类,通知类不检查
		if(TransType.SIGN_IN.name().equals(transType.name())||
		    TransType.SIGN_OFF.name().equals(transType.name())||
			TransType.SETTLE.name().equals(transType.name())||
			TransType.BATCH_UP.name().equals(transType.name())||
			TransType.PURCHASE_REVERSAL.name().equals(transType.name())||
			TransType.PURCHASE_VOID_REVERSAL.name().equals(transType.name())||
			TransType.PRE_AUTH_REVERSAL.name().equals(transType.name())||
			TransType.PRE_AUTH_VOID_REVERSAL.name().equals(transType.name())||
			TransType.PRE_AUTH_COMP_REVERSAL.name().equals(transType.name())||
			TransType.PRE_AUTH_COMP_VOID_REVERSAL.name().equals(transType.name())||
				TransType.DOWNLOAD_MAIN_KEY.name().equals(transType.name())||
			TransType.DOWNLOAD_IC_REQUEST.name().equals(transType.name())||
			TransType.DOWNLOAD_IC_TRANSFER.name().equals(transType.name())||
			TransType.DOWNLOAD_IC_END.name().equals(transType.name())||
			TransType.SPECIFY_QUANCUN_REVERSAL.name().equals(transType.name())||
			TransType.NOT_SPECIFY_QUANCUN_REVERSAL.name().equals(transType.name())||
			TransType.CASH_RECHARGE_QUNCUN_REVERSAL.name().equals(transType.name())||
			TransType.BALANCE_INQUIRY_NOTICE.name().equals(transType.name())||
			TransType.PURCHASE_SCRIPT_NOTICE.name().equals(transType.name())||
			TransType.PREAUTH_SCRIPT_NOTICE.name().equals(transType.name())||
			TransType.SPECIFY_QUANCUN_NOTICE.name().equals(transType.name())||
			TransType.NOT_SPECIFY_QUANCUN_NOTICE.name().equals(transType.name())||
			TransType.CASH_RECHARGE_QUNCUN_NOTICE.name().equals(transType.name())
			){
			return extendPayBean;			
		}		
		boolean hasAuth = check(extendPayBean);
		if(!hasAuth){
			throw new TransRunTimeException(TransExceptionConstant.TRANSTYPE_NOT_PERMIT);
		}
		
		//校验交易需要强制更新
//		verifyTmsUpdate(extendPayBean);
		//校验拨号POS来电号码
//		verifyPhone(extendPayBean);
		//校验试点地区固化POS是否降级
//		if(TransType.PURCHASE.name().equals(transType.name()) || TransType.PRE_AUTH.name().equals(transType.name())){
//			verifyICDemotion(extendPayBean);
//		}
		//校验无线POS位置信息
//		verifyPosLocation(extendPayBean);
		//校验黑名单
		verifyBlackList(extendPayBean);
		//校验POS参数版本号
		verifyPosVersion(extendPayBean);
		//校验卡信息
		verifyCardInfo(extendPayBean);
		return extendPayBean;
	}
	
	

	/**
	 * 校验黑名单
	 * @param extendPayBean
	 * @return
	 */
	private ExtendPayBean verifyBlackList(ExtendPayBean extendPayBean){
		String pan = extendPayBean.getUnionPayBean().getPan();
		if(StringUtils.isNotBlank(pan)){
			List<TransCheckProfile> checkProfiles=transCheckProfileDao.findByBizTypeAndProfileTypeAndProfileValue("PanBlacklist","PAN", extendPayBean.getUnionPayBean().getPan());
			if(checkProfiles!=null&&checkProfiles.size()>0){
				log.info("check black list pan = ["+pan+"]");
				throw new TransRunTimeException(TransExceptionConstant.BLACK_LIST_CHECK);
			}
		}
		return extendPayBean;
	}
	/**
	 * 校验卡信息
	 * @param extendPayBean
	 * @return
	 */
	private ExtendPayBean verifyCardInfo(ExtendPayBean extendPayBean){
		//暂对“发现卡”进行限制
		if(extendPayBean.getCardInfo()!=null){
			if(Constant.CARD_MARK_1.equals(extendPayBean.getCardInfo().getPan().substring(0, 4))){
				throw new TransRunTimeException(TransExceptionConstant.TRX_CARDTYPE_LIMIT);
			}
		}
		
		return extendPayBean;
	}


	/**
	 * @param extendPayBean
	 */
	private ExtendPayBean verifyTmsUpdate(ExtendPayBean extendPayBean) {
		if(TransType.PURCHASE.equals(extendPayBean.getTransType())
				||TransType.PRE_AUTH.equals(extendPayBean.getTransType())){
			Pos pos=extendPayBean.getPos();
			UnionPayBean unionPayBean=extendPayBean.getUnionPayBean();
			
			if(pos.getType().equals(Constant.C930E)){
				String softVersion=(StringUtil.isNull(unionPayBean.getSoftVersion())?pos.getSoftVersion():unionPayBean.getSoftVersion().trim());
				List<TransCheckProfile> transCheckProfiles=transCheckProfileDao.findByBizTypeAndProfileTypeAndProfileValue("TmsUpdate", "PosSn", pos.getPosSn());
				if(Constant.C930E_SOFT_VERSION.compareTo(softVersion)>0&&transCheckProfiles!=null&&transCheckProfiles.size()>0){
					throw new TransRunTimeException(TransExceptionConstant.MANDATORY_UPDATE);	
				}
				
			}
		}
		return extendPayBean;
	}

	/**
	 * 校验无线POS终端位置信息
	 * @param extendPayBean
	 * @return
	 */
	private ExtendPayBean verifyPosLocation(ExtendPayBean extendPayBean) {
		String locationInfo=extendPayBean.getUnionPayBean().getCustomField632();
		String requestIp=extendPayBean.getUnionPayBean().getRequestIp();
//		String requestIp="61.148.10.10";
		
		if(TransType.PURCHASE.equals(extendPayBean.getTransType())){
			if(StringUtils.isNotBlank(locationInfo)||StringUtils.isNotBlank(requestIp)){
				//使用独立线程处理终端位置校验
				Future<String> locationQueryFuture=LocationQueryService.verifyLocation(new LocationQueryBean(locationInfo, requestIp),extendPayBean);	
				extendPayBean.setLocationQueryFuture(locationQueryFuture);
			}
		}
		
		return extendPayBean;
	}
	
	private ExtendPayBean verifyPosVersion(ExtendPayBean extendPayBean){
		if(TransType.PURCHASE.equals(extendPayBean.getTransType())
				||TransType.PRE_AUTH.equals(extendPayBean.getTransType())){
			Pos pos=extendPayBean.getPos();
			if(RunStatus.SIGN_OFF.equals(pos.getRunStatus())){
				throw new TransRunTimeException(TransExceptionConstant.POS_NEED_RESIGNIN);
			}
		}
		return extendPayBean;
	}
	
	private ExtendPayBean verifyPhone(ExtendPayBean extendPayBean) {
		UnionPayBean unionPayBean=extendPayBean.getUnionPayBean();
		// 拨号POS来电号码
		String callPhoneNo = unionPayBean.getCallPhoneNo();
		TransType transType = extendPayBean.getTransType();
		Shop shop=extendPayBean.getShop();
		if(StringUtils.isNotBlank(callPhoneNo)&&!propertyUtil.getProperty("verifyPhone").equals(callPhoneNo)){
			//只在消费交易，退货交易,预授权和预授权完成时需要校验来电号码
			if(TransType.PURCHASE.equals(transType)||TransType.PURCHASE_REFUND.equals(transType)
					||TransType.PRE_AUTH.equals(transType)||TransType.PRE_AUTH_COMP.equals(transType)){
				
				String bindPhoneNo = shop.getBindPhoneNo();
				
				if(StringUtils.isBlank(bindPhoneNo)){
					throw new TransRunTimeException(TransExceptionConstant.DIAL_NUMBER_ERROR);
				}
				
				if("NO_BIND".equals(bindPhoneNo)){  //特殊处理
					return extendPayBean;
				}
				
				long callPhoneNoForLong = Long.parseLong(callPhoneNo);
				String callPhoneNoTemp = String.valueOf(callPhoneNoForLong);
				String[] usablePhoneArr = bindPhoneNo.split(",");
				boolean flag=false;
				for(String temp : usablePhoneArr){
					temp = temp.trim().replace("-", "");//兼容010-12345678类型号码
					int i = temp.indexOf(callPhoneNoTemp);
					if(i <= -1){
						continue;
					}else if(i >= 0 && i <= 1){
						if(temp.endsWith(callPhoneNoTemp)){
							flag= true;
						}
					}else{
						//如果是010开头，则callPhone中不附加区号，而设置号码时添加了区号，兼容添加区号的号码
//						if(temp.substring(0, 3).equals("010") && temp.endsWith(callPhoneNoTemp)){
//							flag= true;
//						}
						//modify by jun.yu 外地号码没有区号的情况
						if(callPhoneNoTemp.length()>=7&& temp.endsWith(callPhoneNoTemp)){
							flag=true;
						}
					}
				}
				
				if(!flag){
					throw new TransRunTimeException(TransExceptionConstant.DIAL_NUMBER_ERROR);
				}
				
			}
		}
		
		return extendPayBean;
	}
	
	public boolean check(ExtendPayBean extendPayBean) {

		TransType transType = extendPayBean.getTransType();
		CardType cardType = extendPayBean.getCardInfo().getCardType();
		Customer customer = extendPayBean.getCustomer();
		// 只有贷记卡能做预授权
		if((TransType.PRE_AUTH.equals(transType) || TransType.PRE_AUTH_VOID.equals(transType)
				|| TransType.PRE_AUTH_COMP.equals(transType) || TransType.PRE_AUTH_COMP_VOID.equals(transType))
				&& !CardType.CREDIT_CARD.equals(cardType)){
			throw new TransRunTimeException(TransExceptionConstant.TRX_CARDTYPE_LIMIT);
		}
		
		//商户级				
		if(YesNo.Y.name().equals(customer.getUseCustomPermission().name())){
			CustomerTransFunction customerTransFunction = customerTransFunctionDao.findByCustomerNo(customer.getCustomerNo());
			if(null != customerTransFunction && "TRUE".equals(customerTransFunction.getStatus())){
				CustomerTransSort customerTransSort = customerTransSortDao.findBySortAndTransType(customerTransFunction.getSortCode(), transType.name());
				if(null != customerTransSort && "TRUE".equals(customerTransSort.getStatus().name())){
					return true;
				}
			}
			return false;
		}		
		
		//行业级	
		List<IndustryFunction> industryFunctionList = industryFunctionDao.findByMcc(customer.getMcc());
		if(industryFunctionList!=null && industryFunctionList.size()>0){			
			for(IndustryFunction industryFunction : industryFunctionList){
				if(industryFunction.getTrsanType().equals(transType.name())){
					return true;
				}				
			}
			return false;
		}
		
		//系统默认级
		PropertyUtil propertyUtil = PropertyUtil.getInstance("posp");
		String defaultFunction = propertyUtil.getProperty("trans.default.function");		
		if(defaultFunction.indexOf(transType.toString())>-1){
			return true;
		}	
		
		return false;
	}
	
	private ExtendPayBean verifyICDemotion(ExtendPayBean extendPayBean){
		//发卡行标识代码
		BankIdNumber bankIdNumber = extendPayBean.getBankIdNumber();
		if(null != bankIdNumber){
			Bank bank = bankIdNumber.getBank();
			if(null != bank){
				String bankCode = bank.getCode();
				String verifyCode = bankIdNumber.getVerifyCode();
				//降级交易拦截白名单
				DemotionWhiteBill demotionWhiteBill = demotionWhiteBillDao.findByCodeAndLength(bankCode, 
						verifyCode,bankIdNumber.getPanLength(),bankIdNumber.getVerifyLength());
				//降级交易拦截白名单不为空，则return，否则继续校验
				if(null != demotionWhiteBill){
					return extendPayBean;
				}
			}
		}	
		UnionPayBean unionPayBean=extendPayBean.getUnionPayBean();
		//指定终端关闭降级交易
		/*String posCati = extendPayBean.getPos().getPosCati();
		if(Constant.CHECK_POSCATI_LIST.contains(posCati)){
			String track2 = unionPayBean.getTrack2();
			if(StringUtils.isBlank(track2) || !track2.contains("=")){
				return extendPayBean;
			}
			String[] track = track2.split("=");
			String identify = track[1].substring(4,5);
			
			String icSystemRelated = unionPayBean.getICSystemRelated(); //IC芯片数据
			if(("2".equals(identify) || "6".equals(identify)) && StringUtils.isBlank(icSystemRelated)){ //2/6 代表该交易上送的卡片是复合卡
				log.info("posCati = "+posCati+" check error ..");
				throw new TransRunTimeException(TransExceptionConstant.POS_DEMOTION);
			}
		}
		*/
		//获取对所有类型POS关闭降级的省编号
//		String allCheckPilotAreas = propertyUtil.getProperty("allCheckPilotAreas");
//		Organization organization = extendPayBean.getCustomer().getOrganization();
//		if(null == organization){
//			return extendPayBean;
//		}
//		String code = organization.getCode();
//		if(StringUtils.isBlank(code)){
//			return extendPayBean;
//		}
		//获得地区码
//		code = code.substring(0,2);
//		if(!allCheckPilotAreas.contains(code)){
//			// 拨号POS来电号码
//			String callPhoneNo = unionPayBean.getCallPhoneNo();
//			if(StringUtils.isBlank(callPhoneNo)){
//				return extendPayBean;
//			}
//			String pilotAreas = propertyUtil.getProperty("pilotAreas");
//			if(!pilotAreas.contains(code)){
//				return extendPayBean;
//			}
//		}
		String track2 = unionPayBean.getTrack2();
		if(StringUtils.isBlank(track2) || !track2.contains("=")){
			return extendPayBean;
		}
		String[] track = track2.split("=");
		if(track[1].length()<5){
			return extendPayBean;
		}
		String identify = track[1].substring(4,5);
		
		String icSystemRelated = unionPayBean.getICSystemRelated(); //IC芯片数据
		if(("2".equals(identify) || "6".equals(identify)) && StringUtils.isBlank(icSystemRelated)){ //2/6 代表该交易上送的卡片是复合卡
			log.info("fix Pos POS_DEMOTION check error");
			throw new TransRunTimeException(TransExceptionConstant.POS_DEMOTION);
		}
		return extendPayBean;
	}

//	public DuplicatePOSRequestDetectService getDuplicatePOSRequestDetectService() {
//		return duplicatePOSRequestDetectService;
//	}
//
//	public void setDuplicatePOSRequestDetectService(
//			DuplicatePOSRequestDetectService duplicatePOSRequestDetectService) {
//		this.duplicatePOSRequestDetectService = duplicatePOSRequestDetectService;
//	}
//	
}
