package com.yl.agent.action;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.pay.common.util.PreventAttackUtil;
import com.yl.agent.Constant;
import com.yl.agent.entity.Authorization;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.web.mvc.ValueListHandlerHelper;

/**
 * 日志查询控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月26日
 * @version V1.0.0
 */
public class LogQueryAction extends Struts2ActionSupport{
	private static final long serialVersionUID = 8930212638609786768L;
	private static final Logger logger = Logger.getLogger(LogQueryAction.class);
	private ValueListHandlerHelper valueListHelper;	
	
	public String loginLogQuery(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		Map params= retrieveParams(getHttpRequest().getParameterMap());		//request 参数转换
		if (auth.getRoleId()==1) {
			params.put("customer_no", auth.getAgentNo());
		}else {
			params.put("username",auth.getUsername());
		}
		ValueListInfo info = new ValueListInfo(params); 
		if(params.get("pagingPage")==null) {
			 info.setPagingPage(1);
		}    
	    ValueList valueList = valueListHelper.getValueList("loginLogQuery", info);  
	    getHttpRequest().setAttribute("loginLogQuery", valueList);
		return SUCCESS;
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
}