package com.yl.client.front.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lefu.commons.cache.util.CacheUtils;
import com.yl.boss.api.bean.AppVersionBean;
import com.yl.boss.api.interfaces.AgentInterface;
import com.yl.client.front.Constant;
import com.yl.client.front.service.VersionService;

@Service("versionService")
public class VersionServiceImpl implements VersionService{
	@Resource
	AgentInterface agentInterface;
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> checkVersion(String type,String oem){
		Map<String, Object> info=null;
		if ("ylzf".equals(oem)) {
			if ("ios".equals(type)) {
				info=CacheUtils.get(Constant.OPERATOR_RESOURCE_CACHE_DB,Constant.IOS_VERSION,Map.class, true);
			}else if ("android".equals(type)) {
				info=CacheUtils.get(Constant.OPERATOR_RESOURCE_CACHE_DB,Constant.ANDROID_VERSION,Map.class, true);
			}
		}else if("ylzfStore".equals(oem)){
			info=new HashMap<>();
			if ("ios".equals(type)) {
				info=CacheUtils.get(Constant.OPERATOR_RESOURCE_CACHE_DB,Constant.IOS_STORE_VERSION,Map.class, true);
			}
		}else {
			AppVersionBean appVersionBean=agentInterface.getAppVersion(type, oem);
			if (appVersionBean!=null) {
				info=new HashMap<>();
				if ("android".equals(type)) {
					info.put("versionCode", appVersionBean.getRemark());
				}
				info.put("version", appVersionBean.getVersion());
				info.put("shortApp", appVersionBean.getShortApp());
				info.put("enforce", appVersionBean.getEnforce().toString());
				info.put("url", appVersionBean.getUrl());
			}
		}
		return info;
	}
}
