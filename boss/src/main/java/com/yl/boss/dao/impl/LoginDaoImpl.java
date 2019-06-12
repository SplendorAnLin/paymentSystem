package com.yl.boss.dao.impl;

import java.util.Date;
import java.util.List;

import com.yl.boss.dao.DAOException;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.dao.LoginLogDao;
import com.yl.boss.entity.LoginLog;
import com.yl.boss.enums.LoginStatus;


/**
 * 操作员登录日志接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class LoginDaoImpl implements LoginLogDao {

	private EntityDao entityDao ;
	

	public LoginLog create(LoginLog loginLog) {
		if (loginLog == null) {
			throw new DAOException("");
		}
		entityDao.persist(loginLog);
		return loginLog;
	}


	public LoginLog update(LoginLog loginLog) {
		if(loginLog==null){
			throw new DAOException("");
		}
		return entityDao.merge(loginLog);
	}

	public void update(List<LoginLog> loginLogs){
		Date date = new Date();
		for(int i=0;i<loginLogs.size();i++){
			LoginLog log = loginLogs.get(i);
			if(log.getLogoutTime() == null){
				log.setLogoutTime(date);
				entityDao.merge(log);
			}
		}
	}
	public LoginLog findById(Long id) {
		
		return entityDao.findById(LoginLog.class, id);
	}

	public List<LoginLog> findByUsername(String username){
		String sql = "from LoginLog l where l.username=? order by l.loginTime DESC";
		return entityDao.find(sql,username);
	}
	
	public LoginLog findLastByUsername(String username){
		String sql = "from LoginLog l where l.username=? and LOGIN_STATUS='SUCCESS' order by l.loginTime DESC limit 1,1";
		return (LoginLog)entityDao.find(sql,username).get(0);
	}
	
	public List<LoginLog> findByUsernameAndStatusAndLogoutTimeNull(String username,LoginStatus loginStatus){
		String sql = "from LoginLog l where l.username=? and l.loginStatus=? and l.logoutTime=null  order by l.loginTime desc";
		return entityDao.find(sql,username,loginStatus);
	}

	public List<LoginLog> findBySessionIdAndLoginStatus(String username,String sessionId,LoginStatus loginStatus){
		String sql = "from LoginLog l where l.sessionId=? and l.loginStatus=? and l.username=? order by l.loginTime desc";
		return entityDao.find(sql,sessionId,loginStatus,username);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	
}
