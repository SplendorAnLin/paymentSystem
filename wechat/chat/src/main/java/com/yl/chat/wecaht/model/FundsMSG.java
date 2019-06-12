package com.yl.chat.wecaht.model;

public class FundsMSG extends BaseMSG {
    /**
     * 变动时间
     */
    private String date;
    /**
     * 变动余额
     */
    private String adCharge;
    /**
     * 可用余额
     */
    private String cashBalance;

    public String getDate() {
        return date;
    }

    public String getAdCharge() {
        return adCharge;
    }

    public String getCashBalance() {
        return cashBalance;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAdCharge(String adCharge) {
        this.adCharge = adCharge;
    }

    public void setCashBalance(String cashBalance) {
        this.cashBalance = cashBalance;
    }
}