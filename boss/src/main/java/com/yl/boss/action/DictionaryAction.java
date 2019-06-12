package com.yl.boss.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.TypeReference;
import com.lefu.commons.cache.bean.Dictionary;
import com.lefu.commons.cache.bean.DictionaryType;
import com.lefu.commons.cache.tags.DictionaryUtils;
import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.Constant;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 字典控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月28日
 * @version V1.0.0
 */
public class DictionaryAction extends Struts2ActionSupport {
	private static final long serialVersionUID = 926009496450468844L;
	private DictionaryType dictionaryTypeRanged;
	private Dictionary dictionaryRanged;
	private List<DictionaryType> dictionaryTypeRangedList;
	private List<Dictionary> dictionaryRangedList;
	private Page page;
	private String msg;
	private String type;
	
	/**
	 * 根据编号查询字典类型
	 * @return
	 */
	public String queryDictionaryTypeByPay(){
		if (page == null) {
			page = new Page<>();
		}
		Map<String,JSONObject> dic=new HashMap<>();
		Map<String,JSONObject> dict=new HashMap<>();
		dictionaryTypeRangedList=new ArrayList<>();
		if(dictionaryTypeRanged.getCode() != null && !dictionaryTypeRanged.getCode().equals("")){
			for (String key : Constant.DICTS.keySet()) {
				if (key.indexOf(dictionaryTypeRanged.getCode().trim()) >-1) {
					dic.put(key, Constant.DICTS.get(key));
				}
			}
		}else {
			for (String key : Constant.DICTS.keySet()) {
				dic.put(key, Constant.DICTS.get(key));
			}
		}
		if(dictionaryTypeRanged.getName() != null && !dictionaryTypeRanged.getName().equals("")){
			for (String key : dic.keySet()) {
				DictionaryType dictionaryType=JsonUtils.toObject(JsonUtils.toJsonString(dic.get(key)), DictionaryType.class);
				if (dictionaryType.getName().indexOf(dictionaryTypeRanged.getName().trim()) >-1) {
					dict.put(key, dic.get(key));
				}
			}
		}else {
			for (String key : dic.keySet()) {
				dict.put(key, dic.get(key));
			}
		}
		Set<String> keys=dict.keySet();
		page.setTotalResult(keys.size());
		page.getTotalPage();
		int currentPage = getHttpRequest().getParameter("currentPage") == null ? 1 : Integer.parseInt(getHttpRequest().getParameter("currentPage"));
		if (currentPage > 1) {
			page.setCurrentPage(currentPage);
		}
		int count=0;
		for (String key : keys) {
			count++;
			if (count>(page.getCurrentPage()-1)*page.getShowCount()&&count<=page.getCurrentPage()*page.getShowCount()) {
				dictionaryTypeRangedList.add(JsonUtils.toObject(JsonUtils.toJsonString(dict.get(key)), DictionaryType.class));
			}
		}
		page.setObject(dictionaryTypeRangedList);
		return SUCCESS;
	}
	/**
	 * 根据字典类型code单条查询
	 * @return
	 */
	public String queryDictionaryTypeByCode(){
		if(dictionaryTypeRanged.getCode() != null && !dictionaryTypeRanged.getCode().equals("")){
			dictionaryTypeRanged=JsonUtils.toJsonToObject(Constant.DICTS.get(dictionaryTypeRanged.getCode().replace("DICTIONARY_TYPE.", "")),DictionaryType.class);
			dictionaryTypeRanged.setCode(dictionaryTypeRanged.getCode().replace("DICTIONARY_TYPE.", ""));
		}
		return SUCCESS;
	}
	
	/**
	 * 字典类型增加和修改操作
	 */
	public String dictionaryTypeSaveOrUpdate() {
		JSONObject dicts=Constant.DICTS.get(dictionaryTypeRanged.getCode());
		JSONArray dictionaries;
		if (dicts!=null) {//存在字典就是修改
			if (dicts.containsKey("dictionaries")) {
				dictionaries=dicts.getJSONArray("dictionaries");
				dictionaryTypeRanged.setDictionaries(dictionaries);
			}
		}
		DictionaryType dictionaryTypeNow=new DictionaryType();
		dictionaryTypeNow.setCode(Constant.DICTIONARY_TYPE + dictionaryTypeRanged.getCode());
		dictionaryTypeNow.setDictionaries(dictionaryTypeRanged.getDictionaries());
		dictionaryTypeNow.setName(dictionaryTypeRanged.getName());
		dictionaryTypeNow.setRemark(dictionaryTypeRanged.getRemark());
		Constant.DICTS.put(dictionaryTypeRanged.getCode(),JSONObject.fromObject(dictionaryTypeNow));
		DictionaryUtils.setDictionaryType(dictionaryTypeNow);
		return SUCCESS;
	}
	
