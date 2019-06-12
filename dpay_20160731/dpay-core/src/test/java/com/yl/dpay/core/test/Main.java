package com.yl.dpay.core.test;

import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.type.TypeReference;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.dpay.core.entity.RouteInfo;
import com.yl.dpay.core.enums.AccountType;

/**
 * 测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月15日
 * @version V1.0.0
 */
public class Main {

	public static void main(String[] args) {
		RouteInfo info = new RouteInfo();
		Set<AccountType> accountTypes = new HashSet<>();
		accountTypes.add(AccountType.INDIVIDUAL);
		accountTypes.add(AccountType.OPEN);
		info.setAccountTypes(null);
		info.setBankCodes(null);
		info.setCardTypes(null);
		info.setCerTypes(null);
		info.setCustomerNoList(null);
		info.setPriority(1);
		info.setRemitCode("QIUJIAN-MINSHEN");
		System.out.println(JsonUtils.toJsonString(info));
		String str = JsonUtils.toJsonString(info);
		RouteInfo routeInfo = JsonUtils.toObject(str, new TypeReference<RouteInfo>(){});
		
		System.out.println(JsonUtils.toJsonString(routeInfo));
	}

}
