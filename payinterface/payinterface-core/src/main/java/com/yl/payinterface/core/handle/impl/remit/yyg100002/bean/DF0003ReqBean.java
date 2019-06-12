package com.yl.payinterface.core.handle.impl.remit.yyg100002.bean;

/**
 * 批量代付
 *
 * @author wanglei
 * @create 2017-07-19 上午11:16
 **/
public class DF0003ReqBean {

    private DF0001ReqBean.BatchPayHeadReq head;

    /**
     * 持卡人姓名
     */
    private String cardByName;
    /**
     * 持卡卡号
     */
    private String cardByNo;
    /**
     * 证件类型
     */
    private String idType;
    /**
     * 证件号码
     */
    private String idNumber;
    /**
     * 交易时间
     */
    private String tradeTime;
    /**
     * 银行预留手机号
     */
    private String bankMobile;
    /**
     * 商户订单号
     */
    private String orderId;
    /**
     * 代付金额
     */
    private String amount;
    /**
     * 对公标示
     */
    private String accType;
    /**
     * 银行编码
     */
    private String bankCode;

    private String bankCity;

    private String bankOpenName;

    private String bankProvcince;


    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    public String getBankOpenName() {
        return bankOpenName;
    }

    public void setBankOpenName(String bankOpenName) {
        this.bankOpenName = bankOpenName;
    }

    public String getBankProvcince() {
        return bankProvcince;
    }

    public void setBankProvcince(String bankProvcince) {
        this.bankProvcince = bankProvcince;
    }

    public DF0001ReqBean.BatchPayHeadReq getHead() {
        return head;
    }

    public void setHead(DF0001ReqBean.BatchPayHeadReq head) {
        this.head = head;
    }

    public String getCardByName() {
        return cardByName;
    }

    public void setCardByName(String cardByName) {
        this.cardByName = cardByName;
    }

    public String getCardByNo() {
        return cardByNo;
    }

    public void setCardByNo(String cardByNo) {
        this.cardByNo = cardByNo;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getBankMobile() {
        return bankMobile;
    }

    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DF0003ReqBean{");
        sb.append("head=").append(head);
        sb.append(", cardByName='").append(cardByName).append('\'');
        sb.append(", cardByNo='").append(cardByNo).append('\'');
        sb.append(", idType='").append(idType).append('\'');
        sb.append(", idNumber='").append(idNumber).append('\'');
        sb.append(", tradeTime='").append(tradeTime).append('\'');
        sb.append(", bankMobile='").append(bankMobile).append('\'');
        sb.append(", orderId='").append(orderId).append('\'');
        sb.append(", amount='").append(amount).append('\'');
        sb.append(", accType='").append(accType).append('\'');
        sb.append(", bankCode='").append(bankCode).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