	/**
	 * 更新字典类型
	 * @param dictionaryType
	 */
	public void upDictionaryType(DictionaryType dictionaryType) {
		dictionaryTypeRanged=JsonUtils.toJsonToObject(Constant.DICTS.get(dictionaryType.getCode().replace("DICTIONARY_TYPE.", "")),DictionaryType.class);
		DictionaryType dictionaryTypeNow=new DictionaryType();
		dictionaryTypeNow.setCode(dictionaryTypeRanged.getCode());
		dictionaryTypeNow.setDictionaries(dictionaryType.getDictionaries());
		dictionaryTypeNow.setName(dictionaryTypeRanged.getName());
		dictionaryTypeNow.setRemark(dictionaryTypeRanged.getRemark());
		Constant.DICTS.put(dictionaryTypeRanged.getCode().replace(Constant.DICTIONARY_TYPE, ""),JSONObject.fromObject(dictionaryTypeNow));
		DictionaryUtils.setDictionaryType(dictionaryTypeNow);
	}
	
	/**
	 * 字典类型删除操作
	 * @return
	 */
	public String dictionaryTypeDelete(){
		try {
			DictionaryUtils.delDictionaryTypeBy(dictionaryTypeRanged.getCode());
			Constant.DICTS.remove(dictionaryTypeRanged.getCode().replace("DICTIONARY_TYPE.", ""));
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return SUCCESS;
	}
	/**
	 * 子字典操作
	 * @return
	 */
	public String dictionaryRangedByKeyCrud() {
		JSONObject dicts=Constant.DICTS.get(dictionaryTypeRanged.getCode().replace(Constant.DICTIONARY_TYPE, ""));
		if (dicts!=null&&dicts.containsKey("dictionaries")) {
			JSONArray dictionaries=dicts.getJSONArray("dictionaries");
			String dictionaryType = getHttpRequest().getParameter("dictionaryType");
			String dictKey=dictionaryRanged.getKey();
			if(dictionaryType.equals("queryDictionary")){//查询
				for (Object object : dictionaries) {
					dictionaryRanged=JsonUtils.toObject(JsonUtils.toJsonString(object), Dictionary.class);
					if (dictionaryRanged.getKey().equals(dictKey)) {
						getHttpRequest().setAttribute("dictionaryTypeCode", dictionaryTypeRanged.getCode());
						break;
					}
				}
			}else if(dictionaryType.equals("updateDictionary")){//修改
				update(false, dictionaries, dictKey);
			}else if(dictionaryType.equals("delDictionary")){//删除
				update(true, dictionaries, dictKey);
			}else if(dictionaryType.equals("addDictionary")){
				dictionaryRanged.setKey(Constant.DICTIONARY+dictionaryRanged.getKey());
				dictionaries.add(dictionaryRanged);
				dictionaryTypeRanged.setDictionaries(dictionaries);
				upDictionaryType(dictionaryTypeRanged);
			}
		}
		return SUCCESS;
	}
	/**
	 * 更新子字典
	 * @param isDel
	 * @param dictionaries
	 * @param dictKey
	 */
	public void update(Boolean isDel,JSONArray dictionaries,String dictKey) {
		for (Object object : dictionaries) {
			Dictionary dictionaryTemp=JsonUtils.toObject(JsonUtils.toJsonString(object), Dictionary.class);
			if (dictionaryTemp.getKey().equals(dictKey)) {
				dictionaries.remove(object);
				if (!isDel) {
					dictionaries.add(dictionaryRanged);
				}
				break;
			}
		}
		dictionaryTypeRanged.setDictionaries(dictionaries);
		upDictionaryType(dictionaryTypeRanged);
	}
	
	
	/**
	 * 字典和字典类型增加和修改操作
	 */
//	public String SaveOrUpdate(){
//		try {
//			//判断当前dictionaryRanged是否为空，若不为空则说明用户进行增加或者修改字典操作，反之为新增或者修改字典类型操作
//			if(dictionaryRanged != null){
//				//截取字符串判断当前是新增还是修改，若为新增则为KEY属性值添加前缀，反之则不需要
//				if(!Constant.DICTIONARY.equals(dictionaryRanged.getKey().split("\\.")[0]+".")){
//					//为key加标识
//					dictionaryRanged.setKey(Constant.DICTIONARY + dictionaryRanged.getKey());
//				}
//				//判断当前dictionaryTypeRanged是否为空，若为空则说明当前用户未选中任何字典类型,在此将不进行任何操作
//				if(dictionaryTypeRanged != null){
//					//根据用户在页面输入的字典类型编号进行字典类型查询，获取对应的字典类型对象
//					dictionaryTypeRanged = DictionaryUtils.getDictionaryTypeBy(Constant.DICTIONARY_TYPE + dictionaryTypeRanged.getCode());
//					
//					//若当前查询到的结果不为空则继续
//					if(dictionaryTypeRanged != null){
//						//从获取到的字典类型对象中取出字典集合属性
//						dictionaryRangedList = dictionaryTypeRanged.getDictionaries();
//						
//						//判断当前获取到的字典集合属性是否为空，若不为空则累加，反之重新创建一个
//						if(dictionaryRangedList != null){
//							//将字典对象累加插入进原字典类型字典集合属性中
//							dictionaryRangedList.add(dictionaryRanged);
//						}else{
//							dictionaryRangedList = new ArrayList<com.lefu.commons.cache.bean.Dictionary>();
//							dictionaryRangedList.add(dictionaryRanged);
//						}
//						//将新的字典集合属性赋值给字典类型对象
//						dictionaryTypeRanged.setDictionaries(dictionaryRangedList);
//					}
//				}
//			}
//			
//			//调用字典类型新增OR修改方法
//			dictionaryTypeSaveOrUpdate(dictionaryTypeRanged);
//			
//		} catch (Exception e) {
//			
//		}
//		return SUCCESS;
//	}
	
	/**
	 * 根据key单条字典的改查删操作
	 * @return
	 */
//	public String dictionaryRangedByKeyCrud(){
//		//判断当前是否有传入字典类型编号
//		if(dictionaryTypeRanged != null){
//			//根据用户在页面输入的字典类型编号进行字典类型查询，获取对应的字典类型对象
//			dictionaryTypeRanged = DictionaryUtils.getDictionaryTypeBy(dictionaryTypeRanged.getCode());
//			//若当前查询到的结果不为空则继续
//			if(dictionaryTypeRanged != null){
//				//从获取到的字典类型对象中取出字典集合属性
//				dictionaryRangedList = dictionaryTypeRanged.getDictionaries();
//				//循环判断，取出查询的单条数据
//				for (int i = 0; i < dictionaryRangedList.size(); i++) {
//					if(dictionaryRangedList.get(i).getKey().equals(dictionaryRanged.getKey())){
//						//type:
//						//查询单条信息：queryDictionary
//						//修改单条信息：updateDictionary
//						//删除单条信息：delDictionary
//						String dictionaryType = getHttpRequest().getParameter("dictionaryType");
//						
//						if(dictionaryType.equals("queryDictionary")){
//							
//							dictionaryRanged = dictionaryRangedList.get(i);
//							
//							getHttpRequest().setAttribute("dictionaryTypeCode", dictionaryTypeRanged.getCode());
//							
//						}else if(dictionaryType.equals("updateDictionary")){
//							
//							dictionaryRangedList.set(i, dictionaryRanged);
//							
//							dictionaryTypeRanged.setDictionaries(dictionaryRangedList);
//							//调用字典类型新增OR修改方法
//							dictionaryTypeSaveOrUpdate(dictionaryTypeRanged);
//							
//						}else if(dictionaryType.equals("delDictionary")){
//							dictionaryRangedList.remove(i);
//							
//							if(dictionaryRangedList.size() == 0){
//								dictionaryRangedList = null;
//							}
//							
//							dictionaryTypeRanged.setDictionaries(dictionaryRangedList);
//							
//							
//							//调用字典类型新增OR修改方法
//							dictionaryTypeSaveOrUpdate(dictionaryTypeRanged);
//						}
//					}
//				}
//			}
//		}
//		return SUCCESS;
//	}
	
	
	/**
	 * 根据字典类型编号Ajax单条查询
	 * @return
	 */
	public String queryDictionaryTypeByCodeAjax(){
		
		if(!dictionaryTypeRanged.getCode().equals("")){
			
			//根据用户在页面输入的字典类型编号进行字典类型查询，获取对应的字典类型对象
			dictionaryTypeRanged = DictionaryUtils.getDictionaryTypeBy(Constant.DICTIONARY_TYPE + dictionaryTypeRanged.getCode());
			
			if(dictionaryTypeRanged != null && !dictionaryTypeRanged.equals("")){
				msg = "true";
			}else {
				msg = "false";
			}
		}else {
			msg = "false";
		}
		
		return SUCCESS;
	}
	
	/**
	 * 公共查询指定类型的字典
	 * @return
	 */
	public String findDict(){
		try {
			dictionaryRangedList=DictionaryUtils.getDictionaryTypeBy(Constant.DICTIONARY_TYPE+type).getDictionaries();
			if (dictionaryRangedList.size()>0) {
				msg=JsonUtils.toJsonString(dictionaryRangedList).replace(Constant.DICTIONARY, "");
			}
		} catch (Exception e) {
			msg="{\"date\"=\"false\",\"msg\"=\"没有数据\"}";
		}
		return SUCCESS;
	}
	
	
	/**
	 * 根据字典类型编号查询旗下所有字典数据
	 * @return
	 */
	public String queryDictionaryByPay(){
		JSONObject dicts=Constant.DICTS.get(dictionaryTypeRanged.getCode());
		if (dicts!=null) {
			dictionaryTypeRanged=JsonUtils.toObject(JsonUtils.toJsonString(dicts), DictionaryType.class);
			if (dicts.containsKey("dictionaries")) {
				dictionaryRangedList=new ArrayList<>();
				for (Object temp : dicts.getJSONArray("dictionaries")) {
					Dictionary temp1=JsonUtils.toObject(JsonUtils.toJsonString(temp), Dictionary.class);
					dictionaryRangedList.add(temp1);
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 根据字典类型编号Ajax查询旗下所有字典
	 * @return
	 */
	public String ajaxQueryDictionaryByTypeCode(){
		try {
			dictionaryTypeRanged = DictionaryUtils.getDictionaryTypeBy(Constant.DICTIONARY_TYPE + dictionaryTypeRanged.getCode());
			dictionaryRangedList = dictionaryTypeRanged.getDictionaries();
			msg = JsonUtils.toJsonString(dictionaryRangedList);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return SUCCESS;
	}
	
	
	
	/**
	 * 字典删除操作
	 * @return
	 */
//	public String dictionaryDelete(){
//		try {
//			dictionaryTypeRanged = DictionaryUtils.getDictionaryTypeBy(dictionaryTypeRanged.getCode());
//			dictionaryRangedList = dictionaryTypeRanged.getDictionaries();
//			dictionaryRangedList.remove(Integer.parseInt(getHttpRequest().getParameter("status")));
//			dictionaryTypeRanged.setDictionaries(dictionaryRangedList);
//			//调用字典类型新增OR修改方法
//			dictionaryTypeSaveOrUpdate(dictionaryTypeRanged);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return SUCCESS;
//	}
	
	

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public com.lefu.commons.cache.bean.DictionaryType getDictionaryTypeRanged() {
		return dictionaryTypeRanged;
	}

	public void setDictionaryTypeRanged(com.lefu.commons.cache.bean.DictionaryType dictionaryTypeRanged) {
		this.dictionaryTypeRanged = dictionaryTypeRanged;
	}

	public com.lefu.commons.cache.bean.Dictionary getDictionaryRanged() {
		return dictionaryRanged;
	}

	public void setDictionaryRanged(com.lefu.commons.cache.bean.Dictionary dictionaryRanged) {
		this.dictionaryRanged = dictionaryRanged;
	}

	public List<com.lefu.commons.cache.bean.Dictionary> getDictionaryRangedList() {
		return dictionaryRangedList;
	}

	public void setDictionaryRangedList(List<com.lefu.commons.cache.bean.Dictionary> dictionaryRangedList) {
		this.dictionaryRangedList = dictionaryRangedList;
	}

	public List<com.lefu.commons.cache.bean.DictionaryType> getDictionaryTypeRangedList() {
		return dictionaryTypeRangedList;
	}

	public void setDictionaryTypeRangedList(List<com.lefu.commons.cache.bean.DictionaryType> dictionaryTypeRangedList) {
		this.dictionaryTypeRangedList = dictionaryTypeRangedList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
