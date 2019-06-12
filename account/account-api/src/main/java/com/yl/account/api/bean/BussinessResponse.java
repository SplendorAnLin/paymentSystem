package com.yl.account.api.bean;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.yl.account.api.enums.HandlerResult;

/**
 * 响应消息父类
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月15日
 * @version V1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BussinessResponse implements Serializable {

	private static final long serialVersionUID = 7196580938870111760L;

	/** 处理结果 */
	private HandlerResult result;
	/** 处理结果描述 */
	private String resultMsg;
	/** 处理完成时间 */
	private Date finishTime;

	public HandlerResult getResult() {
		return result;
	}

	public void setResult(HandlerResult result) {
		this.result = result;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

}
