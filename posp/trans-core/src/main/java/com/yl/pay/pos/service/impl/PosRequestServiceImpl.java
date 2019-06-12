package com.yl.pay.pos.service.impl;

import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.PosRequest;
import com.yl.pay.pos.entity.SysRespCustomerNoConfig;
import com.yl.pay.pos.enums.CurrencyType;
import com.yl.pay.pos.enums.TransType;
import com.yl.pay.pos.service.IPosRequestService;
import org.apache.commons.lang.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Title: POS原始请求  服务
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public class PosRequestServiceImpl extends BaseService implements IPosRequestService{

	public ExtendPayBean create(ExtendPayBean extendPayBean) {
		UnionPayBean unionPayBean=extendPayBean.getUnionPayBean();
		PosRequest posRequest = new PosRequest();
		
		posRequest.setCreateTime(new Date());
		posRequest.setTransType(extendPayBean.getTransType());
		posRequest.setAmount(extendPayBean.getTransAmount());
		posRequest.setPan(unionPayBean.getPan());
		posRequest.setShopNo(unionPayBean.getCardAcceptorId());
		posRequest.setPosCati(unionPayBean.getCardAcceptorTerminalId());
		posRequest.setPosBatch(unionPayBean.getBatchNo());
		posRequest.setPosRequestId(unionPayBean.getSystemsTraceAuditNumber());		
		posRequest.setCurrencyType(CurrencyType.CNY);
		posRequest.setOperator(unionPayBean.getOperator());
		posRequest.setRequestIp(unionPayBean.getRequestIp());
		posRequest.setCallPhoneNo(unionPayBean.getCallPhoneNo());
		String baseInfo = unionPayBean.getCustomField632();
		if(StringUtils.isNotBlank(baseInfo)&&baseInfo.length()>128){
			posRequest.setBaseStationInfo(baseInfo.substring(0, baseInfo.substring(0,128).lastIndexOf("|")));
		}else {
			posRequest.setBaseStationInfo(baseInfo);
		}
		posRequest.setSimCard(unionPayBean.getCustomField633());
		posRequest.setCustomerNo(extendPayBean.getCustomer().getCustomerNo());
		//记录主机名称和地址
		try {
			String remark=InetAddress.getLocalHost().getHostName()+"|"+InetAddress.getLocalHost().getHostAddress();
			if(remark.getBytes().length<64){
				posRequest.setRemark(remark);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		if(extendPayBean.getIsIC()!=null||extendPayBean.getIsComplexCard()!=null){
			String cardTrackInfo=(extendPayBean.getIsComplexCard()==null?"":extendPayBean.getIsComplexCard().name())+"|"
			+(extendPayBean.getIsIC()==null?"":extendPayBean.getIsIC().name());
			posRequest.setCardTrackInfo(cardTrackInfo);
		}
		
		//原交易信息
		posRequest.setSourceBatchNo(unionPayBean.getSourceBatchNo());
		posRequest.setSourcePosRequestId(unionPayBean.getSourcePosRequestId());
		posRequest.setSourceTransDate(unionPayBean.getSourceTranDate());
		posRequest.setSourceAuthorizationCode(unionPayBean.getAuthorizationCode());
		
		//冲正交易原信息处理
		if(TransType.PURCHASE_REVERSAL.name().equals(extendPayBean.getTransType().name())){
			posRequest.setSourceBatchNo(unionPayBean.getBatchNo());
			posRequest.setSourcePosRequestId(unionPayBean.getSystemsTraceAuditNumber());
		}		
			
		posRequest = posRequestDao.create(posRequest);		
		extendPayBean.setPosRequest(posRequest);
		
		return extendPayBean;
	}

	public ExtendPayBean complete(ExtendPayBean extendPayBean) {
		//TODO 
		UnionPayBean unionPayBean = extendPayBean.getUnionPayBean();
		TransType transType=extendPayBean.getTransType();
		if(TransType.PRE_AUTH.equals(transType)||TransType.PRE_AUTH_COMP.equals(transType)||TransType.PRE_AUTH_COMP_VOID.equals(transType)
				||TransType.PRE_AUTH_VOID.equals(transType)||TransType.PURCHASE.equals(transType)||TransType.PURCHASE_VOID.equals(transType)
				||TransType.SETTLE.equals(transType)||TransType.SIGN_OFF.equals(transType)){
			SysRespCustomerNoConfig respCustomerNoConf=sysRespCustomerNoConfigDao.findByCustomerNo(extendPayBean.getCustomer().getCustomerNo());
			if(respCustomerNoConf!=null){
				unionPayBean.setNoUse47(respCustomerNoConf.getBankCustomerNo());
			}
		}
				
		PosRequest posRequest = extendPayBean.getPosRequest();
		if(posRequest==null){													//异常处理时,如请求信息不存在,先创建请求信息
			extendPayBean = this.create(extendPayBean);
			posRequest = extendPayBean.getPosRequest();
		}		
		
		posRequest.setLocationInfo(extendPayBean.getLocationInfo());			//位置信息
		posRequest.setResponseCode(unionPayBean.getResponseCode());				//返回码
		posRequest.setExceptionCode(extendPayBean.getExceptionCode());			//异常码
		posRequest.setCompleteTime(new Date());									//完成时间
		posRequest.setExternalId(unionPayBean.getRetrievalReferenceNumber());	//参考号
		
		posRequest = posRequestDao.update(posRequest);		
		extendPayBean.setPosRequest(posRequest);
		
		return extendPayBean;
	}

}
