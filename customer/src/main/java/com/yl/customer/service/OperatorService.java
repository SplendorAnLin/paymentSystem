package com.yl.customer.service;

import java.util.List;
import java.util.Map;

import com.yl.customer.dao.DAOException;
import com.yl.customer.entity.Authorization;
import com.yl.customer.entity.Operator;

/**
 * 操作员管理
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月20日
 * @version V1.0.0
 */
public interface OperatorService {

	/**
	 * 商户密码重置
	 */
	public String resetPassWord(String customer);
	public String resetPassWordByPhone(String customer,String phone);
	/**
	 * 登陆密码重置。
	 * @param operatorId 操作员id
	 * @param newPwd 新密码
	 */
   
	public Operator updatePassword(String username, String password) throws ServiceException;
	
	/**
	 * 审核密码修改
	 * @param operatorId 操作员id
	 * @param newPwd 新密码
	 */
   
	public Operator updateAuditPassword(String username, String password, Authorization auth) throws ServiceException;
	
	/**
	 * 查找指定username的操作员
	 * @param operatorId
	 * @return
	 */
	public Operator findByUsername(String username);
	
	/**
	 * 查询所有操作员
	 * @return
	 */
	public List<Operator> findAll();

	/**
	 * 更新操作员
	 * @param operator
	 * @return
	 */
	public Operator update(Operator operator);
	
	/**
	 * 新增操作员
	 * @param operator
	 */
	public void add(Operator operator);
	
	/**
	 * 根据商编查询操作员
	 * @param customerNo
	 * @return
	 */
	public Operator findByCustNo(String customerNo);
	
	/**
	 * APP密码修改
	 */
	public String updateAppPassword(Map<String, Object> param);
}