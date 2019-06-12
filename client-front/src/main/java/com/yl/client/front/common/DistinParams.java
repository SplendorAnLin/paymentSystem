package com.yl.client.front.common;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.yl.client.front.Constant;
import com.yl.client.front.enums.AppExceptionEnum;
import com.yl.client.front.enums.ParamType;
import com.yl.client.front.model.ClientInfo;
import com.yl.client.front.model.ReqAllInfo;

/**
 * 参数识别
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public class DistinParams {

	private static Logger log = LoggerFactory.getLogger(DistinParams.class);
	
	private static final String INPUT_CHARSET = "UTF-8";
	
	private static ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 50, 200L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(200));


	public static Map manageSign(HttpServletRequest request,PrintWriter pw) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		if (StringUtils.isBlank(sb)){
			ResponseMsg.msgNotEncrypt(pw,AppExceptionEnum.PARAMSERR);
		}
		Map info= JsonUtils.toJsonToObject(JSONObject.fromObject(new String(Base64Utils.decode(sb.toString()),"UTF-8")), Map.class);
		if (!md5Check(info.get("oper").toString(),info.get("sys").toString(),info)){
			ResponseMsg.msgNotEncrypt(pw,AppExceptionEnum.AUTHEMPTY);
			return null;
		}else{
			return JsonUtils.toObject(JsonUtils.toJsonString(info),Map.class);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static boolean md5Check(String oper,String oem,Map info) throws UnsupportedEncodingException{
		Map<String, Object> tempSign=new HashMap<>();
		String md5=info.get("md5").toString();
		info.remove("md5");
		tempSign.putAll(info);
		if (tempSign.remove("content")!=null) {
			tempSign.remove("content");
		}
		ArrayList<String> paramNames = new ArrayList<>(tempSign.keySet());
		Collections.sort(paramNames);
		Iterator<String> iterator = paramNames.iterator();
		StringBuffer signSource = new StringBuffer();
		while (iterator.hasNext()) {
			String paramName = iterator.next();
			if (StringUtils.notBlank(String.valueOf(tempSign.get(paramName)))) {
				signSource.append(paramName).append("=").append(tempSign.get(paramName));
				if (iterator.hasNext()) signSource.append("&");
			}
		}
		String md5String=MD5.MD5Encode(oper+oem+signSource.substring(signSource.length()/2,signSource.length()));
		return md5String.equals(md5);
	}

	@SuppressWarnings("unchecked")
	public static ReqAllInfo signValidate(HttpServletRequest request) throws AppRuntimeException {
		ReqAllInfo reqInfo=null;
		Map<String, Object> info=null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			info=JsonUtils.toObject(sb.toString(), Map.class);
			//log.info("原始请求信息:"+info);
			//解密 校验参数
			info=decryptApp(info);
			if (!checkSign(info))
				throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
			log.info("解密请求信息:"+info);
		} catch (Exception e) {
			log.error("客户端请求解密异常：{}", e);
			throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
		}
		if (info!=null) {
			try {
				//记录请求信息
				reqInfo=new ReqAllInfo();
				reqInfo.setInfo(JsonUtils.toJsonString(info));
				reqInfo.setReqAddress(request.getRequestURL().toString());
				reqInfo.setReqIp(getIpAddr(request));
				if (reqInfo.getInfo()!=null&&reqInfo.getInfo().length()>0) {
					String code=info.get("ACTION_NAME").toString();
					reqInfo.setReqInfo(info.get("ACTION_INFO"));
					reqInfo.setClientInfo(JsonUtils.toObject(JsonUtils.toJsonString(info.get("ACTION_MSG")),new TypeReference<ClientInfo>(){}));
					reqInfo.setReqSys(ParamType.getSys(code));
					reqInfo.setReqType(ParamType.getType(code));
					reqInfo.setReqCode(code);
					pool.submit(new SaveIpThread(reqInfo, request));
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			
		}else {
			log.error("客户端请求解析参数失败信息:{}",JsonUtils.toJsonString(reqInfo));
			throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
		}
		return reqInfo;
	}
	
	public static void gatherIp(ReqAllInfo reqInfo,HttpServletRequest request){
		ClientInfo clientInfo=reqInfo.getClientInfo();
		try {
			String ip=IpUtil.getAddressByIp(request);
			if(reqInfo.getClientInfo().getLng().equals("4.9E-324")){
				IpUtil.setIpInfo(ip);
			}else {
				if ("wifi".equals(clientInfo.getNetType())) {
					if (!IpUtil.exist(ip)) {
						IpUtil.setIpInfo(ip);
					}
				}else {
					if (!IpUtil.existType(ip)) {
						Map ipInfo=IpUtil.getAddressByll(clientInfo.getLat(), clientInfo.getLng());
						ipInfo.put("type", "base");
						CacheUtils.set(Constant.IP_DB, ip, ipInfo, true);
					}
				}
			}
		} catch (Exception e) {
			log.error("请求ip记录到IP库异常");
		}
	}
	
	@SuppressWarnings("unchecked")
	public static boolean checkSign(Map<String,Object> info) throws UnsupportedEncodingException{
		String md5=info.get("md5").toString();
		info.remove("md5");
		Map<String,Object> temp=new HashMap<>() ;
		temp.putAll((Map<String, Object>) info.get("ACTION_INFO"));
		temp.putAll((Map<String, Object>) info.get("ACTION_MSG"));
		ArrayList<String> paramNames = new ArrayList<>(temp.keySet());
		Collections.sort(paramNames);
		Iterator<String> iterator = paramNames.iterator();
		StringBuffer signSource = new StringBuffer();
		while (iterator.hasNext()) {
			String paramName = iterator.next();
			if (StringUtils.notBlank(String.valueOf(temp.get(paramName)))) {
				signSource.append(paramName).append("=").append(temp.get(paramName));
				if (iterator.hasNext()) signSource.append("&");
			}
		}
		String calSign = DigestUtils.md5DigestAsHex(signSource.toString().getBytes(INPUT_CHARSET));
		return calSign.equals(md5);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String ,Object> decryptApp(Map<String,Object> info) throws Exception{
		String pwd =AESUtils.outKey(info.get("mark").toString(), info.get("timestamp").toString());
		String data=info.get("data").toString();
		String reqStr=AESUtils.decrypt(data,pwd);
		Map<String, Object> reqInfo=JsonUtils.toObject(reqStr, Map.class);
		return reqInfo;
	}
	
	public static Map<String ,String> encryptApp(String params) throws Exception{
		String time=Long.toString(System.currentTimeMillis());
		String uuid=CodeBuilder.build();
		String resStr=AESUtils.encrypt(params, AESUtils.outKey(time,uuid));
		Map<String ,String> resMap=new HashMap<>();
		resMap.put("timestamp", time);
		resMap.put("mark", uuid);
		resMap.put("data", resStr);
		return resMap;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean authValidate(ReqAllInfo reqInfo){
		String sign=reqInfo.getClientInfo().getMsg();
		Map loginInfo=CacheUtils.get(Constant.OPERATOR_RESOURCE_CACHE_DB,Constant.OPERATOR_RESOUSE+"."+reqInfo.getClientInfo().getPhone(),Map.class, true);
		if (loginInfo!=null&&sign.equals(loginInfo.get("sign"))) {
			return true;
		}else {
			return false;
		}
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		   String ip = request.getHeader("x-forwarded-for");
		   if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			   ip = request.getHeader("Proxy-Client-IP");
		   }
		   if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			   ip = request.getHeader("WL-Proxy-Client-IP");
		   }
		   if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		       ip = request.getRemoteAddr();
		   }
		   return ip;
	}
}

//记录ip到资料库
	class SaveIpThread implements Runnable{
		private ReqAllInfo reqInfo;
		private HttpServletRequest request;
		public SaveIpThread(ReqAllInfo reqInfo,HttpServletRequest request){
			this.reqInfo = reqInfo;
			this.request=request;
		}
		@Override
		public void run() {
			DistinParams.gatherIp(reqInfo, request);
		}
		
	}
