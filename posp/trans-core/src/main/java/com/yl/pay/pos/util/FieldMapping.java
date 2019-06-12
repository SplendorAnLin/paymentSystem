package com.yl.pay.pos.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Title: ISO8583 银行域与属性名称的对应关系
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class FieldMapping {
	
	public static final Map<Integer, String> fields = new HashMap<Integer, String>();
	
	static {
		fields.put(2, "pan");
		fields.put(3, "processCode");
		fields.put(4, "amount");
		fields.put(11, "systemsTraceAuditNumber");
		fields.put(12, "timeLocalTransaction");
		fields.put(13, "dateLocalTransaction");
		fields.put(14, "dateExpiration");
		fields.put(15, "dateSettlement");
		fields.put(22, "posEntryModeCode");		
		fields.put(23, "cardSequenceNumber");
		fields.put(25, "posConditionCode");
		fields.put(26, "posPinCaptureCode");
		fields.put(28, "transactionFee");
		fields.put(32, "aquiringInstitutionId");
		fields.put(35, "track2");
		fields.put(36, "track3");
		fields.put(37, "retrievalReferenceNumber");
		fields.put(38, "authorizationCode");
		fields.put(39, "responseCode");
		fields.put(41, "cardAcceptorTerminalId");
		fields.put(42, "cardAcceptorId");
		fields.put(44, "additionalResponseData");
		fields.put(47, "noUse47");
		fields.put(48, "additionalData48");
		fields.put(49, "currencyCode");
		fields.put(50, "currencyCodeSettle");
		fields.put(51, "currencyCodeCardholderMap");
		fields.put(52, "pin");
		fields.put(53, "securityControlInfo");		
		fields.put(54, "additionalAmount");	
		fields.put(55, "ICSystemRelated");
		fields.put(56, "noUse56");
		fields.put(59, "detailInqrng"); 
		fields.put(61, "cardholderAuthInfo");
		fields.put(62, "switchingData");
		fields.put(64, "mac");
		
		//以下为有条件使用
		
		fields.put(571, "updateFlag");
		fields.put(572, "softVersion");
		fields.put(573, "paramVersion");
		
		fields.put(601, "msgTypeCode");
		fields.put(602, "batchNo");
		fields.put(603, "netMngInfoCode");
		fields.put(605, "icConditionCode");
		
		
		fields.put(611, "sourceBatchNo");
		fields.put(612, "sourcePosRequestId");
		fields.put(613, "sourceTranDate");	
		
		fields.put(631, "operator");		
		fields.put(632, "customField632");
		fields.put(633, "customField633");
		//以下未确认
		/*
		fields.put(571, "update_logo");
		fields.put(572, "soft_version");
		fields.put(573, "param_version");

		fields.put(591, "trx_source");
		fields.put(592, "custermer_requestid");
		fields.put(593, "custermer_name");

		fields.put(601, "msg_type_code");
		fields.put(602, "transBatchNo");
		fields.put(603, "net_mng_info_code");
		fields.put(604, "termnl_rcv_ablt");
		fields.put(605, "card_cond_code");
		
		fields.put(611, "org_trans_batch_no");
		fields.put(612, "org_sys_trace_audit_num");
		fields.put(613, "org_trans_date_time");
		

		fields.put(631, "Interna_crecardcompa_code");
		fields.put(632, "custom_fields");*/
	}	
}
