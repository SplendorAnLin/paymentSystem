package com.yl.boss.service;

import java.util.List;

import com.yl.boss.dao.DAOException;
import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.Operator;

/**
 * 操作员管理业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface OperatorService {
	

	/**
	 * 登陆密码重置。
	 * @param operatorId 操作员id
	 * @param newPwd 新密码
	 */
   
	public Operator updatePassword(String username, String password, Authorization auth) throws ServiceException;
	
	/**
	 * 修改审核密码
	 * @param username
	 * @param password
	 * @param auth
	 * @return
	 * @throws DAOException
	 */
	public Operator updateAuditPassword(String username, String password, Authorization auth)
			throws DAOException;
	
	/**
	 * 查找指定username的操作员
	 * @param operatorId
	 * @return
	 */
	public Operator findByUsername(String username);
	
	/**
	 * 根据编号查询操作员信息
	 * @param customerNo
	 * @return
	 */
	public Operator findByCustomerNo(String customerNo);
	
	public List<Operator> findAll();

	public Operator update(Operator operator);
	
	public List findBySql(String sql);
	
	public List<Operator> findByHql(String sql);
	
	public void add(Operator operator);
	
}
