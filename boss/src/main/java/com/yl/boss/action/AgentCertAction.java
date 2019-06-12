package com.yl.boss.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.pay.common.util.DateUtil;
import com.pay.common.util.StringUtil;
import com.yl.boss.Constant;
import com.yl.boss.entity.AgentCert;
import com.yl.boss.entity.Authorization;
import com.yl.boss.service.AgentCertService;
import com.yl.boss.utils.FileUtil;
import com.yl.boss.utils.ImgUtil;

/**
 * 服务商证件控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月28日
 * @version V1.0.0
 */
public class AgentCertAction extends Struts2ActionSupport {

	private static final long serialVersionUID = -3058847613636230888L;
	private AgentCertService agentCertService;
	private AgentCert agentCert;
	private File busiLiceCert;			//企业营业执照|个人身份证正面
	private File taxRegCert;			//企业税务登记证|个人身份证反面
	private File organizationCert;		//组织机构证|个人银行卡正面
	private File openBankAccCert;		//银行开户许可证|个人银行卡反面
	private File idCard;				//企业法人身份证|个人手持身份证
	private String imgPath;
	
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(new InputStreamReader(CustomerAction.class.getClassLoader().getResourceAsStream("system.properties")));
		} catch (IOException e) {
			logger.error("AgentCertAction load Properties error:", e);
		}
	}
	
	public String update(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		String msg = null;
		String dateString = DateUtil.formatDate(new Date(), "yyyyMMddHHMMss");
		String proPath = prop.getProperty("agent.imgPath");
		String filePath = proPath + dateString + "/";
		
		try {
			msg=ImgUtil.checkImg(busiLiceCert);
			if(StringUtil.isNull(msg)){
				String imgPath = "bc-" + agentCert.getAgentNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
				FileUtil.copy(busiLiceCert, proPath + filePath, imgPath);
				agentCert.setBusiLiceCert(filePath + imgPath);
			}else{
				throw new Exception(msg);
			}
			
			msg=ImgUtil.checkImg(taxRegCert);
			if(StringUtil.isNull(msg)){
				String imgPath = "tc-" + agentCert.getAgentNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
				FileUtil.copy(taxRegCert, proPath + filePath, imgPath);
				agentCert.setTaxRegCert(filePath + imgPath);
			}else{
				throw new Exception(msg);
			}
			msg=ImgUtil.checkImg(organizationCert);
			if(StringUtil.isNull(msg)){
				String imgPath = "oc-" + agentCert.getAgentNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
				FileUtil.copy(organizationCert, proPath + filePath, imgPath);
				agentCert.setOrganizationCert(filePath + imgPath);
			}else{
				throw new Exception(msg);
			}
			msg=ImgUtil.checkImg(openBankAccCert);
			if(StringUtil.isNull(msg)){
				String imgPath = "ac-" + agentCert.getAgentNo() + "-" + Long.toHexString(System.nanoTime()) + ".jpg";
				FileUtil.copy(openBankAccCert, proPath + filePath, imgPath);
				agentCert.setOpenBankAccCert(filePath + imgPath);
			}else{
				throw new Exception(msg);
			}
			msg=ImgUtil.checkImg(idCard);
			if(StringUtil.isNull(msg)){
				String imgPath = "mp_img_" + System.currentTimeMillis() +".jpg";
				FileUtil.copy(idCard, proPath + filePath, imgPath);
				agentCert.setIdCard(filePath + imgPath);
			}else{
				throw new Exception(msg);
			}

			agentCertService.update(agentCert, auth.getRealname());
		} catch (Exception e) {
			throw new RuntimeException("update agentCert:["+agentCert.getAgentNo()+"] is failed! exception:{}", e);
		}
		return SUCCESS;
	}
	
	public String findByCustNo(){
		agentCert = agentCertService.findByAgentNo(agentCert.getAgentNo());
		return SUCCESS;
	}
	
	public void showImg() throws Exception {
	    HttpServletResponse response=ServletActionContext.getResponse(); 
	    response.setContentType("image/gif"); 
		byte[] image = FileUtil.input(imgPath);
	    ServletOutputStream out=response.getOutputStream(); 
	    out.write(image);
	    out.close();
	}

	public AgentCertService getAgentCertService() {
		return agentCertService;
	}

	public void setAgentCertService(AgentCertService agentCertService) {
		this.agentCertService = agentCertService;
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

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
}
