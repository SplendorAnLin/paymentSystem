package com.yl.agent.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;
import com.pay.common.util.DateUtil;
import com.pay.common.util.StringUtil;
import com.yl.agent.Constant;
import com.yl.agent.entity.Authorization;
import com.yl.agent.enums.FileName;
import com.yl.agent.utils.FileUtil;
import com.yl.agent.utils.ImgUtil;
import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.bean.CustomerCertBean;
import com.yl.boss.api.bean.CustomerFee;
import com.yl.boss.api.bean.CustomerSettle;
import com.yl.boss.api.bean.Shop;
import com.yl.boss.api.enums.CustomerBeanType;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.boss.api.interfaces.ShopInterface;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.receive.hessian.ReceiveQueryFacade;
import com.yl.receive.hessian.bean.ReceiveConfigInfoBean;

import net.sf.json.JSONObject;

/**
 * 商户控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月26日
 * @version V1.0.0
 */
public class CustomerAction extends Struts2ActionSupport {
	private static final long serialVersionUID = -192274096751073176L;

	private CustomerInterface customerInterface;
	private ReceiveConfigInfoBean receiveConfigInfoBean;
	private CustomerCertBean customerCertBean;
	private List<CustomerFee> customerFees;
	private Customer customer;
	private ServiceConfigBean serviceConfigBean;
	private CustomerSettle customerSettle;
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
	private int cycle;
	private String phone;
	private Page page;
	private CustomerCertBean customerCert;
	private InputStream fileOutput=null;//图片
	private FileName fileName;
	private String msg;
	private ReceiveQueryFacade receiveQueryFacade;
	private String custMsg;
	private List<Shop> shopList;
	private ShopInterface shopInterface;
	//	private File busiLiceCert; // 企业营业执照|个人身份证正面
	//	private String busiLiceCertName; // 企业营业执照|个人身份证正面
	//	private File taxRegCert; // 企业税务登记证|个人身份证反面
	//	private String taxRegCertName; // 企业税务登记证|个人身份证反面
	//	private File organizationCert; // 组织机构证|个人银行卡正面
	//	private String organizationCertName; // 组织机构证|个人银行卡正面
	//	private File openBankAccCert; // 银行开户许可证|个人银行卡反面
	//	private String openBankAccCertName; // 银行开户许可证|个人银行卡反面
	//	private File idCard; // 企业法人身份证|个人手持身份证
	//	private String idCardName; // 企业法人身份证|个人手持身份证

	private static Properties prop = new Properties();
	static {
		try {
			prop.load(new InputStreamReader(CustomerAction.class.getClassLoader().getResourceAsStream("system.properties")));
		} catch (IOException e) {
			logger.error("CustomerAction load Properties error:", e);
		}
	}

	/**
	 *  
	 */
	public String checkShortName(){
		msg = customerInterface.checkShortName(getHttpRequest().getParameter("shortName"));
		if(msg != null){
			msg ="TURE";
		}
		return SUCCESS;
	};
	
	public String checkPhone(){
		try {
			if (customerInterface.getCustomerByPhone(phone)==null) {
				msg ="true";
			}else {
				msg ="false";
			}
		} catch (Exception e) {
			msg ="false";
		}
		return SUCCESS;
	};

