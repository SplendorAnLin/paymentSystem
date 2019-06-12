package com.yl.boss.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhlabs.image.MaskFilter;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.yl.agent.api.enums.Status;
import com.yl.boss.Constant;
import com.yl.boss.api.enums.KeyType;
import com.yl.boss.api.enums.OrderStatus;
import com.yl.boss.api.enums.ProcessStatus;
import com.yl.boss.entity.AgentDeviceOrder;
import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.Device;
import com.yl.boss.entity.DeviceConfig;
import com.yl.boss.entity.DeviceConfigHistory;
import com.yl.boss.entity.DevicePurch;
import com.yl.boss.entity.DeviceType;
import com.yl.boss.enums.PurchaseType;
import com.yl.boss.service.CustomerKeyService;
import com.yl.boss.service.DeviceConfigHistoryService;
import com.yl.boss.service.DeviceConfigService;
import com.yl.boss.service.DeviceService;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.web.mvc.ValueListHandlerHelper;
import net.sf.json.JSONObject;

/**
 * 设备控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月28日
 * @version V1.0.0
 */
public class DeviceAction extends Struts2ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(DeviceAction.class);
	private ValueListHandlerHelper valueListHelper;
	private DeviceService deviceService;
	private DevicePurch devicePurch;
	private Long deviceTypeId;
	private DeviceType deviceTypeInfo;
	private List<Device> device;
	private List<DeviceType> deviceTypeList;
	private String custNo;
	private String batchNumber;
	private String purchaseSerialNumber;
	private String msg;
	private String customerPayNo;
	private Map countInfo;
	private DeviceConfig deviceConfig;
	private DeviceConfigService deviceConfigService;
	private DeviceConfigHistoryService deviceConfigHistoryService;
	private CustomerKeyService customerKeyService;
	private AgentDeviceOrder agentDeviceOrder;

	public String pageCallBack() {
		try {
			msg = msg.substring(1, msg.length());
			String customerNo = deviceService.getConfigKey();
			String key = customerKeyService.findBy(customerNo, KeyType.MD5).getKey();
			Map<String, String> params = getUrlParams(msg);
			String serverSign = params.get("sign");
			String outOrderId = params.get("outOrderId");
			params.remove("sign");
			ArrayList<String> paramNames = new ArrayList<>(params.keySet());
			Collections.sort(paramNames);
			// 组织待签名信息
			StringBuffer signSource = new StringBuffer();
			Iterator<String> iterator = paramNames.iterator();
			while (iterator.hasNext()) {
				String paramName = iterator.next();
				if (!"sign".equals(paramName) && StringUtils.notBlank(params.get(paramName))) {
					signSource.append(paramName).append("=").append(params.get(paramName));
					if (iterator.hasNext()) {
						signSource.append("&");
					}
				}
			}
			// 计算签名
			String calSign = null;
			signSource.append(key);
			calSign = DigestUtils.md5DigestAsHex(signSource.toString().getBytes("UTF-8"));
			if (!serverSign.equals(calSign)) {
				logger.info("******** 页面支付回调签名错误！********");
				custNo = "times-circle";
				msg = "支付失败！";
				return SUCCESS;
			} else {
				agentDeviceOrder = deviceService.findByOrderId(outOrderId);
				agentDeviceOrder.setOrderStatus(OrderStatus.SUCCESS);
				deviceService.updateAgentOrder(agentDeviceOrder, "支付成功回调");
				custNo = "check-circle";
				msg = "支付成功！";
				return SUCCESS;
			}
		} catch (IOException e) {
			msg = "FLASE";
			logger.error("pageCallBack ERROR:",e);
		}
		return SUCCESS;
	}

	/**
	 * 后台异步通知流
	 */
	public String callback() {
		try {
			String customerNo = deviceService.getConfigKey();
			String key = customerKeyService.findBy(customerNo, KeyType.MD5).getKey();
			BufferedReader br = new BufferedReader(
					new InputStreamReader((ServletInputStream) getHttpRequest().getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			Map<String, String> params = toMap(sb.toString());
			String serverSign = params.get("sign");
			String outOrderId = params.get("outOrderId");
			params.remove("sign");
			params.remove("returnParam");
			ArrayList<String> paramNames = new ArrayList<>(params.keySet());
			Collections.sort(paramNames);
			// 组织待签名信息
			StringBuffer signSource = new StringBuffer();
			Iterator<String> iterator = paramNames.iterator();
			while (iterator.hasNext()) {
				String paramName = iterator.next();
				if (!"sign".equals(paramName) && StringUtils.notBlank(params.get(paramName))) {
					signSource.append(paramName).append("=").append(params.get(paramName));
					if (iterator.hasNext()) {
						signSource.append("&");
					}
				}
			}
			// 计算签名
			String calSign = null;
			signSource.append(key);
			calSign = DigestUtils.md5DigestAsHex(signSource.toString().getBytes("UTF-8"));
			if (!serverSign.equals(calSign)) {
				logger.info("******** 支付回调签名错误！********");
				msg = "FLASE";
				return SUCCESS;
			} else {
				agentDeviceOrder = deviceService.findByOrderId(outOrderId);
				agentDeviceOrder.setOrderStatus(OrderStatus.SUCCESS);
				deviceService.updateAgentOrder(agentDeviceOrder, "支付成功回调");
				msg = "TRUE";
				// 通知相应 SUCCESS
				PrintWriter printWriter = getHttpResponse().getWriter();
				printWriter.print("SUCCESS");
				return SUCCESS;
			}
		} catch (IOException e) {
			msg = "FLASE";
			logger.error("callback ERROR:",e);
		}
		return SUCCESS;
	}
	
	public static Map<String, String> toMap(Object object) {
	    Map<String, String> data = new HashMap<String, String>();
	    // 将json字符串转换成jsonObject
	    JSONObject jsonObject = JSONObject.fromObject(object);
	    Iterator ite = jsonObject.keys();
	    // 遍历jsonObject数据,添加到Map对象
	    while (ite.hasNext()) {
	        String key = ite.next().toString();
	        String value = jsonObject.get(key).toString();
	        data.put(key, value);
	    }
	    // 或者直接将 jsonObject赋值给Map
	    // data = jsonObject;
	    return data;
	}

	public String getQRcode() {
		msg = deviceService.getQRcode(customerPayNo);
		return SUCCESS;
	}

	public String addDeviceType() {
		try {
			deviceService.create(deviceTypeInfo);
			msg = "true";
		} catch (Exception e) {
			msg = "false";
			logger.info(e.getMessage());
		}
		return SUCCESS;
	}

	public String upDeviceType() {
		try {
			deviceService.update(deviceTypeInfo);
			msg = "true";
		} catch (Exception e) {
			msg = "false";
			logger.info(e.getMessage());
		}
		return SUCCESS;
	}

	public String toUpDeviceType() {
		deviceTypeList = deviceService.byParentId();
		deviceTypeInfo = deviceService.findDeviceType(deviceTypeId);
		return SUCCESS;
	}

	/**
	 * 参数转换
	 */
	private Map<String, Object> retrieveParams(Map<String, String[]> requestMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (String key : requestMap.keySet()) {
			String[] value = requestMap.get(key);
			if (value != null) {
				resultMap.put(key, Array.get(value, 0).toString().trim());
			}
		}
		return resultMap;
	}

	/**
	 * URL String 转Map
	 * 
	 * @return
	 */
	public static Map<String, String> getUrlParams(String param) {
		Map<String, String> map = new HashMap<String, String>();
		if ("".equals(param) || null == param) {
			return map;
		}
		String[] params = param.split("&");
		for (int i = 0; i < params.length; i++) {
			String[] p = params[i].split("=");
			if (p.length == 2) {
				map.put(p[0], p[1]);
			}
		}
		return map;
	}

	public String addDeviceOrder() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		try {
			deviceService.createDevicePurch(devicePurch, auth.getUsername());
			msg = "true";
		} catch (Exception e) {
			msg = "false";
			logger.info(e.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 分配商户设备
	 */
	public String allotCustDevice() {
		if (deviceService.allotDevice(1, deviceTypeId, null, custNo, false)) {
			msg = "true";
		} else {
			msg = "库存不足或处理失败";
		}
		return SUCCESS;
	}

	/**
	 * 分配服务商设备
	 * 
	 * @return
	 */
	public String allotAgentDevice() {
		String psNo = getHttpRequest().getParameter("psNo");
		if (psNo != null && psNo.length() > 0) {
			try {
				Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
				AgentDeviceOrder agentDeviceOrder = deviceService.findByAgentOrder(psNo);
				if (deviceService.allotDevice(agentDeviceOrder.getQuantity(), agentDeviceOrder.getDeviceTypeId(), psNo,
						agentDeviceOrder.getOwnerId(), PurchaseType.SERVICE_PROVIDER==agentDeviceOrder.getPurchaseChannel())) {
					agentDeviceOrder.setFlowStatus(ProcessStatus.ALLOT);
					agentDeviceOrder.setUpdateTime(new Date());
					deviceService.updateAgentOrder(agentDeviceOrder, auth.getUsername());
					msg = "true";
				} else {
					msg = "库存不足或处理失败";
				}
			} catch (Exception e) {
				msg = "订单处理异常";
				logger.info(e.getMessage());
			}
		}
		return SUCCESS;
	}

	public String denyAllot() {
		String psNo = getHttpRequest().getParameter("psNo");// 服务商订单流水号
		if (psNo != null && psNo.length() > 0) {
			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			AgentDeviceOrder agentDeviceOrder = deviceService.findByAgentOrder(psNo);
			agentDeviceOrder.setUpdateTime(new Date());
			agentDeviceOrder.setFlowStatus(ProcessStatus.ORDER_FAIL);
			deviceService.updateAgentOrder(agentDeviceOrder, auth.getUsername());
			msg = "true";
		}
		return SUCCESS;
	}

	public String getDeviceType() {
		deviceTypeList = deviceService.getDeviceType();
		return SUCCESS;
	}

	public String remitDevice() {
		String psNo = getHttpRequest().getParameter("psNo");// 服务商订单流水号
		if (psNo != null && psNo.length() > 0) {
			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			AgentDeviceOrder agentDeviceOrder = deviceService.findByAgentOrder(psNo);
			agentDeviceOrder.setUpdateTime(new Date());
			agentDeviceOrder.setPurchaseStatus(Status.TRUE);
			// 更新设备出库时间
			List<Device> list = deviceService.findDeviceByPsn(agentDeviceOrder.getPurchaseSerialNumber());
			for (Device device : list) {
				device.setOutWarehouseTime(new Date());
				if(agentDeviceOrder.getPurchaseChannel()==PurchaseType.SERVICE_PROVIDER) {
					device.setStatus(ProcessStatus.ALLOT);
				}
				deviceService.upDevice(device);
			}
			deviceService.updateAgentOrder(agentDeviceOrder, auth.getUsername());
			msg = "true";
		}
		return SUCCESS;
	}

	public String checkOrderMaking() {
		String batchNumber = getHttpRequest().getParameter("batchNumber");
		if (batchNumber != null && batchNumber.length() > 0) {
			if ("MAKING".equals(deviceService.findDevicePurchBy(batchNumber).getPurchaseStatus().toString())) {
				msg = "true";
			} else {
				msg = "false";
			}
		} else {
			msg = "false";
		}
		return SUCCESS;
	}

	public String deviceExport() {
		try {
			devicePurch = deviceService.findDevicePurchBy(batchNumber);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("batch_number", batchNumber);
			ValueListInfo info = new ValueListInfo(params);
			ValueList valueList = valueListHelper.getValueList("deviceExport", info);
			if ("ORDER_WAIT".equals(devicePurch.getPurchaseStatus().toString())) {
				if (valueList.getList().size() > 0) {// 导出，更改设备状态和订单状态
					Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
					devicePurch = deviceService.findDevicePurchBy(batchNumber);
					devicePurch.setPurchaseStatus(ProcessStatus.MAKING);
					deviceService.updateDevicePurch(devicePurch, auth.getUsername());
				}
			}
			getHttpRequest().setAttribute("deviceExport", valueList);
		} catch (Exception e) {
			logger.info("导出设备失败：" + e.getMessage());
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String deviceSum() {
		Map<String, Object> params = retrieveParams(getHttpRequest().getParameterMap());
		ValueList valueList = valueListHelper.getValueList("deviceSum", new ValueListInfo(params));
		List<Map> info = valueList.getList();
		if (info.size() > 0) {
			msg = JsonUtils.toJsonString(info.get(0));
		}
		return SUCCESS;
	}

	public String deviceExportByPsn() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("purchase_serial_number", purchaseSerialNumber);
			ValueListInfo info = new ValueListInfo(params);
			ValueList valueList = valueListHelper.getValueList("deviceExport", info);
			getHttpRequest().setAttribute("deviceExport", valueList);
		} catch (Exception e) {
			logger.info("导出设备失败：" + e.getMessage());
		}
		return SUCCESS;
	}

	public String warehousing() {
		try {
			String batchNumber = getHttpRequest().getParameter("batchNumber");
			if (batchNumber != null && batchNumber.length() > 0) {
				Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
				devicePurch = deviceService.findDevicePurchBy(batchNumber);
				devicePurch.setPurchaseStatus(ProcessStatus.OK);
				deviceService.updateDevicePurch(devicePurch, auth.getUsername());
			}
			msg = "true";
		} catch (Exception e) {
			msg = "false";
			logger.info("入库失败！", e);
		}
		return SUCCESS;
	}

	public String toDeviceTypeAdd() {
		try {
			deviceTypeList = deviceService.byParentId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String addDeviceParentType() {
		try {
			deviceTypeInfo.setParentId(null);
			deviceService.create(deviceTypeInfo);
			msg = "true";
		} catch (Exception e) {
			msg = "false";
			logger.info(e.getMessage());
		}
		return SUCCESS;
	}

	public String toUpDeviceParentType() {
		deviceTypeInfo = deviceService.findDeviceType(deviceTypeId);
		return SUCCESS;
	}

	public String upDeviceParentType() {
		try {
			deviceService.updateParent(deviceTypeInfo);
			msg = "true";
		} catch (Exception e) {
			msg = "false";
			logger.info(e.getMessage());
		}
		return SUCCESS;
	}

	public String deviceConfigUpdate() {
		try {
			int i = deviceConfigService.deviceConfigCount();
			if (deviceConfigService.deviceConfigCount() > 0) {
				DeviceConfig device = deviceConfigService.byId(deviceConfig);
				if (device.getConfigKey().equals(deviceConfig.getConfigKey())) {

				} else {
					device.setConfigKey(deviceConfig.getConfigKey());
					deviceConfigService.update(device);
					DeviceConfigHistory h = new DeviceConfigHistory();
					Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
					h.setUpdateKey(deviceConfig.getConfigKey());
					h.setUpdateTime(new Date());
					h.setOperator(auth.getUsername());
					deviceConfigHistoryService.insert(h);
				}
			} else {
				if (deviceConfig.getConfigKey() != null && deviceConfig.getConfigKey() != "") {
					deviceConfigService.save(deviceConfig);
					DeviceConfigHistory h = new DeviceConfigHistory();
					Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
					h.setUpdateKey(deviceConfig.getConfigKey());
					h.setUpdateTime(new Date());
					h.setOperator(auth.getUsername());
					deviceConfigHistoryService.insert(h);

				} else {
				}
			}
		} catch (Exception e) {
			msg = "false";
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return SUCCESS;
	}

	public String toDeviceConfigQuery() {
		deviceConfig = deviceConfigService.select();
		return SUCCESS;
	}

	public DevicePurch getDevicePurch() {
		return devicePurch;
	}

	public void setDevicePurch(DevicePurch devicePurch) {
		this.devicePurch = devicePurch;
	}

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<DeviceType> getDeviceTypeList() {
		return deviceTypeList;
	}

	public void setDeviceTypeList(List<DeviceType> deviceTypeList) {
		this.deviceTypeList = deviceTypeList;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public ValueListHandlerHelper getValueListHelper() {
		return valueListHelper;
	}

	public void setValueListHelper(ValueListHandlerHelper valueListHelper) {

		this.valueListHelper = valueListHelper;
	}

	public List<Device> getDevice() {
		return device;
	}

	public void setDevice(List<Device> device) {
		this.device = device;
	}

	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public DeviceType getDeviceTypeInfo() {
		return deviceTypeInfo;
	}

	public void setDeviceTypeInfo(DeviceType deviceTypeInfo) {
		this.deviceTypeInfo = deviceTypeInfo;
	}

	public String getPurchaseSerialNumber() {
		return purchaseSerialNumber;
	}

	public void setPurchaseSerialNumber(String purchaseSerialNumber) {
		this.purchaseSerialNumber = purchaseSerialNumber;
	}

	public String getCustomerPayNo() {
		return customerPayNo;
	}

	public void setCustomerPayNo(String customerPayNo) {
		this.customerPayNo = customerPayNo;
	}

	public Map getCountInfo() {
		return countInfo;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public void setCountInfo(Map countInfo) {
		this.countInfo = countInfo;
	}

	public DeviceConfig getDeviceConfig() {
		return deviceConfig;
	}

	public void setDeviceConfig(DeviceConfig deviceConfig) {
		this.deviceConfig = deviceConfig;
	}

	public DeviceConfigService getDeviceConfigService() {
		return deviceConfigService;
	}

	public void setDeviceConfigService(DeviceConfigService deviceConfigService) {
		this.deviceConfigService = deviceConfigService;
	}

	public DeviceConfigHistoryService getDeviceConfigHistoryService() {
		return deviceConfigHistoryService;
	}

	public void setDeviceConfigHistoryService(DeviceConfigHistoryService deviceConfigHistoryService) {
		this.deviceConfigHistoryService = deviceConfigHistoryService;
	}

	public CustomerKeyService getCustomerKeyService() {
		return customerKeyService;
	}

	public void setCustomerKeyService(CustomerKeyService customerKeyService) {
		this.customerKeyService = customerKeyService;
	}

	public AgentDeviceOrder getAgentDeviceOrder() {
		return agentDeviceOrder;
	}

	public void setAgentDeviceOrder(AgentDeviceOrder agentDeviceOrder) {
		this.agentDeviceOrder = agentDeviceOrder;
	}
}