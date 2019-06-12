/**
 *
 */
package com.pay.test;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.pay.pos.dao.impl.helper.EntityDao;


/**
 */
public abstract class AbstractServiceTest extends TestCase {

	protected Log log = LogFactory.getLog(getClass());

	protected ServiceLocator locator;
	protected ApplicationContext context;
	protected EntityDao entityDao;
	
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	abstract protected String getConfigName();
	
	protected AutowireCapableBeanFactory findAutoWiringBeanFactory(ApplicationContext context) {
        if (context instanceof AutowireCapableBeanFactory) {
            // Check the context
            return (AutowireCapableBeanFactory) context;
        } else if (context instanceof ConfigurableApplicationContext) {
            // Try and grab the beanFactory
            return ((ConfigurableApplicationContext) context).getBeanFactory();
        } else if (context.getParent() != null) {
            // And if all else fails, try again with the parent context
            return findAutoWiringBeanFactory(context.getParent());
        }
        return null;
    }

	protected void setUp() throws Exception {
		super.setUp();
		ServiceLocator.BEAN_REFERENCE_LOCATION = getConfigName();
		locator=ServiceLocator.instance();
		context=locator.getContext();
		AutowireCapableBeanFactory factory = findAutoWiringBeanFactory(context);
		factory.autowireBeanProperties(this, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		locator.shutdown();
	}
}
