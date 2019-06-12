package com.yl.payinterface.core.handle.impl.quick.pengrui100101.bean;

import net.sf.json.JSONObject;

/**
 * 全时优服 商户入网Bean
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/20
 */
public class PengRui100101FilingBean {

    /** 请求头 */
    private JSONObject head;

    /** 商户号 */
    private String merchantCode;

    /** 商户名称 */
    private String merchantName;

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

    /** 入账银行名称 */
    private String inBankName;

    /** 入账银行总行联行号 */
    private String inBankUnitNo;

    /** 消费交易手续费扣率 */
    private String t0TxnRate;

    /** 单笔消费交易手续费 */
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

    public String getInBankName() {
        return inBankName;
    }

    public void setInBankName(String inBankName) {
        this.inBankName = inBankName;
    }

    public String getInBankUnitNo() {
        return inBankUnitNo;
    }

    public void setInBankUnitNo(String inBankUnitNo) {
        this.inBankUnitNo = inBankUnitNo;
    }

    public String getT0TxnRate() {
        return t0TxnRate;
    }

    public void setT0TxnRate(String t0TxnRate) {
        this.t0TxnRate = t0TxnRate;
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
        return "PengRui100101FilingBean{" +
                "head=" + head +
                ", merchantCode='" + merchantCode + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", idCard='" + idCard + '\'' +
                ", inBankCardNo='" + inBankCardNo + '\'' +
                ", inBankName='" + inBankName + '\'' +
                ", inBankUnitNo='" + inBankUnitNo + '\'' +
                ", t0TxnRate='" + t0TxnRate + '\'' +
                ", t0TxnFee='" + t0TxnFee + '\'' +
                ", operateType='" + operateType + '\'' +
                ", remark='" + remark + '\'' +
                ", extend1='" + extend1 + '\'' +
                ", extend2='" + extend2 + '\'' +
                ", extend3='" + extend3 + '\'' +
                '}';
    }
}