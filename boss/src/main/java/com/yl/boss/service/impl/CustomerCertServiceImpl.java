package com.yl.boss.service.impl;

import java.util.List;

import com.yl.boss.dao.CustomerCertDao;
import com.yl.boss.dao.CustomerCertHistoryDao;
import com.yl.boss.entity.CustomerCert;
import com.yl.boss.entity.CustomerCertHistory;
import com.yl.boss.service.CustomerCertService;

/**
 * 商户证件信息服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class CustomerCertServiceImpl implements CustomerCertService {
	
	private CustomerCertDao customerCertDao;
	private CustomerCertHistoryDao customerCertHistoryDao;

	@Override
	public void update(CustomerCert customerCert, String oper) {
		CustomerCert custCert = customerCertDao.findByCustNo(customerCert.getCustomerNo());
		if(custCert != null){
			if (customerCert != null){
				custCert.setValidStartTime(customerCert.getValidStartTime());
				custCert.setIdCard(customerCert.getIdCard());
				custCert.setValidEndTime(customerCert.getValidEndTime());
				custCert.setBusinessScope(customerCert.getBusinessScope());
				custCert.setBusinessAddress(customerCert.getBusinessAddress());
				custCert.setEnterpriseCode(customerCert.getEnterpriseCode());
				custCert.setEnterpriseUrl(customerCert.getEnterpriseUrl());
				custCert.setIcp(customerCert.getIcp());
				custCert.setConsumerPhone(customerCert.getConsumerPhone());
				custCert.setLegalPerson(customerCert.getLegalPerson());
				
				custCert.setAttachment(customerCert.getAttachment()!=null?customerCert.getAttachment():custCert.getAttachment());
				custCert.setBusiLiceCert(customerCert.getBusiLiceCert()!=null?customerCert.getBusiLiceCert():custCert.getBusiLiceCert());
				custCert.setIdCard(customerCert.getIdCard()!=null?customerCert.getIdCard():custCert.getIdCard());
				custCert.setOpenBankAccCert(customerCert.getOpenBankAccCert()!=null?customerCert.getOpenBankAccCert():custCert.getOpenBankAccCert());
				custCert.setOrganizationCert(customerCert.getOrganizationCert()!=null?customerCert.getOrganizationCert():custCert.getOrganizationCert());
				custCert.setTaxRegCert(customerCert.getTaxRegCert()!=null?customerCert.getTaxRegCert():custCert.getTaxRegCert());
				customerCertDao.update(custCert);
				
				CustomerCertHistory customerCertHistorty = new CustomerCertHistory(customerCert, oper);
				customerCertHistoryDao.create(customerCertHistorty);
			}
		}

	}

	@Override
	public CustomerCert findByCustNo(String customerNo) {
		return customerCertDao.findByCustNo(customerNo);
	}

	public CustomerCertDao getCustomerCertDao() {
		return customerCertDao;
	}

	public void setCustomerCertDao(CustomerCertDao customerCertDao) {
		this.customerCertDao = customerCertDao;
	}

	public CustomerCertHistoryDao getCustomerCertHistoryDao() {
		return customerCertHistoryDao;
	}

	public void setCustomerCertHistoryDao(
			CustomerCertHistoryDao customerCertHistoryDao) {
		this.customerCertHistoryDao = customerCertHistoryDao;
	}

	@Override
	public List<CustomerCertHistory> findHistByCustNo(String customerNo) {
		return customerCertHistoryDao.findByCustNo(customerNo);
	}

	@Override
	public String findLegalPersonByNo(String customerNo) {
		return customerCertDao.findLegalPersonByNo(customerNo);
	}

}
