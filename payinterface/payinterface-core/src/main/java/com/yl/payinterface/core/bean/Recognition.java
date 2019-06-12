package com.yl.payinterface.core.bean;


/**
 * @author Shark
 * @Description {"code":"522001","length":6,"panLength":16,"providerCode":"BOB",
 * "cardType":"CREDIT","cardName":"万事达双币金卡","agencyCode":"64031000","agencyName":"北京银行"}
 * @Date 2017/12/27 15:38
 */
public class Recognition {

    private String code;
    private String length;
    private String panLength;
    /** 银行编号(BOB\ICBC....)*/
    private String providerCode;
    /**卡类型 */
    private String cardType;
    /** 卡名称 */
    private String cardName;
    /** 机构编号 */
    private String agencyCode;
    /** 机构名称 */
    private String agencyName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getPanLength() {
        return panLength;
    }

    public void setPanLength(String panLength) {
        this.panLength = panLength;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    @Override
    public String toString() {
        return "Recognition{" +
                "code='" + code + '\'' +
                ", length='" + length + '\'' +
                ", panLength='" + panLength + '\'' +
                ", providerCode='" + providerCode + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardName='" + cardName + '\'' +
                ", agencyCode='" + agencyCode + '\'' +
                ", agencyName='" + agencyName + '\'' +
                '}';
    }
}
