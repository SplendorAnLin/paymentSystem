package com.yl.recon.core.entity.fileinfo;


import com.yl.recon.core.entity.BaseEntity;
import com.yl.recon.core.enums.ReconFileType;
import com.yl.recon.core.enums.SystemCode;
import lombok.Data;

/**
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2018年01月05
 */
@Data
public class ReconFileInfo extends BaseEntity {
	private static final long serialVersionUID = -4466230102129368128L;
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
	 * 对账文件类型
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
}