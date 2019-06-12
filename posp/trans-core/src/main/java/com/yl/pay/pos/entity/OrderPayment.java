package com.yl.pay.pos.entity;

import com.yl.pay.pos.bean.ExtendPayBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "POS_ORDER_PAYMENT")
public class OrderPayment extends BaseEntity{
	 /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5700105912184836498L;
    
    private String orderOptimistic;
    
    /**
     * order表交易金额
     */
    private String amount;
    
    /**
     * payment表交易金额
     */
    private String payAmount;
    
    /**
     * 授权码
     */
    private String authorizationCode;
    
    /**
     * 银行批次号
     */
    private String bankBatch;
    
    /**
     * 银行通道
     */
    private String bankChannel;
    
    /**
     * 银行通道费率
     */
    private String bankChannelRate;
    
    /**
     * bankCost
     */
    private String bankCost;
    
    /**
     * 银行商户级别
     */
    private String bankCustomerLevel;
    
    /**
     * 银行商户号
     */
    private String bankCustomerNo;
    
    /**
     * 银行交易日期
     */
    private String bankDate;
    
    /**
     * 银行流水号(37域)
     */
    private String bankExternalId;
    
    /**
     * 收单接口银行
     */
    private String bankInterface;
    
    /**
     * 银行接口
     */
    private String payBankInterface;
    
    /**
     * 银行终端号
     */
    private String bankPosCati;
    
    /**
     * 银行请求号(11域)
     */
    private String bankRequestId;
    
    /**
     * 银行交易时间
     */
    private String bankTime;
    
    /**
     * 卡类型
     */
    private String cardType;
    
    /**
     * 卡标识号
     */
    private String cardVerifyCode;
    
    /**
     * 订单完成时间
     */
    private Date completeTime;
    
    /**
     * 交易完成时间
     */
    private Date payCompleteTime;
    
    /**
     * 订单创建时间
     */
    private Date createTime;
    
    /**
     * 交易创建时间
     */
    private Date payCreateTime;
    
    /**
     * 入账状态
     */
    private String creditStatus;
    
    /**
     * 入账时间
     */
    private Date creditTime;
    
    /**
     * 币种
     */
    private String currencyType;
    
    /**
     * 商户手续费
     */
    private String customerFee;
    
    /**
     * POS商户号
     */
    private String customerNo;
    
    /**
     * 商家订单号
     */
    private String customerOrderCode;
    
    /**
     * 商户费率
     */
    private String customerRate;
    
    /**
     * 订单描述信息
     */
    private String description;
    
    /**
     * 系统流水号,参考号
     */
    private String externalId;
    
    /**
     * 最后仪表交易ID
     */
    private String finalPaymentId;
    
    /**
     * 交易系统ID
     */
    private String payId;
    
    /**
     * 发卡银行
     */
    private String issuer;
    
    /**
     * 交易版本号
     */
    private String payOptimistic;
    
    /**
     * 交易系统订单ID
     */
    private String orderId;
    
    /**
     * 卡号
     */
    private String pan;
    
    /**
     * 交易费用ID
     */
    private String paymentfeeId;
    
    /**
     * POS批次号
     */
    private String posBatch;
    
    /**
     * 交易批次号
     */
    private String payPosBatch;
    
    /**
     * POS终端号
     */
    private String posCati;
    
    /**
     * POS流水号
     */
    private String posRequestId;
    
    /**
     * POS流水号
     */
    private String payPosRequestId;
    
    /**
     * 结算时间
     */
    private Date settleTime;
    
    /**
     * 网点编号
     */
    private String shopId;
    
    /**
     * 交易系统原payment
     */
    private String sourcePaymentId;
    
    /**
     * 订单状态
     */
    private String status;
    
    /**
     * 交易状态
     */
    private String payStatus;
    
    /**
     * 交易类型
     */
    private String transType;
    
    /**
     * 交易类型
     */
    private String payTransType;
    
    private String tags;
    
    private String payAuthorizationCode;
    
