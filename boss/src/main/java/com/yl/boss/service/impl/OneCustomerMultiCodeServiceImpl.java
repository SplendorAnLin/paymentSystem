package com.yl.boss.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.Page;
import com.yl.boss.bean.BankCustomerBean;
import com.yl.boss.bean.FollowCustomerResponseBean;
import com.yl.boss.bean.MccInfoBean;
import com.yl.boss.bean.OrganizationBean;
import com.yl.boss.bean.ProvinceAndCityBean;
import com.yl.boss.constant.CategoryConstant;
import com.yl.boss.entity.BankCustomer;
import com.yl.boss.entity.Customer;
import com.yl.boss.entity.MccInfo;
import com.yl.boss.entity.Organization;
import com.yl.boss.entity.Pos;
import com.yl.boss.service.BankCustomerService;
import com.yl.boss.service.CustomerService;
import com.yl.boss.service.MccService;
import com.yl.boss.service.OneCustomerMultiCodeService;
import com.yl.boss.service.OrganizationService;
import com.yl.boss.service.PosService;
import com.yl.boss.utils.ProvinceAndCityUtil;

public class OneCustomerMultiCodeServiceImpl implements OneCustomerMultiCodeService{

	@Resource
	private BankCustomerService bankCustomerService;
	@Resource
	private CustomerService customerService;
	@Resource
	private MccService mccService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private PosService posService;
	@Override
	public Map<String, Object> findBankCustomerByPage(Page page,String org,String category) {

		Integer count = bankCustomerService.findCountByOrg(org, category);
		page.setTotalResult(count);
		List<BankCustomer> list = bankCustomerService.findByOrg(page, org, category);
		Map<String, Object> map = new HashMap<>();
		List<BankCustomerBean> bankCustomerBeans = null;
		if(null != list && !list.isEmpty()){
			bankCustomerBeans = new ArrayList<>();
			BankCustomerBean bankCustomerBean = null;
			for(BankCustomer bankCustomer : list){
				bankCustomerBean = new BankCustomerBean();
				BeanUtils.copyProperties(bankCustomer, bankCustomerBean);
				
				ProvinceAndCityBean provinceAndCityBean = ProvinceAndCityUtil.map.get(bankCustomer.getOrganization());
				if (null != provinceAndCityBean){
					bankCustomerBean.setCity(provinceAndCityBean.getCity().getName());
					bankCustomerBean.setProvince(provinceAndCityBean.getProvince().getName());
				}
				bankCustomerBean.setCategory(CategoryConstant.gategoryMap.get(bankCustomer.getMccCategory()));
				bankCustomerBeans.add(bankCustomerBean);
			}
		}
		page.setTotalResult(count);
		map.put("customers", bankCustomerBeans);
		map.put("showCount", page.getShowCount());
		map.put("currentPage", page.getCurrentPage());
		map.put("count", count);
		map.put("totalPage", page.getTotalPage());
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
		List<Organization> list = organizationService.findByParentCode(parentCode);
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
	public FollowCustomerResponseBean followBankCustomer(String customerNo, String bankCustomerNo,String posCati) {
		FollowCustomerResponseBean bean = new FollowCustomerResponseBean();
		//验证商户信息
		Customer customer = customerService.findByCustNo(customerNo);
		if(null == customer){
			bean.setResult(FollowCustomerResponseBean.FAILED);
			bean.setMsg("商户不存在");
			return bean;
		}
		Pos pos = posService.findPosByPosCati(posCati);
		if(null == pos){
			bean.setResult(FollowCustomerResponseBean.FAILED);
			bean.setMsg("终端不存在");
			return bean;
		}
		BankCustomer bankCustomer = bankCustomerService.findByCustomerNo(bankCustomerNo);
		if(null == bankCustomer){
			bean.setResult(FollowCustomerResponseBean.FAILED);
			bean.setMsg("关注的商家不存在");
			return bean;
		}
		CacheUtils.setEx("ONE_CUSTOMER_MULTI_CODE_"+customerNo+"_"+posCati, bankCustomerNo,60*60*12);
		bean.setResult(FollowCustomerResponseBean.SUCCESS);
		return bean;
		
	}
	
	@Override
	public FollowCustomerResponseBean cancleFollowBankCustomer(String customerNo, String bankCustomerNo,String posCati) {
		FollowCustomerResponseBean bean = new FollowCustomerResponseBean();
		//验证商户信息
		Customer customer = customerService.findByCustNo(customerNo);
		if(null == customer){
			bean.setResult(FollowCustomerResponseBean.FAILED);
			bean.setMsg("商户不存在");
			return bean;
		}
		BankCustomer bankCustomer = bankCustomerService.findByCustomerNo(bankCustomerNo);
		if(null == bankCustomer){
			bean.setResult(FollowCustomerResponseBean.FAILED);
			bean.setMsg("关注的商家不存在");
			return bean;
		}
		
		Pos pos = posService.findPosByPosCati(posCati);
		if(null == pos){
			bean.setResult(FollowCustomerResponseBean.FAILED);
			bean.setMsg("终端不存在");
			return bean;
		}
		
		String no = CacheUtils.get("ONE_CUSTOMER_MULTI_CODE_"+customerNo+"_"+posCati,String.class);
		if(null == no || !no.equals(bankCustomerNo)){
			bean.setResult(FollowCustomerResponseBean.FAILED);
			bean.setMsg("没有关注该商家，无法取消");
			return bean;
		}
		CacheUtils.del("ONE_CUSTOMER_MULTI_CODE_"+customerNo+"_"+posCati);
		bean.setResult(FollowCustomerResponseBean.SUCCESS);
		return bean;
		
	}
	@Override
	public BankCustomerBean followed(String customerNo, String posCati) {
		String bcn = CacheUtils.get("ONE_CUSTOMER_MULTI_CODE_"+customerNo+"_"+posCati,String.class);
		BankCustomerBean bankCustomerBean = null;
		if(null != bcn){			
			BankCustomer bankCustomer = bankCustomerService.findByCustomerNo(bcn);
			if(null != bankCustomer){
				bankCustomerBean = new BankCustomerBean();
				bankCustomerBean.setBankCustomerName(bankCustomer.getBankCustomerName());
				bankCustomerBean.setBankCustomerNo(bcn);
				bankCustomerBean.setOrganization(bankCustomer.getOrganization());
			}
		}
		return bankCustomerBean;
	}
	
	
}
