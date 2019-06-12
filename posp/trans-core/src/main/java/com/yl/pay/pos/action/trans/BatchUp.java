package com.yl.pay.pos.action.trans;

import com.pay.common.util.DateUtil;
import com.pay.common.util.StringUtil;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Title: 交易处理  - 批上送
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class BatchUp extends BaseTransaction implements ITransaction {
	
	private static final Log log = LogFactory.getLog(BatchUp.class);

	public ExtendPayBean execute(ExtendPayBean extendPayBean){
		
		log.info("####### BatchUp #######");
		
		UnionPayBean unionPayBean = extendPayBean.getUnionPayBean();		
		String additionalDataRequest = unionPayBean.getAdditionalData48();		
		String additionalDataResponse = "0000";
		
		if (StringUtil.notNull(additionalDataRequest)) {			
			if(additionalDataRequest.length()<40){
				additionalDataResponse = additionalDataRequest.substring(0, 4);
			}else{
				String count = additionalDataRequest.substring(0, 2);					
				additionalDataResponse = "00" + count;
			}
		}			
		//设置返回报文		
																				//11	受卡方系统跟踪号		
		unionPayBean.setTimeLocalTransaction(DateUtil.formatNow("HHmmss"));		//12	受卡方所在地时间
		unionPayBean.setDateLocalTransaction(DateUtil.formatNow("MMdd"));		//13	受卡方所在地日期
		unionPayBean.setAquiringInstitutionId("00000001");						//32	受理方标识码
																				//37	检参考号
		unionPayBean.setResponseCode("00");										//39	返回码
																				//41	终端号不变
																				//42	商户号不变
		unionPayBean.setAdditionalData48(additionalDataResponse);				//48	结算结果																				
																				//60.2	批次号不变		
		unionPayBean.setMsgTypeCode("00");										//60.1	交易类型码
		unionPayBean.setNetMngInfoCode("202");									//60.3	网络管理信息码
		return extendPayBean;
	}
}
