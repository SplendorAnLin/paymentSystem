/**
 * 
 */
package com.yl.account.api.bean.request;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

/**
 * 具体业务处理请求
 * 
 * @author 聚合支付有限公司
 * @since 2016年2月24日
 * @version V1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = Id.MINIMAL_CLASS, property = "class")
public abstract class TradeVoucher implements Serializable {

	private static final long serialVersionUID = -4482850012995014741L;

}
