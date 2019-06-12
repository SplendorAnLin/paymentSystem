package com.yl.boss.interfaces.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.bean.Shop;
import com.yl.boss.api.interfaces.ShopInterface;
import com.yl.boss.service.ShopService;
import com.yl.boss.valuelist.ValueListRemoteAction;

import net.mlw.vlh.ValueList;

/**
 * 网点信息信息远程接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2017年6月20日
 * @version V1.0.0
 */
public class ShopInterfaceImpl implements ShopInterface {
	
	private ValueListRemoteAction valueListRemoteAction;
	
	@Resource
	private ShopService shopService;

	@Override
	public Page shopQuery(Map<String, Object> params) {
		ValueList vl = valueListRemoteAction.execute("shopInfo", params).get("shopInfo");
		Page page = new Page<>();
		page.setCurrentPage(vl.getValueListInfo().getPagingPage());
		page.setTotalResult(vl.getValueListInfo().getTotalNumberOfEntries());
		page.setObject(vl.getList());
		return page;
	}
	
	@Override
	public List<Shop> queryShopList(String customerNo) {
		return JsonUtils.toObject(JsonUtils.toJsonString(shopService.queryShopList(customerNo)), new TypeReference<List<com.yl.boss.api.bean.Shop>>() {
		});
	}

	public ValueListRemoteAction getValueListRemoteAction() {
		return valueListRemoteAction;
	}

	public void setValueListRemoteAction(ValueListRemoteAction valueListRemoteAction) {
		this.valueListRemoteAction = valueListRemoteAction;
	}

}
