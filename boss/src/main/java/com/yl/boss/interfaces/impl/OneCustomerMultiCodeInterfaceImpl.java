package com.yl.boss.interfaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.Page;
import com.yl.boss.api.bean.BankCustomerBean;
import com.yl.boss.api.bean.MccInfoBean;
import com.yl.boss.api.bean.OrganizationBean;
import com.yl.boss.api.interfaces.OneCustomerMultiCodeInterface;
import com.yl.boss.entity.BankCustomer;
import com.yl.boss.entity.MccInfo;
import com.yl.boss.entity.Organization;
import com.yl.boss.service.BankCustomerService;
import com.yl.boss.service.MccService;
import com.yl.boss.service.OrganizationService;

public class OneCustomerMultiCodeInterfaceImpl implements OneCustomerMultiCodeInterface{

	@Resource
	private BankCustomerService bankCustomerService;
	@Resource
	private MccService mccService;
	@Resource
	private OrganizationService organizationService;
	@Override
	public Map<String, Object> findBankCustomerByPage(Page page,String org,String mcc) {

		List<BankCustomer> list = bankCustomerService.findByOrg(page, org, mcc);
		Map<String, Object> map = new HashMap<>();
		List<BankCustomerBean> bankCustomerBeans = null;
		if(null != list && !list.isEmpty()){
			bankCustomerBeans = new ArrayList<>();
			BankCustomerBean bankCustomerBean = null;
			for(BankCustomer bankCustomer : list){
				bankCustomerBean = new BankCustomerBean();
				BeanUtils.copyProperties(bankCustomer, bankCustomerBean);
				bankCustomerBeans.add(bankCustomerBean);
			}
		}
		map.put("customers", bankCustomerBeans);
		map.put("showCount", page.getShowCount());
		map.put("currentPage", page.getCurrentPage());
		return map;
	}
	
	@Override
	public List<MccInfoBean> findMcc() {
		List<MccInfo> list = mccService.findAll();
		List<MccInfoBean> mccInfoBeans = null;
		if(null != list && !list.isEmpty()){
			mccInfoBeans = new ArrayList<>();
			MccInfoBean mccInfoBean = null;
			for(MccInfo mcc : list){
				mccInfoBean = new MccInfoBean(); 
				BeanUtils.copyProperties(mcc, mccInfoBean);
				mccInfoBeans.add(mccInfoBean);
			}
		}
		return mccInfoBeans;
	}

	@Override
	public List<OrganizationBean> findProvince() {
		List<Organization> list = organizationService.findAllProvince();
		List<OrganizationBean> organizationBeans = null;
		if(null != list && !list.isEmpty()){
			OrganizationBean organizationBean = null;
			organizationBeans = new ArrayList<>();
			for(Organization organization : list){
				organizationBean = new OrganizationBean();
				BeanUtils.copyProperties(organization, organizationBean);
				organizationBeans.add(organizationBean);
			}
			
		}
		return organizationBeans;
	}

	@Override
	public List<OrganizationBean> findCityByProvince(String parentCode) {
		List<Organization> list = organizationService.findCityByCode(parentCode);
		List<OrganizationBean> organizationBeans = null;
		if(null != list && !list.isEmpty()){
			OrganizationBean organizationBean = null;
			organizationBeans = new ArrayList<>();
			for(Organization organization : list){
				organizationBean = new OrganizationBean();
				BeanUtils.copyProperties(organization, organizationBean);
				organizationBeans.add(organizationBean);
			}
			
		}
		return organizationBeans;
	}

	@Override
	public void followBankCustomer(String customerNo, String bankCustomerNo) {
		CacheUtils.setEx("ONE_CUSTOMER_MULTI_CODE_"+customerNo, bankCustomerNo,60*60*12);
	}

}
