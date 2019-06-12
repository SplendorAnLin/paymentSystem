package com.yl.customer.valuelist;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.web.mvc.ValueListHandlerHelper;

/**
 * ValueListRemote控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月24日
 * @version V1.0.0
 */
public class ValueListRemoteAction {

	private ValueListHandlerHelper valueListHelper;
	private String[] queryIds;

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

	// 调用valuelist查询
	@SuppressWarnings("rawtypes")
	private ValueList getValueList(String queryId, Map params) {
		ValueListInfo info = new ValueListInfo(params);

		if (params.get("pagingPage") == null) {
			info.setPagingPage(1);
		}
		ValueList valueList = valueListHelper.getValueList(queryId, info);
		return valueList;
	}

	// 原始请求参数转换
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map retrieveParams(Map requestMap) {
		Map resultMap = new HashMap();
		for (Object key : requestMap.keySet()) {
			Object value = requestMap.get(key);
			if (value != null) {
				if (value.getClass().isArray()) {
					int length = Array.getLength(value);
					if (length == 1) {
						if (resultMap.containsKey(key)) {
							resultMap.put(key, resultMap.get(key));
						} else {
							resultMap.put(key, Array.get(value, 0));
						}
					}
					if (length > 1) {
						resultMap.put(key, value);
					}
				} else {
					if (resultMap.containsKey(key)) {
						resultMap.put(key, resultMap.get(key));
					} else {
						resultMap.put(key, value.toString());
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
