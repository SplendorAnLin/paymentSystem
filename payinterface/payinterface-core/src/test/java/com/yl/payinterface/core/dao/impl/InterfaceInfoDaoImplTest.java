package com.yl.payinterface.core.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.BaseTest;
import com.yl.payinterface.core.bean.CardTypeInfo;
import com.yl.payinterface.core.dao.InterfaceInfoDao;
import com.yl.payinterface.core.enums.CardType;
import com.yl.payinterface.core.enums.InterfaceType;
import com.yl.payinterface.core.model.InterfaceInfo;
import com.yl.payinterface.core.model.InterfaceProvider;

/**
 * 测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月13日
 * @version V1.0.0
 */
public class InterfaceInfoDaoImplTest extends BaseTest {

	@Resource
	private InterfaceInfoDao interfaceInfoDao;

	@Test
	public void findInterfaceProviderBy() {
		List<InterfaceProvider> interfaceProviders = interfaceInfoDao.findInterfaceProviderBy("B2C", "DEBIT_CARD");
		System.out.println(interfaceProviders.size());
		for (InterfaceProvider interfaceProvider : interfaceProviders) {
			System.out.println(interfaceProvider.getCode() + "," + interfaceProvider.getShortName());
		}
	}

	@Test
	public void create() {
		String[] providers = { "CCB" };
		InterfaceInfo info = new InterfaceInfo();

		CardTypeInfo supportCardTypeInfos = new CardTypeInfo();
		supportCardTypeInfos.setCardType(CardType.DEBIT_CARD);
		supportCardTypeInfos.setSupportProviders(providers);

		CardType[] cardTypeInfos = { CardType.CREDIT_CARD, CardType.DEBIT_CARD };

		info.setName("测试接口");
		info.setCardType(JsonUtils.toJsonString(cardTypeInfos));
		info.setType(InterfaceType.B2C);

		interfaceInfoDao.create(info);
	}

	@Test
	public void findAllEnableByInterfaceType() {
		String interfaceTypes = "'REMIT'";
		List<InterfaceInfo> interfaceInfoList = interfaceInfoDao.queryAllEnableByInterfaceType(interfaceTypes);
		if (interfaceInfoList == null || interfaceInfoList.isEmpty()) {
			System.out.print("Debug: empty");
		} else {
			System.out.print("Debug: not empty");
		}

	}
}
