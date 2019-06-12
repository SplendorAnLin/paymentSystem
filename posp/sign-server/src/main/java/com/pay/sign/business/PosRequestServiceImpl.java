package com.pay.sign.business;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pay.sign.bean.ExtendPayBean;
import com.pay.sign.bean.UnionPayBean;
import com.pay.sign.dao.PosRequestDao;
import com.pay.sign.dbentity.PosRequest;
import com.pay.sign.enums.CurrencyType;
/**
 * Title:POS原始请求  服务 
 * Description:   
 * Copyright: 2015年6月12日下午2:49:00
 * Company: SDJ
 * @author zhongxiang.ren
 */
@Component
public class PosRequestServiceImpl implements IPosRequestService{
	@Resource
	private PosRequestDao posRequestDao;
	
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
		
		//原交易信息
		posRequest.setSourceBatchNo(unionPayBean.getSourceBatchNo());
		posRequest.setSourcePosRequestId(unionPayBean.getSourcePosRequestId());
		posRequest.setSourceTransDate(unionPayBean.getSourceTranDate());
		posRequest.setSourceAuthorizationCode(unionPayBean.getAuthorizationCode());
		
		posRequest = posRequestDao.create(posRequest);		
		extendPayBean.setPosRequest(posRequest);
		
		return extendPayBean;
	}

	public ExtendPayBean complete(ExtendPayBean extendPayBean) {
				
		PosRequest posRequest = extendPayBean.getPosRequest();
		
		if(posRequest==null){													//异常处理时,如请求信息不存在,先创建请求信息
			extendPayBean = this.create(extendPayBean);
			posRequest = extendPayBean.getPosRequest();
		}		
		UnionPayBean unionPayBean = extendPayBean.getUnionPayBean();
		
		posRequest.setResponseCode(unionPayBean.getResponseCode());				//返回码
		posRequest.setExceptionCode(extendPayBean.getExceptionCode());			//异常码
		posRequest.setCompleteTime(new Date());									//完成时间
		posRequest.setExternalId(unionPayBean.getRetrievalReferenceNumber());	//参考号
		
		posRequest = posRequestDao.update(posRequest);		
		extendPayBean.setPosRequest(posRequest);
		
		return extendPayBean;
	}

}
