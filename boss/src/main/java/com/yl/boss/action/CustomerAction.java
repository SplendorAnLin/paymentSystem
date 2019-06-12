package com.yl.boss.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;
import com.pay.common.util.DateUtil;
import com.pay.common.util.StringUtil;
import com.yl.receive.hessian.ReceiveQueryFacade;
import com.yl.receive.hessian.bean.ReceiveConfigInfoBean;
import com.yl.account.api.bean.AccountBean;
import com.yl.account.api.bean.request.AccountQuery;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.UserRole;
import com.yl.agent.api.bean.Function;
import com.yl.agent.api.bean.Menu;
import com.yl.boss.Constant;
import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.Customer;
import com.yl.boss.entity.CustomerCert;
import com.yl.boss.entity.CustomerFee;
import com.yl.boss.entity.CustomerKey;
import com.yl.boss.entity.CustomerSettle;
import com.yl.boss.entity.Device;
import com.yl.boss.entity.Shop;
import com.yl.boss.enums.CustomerType;
import com.yl.boss.enums.FileName;
import com.yl.boss.service.AgentService;
import com.yl.boss.service.CustomerCertService;
import com.yl.boss.service.CustomerFeeService;
import com.yl.boss.service.CustomerService;
import com.yl.boss.service.CustomerSettleService;
import com.yl.boss.service.DeviceService;
import com.yl.boss.service.QRCodeService;
import com.yl.boss.service.ShopService;
import com.yl.boss.utils.CodeBuilder;
import com.yl.boss.utils.FileUtil;
import com.yl.boss.utils.ImgUtil;
import com.yl.customer.api.interfaces.CustFunctionInterface;
import com.yl.customer.api.interfaces.CustMenuInterface;
import com.yl.customer.api.interfaces.CustOperInterface;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.dpay.hessian.service.ServiceConfigFacade;
import com.yl.online.trade.hessian.bean.PartnerRouterBean;
import com.yl.online.trade.hessian.bean.PartnerRouterProfileBean;
import com.yl.realAuth.hessian.AuthConfigHessianService;
import com.yl.realAuth.hessian.bean.AuthConfigBean;
import com.yl.realAuth.hessian.enums.AuthBusiType;
import com.yl.realAuth.hessian.enums.AuthConfigStatus;
import com.yl.sms.SmsUtils;

/**
 * 商户信息控制器 
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月28日
 * @version V1.0.0
 */
public class CustomerAction extends Struts2ActionSupport {

