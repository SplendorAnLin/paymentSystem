/**
 * 
 */
package com.yl.account.core.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.account.core.dao.AccountingVoucherDao;
import com.yl.account.core.service.AccountingVoucherService;
import com.yl.account.core.utils.CodeBuilder;
import com.yl.account.model.AccountingVoucher;

/**
 * 账务记账凭证处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月22日
 * @version V1.0.0
 */
@Service
public class AccountingVoucherServiceImpl implements AccountingVoucherService {

	@Resource
	private AccountingVoucherDao accountingVoucherDao;

	@Override
	public void recordAccountingVoucher(AccountingVoucher accountingVoucher) {
		accountingVoucher.setCode(CodeBuilder.build("AV", "yyyyMMdd"));
		try {
			accountingVoucher.setServerHost(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {}
		accountingVoucherDao.create(accountingVoucher);
	}

}
