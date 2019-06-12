package com.yl.boss.service.impl;

import java.util.Date;
import java.util.List;
import com.pay.common.util.DigestUtil;
import com.yl.boss.dao.DAOException;
import com.yl.boss.dao.OperatorDao;
import com.yl.boss.dao.RoleDao;
import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.Operator;
import com.yl.boss.entity.Role;
import com.yl.boss.service.OperatorService;

/**
 * 操作员管理业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class OperatorServiceImpl implements OperatorService {
	
	private OperatorDao operatorDao;	
	private RoleDao roleDao;
	
	// 密码修改
	public Operator updatePassword(String username, String password, Authorization auth)
			throws DAOException {
		Operator operator =operatorDao.findByUsername(username);
		operator.setPassword(DigestUtil.md5(password));
		operator.setPasswdModifyTime(new Date());		
		operator = operatorDao.update(operator);
		return operator;
	}
	
	public Operator findByCustomerNo(String customerNo){
		return operatorDao.findByCustomerNo(customerNo);
	}
	
	public Operator findById(Long operatorId){
		return operatorDao.findById(operatorId);
	}	
	public Operator findByUsername(String username){
		return operatorDao.findByUsername(username);
	}
	public List<Operator> findAll(){
		return operatorDao.findAll();
	}	
	public OperatorDao getOperatorDao() {
		return operatorDao;
	}
	public void setOperatorDao(OperatorDao operatorDao) {
		this.operatorDao = operatorDao;
	}

	public Operator update(Operator operator) {
		Operator operatorOld = operatorDao.findByUsername(operator.getUsername());
		operatorOld.setRealname(operator.getRealname());
		operatorOld.setStatus(operator.getStatus());
		if (operator.getRoleId()!=null) {
			operatorOld.setRoleId(operator.getRoleId());
		}
		return operatorDao.update(operatorOld);
	}

	public List findBySql( final String sql) {
		return operatorDao.findBySql(sql);
	}

	public List<Operator> findByHql(String sql) {
		return operatorDao.findByHql(sql);
	}

	@Override
	public void add(Operator operator) {
		operator.setAuditPassword(DigestUtil.md5(operator.getAuditPassword()));
		operator.setPassword(DigestUtil.md5(operator.getPassword()));
		operator.setPasswdModifyTime(new Date());	
		operator.setMaxErrorTimes(0);
		operator.setCustomerNo(Long.toString(System.currentTimeMillis()));
		operator = operatorDao.create(operator);
		Role role = roleDao.findById(operator.getRoleId());
		operatorDao.createRole(operator.getId(), role.getId());
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	// 密码修改
	public Operator updateAuditPassword(String username, String password, Authorization auth)
			throws DAOException {
		Operator operator =operatorDao.findByUsername(username);
		operator.setAuditPassword(DigestUtil.md5(password));
		operator.setPasswdModifyTime(new Date());		
		operator = operatorDao.update(operator);
		return operator;
	}
}