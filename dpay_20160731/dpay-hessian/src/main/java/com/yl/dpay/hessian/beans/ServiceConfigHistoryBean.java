package com.yl.dpay.hessian.beans;

import java.io.Serializable;

import com.yl.dpay.hessian.enums.FireType;
import com.yl.dpay.hessian.enums.Status;


/**
 * 代付配置历史服务Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月15日
 * @version V1.0.0
 */
public class ServiceConfigHistoryBean extends BaseBean implements Serializable {
	/**
	 * 持有者ID
	 */
	private String ownerId;
	/**
	 * 持有者角色
	 */
	private String ownerRole;
	/**
	 * 是否有效
	 */
	private String valid = "TRUE";
	/**
	 * 是否手动审核
	 */
	private String manualAudit = "FALSE"; // TRUE：自动审核
	/**
	 * 代付复核密码接收手机号
	 */
	private String phone; // 手动审核必输
	/**
	 * 代付复核密码
	 */
	private String complexPassword; // 手动审核必输

	/**
	 * 代付是否开通手机验证复核
	 */
	private String usePhoneCheck = "FALSE";
	
	/**
	 * 代付公钥
	 */
	private String publicKey;
	
	/**
	 * 代付私钥
	 */
	private String privateKey;
	
	/**
	 * ip
	 */
	private String custIp;
	
	/**
	 * 域名
	 */
	private String domain;
	
	/**
	 * 最小金额
	 */
	private double minAmount;
	
	/**
	 * 最大金额
	 */
	private double maxAmount;
	
	/**
	 * 假日付状态
	 */
	private String holidayStatus;

    /**
     * Column: 操作人
    @mbggenerated 2016-11-02 16:48:03
     */
    private String operator;
    /**
	 * 日最大交易额
	 */
	private double dayMaxAmount;
	/**
	 * 运营是否手动审核
	 */
	private String bossAudit = "FALSE"; // TRUE：自动审核
	private String startTime;		//代付开始时间
	private String endTime;			//代付结束时间
	/**
	 * 发起方式
	 */
	private String fireType;

    /**
     * Table: service_config_history
    @mbggenerated 2016-11-02 16:48:03
     */
    private static final long serialVersionUID = 1L;

	public void setMinAmount(double minAmount) {
		this.minAmount = minAmount;
	}

	public void setMaxAmount(double maxAmount) {
		this.maxAmount = maxAmount;
	}

	
	public double getMinAmount() {
		return minAmount;
	}

	public double getMaxAmount() {
		return maxAmount;
	}

	public String getManualAudit() {
		return manualAudit;
	}

	public void setManualAudit(String manualAudit) {
		this.manualAudit = manualAudit;
	}

	public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId == null ? null : ownerId.trim();
    }

    public String getOwnerRole() {
		return ownerRole;
	}

	public void setOwnerRole(String ownerRole) {
		this.ownerRole = ownerRole;
	}

	public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid == null ? null : valid.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getComplexPassword() {
        return complexPassword;
    }

    public void setComplexPassword(String complexPassword) {
        this.complexPassword = complexPassword == null ? null : complexPassword.trim();
    }

    public String getUsePhoneCheck() {
        return usePhoneCheck;
    }

    public void setUsePhoneCheck(String usePhoneCheck) {
        this.usePhoneCheck = usePhoneCheck == null ? null : usePhoneCheck.trim();
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey == null ? null : publicKey.trim();
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey == null ? null : privateKey.trim();
    }

    public String getCustIp() {
        return custIp;
    }

    public void setCustIp(String custIp) {
        this.custIp = custIp == null ? null : custIp.trim();
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }
    
    public String getHolidayStatus() {
		return holidayStatus;
	}

	public void setHolidayStatus(String holidayStatus) {
		this.holidayStatus = holidayStatus;
	}

	public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }
    
    public double getDayMaxAmount() {
		return dayMaxAmount;
	}

	public void setDayMaxAmount(double dayMaxAmount) {
		this.dayMaxAmount = dayMaxAmount;
	}

	public String getBossAudit() {
		return bossAudit;
	}

	public void setBossAudit(String bossAudit) {
		this.bossAudit = bossAudit;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getFireType() {
		return fireType;
	}

	public void setFireType(String fireType) {
		this.fireType = fireType;
	}

	public ServiceConfigHistoryBean(){}
    
    public ServiceConfigHistoryBean(ServiceConfigBean serviceConfigBean,String operator){
    	super();
    	super.setId(serviceConfigBean.getId());
    	super.setCreateDate(serviceConfigBean.getCreateDate());
    	super.setOptimistic(serviceConfigBean.getOptimistic());
    	this.ownerId = serviceConfigBean.getOwnerId();
		this.ownerRole = serviceConfigBean.getOwnerRole();
		this.valid = serviceConfigBean.getValid();
		this.manualAudit = serviceConfigBean.getManualAudit();
		this.phone = serviceConfigBean.getPhone();
		this.complexPassword = serviceConfigBean.getComplexPassword();
		this.usePhoneCheck = serviceConfigBean.getUsePhoneCheck();
		this.publicKey = serviceConfigBean.getPublicKey();
		this.privateKey = serviceConfigBean.getPrivateKey();
		this.custIp = serviceConfigBean.getCustIp();
		this.domain = serviceConfigBean.getDomain();
		this.minAmount = serviceConfigBean.getMinAmount();
		this.maxAmount = serviceConfigBean.getMaxAmount();
		this.holidayStatus = serviceConfigBean.getHolidayStatus();
		this.operator = operator;
		this.bossAudit=serviceConfigBean.getBossAudit();
		this.startTime=serviceConfigBean.getStartTime();
		this.endTime=serviceConfigBean.getEndTime();
		this.dayMaxAmount=serviceConfigBean.getDayMaxAmount();
		this.fireType=serviceConfigBean.getFireType();
    }
}