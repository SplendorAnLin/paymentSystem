package com.yl.agent.valuelist;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.web.mvc.ValueListHandlerHelper;

import com.pay.common.util.PreventAttackUtil;
import com.yl.agent.action.Struts2ActionSupport;
import com.yl.agent.entity.Authorization;

/**
 * ValueList控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class ValueListAction extends Struts2ActionSupport {
	
	private static final long serialVersionUID = -5481651367068975925L;
	public ValueListHandlerHelper valueListHelper;	
	public String queryId;
	public String[] queryIds;
	
	Authorization auth = (Authorization)getSession().getAttribute("auth");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String execute(){
		
		Map requestParam = retrieveParams(getParamsMap());				//request 参数转换
		if(auth != null){
			requestParam.put("ownerId", auth.getAgentNo());
			requestParam.put("customerNo", auth.getAgentNo());
		}
		ValueList valueList = this.getValueList(queryId, requestParam);
		getHttpRequest().setAttribute(queryId, valueList);
		
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, ValueList> execute(String queryId, Map requestParam) {
		queryIds = queryId.split(",");
		Map<String, ValueList> map = new HashMap<String, ValueList>();
		Map params = retrieveParams(requestParam);
		for (int i = 0; i < queryIds.length; i++) {
			ValueList valueList = this.getValueList(queryIds[i], params);
			map.put(queryIds[i], valueList);
		}
		return map;
	}
	
	
 
	
	
	//调用valuelist查询
	@SuppressWarnings("rawtypes")
	public ValueList getValueList(String queryId, Map params) {  
		
		ValueListInfo info = new ValueListInfo(params); 
		
		if(params.get("pagingPage")==null) {
			 info.setPagingPage(1);
		}    
	    ValueList valueList = valueListHelper.getValueList(queryId, info);  
	    return valueList;  
	}
	
	
	//原始请求参数转换
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map retrieveParams(Map requestMap){
		Map resultMap = new HashMap();
		for(Object key : requestMap.keySet()){
			Object value = requestMap.get(key);
			if(value!=null){
				if(value.getClass().isArray()){
					int length = Array.getLength(value);
					if(length==1){
						boolean includedInSQLInjectionWhitelist = false;
						boolean includedInXSSWhitelist = false;
						if(!includedInSQLInjectionWhitelist){
							resultMap.put(key, PreventAttackUtil.filterSQLInjection(Array.get(value,0).toString()).trim());
						}
						if(!includedInXSSWhitelist){
							if(resultMap.containsKey(key)){
								resultMap.put(key, PreventAttackUtil.filterXSS(resultMap.get(key).toString()).trim());
							}else{
								resultMap.put(key, PreventAttackUtil.filterXSS(Array.get(value,0).toString()).trim());
							}
						}
						if(includedInSQLInjectionWhitelist && includedInXSSWhitelist) {
								resultMap.put(key, Array.get(value,0).toString().trim());
						}
					}
					if(length>1){
						resultMap.put(key, value);
					}
				}else{
					boolean includedInSQLInjectionWhitelist = false;
					boolean includedInXSSWhitelist = false;
					if(!includedInSQLInjectionWhitelist){
						resultMap.put(key, PreventAttackUtil.filterSQLInjection(value.toString()).trim());
					}
					if(!includedInXSSWhitelist){
						if(resultMap.containsKey(key)){
							resultMap.put(key, PreventAttackUtil.filterXSS(resultMap.get(key).toString()).trim());
						}else{
							resultMap.put(key, PreventAttackUtil.filterXSS(value.toString()).trim());
						}
					}
					if(includedInSQLInjectionWhitelist && includedInXSSWhitelist) {
						resultMap.put(key, value.toString().trim());
					}
				}
			}
		}
		return resultMap;
	}

	public ValueListHandlerHelper getValueListHelper() {
		return valueListHelper;
	}
	public void setValueListHelper(ValueListHandlerHelper valueListHelper) {
		this.valueListHelper = valueListHelper;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}	
	public String[] getQueryIds() {
		return queryIds;
	}
	public void setQueryIds(String[] queryIds) {
		this.queryIds = queryIds;
	}

}
