package com.yl.pay.pos.action.trans;

import com.pay.common.util.DateUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.Pos;
import com.yl.pay.pos.enums.RunStatus;
import com.yl.pay.pos.util.CodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Title: 交易处理  - 签退
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class SignOff extends BaseTransaction implements ITransaction {
	
	private static final Log log = LogFactory.getLog(SignOff.class);

	public ExtendPayBean execute(ExtendPayBean extendPayBean){
		log.info("####### SignIn #######");
		UnionPayBean unionPayBean=extendPayBean.getUnionPayBean();
		
		//更新POS信息		
		Pos pos = extendPayBean.getPos();
		pos.setRunStatus(RunStatus.SIGN_OFF);				//签退状态
		posDao.update(pos);
		
		//生成 检参考号
		String retrievalReferenceNumber = CodeBuilder.getPosSequnce();
		
		//设置返回报文
		unionPayBean.setAquiringInstitutionId("10000001");							//32	受理方标识码
		unionPayBean.setTimeLocalTransaction(DateUtil.formatNow("HHmmss"));		//12	受卡方所在地时间
		unionPayBean.setDateLocalTransaction(DateUtil.formatNow("MMdd"));			//13	受卡方所在地日期
		unionPayBean.setRetrievalReferenceNumber(retrievalReferenceNumber);	    //37	 检参考号
		unionPayBean.setResponseCode(Constant.SUCCESS_POS_RESPONSE);			    //39	 应答码
		return extendPayBean;
	}

}
