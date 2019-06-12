package com.yl.payinterface.core.handle.impl.wallet.yyg100001.bean;

import java.util.List;

/**
 * 商户入驻请求bean
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/7/19
 */
public class PP1017ReqBean {

    private ProductInfos productInfos;

    private HeadReq head;
    /**
     * 订单总金额
     */
    private String orderAmount;
    /**
     * 订单备注
     */
    private String remark;
    /**
     * 授权码
     */
    private String authCode;
    /**
     * 支付类型 00-现金 10-微信被扫 11-微信主扫 12-微信公众号支付 20-支付宝被扫 21-支付宝主扫 30-银联被扫 31-银联主扫
     * 99-聚合支付码（被扫）-（APP主页收款码上送此支付方式
     */
    private String payType;
    /**
     * 法个人用户在接入方微信公众号下的唯一Id 若支付方式为12-微信公众号支付则需上送此值。此值为接入方自行调用微信授权登录获取。相关接口文档如下：
     * http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html
     */
    private String openId;
    /**
     * 微信公众号Id
     */
    private String wechatAppId;

    private String cardNo;

    private String backNoticeUrl;


    public PP1017ReqBean(String tranCode, String userId, String orderId) {
        HeadReq head = new HeadReq(tranCode, userId, orderId);
        this.setHead(head);
    }

    public String getBackNoticeUrl() {
        return backNoticeUrl;
    }

    public void setBackNoticeUrl(String backNoticeUrl) {
        this.backNoticeUrl = backNoticeUrl;
    }

    public ProductInfos getProductInfos() {
        return productInfos;
    }

    public void setProductInfos(ProductInfos productInfos) {
        this.productInfos = productInfos;
    }

    public HeadReq getHead() {
        return head;
    }

    public void setHead(HeadReq head) {
        this.head = head;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getWechatAppId() {
        return wechatAppId;
    }

    public void setWechatAppId(String wechatAppId) {
        this.wechatAppId = wechatAppId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public static class ProductInfos {

        public static List<ProductInfo> productInfo;

        public List<ProductInfo> getProductInfo() {
            return productInfo;
        }

        public void setProductInfo(List<ProductInfo> productInfo) {
            this.productInfo = productInfo;
        }

        public static class ProductInfo {
            /**
             * 商品名称
             */
            private String productName;
            /**
             * 商品价格
             */
            private String productPrice;
            /**
             * 商品数量
             */
            private String productNumbers;

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getProductPrice() {
                return productPrice;
            }

            public void setProductPrice(String productPrice) {
                this.productPrice = productPrice;
            }

            public String getProductNumbers() {
                return productNumbers;
            }

            public void setProductNumbers(String productNumbers) {
                this.productNumbers = productNumbers;
            }
        }
    }
}