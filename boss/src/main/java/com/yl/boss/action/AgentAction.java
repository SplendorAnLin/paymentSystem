package com.yl.boss.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.pay.common.util.DateUtil;
import com.pay.common.util.StringUtil;
import com.yl.account.api.bean.AccountBean;
import com.yl.account.api.bean.request.AccountModify;
import com.yl.account.api.bean.request.AccountQuery;
import com.yl.account.api.bean.response.AccountModifyResponse;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.api.dubbo.AccountInterface;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.api.enums.UserRole;
import com.yl.agent.api.bean.Function;
import com.yl.agent.api.bean.Menu;
import com.yl.agent.api.interfaces.AgentFunctionInterface;
import com.yl.agent.api.interfaces.AgentMenuInterface;
import com.yl.boss.Constant;
import com.yl.boss.api.enums.AgentStatus;
import com.yl.boss.api.enums.AgentType;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.entity.Agent;
import com.yl.boss.entity.AgentCert;
import com.yl.boss.entity.AgentFee;
import com.yl.boss.entity.AgentSettle;
import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.Customer;
import com.yl.boss.enums.FileName;
import com.yl.boss.service.AgentCertService;
import com.yl.boss.service.AgentFeeService;
import com.yl.boss.service.AgentService;
import com.yl.boss.service.AgentSettleService;
import com.yl.boss.service.CustomerService;
import com.yl.boss.utils.FileUtil;
import com.yl.boss.utils.ImgUtil;
import com.yl.boss.valuelist.ValueListRemoteAction;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.dpay.hessian.service.ServiceConfigFacade;
import com.yl.receive.hessian.ReceiveQueryFacade;
import com.yl.receive.hessian.bean.ReceiveConfigInfoBean;

import net.mlw.vlh.ValueList;

/**
 * 服务商信息控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月28日
 * @version V1.0.0
 */
public class AgentAction extends Struts2ActionSupport {

	private static final long serialVersionUID = -35344883318458240L;
	private AgentService agentService;
	private CustomerService customerService;
	private AccountQueryInterface accountQueryInterface;
	private ValueListRemoteAction valueListRemoteAction;
	private String menuName;
	private Agent agent;
	private AgentCert agentCert;
	private List<AgentFee> agentFees;
	private AgentSettle agentSettle;
	private File attachment;//补充材料
	private File busiLiceCert; // 企业营业执照|个人身份证正面
	private File taxRegCert; // 企业税务登记证|个人身份证反面
	private File organizationCert; // 组织机构证|个人银行卡正面
	private File openBankAccCert; // 银行开户许可证|个人银行卡反面
	private File idCard; // 企业法人身份证|个人手持身份证
	private ServiceConfigBean serviceConfigBean;
	private ReceiveConfigInfoBean receiveConfigInfoBean;//代收配置新增
	private double balance;// 可用余额
	private AgentCertService agentCertService;
	private AgentSettleService agentSettleService;
	private AgentFeeService agentFeeService;
	private List<Menu> menuList;
	private FileName fileName;
	private InputStream fileOutput;
	private AgentMenuInterface agentMenuInterface;
	private AgentFunctionInterface agentFunctionInterface;
	private String phone;
	private String msg;
	private Menu menu;
	private Function function;
	private Page page;
	private long id;
	/* 入账周期 */
	private int cycle;
	private AccountBean account;
	private AccountInterface accountInterface;
	private String agentLevel;
	private String feeType;
	private ServiceConfigFacade serviceConfigFacade;
	private ReceiveQueryFacade receiveQueryFacade;

	private static Properties prop = new Properties();

	static {
		try {
			prop.load(
					new InputStreamReader(AgentAction.class.getClassLoader().getResourceAsStream("system.properties")));
		} catch (IOException e) {
			logger.error("AgentAction load Properties error:", e);
		}
	}

	
	/**
	 * 分页查询
	 * 
	 * @return
	 */
	public String findAll() {
		Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
		Map<String, Object> params = retrieveParams(requestMap);
		page = agentFunctionInterface.findAll(params);
		return SUCCESS;
	}

	/**
	 * 新增功能
	 * 
	 * @return
	 */
	public String addFunction() {
		agentFunctionInterface.addFunction(function);
		return SUCCESS;
	}
	
