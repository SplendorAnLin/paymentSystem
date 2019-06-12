package com.yl.customer.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pay.common.util.DigestUtil;
import com.yl.customer.dao.DAOException;
import com.yl.customer.dao.OperatorDao;
import com.yl.customer.dao.RoleDao;
import com.yl.customer.entity.Authorization;
import com.yl.customer.entity.Operator;
import com.yl.customer.entity.Role;
import com.yl.customer.service.OperatorService;
import com.yl.customer.service.ServiceException;
import com.yl.customer.utils.CodeBuilder;

/**
 * 操作员
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月20日
 * @version V1.0.0
 */
public class OperatorServiceImpl implements OperatorService {
	
//	private static final Logger logger = Logger.getLogger(OperatorServiceImpl.class) ;
	
	private OperatorDao operatorDao;	
	private RoleDao roleDao;
	
	/**
	 * 商户密码重置
	 */
	@Override
	public String resetPassWord(String customer) {
		Operator oper = null;
		String pwd = Long.toHexString(System.nanoTime());
		if(customer!=null&&!("".equals(customer))){
			oper = operatorDao.findByCustomerNo(customer);
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

	public String resetPassWordByPhone(String customer,String phone){
		if(customer!=null&&!("".equals(customer))){
			Operator oper = operatorDao.findByPhone(phone, customer);
			if(oper!=null){
				try {
				    String pwd = CodeBuilder.buildNumber(6);
					oper.setPassword(DigestUtil.md5(pwd));
					oper.setPasswdModifyTime(new Date());
					operatorDao.update(oper);
					return pwd;
				} catch (Exception e) {
					throw new RuntimeException("OperatorServiceImpl resetPassword update is failed! exception:{}", e);
				}
			}
		}
		return null;
	}
	
	// 密码修改
	public Operator updatePassword(String username, String password)
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
		operatorOld.setStatus(operator.getStatus());
		if(operator.getRoleId() != operatorOld.getRoleId()){
			Role role = roleDao.findById(operator.getRoleId());
			operatorDao.deleteRole(operatorOld.getId());
			operatorDao.createRole(operatorOld.getId(), role.getId());
		}
		operatorOld.setRoleId(operator.getRoleId());
		return operatorDao.update(operatorOld);
	}

	@Override
	public void add(Operator operator) {
		operator.setPassword(DigestUtil.md5(operator.getPassword()));
		operator.setComplexPassword(DigestUtil.md5(operator.getComplexPassword()));
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
	public Operator updateAuditPassword(String username, String password, Authorization auth) throws ServiceException {
		Operator operator =operatorDao.findByUsername(username);
		operator.setComplexPassword(DigestUtil.md5(password));
		operator = operatorDao.update(operator);
		return operator;
	}

	@Override
	public Operator findByCustNo(String customerNo) {
		return operatorDao.findByCustomerNo(customerNo);
	}
	
	@Override
	public String updateAppPassword(Map<String, Object> param) {
		Operator operator =operatorDao.findByUsername(param.get("userName").toString());
		if(operator != null && operator.getPassword().equals(DigestUtil.md5(param.get("oldPassWord").toString()))){
			operator.setPassword(DigestUtil.md5(param.get("passWord").toString()));
			operator.setPasswdModifyTime(new Date());
			operator = operatorDao.update(operator);
			return "修改成功！";
		}
		return "用户名或原密码错误！";
	}
}