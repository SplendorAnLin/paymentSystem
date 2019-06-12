package com.yl.agent.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.cache.util.CacheUtils;
import com.yl.agent.Constant;

import net.sf.json.JSONObject;

public class IpUtil {
	
	private static Logger logger = LoggerFactory.getLogger(IpUtil.class);
	
	static String ak="3gkgKnqkc5uSnH56VU5g941WS1N2tx2b";
	static String baiduAddressPath="http://api.map.baidu.com/place/v2/suggestion?query=CITY&region=all&output=json&ak="+ak;
	static String baiduAddressByll="http://api.map.baidu.com/geocoder/v2/?location=LL&output=json&ak=3gkgKnqkc5uSnH56VU5g941WS1N2tx2b";
	static String baiduIpPath="http://api.map.baidu.com/location/ip?ak="+ak+"&coor=bd09ll&ip=";
	static String taobaoIpPath="http://ip.taobao.com/service/getIpInfo.php?ip=";
	
	public static String getAddressByIp(HttpServletRequest request){
		String ip=request.getParameter("ip");
		if (ip==null||"".equals(ip)) {
			ip=HttpUtil.getIpAddress(request);
		}
		return getAddressByIp(ip);
	}
	
	public static boolean existType(String ip) {
		Map info = null;
		try{
			info = CacheUtils.get(Constant.IP_DB,ip,Map.class, true);
		}catch (Exception e){
			logger.error("", e);
		}

		if (info!=null) {
			if (info.get("type")!=null) {
				return true;
			}
		}
		return false;
	}
	public static boolean exist(String ip) {
		Map info = null;
		try{
			info = CacheUtils.get(Constant.IP_DB,ip,Map.class, true);
		}catch (Exception e){
			logger.error("IP不存在本地库{}",ip);
		}
		
		if (info!=null) {
			return true;
		}
		return false;
	}

	public static String getAddressByIp(String ip){
		Map info = null;
		try{
			info = CacheUtils.get(Constant.IP_DB,ip,Map.class, true);
		}catch (Exception e){
			logger.error("", e);
		}
		if (info==null) {
			info=getIpInfo(ip);
		}
		if (info!=null) {
			return info.get("address").toString();
		}
		return null;
	}
	public static void setIpInfo(String ip){
		getIpInfo(ip);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getIpInfo(String ip){
		Map info=null;
		if (info==null) {
			JSONObject baiduIp=HttpUtil.sendAsciiJsonReq(baiduIpPath+ip);
			if (baiduIp!=null&&"0".equals(baiduIp.getString("status"))) {
				JSONObject ipInfo=baiduIp.getJSONObject("content");
				info=new HashMap<>();
				info.put("source", "baidu");//来源
				info.put("country", "中国");//国家【百度只能识别国内地址】
				info.put("address","中国"+ipInfo.get("address"));//地址
				JSONObject detailInfo=ipInfo.getJSONObject("address_detail");
				info.put("city", detailInfo.get("city"));//市
				info.put("region", detailInfo.get("province"));//省
				info.put("county", detailInfo.get("district"));//区
				info.put("street", detailInfo.get("street"));//街
				info.put("street_number", detailInfo.get("street_number"));//门牌号
				JSONObject pointInfo=ipInfo.getJSONObject("point");
				info.put("lat", pointInfo.get("y"));
				info.put("lng", pointInfo.get("x"));
			}
		}
		if (info==null) {//淘宝可识别ip是否为哪国IP和isp运营商
			JSONObject taobaoIp=HttpUtil.sendAsciiJsonReq(taobaoIpPath+ip);
			if (taobaoIp!=null&&"0".equals(taobaoIp.getString("code"))) {
				JSONObject ipInfo=taobaoIp.getJSONObject("data");
				info=new HashMap<>();
				info.put("source", "taobao");
				info.put("country", ipInfo.get("country"));//国家
				info.put("area", ipInfo.get("area"));//国内区域
				info.put("region", ipInfo.get("region"));
				info.put("city", ipInfo.get("city"));
				info.put("county", ipInfo.get("county"));
				info.put("isp", ipInfo.get("isp"));//运营商
				if (info.get("city")!=null&&!"".equals(info.get("city").toString())) {
					JSONObject baiduAddress=HttpUtil.sendAsciiJsonReq(baiduAddressPath.replace("CITY", info.get("city").toString()));
					if (baiduAddress.getInt("status")==0) {
						baiduAddress=baiduAddress.getJSONArray("result").getJSONObject(0).getJSONObject("location");
						info.put("lat", baiduAddress.get("lat"));
						info.put("lng", baiduAddress.get("lng"));
					}
				}
				if ("重庆市".equals(ipInfo.get("region"))||"北京市".equals(ipInfo.get("region"))||"天津市".equals(ipInfo.get("region"))||"上海市".equals(ipInfo.get("region"))) {
					info.put("address", ipInfo.get("country").toString()+ipInfo.get("city")+ipInfo.get("county"));
				}else {
					info.put("address", ipInfo.get("country").toString()+ipInfo.get("region")+ipInfo.get("city")+ipInfo.get("county"));
				}
			}
		}
		if (info!=null) {
			CacheUtils.set(Constant.IP_DB, ip, info, true);
			return info;
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static Map getAddressByll(String lat,String lng) {
		return getAddressByll(lat, lng,true);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getAddressByll(String lat,String lng,boolean BaseStation) {
		JSONObject address=HttpUtil.sendAsciiJsonReq(baiduAddressByll.replace("LL", lat+","+lng));
		if ("0".equals(address.getString("status"))) {
			address=address.getJSONObject("addressComponent");
			Map info=new HashMap<>();
			info.put("source", "map");
			info.put("country", address.get("country"));
			info.put("region", address.get("province"));
			info.put("city", address.get("city"));
			info.put("county", address.get("district"));
			info.put("street", address.get("street"));
			if (!BaseStation) {//基站只查找到街
				info.put("street_number", address.get("street_number"));
			}
			info.put("lat", lat);
			info.put("lng", lng);
			return info;
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(getIpInfo("122.55.125.12"));
	}
}
