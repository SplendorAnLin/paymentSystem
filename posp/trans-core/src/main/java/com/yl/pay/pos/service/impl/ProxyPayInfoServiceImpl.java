package com.yl.pay.pos.service.impl;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonNode;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;
import com.pay.common.util.AmountUtil;
import com.pay.common.util.PropertyUtil;
import com.yl.boss.api.bean.CustomerFee;
import com.yl.boss.api.bean.CustomerSettle;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.ProxyPayQueyrResponseBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.constant.ProxyPayConstant;
import com.yl.pay.pos.dao.ProxyPayInfoDao;
import com.yl.pay.pos.entity.Customer;
import com.yl.pay.pos.entity.Order;
import com.yl.pay.pos.entity.Payment;
import com.yl.pay.pos.entity.ProxyPayInfo;
import com.yl.pay.pos.enums.ProxyPayStatus;
import com.yl.pay.pos.service.ProxyPayInfoService;
import com.yl.pay.pos.util.Base64Utils;
import com.yl.pay.pos.util.FeeUtils;
import com.yl.pay.pos.util.HolidayUtils;
import com.yl.pay.pos.util.HttpUtils;
import com.yl.pay.pos.util.MD5;


/**
 * Title: BankChannelDaoImpl
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class ProxyPayInfoServiceImpl implements ProxyPayInfoService{

	private static final Log log = LogFactory.getLog(ProxyPayInfoServiceImpl.class);
	
	private static String proxyPayUrl;
	private static String proxyPayCallback;
	private static String proxyPayCustomerNo;
	private static String proxyPaySecretKey;
	private static String proxyPayQueryUrl;
	private static PropertyUtil propertyUtil =null;
	static{
		propertyUtil = PropertyUtil.getInstance("system");
		proxyPayUrl=propertyUtil.getProperty("proxy.pay.interface.url");
		proxyPayCallback=propertyUtil.getProperty("proxy.pay.callback.url");
		proxyPayCustomerNo=propertyUtil.getProperty("proxy.pay.customer.customerno");
		proxyPaySecretKey=propertyUtil.getProperty("proxy.pay.customer.secretkey");
		proxyPayQueryUrl=propertyUtil.getProperty("proxy.pay.query.interface.url");
		
	}

	
	private CustomerInterface customerInterface;
	
	public CustomerInterface getCustomerInterface() {
		return customerInterface;
	}

	public void setCustomerInterface(CustomerInterface customerInterface) {
		this.customerInterface = customerInterface;
	}
	
	private ProxyPayInfoDao proxyPayInfoDao;

	public ProxyPayInfoDao getProxyPayInfoDao() {
		return proxyPayInfoDao;
	}

	public void setProxyPayInfoDao(ProxyPayInfoDao proxyPayInfoDao) {
		this.proxyPayInfoDao = proxyPayInfoDao;
	}

	@Override
	public ProxyPayInfo findById(Long id) {
		return proxyPayInfoDao.findById(id);
	}

	@Override
	public ProxyPayInfo create(ProxyPayInfo proxyPayInfo) {
		return proxyPayInfoDao.create(proxyPayInfo);
	}

	@Override
	public ProxyPayInfo update(ProxyPayInfo proxyPayInfo) {
		return proxyPayInfoDao.update(proxyPayInfo);
	}

	@Override
	public List<ProxyPayInfo> findByOrderNo(String orderNo) {
		return proxyPayInfoDao.findByOrderNo(orderNo);
	}
	
	
	@Override
	public void proxyPay(ProxyPayInfo proxyPayInfo) {
		log.info("++++++++++++代付开始,orderNo:"+proxyPayInfo.getOrderNo());

		String url = proxyPayUrl;
		log.info("++++++++++++代付调用接口请求URL:"+url);
		
		//创建代付单
		List<ProxyPayInfo> list = findByOrderNo(proxyPayInfo.getOrderNo());
		if (null != list && list.size() > 0 ){
			log.error("+++++++++代付订单号已经存在," + proxyPayInfo.toString());
			return;
		}
		proxyPayInfo = create(proxyPayInfo);
		try {
			String data = generateData(proxyPayInfo);
			log.info("++++++++++++代付调用接口请求数据:"+data);
			String rspCode = HttpUtils.sendReq(url, data, "GET");
			log.info("++++++++++++代付调用接口请求结果:"+rspCode);
			Map<String,String > respMap = JsonUtils.toObject(rspCode, Map.class);
			if (ProxyPayConstant.PROXY_PAY_SUCC.equals(respMap.get("resultCode"))){
				proxyPayInfo.setStatus(ProxyPayStatus.PAY_REQ_SUCC);
			} else {
				proxyPayInfo.setStatus(ProxyPayStatus.PAY_REQ_FAIL);
			}
			proxyPayInfo.setRspCode(respMap.get("resultCode"));
			proxyPayInfo.setRspMsg(respMap.get("message"));
			proxyPayInfo.setLastUpdateTime(new Date());
			proxyPayInfo = update(proxyPayInfo);
		} catch (Exception e) {
			log.error("+++++++++代付异常,orderNo:" + proxyPayInfo.getOrderNo(),e);
			proxyPayInfo.setStatus(ProxyPayStatus.PAY_REQ_FAIL);
			proxyPayInfo.setRspCode(ProxyPayConstant.PROXY_PAY_SYSTEM_ERROR);
			proxyPayInfo.setRspMsg("系统异常："+e.toString());
			proxyPayInfo.setLastUpdateTime(new Date());
			proxyPayInfo = update(proxyPayInfo);
		}
		
	}
	
	@Override
	public ProxyPayInfo generateProxyPayInfo(ExtendPayBean extendPayBean) throws Exception{
		//调用接口查询结算卡信息
		CustomerSettle customerSettle = customerInterface.getCustomerSettle(extendPayBean.getCustomer().getCustomerNo());
		if(null != customerSettle){
			UnionPayBean unionPayBean = extendPayBean.getUnionPayBean();
			Payment payment = extendPayBean.getPayment();
			ProxyPayInfo proxyPayInfo = new ProxyPayInfo();
			//proxyPayInfo.setBankCustomerNo(unionPayBean.getCardAcceptorId());
			proxyPayInfo.setBankCustomerNo("896420157021880");
			proxyPayInfo.setSysFlowNo(extendPayBean.getOrder().getPosRequestId());
			proxyPayInfo.setPan(unionPayBean.getPan());
			proxyPayInfo.setTransAmount(String.valueOf(extendPayBean.getOrder().getAmount()));
			proxyPayInfo.setCustomerNo(extendPayBean.getCustomer().getCustomerNo());
			proxyPayInfo.setCreateTime(new Date());
			proxyPayInfo.setExternalId(extendPayBean.getOrder().getExternalId());
			proxyPayInfo.setBankExternalId(payment.getBankExternalId());
			proxyPayInfo.setCustName(customerSettle.getAccountName());
			proxyPayInfo.setCustCardNo(customerSettle.getAccountNo());
			proxyPayInfo.setIssName(customerSettle.getOpenBankName());
			proxyPayInfo.setIssNo(getCnaps(customerSettle.getOpenBankName()));
			proxyPayInfo.setCardType(customerSettle.getCustomerType());
			proxyPayInfo.setOrderCompleteTime(extendPayBean.getOrder().getCompleteTime());
			CustomerFee customerProxyPayFee = null;
			if(HolidayUtils.isHoliday()){
				customerProxyPayFee = customerInterface.getCustomerFee(extendPayBean.getCustomer().getCustomerNo(), ProductType.HOLIDAY_REMIT.name());
			} else  {
				customerProxyPayFee = customerInterface.getCustomerFee(extendPayBean.getCustomer().getCustomerNo(), ProductType.REMIT.name());
			}
			/**
			 * 计算商户结算金额
			 * 商户结算金额=商户交易金额-商户交易手续费-商户代付手续费（代付、假日付）
			 * 要查询当前日期是否节假日
			 */
			//计算代付手续费
			Double proxyPayFee = FeeUtils.computeFee(extendPayBean.getOrder().getAmount(), customerProxyPayFee.getFeeType(), customerProxyPayFee.getFee(),0.01);
			proxyPayInfo.setProxyPayCost(String.valueOf(proxyPayFee));
			
			//计算商户结算金额
			Double custAmount = AmountUtil.sub( AmountUtil.sub(extendPayBean.getOrder().getAmount(), extendPayBean.getPayment().getCustomerFee()), proxyPayFee);
			
			proxyPayInfo.setCustAmount(String.valueOf(custAmount));
			
			proxyPayInfo.setOrderNo(payment.getBankExternalId());
			proxyPayInfo.setNotifyUrl(proxyPayCallback);
			proxyPayInfo.setSignCode(MD5.MD5Encode(proxyPaySecretKey+proxyPayCustomerNo,"utf-8").toUpperCase());
			proxyPayInfo.setStatus(ProxyPayStatus.INIT);
			return proxyPayInfo;
			
		}  else {
			throw new Exception("代付结算卡异常");
		}
	}
	
	private String generateData(ProxyPayInfo proxyPayInfo) throws Exception{
		String custName = URLEncoder.encode(Base64Utils.encode(proxyPayInfo.getCustName().getBytes("UTF-8")), "UTF-8");
		//String issname = URLEncoder.encode(Base64Utils.encode(proxyPayInfo.getIssName().getBytes("UTF-8")), "UTF-8");
		
		String data = 
					"refNo=" + proxyPayInfo.getBankExternalId()+ "&" + 
					"custName=" + custName + "&" + 
					"custCardNo=" + proxyPayInfo.getCustCardNo() + "&" + 
					"txnAmt=" + amountCovert(proxyPayInfo.getCustAmount()) + "&" + 
					"cardType=" + (proxyPayInfo.getCardType().equals("INDIVIDUAL") ? "PERSONAL" : "BUSINESS") + "&" + 
					"issno=" + proxyPayInfo.getIssNo() + "&" + 
					"casOrdNo=" + proxyPayInfo.getOrderNo() + "&" + 
					"notifyUrl=" + proxyPayInfo.getNotifyUrl() + "&" + 
					"mercNo=" + proxyPayInfo.getBankCustomerNo() + "&" + 
					"signCode=" + proxyPayInfo.getSignCode();
		
		return data;
	}

	@Override
	public ProxyPayInfo complete(ProxyPayInfo proxyPayInfo) {
		List<ProxyPayInfo> list = findByOrderNo(proxyPayInfo.getOrderNo());
		if(null != list && list.size() > 0 ){
			ProxyPayInfo proxyPayInfoComplet = list.get(0);
			if(ProxyPayConstant.PROXY_PAY_SUCC.equals(proxyPayInfo.getRspCode())){
				proxyPayInfoComplet.setStatus(ProxyPayStatus.PAY_RESP_SUCC);
			} else {
				proxyPayInfoComplet.setStatus(ProxyPayStatus.PAY_RESP_FAIL);
			}
			proxyPayInfoComplet.setRspCode(proxyPayInfo.getRspCode());
			proxyPayInfoComplet.setRspMsg(proxyPayInfo.getRspMsg());
			update(proxyPayInfoComplet);
			
		}
		return null;
	}

	/**
	 * 以元为单位的金额转换为以分为单位的金额
	 * 只适用于小数点两位以内（包含两位）的转换
	 * @param amount
	 * @return
	 */
	private String amountCovert(String amount){
		String amountTmp = amount;
		if (!amountTmp.contains(".")){
			return amountTmp+"00";
		} else {
			//小数点之前金额
			String pointBefore = amountTmp.substring(0,amountTmp.indexOf("."));
			
			//小数点之后金额
			String pointAfter = amountTmp.substring(amountTmp.indexOf(".")+1);
			if (pointAfter.length()==2){
				amountTmp = pointBefore + pointAfter;
			} else {
				amountTmp = pointBefore + pointAfter+"0";
			}
			return amountTmp;
		}
	}
	
	/**
	 * 根据银行名称获取银行联行号
	 * @param bankName
	 * @return
	 * @throws Exception
	 */
	private static String getCnaps(String bankName) throws Exception {
		String code="";
		String words = "";
		words = "words=" + URLEncoder.encode(bankName, "UTF-8");
		words += "&1=1";
		String url ="http://pay.feiyijj.com/cachecenter/cnaps/batchSearch.htm?fields=clearingBankCode&fields=code&fields=providerCode&fields=clearingBankLevel&fields=name";
		String resData = HttpClientUtils.send(Method.POST, url, words, true, Charset.forName("UTF-8").name(), 40000);
		JsonNode cnapsNodes = JsonUtils.getInstance().readTree(resData);
		for (JsonNode cnapsNode : cnapsNodes) {
			code=cnapsNode.get("code").getTextValue().toString();
		}

		return code;
	}
	
	@Override
	public ProxyPayQueyrResponseBean proxyPayQuery(String orderNo) {
		String rspCode;
		ProxyPayQueyrResponseBean proxyPayQueyrResponseBean= null;
		try {
			proxyPayQueyrResponseBean = new ProxyPayQueyrResponseBean();
			String url = proxyPayQueryUrl;
			log.info("++++++++++++代付查询调用接口请求数据:"+url);
			String data = "orderId="+orderNo;
			log.info("++++++++++++代付查询调用接口请求数据:"+data);
			rspCode = HttpUtils.sendReq(url, data, "GET");
			log.info("++++++++++++代付查询调用接口请求结果:"+rspCode);
			Map<String,String > respMap = JsonUtils.toObject(rspCode, Map.class);
			proxyPayQueyrResponseBean.setOrdernum(respMap.get("orderNo"));
			proxyPayQueyrResponseBean.setSign(respMap.get("sign"));
			proxyPayQueyrResponseBean.setCode(respMap.get("resultCode"));
			proxyPayQueyrResponseBean.setMsg(respMap.get("message"));
		} catch (Exception e) {
			proxyPayQueyrResponseBean = new ProxyPayQueyrResponseBean();
			proxyPayQueyrResponseBean.setOrdernum(orderNo);
			proxyPayQueyrResponseBean.setCode(ProxyPayConstant.PROXY_PAY_SYSTEM_ERROR);
			proxyPayQueyrResponseBean.setMsg("系统异常："+e.toString());
			
			log.error("+++++++++代付查询调用接口异常:orderNo:"+orderNo,e);
		}
		return proxyPayQueyrResponseBean;
	}
	

	@Override
	public List<ProxyPayInfo> findListByStatusAndCreateTime(Date start, Date end) {
		return proxyPayInfoDao.findListByStatusAndCreateTime(start, end);
	}
	
	public static void main(String[] args) {
		ProxyPayInfoServiceImpl proxyPayInfoServiceImpl = new ProxyPayInfoServiceImpl();
		System.out.println(ProxyPayInfoServiceImpl.proxyPayCallback);
		System.out.println(ProxyPayInfoServiceImpl.proxyPayQueryUrl);
		System.out.println(ProxyPayInfoServiceImpl.proxyPayUrl);
		System.out.println(ProxyPayInfoServiceImpl.proxyPayCustomerNo);
		System.out.println(ProxyPayInfoServiceImpl.proxyPaySecretKey);
		System.out.println(proxyPayInfoServiceImpl.proxyPayQuery("183009161325").toString());
	}
	
}

