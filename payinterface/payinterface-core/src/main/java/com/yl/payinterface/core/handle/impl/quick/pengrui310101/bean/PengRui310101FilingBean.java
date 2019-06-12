package com.yl.payinterface.core.handle.impl.quick.pengrui310101.bean;

import net.sf.json.JSONObject;

/**
 * 全时优服入网报备Bean
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/6
 */
public class PengRui310101FilingBean {

    /** 请求头 */
    private JSONObject head;

    /** 商户编号 */
    private String merchantCode;

    /** 商户名称 */
    private String merchantName;

    /** 商户简称 */
    private String shortName;

    /** 联系电话 */
    private String tel;

    /** 商户所在省份 */
    private String province;

    /** 商户所在城市 */
    private String city;

    /** 商户所在详细地址 */
    private String address;

    /** 持卡人姓名 */
    private String cardHolderName;

    /** 手机号 */
    private String mobile;

    /** 身份证号 */
    private String idCard;

    /** 入账银行卡号 */
    private String inBankCardNo;

    /** 到帐模式 */
    private String arriveMode;

    /** 交易手续费扣率 */
    private String txnRate;

    /** T0单笔消费交易手续费 */
    private String t0TxnFee;

    /** 操作类型 */
    private String operateType;

    /** 备注说明 */
    private String remark;

    /** 备用字段1 */
    private String extend1;

    /** 备用字段2 */
    private String extend2;

    /** 备用字段3 */
    private String extend3;

    public JSONObject getHead() {
        return head;
    }

    public void setHead(JSONObject head) {
        this.head = head;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getInBankCardNo() {
        return inBankCardNo;
    }

    public void setInBankCardNo(String inBankCardNo) {
        this.inBankCardNo = inBankCardNo;
    }

    public String getArriveMode() {
        return arriveMode;
    }

    public void setArriveMode(String arriveMode) {
        this.arriveMode = arriveMode;
    }

    public String getTxnRate() {
        return txnRate;
    }

    public void setTxnRate(String txnRate) {
        this.txnRate = txnRate;
    }

    public String getT0TxnFee() {
        return t0TxnFee;
    }

    public void setT0TxnFee(String t0TxnFee) {
        this.t0TxnFee = t0TxnFee;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3;
    }

    @Override
    public String toString() {
        return "PengRui310101FilingBean{" +
                "merchantCode='" + merchantCode + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", tel='" + tel + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", idCard='" + idCard + '\'' +
                ", inBankCardNo='" + inBankCardNo + '\'' +
                ", arriveMode='" + arriveMode + '\'' +
                ", txnRate='" + txnRate + '\'' +
                ", t0TxnFee='" + t0TxnFee + '\'' +
                ", operateType='" + operateType + '\'' +
                ", remark='" + remark + '\'' +
                ", extend1='" + extend1 + '\'' +
                ", extend2='" + extend2 + '\'' +
                ", extend3='" + extend3 + '\'' +
                '}';
    }
}