	public OrderPayment() {
		super();
	}
	public OrderPayment(ExtendPayBean extendPayBean) {
		super();
		if(extendPayBean.getOrder()!=null){
			this.orderId = extendPayBean.getOrder().getId().toString();
			this.orderOptimistic=extendPayBean.getOrder().getOptimistic().toString();
			if(extendPayBean.getOrder().getAmount()!=null)this.amount=String.valueOf(extendPayBean.getOrder().getAmount());
			if(extendPayBean.getOrder().getBankInterface()!=null)this.bankInterface=extendPayBean.getOrder().getBankInterface().getCode();
			if(extendPayBean.getOrder().getCardType()!=null)this.cardType =extendPayBean.getOrder().getCardType().toString();
			this.createTime = extendPayBean.getOrder().getCreateTime();
			this.completeTime =extendPayBean.getOrder().getCompleteTime();
			this.creditStatus =extendPayBean.getOrder().getCreditStatus()==null?"N":extendPayBean.getOrder().getCreditStatus().toString();
			this.creditTime =extendPayBean.getOrder().getCreditTime();
			this.customerNo =extendPayBean.getOrder().getCustomerNo();
			this.customerOrderCode =extendPayBean.getOrder().getCustomerOrderCode();
			this.description =extendPayBean.getOrder().getDescription();
			this.posBatch = extendPayBean.getOrder().getPosBatch();
			this.posCati = extendPayBean.getOrder().getPosCati();
			this.posRequestId =extendPayBean.getOrder().getPosRequestId();
			this.settleTime = extendPayBean.getOrder().getSettleTime();
			this.externalId = extendPayBean.getOrder().getExternalId();
			if(extendPayBean.getOrder().getFinalPaymentId()!=null)this.finalPaymentId = extendPayBean.getOrder().getFinalPaymentId().toString();
			this.pan =extendPayBean.getOrder().getPan();
			if(extendPayBean.getOrder().getStatus()!=null)this.status=extendPayBean.getOrder().getStatus().toString();
			if(extendPayBean.getOrder().getTransType()!=null)this.transType =extendPayBean.getOrder().getTransType().toString();
			this.authorizationCode = extendPayBean.getOrder().getAuthorizationCode();
			if(extendPayBean.getShop()!=null)this.shopId=extendPayBean.getShop().getShopNo();
		}
		if(extendPayBean.getPayment()!=null){
			if(extendPayBean.getPayment().getAmount()!=null)this.payAmount = String.valueOf(extendPayBean.getPayment().getAmount());
			this.bankBatch = extendPayBean.getPayment().getBankBatch();
			if(extendPayBean.getPayment().getIssuer()!=null)this.issuer = extendPayBean.getPayment().getIssuer().getCode();
			if(extendPayBean.getPayment().getBankCost()!=null)this.bankCost =String.valueOf(extendPayBean.getPayment().getBankCost());
			if(extendPayBean.getPayment().getBankChannel()!=null)this.bankChannel =extendPayBean.getPayment().getBankChannel().getCode();
			if(extendPayBean.getPayment().getBankInterface()!=null)this.payBankInterface =extendPayBean.getPayment().getBankInterface().getCode();
			if(extendPayBean.getPayment().getPaymentFee()!=null)this.paymentfeeId =extendPayBean.getPayment().getPaymentFee().getId().toString();
			if(extendPayBean.getPayment().getSourcePayment()!=null)this.sourcePaymentId = extendPayBean.getPayment().getSourcePayment().getId().toString();
			this.bankCustomerNo =extendPayBean.getPayment().getBankCustomerNo();
			this.bankDate = extendPayBean.getPayment().getBankDate();
			this.bankExternalId =  extendPayBean.getPayment().getBankExternalId();
			this.bankPosCati =  extendPayBean.getPayment().getBankPosCati();
			this.bankRequestId = extendPayBean.getPayment().getBankRequestId();
			this.bankTime = extendPayBean.getPayment().getBankTime();
			this.payCompleteTime = extendPayBean.getPayment().getCompleteTime();
			this.payCreateTime = extendPayBean.getPayment().getCreateTime();
			if(extendPayBean.getPayment().getCurrencyType()!=null)this.currencyType = extendPayBean.getPayment().getCurrencyType().toString();
			if(extendPayBean.getPayment().getCustomerFee()!=null)this.customerFee = extendPayBean.getPayment().getCustomerFee().toString();
			this.payId = extendPayBean.getPayment().getId().toString();
			this.payOptimistic = extendPayBean.getPayment().getOptimistic().toString();
			this.payPosBatch = extendPayBean.getPayment().getPosBatch();
			this.payPosRequestId = extendPayBean.getPayment().getPosRequestId();
			if(extendPayBean.getPayment().getStatus()!=null)this.payStatus = extendPayBean.getPayment().getStatus().toString();
			if(extendPayBean.getPayment().getTransType()!=null)this.payTransType = extendPayBean.getPayment().getTransType().toString();
			this.payAuthorizationCode=extendPayBean.getPayment().getAuthorizationCode();
		}
		if(extendPayBean.getCardInfo()!=null){
		this.cardVerifyCode =extendPayBean.getCardInfo().getCardVerifyCode();
		}
	}

