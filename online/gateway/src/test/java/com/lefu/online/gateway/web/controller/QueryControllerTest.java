package com.lefu.online.gateway.web.controller;

import java.util.Set;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.junit.Test;

import com.lefu.online.gateway.BaseTest;
import com.yl.online.model.model.PartnerQueryRequest;

/**
 * 查询控制器测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月11日
 * @version V1.0.0
 */
public class QueryControllerTest extends BaseTest {
	@Resource
	private Validator validator;
	@Test
	public void testQuery() {
		PartnerQueryRequest partnerQueryRequest = new PartnerQueryRequest();
		Set<ConstraintViolation<PartnerQueryRequest>> violations = validator.validate(partnerQueryRequest);
		if (violations.size() > 0) {
			ConstraintViolation<PartnerQueryRequest> violation = violations.iterator().next();
			System.out.println("+++++" + violation.getMessage());
			System.out.println(violation.getConstraintDescriptor().getAnnotation().annotationType().equals(NotNull.class));
		}
	}

}
