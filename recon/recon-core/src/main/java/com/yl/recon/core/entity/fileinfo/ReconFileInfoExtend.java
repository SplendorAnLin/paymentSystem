package com.yl.recon.core.entity.fileinfo;

import com.yl.recon.core.entity.BaseEntity;
import lombok.Data;

/**
 * 聚合支付有限公司
 *
 * @author 邱健
 * @version V1.0.0
 * @create 2018年01月17
 * @desc 通道方对账文件信息扩展类
 **/
@Data
public class ReconFileInfoExtend extends BaseEntity {
	/**
	 * 表头
	 */
	private String header;
	/**
	 * 表尾：合计行标题
	 */
	private String footer;
	/**
	 * 合计标识
	 */
	private String totalFlag;
	/**
	 * 分隔符
	 */
	private String delimiter;
	/**
	 * 接口编码
	 */
	private String interfaceInfoCode;
	/**
	 * 通道名称
	 */
	private String bankName;
	/**
	 * 银行订单号列
	 */
	private String bankOrderCode;
	/**
	 * 接口订单号列
	 */
	private String interfaceOrderCode;
	/**
	 * 订单金额列
	 */
	private String amount;
	/**
	 * 手续费列
	 */
	private String fee;
	/**
	 * 交易时间列
	 */
	private String transTime;



}
