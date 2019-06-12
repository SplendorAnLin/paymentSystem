package com.yl.boss.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.utils.lang.JsonUtils;
import com.pay.common.util.StringUtil;
import com.yl.agent.api.enums.Status;
import com.yl.boss.api.enums.OrderStatus;
import com.yl.boss.api.enums.ProcessStatus;
import com.yl.boss.dao.AgentDeviceOrderDao;
import com.yl.boss.dao.AgentDeviceOrderHistoryDao;
import com.yl.boss.dao.DeviceDao;
import com.yl.boss.dao.DevicePurchDao;
import com.yl.boss.dao.DevicePurchHistoryDao;
import com.yl.boss.dao.DeviceTypeDao;
import com.yl.boss.entity.AgentDeviceOrder;
import com.yl.boss.entity.AgentDeviceOrderHistory;
import com.yl.boss.entity.Device;
import com.yl.boss.entity.DevicePurch;
import com.yl.boss.entity.DevicePurchHistory;
import com.yl.boss.entity.DeviceType;
import com.yl.boss.service.DeviceService;
import com.yl.boss.utils.CodeBuilder;
import com.yl.boss.utils.DateUtils;
import com.yl.boss.utils.QRcodeUtil;
/**
 * 设备业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class DeviceServiceImpl implements DeviceService {
	public static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);
	
	private AgentDeviceOrderDao agentDeviceOrderDao;
	private AgentDeviceOrderHistoryDao agentDeviceOrderHistoryDao;
	private DeviceDao deviceDao;
	private DevicePurchDao devicePurchDao;
	private DevicePurchHistoryDao devicePurchHistoryDao;
	private DeviceTypeDao deviceTypeDao;
	
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(DeviceServiceImpl.class.getClassLoader().getResourceAsStream("system.properties"));
		} catch (IOException e) {
			logger.error("系统异常:", e);
		}
	}
	
	@Override
	public Map findDecIvestate(Map<String, Object> params) {
		
		return 	deviceDao.findDecIvestate(params);
	}

	@Override
	public String getConfigKey() {
		return deviceDao.getConfigKey();
	}
	
	@Override
	public void createAgentOrder(AgentDeviceOrder agentDeviceOrder,String oper) {
		agentDeviceOrder.setPurchaseSerialNumber(CodeBuilder.build("ADO", "yyMMdd"));
		agentDeviceOrder.setCreateTime(new Date());
		agentDeviceOrder.setFlowStatus(ProcessStatus.ORDER_WAIT);
		agentDeviceOrder.setPurchaseStatus(Status.FALSE);//未出库
		agentDeviceOrder.setUnitPrice(deviceTypeDao.findById(agentDeviceOrder.getDeviceTypeId()).getUnitPrice());//单价
		agentDeviceOrder.setTotal(agentDeviceOrder.getQuantity()*agentDeviceOrder.getUnitPrice());
		agentDeviceOrder.setOrderStatus(OrderStatus.WAIT_PAY);
		agentDeviceOrderDao.create(agentDeviceOrder);
		agentDeviceOrderHistoryDao.create(new AgentDeviceOrderHistory(agentDeviceOrder,oper));
	}

	@Override
	public List<Device> findDeviceByCustNo(String custNo) {
		return deviceDao.findByCardNo(custNo);
	}
	
	@Override
	public Device findByCustomerNo(String customerNo) {
		List<Device> list = deviceDao.findByCardNo(customerNo);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public void upDevice(Device device) {
		device.setUpdateTime(new Date());
		deviceDao.update(device);
	}
	
	@Override
	public void create(DeviceType deviceType) {
		deviceType.setStatus(Status.TRUE);
		deviceTypeDao.create(deviceType);
	}

	@Override
	public void update(DeviceType deviceType) {
		DeviceType deviceTypeOld =deviceTypeDao.findById(deviceType.getId());
		deviceTypeOld.setDeviceName(deviceType.getDeviceName());
		deviceTypeOld.setUnitPrice(deviceType.getUnitPrice());
		deviceTypeOld.setStatus(deviceType.getStatus());
		deviceTypeOld.setRemark(deviceType.getRemark());
		deviceTypeOld.setParentId(deviceType.getParentId());
		deviceTypeDao.update(deviceTypeOld);
		
	}
	
	@Override
	public DeviceType findDeviceType(Long id) {
		return deviceTypeDao.findById(id);
	}

	@Override
	public AgentDeviceOrder findByAgentOrder(String psNo) {
		return agentDeviceOrderDao.findByPsNo(psNo);
	}

	public List<Device>  findDeviceBySize(int size,Long type){
		return deviceDao.findDeviceBySize(size,type);
	}
	
	@Override
	public List<Device> findDeviceByPsn(String purchaseSerialNumber) {
		return deviceDao.findDeviceByPsn(purchaseSerialNumber);
	}

	@Override
	public DevicePurch findDevicePurchBy(String batchNumber) {
		return devicePurchDao.findDevicePurchBy(batchNumber);
	}
	
	@Override
	public void updateDevicePurch(DevicePurch devicePurch,String oper) {
		devicePurchDao.update(devicePurch);
		List<Device> list=deviceDao.findByBatchNo(devicePurch.getBatchNumber());
		if ("MAKING".equals(devicePurch.getPurchaseStatus().toString())) {//制作中
			for (Device device : list) {
				device.setStatus(ProcessStatus.MAKING);
				upDevice(device);
			}
		}else if ("OK".equals(devicePurch.getPurchaseStatus().toString())) {//已入库
			for (Device device : list) {
				device.setStatus(ProcessStatus.OK);
				upDevice(device);
			}
		}
		devicePurchHistoryDao.create(new DevicePurchHistory(devicePurch, oper));
	}
	
	@Override
	public void updateAgentOrder(AgentDeviceOrder agentDeviceOrder,String oper) {
		agentDeviceOrderDao.update(agentDeviceOrder);
		agentDeviceOrderHistoryDao.create(new AgentDeviceOrderHistory(agentDeviceOrder,oper));
	}
	
	@Override
	public String getQRcode(String customerPayNo) {
		Map<String,Object> info=new  HashMap<>();
		if (customerPayNo.length()>0) {
			try {
				Device device=getDevice(customerPayNo);
				info.put("type", 1);
				info.put("data", QRcodeUtil.getBase64(device.getQrcodeUrl()));
				info.put("No", device.getCustomerPayNo());
			} catch (Exception e) {
				info.put("type", 3);
				info.put("data", "false");
			}
		}else {
			info.put("type", 2);
			info.put("data", "false");
		}
		return JsonUtils.toJsonString(info);
	}
	
	@Override
	public Device getDevice(String cardNo) {
		return deviceDao.findByPayId(cardNo);
	}

	@Override
	public Device checkDevice(String cardNo, String checkCode) {
		Device device=getDevice(cardNo);
		if (device.getCheckCode().equals(checkCode)) {
			return device;
		}
		return null;
	}
	
	public boolean allotDevice(Integer quantity,Long deviceTypeId,String purchaseSerialNumber,String No,boolean isAgent){
		List<Device> devices=findDeviceBySize(quantity,deviceTypeId);
		if (devices.size()>0) {
			for (int i = 0; i < quantity; i++) {
				if (isAgent) {
					devices.get(i).setAgentNo(No);
					devices.get(i).setStatus(ProcessStatus.OK);
				}else{
					devices.get(i).setCustomerNo(No);
					devices.get(i).setActivateTime(new Date());
					devices.get(i).setStatus(ProcessStatus.BINDED);
				}
				devices.get(i).setPurchaseSerialNumber(purchaseSerialNumber);
				upDevice(devices.get(i));
			}
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public void createDevicePurch(DevicePurch devicePurch, String oper) {
		devicePurch.setBatchNumber(CodeBuilder.build("DO", "yyMMdd"));
		devicePurch.setTotal(devicePurch.getUnitPrice()*devicePurch.getQuantity());
		devicePurch.setUser(oper);
		devicePurch.setCreateTime(new Date());
		devicePurch.setUpdateTime(new Date());
		devicePurch.setPurchaseStatus(ProcessStatus.ORDER_WAIT);//订单初始化
		devicePurchDao.create(devicePurch);//新增订单
		Integer deviceNo=getNowDeviceNo();
		for (int i = 0; i < devicePurch.getQuantity(); i++) {
			deviceNo+=1;
			Device device=new Device();
			device.setCustomerPayNo(produceDeviceNo(deviceNo));//收款码编号
			device.setStatus(ProcessStatus.ORDER_WAIT);//设备初始化
			device.setCreateTime(new Date());
			device.setUpdateTime(new Date());
			device.setDeviceTypeId(devicePurch.getDeviceTypeId());
			device.setBatchNumber(devicePurch.getBatchNumber());//订单批次号
			device.setCheckCode(CodeBuilder.build(8));
			device.setQrcodeUrl(prop.getProperty("qrCode.path")+"?cardNo="+device.getCustomerPayNo()+"&checkCode="+device.getCheckCode());
			deviceDao.create(device);
		}
		devicePurchHistoryDao.create(new DevicePurchHistory(devicePurch,oper));
	}
	/**
	 * 获取当天最大的收款码编号
	 * @return
	 */
	private Integer getNowDeviceNo(){
		String deviceNo=deviceDao.getMaxDeviceNo();
		if (StringUtil.isNull(deviceNo)) {
			deviceNo="0";
		}else {
			deviceNo=deviceNo.substring(8);
		}
		return Integer.parseInt(deviceNo);
	}
	/**
	 * 生成收款码编号
	 * @param no
	 * @return
	 */
	private String produceDeviceNo(Integer no){
		int i=no.toString().length();
		String str=no.toString();
		while (6-i>0) {
			str="0"+str;
			i++;
		}
		return "YL"+DateUtils.parseStr(new Date(), "yyMMdd")+str;
	}
	
	public List<DeviceType> getDeviceType(){
		return deviceTypeDao.find();
	}

	public AgentDeviceOrderDao getAgentDeviceOrderDao() {
		return agentDeviceOrderDao;
	}

	public void setAgentDeviceOrderDao(AgentDeviceOrderDao agentDeviceOrderDao) {
		this.agentDeviceOrderDao = agentDeviceOrderDao;
	}

	public AgentDeviceOrderHistoryDao getAgentDeviceOrderHistoryDao() {
		return agentDeviceOrderHistoryDao;
	}

	public void setAgentDeviceOrderHistoryDao(AgentDeviceOrderHistoryDao agentDeviceOrderHistoryDao) {
		this.agentDeviceOrderHistoryDao = agentDeviceOrderHistoryDao;
	}

	public DeviceTypeDao getDeviceTypeDao() {
		return deviceTypeDao;
	}

	public void setDeviceTypeDao(DeviceTypeDao deviceTypeDao) {
		this.deviceTypeDao = deviceTypeDao;
	}

	public DeviceDao getDeviceDao() {
		return deviceDao;
	}

	public void setDeviceDao(DeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}

	public DevicePurchDao getDevicePurchDao() {
		return devicePurchDao;
	}

	public void setDevicePurchDao(DevicePurchDao devicePurchDao) {
		this.devicePurchDao = devicePurchDao;
	}

	public DevicePurchHistoryDao getDevicePurchHistoryDao() {
		return devicePurchHistoryDao;
	}

	public void setDevicePurchHistoryDao(DevicePurchHistoryDao devicePurchHistoryDao) {
		this.devicePurchHistoryDao = devicePurchHistoryDao;
	}


	@Override
	public List<DeviceType> byParentId() {
		return deviceTypeDao.byParentId();
	}


	@Override
	public void updateParent(DeviceType deviceType) {
		DeviceType deviceTypeOld =deviceTypeDao.findById(deviceType.getId());
		deviceTypeOld.setDeviceName(deviceType.getDeviceName());
		deviceTypeOld.setStatus(deviceType.getStatus());
		deviceTypeDao.update(deviceTypeOld);
		
	}

	@Override
	public int yOrNoOrder(AgentDeviceOrder agentDeviceOrder) {
		return agentDeviceOrderDao.yOrNoOrder(agentDeviceOrder);
	}

	@Override
	public AgentDeviceOrder findByOrderId(String orderId) {
		return agentDeviceOrderDao.findByOrderId(orderId);
	}

	@Override
	public void updateDeviceOrder(AgentDeviceOrder agentDeviceOrderBean) {
		agentDeviceOrderDao.update(agentDeviceOrderBean);
	}

	@Override
	public Device findDevice() {
		return deviceDao.findDevice();
	}
}