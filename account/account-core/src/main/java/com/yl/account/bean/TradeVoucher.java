/**
 * 
 */
package com.yl.account.bean;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

/**
 * 交易凭证信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月22日
 * @version V1.0.0
 */
@JsonTypeInfo(use = Id.MINIMAL_CLASS, property = "class")
public abstract class TradeVoucher implements Serializable {

	private static final long serialVersionUID = -1721078372959861925L;

}
