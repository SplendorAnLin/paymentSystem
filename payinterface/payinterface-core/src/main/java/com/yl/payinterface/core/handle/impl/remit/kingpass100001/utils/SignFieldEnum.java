package com.yl.payinterface.core.handle.impl.remit.kingpass100001.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ruanxin on 2017/7/10.
 */
public enum SignFieldEnum {
    RPM_BIND_CARD("rpmBindCard", "memberId|orderId|idType|idNo|userName|phone|cardNo|cardType","expireDate|cvn2", "聚合支付-快捷绑卡"),
    RPM_UNBIND_CARD("rpmUnbindCard", "memberId|contractId","", "聚合支付-解绑卡"),
    RPM_BANK_LIST("rpmBankList", "","","聚合支付-查询支持绑卡的银行列表"),
    RPM_BANK_PAYMENT("rpmBankPayment","pageReturnUrl|notifyUrl|merchantName|orderTime|orderId|totalAmount|currency|bankAbbr|cardType|payType|validNum|validUnit|goodsName","memberId|showUrl|goodsId|goodsDesc","B2C/B2B支付"),
    RPM_MEMBER_CARD_LIST("rpmMemberCardList","memberId","","聚合支付-查询用户的绑卡信息"),
    RPM_CARD_INFO("rpmCardInfo","cardNo","", "聚合支付-查询银行卡信息"),
    RPM_QUICK_PAY_SMS("rpmQuickPaySms","contractId|memberId","", "聚合支付-短信下发"),
    RPM_QUICK_PAY("rpmQuickPay","contractId|memberId|orderId|payType|amount|currency|orderTime|clientIP|validUnit|validNum|offlineNotifyUrl","goodsName|goodsDesc","聚合支付-快捷支付(商户自验短信)"),
    RPM_QUICK_PAY_INIT("rpmQuickPayInit","contractId|memberId|orderId|payType|amount|currency|orderTime|clientIP|validUnit|validNum|offlineNotifyUrl","goodsName|goodsDesc","聚合支付-快捷支付预下单"),
    RPM_QUICK_PAY_COMMIT("rpmQuickPayCommit","contractId|memberId|orderId|payType|amount|currency|orderTime|clientIP|validUnit|validNum|offlineNotifyUrl|checkCode","goodsName|goodsDesc","聚合支付-快捷支付确认(我方验证短信)"),
    RPM_PAY_QUERY("rpmPayQuery","orderId","", "聚合支付-支付查询"),
    //todo 和文档上的必填字段不一样
    RPM_REFUND("rpmRefund","merchantId|oriOrderId|orderId|refundAmount","","聚合支付-退款"),
    RPM_REFUND_QUERY("rpmRefundQuery","merchantId|oriOrderId|orderId","", "聚合支付-退款查询"),
    RPM_STATEMENT("rpmStatement","acDate|type","", "聚合支付-对账单下载"),

    SINGLE_PAYMENT("capSingleTransfer","mcSequenceNo|mcTransDateTime|orderNo|amount|cardNo|accName|crdType","accType|lBnkNo|lBnkNam|validPeriod|cvv2|cellPhone|remark|bnkRsv|capUse|remark1|remark2|remark3", "单笔代付到银行卡"),
    SINGLE_COLLECTION("capSingleCollection","mcSequenceNo|mcTransDateTime|orderNo|accType|cardNo|accName|amount|crdType|idInfo|idType","lBnkNo|lBnkNam|validPeriod|cvv2|cellPhone|remark|bnkRsv|capUse|reqReserved1|reqReserved2","单笔代收从银行卡"),
    CAP_ORDER_QUERY("capOrderQuery","mcSequenceNo|mcTransDateTime|orderNo|amount","remark1|remark2", "代收付订单查询"),
    CAP_STATEMENT_QUERY("capStatementQuery","txnDate|checkTyp|txTyp|curPag|pageNum","reqReserved", "代收付对账单查询"),
    CAP_STATEMENT_FILE_DOWN("capStatementFileDown","acDate|type","", "委托收付对账单下载"),
    CAP_BATCH_QUERY("capBatchQuery","batchNo|pageSize|pageNo","ordSts", "代收付批量查询"),

    QRCODE_GENERATE_BY_ORDER("qrCodeGenerateByOrder","mcSequenceNo|mcTransDateTime|orderNo|amount|cardNo|accName|crdType","orderId|amount|goodsName|goodsDesc|offlineNotifyUrl|qrType", "二维码"),
    QRCODE_SPDB_PRE_ORDER("qrcodeSpdbPreOrder","mcSequenceNo|mcTransDateTime|orderNo|amount|cardNo|accName|crdType","orderId|amount|goodsName|goodsDesc|offlineNotifyUrl|qrType", "二维码"),
    BAR_CODE_PAY_MENT("barCodePayMent","amount|scanCodeId|orderId|terminalId|offlineNotifyUrl|channelCd|corpOrg|goodsName|goodsDesc","", "条码"),
    ;
    /**
     * 服务名
     */
    String service;
    /**
     * 私有不可空字段
     */
    String notEmptyFields;
    /**
     * 私有可空字段
     */
    String emptyFields;
    /**
     * 服务描述
     */
    String desc;

    final static String COMMON = "charset|version|service|signType|merchantId|requestTime|requestId|";
    static Map<String, SignFieldEnum> SignFieldMap = new HashMap<>();

    /**
     * 将所有的enum一次性放到map中
     *
     */
    static {
        for (SignFieldEnum signFieldEnum : SignFieldEnum.values()) {
            SignFieldMap.put(signFieldEnum.getService(), signFieldEnum);
        }
    }



    SignFieldEnum(String service, String notEmptyFields, String emptyFields, String desc) {
        this.service = service;
        this.notEmptyFields = notEmptyFields;
        this.emptyFields = emptyFields;
        this.desc = desc;
    }

    public void setEmptyFields(String emptyFields) {
        this.emptyFields = emptyFields;
    }

    public String getEmptyFields() {
        return emptyFields;
    }

    public void setNotEmptyFields(String notEmptyFields) {
        this.notEmptyFields = notEmptyFields;
    }

    public String getNotEmptyFields() {
        return notEmptyFields;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getService() {
        return service;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
