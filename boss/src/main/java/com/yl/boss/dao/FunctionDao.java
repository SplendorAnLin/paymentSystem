package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.Function;

/**
 * 功能 操作接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public interface FunctionDao {
	
	/**
	 * 创建功能
	 * @param function
	 * @return
	 * @throws DAOException
	 */
	public Function create(Function function) throws DAOException;
	
	/**
	 * 删除功能
	 * @param function
	 * @throws DAOException
	 */
	public void delete(Function function) throws DAOException;
	
	/**
	 * 更新功能
	 * @param function
	 * @throws DAOException
	 */
	public Function update(Function function) throws DAOException;
	
	/**
	 * 根据ID查找功能
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public Function findById(Long id) throws DAOException;
	
	/**
	 * 查找所有Function
	 * @return
	 * @throws DAOException
	 */
	public List<Function> findAll() throws DAOException;
	/**
	 * 查找可用Function
	 * @return
	 * @throws DAOException
	 */
	public List<Function> findAvailableFunction() throws DAOException;
}
