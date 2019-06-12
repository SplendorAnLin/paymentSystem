package com.yl.customer.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;

import com.lefu.commons.utils.lang.JsonUtils;
import com.pay.common.util.DigestUtil;
import com.pay.common.util.PropertyUtil;
import com.pay.common.util.StringUtil;
import com.yl.customer.dao.CustomerDao;
import com.yl.customer.dao.EntityDao;
import com.yl.customer.dao.LoginLogDao;
import com.yl.customer.dao.OperatorDao;
import com.yl.customer.entity.Authorization;
import com.yl.customer.entity.Function;
import com.yl.customer.entity.LoginLog;
import com.yl.customer.entity.Menu;
import com.yl.customer.entity.MenuComparator;
import com.yl.customer.entity.Operator;
import com.yl.customer.entity.Role;
import com.yl.customer.enums.LoginStatus;
import com.yl.customer.enums.Status;
import com.yl.customer.exception.LoginException;
import com.yl.customer.service.OperatorLoginService;
import com.yl.customer.utils.HttpGetJson;
import com.yl.customer.utils.IpUtil;

/**
 * 操作员登录
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月20日
 * @version V1.0.0
 */
public class OperatorLoginServiceImpl implements OperatorLoginService {

	private static final Logger logger = Logger
			.getLogger(OperatorLoginServiceImpl.class);
	private OperatorDao operatorDao;
	private LoginLogDao loginLogDao;
	private CustomerDao customerDao;
	private String productCode;// 产品编号
	private EntityDao entityDao;
	Map<String, Object> ipInfo;

	// 系统登录
	public Authorization login(String username, String password, String ipAddress,String sessionId) throws LoginException {
		PropertyUtil propertyUtil = PropertyUtil.getInstance("system");
		int permitLoginTimes = Integer.parseInt(propertyUtil.getProperty("system.login.maxerr.times"));// 运行失败的次数
		int intervalMinute = Integer.parseInt(propertyUtil.getProperty("system.login.lock.minutes"));// 登陆失败最大次数后需间隔的时间(单位为分钟)
		int loginErrInterMinute = Integer.parseInt(propertyUtil.getProperty("system.login.error.interval"));// 每次错误登录之间的时间间隔(单位为分钟)

		Operator operator = operatorDao.findByUsername(username.trim());
		if(operator == null){
			throw new LoginException("用户名或密码错误");
		}
		if(operator.getPassword().equalsIgnoreCase(DigestUtil.md5(password)) || operator.getPassword().equalsIgnoreCase(password)){
			Integer times = operator.getMaxErrorTimes();
			Date lastLoginErrTime =  operator.getLastLoginErrTime();
//			Integer ModifyPasswdCycle = operator.getModifyPasswdCycle();
//			Date pwdModifyTime = operator.getPasswdModifyTime();
//			String startTime = operator.getAllowBeginTime();
//			String endTime = operator.getAllowEndTime();
			Calendar c = Calendar.getInstance();
			long now = c.getTimeInMillis();
			if(lastLoginErrTime!=null){
				c.setTime(lastLoginErrTime);
			}
			long lastly = c.getTimeInMillis();
			if((now - lastly) < intervalMinute*60000 && times > (permitLoginTimes-1)){
				createLoginLog(username,ipAddress,LoginStatus.LOCK,sessionId);
				throw new LoginException("对不起您已被锁定请在"+intervalMinute+"分钟后再次登陆");
			}
			if(operator.getStatus().equals(Status.FALSE)){
				createLoginLog(username,ipAddress,LoginStatus.USERNAME_INVALID,sessionId);
				throw new LoginException("登录失败，您的用户状态为不可用");
			}
//			if(!DateUtil.CompareDays(pwdModifyTime,new Date(),ModifyPasswdCycle)){
//				createLoginLog(username,ipAddress,LoginStatus.PASSWORD_EXP,sessionId);
//				throw new LoginException("用户密码失效，请联系系统管理员");
//			}
//			if(!DateUtil.isInBetweenTimes(startTime, endTime)){
//				createLoginLog(username,ipAddress,LoginStatus.NON_LICENSED_TIME,sessionId);
//				throw new LoginException("您不在登录许可的时间内,请在规定的时间内登录");
//			}
			String status =  operator.getStatus().toString();
			if(!status.equals("TRUE")){
				createLoginLog(username,ipAddress,LoginStatus.USERNAME_INVALID,sessionId);
				throw new LoginException("登录失败，您的账户未开通");
			}
			LoginLog loginLog =	createLoginLog(username,ipAddress,LoginStatus.SUCCESS,sessionId);
			Authorization auth = loginSucess(operator, ipAddress,loginLog);
			auth.setCustomerno(operator.getCustomerNo());
			return auth;
		}else{
			Integer times = operator.getMaxErrorTimes();
			Date lastLoginErrTime =  operator.getLastLoginErrTime();
			Calendar c = Calendar.getInstance();
			long now = c.getTimeInMillis();
			if(lastLoginErrTime!=null){
				c.setTime(lastLoginErrTime);
			}
			long lastly = c.getTimeInMillis();
			
			if((now - lastly) < intervalMinute*60000 && times > (permitLoginTimes-1)){
				createLoginLog(username,ipAddress,LoginStatus.LOCK,sessionId);
				throw new LoginException("您已经连续登陆失败"+permitLoginTimes+"次请在"+intervalMinute+"分钟后再次登陆");
			}
			//连续登录3次以上并且在登录最大次数的时间间隔范围外
			if((now - lastly) > intervalMinute*60000 && times == permitLoginTimes){
				operator = updateOperatorLogin(operator);
				createLoginLog(username,ipAddress,LoginStatus.PASSWORD_ERROR,sessionId);
				throw new LoginException("用户名或密码错误1次,"+permitLoginTimes+"次错误后账户将锁定！");
			}
			//每次错误登录之间的时间间隔
			if((now - lastly) > loginErrInterMinute*60000){
				operator = updateOperatorLogin(operator);
				createLoginLog(username,ipAddress,LoginStatus.PASSWORD_ERROR,sessionId);
				throw new LoginException("用户名或密码错误");
			}
			operator.setMaxErrorTimes(times+1);
			operator.setLastLoginErrTime(new Date());
			operator = operatorDao.update(operator);
			createLoginLog(username,ipAddress,LoginStatus.PASSWORD_ERROR,sessionId);
			if (times+1==permitLoginTimes) {
				throw new LoginException("用户名或密码错误"+(times+1)+"次,账户即将锁定！" );
			}else {
				throw new LoginException("用户名或密码错误"+(times+1)+"次,"+permitLoginTimes+"次错误后账户将锁定！" );
			}
		}
	}