	/**
	 * @Description 商户入网
	 * @return
	 * @date 2016年10月23日 下午9:08:55
	 */
	public String openCustomer() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		String msg = null;
		String dateString = DateUtil.formatDate(new Date(), "yyyyMMddHHMMss");
		String proPath = prop.getProperty("customer.imgPath");
		String filePath = proPath + dateString + "/";
		customer.setAgentNo(auth.getAgentNo());
		try {
			if (customer.getCustomerType().toString().equals(CustomerBeanType.INDIVIDUAL.toString())) {
				customerCertBean=new CustomerCertBean();
			}
			if (attachment!=null) {
				msg = ImgUtil.checkImg(attachment);
				if (StringUtil.isNull(msg)) {
					String imgPath = "att-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(attachment, filePath, imgPath);
					customerCertBean.setAttachment(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			msg = ImgUtil.checkImg(busiLiceCert);
			if (StringUtil.isNull(msg)) {
				String imgPath = "bc-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
				FileUtil.copy(busiLiceCert, filePath, imgPath);
				customerCertBean.setBusiLiceCert(filePath + imgPath);
			} else {
				throw new Exception(msg);
			}

			msg = ImgUtil.checkImg(taxRegCert);
			if (StringUtil.isNull(msg)) {
				String imgPath = "tc-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
				FileUtil.copy(taxRegCert, filePath, imgPath);
				customerCertBean.setTaxRegCert(filePath + imgPath);
			} else {
				throw new Exception(msg);
			}
			msg = ImgUtil.checkImg(organizationCert);
			if (StringUtil.isNull(msg)) {
				String imgPath = "oc-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
				FileUtil.copy(organizationCert, filePath, imgPath);
				customerCertBean.setOrganizationCert(filePath + imgPath);
			} else {
				throw new Exception(msg);
			}
			msg = ImgUtil.checkImg(openBankAccCert);
			if (StringUtil.isNull(msg)) {
				String imgPath = "ac-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
				FileUtil.copy(openBankAccCert, filePath, imgPath);
				customerCertBean.setOpenBankAccCert(filePath + imgPath);
			} else {
				throw new Exception(msg);
			}
			msg = ImgUtil.checkImg(idCard);
			if (StringUtil.isNull(msg)) {
				String imgPath = "mp_img_" + System.currentTimeMillis() + ".jpg";
				FileUtil.copy(idCard, filePath, imgPath);
				customerCertBean.setIdCard(filePath + imgPath);
			} else {
				throw new Exception(msg);
			}
			
/*			if(serviceConfigBean != null){
				serviceConfigBean.setOwnerRole("AGENT");
			}*/
			String configBean = JsonUtils.toJsonString(serviceConfigBean);
			String receiveConfigInfo=JsonUtils.toJsonString(receiveConfigInfoBean);
			customerInterface.openCustomer(customer, customerCertBean, customerFees, configBean,receiveConfigInfo,customerSettle, auth.getRealname(), "agent", cycle,shopList);
		} catch (Exception e) {
			return ERROR;
			//throw new RuntimeException("create customer:[" + customer.getShortName() + "] is failed! exception:{}", e);
		}
		return SUCCESS;
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
			res = res + "word=" + request.getParameterMap().get("word")[0] + "&";
			res = res + "providerCode=" + request.getParameterMap().get("providerCode")[0] + "&";
			res = res + "clearBankLevel=" + request.getParameterMap().get("clearBankLevel")[0];
			String url = (String) prop.get("cachecenter.service.url") + "/cnaps/suggestSearch.htm?fields=providerCode&fields=name&fields=clearingBankCode&fields=providerCode";
			String resData = HttpClientUtils.send(Method.POST, url, res, true, Charset.forName("UTF-8").name(), 6000);
			write(resData);
		} catch (IOException e) {
			logger.error("系统异常:", e);
		}
	}


	/**
	 *商户查询
	 */
	public String customerQuery() {

		try {
			String queryId = "customerInfoAgent";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			params.put("AGENT_NO", auth.getAgentNo());
			if(getHttpRequest().getParameter("currentPage") != null && getHttpRequest().getParameter("currentPage") != ""){
				params.put("pagingPage", getHttpRequest().getParameter("currentPage"));
			}
			page = customerInterface.queryCustomerByAgentNo(queryId, params);
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;

	}

	/**
	 * 跳转到修改页面从boss系统获取商户信息
	 * @return
	 * @author qiujian
	 * 2016年10月30日
	 */
	public String toUpdateCustomer() {
		try {
			customer = new Customer();
			customerCert = new CustomerCertBean();
			customerSettle = new CustomerSettle();
			customerFees = new ArrayList<CustomerFee>();
			receiveConfigInfoBean=new ReceiveConfigInfoBean();
			shopList = new ArrayList<Shop>();
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			if(null!=params&&null!=params.get("customerNo")){
				Map<String, Object> bossReturnMap= customerInterface.toUpdateCustomer(params);
				if(null!=bossReturnMap&&bossReturnMap.size()>0){
					customer =  (Customer)bossReturnMap.get("customer");
					customerCert =  (CustomerCertBean)bossReturnMap.get("customerCert");
					customerSettle =  (CustomerSettle)bossReturnMap.get("customerSettle");
					customerFees  = (List<CustomerFee>) bossReturnMap.get("customerFees");
					receiveConfigInfoBean=(ReceiveConfigInfoBean)bossReturnMap.get("receiveConfigInfoBean");
					serviceConfigBean =  (ServiceConfigBean)bossReturnMap.get("serviceConfigBean");
					cycle =   (int) bossReturnMap.get("cycle");
					shopList = shopInterface.queryShopList(params.get("customerNo").toString());
				}

			}
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}


	/**
	 * 代理商系统修改商户信息
	 * @return
	 * @author qiujian
	 * 2016年10月31日
	 */
	public String updateCustomer(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		String msg = null;
		String dateString = DateUtil.formatDate(new Date(), "yyyyMMddHHMMss");
		String proPath = prop.getProperty("customer.imgPath");
		String filePath = proPath + dateString + "/";
		try {
			if (customer.getCustomerType().toString().equals(CustomerBeanType.INDIVIDUAL.toString())) {
				customerCert=new CustomerCertBean();
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
			if(null!=busiLiceCert){
				msg = ImgUtil.checkImg(busiLiceCert);
				if (StringUtil.isNull(msg)) {
					String imgPath = "bc-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(busiLiceCert, filePath, imgPath);
					customerCert.setBusiLiceCert(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}

			}
			if(null!=taxRegCert){
				msg = ImgUtil.checkImg(taxRegCert);
				if (StringUtil.isNull(msg)) {
					String imgPath = "tc-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(taxRegCert, filePath, imgPath);
					customerCert.setTaxRegCert(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			if(null!=organizationCert){
				msg = ImgUtil.checkImg(organizationCert);
				if (StringUtil.isNull(msg)) {
					String imgPath = "oc-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(organizationCert, filePath, imgPath);
					customerCert.setOrganizationCert(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			if(null!=openBankAccCert){
				msg = ImgUtil.checkImg(openBankAccCert);
				if (StringUtil.isNull(msg)) {
					String imgPath = "ac-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(openBankAccCert, filePath, imgPath);
					customerCert.setOpenBankAccCert(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			if(null!=idCard){
				msg = ImgUtil.checkImg(idCard);
				if (StringUtil.isNull(msg)) {
					String imgPath = "mp_img_" + System.currentTimeMillis() + ".jpg";
					FileUtil.copy(idCard, filePath, imgPath);
					customerCert.setIdCard(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}


			String configBean = JsonUtils.toJsonString(serviceConfigBean);
			String receiveConfigInfo=JsonUtils.toJsonString(receiveConfigInfoBean);
			customerInterface.updateCustomer(customer,configBean, customerCert, customerSettle,customerFees, auth.getRealname(),receiveConfigInfo, "agent", cycle,shopList);

		}  catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("update customer is failed! exception:{}", e);
		}

		return SUCCESS;

	}

	/**
	 * 代理商下的商户：获取图片
	 * @return
	 * @author qiujian
	 * 2016年10月31日
	 */
	public String findCustomerDocumentImg() {  

		//	    if(null==imgUrl||"".equals(imgUrl)){//如果指定的图片不存在，显示默认图片  
		//	        imgUrl = getSession().getServletContext().getRealPath("/")+"images"+File.separator+"123.jpg";  
		//	    }  
		try {

			if(customerCert!=null&&null!=customerCert.getCustomerNo()){
				Map<String, Object> params =new HashMap<String, Object>();
				params.put("customerNo",customerCert.getCustomerNo());
				Map<String, Object> bossReturnMap= customerInterface.toUpdateCustomer(params);
				if(null!=bossReturnMap&&bossReturnMap.size()>0){
					customerCert =  (CustomerCertBean) bossReturnMap.get("customerCert");
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
						fileOutput =ImgUtil.findDocumentImgByName(imgUrl);
					}
					if (fileName.equals(FileName.FILE_ORGANIZATIONCERT)) {
						String imgUrl = customerCert.getOrganizationCert();
						fileOutput = ImgUtil.findDocumentImgByName(imgUrl);;

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
			}
		} catch (Exception e) { 
			e.printStackTrace(); 
		}  
		return SUCCESS;  

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
			msg = JsonUtils.toJsonString(customerInterface.checkCustomerName(customer.getFullName(), customer.getShortName()));
		} catch (UnsupportedEncodingException e) {
		}
		return SUCCESS;
	}

	/**
	 * 查询商户审核信息
	 * @return
	 */
	public String customerAuditQuery(){
		try {
			String queryId = "customerInfo";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			params.put("AGENT_NO", auth.getAgentNo());
			params.put("status", "AGENT_AUDIT");
			if(getHttpRequest().getParameter("currentPage") != null && getHttpRequest().getParameter("currentPage") != ""){
				params.put("pagingPage", getHttpRequest().getParameter("currentPage"));
			}
			page = customerInterface.queryCustomerByAgentNo(queryId, params);
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 商户审核
	 * @return
	 */
	public String auditCustomerAction(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		String msg = null;
		String dateString = DateUtil.formatDate(new Date(), "yyyyMMddHHMMss");
		String proPath = prop.getProperty("customer.imgPath");
		String filePath = proPath + dateString + "/";
		try {
			getHttpRequest().setCharacterEncoding("utf-8");
			if (customer.getCustomerType().toString().equals(CustomerBeanType.INDIVIDUAL.toString())) {
				customerCert=new CustomerCertBean();
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
			if(null!=busiLiceCert){
				msg = ImgUtil.checkImg(busiLiceCert);
				if (StringUtil.isNull(msg)) {
					String imgPath = "bc-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(busiLiceCert, filePath, imgPath);
					customerCert.setBusiLiceCert(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}

			}
			if(null!=taxRegCert){
				msg = ImgUtil.checkImg(taxRegCert);
				if (StringUtil.isNull(msg)) {
					String imgPath = "tc-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(taxRegCert, filePath, imgPath);
					customerCert.setTaxRegCert(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			if(null!=organizationCert){
				msg = ImgUtil.checkImg(organizationCert);
				if (StringUtil.isNull(msg)) {
					String imgPath = "oc-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(organizationCert, filePath, imgPath);
					customerCert.setOrganizationCert(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			if(null!=openBankAccCert){
				msg = ImgUtil.checkImg(openBankAccCert);
				if (StringUtil.isNull(msg)) {
					String imgPath = "ac-" + customer.getPhoneNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(openBankAccCert, filePath, imgPath);
					customerCert.setOpenBankAccCert(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			if(null!=idCard){
				msg = ImgUtil.checkImg(idCard);
				if (StringUtil.isNull(msg)) {
					String imgPath = "mp_img_" + System.currentTimeMillis() + ".jpg";
					FileUtil.copy(idCard, filePath, imgPath);
					customerCert.setIdCard(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}


			String configBean = JsonUtils.toJsonString(serviceConfigBean);
			String receiveConfigInfo=JsonUtils.toJsonString(receiveConfigInfoBean);
			customerInterface.auditCustomerAction(customer,configBean, customerCert, customerSettle,customerFees, auth.getRealname(),receiveConfigInfo, "agent", cycle,custMsg,shopList);

		}  catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("update customer is failed! exception:{}", e);
		}

		return SUCCESS;
	}
	
	/**
	 * 获取最近一次拒绝信息
	 * @return
	 */
	public String findCustomerNo(){
		if(customer != null && customer.getCustomerNo() != null){
			msg = customerInterface.getLastInfo(customer.getCustomerNo());
		}else {
			msg = null;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 根据商户编号查询商户简称
	 * @return
	 */
	public String findShortNameByCustomerNo(){
		msg = customerInterface.findShortNameByCustomerNo(getHttpRequest().getParameter("customerNo"));
		return SUCCESS;
	}
	
    /**
     * 查询当前商户是否存在，并且是否所属当前服务商
     * @return
     */
    public String queryCustomerExistsAndNotSuperior(){
    	String customerNo = getHttpRequest().getParameter("customerNo");
    	if(customerNo != null){
    		customer = customerInterface.getCustomer(customerNo);
    		if(customer != null){
    			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
    			
    			if(customer.getAgentNo().equals(auth.getAgentNo())){
					msg = JsonUtils.toJsonString(customer);
				}else {
					msg = "notSuperior";
				}
    		}else {
    			msg = "false";
    		}
    	}else {
    		msg = "customerNo_null";
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

	public CustomerCertBean getCustomerCertBean() {
		return customerCertBean;
	}

	public void setCustomerCertBean(CustomerCertBean customerCertBean) {
		this.customerCertBean = customerCertBean;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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

	public CustomerInterface getCustomerInterface() {
		return customerInterface;
	}

	public void setCustomerInterface(CustomerInterface customerInterface) {
		this.customerInterface = customerInterface;
	}

	public List<CustomerFee> getCustomerFees() {
		return customerFees;
	}

	public void setCustomerFees(List<CustomerFee> customerFees) {
		this.customerFees = customerFees;
	}

	public ServiceConfigBean getServiceConfigBean() {
		return serviceConfigBean;
	}

	public void setServiceConfigBean(ServiceConfigBean serviceConfigBean) {
		this.serviceConfigBean = serviceConfigBean;
	}

	public CustomerSettle getCustomerSettle() {
		return customerSettle;
	}

	public void setCustomerSettle(CustomerSettle customerSettle) {
		this.customerSettle = customerSettle;
	}

	public int getCycle() {
		return cycle;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public CustomerCertBean getCustomerCert() {
		return customerCert;
	}

	public void setCustomerCert(CustomerCertBean customerCert) {
		this.customerCert = customerCert;
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ReceiveQueryFacade getReceiveQueryFacade() {
		return receiveQueryFacade;
	}

	public void setReceiveQueryFacade(ReceiveQueryFacade receiveQueryFacade) {
		this.receiveQueryFacade = receiveQueryFacade;
	}

	public ReceiveConfigInfoBean getReceiveConfigInfoBean() {
		return receiveConfigInfoBean;
	}

	public void setReceiveConfigInfoBean(ReceiveConfigInfoBean receiveConfigInfoBean) {
		this.receiveConfigInfoBean = receiveConfigInfoBean;
	}

	public String getCustMsg() {
		return custMsg;
	}

	public void setCustMsg(String custMsg) {
		this.custMsg = custMsg;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}

	public ShopInterface getShopInterface() {
		return shopInterface;
	}

	public void setShopInterface(ShopInterface shopInterface) {
		this.shopInterface = shopInterface;
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
