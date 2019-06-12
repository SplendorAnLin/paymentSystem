package com.yl.client.front;

import java.util.Map;

import com.yl.client.front.model.Message;
import com.yl.client.front.service.MessageService;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.lefu.commons.cache.tags.DictionaryUtils;
import com.lefu.commons.cache.util.CacheUtils;
import com.yl.client.front.common.IpUtil;
import com.yl.client.front.context.SpringRootConfig;

import javax.annotation.Resource;


/**
 * 测试类的父类，自动加载spring配置文件且运行于事务之下，测试完毕之后不回滚
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringRootConfig.class })
@ActiveProfiles("development")
@TransactionConfiguration(defaultRollback = false)
public class BaseTest {
	@Resource
	MessageService messageService;
	@Test
	public void testConfig() {
		Message a=new Message();
		JSONObject info=new JSONObject();
		info.put("AA",2);
		a.setContent(info);
		messageService.pushMSG("C100009",a);
		//System.out.println(IpUtil.getAddressByIp("111.172.253.44"));
	}
}
