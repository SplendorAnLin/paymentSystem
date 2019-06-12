package com.yl.pay.pos.bean;

import com.yl.pay.pos.entity.*;
import com.yl.pay.pos.enums.TransType;
import com.yl.pay.pos.enums.YesNo;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * 
 * Title: 银联交易标准参数 - 扩展Bean
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class ExtendPayBean{
	
	//以下为交易过程中公共属性
	private String responseCode;								//相应码
	private String exceptionCode;								//异常码	
	private Double transAmount;									//交易金额（元）
	
	//以下为交易过程中公共对象
	private TransType transType;								//交易类型	
	private CardInfo cardInfo;									//卡片信息
	private BankIdNumber bankIdNumber;							//银行卡BIN
	private Bank issuer;										//发卡行
	private Pos pos;											//POS对象
	private Shop shop;											//网点对象
	private Customer customer;									//商户对象
	private PosRequest posRequest;								//POS请求对象
	private Order order;										//订单
	private Payment payment;									//交易信息
	private Payment sourcePayment;								//原交易信息
	private BankInterface bankInterface;						//银行接口
	private BankRequest bankRequest;							//银行请求信息
	private BankChannelRouteReturnBean bankChannelRouteBean;	//银行路由信息
	private BankInterfaceTerminal bankRequestTerminal;			//银行终端信息
	private SysRouteCustomerConfigDetail sysRouteCustConfDetail;//系统路由商编配置明细
	
	private LngAndLat lngAndLat;                                //经纬度
	private EnterpriseCheckInfo enterpriseCheckInfo;            //企业验证信息
	
	private boolean sysRouteFlag;								//系统路由标识
	
	private YesNo   isIC;										//IC标识
	
	private YesNo  isForeignCard;								//境外卡标识
	
	private String serviceInfo;									//银联60.2域信息
	
	private String aquiringInstitutionId;						//银联32域,代理组织机构标识
	
	private String originalSerialNumber;						//原始流水号
	
	private Future<String> locationQueryFuture=null;			//位置查询服务结果

	//8583报文信息
	private UnionPayBean unionPayBean;					
	
	private Set<Payment> payments=new HashSet<Payment>();								//存放银行请求
	
	private YesNo  isLfb;										//是否乐付宝商户
	
	private YesNo isComplexCard;								//是否复合卡
	
	private String locationInfo;								//位置信息

		
	/**
	 * 构造
	 */
	public ExtendPayBean() {
		super();
		this.bankRequestTerminal=new BankInterfaceTerminal();
	}
	
	public Bank getIssuer() {
		return issuer;
	}
	public void setIssuer(Bank issuer) {
		this.issuer = issuer;
	}
	public TransType getTransType() {
		return transType;
	}
	public void setTransType(TransType transType) {
		this.transType = transType;
	}
	public Pos getPos() {
		return pos;
	}
	public void setPos(Pos pos) {
		this.pos = pos;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public CardInfo getCardInfo() {
		return cardInfo;
	}
	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}
	public BankIdNumber getBankIdNumber() {
		return bankIdNumber;
	}
	public void setBankIdNumber(BankIdNumber bankIdNumber) {
		this.bankIdNumber = bankIdNumber;
	}
	public PosRequest getPosRequest() {
		return posRequest;
	}
	public void setPosRequest(PosRequest posRequest) {
		this.posRequest = posRequest;
	}
	public String getExceptionCode() {
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public Double getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(Double transAmount) {
		this.transAmount = transAmount;
	}
	public BankInterface getBankInterface() {
		return bankInterface;
	}
	public void setBankInterface(BankInterface bankInterface) {
		this.bankInterface = bankInterface;
	}
	public BankRequest getBankRequest() {
		return bankRequest;
	}
	public void setBankRequest(BankRequest bankRequest) {
		this.bankRequest = bankRequest;
	}
	public UnionPayBean getUnionPayBean() {
		return unionPayBean;
	}
	public void setUnionPayBean(UnionPayBean unionPayBean) {
		this.unionPayBean = unionPayBean;
	}
	public BankChannelRouteReturnBean getBankChannelRouteBean() {
		return bankChannelRouteBean;
	}
	public void setBankChannelRouteBean(
			BankChannelRouteReturnBean bankChannelRouteBean) {
		this.bankChannelRouteBean = bankChannelRouteBean;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public BankInterfaceTerminal getBankRequestTerminal() {
		return bankRequestTerminal;
	}
	public void setBankRequestTerminal(BankInterfaceTerminal bankRequestTerminal) {
		this.bankRequestTerminal = bankRequestTerminal;
	}
	public Payment getSourcePayment() {
		return sourcePayment;
	}
	public void setSourcePayment(Payment sourcePayment) {
		this.sourcePayment = sourcePayment;
	}
	public boolean isSysRouteFlag() {
		return sysRouteFlag;
	}
	public void setSysRouteFlag(boolean sysRouteFlag) {
		this.sysRouteFlag = sysRouteFlag;
	}

	public YesNo getIsIC() {
		return isIC;
	}

	public void setIsIC(YesNo isIC) {
		this.isIC = isIC;
	}

	public SysRouteCustomerConfigDetail getSysRouteCustConfDetail() {
		return sysRouteCustConfDetail;
	}

	public void setSysRouteCustConfDetail(
			SysRouteCustomerConfigDetail sysRouteCustConfDetail) {
		this.sysRouteCustConfDetail = sysRouteCustConfDetail;
	}

	public YesNo getIsForeignCard() {
		return isForeignCard;
	}

	public void setIsForeignCard(YesNo isForeignCard) {
		this.isForeignCard = isForeignCard;
	}

	public String getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(String serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public String getAquiringInstitutionId() {
		return aquiringInstitutionId;
	}

	public void setAquiringInstitutionId(String aquiringInstitutionId) {
		this.aquiringInstitutionId = aquiringInstitutionId;
	}

	public String getOriginalSerialNumber() {
		return originalSerialNumber;
	}

	public void setOriginalSerialNumber(String originalSerialNumber) {
		this.originalSerialNumber = originalSerialNumber;
	}

	public Set<Payment> getPayments() {
		return payments;
	}

	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}
	
	public Future<String> getLocationQueryFuture() {
		return locationQueryFuture;
	}

	public void setLocationQueryFuture(Future<String> locationQueryFuture) {
		this.locationQueryFuture = locationQueryFuture;
	}

	public YesNo getIsLfb() {
		return isLfb;
	}

	public void setIsLfb(YesNo isLfb) {
		this.isLfb = isLfb;
	}

	public LngAndLat getLngAndLat() {
		return lngAndLat;
	}

	public void setLngAndLat(LngAndLat lngAndLat) {
		this.lngAndLat = lngAndLat;
	}

	public YesNo getIsComplexCard() {
		return isComplexCard;
	}

	public void setIsComplexCard(YesNo isComplexCard) {
		this.isComplexCard = isComplexCard;
	}

	public String getLocationInfo() {
		return locationInfo;
	}

	public void setLocationInfo(String locationInfo) {
		this.locationInfo = locationInfo;
	}

	public void setEnterpriseCheckInfo(EnterpriseCheckInfo enterpriseCheckInfo) {
		this.enterpriseCheckInfo = enterpriseCheckInfo;
	}

	public EnterpriseCheckInfo getEnterpriseCheckInfo() {
		return enterpriseCheckInfo;
	}
	
	
}
