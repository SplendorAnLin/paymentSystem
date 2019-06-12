package com.yl.boss.interfaces.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.action.CustomerAction;
import com.yl.boss.api.bean.Ad;
import com.yl.boss.api.interfaces.AdInterface;
import com.yl.boss.entity.Agent;
import com.yl.boss.service.AdService;
import com.yl.boss.service.AgentService;
import com.yl.boss.valuelist.ValueListRemoteAction;

import net.mlw.vlh.ValueList;

/**
 * 广告远程接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月19日
 * @version V1.0.0
 */
public class AdInterfaceImpl implements AdInterface {
	private static Logger log = LoggerFactory.getLogger(AdInterfaceImpl.class);
	@Resource
	AdService adService;
	@Resource
	AgentService agentService;
	
	private ValueListRemoteAction valueListRemoteAction;
	
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(new InputStreamReader(CustomerAction.class.getClassLoader().getResourceAsStream("system.properties")));
		} catch (IOException e) {
			log.error("AdInterfaceImpl load Properties error:", e);
		}
	}
	
	public String findAgentByOem(String oem){
		Agent agent=agentService.findByShortName(oem);
		if (agent!=null&&agent.getAgentLevel()==1) {
			Map<String, String> info=new HashMap<>();
			info.put("oem", agent.getShortName());
			info.put("agentNo", agent.getAgentNo());
			info.put("agentName", agent.getFullName());
			return JsonUtils.toJsonString(info);
		}else {
			return "false";
		}
	}
	/**
	 * 根据代理商编号查询匹配信息
	 * @return
	 */
	public String findAgentByAgentNo(String agentNo){
		Agent agent=agentService.findByNo(agentNo);
		if (agent!=null&&agent.getAgentLevel()==1) {
			Map<String, String> info=new HashMap<>();
			info.put("oem", agent.getShortName());
			info.put("agentNo", agent.getAgentNo());
			info.put("agentName", agent.getFullName());
			return JsonUtils.toJsonString(info);
		}else {
			return "false";
		}
	}
	
	@Override
	public boolean create(Ad ad){
		try {
			com.yl.boss.entity.Ad newAd=JsonUtils.toObject(JsonUtils.toJsonString(ad), new TypeReference<com.yl.boss.entity.Ad>() {});
			adService.save(newAd);
			return true;
		} catch (Exception e) {
			log.error("添加广告异常:{}",e);
			return false;
		}
	}
	
	public Ad fingAdById(int id){
		com.yl.boss.entity.Ad ad=adService.queryById(id,null);
		if (ad!=null) {
			return JsonUtils.toObject(JsonUtils.toJsonString(ad), new TypeReference<Ad>() {});
		}
		return null;
	}
	
	@Override
	public boolean updateAd(Ad ad){
		try {
			com.yl.boss.entity.Ad newAd=JsonUtils.toObject(JsonUtils.toJsonString(ad), new TypeReference<com.yl.boss.entity.Ad>() {});
			adService.update(newAd);
			return true;
		} catch (Exception e) {
			log.error("更新广告异常:{}",e);
			return false;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, Object> query(String oem) {
		Map<String, Object> res=new HashMap<>();
		Map<String, String> param=new HashMap<>();
		param.put("oem", "ylzf");
		ValueList yzlfVl = valueListRemoteAction.execute("adcQuery", param).get("adcQuery");
		List tips=new  ArrayList<>();
		List menu=new  ArrayList<>();
		if(oem.indexOf("Store")>-1){
			oem=oem.replace("Store","");
		}
		if (oem!=null&&oem.indexOf("ylzf")<0){
			param.put("oem", oem);
			ValueList vl = valueListRemoteAction.execute("adcQuery", param).get("adcQuery");
			for (Object adInfo : vl.getList()) {
				Map ad=JsonUtils.toJsonToObject(adInfo, Map.class);
				ad.put("image_url", ImgUrl(ad.get("image_url").toString()));
				if (ad.get("ad_type").equals("MENU")) {
					menu.add(ad);
				}else if (ad.get("ad_type").equals("TIPS")) {
					tips.add(ad);
				}
			}
			if (tips.size()<1) {
				tips=getAd(yzlfVl,"ad_type","TIPS");
			}
			if (menu.size()<1) {
				menu=getAd(yzlfVl,"ad_type","MENU");
			}
		}else{
			menu=getAd(yzlfVl,"ad_type","MENU");
			tips=getAd(yzlfVl,"ad_type","TIPS");
		}
		res.put("tisp", tips);
		res.put("menu", menu);
		return res;
	}

	List getAd(ValueList vl,String column,String type){
		List adList=new  ArrayList<>();
		for (Object adInfo : vl.getList()) {
			Map ad=JsonUtils.toJsonToObject(adInfo, Map.class);
			ad.put("image_url", ImgUrl(ad.get("image_url").toString()));
			if (ad.get(column).equals(type)) {
				adList.add(ad);
			}
		}
		return adList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List findAdByType(String type,String oem){
		Map param=new HashMap<>();
		param.put("ad_type", type);
		param.put("oem", oem);
		ValueList vl = valueListRemoteAction.execute("adcQuery", param).get("adcQuery");
		List resList=new  ArrayList<>();
		for (Object adInfo : vl.getList()) {
			Map ad=JsonUtils.toJsonToObject(adInfo, Map.class);
			ad.put("image_url", ImgUrl(ad.get("image_url").toString()));
			resList.add(ad);
		}
		return resList;
	}
	
	public String ImgUrl(String url){
		if (url.indexOf("http://")==-1) {
			url=prop.getProperty("imgOpen.path")+url;
		}
		return url;
	}

	public ValueListRemoteAction getValueListRemoteAction() {
		return valueListRemoteAction;
	}

	public void setValueListRemoteAction(ValueListRemoteAction valueListRemoteAction) {
		this.valueListRemoteAction = valueListRemoteAction;
	}
}
