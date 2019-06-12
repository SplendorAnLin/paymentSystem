package com.yl.pay.pos.action.trans;

import com.pay.common.util.DateUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.service.IIcKeyService;
import com.yl.pay.pos.service.IIcParamsService;
import com.yl.pay.pos.service.IParameterService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DownLoadIcRequest  extends BaseTransaction implements ITransaction{
	private static final Log log = LogFactory.getLog(SignIn.class);
	
	protected IParameterService parameterService;
    private IIcKeyService iIcKeyService;
    private IIcParamsService iIcParamsService;
	@Override
	public ExtendPayBean execute(ExtendPayBean extendPayBean) throws Exception {
		log.info("####### DownLoadIcRequest #######");
		UnionPayBean unionPayBean=extendPayBean.getUnionPayBean();	
		unionPayBean.setTimeLocalTransaction(DateUtil.formatNow("HHmmss"));		//12	受卡方所在地时间
		unionPayBean.setDateLocalTransaction(DateUtil.formatNow("MMdd"));		//13	受卡方所在地日期
		unionPayBean.setAquiringInstitutionId("10000001");						//32	受理方标识码
		unionPayBean.setResponseCode(Constant.SUCCESS_POS_RESPONSE);			//39	应答码
		unionPayBean.setBatchNo(extendPayBean.getPos().getBatchNo());			//60.2	批次号		
		unionPayBean.setMsgTypeCode("00");										//60.1	交易类型码
		unionPayBean.setNetMngInfoCode(unionPayBean.getNetMngInfoCode());									//60.3	网络管理信息码
		String switchDdata="";
		if("382".equals(unionPayBean.getNetMngInfoCode())){
		 switchDdata=iIcParamsService.findParamsByPosCati(unionPayBean.getCardAcceptorTerminalId());
		}else if("372".equals(unionPayBean.getNetMngInfoCode())){
		switchDdata=iIcKeyService.findKeysByPosCati(unionPayBean.getCardAcceptorTerminalId());	
		}
		unionPayBean.setSwitchingData(switchDdata);
		return extendPayBean;
	}
	public IParameterService getParameterService() {
		return parameterService;
	}
	public void setParameterService(IParameterService parameterService) {
		this.parameterService = parameterService;
	}
	public IIcKeyService getiIcKeyService() {
		return iIcKeyService;
	}
	public void setiIcKeyService(IIcKeyService iIcKeyService) {
		this.iIcKeyService = iIcKeyService;
	}
	public IIcParamsService getiIcParamsService() {
		return iIcParamsService;
	}
	public void setiIcParamsService(IIcParamsService iIcParamsService) {
		this.iIcParamsService = iIcParamsService;
	}
	
	
	
}