	/**
     * 获取 amount
     * @return 返回 amount
     */
    @Column(name = "AMOUNT")
    public String getAmount()
    {
        return amount;
    }

    /**
     * 设置 amount
     * @param amount
     */
    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    /**
     * 获取 payAmount
     * @return 返回 payAmount
     */
    @Column(name = "PAYAMOUNT")
    public String getPayAmount()
    {
        return payAmount;
    }

    /**
     * 设置 payAmount
     * @param payAmount
     */
    public void setPayAmount(String payAmount)
    {
        this.payAmount = payAmount;
    }

    /**
     * 获取 authorizationCode
     * @return 返回 authorizationCode
     */
    @Column(name = "AUTHORIZATIONCODE")
    public String getAuthorizationCode()
    {
        return authorizationCode;
    }

    /**
     * 设置 authorizationCode
     * @param authorizationCode
     */
    public void setAuthorizationCode(String authorizationCode)
    {
        this.authorizationCode = authorizationCode;
    }

    /**
     * 获取 bankBatch
     * @return 返回 bankBatch
     */
    @Column(name = "BANKBATCH")
    public String getBankBatch()
    {
        return bankBatch;
    }

    /**
     * 设置 bankBatch
     * @param bankBatch
     */
    public void setBankBatch(String bankBatch)
    {
        this.bankBatch = bankBatch;
    }

    /**
     * 获取 bankChannel
     * @return 返回 bankChannel
     */
    @Column(name = "BANKCHANNEL")
    public String getBankChannel()
    {
        return bankChannel;
    }

    /**
     * 设置 bankChannel
     * @param bankChannel
     */
    public void setBankChannel(String bankChannel)
    {
        this.bankChannel = bankChannel;
    }

    /**
     * 获取 bankChannelRate
     * @return 返回 bankChannelRate
     */
    @Column(name = "BANKCHANNELRATE")
    public String getBankChannelRate()
    {
        return bankChannelRate;
    }

    /**
     * 设置 bankChannelRate
     * @param bankChannelRate
     */
    public void setBankChannelRate(String bankChannelRate)
    {
        this.bankChannelRate = bankChannelRate;
    }

    /**
     * 获取 bankCost
     * @return 返回 bankCost
     */
    @Column(name = "BANKCOST")
    public String getBankCost()
    {
        return bankCost;
    }

    /**
     * 设置 bankCost
     * @param bankCost
     */
    public void setBankCost(String bankCost)
    {
        this.bankCost = bankCost;
    }

    /**
     * 获取 bankCustomerLevel
     * @return 返回 bankCustomerLevel
     */
    @Column(name = "BANKCUSTOMERLEVEL")
    public String getBankCustomerLevel()
    {
        return bankCustomerLevel;
    }

