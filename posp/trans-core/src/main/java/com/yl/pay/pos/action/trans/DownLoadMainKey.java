package com.yl.pay.pos.action.trans;

import com.pay.common.util.DateUtil;
import com.pay.common.util.ISO8583.ISO8583Utile;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.util.BCDHelper;
import com.yl.pay.pos.util.CodeBuilder;
import com.yl.pay.pos.util.EsscUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.Map;

/**
 * Title: 交易处理  - 下载主密钥
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class DownLoadMainKey extends BaseTransaction implements ITransaction {
	
	private static final Log log = LogFactory.getLog(DownLoadMainKey.class);
	private static final String KEY = "11111111111111111111111111111111";


	public ExtendPayBean execute(ExtendPayBean extendPayBean) throws Exception{
		log.info("####### DownLoadMain #######");
		UnionPayBean unionPayBean=extendPayBean.getUnionPayBean();		
//		SecretKey secretKey=secretKeyService.findByKey("pos."+unionPayBean.getCardAcceptorTerminalId()+".zmk");

		Map<String,String> field51Map = unionPayBean.getCurrencyCodeCardholderMap();
		String mcode = field51Map.get("A1"); //下载主密钥为厂商编号
		String pcode = field51Map.get("A2");//下载主密钥为机型编号
		String sn = field51Map.get("A3");//下载主密钥为SN
		Date date = new Date();
		String time = DateUtil.formatDate(date,"HHmm");
		String md = DateUtil.formatNow("MMdd");
		String year = DateUtil.formatNow("yyyy");
		String manufatureInfo = repairZero(mcode,8) + repairZero(pcode,4) + repairZero(sn,20);

		log.info("manufatureInfo:"+manufatureInfo);
		String tempKey = repairZero(mcode,8) + repairZero(pcode,4) + repairZero(year+md+time,20);
		log.info("tempKey:"+tempKey);
		byte[] installedKey = BCDHelper.stringToBcd(manufatureInfo, 32);
		for (int i = 0; i < 16; i++) {
			installedKey[i] ^= (byte) 0x11;
		}

		byte[] tlk = BCDHelper.stringToBcd(tempKey, 32);
		for (int i = 0; i < 16; i++) {
			tlk[i] ^= installedKey[i];
		}
		String secertKey = BCDHelper.bcdToString(tlk, 0, tlk.length);

		//生成工作密钥
		String key=EsscUtils.generateMainSecretPos(unionPayBean.getCardAcceptorTerminalId(),secertKey);
		String retrievalReferenceNumber = CodeBuilder.getPosSequnce();

		//检查Tms
		boolean needUpdate = false;
		String paramVersion = unionPayBean.getParamVersion();

		//设置返回报文		
		unionPayBean.setTimeLocalTransaction(DateUtil.formatDate(date,"HHmmss"));		//12	受卡方所在地时间
		unionPayBean.setDateLocalTransaction(md);		//13	受卡方所在地日期
		unionPayBean.setAquiringInstitutionId("10000001");						//32	受理方标识码
		unionPayBean.setRetrievalReferenceNumber(retrievalReferenceNumber);		//37	检参考号
		unionPayBean.setResponseCode(Constant.SUCCESS_POS_RESPONSE);			//39	应答码

		unionPayBean.setBatchNo("000000");								//60.2	批次号
		unionPayBean.setMsgTypeCode("01");										//60.1	交易类型码
		unionPayBean.setNetMngInfoCode("003");									//60.3	网络管理信息码
		unionPayBean.setSwitchingData(key);				//62	终端密钥
		
		return extendPayBean;
	}



	private String repairZero(String num,int len){
		StringBuffer sb = new StringBuffer(len);
		sb.append(num);
		for (int i = 0; i < len - num.length(); i++){
			sb.append("0");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String manufatureInfo = "000015000400P100AA00000G92345870";

		log.info("manufatureInfo:"+manufatureInfo);
		String tempKey = "00001500040020171123104500000000";
		log.info("tempKey:"+tempKey);
		byte[] installedKey = BCDHelper.stringToBcd(manufatureInfo, 32);
		for (int i = 0; i < 16; i++) {
			installedKey[i] ^= (byte) 0x11;
		}

		byte[] tlk = BCDHelper.stringToBcd(tempKey, 32);
		for (int i = 0; i < 16; i++) {
			tlk[i] ^= installedKey[i];
		}
		String secertKey = BCDHelper.bcdToString(tlk, 0, tlk.length);

		System.out.println(secertKey);
	}

}
