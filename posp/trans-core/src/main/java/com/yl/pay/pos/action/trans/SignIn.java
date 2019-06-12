package com.yl.pay.pos.action.trans;

import com.pay.common.util.DateUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.Pos;
import com.yl.pay.pos.enums.RunStatus;
import com.yl.pay.pos.service.IIcParamsService;
import com.yl.pay.pos.service.IParameterService;
import com.yl.pay.pos.service.PosMessageService;
import com.yl.pay.pos.util.CodeBuilder;
import com.yl.pay.pos.util.EsscUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Title: 交易处理  - 签到
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class SignIn extends BaseTransaction implements ITransaction {
	
	private static final Log log = LogFactory.getLog(SignIn.class);

	protected IParameterService parameterService;
	protected IIcParamsService iIcParamsService;
	protected PosMessageService posMessageService;
	public ExtendPayBean execute(ExtendPayBean extendPayBean) throws Exception{
		log.info("####### SignIn #######");
		UnionPayBean unionPayBean=extendPayBean.getUnionPayBean();		
//		SecretKey secretKey=secretKeyService.findByKey("pos."+unionPayBean.getCardAcceptorTerminalId()+".zmk");
		//生成工作密钥
		String key=EsscUtils.generateCoverSecretPos(unionPayBean.getCardAcceptorTerminalId());
		String retrievalReferenceNumber = CodeBuilder.getPosSequnce();
		//更新POS信息		
		Pos pos = extendPayBean.getPos();
		pos.setRunStatus(RunStatus.SIGN_IN);				//签到状态
		pos.setLastSigninTime(new Date());					//最后签到时间
		pos.setOperator(unionPayBean.getOperator());		//签到操作员	
		
		pos = posDao.update(pos);		
		extendPayBean.setPos(pos);
		//检查Tms
		boolean needUpdate = false;
		String paramVersion = unionPayBean.getParamVersion();
		if(paramVersion!=null){
			needUpdate = parameterService.getUpdateFlag(pos.getPosCati(), unionPayBean.getParamVersion());				
		}
		//设置返回报文		
		unionPayBean.setTimeLocalTransaction(DateUtil.formatNow("HHmmss"));		//12	受卡方所在地时间
		unionPayBean.setDateLocalTransaction(DateUtil.formatNow("MMdd"));		//13	受卡方所在地日期
		unionPayBean.setAquiringInstitutionId("10000001");						//32	受理方标识码
		unionPayBean.setRetrievalReferenceNumber(retrievalReferenceNumber);		//37	检参考号
		unionPayBean.setResponseCode(Constant.SUCCESS_POS_RESPONSE);			//39	应答码

		Map<String,String> map = unionPayBean.getCurrencyCodeCardholderMap();
		if(null == map ){
			map = new HashMap<>();
			map.put("C8",extendPayBean.getCustomer().getShortName());
			unionPayBean.setCurrencyCodeCardholderMap(map);
		}
		if(needUpdate){
			//强制更新标志
			unionPayBean.setUpdateFlag("02");									//57.1	更新标志
		}else{
			unionPayBean.setUpdateFlag("00");
		}
		unionPayBean.setBatchNo(pos.getBatchNo());								//60.2	批次号		
		unionPayBean.setMsgTypeCode("00");										//60.1	交易类型码
		unionPayBean.setNetMngInfoCode("001");									//60.3	网络管理信息码	
		unionPayBean.setSwitchingData(key);				//62	终端密钥
		
		return extendPayBean;
	}

	public IParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(IParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public IIcParamsService getiIcParamsService() {
		return iIcParamsService;
	}

	public void setiIcParamsService(IIcParamsService iIcParamsService) {
		this.iIcParamsService = iIcParamsService;
	}


	public PosMessageService getPosMessageService() {
		return posMessageService;
	}

	public void setPosMessageService(PosMessageService posMessageService) {
		this.posMessageService = posMessageService;
	}




}
