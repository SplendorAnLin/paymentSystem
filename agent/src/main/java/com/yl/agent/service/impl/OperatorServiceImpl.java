package com.yl.agent.service.impl;

import java.util.Date;
import java.util.List;
import com.pay.common.util.DigestUtil;
import com.yl.agent.dao.DAOException;
import com.yl.agent.dao.OperatorDao;
import com.yl.agent.dao.RoleDao;
import com.yl.agent.entity.Authorization;
import com.yl.agent.entity.Operator;
import com.yl.agent.entity.Role;
import com.yl.agent.service.OperatorService;

/**
 * 操作员
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class OperatorServiceImpl implements OperatorService {
	
//	private static final Logger logger = Logger.getLogger(OperatorServiceImpl.class) ;
	
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
		if (operator.getStatus()!=null) {
			operatorOld.setStatus(operator.getStatus());
		}
		operatorOld.setRoleId(operator.getRoleId());
		return operatorDao.update(operatorOld);
	}
	
	@Override
	public String resetPassword(String agentNo) {
		Operator oper = null;
		String pwd = Long.toHexString(System.nanoTime());
		if(agentNo!=null&&!("".equals(agentNo))){
			oper = operatorDao.findByAgentNo(agentNo);
			if(oper!=null){
				try {
					oper.setPassword(DigestUtil.md5(pwd));
					oper.setPasswdModifyTime(new Date());
					operatorDao.update(oper);
				} catch (Exception e) {
					throw new RuntimeException("OperatorServiceImpl resetPassword update is failed! exception:{}", e);
				}
			}
		}
		return pwd;
	}

	public List findBySql( final String sql) {
		return operatorDao.findBySql(sql);
	}

	public List<Operator> findByHql(String sql) {
		return operatorDao.findByHql(sql);
	}

	@Override
	public void add(Operator operator) {
		operator.setPassword(DigestUtil.md5(operator.getPassword()));
		operator.setPasswdModifyTime(new Date());	
		operator.setMaxErrorTimes(0);
		operator = operatorDao.create(operator);
		Role role = roleDao.findById(operator.getRoleId());
		operatorDao.createRole(operator.getId(), role.getId());
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public Operator findByAgentNo(String agentNo) {
		return operatorDao.findByAgentNo(agentNo);
	}
}