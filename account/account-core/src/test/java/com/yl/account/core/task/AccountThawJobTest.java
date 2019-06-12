/**
 * 
 */
package com.yl.account.core.task;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.request.AccountThaw;
import com.yl.account.core.BaseTest;
import com.yl.account.core.C;
import com.yl.account.core.facade.AccountPrefreezeThawFacade;

/**
 * 账户解冻定时单元测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountThawJobTest extends BaseTest {

	@Resource
	private AccountPrefreezeThawFacade accountPrefreezeThawFacade;

	/**
	 * Test method for {@link com.yl.account.core.task.AccountThawJob#execute()}.
	 */
	@Test
	public void testExecute() {
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setOperator(C.APP_NAME);
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode(C.APP_NAME);
		accountBussinessInterfaceBean.setSystemFlowId(String.valueOf(System.currentTimeMillis()));

		AccountThaw accountThaw = new AccountThaw();
		accountThaw.setAccountNo("203631388397");
		accountThaw.setFreezeNo("ff7-37cdc99ddae5");
		accountBussinessInterfaceBean.setTradeVoucher(accountThaw);

		accountBussinessInterfaceBean.setBussinessCode("PREFREEZE_THAW");
		accountPrefreezeThawFacade.thaw(accountBussinessInterfaceBean);
	}
}
