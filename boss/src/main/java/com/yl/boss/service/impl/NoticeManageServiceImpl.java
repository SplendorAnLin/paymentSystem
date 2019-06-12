package com.yl.boss.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.boss.bean.NoticeConfigBean;
import com.yl.boss.service.NoticeManageService;
import com.yl.boss.utils.Base64Utils;
import com.yl.boss.utils.HttpUtil;
import com.yl.boss.utils.MD5;

import net.sf.json.JSONObject;

public class NoticeManageServiceImpl implements NoticeManageService {
	
	private final String url = "http://localhost:8080/client-front/";

	@Override
	public String addNoticeConfig(NoticeConfigBean noticeConfigBean) {
		
		return null;
	}

	@Override
	public String upNoticeConfig(NoticeConfigBean noticeConfigBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoticeConfigBean findNoticeConfig(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoticeConfigBean findNoticeConfigByOem(String oem) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unused")
	@Override
	public Map<String, Object> findAllNoticeConfigByInfo(Map<String, Object> params) {
		if(!params.get("oper").equals("") && !params.get("sys").equals("")){
			params.put("md5",md5String(params));
			String res = null;
			try {
				res = HttpUtil.sendReq(url + "findAllNoticeConfigByInfo",Base64Utils.encode(JsonUtils.toJsonString(params).getBytes("UTF-8")),"POST");
				
				if(res != null && !res.equals("")){
					JSONObject  jasonObject = JSONObject.fromObject(res);
					params = (Map)jasonObject;
					
					return (Map<String, Object>) params.get("responseData");
				}
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String sendAllNotice(Map<String, Object> params) {
		if(!params.equals("") && params != null){
			params.put("md5",md5String(params));
			String res = null;
			try {
				res = HttpUtil.sendReq(url + "sendAllNotice",Base64Utils.encode(JsonUtils.toJsonString(params).getBytes("UTF-8")),"POST");
				
				if(res != null && !res.equals("")){
					JSONObject  jasonObject = JSONObject.fromObject(res);
					params = (Map)jasonObject;
					
					return (String) params.get("responseData");
				}
				return res;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String md5String(Map<String, Object> info){
		Map<String, Object> tempSign=new HashMap<>();
		tempSign.putAll(info);
		if (tempSign.get("content")!=null) {
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
		
		String md5String=MD5.MD5Encode(info.get("oper").toString()+info.get("sys").toString()+signSource.substring(signSource.length()/2,signSource.length()));
		return md5String;
	}
	
}
