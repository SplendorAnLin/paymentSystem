package com.yl.boss.dao;

import java.util.List;
import java.util.Map;

import com.yl.boss.entity.Customer;

/**
 * 商户信息数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public interface CustomerDao {
	/**
	 * 新建商户信息
	 * @param customer
	 */
	void create(Customer customer);
	
	/**
	 * 根据商户编号查询
	 * @param customerNo
	 * @return
	 */
	Customer findByNo(String customerNo);
	
	/**
	 * 根据商户全称查询
	 * @param fullName
	 * @return
	 */
	Customer findByFullName(String fullName);
	
	/**
	 * 根据手机号查询
	 * @param phone
	 * @return
	 */
	Customer findByPhone(String phone);
	
	/**
	 * 根据商户简称查询
	 * @param shortName
	 * @return
	 */
	Customer findByShortName(String shortName);
	
	/**
	 * 更新商户信息
	 * @param customer
	 */
	void update(Customer customer);
	
	/**
	 * 根据服务商编号查询
	 * @param AgentNo
	 * @return
	 */
	List<Customer> findByAgentNo(String AgentNo);
	/**
	 * 查询最大的商户号
	 * @return
	 */
	public String getMaxCustomerNo();
	
	/**
	 * 根据商户全称和简称，检查是否有重复
	 * @param fullName
	 * @param shortName
	 * @return
	 */
	public Map<String,String> checkCustomerName(String fullName,String shortName);
	
	/**
	 * 根据商户编号查询简称
	 * @param customerNo
	 * @return
	 */
	public String findShortNameByCustomerNo(String customerNo);
	
	/**
     * 根据商户编号查询所属上级服务商编号
     * @param customerNo
     * @return
     */
    public String queryAgentNoByCustomerNo(String customerNo);
    /**
     *根据商户号查身份证号码
     * @param customerNo
     * @return
     */
    public String findCustomerCardByNo(String customerNo);
    
    /**
     * 根据商户编号判断当前商户是否存在
     * @param customerNo
     * @return
     */
    public boolean customerNoBool(String customerNo);
    
    /**
     * 根据商户编号获取全称
     * @param customerNo
     * @return
     */
    public String fullNameByCustNo(String customerNo);
}
