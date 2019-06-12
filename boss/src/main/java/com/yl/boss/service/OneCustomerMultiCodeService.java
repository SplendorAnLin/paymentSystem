package com.yl.boss.service;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.boss.bean.BankCustomerBean;
import com.yl.boss.bean.FollowCustomerResponseBean;
import com.yl.boss.bean.MccInfoBean;
import com.yl.boss.bean.OrganizationBean;

public interface OneCustomerMultiCodeService {
	/**
	 * 查询银行商户
	 * @param owner
	 * @return
	 */
	public Map<String,Object> findBankCustomerByPage(Page<BankCustomerBean> page,String org,String category);
	
	/**
	 * 查询MCC
	 * @return
	 */
	public List<MccInfoBean> findMcc();
	
	/**
	 * 查询省
	 * @return
	 */
	public List<OrganizationBean> findProvince();
	
	/**
	 * 查询市
	 * @param parentCode
	 * @return
	 */
	public List<OrganizationBean> findCityByProvince(String parentCode);
	
	/**
	 * 用户关注商家
	 */
	public FollowCustomerResponseBean followBankCustomer(String customerNo,String bankCustomerNo,String posCati);
	
	/**
	 * 用户取消关注商家
	 */
	public FollowCustomerResponseBean cancleFollowBankCustomer(String customerNo,String bankCustomerNo,String posCati);
	

	/**
	 * 查询已关注 商家
	 */
	public BankCustomerBean followed(String customerNo,String posCati);
	
	
}
