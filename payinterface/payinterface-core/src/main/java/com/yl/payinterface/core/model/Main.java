package com.yl.payinterface.core.model;

import java.util.Properties;

import com.lefu.commons.utils.lang.JsonUtils;

/**
 * 测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月27日
 * @version V1.0.0
 */
public class Main {

	public static void main(String[] args) {
		Properties prop = new Properties();
		prop.put("mch_id", "101590000512");
		prop.put("key", "46e0f114934e1fedb82e2916fcbafafa");
		prop.put("server_ip", "127.0.0.1");
		prop.put("notify_url", "http://127.0.0.1/");
		prop.put("pay_url", "https://pay.swiftpass.cn/pay/gateway");
		prop.put("service", "pay.weixin.native");
		System.out.println(JsonUtils.toJsonString(prop));
	}

}