    /**
     * 设置 bankCustomerLevel
     * @param bankCustomerLevel
     */
    public void setBankCustomerLevel(String bankCustomerLevel)
    {
        this.bankCustomerLevel = bankCustomerLevel;
    }

    /**
     * 获取 bankCustomerNo
     * @return 返回 bankCustomerNo
     */
    @Column(name = "BANKCUSTOMERNO")
    public String getBankCustomerNo()
    {
        return bankCustomerNo;
    }

    /**
     * 设置 bankCustomerNo
     * @param bankCustomerNo
     */
    public void setBankCustomerNo(String bankCustomerNo)
    {
        this.bankCustomerNo = bankCustomerNo;
    }

    /**
     * 获取 bankDate
     * @return 返回 bankDate
     */
    @Column(name = "BANKDATE")
    public String getBankDate()
    {
        return bankDate;
    }

    /**
     * 设置 bankDate
     * @param bankDate
     */
    public void setBankDate(String bankDate)
    {
        this.bankDate = bankDate;
    }

    /**
     * 获取 bankExternalId
     * @return 返回 bankExternalId
     */
    @Column(name = "BANKEXTERNALID")
    public String getBankExternalId()
    {
        return bankExternalId;
    }

    /**
     * 设置 bankExternalId
     * @param bankExternalId
     */
    public void setBankExternalId(String bankExternalId)
    {
        this.bankExternalId = bankExternalId;
    }

    /**
     * 获取 bankInterface
     * @return 返回 bankInterface
     */
    @Column(name = "BANKINTERFACE")
    public String getBankInterface()
    {
        return bankInterface;
    }

    /**
     * 设置 bankInterface
     * @param bankInterface
     */
    public void setBankInterface(String bankInterface)
    {
        this.bankInterface = bankInterface;
    }

    /**
     * 获取 payBankInterface
     * @return 返回 payBankInterface
     */
    @Column(name = "PAYBANKINTERFACE")
    public String getPayBankInterface()
    {
        return payBankInterface;
    }

    /**
     * 设置 payBankInterface
     * @param payBankInterface
     */
    public void setPayBankInterface(String payBankInterface)
    {
        this.payBankInterface = payBankInterface;
    }

    /**
     * 获取 bankPosCati
     * @return 返回 bankPosCati
     */
    @Column(name = "BANKPOSCATI")
    public String getBankPosCati()
    {
        return bankPosCati;
    }

    /**
     * 设置 bankPosCati
     * @param bankPosCati
     */
    public void setBankPosCati(String bankPosCati)
    {
        this.bankPosCati = bankPosCati;
    }

    /**
     * 获取 bankRequestId
     * @return 返回 bankRequestId
     */
    @Column(name = "BANKREQUESTID")
    public String getBankRequestId()
    {
        return bankRequestId;
    }

    /**
     * 设置 bankRequestId
     * @param bankRequestId
     */
    public void setBankRequestId(String bankRequestId)
    {
        this.bankRequestId = bankRequestId;
    }

    /**
     * 获取 bankTime
     * @return 返回 bankTime
     */
    @Column(name = "BANKTIME")
    public String getBankTime()
    {
        return bankTime;
    }

    /**
     * 设置 bankTime
     * @param bankTime
     */
    public void setBankTime(String bankTime)
    {
        this.bankTime = bankTime;
    }

    /**
     * 获取 cardType
     * @return 返回 cardType
     */
    @Column(name = "CARDTYPE")
    public String getCardType()
    {
        return cardType;
    }

    /**
     * 设置 cardType
     * @param cardType
     */
    public void setCardType(String cardType)
    {
        this.cardType = cardType;
    }

    /**
     * 获取 cardVerifyCode
     * @return 返回 cardVerifyCode
     */
    @Column(name = "CARDVERIFYCODE")
    public String getCardVerifyCode()
    {
        return cardVerifyCode;
    }

    /**
     * 设置 cardVerifyCode
     * @param cardVerifyCode
     */
    public void setCardVerifyCode(String cardVerifyCode)
    {
        this.cardVerifyCode = cardVerifyCode;
    }