	public String findFunctionById(){
		function=agentFunctionInterface.findById(id);
		menuName=agentMenuInterface.findById(Long.valueOf(function.getMenuId())).getName();
		return SUCCESS;
	}
	/**
	 * 编辑功能
	 * 
	 * @return
	 */
	public String modify() {
		Function functionDb = agentFunctionInterface.findById(function.getId());
		functionDb.setName(function.getName());
		functionDb.setAction(function.getAction());
		functionDb.setIsCheck(function.getIsCheck());
		functionDb.setStatus(function.getStatus());
		functionDb.setRemark(function.getRemark());
		functionDb.setMenuId(function.getMenuId());
		agentFunctionInterface.modify(functionDb);
		return SUCCESS;
	}

	/**
	 * 删除功能
	 * 
	 * @return
	 */
	public String delete() {
		String functionId = getHttpRequest().getParameter("Id");
		Long id = Long.parseLong(functionId);
		agentFunctionInterface.delete(id);
		return SUCCESS;
	}

	/**
	 * 新增
	 * 
	 * @return
	 */
	public String agentAddChildMenu() {
		Menu menuDb = agentMenuInterface.findById(menu.getParentId());
		Long level = Long.parseLong(menuDb.getLevel());
		menu.setIsLeaf("Y");
		menu.setLevel(String.valueOf(level + 1));
		menu = agentMenuInterface.create(menu);
		return SUCCESS;
	}
	public String findMenuByName() {
		try {
			menuList = agentMenuInterface.findMenuByName(java.net.URLDecoder.decode(menuName, "utf-8"));
			msg = JsonUtils.toJsonString(menuList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String findMenuName() {
		try {
			menu = agentMenuInterface.findMenuName(java.net.URLDecoder.decode(menuName, "utf-8"));
			if (menu != null) {
				msg = JsonUtils.toJsonString(menu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	public String agentModifyMenu() {
		Menu menuDb = agentMenuInterface.findById(menu.getId());
		menuDb.setName(menu.getName());
		menuDb.setUrl(menu.getUrl());
		menuDb.setDisplayOrder(menu.getDisplayOrder());
		menuDb.setStatus(menu.getStatus());
		menuDb.setParentId(menu.getParentId());
		menu = agentMenuInterface.update(menuDb);
		return SUCCESS;
	}

	/**
	 * 编辑
	 * 
	 * @return
	 */
	public String toagentMenuEdit() {
		String menuId = getHttpRequest().getParameter("menuId");
		Long id = Long.parseLong(menuId);
		menu = agentMenuInterface.findById(id);
		List<Menu> pMenus = agentMenuInterface.findByLevel("1");
		getHttpRequest().setAttribute("pMenus", pMenus);
		return SUCCESS;
	}

	/**
	 * 服务商菜单查询
	 * 
	 * @return
	 */
	public String agentMenuQuery() {
		try {
			menuList = agentMenuInterface.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 参数转换
	 */
	private Map<String, Object> retrieveParams(Map<String, String[]> requestMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (String key : requestMap.keySet()) {
			String[] value = requestMap.get(key);
			if (value != null) {
				resultMap.put(key, Array.get(value, 0).toString().trim());
			}
		}
		return resultMap;
	}

	public String checkPhone() {
		agent = agentService.findByPhone(phone);
		if (agent == null) {
			msg = "true";
		} else {
			msg = "false";
		}
		return SUCCESS;
	}

	public String openAgent() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		String msg = null;
		String dateString = DateUtil.formatDate(new Date(), "yyyyMMddHHMMss");
		String proPath = prop.getProperty("agent.imgPath");
		String filePath = proPath + dateString + "/";
		try {
			if (agent.getAgentType()==AgentType.INDIVIDUAL) {
				agentCert=new AgentCert();
			}
			if (null != attachment) {
				msg = ImgUtil.checkImg(attachment);
				if (StringUtil.isNull(msg)) {
					String imgPath = "att-" + agent.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(attachment, filePath, imgPath);
					agentCert.setAttachment(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			msg = ImgUtil.checkImg(busiLiceCert);
			if (StringUtil.isNull(msg)) {
				String imgPath = "bc-" + agent.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
				FileUtil.copy(busiLiceCert, filePath, imgPath);
				agentCert.setBusiLiceCert(filePath + imgPath);
			} else {
				throw new Exception(msg);
			}

			msg = ImgUtil.checkImg(taxRegCert);
			if (StringUtil.isNull(msg)) {
				String imgPath = "tc-" + agent.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
				FileUtil.copy(taxRegCert, filePath, imgPath);
				agentCert.setTaxRegCert(filePath + imgPath);
			} else {
				throw new Exception(msg);
			}
			msg = ImgUtil.checkImg(organizationCert);
			if (StringUtil.isNull(msg)) {
				String imgPath = "oc-" + agent.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
				FileUtil.copy(organizationCert, filePath, imgPath);
				agentCert.setOrganizationCert(filePath + imgPath);
			} else {
				throw new Exception(msg);
			}
			msg = ImgUtil.checkImg(openBankAccCert);
			if (StringUtil.isNull(msg)) {
				String imgPath = "ac-" + agent.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
				FileUtil.copy(openBankAccCert, filePath, imgPath);
				agentCert.setOpenBankAccCert(filePath + imgPath);
			} else {
				throw new Exception(msg);
			}
			msg = ImgUtil.checkImg(idCard);
			if (StringUtil.isNull(msg)) {
				String imgPath = "mp_img_" + System.currentTimeMillis() + ".jpg";
				FileUtil.copy(idCard, filePath, imgPath);
				agentCert.setIdCard(filePath + imgPath);
			} else {
				throw new Exception(msg);
			}
			agentService.create(receiveConfigInfoBean, agent, agentCert, agentSettle, agentFees, serviceConfigBean, auth.getRealname(), cycle);
		} catch (Exception e) {
			return ERROR;
			//throw new RuntimeException("create agent:[" + agent.getShortName() + "] is failed! exception:{}", e);
		}
		return SUCCESS;
	}

	// 重置服务商密码
	public String resetAgentPassword() {
		try {
			agentService.resetPassword(agent.getAgentNo());
			msg = "true";
		} catch (Exception e) {
			msg = "false";
			throw new RuntimeException(
					"resetAgentPassword agentNo:[" + agent.getAgentNo() + "] is failed! exception:{}", e);
		}
		return SUCCESS;
	}

	public String update() {
		try {
			String msg = null;
			String dateString = DateUtil.formatDate(new Date(), "yyyyMMddHHMMss");
			String proPath = prop.getProperty("agent.imgPath");
			String filePath = proPath + dateString + "/";
			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			if (null != attachment) {
				msg = ImgUtil.checkImg(attachment);
				if (StringUtil.isNull(msg)) {
					String imgPath = "att-" + agent.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(attachment, filePath, imgPath);
					agentCert.setAttachment(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			if (null != busiLiceCert) {
				msg = ImgUtil.checkImg(busiLiceCert);
				if (StringUtil.isNull(msg)) {
					String imgPath = "bc-" + agent.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(busiLiceCert, filePath, imgPath);
					agentCert.setBusiLiceCert(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			if (null != taxRegCert) {
				msg = ImgUtil.checkImg(taxRegCert);
				if (StringUtil.isNull(msg)) {
					String imgPath = "tc-" + agent.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(taxRegCert, filePath, imgPath);
					agentCert.setTaxRegCert(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			if (null != organizationCert) {
				msg = ImgUtil.checkImg(organizationCert);
				if (StringUtil.isNull(msg)) {
					String imgPath = "oc-" + agent.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(organizationCert, filePath, imgPath);
					agentCert.setOrganizationCert(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			if (null != openBankAccCert) {
				msg = ImgUtil.checkImg(openBankAccCert);
				if (StringUtil.isNull(msg)) {
					String imgPath = "ac-" + agent.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(openBankAccCert, filePath, imgPath);
					agentCert.setOpenBankAccCert(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			if (null != idCard) {
				msg = ImgUtil.checkImg(idCard);
				if (StringUtil.isNull(msg)) {
					String imgPath = "mp_img_" + System.currentTimeMillis() + ".jpg";
					FileUtil.copy(idCard, filePath, imgPath);
					agentCert.setIdCard(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			agentService.update(agent, auth.getRealname());
			agentCertService.update(agentCert, auth.getRealname());
			//更新帐户信息
			AccountQuery accountQuery = new AccountQuery();
			accountQuery.setUserNo(agent.getAgentNo());
			accountQuery.setUserRole(UserRole.AGENT);
			AccountQueryResponse accountResponse = accountQueryInterface.findAccountBy(accountQuery);
			if(accountResponse!=null&&accountResponse.getAccountBeans()!=null&&accountResponse.getAccountBeans().size()==1){
				AccountBean accountBean =accountResponse.getAccountBeans().get(0);
				
				AccountModify accountModify = new AccountModify();
				accountModify.setAccountNo(accountBean.getCode());
				accountModify.setUserNo(accountBean.getUserNo());
				accountModify.setCycle(cycle);
				accountModify.setAccountStatus(accountBean.getStatus());//注释掉查询时候是什么状态就什么状态,不能因为修改入账周期改变状态
				accountModify.setSystemCode("boss");
				accountModify.setSystemFlowId("OC" + agent.getAgentNo());
				accountModify.setBussinessCode("UPDATE_ACCOUNT");
				accountModify.setRequestTime(new Date());
				accountModify.setOperator(auth.getRealname());
				accountModify.setRemark("运营调整服务商入账周期为:T"+cycle);
				AccountModifyResponse accountModifyResponse = accountInterface.modifyAccount(accountModify);
				if (accountModifyResponse.getResult() == HandlerResult.FAILED) {
					throw new RuntimeException("agent No:" + agent.getAgentNo() + " update account failed:" + accountModifyResponse + "");
				}
				logger.info("update account agent:" + accountModify.getUserNo() + " info:" + JsonUtils.toJsonString(accountModify));
			}else{
				throw new RuntimeException("没有查询到账户信息或账户信息存在多个");
			}
			
			// agentSettleService.update(agentSettle, auth.getRealname());
			// for (AgentFee agentFee : agentFees) {
			// agentFee.setStatus(Status.TRUE);
			// agentFeeService.update(agentFee, auth.getRealname());
			// }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("update agent is failed! exception:{}", e);
		}

		return SUCCESS;
	}

	public String findByAgentNo() {
		agent = agentService.findByNo(agent.getAgentNo());
		agentCert = agentCertService.findByAgentNo(agent.getAgentNo());
		agentSettle = agentSettleService.findByAgentNo(agent.getAgentNo());
		agentFees = agentFeeService.findByAgentNo(agent.getAgentNo());
		serviceConfigBean = serviceConfigFacade.query(agent.getAgentNo());
		receiveConfigInfoBean = receiveQueryFacade.queryReceiveConfigBy(agent.getAgentNo());
		AccountQuery accountQuery = new AccountQuery();
		accountQuery.setUserNo(agent.getAgentNo());
		accountQuery.setUserRole(UserRole.AGENT);
		account=accountQueryInterface.findAccountBy(accountQuery).getAccountBeans().get(0);
		balance = account.getBalance();
		cycle=account.getCycle();
		return SUCCESS;
	}

	/**
	 * 获取一个Agent
	 * 
	 * @return
	 * @author qiujian 2016年10月23日
	 */
	public String findAgentFullNameByAgentNo() {
        try {
            msg = agentService.queryAgentFullNameByAgentNo(agent.getAgentNo());
            if(msg == null){
                msg = "false";
            }
        } catch (Exception e) {
            msg = "false";
        }
        return SUCCESS;
    }


	/**
	 * 获取图片流
	 * 
	 * @return
	 */
	public String findDocumentImg() {

		// if(null==imgUrl||"".equals(imgUrl)){//如果指定的图片不存在，显示默认图片
		// imgUrl =
		// getSession().getServletContext().getRealPath("/")+"images"+File.separator+"123.jpg";
		// }
		try {
			if (agentCert != null && null != agentCert.getAgentNo()) {
				agentCert = agentCertService.findByAgentNo(agentCert.getAgentNo());
				if (fileName.equals(FileName.FILE_BUSILICECERT)) {
					String imgUrl = agentCert.getBusiLiceCert();
					fileOutput = ImgUtil.findDocumentImgByName(imgUrl);
				}
				if (fileName.equals(FileName.FILE_IDCARD)) {
					String imgUrl = agentCert.getIdCard();
					fileOutput = ImgUtil.findDocumentImgByName(imgUrl);

				}
				if (fileName.equals(FileName.FILE_OPENBANKACCCERT)) {
					String imgUrl = agentCert.getOpenBankAccCert();
					fileOutput = ImgUtil.findDocumentImgByName(imgUrl);
				}
				if (fileName.equals(FileName.FILE_ORGANIZATIONCERT)) {
					String imgUrl = agentCert.getOrganizationCert();
					fileOutput = ImgUtil.findDocumentImgByName(imgUrl);

				}
				if (fileName.equals(FileName.FILE_TAXREGCERT)) {
					String imgUrl = agentCert.getTaxRegCert();
					fileOutput = ImgUtil.findDocumentImgByName(imgUrl);
				}
				if (fileName.equals(FileName.FILE_ATT)) {
					String imgUrl = agentCert.getAttachment();
					fileOutput = ImgUtil.findDocumentImgByName(imgUrl);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;

	}

	public void checkShortName() {
		agent = agentService.findByShortName(agent.getShortName());
		if (agent == null) {
			write("TRUE");
		} else {
			write("FALSE");
		}
	}

	public void checkFullName() {
		agent = agentService.findByFullName(agent.getFullName());
		if (agent == null) {
			write("TRUE");
		} else {
			write("FALSE");
		}
	}
	
	/**
	 * 查询需要审核的服务商信息
	 * @return
	 */
	public String agentAuditQuery(){
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(agentLevel != null && !agentLevel.equals("")){
			params.put("agentLevel", agentLevel);
		}
		if(agent.getFullName() != null && !agent.getFullName().equals("")){
			params.put("fullName", agent.getFullName());
		}
		
		if(agent.getAgentNo() != null && !agent.getAgentNo().equals("")){
			params.put("agentNo", agent.getAgentNo());
			
			switch (agentLevel) {
			case "1":
				params.put("agentLevel", agentLevel);
				break;
			case "2":
				params.put("twoLevelAgentNo", agent.getAgentNo());
				params.remove("agentNo");
				params.remove("agentLevel");
				break;
			case "3":
				params.put("threeLevelAgentNo", agent.getAgentNo());
				params.remove("agentNo");
				params.remove("agentLevel");
				break;
			default:
				params.put("allLevelAgentNo", agent.getAgentNo());
				params.remove("agentNo");
				params.remove("agentLevel");
				break;
			}
		
			
		}
		
		
		params.put("status", AgentStatus.AUDIT);
		if(getHttpRequest().getParameter("currentPage") != null && getHttpRequest().getParameter("currentPage") != ""){
			params.put("pagingPage", getHttpRequest().getParameter("currentPage"));
		}
		
		ValueList vl = valueListRemoteAction.execute("agentInfo", params).get("agentInfo");
		page = new Page<>();
		page.setCurrentPage(vl.getValueListInfo().getPagingPage());
		page.setTotalResult(vl.getValueListInfo().getTotalNumberOfEntries());
		page.setObject(vl.getList());
		return SUCCESS;
	}
	
	/**
	 * 服务商审核
	 * @return
	 */
	public String auditAgent(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		agentService.auditAgent(agent, agentFees, auth.getRealname());
		return SUCCESS;
	}
	
	/**
	 * 服务商查询
	 * @return
	 */
	public String agentQuery(){
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(agentLevel != null && !agentLevel.equals("")){
			params.put("agentLevel", agentLevel);
		}
		if(agent.getFullName() != null && !agent.getFullName().equals("")){
			params.put("fullName", agent.getFullName());
		}
		
		if(agent.getStatus() != null && !agent.getStatus().equals("")){
			params.put("status", agent.getStatus());
		}
		
		if(agent.getAgentType() != null && !agent.getAgentType().equals("")){
			params.put("agentType", agent.getAgentType());
		}
		
		if(agent.getPhoneNo() != null && !agent.getPhoneNo().equals("")){
			params.put("phoneNo", agent.getPhoneNo());
		}
		
		if(getHttpRequest().getParameter("create_time_start") != null && !getHttpRequest().getParameter("create_time_start").equals("")){
			params.put("create_time_start", getHttpRequest().getParameter("create_time_start"));
		}
		
		if(getHttpRequest().getParameter("create_time_end") != null && !getHttpRequest().getParameter("create_time_end").equals("")){
			params.put("create_time_end", getHttpRequest().getParameter("create_time_end"));
		}
		
		if(agent.getAgentNo() != null && !agent.getAgentNo().equals("")){
			params.put("agentNo", agent.getAgentNo());
			
			switch (agentLevel) {
			case "1":
				params.put("agentLevel", agentLevel);
				break;
			case "2":
				params.put("twoLevelAgentNo", agent.getAgentNo());
				params.remove("agentNo");
				params.remove("agentLevel");
				break;
			case "3":
				params.put("threeLevelAgentNo", agent.getAgentNo());
				params.remove("agentNo");
				params.remove("agentLevel");
				break;
			default:
				params.put("allLevelAgentNo", agent.getAgentNo());
				params.remove("agentNo");
				params.remove("agentLevel");
				break;
			}
		
			
		}
		params.put("pagingPage",getHttpRequest().getParameter("currentPage"));
		
		ValueList vl = valueListRemoteAction.execute("agentInfo", params).get("agentInfo");
		page = new Page<>();
		page.setCurrentPage(vl.getValueListInfo().getPagingPage());
		page.setTotalResult(vl.getValueListInfo().getTotalNumberOfEntries());
		page.setObject(vl.getList());
		
		return SUCCESS;
	}
	
	/**
	 * 根据服务商全称和简称，检查是否有重复
	 * @return
	 */
	public String checkAgentName(){
		try {
			if(agent.getFullName() != null){
				agent.setFullName(URLDecoder.decode(agent.getFullName(), "UTF-8"));
			}
			if(agent.getShortName() != null){
				agent.setShortName(URLDecoder.decode(agent.getShortName(), "UTF-8"));
			}
			msg = JsonUtils.toJsonString(agentService.checkAgentName(agent.getFullName(),agent.getShortName() ));
		} catch (UnsupportedEncodingException e) {
		}
		
		return SUCCESS;
	}
	
	/**
	 * 根据服务商编号查询所属父级服务商编号
	 * @return
	 */
	public String  queryParenIdByAgentNo(){
		try {
			msg = agentService.queryParenIdByAgentNo(getHttpRequest().getParameter("agentNo"));
			if(msg == null){
				msg = "false";
			}
		} catch (Exception e) {
			msg = "false";
		}
		return SUCCESS;
	}
	
	/**
	 * 查询当前服务商是否存在，并且是否是一级服务商
	 * @return
	 */
	public String queryAgentExistsAndLevelOne(){
		String agentNo = getHttpRequest().getParameter("agentNo");
		if(agentNo != null){
			Agent agent = agentService.findByNo(agentNo);
			if(agent != null){
				if(agent.getAgentLevel() == 1){
					msg = JsonUtils.toJsonString(agent);
				}else {
					msg = "levelNotMatch";
				}
			}else {
				msg = "false";
			}
		}else {
			msg = "agentNo_null";
		}
		return SUCCESS;
	}
	
	/**
	 * 根据服务商编号查询服务商简称
	 * @return
	 */
	public String queryAgentShortNameByAgentNo(){
		msg = agentService.queryAgentShortNameByAgentNo(getHttpRequest().getParameter("agentNo"));
		return SUCCESS;
	}
	

	public AgentService getAgentService() {
		return agentService;
	}

	public void setAgentService(AgentService agentService) {
		this.agentService = agentService;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public AgentCert getAgentCert() {
		return agentCert;
	}

	public AccountBean getAccount() {
		return account;
	}

	public void setAccount(AccountBean account) {
		this.account = account;
	}

	public void setAgentCert(AgentCert agentCert) {
		this.agentCert = agentCert;
	}

	public List<AgentFee> getAgentFees() {
		return agentFees;
	}

	public void setAgentFees(List<AgentFee> agentFees) {
		this.agentFees = agentFees;
	}

	public AgentSettle getAgentSettle() {
		return agentSettle;
	}

	public void setAgentSettle(AgentSettle agentSettle) {
		this.agentSettle = agentSettle;
	}

	public File getBusiLiceCert() {
		return busiLiceCert;
	}

	public void setBusiLiceCert(File busiLiceCert) {
		this.busiLiceCert = busiLiceCert;
	}

	public AccountQueryInterface getAccountQueryInterface() {
		return accountQueryInterface;
	}

	public void setAccountQueryInterface(AccountQueryInterface accountQueryInterface) {
		this.accountQueryInterface = accountQueryInterface;
	}

	public void setAgentMenuInterface(AgentMenuInterface agentMenuInterface) {
		this.agentMenuInterface = agentMenuInterface;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public File getTaxRegCert() {
		return taxRegCert;
	}

	public void setTaxRegCert(File taxRegCert) {
		this.taxRegCert = taxRegCert;
	}

	public File getOrganizationCert() {
		return organizationCert;
	}

	public void setOrganizationCert(File organizationCert) {
		this.organizationCert = organizationCert;
	}

	public File getOpenBankAccCert() {
		return openBankAccCert;
	}

	public void setOpenBankAccCert(File openBankAccCert) {
		this.openBankAccCert = openBankAccCert;
	}

	public File getIdCard() {
		return idCard;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public void setIdCard(File idCard) {
		this.idCard = idCard;
	}

	public InputStream getFileOutput() {
		return fileOutput;
	}

	public void setFileOutput(InputStream fileOutput) {
		this.fileOutput = fileOutput;
	}

	public FileName getFileName() {
		return fileName;
	}

	public void setFileName(FileName fileName) {
		this.fileName = fileName;
	}

	public void setAgentCertService(AgentCertService agentCertService) {
		this.agentCertService = agentCertService;
	}

	public void setAgentSettleService(AgentSettleService agentSettleService) {
		this.agentSettleService = agentSettleService;
	}

	public void setAgentFeeService(AgentFeeService agentFeeService) {
		this.agentFeeService = agentFeeService;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public ServiceConfigBean getServiceConfigBean() {
		return serviceConfigBean;
	}

	public void setServiceConfigBean(ServiceConfigBean serviceConfigBean) {
		this.serviceConfigBean = serviceConfigBean;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Function getFunction() {
		return function;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	
	public AccountInterface getAccountInterface() {
		return accountInterface;
	}

	public void setAccountInterface(AccountInterface accountInterface) {
		this.accountInterface = accountInterface;
	}

	public AgentFunctionInterface getAgentFunctionInterface() {
		return agentFunctionInterface;
	}

	public void setAgentFunctionInterface(AgentFunctionInterface agentFunctionInterface) {
		this.agentFunctionInterface = agentFunctionInterface;
	}

	public ReceiveConfigInfoBean getReceiveConfigInfoBean() {
		return receiveConfigInfoBean;
	}

	public void setReceiveConfigInfoBean(ReceiveConfigInfoBean receiveConfigInfoBean) {
		this.receiveConfigInfoBean = receiveConfigInfoBean;
	}

	public ValueListRemoteAction getValueListRemoteAction() {
		return valueListRemoteAction;
	}

	public void setValueListRemoteAction(ValueListRemoteAction valueListRemoteAction) {
		this.valueListRemoteAction = valueListRemoteAction;
	}

	public String getAgentLevel() {
		return agentLevel;
	}

	public void setAgentLevel(String agentLevel) {
		this.agentLevel = agentLevel;
	}

	public File getAttachment() {
		return attachment;
	}

	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}

	public ServiceConfigFacade getServiceConfigFacade() {
		return serviceConfigFacade;
	}

	public void setServiceConfigFacade(ServiceConfigFacade serviceConfigFacade) {
		this.serviceConfigFacade = serviceConfigFacade;
	}

	public ReceiveQueryFacade getReceiveQueryFacade() {
		return receiveQueryFacade;
	}

	public void setReceiveQueryFacade(ReceiveQueryFacade receiveQueryFacade) {
		this.receiveQueryFacade = receiveQueryFacade;
	}
	
}