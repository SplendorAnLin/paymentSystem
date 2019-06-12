package com.yl.pay.pos.service.impl;

import com.pay.common.util.AmountUtil;
import com.pay.common.util.DateUtil;
import com.pay.common.util.PropertyUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.IPAddressInfoBean;
import com.yl.pay.pos.bean.LocationBean;
import com.yl.pay.pos.bean.LocationQueryBean;
import com.yl.pay.pos.dao.TransCheckProfileDao;
import com.yl.pay.pos.entity.*;
import com.yl.pay.pos.enums.YesNo;
import com.yl.pay.pos.util.LonLatDistanceUtil;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 定位查询服务
 * @author zhongxiang.ren
 *
 */
public class LocationQueryService{
	private static final Log log = LogFactory.getLog(LocationQueryService.class);
	
	private static PropertyUtil propertyUtil =null;
	// 定为服务地址
	private static final String locationServerUrl ;
	private static final double DISTANCE=2500; //单位m
	private static final double AMOUNT=10;      //金额最低值
	private static  Date customeNetINStart; //商户入网时间
	private static  Date customeProvincialStart; //商户跨省启动时间
	private static String pattern="yyyy-MM-dd HH:mm:ss";
	private static String regex=".*[^0]+.*";
	
	private static TransCheckProfileDao transCheckProfileDao;
	static{
		propertyUtil = PropertyUtil.getInstance("transConfig");
		locationServerUrl = propertyUtil.getProperty("locationServerUrl");
		String customeNetINStartStr=propertyUtil.getProperty("customeNetINStart");
		String customeProvincialStartStr=propertyUtil.getProperty("customeProvincialStart");
		try {
			customeNetINStart=DateUtil.parseToDate(customeNetINStartStr, pattern);
			customeProvincialStart=DateUtil.parseToDate(customeProvincialStartStr, pattern);
		} catch (ParseException e) {
			log.error("customeNetINStart is error ", e);
			customeNetINStart=new Date();
			customeProvincialStart=new Date();
		}
	}
	