    /**
     * 获取 completeTime
     * @return 返回 completeTime
     */
    @Column(name = "COMPLETETIME")
    public Date getCompleteTime()
    {
        return completeTime;
    }

    /**
     * 设置 completeTime
     * @param completeTime
     */
    public void setCompleteTime(Date completeTime)
    {
        this.completeTime = completeTime;
    }

    /**
     * 获取 payCompleteTime
     * @return 返回 payCompleteTime
     */
    @Column(name = "PAYCOMPLETETIME")
    public Date getPayCompleteTime()
    {
        return payCompleteTime;
    }

    /**
     * 设置 payCompleteTime
     * @param payCompleteTime
     */
    public void setPayCompleteTime(Date payCompleteTime)
    {
        this.payCompleteTime = payCompleteTime;
    }

    /**
     * 获取 createTime
     * @return 返回 createTime
     */
    @Column(name = "CREATETIME")
    public Date getCreateTime()
    {
        return createTime;
    }

    /**
     * 设置 createTime
     * @param createTime
     */
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    /**
     * 获取 payCreateTime
     * @return 返回 payCreateTime
     */
    @Column(name = "PAYCREATETIME")
    public Date getPayCreateTime()
    {
        return payCreateTime;
    }

    /**
     * 设置 payCreateTime
     * @param payCreateTime
     */
    public void setPayCreateTime(Date payCreateTime)
    {
        this.payCreateTime = payCreateTime;
    }

    /**
     * 获取 creditStatus
     * @return 返回 creditStatus
     */
    @Column(name = "CREDITSTATUS")
    public String getCreditStatus()
    {
        return creditStatus;
    }

    /**
     * 设置 creditStatus
     * @param creditStatus
     */
    public void setCreditStatus(String creditStatus)
    {
        this.creditStatus = creditStatus;
    }

    /**
     * 获取 creditTime
     * @return 返回 creditTime
     */
    @Column(name = "CREDITTIME")
    public Date getCreditTime()
    {
        return creditTime;
    }

    /**
     * 设置 creditTime
     * @param creditTime
     */
    public void setCreditTime(Date creditTime)
    {
        this.creditTime = creditTime;
    }

    /**
     * 获取 currencyType
     * @return 返回 currencyType
     */
    @Column(name = "CURRENCYTYPE")
    public String getCurrencyType()
    {
        return currencyType;
    }

    /**
     * 设置 currencyType
     * @param currencyType
     */
    public void setCurrencyType(String currencyType)
    {
        this.currencyType = currencyType;
    }

    /**
     * 获取 customerFee
     * @return 返回 customerFee
     */
    @Column(name = "CUSTOMERFEE")
    public String getCustomerFee()
    {
        return customerFee;
    }

    /**
     * 设置 customerFee
     * @param customerFee
     */
    public void setCustomerFee(String customerFee)
    {
        this.customerFee = customerFee;
    }

    /**
     * 获取 customerNo
     * @return 返回 customerNo
     */
    @Column(name = "CUSTOMERNO")
    public String getCustomerNo()
    {
        return customerNo;
    }

    /**
     * 设置 customerNo
     * @param customerNo
     */
    public void setCustomerNo(String customerNo)
    {
        this.customerNo = customerNo;
    }

    /**
     * 获取 customerOrderCode
     * @return 返回 customerOrderCode
     */
    @Column(name = "CUSTOMERORDERCODE")
    public String getCustomerOrderCode()
    {
        return customerOrderCode;
    }

    /**
     * 设置 customerOrderCode
     * @param customerOrderCode
     */
    public void setCustomerOrderCode(String customerOrderCode)
    {
        this.customerOrderCode = customerOrderCode;
    }

    /**
     * 获取 customerRate
     * @return 返回 customerRate
     */
    @Column(name = "CUSTOMERRATE")
    public String getCustomerRate()
    {
        return customerRate;
    }

    /**
     * 设置 customerRate
     * @param customerRate
     */
    public void setCustomerRate(String customerRate)
    {
        this.customerRate = customerRate;
    }

