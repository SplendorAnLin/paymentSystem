package com.yl.customer.dao;

import java.util.List;

import com.yl.customer.entity.Operator;

/**
 * 操作员
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
public interface OperatorDao {
	/**
	 * 创建操作员
	 * @param operator
	 *  @return   Operator对象
	 */
	public Operator create(Operator operator);

	/**
	 * 根据操作员username查询操作员
	 * @param username         
	 * @return Operator对象
	 */

	public Operator findByUsernameAndCustomerno(String custmoerno,String username);
	
	/**
	 * 根据操作员username批量查询操作员
	 * @param username         
	 * @return Operator对象
	 */
	public List<Operator> findByUsernames(String username);
	/**
	 * 根据操作员customerno,username查询操作员
	 * @param customerno,username         
	 * @return Operator对象
	 */

	public Operator findByUsername(String username);
	
	/**
	 * 根据操作员id查询操作员
	 * @param id
	 * @return Operator对象
	 */
	public Operator findById(Long id);
	
	/**
	 * 查找全部
	 */
	public List<Operator> findAll();
	/**
	 *  修改操作员信息
	 *  @param operator   
	 * @return Operator对象
	 */
	public Operator update(Operator operator);
	
	/**
	 * 删除操作员
	 * @param operator
	 */
	public void delete(Operator operator);

	/**
	 * @param sql
	 * @return
	 */
	public List findBySql(String sql);

	/**
	 * @param sql
	 * @param list TODO
	 * @return
	 */
	public List<Operator> findByHql(String sql);

	public Operator findByPhone(String phone,String customerNo);
	public Operator findByCustomerNo(String customerNo);
	
	/**
	 * 关联权限
	 * @param oid
	 * @param rid
	 */
	public void createRole(Long oid, Long rid);
	
	
	/**
	 * 删除权限
	 * @param oid
	 */
	public void deleteRole(Long oid);
}
