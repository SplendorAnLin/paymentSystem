package com.yl.pay.pos.action.trans;

import com.pay.common.util.DateUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.service.IIcKeyService;
import com.yl.pay.pos.service.IIcParamsService;
import com.yl.pay.pos.service.IParameterService;
import com.yl.pay.pos.util.CodeBuilder;
import com.yl.pay.pos.util.Tlv;
import com.yl.pay.pos.util.TlvUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

public class DownLoadIcTransfer extends BaseTransaction  implements ITransaction {
	private static final Log log = LogFactory.getLog(SignIn.class);
	
	protected IParameterService parameterService;
	private IIcParamsService iIcParamsService;
	private IIcKeyService iIcKeyService;
	@Override
	public ExtendPayBean execute(ExtendPayBean extendPayBean) throws Exception {
		log.info("####### DownLoadIcTransfer #######");
		UnionPayBean unionPayBean=extendPayBean.getUnionPayBean();	
		String retrievalReferenceNumber = CodeBuilder.getPosSequnce();
		unionPayBean.setTimeLocalTransaction(DateUtil.formatNow("HHmmss"));		//12	受卡方所在地时间
		unionPayBean.setDateLocalTransaction(DateUtil.formatNow("MMdd"));		//13	受卡方所在地日期
		unionPayBean.setAquiringInstitutionId("10000001");						//32	受理方标识码
		unionPayBean.setRetrievalReferenceNumber(retrievalReferenceNumber);		//37	检参考号
		unionPayBean.setResponseCode(Constant.SUCCESS_POS_RESPONSE);			//39	应答码
																				//42	商户号不变
		unionPayBean.setBatchNo(extendPayBean.getPos().getBatchNo());										//60.2	批次号		
		unionPayBean.setMsgTypeCode("00");										//60.1	交易类型码
		unionPayBean.setNetMngInfoCode(unionPayBean.getNetMngInfoCode());		//60.3	网络管理信息码	
		String switchingData=unionPayBean.getSwitchingData();
		Map<String, Tlv> map=TlvUtils.builderTlvMap(switchingData);
		if("380".equals(unionPayBean.getNetMngInfoCode())){
			String aid=map.get("9F06").getValue();
			unionPayBean.setSwitchingData(iIcParamsService.findByAid(aid));	
		}else if("370".equals(unionPayBean.getNetMngInfoCode())){
			String rid=map.get("9F06").getValue();
			String keyIndex=map.get("9F22").getValue();
			unionPayBean.setSwitchingData(iIcKeyService.findByRid(rid, keyIndex));				//62	终端密钥
		}
	
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