	private static final Logger logger = LoggerFactory.getLogger(CustomerAction.class);
	private static final long serialVersionUID = -192274096751073176L;
	private CustomerService customerService;
	private CustomerFeeService customerFeeService;
	private CustomerSettleService customerSettleService;
	private CustomerCertService customerCertService;
	private DeviceService deviceService;
	private QRCodeService qRCodeService;
	private AccountQueryInterface accountQueryInterface;
	private ServiceConfigFacade serviceConfigFacade;
	private Customer customer = new Customer();
	private CustomerCert customerCert;
	private List<CustomerFee> customerFees;
	private List<CustomerKey> customerKeys;
	private CustomerSettle customerSettle;
	private List<String> routeCodes;
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
	private ServiceConfigBean serviceConfigBean;
	private List<PartnerRouterProfileBean> partnerProfiles;
	private InputStream fileOutput = null;// 图片
	private FileName fileName;
	private double balance;//可用余额
	private CustMenuInterface custMenuInterface;
	private CustFunctionInterface custFunctionInterface;
	private Page page;
	private Menu menu;
	private List<Menu> menuList;
	private String msg;
	private String menuName;
	private long id;
	/* 入账周期 */
	private int cycle;
	private Function function;
	private ReceiveConfigInfoBean receiveConfigInfoBean;
	private CustOperInterface custOperInterface;
	private AccountBean account;
	private ReceiveQueryFacade receiveQueryFacade;
	private Device device; 
	private AgentService agentService;
	private List<Shop> shopList;
	private ShopService shopService;
	private AuthConfigHessianService authConfigHessianService;
	private String configStatus;
	
	
	/**
	 * 催审通知
	 */
	public String auditUrging(){
		try {
			com.yl.boss.entity.Agent agent = agentService.findByNo(msg);
			SmsUtils.sendCustom(String.format(Constant.AGENT_AUDIT_URGING), agent.getPhoneNo());
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return SUCCESS;
	}
	
	
	/**
	 * 审核拒绝原因
	 * @return
	 */
	public String  findCustomerNo(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		device = deviceService.findByCustomerNo(msg);
		if(device != null){
			msg = "TRUE";
		} else {
			msg = "FLASE";
		}
		return SUCCESS;
	}
	
	
	public String findAll(){
		Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
		Map<String, Object> params = retrieveParams(requestMap);
		page=custFunctionInterface.findAll(params);
		return SUCCESS;
	}
	
	/**
	 * 新增功能
	 * @return
	 */
	public String addFunction(){
		custFunctionInterface.addFunction(JsonUtils.toJsonToObject(function, com.yl.customer.api.bean.Function.class));
		return SUCCESS;
	}
	
	public String findFunctionById(){
		function = JsonUtils.toJsonToObject(custFunctionInterface.findById(id), com.yl.agent.api.bean.Function.class);
		menuName=custMenuInterface.findById(Long.valueOf(function.getMenuId())).getName();
		return SUCCESS;
	}
	
	/**
	 * 编辑功能
	 * @return
	 */
	public String modify(){
		Function functionDb = JsonUtils.toJsonToObject(custFunctionInterface.findById(function.getId()), com.yl.agent.api.bean.Function.class);
		functionDb.setName(function.getName());
		functionDb.setAction(function.getAction());
		functionDb.setIsCheck(function.getIsCheck());
		functionDb.setStatus(function.getStatus());
		functionDb.setRemark(function.getRemark());
		functionDb.setMenuId(function.getMenuId());
		custFunctionInterface.modify(JsonUtils.toJsonToObject(functionDb, com.yl.customer.api.bean.Function.class));
		return SUCCESS;
	}
	
	/**
	 * 删除功能
	 * @return
	 */
	public String delete(){
		String functionId = getHttpRequest().getParameter("Id");
		Long id = Long.parseLong(functionId);
		custFunctionInterface.delete(id);
		return SUCCESS;
	}
	
	/**
	 * 新增
	 * @return
	 */
	public String customerAddChildMenu(){
		Menu menuDb = JsonUtils.toJsonToObject(custMenuInterface.findById(menu.getParentId()), com.yl.agent.api.bean.Menu.class);
		Long level = Long.parseLong(menuDb.getLevel());
		menu.setIsLeaf("Y");
		menu.setLevel(String.valueOf(level+1));
		menu = JsonUtils.toJsonToObject(custMenuInterface.create(JsonUtils.toJsonToObject(menu, com.yl.customer.api.bean.Menu.class)), com.yl.agent.api.bean.Menu.class);
		return SUCCESS;
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String customerModifyMenu(){
		Menu menuDb = JsonUtils.toJsonToObject(custMenuInterface.findById(menu.getId()), com.yl.agent.api.bean.Menu.class);
		menuDb.setName(menu.getName());
		menuDb.setUrl(menu.getUrl());
		menuDb.setDisplayOrder(menu.getDisplayOrder());
		menuDb.setStatus(menu.getStatus());
		menuDb.setParentId(menu.getParentId());
		menu = JsonUtils.toJsonToObject(custMenuInterface.update(JsonUtils.toJsonToObject(menuDb, com.yl.customer.api.bean.Menu.class)), com.yl.agent.api.bean.Menu.class);
		return SUCCESS;
	}
	public String findMenuByName() {
		try {
			menuList = JsonUtils.toJsonToObject(custMenuInterface.findMenuByName(java.net.URLDecoder.decode(menuName, "utf-8")), List.class);
			msg = JsonUtils.toJsonString(menuList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String findMenuName() {
		try {
			menu = JsonUtils.toJsonToObject(custMenuInterface.findMenuName(java.net.URLDecoder.decode(menuName, "utf-8")), com.yl.agent.api.bean.Menu.class);
			if (menu != null) {
				msg = JsonUtils.toJsonString(menu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 编辑
	 * @return
	 */
	public String toCustomerMenuEdit(){
		String menuId = getHttpRequest().getParameter("menuId");
		Long id = Long.parseLong(menuId);
		menu = JsonUtils.toJsonToObject(custMenuInterface.findById(id), com.yl.agent.api.bean.Menu.class);
		List<Menu> pMenus = JsonUtils.toJsonToObject(custMenuInterface.findByLevel("1"), List.class);
		getHttpRequest().setAttribute("pMenus", pMenus);
		return SUCCESS;
	}

	/**
	 * 商户菜单查询
	 * @return
	 */
	public String custMenuQuery() {
		try {
			menuList = JsonUtils.toJsonToObject(custMenuInterface.findAll(), List.class);
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
	
	/**
	 * 商户密码重置
	 * @return
	 */
	public String resetPassword(){
		try {
			customerService.resetPassWord(customer.getCustomerNo().toString());
			msg = "true";
		} catch (Exception e) {
			msg = "false";
			throw new RuntimeException("This is CustomerAction:resetCustomerPassWord customerNo:[" + customer.getCustomerNo() + "] is failed! exception:{}", e);
		}
		return SUCCESS;
	}

	private static Properties prop = new Properties();
	static {
		try {
			prop.load(new InputStreamReader(CustomerAction.class.getClassLoader().getResourceAsStream("system.properties")));
		} catch (IOException e) {
			logger.error("CustomerAction load Properties error:", e);
		}
	}

	public String openCustomer() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		String msg = null;
		String dateString = DateUtil.formatDate(new Date(), "yyyyMMddHHMMss");
		String proPath = prop.getProperty("customer.imgPath");
		String filePath = proPath + dateString + "/";
		try {
			if (customer.getCustomerType()==CustomerType.INDIVIDUAL) {
				customerCert=new CustomerCert();
			}
			if (attachment!=null) {
				msg = ImgUtil.checkImg(attachment);
				if (StringUtil.isNull(msg)) {
					String imgPath = "att-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(attachment, filePath, imgPath);
					customerCert.setAttachment(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			msg = ImgUtil.checkImg(busiLiceCert);
			if (StringUtil.isNull(msg)) {
				String imgPath = "bc-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
				FileUtil.copy(busiLiceCert, filePath, imgPath);
				customerCert.setBusiLiceCert(filePath + imgPath);
			} else {
				throw new Exception(msg);
			}
			msg = ImgUtil.checkImg(taxRegCert);
			if (StringUtil.isNull(msg)) {
				String imgPath = "tc-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
				FileUtil.copy(taxRegCert, filePath, imgPath);
				customerCert.setTaxRegCert(filePath + imgPath);
			} else {
				throw new Exception(msg);
			}
			msg = ImgUtil.checkImg(organizationCert);
			if (StringUtil.isNull(msg)) {
				String imgPath = "oc-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
				FileUtil.copy(organizationCert, filePath, imgPath);
				customerCert.setOrganizationCert(filePath + imgPath);
			} else {
				throw new Exception(msg);
			}
			msg = ImgUtil.checkImg(openBankAccCert);
			if (StringUtil.isNull(msg)) {
				String imgPath = "ac-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
				FileUtil.copy(openBankAccCert, filePath, imgPath);
				customerCert.setOpenBankAccCert(filePath + imgPath);
			} else {
				throw new Exception(msg);
			}
			msg = ImgUtil.checkImg(idCard);
			if (StringUtil.isNull(msg)) {
				String imgPath = "mp_img_" + System.currentTimeMillis() + ".jpg";
				FileUtil.copy(idCard, filePath, imgPath);
				customerCert.setIdCard(filePath + imgPath);
			} else {
				throw new Exception(msg);
			}
			PartnerRouterBean partnerRouterBean = new PartnerRouterBean();
			if (partnerProfiles != null) {
				partnerRouterBean.setPartnerCode(customer.getCustomerNo());
				partnerRouterBean.setStatus("TRUE");
				partnerRouterBean.setPartnerRole("CUSTOMER");
				for (int i = partnerProfiles.size() - 1; i >= 0; i--) {
					if (partnerProfiles.get(i) == null) {
						partnerProfiles.remove(i);
					}
				}
				partnerRouterBean.setProfiles(partnerProfiles);
			}
			customerService.create(customer, customerCert, customerFees, serviceConfigBean, customerSettle, auth.getRealname(), partnerRouterBean, cycle,receiveConfigInfoBean,shopList,configStatus);
		} catch (Exception e) {
			logger.error("创建商户失败:{}",e);
			return ERROR;
			// throw new RuntimeException("create customer:[" + customer.getShortName() + "] is failed! exception:{}", e);
		}
		return SUCCESS;
	}

	public String findByCustNo() {
		customerCert = customerCertService.findByCustNo(customer.getCustomerNo());
		customerSettle = customerSettleService.findByCustNo(customer.getCustomerNo());
		customer = customerService.findByCustNo(customer.getCustomerNo());
		customerFees = customerFeeService.findByCustNo(customer.getCustomerNo());
		AccountQuery accountQuery = new AccountQuery();
		accountQuery.setUserNo(customer.getCustomerNo());
		accountQuery.setUserRole(UserRole.CUSTOMER);
		if (accountQueryInterface.findAccountBy(accountQuery).getAccountBeans().size() > 0) {
			account = accountQueryInterface.findAccountBy(accountQuery).getAccountBeans().get(0);
			balance = account.getBalance();
		}
		return SUCCESS;
	}
	
	/**
	 * Ajax传入商户编号判断当前商户是否存在
	 * @return
	 */
	public String queryCustomerByCustNo(){
		customer = customerService.findByCustNo(customer.getCustomerNo());
		if(customer != null){
			msg = "true";
		}else {
			msg = "false";
		}
		return SUCCESS;
	}

	public AccountQueryInterface getAccountQueryInterface() {
		return accountQueryInterface;
	}

	public void setAccountQueryInterface(AccountQueryInterface accountQueryInterface) {
		this.accountQueryInterface = accountQueryInterface;
	}

	public String toUpdateCustomer() {
		customer = customerService.findByCustNo(customer.getCustomerNo());
		AccountQuery accountQuery = new AccountQuery();
		accountQuery.setUserNo(customer.getCustomerNo());
		accountQuery.setUserRole(UserRole.CUSTOMER);
		cycle=accountQueryInterface.findAccountBy(accountQuery).getAccountBeans().get(0).getCycle();
		customerCert = customerCertService.findByCustNo(customer.getCustomerNo());
		return SUCCESS;
	}

	public void checkShortName() {
		customer = customerService.findByShortName(customer.getShortName());
		if (customer == null) {
			write("TRUE");
		} else {
			write("FALSE");
		}
	}

	public void checkFullName() {
		customer = customerService.findByFullName(customer.getFullName());
		if (customer == null) {
			write("TRUE");
		} else {
			write("FALSE");
		}
	}

	/**
	 * 获取图片流
	 * 
	 * @return
	 */
	public String findCustomerDocumentImg() {

		// if(null==imgUrl||"".equals(imgUrl)){//如果指定的图片不存在，显示默认图片
		// imgUrl =
		// getSession().getServletContext().getRealPath("/")+"images"+File.separator+"123.jpg";
		// }
		try {
			if (customerCert != null && null != customerCert.getCustomerNo()) {
				customerCert = customerCertService.findByCustNo(customerCert.getCustomerNo());
				if (fileName.equals(FileName.FILE_BUSILICECERT)) {
					String imgUrl = customerCert.getBusiLiceCert();
					fileOutput = ImgUtil.findDocumentImgByName(imgUrl);
				}
				if (fileName.equals(FileName.FILE_IDCARD)) {
					String imgUrl = customerCert.getIdCard();
					fileOutput = ImgUtil.findDocumentImgByName(imgUrl);

				}
				if (fileName.equals(FileName.FILE_OPENBANKACCCERT)) {
					String imgUrl = customerCert.getOpenBankAccCert();
					fileOutput = ImgUtil.findDocumentImgByName(imgUrl);
				}
				if (fileName.equals(FileName.FILE_ORGANIZATIONCERT)) {
					String imgUrl = customerCert.getOrganizationCert();
					fileOutput = ImgUtil.findDocumentImgByName(imgUrl);
				}
				if (fileName.equals(FileName.FILE_TAXREGCERT)) {
					String imgUrl = customerCert.getTaxRegCert();
					fileOutput = ImgUtil.findDocumentImgByName(imgUrl);
				}
				if (fileName.equals(FileName.FILE_ATT)) {
					String imgUrl = customerCert.getAttachment();
					fileOutput = ImgUtil.findDocumentImgByName(imgUrl);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;

	}

	/**
	 * 商户修改
	 * 
	 * @return
	 * @author qiujian 2016年10月25日
	 */
	public String update() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		String msg = null;
		String dateString = DateUtil.formatDate(new Date(), "yyyyMMddHHMMss");
		String proPath = prop.getProperty("customer.imgPath");
		String filePath = proPath + dateString + "/";
		try {
			if (customer.getCustomerType()==CustomerType.INDIVIDUAL) {
				customerCert=new CustomerCert();
			}
			if (null != attachment) {
				msg = ImgUtil.checkImg(attachment);
				if (StringUtil.isNull(msg)) {
					String imgPath = "att-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(attachment, filePath, imgPath);
					customerCert.setAttachment(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			if (null != busiLiceCert) {
				msg = ImgUtil.checkImg(busiLiceCert);
				if (StringUtil.isNull(msg)) {
					String imgPath = "bc-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(busiLiceCert, filePath, imgPath);
					customerCert.setBusiLiceCert(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}

			}
			if (null != taxRegCert) {
				msg = ImgUtil.checkImg(taxRegCert);
				if (StringUtil.isNull(msg)) {
					String imgPath = "tc-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(taxRegCert, filePath, imgPath);
					customerCert.setTaxRegCert(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			if (null != organizationCert) {
				msg = ImgUtil.checkImg(organizationCert);
				if (StringUtil.isNull(msg)) {
					String imgPath = "oc-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(organizationCert, filePath, imgPath);
					customerCert.setOrganizationCert(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			if (null != openBankAccCert) {
				msg = ImgUtil.checkImg(openBankAccCert);
				if (StringUtil.isNull(msg)) {
					String imgPath = "ac-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(openBankAccCert, filePath, imgPath);
					customerCert.setOpenBankAccCert(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			if (null != idCard) {
				msg = ImgUtil.checkImg(idCard);
				if (StringUtil.isNull(msg)) {
					String imgPath = "mp_img_" + System.currentTimeMillis() + ".jpg";
					FileUtil.copy(idCard, filePath, imgPath);
					customerCert.setIdCard(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			customerService.update(customer, customerCert, auth.getRealname(),cycle);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("update customer is failed! exception:{}", e);
		}

		return SUCCESS;
	}

	public String auditCustomer() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		try {
			String lock = CacheUtils.get(Constant.LockPrefix1 + auth.getUsername() + "_" + customer.getCustomerNo(), String.class);
			if (StringUtils.isBlank(lock)) {
				logger.info("运营账号：" + auth.getCustomerno() + "，商户号：" + customer.getCustomerNo()  + " 加锁 ");
				CacheUtils.setEx(Constant.LockPrefix1 + auth.getCustomerno() + "_" + customer.getCustomerNo(), "lock", 120);
				PartnerRouterBean partnerRouterBean = new PartnerRouterBean();
				if (partnerProfiles != null) {
					partnerRouterBean.setPartnerCode(customer.getCustomerNo());
					partnerRouterBean.setStatus("TRUE");
					partnerRouterBean.setPartnerRole("CUSTOMER");
					for (int i = partnerProfiles.size() - 1; i >= 0; i--) {
						if (partnerProfiles.get(i) == null) {
							partnerProfiles.remove(i);
						}
					}
					partnerRouterBean.setProfiles(partnerProfiles);
				}
				// 实名认证路由
				if (null != configStatus) {
					String config = authConfigHessianService.queryAuthConfigByCustomerNo(customer.getCustomerNo());
					if (StringUtils.isBlank(config)) {
						Customer c = customerService.findByCustNo(customer.getCustomerNo());
						AuthConfigBean authConfigBean = new AuthConfigBean();
						authConfigBean.setBusiType(AuthBusiType.BINDCARD_AUTH);
						authConfigBean.setCode(CodeBuilder.build("ACI", "yyyyMMdd"));
						authConfigBean.setCreateTime(new Date());
						authConfigBean.setCustomerName(c.getShortName());
						authConfigBean.setCustomerNo(customer.getCustomerNo());
						authConfigBean.setLastUpdateTime(new Date());
						authConfigBean.setStatus(AuthConfigStatus.TRUE);
						authConfigBean.setRoutingTemplateCode(configStatus);
						authConfigHessianService.createAuthConfig(authConfigBean);
					}
				}
				customerService.updateForAudit(customer, customerFees, serviceConfigBean,receiveConfigInfoBean,partnerRouterBean, auth.getRealname(),msg,shopList);
				// 短信通知到服务商
				if(customer.getStatus().name().equals("AUDIT_REFUSE") && customer.getAgentNo() != null){
					com.yl.boss.entity.Agent agent = agentService.findByNo(customer.getAgentNo());
					SmsUtils.sendCustom(String.format(Constant.BOSS_AUDIT_REFUSE, customer.getCustomerNo(), msg), agent.getPhoneNo());
				}
			}else {
				logger.info("运营账号：" +  auth.getUsername() + "，商户号：" + customer.getCustomerNo() + "重复审核！");
			}
		}catch (Exception e) {
			logger.error("系统异常", e);
		}finally {
			CacheUtils.del(Constant.LockPrefix1 + auth.getCustomerno() + "_" + customer.getCustomerNo());
			logger.info("运营账号：" +  auth.getUsername() + "，商户号：" + customer.getCustomerNo() + " 解锁 ");
		}
		return "auditCustomer";
	}

	public String toAudit() {
		customer = customerService.findByCustNo(customer.getCustomerNo());
		customerFees = customerFeeService.findByCustNo(customer.getCustomerNo());
		customerSettle = customerSettleService.findByCustNo(customer.getCustomerNo());
		serviceConfigBean = serviceConfigFacade.query(customer.getCustomerNo());
		receiveConfigInfoBean = receiveQueryFacade.queryReceiveConfigBy(customer.getCustomerNo());
		shopList = shopService.queryShopList(customer.getCustomerNo());
		customerCert = customerCertService.findByCustNo(customer.getCustomerNo());
		return "toAudit";
	}

	/**
	 * @Description 卡识别
	 * @date 2016年10月30日
	 */
	public void toCachecenterIin() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String res = "";
		try {
			res = res + "cardNo=" + request.getParameterMap().get("cardNo")[0];
			String url = (String) prop.get("cachecenter.service.url") + "/iin/recognition.htm?" + res;
			String resData = HttpClientUtils.send(Method.POST, url, "", true, Charset.forName("UTF-8").name(), 6000);
			write(resData);
		} catch (IOException e) {
			logger.error("系统异常:", e);
		}
	}

	public void toCachecenterCnaps() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String res = "";
		try {
			res = res + "word=" + request.getParameterMap().get("word")[0];
			if (StringUtils.notBlank(request.getParameterMap().get("providerCode")[0])) {
				res = res + "&providerCode=" + request.getParameterMap().get("providerCode")[0];
			}
			if (StringUtils.notBlank(request.getParameterMap().get("clearBankLevel")[0])) {
				res = res + "&clearBankLevel=" + request.getParameterMap().get("clearBankLevel")[0];
			}
			String url = (String) prop.get("cachecenter.service.url") + "/cnaps/suggestSearch.htm?fields=providerCode&fields=name&fields=clearingBankCode&fields=providerCode";
			String resData = HttpClientUtils.send(Method.POST, url, res, true, Charset.forName("UTF-8").name(), 6000);
			write(resData);
		} catch (IOException e) {
			logger.error("系统异常:", e);
		}
	}

	public String queryCustomer() {

		return "queryCustomer";
	}
	
	/**
	 * 根据商户全称和简称，检查是否有重复
	 * @return
	 */
	public String checkCustomerName(){
		try {
			if(customer.getFullName() != null){
				customer.setFullName(URLDecoder.decode(customer.getFullName(), "UTF-8"));
			}
			if(customer.getShortName() != null){
				customer.setShortName(URLDecoder.decode(customer.getShortName(), "UTF-8"));
			}
			msg = JsonUtils.toJsonString(customerService.checkCustomerName(customer.getFullName(), customer.getShortName()));
		} catch (UnsupportedEncodingException e) {
		}
		return SUCCESS;
	}
	
	public String checkPhone() {
		Customer customer = customerService.findByPhone(getHttpRequest().getParameter("phone"));
		if (customer == null) {
			msg = "true";
		} else {
			msg = "false";
		}
		return SUCCESS;
	}
	
	/**
	 * 根据商户编号查询商户简称
	 * @return
	 */
	public String findShortNameByCustomerNo(){
		msg = customerService.findShortNameByCustomerNo(getHttpRequest().getParameter("customerNo"));
		return SUCCESS;
	}
	
	/**
     * 根据商户编号查询父级服务商编号
     * @return
     */
    public String queryAgentNoByCustomerNo(){
        String agentNo = customerService.queryAgentNoByCustomerNo(getHttpRequest().getParameter("customerNo"));
        if(agentNo != null){
            msg = agentNo;
        }else {
            msg = "false";
        }
        return SUCCESS;
    }
    
    /**
     * 根据商户编号查询当前商户是否存在
     * @return
     */
    public String customerNoBool(){
    	boolean customerNo = customerService.customerNoBool(getHttpRequest().getParameter("customerNo"));
        if(customerNo){
            msg = "true";
        }else {
            msg = "false";
        }
    	return SUCCESS;
    }
    
    public String fullNameByCustNo(){
    	String fullName = customerService.fullNameByCustNo(getHttpRequest().getParameter("customerNo"));
    	if(fullName != null){
    		msg = fullName;
    	}else {
    		msg = "false";
    	}
    	return SUCCESS;
    }
    
    /**
     * 查询当前商户是否存在，并且没有上级
     * @return
     */
    public String queryCustomerExistsAndNotSuperior(){
    	String customerNo = getHttpRequest().getParameter("customerNo");
    	if(customerNo != null){
    		customer = customerService.findByCustNo(customerNo);
    		if(customer != null){
    			if(customer.getAgentNo() != null && !customer.getAgentNo().equals("")){
    				msg = "superior";
    			}else {
    				msg = JsonUtils.toJsonString(customer);
    			}
    		}else {
    			msg = "false";
    		}
    	}else {
    		msg = "customerNo_null";
    	}
    	return SUCCESS;
    }

	public CustomerFeeService getCustomerFeeService() {
		return customerFeeService;
	}

	public void setCustomerFeeService(CustomerFeeService customerFeeService) {
		this.customerFeeService = customerFeeService;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CustomerCert getCustomerCert() {
		return customerCert;
	}

	public void setCustomerCert(CustomerCert customerCert) {
		this.customerCert = customerCert;
	}

	public List<CustomerFee> getCustomerFees() {
		return customerFees;
	}

	public void setCustomerFees(List<CustomerFee> customerFees) {
		this.customerFees = customerFees;
	}

	public List<CustomerKey> getCustomerKeys() {
		return customerKeys;
	}

	public void setCustomerKeys(List<CustomerKey> customerKeys) {
		this.customerKeys = customerKeys;
	}

	public CustomerSettle getCustomerSettle() {
		return customerSettle;
	}

	public void setCustomerSettle(CustomerSettle customerSettle) {
		this.customerSettle = customerSettle;
	}

	public File getBusiLiceCert() {
		return busiLiceCert;
	}

	public void setBusiLiceCert(File busiLiceCert) {
		this.busiLiceCert = busiLiceCert;
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

	public void setIdCard(File idCard) {
		this.idCard = idCard;
	}

	public List<String> getRouteCodes() {
		return routeCodes;
	}

	public void setRouteCodes(List<String> routeCodes) {
		this.routeCodes = routeCodes;
	}

	public String getBusiLiceCertName() {
		return busiLiceCertName;
	}

	public void setBusiLiceCertName(String busiLiceCertName) {
		this.busiLiceCertName = busiLiceCertName;
	}

	public String getTaxRegCertName() {
		return taxRegCertName;
	}

	public void setTaxRegCertName(String taxRegCertName) {
		this.taxRegCertName = taxRegCertName;
	}

	public String getOrganizationCertName() {
		return organizationCertName;
	}

	public void setOrganizationCertName(String organizationCertName) {
		this.organizationCertName = organizationCertName;
	}

	public String getOpenBankAccCertName() {
		return openBankAccCertName;
	}

	public void setOpenBankAccCertName(String openBankAccCertName) {
		this.openBankAccCertName = openBankAccCertName;
	}

	public String getIdCardName() {
		return idCardName;
	}

	public void setIdCardName(String idCardName) {
		this.idCardName = idCardName;
	}

	public ServiceConfigBean getServiceConfigBean() {
		return serviceConfigBean;
	}

	public void setServiceConfigBean(ServiceConfigBean serviceConfigBean) {
		this.serviceConfigBean = serviceConfigBean;
	}

	public List<PartnerRouterProfileBean> getPartnerProfiles() {
		return partnerProfiles;
	}

	public void setPartnerProfiles(List<PartnerRouterProfileBean> partnerProfiles) {
		this.partnerProfiles = partnerProfiles;
	}

	public CustomerSettleService getCustomerSettleService() {
		return customerSettleService;
	}

	public void setCustomerSettleService(CustomerSettleService customerSettleService) {
		this.customerSettleService = customerSettleService;
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

	public void setCustomerCertService(CustomerCertService customerCertService) {
		this.customerCertService = customerCertService;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	public void setServiceConfigFacade(ServiceConfigFacade serviceConfigFacade) {
		this.serviceConfigFacade = serviceConfigFacade;
	}


	public void setCustMenuInterface(CustMenuInterface custMenuInterface) {
		this.custMenuInterface = custMenuInterface;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public void setCustFunctionInterface(CustFunctionInterface custFunctionInterface) {
		this.custFunctionInterface = custFunctionInterface;
	}

	public Page getPage() {
		return page;
	}

	public CustOperInterface getCustOperInterface() {
		return custOperInterface;
	}

	public void setCustOperInterface(CustOperInterface custOperInterface) {
		this.custOperInterface = custOperInterface;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public long getId() {
		return id;
	}

	public AccountBean getAccount() {
		return account;
	}

	public void setAccount(AccountBean account) {
		this.account = account;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ReceiveConfigInfoBean getReceiveConfigInfoBean() {
		return receiveConfigInfoBean;
	}

	public void setReceiveConfigInfoBean(ReceiveConfigInfoBean receiveConfigInfoBean) {
		this.receiveConfigInfoBean = receiveConfigInfoBean;
	}

	public ReceiveQueryFacade getReceiveQueryFacade() {
		return receiveQueryFacade;
	}

	public void setReceiveQueryFacade(ReceiveQueryFacade receiveQueryFacade) {
		this.receiveQueryFacade = receiveQueryFacade;
	}

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public QRCodeService getqRCodeService() {
		return qRCodeService;
	}

	public void setqRCodeService(QRCodeService qRCodeService) {
		this.qRCodeService = qRCodeService;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public AgentService getAgentService() {
		return agentService;
	}

	public void setAgentService(AgentService agentService) {
		this.agentService = agentService;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}

	public ShopService getShopService() {
		return shopService;
	}

	public CustomerCertService getCustomerCertService() {
		return customerCertService;
	}

	public ServiceConfigFacade getServiceConfigFacade() {
		return serviceConfigFacade;
	}

	public File getAttachment() {
		return attachment;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public CustMenuInterface getCustMenuInterface() {
		return custMenuInterface;
	}

	public CustFunctionInterface getCustFunctionInterface() {
		return custFunctionInterface;
	}

	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}

	public AuthConfigHessianService getAuthConfigHessianService() {
		return authConfigHessianService;
	}

	public void setAuthConfigHessianService(AuthConfigHessianService authConfigHessianService) {
		this.authConfigHessianService = authConfigHessianService;
	}

	public String getConfigStatus() {
		return configStatus;
	}

	public void setConfigStatus(String configStatus) {
		this.configStatus = configStatus;
	}
}