package com.yl.agent.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.mlw.vlh.ValueList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 银行控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月26日
 * @version V1.0.0
 */
public class BankAction extends Struts2ActionSupport {

	private List dataSource = null;
	public void findAllJson(){	
			try {
				ValueList valueList = (ValueList) getHttpRequest().getAttribute("bank");
				dataSource = new ArrayList(valueList.getList());
				JSONArray ja = new JSONArray();
				
				if(null != dataSource){
					JSONObject jo = null;
					for (int i = 0; i < dataSource.size(); i++) {
						jo = new JSONObject();
						Map<String, Object> row = new HashMap<String, Object>((Map<String, Object>) dataSource.get(i));
						String code = row.get("code").toString();
						String shortName = row.get("short_name").toString();
						String fullName = row.get("full_name").toString();
						jo.accumulate("code", code);
						jo.accumulate("shortName", shortName);
						jo.accumulate("fullName", fullName);
						ja.add(jo);
					}
				}
				this.write(ja.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
}
