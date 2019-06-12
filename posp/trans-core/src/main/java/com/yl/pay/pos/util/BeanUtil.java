package com.yl.pay.pos.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class BeanUtil implements BeanFactoryAware {

	private static BeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		BeanUtil.beanFactory = beanFactory;
	}

	public static Object getBean(String beanName) {
		return beanFactory.getBean(beanName);
	}

}
