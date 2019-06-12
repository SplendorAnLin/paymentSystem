package com.yl.pay.pos.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * Title: 银联交易标准参数
 * Description: 此Bean不可修改，添加，业务扩展属性在扩展Bean中设置
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public class UnionPayBean implements Cloneable, Serializable{
	private static final long serialVersionUID = 6737670545891306013L;
	public String 	orgIso8583Msg;								//存放原始报文信息
	
	public String  	mti;										//FLD ISO 	交易类型                                                               ***
	public String	pan;										//FLD 02  	主账号 primaryAccountNumber
	public String	processCode;								//FLD 03  	处理码 processingCode
	public String	amount;										//FLD 04  	交易金额 amountTransaction（分）
	public String 	amountSettlement;							//FLD 05  	清算金额 amountSettlement
	public String	amountCardholderBilling;					//FLD 06  	持卡人扣账金额 amountCardholderBilling
	public String	transmissionDateAndTime;					//FLD 07  	交易传输时间transmissionDateAndTime GMT
	public String	amountCardholderDocument;					//FLD 08  	*持卡人签单费金额amountCardholderDocument
	public String 	conversionRateSettlement;					//FLD 09  	清算汇率conversionRateSettlement	
	public String	conversionRateCardholderBilling;			//FLD 10  	持卡人扣账汇率conversionRateCardholderBilling	
	public String	systemsTraceAuditNumber;					//FLD 11  	系统跟踪号systemsTraceAuditNumber                               ***
	public String	timeLocalTransaction;						//FLD 12  	受卡方所在地时间timeLocalTransaction 	hhmmss                    ***
	public String	dateLocalTransaction;						//FLD 13  	受卡方所在地日期dateLocalTransaction 	mmdd                      ***
	public String	dateExpiration;								//FLD 14  	卡有效期dateExpiration 			yymm
	public String	dateSettlement;								//FLD 15  	清算日期dateSettlement 			mmdd
	public String	dateConversion;								//FLD 16  	兑换日期dateConversion 			mmdd
	public String	dateCapture;								//FLD 17  	*受理日期dateCapture 				mmdd
	public String	merchantType;								//FLD 18  	商户类型merchantType
	public String	aquiringInstitutionCountryCode;				//FLD 19  	受理机构国家代码 Acquiring Institution Country Code
	public String	panExtendCountryCode;						//FLD 20  	*扩展主帐号国家代码Pan Extended,Country Code
	public String	noUse21;									//FLD 21  	*发送机构国家代码
	public String 	posEntryModeCode;							//FLD 22  	服务点输入方式码Point Of Service Entry Mode Code
	public String 	cardSequenceNumber;							//FLD 23  	卡序列号 Card Sequence Number
	public String 	noUse24;									//FLD 24  	*网络国际标识符
	public String 	posConditionCode;							//FLD 25  	服务点条件码 Point Of Service Condition Code
	public String	posPinCaptureCode;							//FLD 26  	服务点PIN获取码 Point Of Service Pin Capture Code
	public String 	noUse27;									//FLD 27  	*授权标识响应长度
	public String 	transactionFee;								//FLD 28  	交易费 Amount，Transaction Fee
	public String 	settlementFee;								//FLD 29  	*清算费 Amount, Settlement Fee
	public String 	noUse30;									//FLD 30  	*交易处理费金额
	public String 	settlementProcessFee;						//FLD 31  	*清算处理费 Amount, Settlement Processing Fee
	public String 	aquiringInstitutionId;						//FLD 32  	代理机构标识码 Acquiring Institution Identification Code         ***
	public String 	forwardInstitutionId;						//FLD 33  	发送机构标识码 Forwarding Institution Identification Code
	public String 	panExtend;									//FLD 34  	*扩展主账号 Pan Extended
	public String	track2;										//FLD 35   	第二磁道数据 Track 2 data
	public String 	track3;										//FLD 36   	第三磁道数据 Track 3 Data track_3_data
	public String	retrievalReferenceNumber;					//FLD 37  	检索参考号 Retrieval Reference Number ***
	public String	authorizationCode;							//FLD 38  	授权标识应答码 Authorization Identification Response
	public String 	responseCode;								//FLD 39  	应答码 Response Code ***
	public String	noUse40;									//FLD 40  	*服务限制代码
	public String	cardAcceptorTerminalId;						//FLD 41  	受卡机终端标识码 Card Acceptor Terminal Identification ***
	public String	cardAcceptorId;								//FLD 42  	受卡方标识码 Card Acceptor Identification Code ***
	public String	cardAcceptorName;							//FLD 43  	受卡方名称地址 Card Acceptor Name/Location
	public String	additionalResponseData;						//FLD 44  	附加响应数据 Additional Response Data
	public String	track1;										//FLD 45   	第一磁道数据 Track 1 data
	public String	noUse46;									//FLD 46  	*附加数据-JSO                                                   
	public String	noUse47;									//FLD 47  	*附加数据-国家
	public String	additionalData48;							//FLD 48   	附加数据——私有 Additional Data public ***
	public String	currencyCode;								//FLD 49  	交易货币代码 Currency Code, Transaction
	public String	currencyCodeSettle;							//FLD 50  	清算货币代码 Currency Code, Settlement(目前用来存放物流的签名图片的信息)
	public String	currencyCodeCardholder;						//FLD 51
	public Map<String,String>	currencyCodeCardholderMap;		//51 tlv
	public String	pin;										//FLD 52  	个人标识码数据 Pin Data 
	public String	securityControlInfo;						//FLD 53   	安全控制信息 Security Related Control Information
	public String	additionalAmount;							//FLD 54  	存放账户余额等信息 Additional Amounts
	public String	ICSystemRelated;							//FLD 55  	*IC卡数据域 Integrated Circuit Card System Related
	public String	noUse56;									//FLD 56  	*保留给ISO 使用
	public String	additionalData57;							//FLD 57  	 附加交易信息 Additional Data public
	public String	icpbocDate;									//FLD 58 	IC(PBOC)卡交易数据
	public String 	detailInqrng;								//FLD 59 	明细查询数据
	public String	reserved;									//FLD 60  	自定义域 ***	
	public String	cardholderAuthInfo;							//FLD 61 	持卡人身份认证信息Cardholder Authentication Information
	public String	document_number;							//FLD 61.1	证件编号
	public String	cvv_verify_results;							//FLD 61.2	CVV校验结果
	public String	pvv_verify_results;							//FLD 61.3	PVV校验结果
	public String	no_card_checksum;							//FLD 61.4	无卡校验值
	public String	arqc_uthentication_resultvalue;				//FLD 61.5	ARQC认证结果值
	public String	Safety_Information_verifyresults;			//FLD 61.6	安全信息校验值
	public String	switchingData;								//FLD 62 	交换中心数据 Switching Data	
	public String	finaclNetData;								//FLD 63 	金融网络数据 Finacial Network Data
	
	//以下域根据条件使用
	
	public String 	updateFlag;									//FLD 57.1	TMS更新标志
	public String 	softVersion ;                          		//FLD 57.2	当前POS软件版本号
	public String 	paramVersion ;                         		//FLD 57.3	当前POS参数版本号
	
	public String 	msgTypeCode;								//FLD 60.1 	签到：消息类型码
	public String 	batchNo;									//FLD 60.2 	签到：批次号
	public String 	netMngInfoCode;								//FLD 60.3 	签到：网络管理信息码
	
	public String   icConditionCode;							//IC条件代码
	
	public String 	billNo;										//FLD 61.3 	票据号BOC	
	public String 	sourceBatchNo;								//FLD 61.1	原始交易POS 流水号
	public String 	sourcePosRequestId;							//FLD 61.2	原始交易POS 流水号
	public String 	sourceTranDate;								//FLD 61.3 	原始交易日期
	public String 	sourceAuthorizationCode;					//FLD 61.4 	原交易授权码	
	public String	operator;		        					//FLD 63.1	签到：操作员	
	public String   customField632;								//FLD 63.2	自定义域，用法1、基站信息
	public String   customField633;								//FLD 63.3	自定义域，用法1、卡片序列号
	public String	netwk_mgmt_info_code;						//FLD 70	网络管理信息码
	public String	originalDataElements;						//FLD 90	原始数据元
	public String	originalMessageType;						//FLD 90.1	原始报文类型
	public String	originalSystemTraceNumber;					//FLD 90.2	原始系统跟踪号
	public String	originalSystemDateAndTime;					//FLD 90.3	原始系统日期时间
	public String	originalAcquirerInstitutionID;				//FLD 90.4	原始受理机构标识码
	public String	originalForwardInstitutionID;				//FLD 90.5	原始发送机构标识码
	public String 	msg_security_code;							//FLD 96	报文安全码
	public String	rcvg_inst_id_code;							//FLD 100	接收机构标识码
	public String   card_out  ;                                   //FLD 102   ic  转出卡号
	public String   card_in   ;                                  //FLD 103   ic  转入卡号
	public String 	filed110;
	public String 	filed111;
	public String 	wildCardRetention;							//外卡保留  112     
	public String	national_sw_resved;							//FLD 121	CUPS保留
	public String  acq_inst_resvd;                              //FLD122
	public String merchant_deduction_rate;                      //FLD122.1
	public String acq_inst_information;                         //FLD122.2  
	
	public String	mac;										//FLD 128	报文鉴别码
	public String 	noUseTemp;									//FLD 00.0	无用的域，供存储临时字段
	
	public String 	callPhoneNo;								//拨号电话号码
	public String 	servicePhoneNo;								//服务电话号码	
	public String   requestIp;									//请求ip
	/**
	 * 对象克隆
	 */
	@Override
	public UnionPayBean clone() {
		UnionPayBean param=null;
		try {
			param = (UnionPayBean)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return param;
	}
		
	public String getOrgIso8583Msg() {
		return orgIso8583Msg;
	}
	public void setOrgIso8583Msg(String orgIso8583Msg) {
		this.orgIso8583Msg = orgIso8583Msg;
	}
	public String getMti() {
		return mti;
	}
	public void setMti(String mti) {
		this.mti = mti;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAmountSettlement() {
		return amountSettlement;
	}
	public void setAmountSettlement(String amountSettlement) {
		this.amountSettlement = amountSettlement;
	}
	public String getAmountCardholderBilling() {
		return amountCardholderBilling;
	}
	public void setAmountCardholderBilling(String amountCardholderBilling) {
		this.amountCardholderBilling = amountCardholderBilling;
	}
	public String getTransmissionDateAndTime() {
		return transmissionDateAndTime;
	}
	public void setTransmissionDateAndTime(String transmissionDateAndTime) {
		this.transmissionDateAndTime = transmissionDateAndTime;
	}
	public String getAmountCardholderDocument() {
		return amountCardholderDocument;
	}
	public void setAmountCardholderDocument(String amountCardholderDocument) {
		this.amountCardholderDocument = amountCardholderDocument;
	}
	public String getConversionRateSettlement() {
		return conversionRateSettlement;
	}
	public void setConversionRateSettlement(String conversionRateSettlement) {
		this.conversionRateSettlement = conversionRateSettlement;
	}
	public String getConversionRateCardholderBilling() {
		return conversionRateCardholderBilling;
	}
	public void setConversionRateCardholderBilling(
			String conversionRateCardholderBilling) {
		this.conversionRateCardholderBilling = conversionRateCardholderBilling;
	}
	public String getSystemsTraceAuditNumber() {
		return systemsTraceAuditNumber;
	}
	public void setSystemsTraceAuditNumber(String systemsTraceAuditNumber) {
		this.systemsTraceAuditNumber = systemsTraceAuditNumber;
	}
	public String getTimeLocalTransaction() {
		return timeLocalTransaction;
	}
	public void setTimeLocalTransaction(String timeLocalTransaction) {
		this.timeLocalTransaction = timeLocalTransaction;
	}
	public String getDateLocalTransaction() {
		return dateLocalTransaction;
	}
	public void setDateLocalTransaction(String dateLocalTransaction) {
		this.dateLocalTransaction = dateLocalTransaction;
	}
	public String getDateExpiration() {
		return dateExpiration;
	}
	public void setDateExpiration(String dateExpiration) {
		this.dateExpiration = dateExpiration;
	}
	public String getDateSettlement() {
		return dateSettlement;
	}
	public void setDateSettlement(String dateSettlement) {
		this.dateSettlement = dateSettlement;
	}
	public String getDateConversion() {
		return dateConversion;
	}
	public void setDateConversion(String dateConversion) {
		this.dateConversion = dateConversion;
	}
	public String getDateCapture() {
		return dateCapture;
	}
	public void setDateCapture(String dateCapture) {
		this.dateCapture = dateCapture;
	}
	public String getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}
	public String getAquiringInstitutionCountryCode() {
		return aquiringInstitutionCountryCode;
	}
	public void setAquiringInstitutionCountryCode(
			String aquiringInstitutionCountryCode) {
		this.aquiringInstitutionCountryCode = aquiringInstitutionCountryCode;
	}
	public String getPanExtendCountryCode() {
		return panExtendCountryCode;
	}
	public void setPanExtendCountryCode(String panExtendCountryCode) {
		this.panExtendCountryCode = panExtendCountryCode;
	}
	public String getNoUse21() {
		return noUse21;
	}
	public void setNoUse21(String noUse21) {
		this.noUse21 = noUse21;
	}
	public String getPosEntryModeCode() {
		return posEntryModeCode;
	}
	public void setPosEntryModeCode(String posEntryModeCode) {
		this.posEntryModeCode = posEntryModeCode;
	}
	public String getCardSequenceNumber() {
		return cardSequenceNumber;
	}
	public void setCardSequenceNumber(String cardSequenceNumber) {
		this.cardSequenceNumber = cardSequenceNumber;
	}
	public String getNoUse24() {
		return noUse24;
	}
	public void setNoUse24(String noUse24) {
		this.noUse24 = noUse24;
	}
	public String getPosConditionCode() {
		return posConditionCode;
	}
	public void setPosConditionCode(String posConditionCode) {
		this.posConditionCode = posConditionCode;
	}
	public String getPosPinCaptureCode() {
		return posPinCaptureCode;
	}
	public void setPosPinCaptureCode(String posPinCaptureCode) {
		this.posPinCaptureCode = posPinCaptureCode;
	}
	public String getNoUse27() {
		return noUse27;
	}
	public void setNoUse27(String noUse27) {
		this.noUse27 = noUse27;
	}
	public String getTransactionFee() {
		return transactionFee;
	}
	public void setTransactionFee(String transactionFee) {
		this.transactionFee = transactionFee;
	}
	public String getSettlementFee() {
		return settlementFee;
	}
	public void setSettlementFee(String settlementFee) {
		this.settlementFee = settlementFee;
	}
	public String getNoUse30() {
		return noUse30;
	}
	public void setNoUse30(String noUse30) {
		this.noUse30 = noUse30;
	}
	public String getSettlementProcessFee() {
		return settlementProcessFee;
	}
	public void setSettlementProcessFee(String settlementProcessFee) {
		this.settlementProcessFee = settlementProcessFee;
	}
	public String getAquiringInstitutionId() {
		return aquiringInstitutionId;
	}
	public void setAquiringInstitutionId(String aquiringInstitutionId) {
		this.aquiringInstitutionId = aquiringInstitutionId;
	}
	public String getForwardInstitutionId() {
		return forwardInstitutionId;
	}
	public void setForwardInstitutionId(String forwardInstitutionId) {
		this.forwardInstitutionId = forwardInstitutionId;
	}
	public String getPanExtend() {
		return panExtend;
	}
	public void setPanExtend(String panExtend) {
		this.panExtend = panExtend;
	}
	public String getTrack2() {
		return track2;
	}
	public void setTrack2(String track2) {
		this.track2 = track2;
	}
	public String getTrack3() {
		return track3;
	}
	public void setTrack3(String track3) {
		this.track3 = track3;
	}
	public String getRetrievalReferenceNumber() {
		return retrievalReferenceNumber;
	}
	public void setRetrievalReferenceNumber(String retrievalReferenceNumber) {
		this.retrievalReferenceNumber = retrievalReferenceNumber;
	}
	public String getAuthorizationCode() {
		return authorizationCode;
	}
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getNoUse40() {
		return noUse40;
	}
	public void setNoUse40(String noUse40) {
		this.noUse40 = noUse40;
	}
	public String getCardAcceptorTerminalId() {
		return cardAcceptorTerminalId;
	}
	public void setCardAcceptorTerminalId(String cardAcceptorTerminalId) {
		this.cardAcceptorTerminalId = cardAcceptorTerminalId;
	}
	public String getCardAcceptorId() {
		return cardAcceptorId;
	}
	public void setCardAcceptorId(String cardAcceptorId) {
		this.cardAcceptorId = cardAcceptorId;
	}
	public String getCardAcceptorName() {
		return cardAcceptorName;
	}
	public void setCardAcceptorName(String cardAcceptorName) {
		this.cardAcceptorName = cardAcceptorName;
	}
	public String getAdditionalResponseData() {
		return additionalResponseData;
	}
	public void setAdditionalResponseData(String additionalResponseData) {
		this.additionalResponseData = additionalResponseData;
	}
	public String getTrack1() {
		return track1;
	}
	public void setTrack1(String track1) {
		this.track1 = track1;
	}
	public String getNoUse46() {
		return noUse46;
	}
	public void setNoUse46(String noUse46) {
		this.noUse46 = noUse46;
	}
	public String getNoUse47() {
		return noUse47;
	}
	public void setNoUse47(String noUse47) {
		this.noUse47 = noUse47;
	}
	public String getAdditionalData48() {
		return additionalData48;
	}
	public void setAdditionalData48(String additionalData48) {
		this.additionalData48 = additionalData48;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCurrencyCodeSettle() {
		return currencyCodeSettle;
	}
	public void setCurrencyCodeSettle(String currencyCodeSettle) {
		this.currencyCodeSettle = currencyCodeSettle;
	}
	public String getCurrencyCodeCardholder() {
		return currencyCodeCardholder;
	}
	public void setCurrencyCodeCardholder(String currencyCodeCardholder) {
		this.currencyCodeCardholder = currencyCodeCardholder;
	}
	
	public Map<String, String> getCurrencyCodeCardholderMap() {
		return currencyCodeCardholderMap;
	}

	public void setCurrencyCodeCardholderMap(
			Map<String, String> currencyCodeCardholderMap) {
		this.currencyCodeCardholderMap = currencyCodeCardholderMap;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getSecurityControlInfo() {
		return securityControlInfo;
	}
	public void setSecurityControlInfo(String securityControlInfo) {
		this.securityControlInfo = securityControlInfo;
	}
	public String getAdditionalAmount() {
		return additionalAmount;
	}
	public void setAdditionalAmount(String additionalAmount) {
		this.additionalAmount = additionalAmount;
	}
	public String getICSystemRelated() {
		return ICSystemRelated;
	}
	public void setICSystemRelated(String iCSystemRelated) {
		ICSystemRelated = iCSystemRelated;
	}
	public String getNoUse56() {
		return noUse56;
	}
	public void setNoUse56(String noUse56) {
		this.noUse56 = noUse56;
	}
	public String getAdditionalData57() {
		return additionalData57;
	}
	public void setAdditionalData57(String additionalData57) {
		this.additionalData57 = additionalData57;
	}
	public String getIcpbocDate() {
		return icpbocDate;
	}
	public void setIcpbocDate(String icpbocDate) {
		this.icpbocDate = icpbocDate;
	}
	public String getDetailInqrng() {
		return detailInqrng;
	}
	public void setDetailInqrng(String detailInqrng) {
		this.detailInqrng = detailInqrng;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	public String getCardholderAuthInfo() {
		return cardholderAuthInfo;
	}
	public void setCardholderAuthInfo(String cardholderAuthInfo) {
		this.cardholderAuthInfo = cardholderAuthInfo;
	}
	public String getSwitchingData() {
		return switchingData;
	}
	public void setSwitchingData(String switchingData) {
		this.switchingData = switchingData;
	}
	public String getFinaclNetData() {
		return finaclNetData;
	}
	public void setFinaclNetData(String finaclNetData) {
		this.finaclNetData = finaclNetData;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getSourcePosRequestId() {
		return sourcePosRequestId;
	}
	public void setSourcePosRequestId(String sourcePosRequestId) {
		this.sourcePosRequestId = sourcePosRequestId;
	}
	public String getSourceTranDate() {
		return sourceTranDate;
	}
	public void setSourceTranDate(String sourceTranDate) {
		this.sourceTranDate = sourceTranDate;
	}
	public String getSourceAuthorizationCode() {
		return sourceAuthorizationCode;
	}
	public void setSourceAuthorizationCode(String sourceAuthorizationCode) {
		this.sourceAuthorizationCode = sourceAuthorizationCode;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getMsgTypeCode() {
		return msgTypeCode;
	}
	public void setMsgTypeCode(String msgTypeCode) {
		this.msgTypeCode = msgTypeCode;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getNetMngInfoCode() {
		return netMngInfoCode;
	}
	public void setNetMngInfoCode(String netMngInfoCode) {
		this.netMngInfoCode = netMngInfoCode;
	}
	public String getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}
	public String getSoftVersion() {
		return softVersion;
	}
	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}
	public String getParamVersion() {
		return paramVersion;
	}
	public void setParamVersion(String paramVersion) {
		this.paramVersion = paramVersion;
	}	
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getNoUseTemp() {
		return noUseTemp;
	}
	public void setNoUseTemp(String noUseTemp) {
		this.noUseTemp = noUseTemp;
	}
	public String getSourceBatchNo() {
		return sourceBatchNo;
	}
	public void setSourceBatchNo(String sourceBatchNo) {
		this.sourceBatchNo = sourceBatchNo;
	}
	public String getCallPhoneNo() {
		return callPhoneNo;
	}
	public void setCallPhoneNo(String callPhoneNo) {
		this.callPhoneNo = callPhoneNo;
	}
	public String getServicePhoneNo() {
		return servicePhoneNo;
	}
	public void setServicePhoneNo(String servicePhoneNo) {
		this.servicePhoneNo = servicePhoneNo;
	}

	public String getNetwk_mgmt_info_code() {
		return netwk_mgmt_info_code;
	}

	public void setNetwk_mgmt_info_code(String netwkMgmtInfoCode) {
		netwk_mgmt_info_code = netwkMgmtInfoCode;
	}

	public String getRcvg_inst_id_code() {
		return rcvg_inst_id_code;
	}

	public void setRcvg_inst_id_code(String rcvgInstIdCode) {
		rcvg_inst_id_code = rcvgInstIdCode;
	}

	public String getNational_sw_resved() {
		return national_sw_resved;
	}

	public void setNational_sw_resved(String nationalSwResved) {
		national_sw_resved = nationalSwResved;
	}

	public String getMsg_security_code() {
		return msg_security_code;
	}

	public void setMsg_security_code(String msgSecurityCode) {
		msg_security_code = msgSecurityCode;
	}

	public String getOriginalDataElements() {
		return originalDataElements;
	}

	public void setOriginalDataElements(String originalDataElements) {
		this.originalDataElements = originalDataElements;
	}

	public String getOriginalMessageType() {
		return originalMessageType;
	}

	public void setOriginalMessageType(String originalMessageType) {
		this.originalMessageType = originalMessageType;
	}

	public String getOriginalSystemTraceNumber() {
		return originalSystemTraceNumber;
	}

	public void setOriginalSystemTraceNumber(String originalSystemTraceNumber) {
		this.originalSystemTraceNumber = originalSystemTraceNumber;
	}

	public String getOriginalSystemDateAndTime() {
		return originalSystemDateAndTime;
	}

	public void setOriginalSystemDateAndTime(String originalSystemDateAndTime) {
		this.originalSystemDateAndTime = originalSystemDateAndTime;
	}

	public String getOriginalAcquirerInstitutionID() {
		return originalAcquirerInstitutionID;
	}

	public void setOriginalAcquirerInstitutionID(String originalAcquirerInstitutionID) {
		this.originalAcquirerInstitutionID = originalAcquirerInstitutionID;
	}

	public String getOriginalForwardInstitutionID() {
		return originalForwardInstitutionID;
	}

	public void setOriginalForwardInstitutionID(String originalForwardInstitutionID) {
		this.originalForwardInstitutionID = originalForwardInstitutionID;
	}

	public String getDocument_number() {
		return document_number;
	}

	public void setDocument_number(String documentNumber) {
		document_number = documentNumber;
	}

	public String getCvv_verify_results() {
		return cvv_verify_results;
	}

	public void setCvv_verify_results(String cvvVerifyResults) {
		cvv_verify_results = cvvVerifyResults;
	}

	public String getPvv_verify_results() {
		return pvv_verify_results;
	}

	public void setPvv_verify_results(String pvvVerifyResults) {
		pvv_verify_results = pvvVerifyResults;
	}

	public String getNo_card_checksum() {
		return no_card_checksum;
	}

	public void setNo_card_checksum(String noCardChecksum) {
		no_card_checksum = noCardChecksum;
	}

	public String getArqc_uthentication_resultvalue() {
		return arqc_uthentication_resultvalue;
	}

	public void setArqc_uthentication_resultvalue(String arqcUthenticationResultvalue) {
		arqc_uthentication_resultvalue = arqcUthenticationResultvalue;
	}

	public String getSafety_Information_verifyresults() {
		return Safety_Information_verifyresults;
	}

	public void setSafety_Information_verifyresults(String safetyInformationVerifyresults) {
		Safety_Information_verifyresults = safetyInformationVerifyresults;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getCard_out() {
		return card_out;
	}

	public void setCard_out(String card_out) {
		this.card_out = card_out;
	}

	public String getCard_in() {
		return card_in;
	}

	public void setCard_in(String card_in) {
		this.card_in = card_in;
	}

	public String getIcConditionCode() {
		return icConditionCode;
	}

	public void setIcConditionCode(String icConditionCode) {
		this.icConditionCode = icConditionCode;
	}

	public String getCustomField632() {
		return customField632;
	}

	public void setCustomField632(String customField632) {
		this.customField632 = customField632;
	}

	public String getWildCardRetention() {
		return wildCardRetention;
	}

	public void setWildCardRetention(String wildCardRetention) {
		this.wildCardRetention = wildCardRetention;
	}

	public String getFiled110() {
		return filed110;
	}

	public void setFiled110(String filed110) {
		this.filed110 = filed110;
	}

	public String getFiled111() {
		return filed111;
	}

	public void setFiled111(String filed111) {
		this.filed111 = filed111;
	}



	public String getAcq_inst_resvd() {
		return acq_inst_resvd;
	}

	public void setAcq_inst_resvd(String acq_inst_resvd) {
		this.acq_inst_resvd = acq_inst_resvd;
	}

	public String getMerchant_deduction_rate() {
		return merchant_deduction_rate;
	}

	public void setMerchant_deduction_rate(String merchant_deduction_rate) {
		this.merchant_deduction_rate = merchant_deduction_rate;
	}

	public String getAcq_inst_information() {
		return acq_inst_information;
	}

	public void setAcq_inst_information(String acq_inst_information) {
		this.acq_inst_information = acq_inst_information;
	}
	

	public String getCustomField633() {
		return customField633;
	}

	public void setCustomField633(String customField633) {
		this.customField633 = customField633;
	}


}
