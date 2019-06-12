package com.yl.dpay.core.valuelist;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.web.mvc.ValueListHandlerHelper;

/**
 * ValueList控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月15日
 * @version V1.0.0
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class ValueListAction{
//	private static final String[] SQLInjectionWhitelist=new String[]{};
//	private static final String[] XSSWhitelist=new String[]{};
	private ValueListHandlerHelper valueListHelper;		
	private String[] queryIds;

	public Map<String,ValueList> execute(String queryId,Map requestParam){
		queryIds = queryId.split(",");
		Map<String,ValueList> map=new HashMap<String,ValueList>();
		Map params=retrieveParams(requestParam);
		for(int i=0;i<queryIds.length;i++){
			ValueList valueList = this.getValueList(queryIds[i], params);
			map.put(queryIds[i], valueList);			
		}
		return map;
	}

	//调用valuelist查询
	private ValueList getValueList(String queryId, Map params) {  		
		ValueListInfo info = new ValueListInfo(params); 
		
		if(params.get("pagingPage")==null) {
			 info.setPagingPage(1);
		}    
	    ValueList valueList = valueListHelper.getValueList(queryId, info);  
	    return valueList;  
	}
	
	//原始请求参数转换
	private Map retrieveParams(Map requestMap){
		Map resultMap = new HashMap();
		for(Object key : requestMap.keySet()){
			Object value = requestMap.get(key);
			if(value!=null){
				if(value.getClass().isArray()){
					int length = Array.getLength(value);
					if(length==1){
//						boolean includedInSQLInjectionWhitelist = false;
//						boolean includedInXSSWhitelist = false;
//						if(queryIds != null){
//							for (String _queryId : queryIds){
//								for(String _queryIdWhitelist : SQLInjectionWhitelist){
//									if(_queryId.equals(_queryIdWhitelist) || true){
//										includedInSQLInjectionWhitelist = true;
//										break;
//									}
//								}
//							}
//							for (String _queryId : queryIds){
//								for(String _queryIdWhitelist : XSSWhitelist){
//									if(_queryId.equals(_queryIdWhitelist) || true){
//										includedInXSSWhitelist = true;
//										break;
//									}
//								}
//							}
//						}
//						if(!includedInSQLInjectionWhitelist){
//							resultMap.put(key, PreventAttackUtil.filterSQLInjection(Array.get(value,0).toString()).trim());
//						}
//						if(!includedInXSSWhitelist){
							if(resultMap.containsKey(key)){
								resultMap.put(key, resultMap.get(key));
							}else{
								resultMap.put(key, Array.get(value,0));
							}
//						}
//						if(includedInSQLInjectionWhitelist && includedInXSSWhitelist) {
//								resultMap.put(key, Array.get(value,0).toString().trim());
//						}
					}
					if(length>1){
						resultMap.put(key, value);
					}
				}else{
//					boolean includedInSQLInjectionWhitelist = false;
//					boolean includedInXSSWhitelist = false;
//					if(queryIds != null){
//						for (String _queryId : queryIds){
//							for(String _queryIdWhitelist : SQLInjectionWhitelist){
//								if(_queryId.equals(_queryIdWhitelist)){
//									includedInSQLInjectionWhitelist = true;
//									break;
//								}
//							}
//						}
//						for (String _queryId : queryIds){
//							for(String _queryIdWhitelist : XSSWhitelist){
//								if(_queryId.equals(_queryIdWhitelist)){
//									includedInXSSWhitelist = true;
//									break;
//								}
//							}
//						}
//					}
//					if(!includedInSQLInjectionWhitelist){
//						resultMap.put(key, PreventAttackUtil.filterSQLInjection(value.toString()).trim());
//					}
//					if(!includedInXSSWhitelist){
						if(resultMap.containsKey(key)){
							resultMap.put(key, resultMap.get(key));
						}else{
							resultMap.put(key,  value.toString());
						}
//					}
//					if(includedInSQLInjectionWhitelist && includedInXSSWhitelist) {
//						resultMap.put(key, value.toString().trim());
//					}
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

}
