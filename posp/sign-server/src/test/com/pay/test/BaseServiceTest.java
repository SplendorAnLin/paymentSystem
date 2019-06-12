package com.pay.test;




public class BaseServiceTest extends AbstractServiceTest{
	@Override
	protected String getConfigName() {
		return "springContext/beanRefFactory.xml";
	}
}