	/**
	 * 根据  小区编号和基站编号查询位置信息
	 * @param url
	 * @return 地区编码
	 */
	public static String queryLocation(String url) {
		log.info("queryLocationURL: "+url);
		String retStr = "";
		// 创建HttpClient实例     
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, 
        		"UTF-8");//指定传送字符集为UTF-8格式
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);//设置连接超时时间为5秒（连接初始化时间）
        //创建GET方法的实例
        GetMethod getMethod = new GetMethod(url);
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,5000); //设置请求超时时间5秒
        getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        getMethod.addRequestHeader("Content-Type","text/html;charset=UTF-8");
        getMethod.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
        try {
	        //执行getMethod
	        int statusCode = httpClient.executeMethod(getMethod);
	        if (statusCode != HttpStatus.SC_OK) {
	        	log.info("Method failed: "+ getMethod.getStatusLine());
	        	return null;
	        }
	        //读取内容 
	        byte[] responseBody = getMethod.getResponseBody();
	        //处理内容
	        retStr = new String(responseBody);
//	        log.info("retStr: "+retStr);
        } catch (Exception e) {
        	//发生致命的异常，可能是协议不对或者返回的内容有问题
        	log.info("queryLocation error ",e);
        } finally {
        	//释放连接
        	getMethod.releaseConnection();
        }
		return retStr;
	}
	
	public static Future<String> verifyLocation(LocationQueryBean locationQueryBean, ExtendPayBean extendPayBean){
		Callable<String> lqTask = new LocationQueryTask(locationQueryBean,extendPayBean);
		FutureTask<String> futureTask = new FutureTask<String>(lqTask);
		// 使用futureTask创建一个线程
		Thread locationQueryThread = new Thread(futureTask);
		locationQueryThread.start();
		
		return futureTask;
	}
	
	/**
	 * Title: 内部类  位置查询任务 
	 * Description: 
	 * Copyright: Copyright (c)2014
	 * Company: com.yl.pay
	 * @author jun
	 */
	private static class LocationQueryTask implements Callable<String> {
		/**
		 * 位置查询参数
		 */
		private LocationQueryBean locationQueryBean;//地址参数查询（基站信息，IP）
		private ExtendPayBean extendPayBean ;
		public LocationQueryTask(LocationQueryBean locationQueryBean, ExtendPayBean extendPayBean) {
			super();
			this.locationQueryBean = locationQueryBean;
			this.extendPayBean=extendPayBean;
		}

		public boolean  acrossProvinceWhite(){
			Customer customer=extendPayBean.getCustomer();
			List<TransCheckProfile> transCheckProfileli=transCheckProfileDao.findByBizTypeAndProfileTypeAndProfileValue("AcrossProvinceWhite", "customer", customer.getCustomerNo());
			if(transCheckProfileli!=null&&transCheckProfileli.size()>0){
				return true;
			}
			return false;
		}
		@Override
		public String call() throws Exception {
			String locationInfo = locationQueryBean.getLocationInfo();
			String ip = locationQueryBean.getIp();
			try {
				String requestLocationInfo=null;		//交易位置信息
				Customer customer=extendPayBean.getCustomer();
				EnterpriseCheckInfo enterpriseCheckInfo=extendPayBean.getEnterpriseCheckInfo();
				if(StringUtils.isNotBlank(locationInfo)){
					if(!locationInfo.startsWith("T")){
						String urlLbs = locationServerUrl;
						if(locationInfo.contains("|")){
							locationInfo = locationInfo.substring(0, locationInfo.indexOf("|"));
						}
						
						if(!locationInfo.matches(regex)){
							return Constant.LOCATION_SUCCESS_CODE;
						}
						String lac=locationInfo.substring(0,5);
						String cellId=locationInfo.substring(5);
						urlLbs+="/info/lbs/get?";
						urlLbs+="cellId="+cellId+"&lac="+lac;
				        String location=queryLocation(urlLbs);//调用远程基站查询服务
				        if(StringUtils.isNotBlank(location)){
				        	JSONObject jsonobject = JSONObject.fromObject(location);
							LocationBean locationBean =(LocationBean) JSONObject.toBean(jsonobject, LocationBean.class);
							log.info("check lac and cell location response country:"+locationBean.getCountry()+",province:"+locationBean.getProvince()+",provinceCode:"+locationBean.getProvinceCode()+",lat:"+locationBean.getLat()+",lng:"+locationBean.getLng());
							requestLocationInfo=locationBean.getProvinceCode();
							String city = locationBean.getCityCode();
							if(StringUtils.isBlank(city)){
								requestLocationInfo+="|NULL";
							}else {
								requestLocationInfo+="|";
								requestLocationInfo+=city;
							}
							
							//TODO 符合条件进行跨省校验
							if(StringUtils.isNotBlank(locationBean.getProvinceCode())&&
									!locationBean.getProvinceCode().equals(extendPayBean.getCustomer().getOrganization().getCode().substring(0,2))){
								requestLocationInfo+="|"+Constant.POSREQUEST_LOCATION_KS;
								
								if(customeProvincialStart.compareTo(new Date())<0&&customer.getOpenTime()!=null&&customer.getOpenTime().compareTo(customeNetINStart)>0&&enterpriseCheckInfo!=null&&!"Y".equals(enterpriseCheckInfo.getStatus())){
									//跨省，直接返回02
									if(acrossProvinceWhite()){
										return Constant.LOCATION_SUCCESS_CODE;
									}else{
										extendPayBean.setLocationInfo(requestLocationInfo);
										return Constant.LOCATION_PRO_CODE;	
									}
									
								}
								
							}
							
							//暂时增加基站定位返回null的日志
							if(StringUtils.isBlank(locationBean.getConntryCode())&&StringUtils.isBlank(locationBean.getProvinceCode())&&StringUtils.isBlank(locationBean.getCityCode())){
								log.info("location by base station return msg is null/customerNo=["+extendPayBean.getCustomer().getCustomerNo()+"],posCati=["+extendPayBean.getPos().getPosCati()+"],baseInfo=["+locationInfo+"]");
							}
							
							if(StringUtils.isNotBlank(locationBean.getConntryCode())&&!Constant.LOCATION_ALLOW_COUNTRY_CODE.equals(locationBean.getConntryCode())){
								return Constant.LOCATION_BORDER_CODE;
							}else{
								if((StringUtils.isNotBlank(locationBean.getProvinceCode()))&&(locationBean.getProvinceCode().startsWith(Constant.LOCATION_VERIFY_1)
										||locationBean.getProvinceCode().startsWith(Constant.LOCATION_VERIFY_2)
					        			||locationBean.getProvinceCode().startsWith(Constant.LOCATION_VERIFY_3))){
									return Constant.LOCATION_BORDER_CODE;
								}else{
									YesNo  lfb=extendPayBean.getIsLfb();
									Double amount= extendPayBean.getTransAmount();
									if(locationBean.getLat()!=null&&locationBean.getLng()!=null&&YesNo.Y.equals(lfb)&&AmountUtil.compare(amount, AMOUNT)){
										LngAndLat lngAndLat=extendPayBean.getLngAndLat();
										Pos pos =extendPayBean.getPos();
										if(lngAndLat!=null){
											log.info("lngAndLat getLat:"+lngAndLat.getLat()+" getLng:"+lngAndLat.getLng());
											double distance=LonLatDistanceUtil.GetDistance(lngAndLat, new LngAndLat(locationBean.getLng().doubleValue(),locationBean.getLat().doubleValue()));
											log.info("distance:"+distance+" pos:"+pos.getPosCati());
											if(distance>DISTANCE){
												return  Constant.LOCATION_LFB_CODE;
											}
											
										}else{
											LngAndLat lngAndLatNew=new LngAndLat();
											lngAndLatNew.setIsSave(true);
											lngAndLatNew.setPosCati(pos.getPosCati());
											lngAndLatNew.setLat(locationBean.getLat().doubleValue());
											lngAndLatNew.setLng(locationBean.getLng().doubleValue());
											extendPayBean.setLngAndLat(lngAndLatNew);
										}
									}
									
								}
							}
				        }
					}else{//校验经纬度信息
						if(locationInfo.startsWith("T")){
							String urlLngLat = locationServerUrl;
							locationInfo=locationInfo.substring(1);
							String lat = locationInfo.substring(0,locationInfo.indexOf("|"));
							String lng = locationInfo.substring(locationInfo.indexOf("|")+1);
							urlLngLat+="/info/position/get?";
							urlLngLat+="lng="+lng+"&lat="+lat;
							String location=queryLocation(urlLngLat);//调用远程基站查询服务
							if(StringUtils.isNotBlank(location)){
								JSONObject jsonobject = JSONObject.fromObject(location);
								LocationBean locationBean =(LocationBean) JSONObject.toBean(jsonobject, LocationBean.class);
								log.info("check lnglat location response country:"+locationBean.getCountry()+",province:"+locationBean.getProvince()+",provinceCode:"+locationBean.getProvinceCode());
								requestLocationInfo=locationBean.getProvinceCode();
								String city = locationBean.getCityCode();
								if(StringUtils.isBlank(city)){
									requestLocationInfo+="|NULL";
								}else {
									requestLocationInfo+="|";
									requestLocationInfo+=city;
								}
								//TODO 符合条件进行跨省校验
								if(StringUtils.isNotBlank(locationBean.getProvinceCode())&&
										!locationBean.getProvinceCode().equals(extendPayBean.getCustomer().getOrganization().getCode().substring(0,2))){
									requestLocationInfo+="|"+Constant.POSREQUEST_LOCATION_KS;
									if(customeProvincialStart.compareTo(new Date())<0&&customer.getOpenTime()!=null&&customer.getOpenTime().compareTo(customeNetINStart)>0&&enterpriseCheckInfo!=null&&!"Y".equals(enterpriseCheckInfo.getStatus())){
										//跨省，直接返回02
										
										if(acrossProvinceWhite()){
											return Constant.LOCATION_SUCCESS_CODE;
										}else{
											extendPayBean.setLocationInfo(requestLocationInfo);
											return Constant.LOCATION_PRO_CODE;	
										}
									}
								}
								
								if(StringUtils.isNotBlank(locationBean.getConntryCode())&&!Constant.LOCATION_ALLOW_COUNTRY_CODE.equals(locationBean.getConntryCode())){
									return  Constant.LOCATION_BORDER_CODE;
								}else{
									if((StringUtils.isNotBlank(locationBean.getProvinceCode()))&&(locationBean.getProvinceCode().startsWith(Constant.LOCATION_VERIFY_1)
											||locationBean.getProvinceCode().startsWith(Constant.LOCATION_VERIFY_2)
						        			||locationBean.getProvinceCode().startsWith(Constant.LOCATION_VERIFY_3))){
										return  Constant.LOCATION_BORDER_CODE;
									}
								}
							}
						}
					}
				}
				
				if(StringUtils.isNotBlank(ip)){//校验IP
					String url = locationServerUrl;
					url+="/info/ip/get?";
					url+="ip="+ip;
					String result = queryLocation(url);
					if(StringUtils.isBlank(result)){
						return Constant.LOCATION_SUCCESS_CODE;
					}
					JSONObject jsonobject = JSONObject.fromObject(result);
					IPAddressInfoBean ipaddressBean =(IPAddressInfoBean) JSONObject.toBean(jsonobject, IPAddressInfoBean.class);
					
					if(requestLocationInfo==null){
						requestLocationInfo=ipaddressBean.getProvinceCode();
						String city = ipaddressBean.getCityCode();
						if(StringUtils.isBlank(city)){
							requestLocationInfo+="|NULL";
						}else {
							requestLocationInfo+="|";
							requestLocationInfo+=city;
						}
						//TODO 符合条件进行跨省校验
						if(StringUtils.isNotBlank(ipaddressBean.getProvinceCode())&&
								!ipaddressBean.getProvinceCode().equals(extendPayBean.getCustomer().getOrganization().getCode().substring(0,2))){
							requestLocationInfo+="|"+Constant.POSREQUEST_LOCATION_KS;
							if(customeProvincialStart.compareTo(new Date())<0&&customer.getOpenTime()!=null&&customer.getOpenTime().compareTo(customeNetINStart)>0&&enterpriseCheckInfo!=null&&!"Y".equals(enterpriseCheckInfo.getStatus())){
								//跨省，直接返回02
								if(acrossProvinceWhite()){
									return Constant.LOCATION_SUCCESS_CODE;
								}else{
									extendPayBean.setLocationInfo(requestLocationInfo);
									return Constant.LOCATION_PRO_CODE;	
								}
							}
						}
					}
					
					if(StringUtils.isNotBlank(ipaddressBean.getCountry())){
						if(!Constant.LOCATION_ALLOW_COUNTRY.equals(ipaddressBean.getCountry())){
							return  Constant.LOCATION_BORDER_CODE;
						}else if(StringUtils.isNotBlank(ipaddressBean.getProvinceCode())){
							log.info("check ip location response country:"+ipaddressBean.getCountry()+",province:"+ipaddressBean.getProvince()+",provinceCode:"+ipaddressBean.getProvinceCode());
							if(ipaddressBean.getProvinceCode().startsWith(Constant.LOCATION_VERIFY_1)
									||ipaddressBean.getProvinceCode().startsWith(Constant.LOCATION_VERIFY_2)
				        			||ipaddressBean.getProvinceCode().startsWith(Constant.LOCATION_VERIFY_3)){
								return  Constant.LOCATION_BORDER_CODE;
							}
						}
					}
				}
				extendPayBean.setLocationInfo(requestLocationInfo);
			} catch (Throwable e) {
				log.info("LocationQueryTask queryLocation error ",e);
			}
			return Constant.LOCATION_SUCCESS_CODE;
		}
	}

	public  void setTransCheckProfileDao(
			TransCheckProfileDao transCheckProfileDao) {
		LocationQueryService.transCheckProfileDao = transCheckProfileDao;
	}
	
	
}

