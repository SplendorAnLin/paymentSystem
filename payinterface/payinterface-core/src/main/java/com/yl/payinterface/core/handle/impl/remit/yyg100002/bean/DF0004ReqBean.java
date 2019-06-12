package com.yl.payinterface.core.handle.impl.remit.yyg100002.bean;

/**
 * 批量代付
 *
 * @author wanglei
 * @create 2017-07-19 上午11:16
 **/
public class DF0004ReqBean {

    private DF0001ReqBean.BatchPayHeadReq head;

    private String tradeDate;
    private String orderId;

    public DF0001ReqBean.BatchPayHeadReq getHead() {
        return head;
    }

    public void setHead(DF0001ReqBean.BatchPayHeadReq head) {
        this.head = head;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DF0004ReqBean{");
        sb.append("head=").append(head);
        sb.append(", tradeDate='").append(tradeDate).append('\'');
        sb.append(", orderId='").append(orderId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
