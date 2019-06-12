package com.lefu.online.gateway;

import java.util.Map;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.online.gateway.bean.PartnerQueryResponse;

/**
 * 合作方查询响应信息测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月8日
 * @version V1.0.0
 */
public class PartnerQueryResponseTest {

	public static void main(String[] args) {
		PartnerQueryResponse partnerQueryResponse = new PartnerQueryResponse();
		partnerQueryResponse.setOrderCode("1111111");
		partnerQueryResponse.setAmount("0.01");
		partnerQueryResponse.setPartner("827363");
		partnerQueryResponse.setPartnerOrderStatus("02");
		partnerQueryResponse.setResponseCode("0000");
		partnerQueryResponse.setResponseMsg("SUCCESS");
		partnerQueryResponse.setSign("1212");
		JsonUtils.toJsonToObject(partnerQueryResponse, Map.class);
	}

}
