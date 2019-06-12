package com.yl.boss.api.bean;

import java.util.Date;

import com.yl.boss.api.enums.KeyType;
import com.yl.boss.api.enums.ProductType;

/**
 * 商户密钥Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class CustomerKey extends BaseBean{
	
	private static final long serialVersionUID = -3819129925132221859L;
	private String customerNo;				//商户编号
	private ProductType productType;		//产品类型
	private KeyType keyType;				//密钥类型
	private String key;						//密钥|公钥
	private String privateKey;				//私钥
	private Date createTime;
	
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	public KeyType getKeyType() {
		return keyType;
	}
	public void setKeyType(KeyType keyType) {
		this.keyType = keyType;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
