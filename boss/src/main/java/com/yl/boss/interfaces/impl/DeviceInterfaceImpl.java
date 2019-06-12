package com.yl.boss.interfaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.bean.DeviceBean;
import com.yl.boss.api.bean.DeviceTypeBean;
import com.yl.boss.api.interfaces.DeviceInterface;
import com.yl.boss.entity.Device;
import com.yl.boss.entity.DeviceType;
import com.yl.boss.service.DeviceService;

import freemarker.core.Macro;

/**
 * 设备信息远程接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月19日
 * @version V1.0.0
 */
public class DeviceInterfaceImpl implements DeviceInterface{

	private DeviceService deviceService;
	
	@Override
	public String checkLicense(String customerPayNo, String checkCode) {
		com.yl.boss.entity.Device device = deviceService.checkDevice(customerPayNo, checkCode);
		if(device != null){
			return device.getStatus().name();
		}
		return null;
	}
	
	@Override
	public String getCustomerNo(String customerPayNo, String checkCode) {
		com.yl.boss.entity.Device device = deviceService.checkDevice(customerPayNo, checkCode);
		if(device!= null){
			return device.getCustomerNo();
		}
		return null;
	}

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	@Override
	public String getConfigKey() {
		return deviceService.getConfigKey();
	}

	@Override
	public String getCustomerPayNo(String customerPayNo) {
		return deviceService.getQRcode(customerPayNo);
		
	}

	@Override
	public List<DeviceTypeBean> getDeviceType() {
		List<DeviceType> list=deviceService.getDeviceType();
		List<DeviceTypeBean> lists=new ArrayList<DeviceTypeBean>();
		if(list.size()>0){
			for (int i = 0; i <list.size(); i++) {
				DeviceTypeBean dvice=new DeviceTypeBean();
				dvice.setDeviceName(list.get(i).getDeviceName());
				dvice.setId(list.get(i).getId());
				dvice.setOptimistic(list.get(i).getOptimistic());
				dvice.setRemark(list.get(i).getRemark());
				dvice.setParentId(list.get(i).getParentId());
				dvice.setStatus(JsonUtils.toJsonToObject(list.get(i).getStatus(), com.yl.boss.api.enums.Status.class));
				if(list.get(i).getUnitPrice()!=null){
					dvice.setUnitPrice(list.get(i).getUnitPrice());
				}else{
					dvice.setUnitPrice(0.00);
				}
				lists.add(dvice);
			}
		}
		return lists;
	}

	public String getDeviceUrl(String custNo){
		List<Device> deviceList=deviceService.findDeviceByCustNo(custNo);
		if (deviceList.size()>0) {
			return deviceList.get(0).getQrcodeUrl();
		}
		return null;
	}
	@Override
	public List<DeviceBean> findDeviceByCustNo(String custNo) {
		return JsonUtils.toJsonToObject(deviceService.findDeviceByCustNo(custNo), List.class);
	}

	@Override
	public String getQRcode(String customerPayNo) {
		return deviceService.getQRcode(customerPayNo);
	}

	@Override
	public Map<String, Object> getDevice() {
		Device device = deviceService.findDevice();
		if (device != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("checkCode", device.getCheckCode());
			map.put("cardNo", device.getCustomerPayNo());
			map.put("url", device.getQrcodeUrl());
			return map;
		}
		return null;
	}
}