    /**
     * 获取 description
     * @return 返回 description
     */
    @Column(name = "DESCRIPTION")
    public String getDescription()
    {
        return description;
    }

    /**
     * 设置 description
     * @param description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * 获取 externalId
     * @return 返回 externalId
     */
    @Column(name = "EXTERNALID")
    public String getExternalId()
    {
        return externalId;
    }

    /**
     * 设置 externalId
     * @param externalId
     */
    public void setExternalId(String externalId)
    {
        this.externalId = externalId;
    }

    /**
     * 获取 finalPaymentId
     * @return 返回 finalPaymentId
     */
    @Column(name = "FINALPAYMENTID")
    public String getFinalPaymentId()
    {
        return finalPaymentId;
    }

    /**
     * 设置 finalPaymentId
     * @param finalPaymentId
     */
    public void setFinalPaymentId(String finalPaymentId)
    {
        this.finalPaymentId = finalPaymentId;
    }

    /**
     * 获取 payId
     * @return 返回 payId
     */
    @Column(name = "PAYID")
    public String getPayId()
    {
        return payId;
    }

    /**
     * 设置 payId
     * @param payId
     */
    public void setPayId(String payId)
    {
        this.payId = payId;
    }

    /**
     * 获取 issuer
     * @return 返回 issuer
     */
    @Column(name = "ISSUER")
    public String getIssuer()
    {
        return issuer;
    }

    /**
     * 设置 issuer
     * @param issuer
     */
    public void setIssuer(String issuer)
    {
        this.issuer = issuer;
    }

    /**
     * 获取 payOptimistic
     * @return 返回 payOptimistic
     */
    @Column(name = "PAYOPTIMISTIC")
    public String getPayOptimistic()
    {
        return payOptimistic;
    }

    /**
     * 设置 payOptimistic
     * @param payOptimistic
     */
    public void setPayOptimistic(String payOptimistic)
    {
        this.payOptimistic = payOptimistic;
    }

    /**
     * 获取 orderId
     * @return 返回 orderId
     */
    @Column(name = "ORDERID")
    public String getOrderId()
    {
        return orderId;
    }

    /**
     * 设置 orderId
     * @param orderId
     */
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    /**
     * 获取 pan
     * @return 返回 pan
     */
    @Column(name = "PAN")
    public String getPan()
    {
        return pan;
    }

    /**
     * 设置 pan
     * @param pan
     */
    public void setPan(String pan)
    {
        this.pan = pan;
    }

    /**
     * 获取 paymentfeeId
     * @return 返回 paymentfeeId
     */
    @Column(name = "PAYMENTFEEID")
    public String getPaymentfeeId()
    {
        return paymentfeeId;
    }

    /**
     * 设置 paymentfeeId
     * @param paymentfeeId
     */
    public void setPaymentfeeId(String paymentfeeId)
    {
        this.paymentfeeId = paymentfeeId;
    }

    /**
     * 获取 posBatch
     * @return 返回 posBatch
     */
    @Column(name = "POSBATCH")
    public String getPosBatch()
    {
        return posBatch;
    }

    /**
     * 设置 posBatch
     * @param posBatch
     */
    public void setPosBatch(String posBatch)
    {
        this.posBatch = posBatch;
    }

    /**
     * 获取 payPosBatch
     * @return 返回 payPosBatch
     */
    @Column(name = "PAYPOSBATCH")
    public String getPayPosBatch()
    {
        return payPosBatch;
    }

    /**
     * 设置 payPosBatch
     * @param payPosBatch
     */
    public void setPayPosBatch(String payPosBatch)
    {
        this.payPosBatch = payPosBatch;
    }

    /**
     * 获取 posCati
     * @return 返回 posCati
     */
    @Column(name = "POSCATI")
    public String getPosCati()
    {
        return posCati;
    }

    /**
     * 设置 posCati
     * @param posCati
     */
    public void setPosCati(String posCati)
    {
        this.posCati = posCati;
    }

