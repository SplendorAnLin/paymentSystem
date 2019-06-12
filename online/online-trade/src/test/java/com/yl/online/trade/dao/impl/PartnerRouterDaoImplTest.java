package com.yl.online.trade.dao.impl;

import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.online.trade.BaseTest;
import com.yl.online.trade.dao.PartnerRouterDao;

/**
 * 合作方路由数据实现测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月8日
 * @version V1.0.0
 */
public class PartnerRouterDaoImplTest extends BaseTest {
	
	@Resource
	private PartnerRouterDao partnerRouterDao;
	
	@Test
	public void testCreatePartnerRouter() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryPartnerRouterBy() throws Exception {
		Page page = new Page();
		page.setCurrentPage(1);
		Map<String, Object> requestParameters = new LinkedHashMap<String, Object>();

		String partnerRole = "";
		String partnerCode = "";
		String status = "TRUE";
		if (StringUtils.isNotBlank(partnerRole)) requestParameters.put("partnerRole", partnerRole);
		if (StringUtils.isNotBlank(partnerCode)) requestParameters.put("partnerCode", partnerCode);
		if (StringUtils.isNotBlank(status)) requestParameters.put("status", status);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date createStartTime = dateFormat.parse("2014-3-6 17:30:01");
		Date createEndTime = dateFormat.parse("2014-3-9 17:30:01");
		// 构成左右边界值(路由模板创建时间)
		Map<String, Date> createTimes = new LinkedHashMap<String, Date>();
		if (createStartTime != null) createTimes.put("createStartTime", createStartTime);
		if (createEndTime != null) createTimes.put("createEndTime", createEndTime);
		requestParameters.put("createTime", createTimes);
		
		page.setObject(requestParameters);
		
		System.out.println(partnerRouterDao.queryPartnerRouterByPage(page).getObject());
	}

	@Test
	public void testQueryPartnerRouterByCondition() throws Exception {
		System.out.println(partnerRouterDao.queryPartnerRouterBy("CUSTOMER", "0101001"));
	}
}
