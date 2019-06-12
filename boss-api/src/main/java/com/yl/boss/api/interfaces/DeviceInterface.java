package com.yl.boss.api.interfaces;

import java.util.List;
import java.util.Map;

import com.yl.boss.api.bean.DeviceBean;
import com.yl.boss.api.bean.DeviceTypeBean;

/**
 * 设备信息远程接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月19日
 * @version V1.0.0
 */
public interface DeviceInterface {
	
	String checkLicense(String customerPayNo, String checkCode);
	
	String getCustomerNo(String customerPayNo, String checkCode);
	/**
	 * 根据设备编号
	 */
	String  getCustomerPayNo(String customerPayNo);
	
	String getConfigKey();
	
	List<DeviceTypeBean> getDeviceType();
	/**
	 * 根据商户号查找第一个设备的支付地址
	 * @param custNo
	 * @return
	 */
	String getDeviceUrl(String custNo);
	/**
	 * 商户号查找设备
	 * @param custNo
	 * @return
	 */
	List<DeviceBean> findDeviceByCustNo(String custNo);
	
	/**
	 * 返回二维码json，包含类型type，设备编号No，二维码data
	 * @param customerPayNo
	 * @return
	 */
	String getQRcode(String customerPayNo);
	
	/**
	 * 获取一个未绑定水牌基本信息
	 */
	Map<String, Object> getDevice();
}