package com.yl.agent.action;

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

import org.apache.http.HttpRequest;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.pay.common.util.DateUtil;
import com.pay.common.util.StringUtil;
import com.yl.account.api.bean.request.AccountBalanceQuery;
import com.yl.account.api.bean.response.AccountBalanceQueryResponse;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.UserRole;
import com.yl.agent.Constant;
import com.yl.agent.entity.Authorization;
import com.yl.agent.enums.FileName;
import com.yl.agent.utils.FileUtil;
import com.yl.agent.utils.ImgUtil;
import com.yl.boss.api.bean.Agent;
import com.yl.boss.api.bean.AgentCert;
import com.yl.boss.api.bean.AgentFee;
import com.yl.boss.api.bean.AgentSettle;
import com.yl.boss.api.enums.AgentType;
import com.yl.boss.api.interfaces.AgentInterface;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.dpay.hessian.service.ServiceConfigFacade;
import com.yl.receive.hessian.ReceiveQueryFacade;
import com.yl.receive.hessian.bean.ReceiveConfigInfoBean;

/**
 * 代理商控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月23日
 * @version V1.0.0
 */
public class AgentAction extends Struts2ActionSupport {

	private static final long serialVersionUID = -35344883318458240L;

	private AgentInterface agentInterface;
	private List<AgentFee> agentFees;
	private List<AgentSettle> listAgentSettle;
	private AgentSettle agentSettle;
	private Agent agent;
	private String msg;
	private AgentCert agentCert;
	private File attachment;//补充材料
	private String attachmentName;//补充材料
	private File busiLiceCert; // 企业营业执照|个人身份证正面
	private String busiLiceCertName; // 企业营业执照|个人身份证正面
	private File taxRegCert; // 企业税务登记证|个人身份证反面
	private String taxRegCertName; // 企业税务登记证|个人身份证反面
	private File organizationCert; // 组织机构证|个人银行卡正面
	private String organizationCertName; // 组织机构证|个人银行卡正面
	private File openBankAccCert; // 银行开户许可证|个人银行卡反面
	private String openBankAccCertName; // 银行开户许可证|个人银行卡反面
	private File idCard; // 企业法人身份证|个人手持身份证
	private String idCardName; // 企业法人身份证|个人手持身份证
	private ReceiveConfigInfoBean receiveConfigInfoBean;//代收配置新增
	private ServiceConfigBean serviceConfigBean;
	/* 入账周期 */
	private int cycle;
	private Page page;
	private ServiceConfigFacade serviceConfigFacade; //代付接口
	private ReceiveQueryFacade receiveQueryFacade; //代收接口
	private FileName fileName;
	private InputStream fileOutput;
	private AccountQueryInterface accountQueryInterface;
	private Double balance;
	private String agentLevel;
	
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(new InputStreamReader(CustomerAction.class.getClassLoader().getResourceAsStream("system.properties")));
		} catch (IOException e) {
			logger.error("CustomerAction load Properties error:", e);
		}
	}

	/**
	 * 查询单条代理商信息
	 * @return
	 */
	public String queryAgentBasicInfo(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		
		//获取代理商信息
		agent = agentInterface.getAgent(auth.getAgentNo());
		
		//获取费率信息
		agentFees = agentInterface.getAgentFee(auth.getAgentNo());
		
		//获取结算卡信息
		listAgentSettle = agentInterface.getListAgentSettle(auth.getAgentNo());
		agentSettle = listAgentSettle.get(0);
		
		return SUCCESS;
	}
	
	public String queryAgentFee() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		agentFees = agentInterface.getAgentFee(auth.getAgentNo());
		msg = JsonUtils.toJsonString(agentFees);
		return SUCCESS;
	}

	public String queryAgentSettle() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		listAgentSettle = agentInterface.getListAgentSettle(auth.getAgentNo());
		return "queryAgentSettle";
	}
	
	/**
	 * 服务商入网
	 * @return
	 */
	public String openAgent(){
		//入网前需要先查一遍当前登陆的代理商等级，若当前为三级代理，则不能入网，反之，则将当前等级传入Service处理
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		
		//获取当前登陆的代理商信息
		Agent loginAgent = agentInterface.getAgent(auth.getAgentNo());
		
		//判断当前登陆的代理商等级是否小于三级，若小于三级则给入网的代理商等级加一，并添加所属代理商为该登陆代理商；反之，则不准入网
		if(loginAgent.getAgentLevel() < 2){
			agent.setAgentLevel(loginAgent.getAgentLevel() + 1);
			agent.setParenId(loginAgent.getAgentNo());
			
			
			String msg = null;
			String dateString = DateUtil.formatDate(new Date(), "yyyyMMddHHMMss");
			String proPath = prop.getProperty("agent.imgPath");
			String filePath = proPath + dateString + "/";
			try {
				if (agent.getAgentType().toString().equals(AgentType.INDIVIDUAL.toString())) {
					agentCert=new AgentCert();
				}
				if (attachment!=null) {
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
				
				String configBean = JsonUtils.toJsonString(serviceConfigBean);
				agentInterface.createAgent(receiveConfigInfoBean, agent, agentCert, agentSettle, agentFees, configBean, auth.getRealname(), cycle);
			} catch (Exception e) {
				return ERROR;
				//throw new RuntimeException("create agent:[" + agent.getShortName() + "] is failed! exception:{}", e);
			}
		}else {
			return ERROR;
			//throw new RuntimeException("create agent failed!   your level insufficient!");
		}
		return SUCCESS;
	}
	
	/**
	 * 服务商查询
	 * @return
	 */
	public String agentQuery(){
		try {
			String queryId = "agentInfo";
			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			Map<String, Object> params = new HashMap<String, Object>();
			
			//查询全部
			params.put("allLevelAgentNo", auth.getAgentNo());
			
			if(agentLevel != null && !agentLevel.equals("")){
				params.remove("allLevelAgentNo");
				switch (agentLevel) {
				case "1":
					params.put("agentNo", auth.getAgentNo());
					break;
				case "2":
					params.put("twoLevelAgentNo", auth.getAgentNo());
					break;
				case "3":
					params.put("threeLevelAgentNo", auth.getAgentNo());
					break;
				}
			}
			
			if(agent.getFullName() != null && !agent.getFullName().equals("")){
				params.put("fullName", agent.getFullName());
			}
			
			if(agent.getStatus() != null){
				params.put("status", agent.getStatus());
			}
			
			if(agent.getAgentNo() != null && !agent.getAgentNo().equals("")){
				
				params.remove("agentLevel");
				params.remove("twoLevelAgentNo");
				params.remove("threeLevelAgentNo");
				params.remove("allLevelAgentNo");
				switch (agentLevel) {
				case "2":
					params.put("parenId", -1);
					params.put("agentNo", agent.getAgentNo());
					break;
				case "3":
					params.put("allTwoLevelAgentNo", agent.getAgentNo());
					params.put("agentParenId", auth.getAgentNo());
					params.remove("agentNo");
					break;
				default:
					params.put("allThreeLevelAgentNo", agent.getAgentNo());
					params.put("agentParenId", auth.getAgentNo());
					params.remove("agentNo");
					break;
				}
			
				
			}
			params.put("pagingPage",getHttpRequest().getParameter("currentPage"));
			if(getHttpRequest().getParameter("currentPage") != null && getHttpRequest().getParameter("currentPage") != ""){
				params.put("pagingPage", getHttpRequest().getParameter("currentPage"));
			}
			page = agentInterface.queryAgent(queryId, params);
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 代理商修改
	 * @return
	 */
	public String updateAgent(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);

		String msg = null;
		String dateString = DateUtil.formatDate(new Date(), "yyyyMMddHHMMss");
		String proPath = prop.getProperty("customer.imgPath");
		String filePath = proPath + dateString + "/";

		try {
			if (agent.getAgentType().toString().equals(AgentType.INDIVIDUAL.toString())) {
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
			if(null!=busiLiceCert){
				msg = ImgUtil.checkImg(busiLiceCert);
				if (StringUtil.isNull(msg)) {
					String imgPath = "bc-" + agent.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(busiLiceCert, filePath, imgPath);
					agentCert.setBusiLiceCert(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}

			}
			if(null!=taxRegCert){
				msg = ImgUtil.checkImg(taxRegCert);
				if (StringUtil.isNull(msg)) {
					String imgPath = "tc-" + agent.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(taxRegCert, filePath, imgPath);
					agentCert.setTaxRegCert(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			if(null!=organizationCert){
				msg = ImgUtil.checkImg(organizationCert);
				if (StringUtil.isNull(msg)) {
					String imgPath = "oc-" + agent.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(organizationCert, filePath, imgPath);
					agentCert.setOrganizationCert(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			if(null!=openBankAccCert){
				msg = ImgUtil.checkImg(openBankAccCert);
				if (StringUtil.isNull(msg)) {
					String imgPath = "ac-" + agent.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(openBankAccCert, filePath, imgPath);
					agentCert.setOpenBankAccCert(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			if(null!=idCard){
				msg = ImgUtil.checkImg(idCard);
				if (StringUtil.isNull(msg)) {
					String imgPath = "mp_img_" + System.currentTimeMillis() + ".jpg";
					FileUtil.copy(idCard, filePath, imgPath);
					agentCert.setIdCard(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}

			agentInterface.updateAgent(agent, agentFees, receiveConfigInfoBean, agentCert, agentSettle, JsonUtils.toJsonString(serviceConfigBean), auth.getAgentNo(), cycle);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("create agent:[" + agent.getShortName() + "] is failed! exception:{}", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据代理商编号查询单条代理商信息
	 * @return
	 */
	public String queryAgentByAgentNo(){
		String agentNo = agent.getAgentNo();
		
		//判断当前传入参数，agentNo是否有值，若无值，则表示当前查询登陆的代理商信息；反之，则根据agentNo查询单条代理商信息
		if(!agentNo.equals("")){
			//获取代理商信息
			agent = agentInterface.getAgent(agentNo);
			
			//获取费率信息
			agentFees = agentInterface.getAgentFee(agentNo);
			
			//获取代付信息
			serviceConfigBean = serviceConfigFacade.query(agentNo);
			
			//获取代收信息
			receiveConfigInfoBean = receiveQueryFacade.queryReceiveConfigBy(agentNo);
			
			//获取结算卡信息
			listAgentSettle = agentInterface.getListAgentSettle(agentNo);
			agentSettle = listAgentSettle.get(0);
			
			//企业信息
			agentCert = agentInterface.findByAgentNo(agentNo);
		}
		
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		AccountBalanceQuery accountBalanceQuery= new AccountBalanceQuery();
		accountBalanceQuery.setUserNo(auth.getAgentNo());
		accountBalanceQuery.setUserRole(UserRole.AGENT);
		AccountBalanceQueryResponse accountResponseYe = accountQueryInterface.findAccountBalance(accountBalanceQuery);
		balance = accountResponseYe.getAvailavleBalance();
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
				agentCert = agentInterface.findByAgentNo(agentCert.getAgentNo());
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
	
	/**
	 * 根据代理商全称和简称，检查是否有重复
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
			msg = JsonUtils.toJsonString(agentInterface.checkAgentName(agent.getFullName(),agent.getShortName() ));
		} catch (UnsupportedEncodingException e) {
		}
		
		return SUCCESS;
	}
	
	/**
	 * 代理商手机号查询
	 * @return
	 */
	public String findByPhone(){
		if(agentInterface.findByPhone(getHttpRequest().getParameter("phone")) == null){
			msg = "true";
		}else {
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
			Agent agent = agentInterface.getAgent(agentNo);
			if(agent != null){
				Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
				if(agent.getParenId().equals(auth.getAgentNo())){
					msg = JsonUtils.toJsonString(agent);
				}else {
					msg = "NotMatch";
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

	public AgentInterface getAgentInterface() {
		return agentInterface;
	}

	public void setAgentInterface(AgentInterface agentInterface) {
		this.agentInterface = agentInterface;
	}

	public List<AgentFee> getAgentFees() {
		return agentFees;
	}

	public void setAgentFees(List<AgentFee> agentFees) {
		this.agentFees = agentFees;
	}

	public List<AgentSettle> getListAgentSettle() {
		return listAgentSettle;
	}

	public void setListAgentSettle(List<AgentSettle> listAgentSettle) {
		this.listAgentSettle = listAgentSettle;
	}

	public AgentSettle getAgentSettle() {
		return agentSettle;
	}

	public void setAgentSettle(AgentSettle agentSettle) {
		this.agentSettle = agentSettle;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public AgentCert getAgentCert() {
		return agentCert;
	}

	public void setAgentCert(AgentCert agentCert) {
		this.agentCert = agentCert;
	}

	public File getBusiLiceCert() {
		return busiLiceCert;
	}

	public void setBusiLiceCert(File busiLiceCert) {
		this.busiLiceCert = busiLiceCert;
	}

	public String getBusiLiceCertName() {
		return busiLiceCertName;
	}

	public void setBusiLiceCertName(String busiLiceCertName) {
		this.busiLiceCertName = busiLiceCertName;
	}

	public File getTaxRegCert() {
		return taxRegCert;
	}

	public void setTaxRegCert(File taxRegCert) {
		this.taxRegCert = taxRegCert;
	}

	public String getTaxRegCertName() {
		return taxRegCertName;
	}

	public void setTaxRegCertName(String taxRegCertName) {
		this.taxRegCertName = taxRegCertName;
	}

	public File getOrganizationCert() {
		return organizationCert;
	}

	public void setOrganizationCert(File organizationCert) {
		this.organizationCert = organizationCert;
	}

	public String getOrganizationCertName() {
		return organizationCertName;
	}

	public void setOrganizationCertName(String organizationCertName) {
		this.organizationCertName = organizationCertName;
	}

	public File getOpenBankAccCert() {
		return openBankAccCert;
	}

	public void setOpenBankAccCert(File openBankAccCert) {
		this.openBankAccCert = openBankAccCert;
	}

	public String getOpenBankAccCertName() {
		return openBankAccCertName;
	}

	public void setOpenBankAccCertName(String openBankAccCertName) {
		this.openBankAccCertName = openBankAccCertName;
	}

	public File getIdCard() {
		return idCard;
	}

	public void setIdCard(File idCard) {
		this.idCard = idCard;
	}

	public String getIdCardName() {
		return idCardName;
	}

	public void setIdCardName(String idCardName) {
		this.idCardName = idCardName;
	}

	public ReceiveConfigInfoBean getReceiveConfigInfoBean() {
		return receiveConfigInfoBean;
	}

	public void setReceiveConfigInfoBean(ReceiveConfigInfoBean receiveConfigInfoBean) {
		this.receiveConfigInfoBean = receiveConfigInfoBean;
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

	public static Properties getProp() {
		return prop;
	}

	public static void setProp(Properties prop) {
		AgentAction.prop = prop;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
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

	public FileName getFileName() {
		return fileName;
	}

	public void setFileName(FileName fileName) {
		this.fileName = fileName;
	}

	public InputStream getFileOutput() {
		return fileOutput;
	}

	public void setFileOutput(InputStream fileOutput) {
		this.fileOutput = fileOutput;
	}

	public AccountQueryInterface getAccountQueryInterface() {
		return accountQueryInterface;
	}

	public void setAccountQueryInterface(AccountQueryInterface accountQueryInterface) {
		this.accountQueryInterface = accountQueryInterface;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
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

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	
}