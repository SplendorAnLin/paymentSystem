package com.yl.account.core.dubbo.service;

import javax.annotation.Resource;

import org.junit.Test;

import com.yl.account.api.bean.request.AccountBalanceQuery;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.AccountType;
import com.yl.account.api.enums.UserRole;
import com.yl.account.core.BaseTest;

/**
 * 账务查询业务逻辑处理接口单元测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountQueryInterfaceImplTest extends BaseTest{
	
	@Resource AccountQueryInterface accountQueryInterface;
	
	@Test
	public void findAccontByAccountNo(){
		System.out.println(accountQueryInterface.findAccontByAccountNo("1069963996648").toString());
	}
	
	@Test
	public void findAccountBalance(){
		AccountBalanceQuery accountBalanceQuery = new AccountBalanceQuery();
		accountBalanceQuery.setAccountNo("1069963996648");
		accountBalanceQuery.setAccountType(AccountType.CASH);
		accountBalanceQuery.setBussinessCode("QUERY-BALANCE");
		accountBalanceQuery.setOperator("ACCOUNT-CORE");
		accountBalanceQuery.setSystemCode("ACCOUNT");
		accountBalanceQuery.setSystemFlowId(String.valueOf(System.currentTimeMillis()));
		accountBalanceQuery.setUserNo("89757");
		accountBalanceQuery.setUserRole(UserRole.CUSTOMER);
		System.out.println(accountQueryInterface.findAccountBalance(accountBalanceQuery).toString());
	}
	
	@Test
	public void findAccountBy(){
		AccountBalanceQuery accountBalanceQuery = new AccountBalanceQuery();
		accountBalanceQuery.setUserNo("100001");
		accountBalanceQuery.setUserRole(UserRole.CUSTOMER);
		accountBalanceQuery.setAccountType(AccountType.CASH);
		System.out.println(accountQueryInterface.findAccountBalance(accountBalanceQuery).toString());
	}

}
