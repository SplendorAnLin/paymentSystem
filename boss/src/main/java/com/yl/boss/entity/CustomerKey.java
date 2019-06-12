package com.yl.boss.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.yl.boss.api.enums.KeyType;
import com.yl.boss.api.enums.ProductType;

/**
 * 商户密钥信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name = "CUSTOMER_KEY")
public class CustomerKey extends AutoIDEntity{
	
	private static final long serialVersionUID = -3374131465600669380L;
	private String customerNo;				//商户编号
	private ProductType productType;		//产品类型
	private KeyType keyType;				//密钥类型
	private String publicKey;						//密钥|公钥
	private String privateKey;				//私钥
	private Date createTime;
	
	@Column(name = "CUSTOMER_NO", length = 30)
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	
	@Column(name = "CREATE_TIME", columnDefinition = "DATE")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "PRODUCT_TYPE", columnDefinition = "VARCHAR(30)")
	public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "KEY_TYPE", columnDefinition = "VARCHAR(30)")
	public KeyType getKeyType() {
		return keyType;
	}
	public void setKeyType(KeyType keyType) {
		this.keyType = keyType;
	}
	
	@Column(name = "PUBLIC_KEY", length = 500)
	public String getKey() {
		return publicKey;
	}
	public void setKey(String key) {
		this.publicKey = key;
	}
	
	@Column(name = "PRIVATE_KEY", length = 500)
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	
}