    /**
     * 获取 posRequestId
     * @return 返回 posRequestId
     */
    @Column(name = "POSREQUESTID")
    public String getPosRequestId()
    {
        return posRequestId;
    }

    /**
     * 设置 posRequestId
     * @param posRequestId
     */
    public void setPosRequestId(String posRequestId)
    {
        this.posRequestId = posRequestId;
    }

    /**
     * 获取 payPosRequestId
     * @return 返回 payPosRequestId
     */
    @Column(name = "PAYPOSREQUESTID")
    public String getPayPosRequestId()
    {
        return payPosRequestId;
    }

    /**
     * 设置 payPosRequestId
     * @param payPosRequestId
     */
    public void setPayPosRequestId(String payPosRequestId)
    {
        this.payPosRequestId = payPosRequestId;
    }

    /**
     * 获取 settleTime
     * @return 返回 settleTime
     */
    @Column(name = "SETTLETIME")
    public Date getSettleTime()
    {
        return settleTime;
    }

    /**
     * 设置 settleTime
     * @param settleTime
     */
    public void setSettleTime(Date settleTime)
    {
        this.settleTime = settleTime;
    }

    /**
     * 获取 shopId
     * @return 返回 shopId
     */
    @Column(name = "SHOPID")
    public String getShopId()
    {
        return shopId;
    }

    /**
     * 设置 shopId
     * @param shopId
     */
    public void setShopId(String shopId)
    {
        this.shopId = shopId;
    }

    /**
     * 获取 sourcePaymentId
     * @return 返回 sourcePaymentId
     */
    @Column(name = "SOURCEPAYMENTID")
    public String getSourcePaymentId()
    {
        return sourcePaymentId;
    }

    /**
     * 设置 sourcePaymentId
     * @param sourcePaymentId
     */
    public void setSourcePaymentId(String sourcePaymentId)
    {
        this.sourcePaymentId = sourcePaymentId;
    }

    /**
     * 获取 status
     * @return 返回 status
     */
    @Column(name = "STATUS")
    public String getStatus()
    {
        return status;
    }

    /**
     * 设置 status
     * @param status
     */
    public void setStatus(String status)
    {
        this.status = status;
    }

    /**
     * 获取 payStatus
     * @return 返回 payStatus
     */
    @Column(name = "PAYSTATUS")
    public String getPayStatus()
    {
        return payStatus;
    }

    /**
     * 设置 payStatus
     * @param payStatus
     */
    public void setPayStatus(String payStatus)
    {
        this.payStatus = payStatus;
    }

    /**
     * 获取 transType
     * @return 返回 transType
     */
    @Column(name = "TRANSTYPE")
    public String getTransType()
    {
        return transType;
    }

    /**
     * 设置 transType
     * @param transType
     */
    public void setTransType(String transType)
    {
        this.transType = transType;
    }

    /**
     * 获取 payTransType
     * @return 返回 payTransType
     */
    @Column(name = "PAYTRANSTYPE")
    public String getPayTransType()
    {
        return payTransType;
    }

    /**
     * 设置 payTransType
     * @param payTransType
     */
    public void setPayTransType(String payTransType)
    {
        this.payTransType = payTransType;
    }
    /**
     * 获取 payTransType
     * @return 返回 payTransType
     */
    @Column(name = "ORDER_OPTIMISTIC")
	public String getOrderOptimistic() {
		return orderOptimistic;
	}
	public void setOrderOptimistic(String orderOptimistic) {
		this.orderOptimistic = orderOptimistic;
	}
	/**
	 * 标签
	 * @return
	 */
	@Column(name = "TAGS")
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	/**
	 * 授权码
	 * @return
	 */
	@Column(name = "PAYAUTHORIZATIONCODE")
	public String getPayAuthorizationCode() {
		return payAuthorizationCode;
	}
	public void setPayAuthorizationCode(String payAuthorizationCode) {
		this.payAuthorizationCode = payAuthorizationCode;
	}
    
    
    
}
