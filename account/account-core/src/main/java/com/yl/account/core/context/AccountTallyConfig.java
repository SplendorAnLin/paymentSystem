/**
 * 
 */
package com.yl.account.core.context;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lefu.commons.utils.lang.StringUtils;
import com.yl.account.core.facade.AccountTallyFacade;
import com.yl.account.enums.CompositeTallyType;
import com.yl.account.enums.FundSymbol;

/**
 * 账务出入账配置项
 * 
 * @author 聚合支付有限公司
 * @since 2016年2月23日
 * @version V1.0.0
 */
@Configuration
public class AccountTallyConfig {
	@Autowired
	private AccountTallyFacade accountCreditFacade;
	@Autowired
	private AccountTallyFacade accountDebitFacade;
	@Autowired
	private AccountTallyFacade accountSpecialCreditFacade;
	@Autowired
	private AccountTallyFacade accountSpecialDebitFacade;

	@Bean
	public Map<String, AccountTallyFacade> accountTallyFacades() {
		Map<String, AccountTallyFacade> accountTallyFacades = new HashMap<String, AccountTallyFacade>();
		accountTallyFacades.put(StringUtils.concatToSB(CompositeTallyType.STANDARD.name(), FundSymbol.PLUS.name()).toString(), accountCreditFacade);
		accountTallyFacades.put(StringUtils.concatToSB(CompositeTallyType.STANDARD.name(), FundSymbol.SUBTRACT.name()).toString(), accountDebitFacade);
		accountTallyFacades.put(StringUtils.concatToSB(CompositeTallyType.SPECIAL.name(), FundSymbol.PLUS.name()).toString(), accountSpecialCreditFacade);
		accountTallyFacades.put(StringUtils.concatToSB(CompositeTallyType.SPECIAL.name(), FundSymbol.SUBTRACT.name()).toString(), accountSpecialDebitFacade);
		return accountTallyFacades;
	}
}
