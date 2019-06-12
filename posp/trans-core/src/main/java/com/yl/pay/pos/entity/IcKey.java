package com.yl.pay.pos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * IC卡公钥下载
 * @author haitao.liu
 *
 */

@Entity
@Table(name = "IC_KEY")
public class IcKey  extends BaseEntity  {
	private static final long serialVersionUID = 1L;

	private String rid;						//rid
	
	private String keyIndex;				//公钥序列
	
	private String expTime;					//有效期
	
	private String hashAlgorithmId;			//哈什算法标识
	
	private String algorithmId;				//公钥算法标识
	
	private String modulus;					//公钥模
	
	private String exponent;				//公钥指数
	
	private String checkvalue;				//公钥校验值
	
	private Date createTime;				//创建日期
	
	private Integer seqIndex;				//索引号

	/**
	 * RID
	 * @return
	 */
	@Column(name = "RID", length = 30)
	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}
	@Column(name = "KEY_INDEX", length = 30)
	public String getKeyIndex() {
		return keyIndex;
	}

	public void setKeyIndex(String keyIndex) {
		this.keyIndex = keyIndex;
	}
	@Column(name = "EXP_TIME", length = 30)
	public String getExpTime() {
		return expTime;
	}

	public void setExpTime(String expTime) {
		this.expTime = expTime;
	}
	@Column(name = "HASH_ALGORITHM_ID", length =10)
	public String getHashAlgorithmId() {
		return hashAlgorithmId;
	}

	public void setHashAlgorithmId(String hashAlgorithmId) {
		this.hashAlgorithmId = hashAlgorithmId;
	}
	@Column(name = "ALGORITHM_ID", length =10)
	public String getAlgorithmId() {
		return algorithmId;
	}

	public void setAlgorithmId(String algorithmId) {
		this.algorithmId = algorithmId;
	}
	@Column(name = "MODULUS", length =1000)
	public String getModulus() {
		return modulus;
	}

	public void setModulus(String modulus) {
		this.modulus = modulus;
	}
	@Column(name = "EXPONENT", length =10)
	public String getExponent() {
		return exponent;
	}

	public void setExponent(String exponent) {
		this.exponent = exponent;
	}
	@Column(name = "CHECK_VALUE", length =500)
	public String getCheckvalue() {
		return checkvalue;
	}

	public void setCheckvalue(String checkvalue) {
		this.checkvalue = checkvalue;
	} 
	@Column(name = "CREATE_TIME", length =500,columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "seq_Index", length = 30)
	public Integer getSeqIndex() {
		return seqIndex;
	}

	public void setSeqIndex(Integer seqIndex) {
		this.seqIndex = seqIndex;
	}
	
	
	
	
	
}
