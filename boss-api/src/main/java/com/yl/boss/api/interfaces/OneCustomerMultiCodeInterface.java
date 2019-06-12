package com.yl.boss.api.interfaces;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.boss.api.bean.BankCustomerBean;
import com.yl.boss.api.bean.MccInfoBean;
/**
 * 商户信息远程接口
 *
 * @author 聚合支付有限公司
 * @since 2016年9月19日
 * @version V1.0.0
 */
import com.yl.boss.api.bean.OrganizationBean;
public interface OneCustomerMultiCodeInterface {

	/**
	 * 查询银行商户
	 * @param owner
	 * @return
	 */
	public Map<String,Object> findBankCustomerByPage(Page<BankCustomerBean> page,String org,String mcc);
	
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
	public void followBankCustomer(String customerNo,String bankCustomerNo);
}