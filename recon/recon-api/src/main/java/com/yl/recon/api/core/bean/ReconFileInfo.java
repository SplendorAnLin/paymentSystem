package com.yl.recon.api.core.bean;


import com.yl.recon.api.core.enums.ReconFileType;
import com.yl.recon.api.core.enums.SystemCode;

/**
 * @author 聚合支付有限公司
 * @since 2018年01月05
 * @version V1.0.0
 */
public class ReconFileInfo extends BaseEntity {
	/**
	 * 文件扩展信息主键
	 */
	private Long extendCode;
	/**
	 * 文件名
	 **/
	private String fileName;
	/**
	 * 文件来源，系统标识[DPAY,BOSS,ONLINE,RECEIVE,POSP,1001]
	 **/
	private SystemCode systemCode;
	/**
	 * 对账文件类型[1.订单、2.接口、3.账目、4.通道]
	 **/
	private ReconFileType reconFileType;
	/**
	 * 是否有效[0.否、1.是]
	 **/
	private Integer valid;
	/**
	 * 是否通道对账文件[0.否、1.是]
	 **/
	private Integer isBankChannel;
	/**
	 * 路径
	 **/
	private String filePath;
	/**
	 * 备注
	 **/
	private String remark;


	/**
	 * 通道对账文件扩展
	 */
	ReconFileInfoExtend reconFileInfoExtend;

	public ReconFileInfo() {
	}

	public Long getExtendCode() {
		return extendCode;
	}

	public void setExtendCode(Long extendCode) {
		this.extendCode = extendCode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public SystemCode getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(SystemCode systemCode) {
		this.systemCode = systemCode;
	}

	public ReconFileType getReconFileType() {
		return reconFileType;
	}

	public void setReconFileType(ReconFileType reconFileType) {
		this.reconFileType = reconFileType;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getIsBankChannel() {
		return isBankChannel;
	}

	public void setIsBankChannel(Integer isBankChannel) {
		this.isBankChannel = isBankChannel;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ReconFileInfoExtend getReconFileInfoExtend() {
		return reconFileInfoExtend;
	}

	public void setReconFileInfoExtend(ReconFileInfoExtend reconFileInfoExtend) {
		this.reconFileInfoExtend = reconFileInfoExtend;
	}
}