package com.yl.boss.interfaces.impl;

import java.util.Map;

import com.yl.boss.api.interfaces.MccInterface;
import com.lefu.commons.utils.Page;
import com.yl.boss.valuelist.ValueListRemoteAction;

import net.mlw.vlh.ValueList;

/**
 * Mcc信息远程接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2017年6月20日
 * @version V1.0.0
 */
public class MccInterfaceImpl implements MccInterface {

	private ValueListRemoteAction valueListRemoteAction;
	
	@Override
	public Page mccInfoQuery(Map<String, Object> params) {
		ValueList vl = valueListRemoteAction.execute("mccInfo", params).get("mccInfo");
		Page page = new Page<>();
		page.setCurrentPage(vl.getValueListInfo().getPagingPage());
		page.setTotalResult(vl.getValueListInfo().getTotalNumberOfEntries());
		page.setObject(vl.getList());
		return page;
	}
	
	public ValueListRemoteAction getValueListRemoteAction() {
		return valueListRemoteAction;
	}
	public void setValueListRemoteAction(ValueListRemoteAction valueListRemoteAction) {
		this.valueListRemoteAction = valueListRemoteAction;
	}

}
