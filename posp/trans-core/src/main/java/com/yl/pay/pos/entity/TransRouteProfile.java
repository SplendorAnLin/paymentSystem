package com.yl.pay.pos.entity;

import com.yl.pay.pos.bean.BankChannelAssignABean;
import com.yl.pay.pos.bean.BankChannelAssignBBean;
import com.yl.pay.pos.bean.TransAttributeBean;
import com.yl.pay.pos.enums.BankChannelChooseType;
import com.yl.pay.pos.enums.Status;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: 路由模版交易属性相关配置
 * Description: 
 * Copyright: 
 * Company: com.yl.pay
 * @author jun
 */
@Entity
@Table(name = "TRANS_ROUTE_PROFILE")
public class TransRouteProfile extends BaseEntity {

	private static final long serialVersionUID = 7672214073120478233L;
	private String code; 									// 编号
	private String transRouteCode; 							// 路由模版编号
	private TransAttributeBean transAttributeBean; 			// 交易属性
	private List<BankChannelAssignABean> oneBeans;
	private BankChannelChooseType chooseType;	 			// 通道选择类型
	private Date createTime; 								// 创建时间
	private Status status;									//状态

	@Column(name = "CODE", length = 30)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "TRANS_ROUTE_CODE", length = 30)
	public String getTransRouteCode() {
		return transRouteCode;
	}

	public void setTransRouteCode(String transRouteCode) {
		this.transRouteCode = transRouteCode;
	}

	@Column(name = "TRANS_ATTRIBUTE", length = 512)
	public String getTransAttribute() {
		if (transAttributeBean == null) {
			return null;
		}
		return JSONObject.fromObject(transAttributeBean).toString();
	}

	public void setTransAttribute(String transAttribute) {
		this.transAttributeBean = (TransAttributeBean) JSONObject.toBean(JSONObject.fromObject(transAttribute), TransAttributeBean.class);
	}

	@Transient
	public TransAttributeBean getTransAttributeBean() {
		return transAttributeBean;
	}

	public void setTransAttributeBean(TransAttributeBean transAttributeBean) {
		this.transAttributeBean = transAttributeBean;
	}

	@Column(name = "CHANNEL_ASSIGN_DATA", length = 2048)
	public String getBankChannelAssignData() {
		if (oneBeans == null) {
			return null;
		}
		return JSONArray.fromObject(oneBeans).toString();
	}

	@SuppressWarnings("unchecked")
	public void setBankChannelAssignData(String bankChannelAssignData) {
		Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
		classMap.put("twoBeans", BankChannelAssignBBean.class);
		this.oneBeans = JSONArray.toList(JSONArray.fromObject(bankChannelAssignData), BankChannelAssignABean.class, classMap);
	}

	@Transient
	public List<BankChannelAssignABean> getOneBeans() {
		return oneBeans;
	}

	public void setOneBeans(List<BankChannelAssignABean> oneBeans) {
		this.oneBeans = oneBeans;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "CHOOSE_TYPE", columnDefinition = "VARCHAR(30)")
	public BankChannelChooseType getChooseType() {
		return chooseType;
	}

	public void setChooseType(BankChannelChooseType chooseType) {
		this.chooseType = chooseType;
	}

	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(30)")
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

}