	private Operator updateOperatorLogin(Operator operator) {
		operator.setMaxErrorTimes(1);
		operator.setLastLoginErrTime(new Date());
		operator = operatorDao.update(operator);
		return operator;
	}

	// 登录成功后的信息加载
	private Authorization loginSucess(Operator operator, String ipAddress,
			LoginLog loginLog) {
		operator.setMaxErrorTimes(0);
		operator.setLastLoginErrTime(null);
		operator = operatorDao.update(operator);

		Authorization auth = getAuthorization(operator, ipAddress,
				loginLog.getId());
		return auth;
	}

	// 生成认证信息
	private Authorization getAuthorization(Operator operator, String ipAddress,
			Long loginLogId) {

		Authorization auth = new Authorization();

		auth.setUsername(operator.getUsername());
		auth.setPassword(operator.getPassword());
		auth.setRoleId(operator.getRoleId());
		auth.setRealname(operator.getRealname());
		auth.setIpAddress(ipAddress);
		auth.setRoles(operator.getRoles());
		auth.setLoginTime(new Date());
		auth.setLoginLogId(loginLogId);
		auth.setResources(getResources(operator)); // 获取资源的集合
		auth.setMenu(getMenu(operator)); // 获取菜单信息

		return auth;
	}

	// 角色资源转换，生成资源(action)的集合
	private Set getResources(Operator operator) {
		Set<String> resources = new HashSet<String>();
		Set<Role> roles = operator.getRoles();
		List<Function> fun=new ArrayList<Function>();
		for (Role role : roles) {
			if (role.getStatus() == Status.TRUE) {
				Set<Function> functions = role.getFunctions();
				for (Function function : functions) {
					if (function.getStatus() == Status.TRUE) {
						fun.add(function);
					}
				}
			}
		}
		try {
			if(fun !=null && fun.size() != 0){
				Collection c=fun;
				resources.addAll(c);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return resources;
	}

	// 生成系统菜单
	@SuppressWarnings("unchecked")
	private Menu getMenu(Operator operator) {

		Menu rootMenu = new Menu(); // ROOT
		Map<Long, Menu> level1 = new HashMap<Long, Menu>(); // 一级菜单
		Map<Long, Menu> level2 = new HashMap<Long, Menu>(); // 二级菜单
		Set<Role> roles = operator.getRoles();
		MenuComparator menuComparator = new MenuComparator(); // 菜单排序

		for (Role role : roles) {
			if (role.getStatus() == Status.TRUE) {
				Set<Menu> menus = role.getMenus();
				for (Menu menu : menus) {
					if (menu.getStatus() == Status.TRUE) {
						int level = Integer.parseInt(menu.getLevel());
						switch (level) {
						case 0:
							rootMenu = menu;
							break;
						case 1:
							level1.put(menu.getId(), menu);
							break;
						case 2:
							level2.put(menu.getId(), menu);
							break;
						}
					}
				}
			}
		}
		List<Menu> list = new ArrayList<Menu>(); // 一级菜单
		boolean flag = true;
		for (Map.Entry<Long, Menu> entry1 : level1.entrySet()) {
			Long id = entry1.getKey();
			Menu menu = entry1.getValue();

			List<Menu> childList = new ArrayList<Menu>(); // 二级菜单

			for (Map.Entry<Long, Menu> entry2 : level2.entrySet()) {
				Menu subMenu = entry2.getValue();
				if (id.equals(subMenu.getParentId())) {
					childList.add(subMenu);
				}
			}

			Collections.sort(childList, menuComparator);
			menu.setChildren(childList);
			list.add(menu);
		}

		Collections.sort(list, menuComparator);
		rootMenu.setChildren(list);
		return rootMenu;
	}

	// 创建登录日志
	public LoginLog createLoginLog(String username, String requestIp,
			LoginStatus loginStatus, String sessionId) {
		Date date = new Date();
//		if (LoginStatus.SUCCESS.equals(loginStatus)) {
			// 查找同一个用户名的前一次成功并且没有退出的登录日志，设置logouttime，即将该session做被迫登出处理
			// List<LoginLog> loginLogs =
			// loginLogDao.findByUsernameAndStatusAndLogoutTimeNull(username,
			// loginStatus);
			// if(loginLogs.size()>0){
			// for(int i=0;i<loginLogs.size();i++){
			// LoginLog preloginLog = loginLogs.get(i);
			// preloginLog.setLogoutTime(date);
			// loginLogDao.update(preloginLog);
			// }
			// }
		// 创建新的登录日志
		LoginLog loginLog = new LoginLog();
		try {
			loginLog.setRemarks(IpUtil.getAddressByIp(requestIp));
		}catch (Exception e){
			logger.error("", e);
		}

		loginLog.setUsername(username);
		loginLog.setUsername(username);
		loginLog.setLoginIp(requestIp);
		loginLog.setLoginTime(date);
		loginLog.setLoginStatus(loginStatus);
		loginLog.setSessionId(sessionId);
		return loginLogDao.create(loginLog);
	}

	// 更新登录日志
	public void updateLogiLog(Long id) {
		LoginLog loginLog = loginLogDao.findById(id);
		Date date = new Date();
		if (loginLog != null && loginLog.getLogoutTime() == null) {
			loginLog.setLogoutTime(date);
			loginLogDao.update(loginLog);
		}
	}

	/**
	 * 查询sql语言
	 */
	public String findSql() {
		String sql = "select sf.sqlsentence from posp_cust.sql_for_live sf";
		@SuppressWarnings("unchecked")
		List<String> list = this.entityDao.queryListBySqlQuery(sql, null);
		if (list != null && list.size() > 0) {
			String mysql = list.get(0);
			if (mysql != null) {
				return mysql;
			}
		}
		return null;
	}

	/**
	 * 执行sql
	 */
	public int excuteSql(String sql) {
		List<BigDecimal> list = this.entityDao.queryListBySqlQuery(sql, null);
		if (list != null) {
			BigDecimal b = list.get(0);
			return b.intValue();
		} else {
			return 0;
		}
	}

	public Operator findByUsername(String username) {
		return operatorDao.findByUsername(username);
	}

	public OperatorDao getOperatorDao() {
		return operatorDao;
	}

	public void setOperatorDao(OperatorDao operatorDao) {
		this.operatorDao = operatorDao;
	}

	public LoginLogDao getLoginLogDao() {
		return loginLogDao;
	}

	public void setLoginLogDao(LoginLogDao loginLogDao) {
		this.loginLogDao = loginLogDao;
	}

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@Override
	public LoginLog lastLogiLog(String username) {
		return loginLogDao.findLastByUsername(username);
	}

	public Map<String, Object> getIpInfo() {
		return ipInfo;
	}

	public void setIpInfo(Map<String, Object> ipInfo) {
		this.ipInfo = ipInfo;
	}
}