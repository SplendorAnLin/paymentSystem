package com.yl.dpay.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.dpay.core.dao.PayeeConfigDao;
import com.yl.dpay.core.entity.Payee;
import com.yl.dpay.core.service.PayeeConfigService;
import com.yl.dpay.hessian.beans.DpayRuntimeException;
import com.yl.dpay.hessian.enums.DpayExceptionEnum;

/**
 * 收款人配置业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
@Service("payeeService")
public class PayeeConfigServiceImpl implements PayeeConfigService{
	
	@Resource
	private PayeeConfigDao payeeConfigDao;
	@Override
	public void create(Payee payee) {
		payeeConfigDao.insert(payee);
	}
	
	@Override
	public Payee findById(Long id) {
		return payeeConfigDao.findById(id);
	}
	
	@Override
	public void update(Payee payee) {
		Payee p=payeeConfigDao.findById(payee.getId());
		if (p!=null) {
			p.setAccountName(payee.getAccountName());
			p.setAccountNo(payee.getAccountNo());
			p.setAccountType(payee.getAccountType());
			p.setBankCode(payee.getBankCode());
			p.setSabkCode(payee.getSabkCode());
			p.setSabkName(payee.getSabkName());
			p.setCnapsCode(payee.getCnapsCode());
			p.setCnapsName(payee.getCnapsName());
			p.setOpenBankName(payee.getOpenBankName());
			p.setIdCardNo(payee.getIdCardNo());
			payeeConfigDao.update(p);
		}else{
			throw new DpayRuntimeException(DpayExceptionEnum.SYSERR.getCode(), DpayExceptionEnum.getMessage(DpayExceptionEnum.SYSERR.getCode()));
		}
	}

	@Override
	public void delete(int id) {
		payeeConfigDao.delete(id);
	}

	@Override
	public void deleteAll(int[] ids) {
		payeeConfigDao.deleteAll(ids);
	}
	
	public PayeeConfigDao getPayeeConfigDao() {
		return payeeConfigDao;
	}

	public void setPayeeConfigDao(PayeeConfigDao payeeConfigDao) {
		this.payeeConfigDao = payeeConfigDao;
	}

	
}
