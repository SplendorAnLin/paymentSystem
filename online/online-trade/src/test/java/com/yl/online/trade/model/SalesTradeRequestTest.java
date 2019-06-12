package com.yl.online.trade.model;

import java.util.Set;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yl.online.model.bean.SalesTradeRequest;
import com.yl.online.trade.BaseTest;

/**
 * 普通消费交易请求测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月8日
 * @version V1.0.0
 */
public class SalesTradeRequestTest extends BaseTest {
	private static final Logger logger = LoggerFactory.getLogger(SalesTradeRequestTest.class);
	@Resource
	private Validator validator;
	@Test
	public void testValidation(){
		SalesTradeRequest reqeust = new SalesTradeRequest();
		reqeust.setTimeout("1111H");
		Set<ConstraintViolation<SalesTradeRequest>> results = validator.validate(reqeust);
		for(ConstraintViolation result : results){
			logger.info(result.getMessage());
		}
	}
}
