package com.yl.boss.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.bean.BankCustomerBean;
import com.yl.boss.bean.FollowCustomerResponseBean;
import com.yl.boss.bean.MccInfoBean;
import com.yl.boss.bean.OrganizationBean;
import com.yl.boss.service.OneCustomerMultiCodeService;

public class OneCustomerMultiCodeAction extends Struts2ActionSupport{

	private OneCustomerMultiCodeService oneCustomerMultiCodeService;
	private String provinces;
	private String citys;
	private String parentCode;
	private String mccs;
	private String currentPage;
	private String showCount;
	private String org;
	private String mcc;
	private String customers;
	private String bankCustomerNo;
	private String customerNo;
	private String msg;
	private String followed;
	private String posCati;
	public String findProvince(){
		List<OrganizationBean> list = oneCustomerMultiCodeService.findProvince();
		Map<String,List<OrganizationBean>> map = new HashMap<>();
		map.put("provinces", list);
		provinces = JsonUtils.toJsonString(map);
		return SUCCESS;
	}
	
	public String findCityByProvince(){
		List<OrganizationBean> list = oneCustomerMultiCodeService.findCityByProvince(parentCode);
		Map<String,List<OrganizationBean>> map = new HashMap<>();
		map.put("citys", list);
		citys = JsonUtils.toJsonString(map);
		return SUCCESS;
	}
	
	public String findMcc(){
		List<MccInfoBean> list = oneCustomerMultiCodeService.findMcc();
		Map<String,List<MccInfoBean>> map = new HashMap<>();
		map.put("mccs", list);
		mccs = JsonUtils.toJsonString(map);
		return SUCCESS;
	}
	
	public String findBankCustomerByPage(){
		Page page = new Page();
		page.setCurrentPage(Integer.valueOf(currentPage));
		page.setShowCount(Integer.valueOf(showCount));
		Map<String,Object> map = oneCustomerMultiCodeService.findBankCustomerByPage(page, org, mcc);
		customers = JsonUtils.toJsonString(map);
		return SUCCESS;
	}

	public String followBankCustomer(){
		FollowCustomerResponseBean bean = oneCustomerMultiCodeService.followBankCustomer(customerNo, bankCustomerNo,posCati);
		
		msg = JsonUtils.toJsonString(bean);
		return SUCCESS;
	}
	
	public String cancleFollowBankCustomer(){
		FollowCustomerResponseBean bean = oneCustomerMultiCodeService.cancleFollowBankCustomer(customerNo, bankCustomerNo,posCati);
		
		msg = JsonUtils.toJsonString(bean);
		return SUCCESS;
	}
	public String followed(){
		BankCustomerBean bankCustomerBean = oneCustomerMultiCodeService.followed(customerNo, posCati);
		Map<String,Object> map = new HashMap<>();
		map.put("customer", bankCustomerBean);
		followed = JsonUtils.toJsonString(map);
		return SUCCESS;
	}
	
	public OneCustomerMultiCodeService getOneCustomerMultiCodeService() {
		return oneCustomerMultiCodeService;
	}

	public void setOneCustomerMultiCodeService(OneCustomerMultiCodeService oneCustomerMultiCodeService) {
		this.oneCustomerMultiCodeService = oneCustomerMultiCodeService;
	}

	public String getProvinces() {
		return provinces;
	}

	public void setProvinces(String provinces) {
		this.provinces = provinces;
	}

	public String getCitys() {
		return citys;
	}

	public void setCitys(String citys) {
		this.citys = citys;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getMccs() {
		return mccs;
	}

	public void setMccs(String mccs) {
		this.mccs = mccs;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getShowCount() {
		return showCount;
	}

	public void setShowCount(String showCount) {
		this.showCount = showCount;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getCustomers() {
		return customers;
	}

	public void setCustomers(String customers) {
		this.customers = customers;
	}

	public String getBankCustomerNo() {
		return bankCustomerNo;
	}

	public void setBankCustomerNo(String bankCustomerNo) {
		this.bankCustomerNo = bankCustomerNo;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFollowed() {
		return followed;
	}

	public void setFollowed(String followed) {
		this.followed = followed;
	}

	public String getPosCati() {
		return posCati;
	}

	public void setPosCati(String posCati) {
		this.posCati = posCati;
	}
	
}
