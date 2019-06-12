package com.yl.boss.interfaces.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lefu.commons.utils.lang.JsonUtils;
import com.pay.common.util.Md5Util;
import com.yl.receive.hessian.ReceiveQueryFacade;
import com.yl.receive.hessian.bean.ReceiveConfigInfoBean;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.boss.action.AdAction;
import com.yl.boss.api.bean.License;
import com.yl.boss.enums.CustomerStatus;
import com.yl.boss.api.enums.ProcessStatus;
import com.yl.boss.api.interfaces.QRCodeInterface;
import com.yl.boss.entity.Customer;
import com.yl.boss.entity.CustomerCert;
import com.yl.boss.entity.CustomerFee;
import com.yl.boss.entity.CustomerSettle;
import com.yl.boss.entity.Device;
import com.yl.boss.enums.CustomerType;
import com.yl.boss.service.CustomerCertService;
import com.yl.boss.service.CustomerFeeService;
import com.yl.boss.service.CustomerService;
import com.yl.boss.service.CustomerSettleService;
import com.yl.boss.service.DeviceService;
import com.yl.boss.service.QRCodeService;
import com.yl.boss.utils.CodeBuilder;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import  com.yl.dpay.hessian.service.ServiceConfigFacade;
import sun.misc.BASE64Decoder;

/**
 * 扫码入网远程服务接口实现
 * @author 聚合支付有限公司
 * @since 2016年9月19日
 * @version V1.0.0
 */
public class QRCodeInterfaceImpl implements QRCodeInterface {

	private static final Logger logger = LoggerFactory.getLogger(QRCodeInterface.class);

	private QRCodeService qRCodeService;
	
	private DeviceService deviceService;
	
	private CustomerService customerService;
	
	private CustomerCertService customerCertService;
	
	private CustomerSettleService customerSettleService;

	private CustomerFeeService customerFeeService;
	
	private ReceiveQueryFacade receiveQueryFacade;
	
	private AccountQueryInterface accountQueryInterface;
	
	private ReceiveConfigInfoBean receiveConfigInfoBean;
	
	private ServiceConfigFacade serviceConfigFacade;
	
	private static Properties prop = new Properties();
	
	static {
		try {
			prop.load(
					new InputStreamReader(AdAction.class.getClassLoader().getResourceAsStream("system.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean sweepTheNetwork(License license) {
		// 图片路径 D:\\1\\test\\   /home/attachment/qrcode/
		String idImgFilePath = prop.getProperty("sweepTheNetwork") + CodeBuilder.build("ID", "yyyyMMdd") + ".jpg";
		String bankImgFilePath = prop.getProperty("sweepTheNetwork") + CodeBuilder.build("BK", "yyyyMMdd") + ".jpg";
		// 图像数据为空
		if (license.getHandheldBank() == null || license.getHandheldId() == null) {
			logger.error("************** 扫码入网未获取到图片数据{} **************",license.getPhone());
			return false;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] bank = decoder.decodeBuffer(license.getHandheldBank());
			for (int i = 0; i < bank.length; ++i) {
				// 调整异常数据
				if (bank[i] < 0) {
					bank[i] += 256;
				}
			}
			// Base64解码
			byte[] id = decoder.decodeBuffer(license.getHandheldId());
			for (int i = 0; i < id.length; ++i) {
				// 调整异常数据
				if (id[i] < 0) {
					id[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream handheldBank = new FileOutputStream(bankImgFilePath);
			OutputStream handheldId = new FileOutputStream(idImgFilePath);
			handheldBank.write(bank);
			handheldId.write(id);
			handheldBank.flush();
			handheldId.flush();
			handheldBank.close();
			handheldId.close();
			license.setHandheldBank(bankImgFilePath);
			license.setHandheldId(idImgFilePath);
			//二次审核判断
			com.yl.boss.entity.License li = qRCodeService.getInfo(license.getId());
			Device device = deviceService.checkDevice(license.getCardNo(), license.getCheckCode());
			if(li == null){
				qRCodeService.sweepTheNetwork(JsonUtils.toObject(JsonUtils.toJsonString(license), new TypeReference<com.yl.boss.entity.License>() {}));
				// 入网开始   商户基本信息
				Customer customer = new Customer();
				customer.setShortName(license.getFullName());
				customer.setFullName(license.getFullName());
				customer.setPhoneNo(license.getPhone());
				customer.setStatus(CustomerStatus.AGENT_AUDIT);
				customer.setLinkMan(license.getLinkName());
				customer.setCustomerType(CustomerType.INDIVIDUAL); // 默认个体
				customer.setAddr(license.getAddr());
				customer.setCity(license.getCity());
				customer.setProvince(license.getProvince());
				customer.setAgentNo(device.getAgentNo());
				// 商户证件信息
				CustomerCert customerCert = new CustomerCert();
				customerCert.setIdCard(idImgFilePath);
				customerCert.setOrganizationCert(bankImgFilePath);
				// 费率信息  默认
				List<CustomerFee> custFees = new ArrayList<>();
				// 代付交易配置
				ServiceConfigBean serviceConfigBean = new ServiceConfigBean();
				serviceConfigBean.setPhone(license.getPhone());
				serviceConfigBean.setComplexPassword(Md5Util.hmacSign("","DPAY"));
				serviceConfigBean.setHolidayStatus("TRUE");// 默认
				serviceConfigBean.setManualAudit("TRUE");// 默认
				serviceConfigBean.setUsePhoneCheck("TRUE");// 默认
				serviceConfigBean.setFireType("MAN");
				// 代收交易配置
				ReceiveConfigInfoBean receiveConfigInfoBean = new ReceiveConfigInfoBean();
				receiveConfigInfoBean.setDailyMaxAmount(0);
				receiveConfigInfoBean.setRemark("扫码入网");
				// 商户结算卡信息
				CustomerSettle customerSettle = new CustomerSettle();
				customerSettle.setAccountName(license.getAccName());
				customerSettle.setAccountNo(license.getAccNo());
				customerSettle.setOpenBankName(license.getBankName());
				customerSettle.setCustomerType(CustomerType.INDIVIDUAL);
				String customerNo = customerService.QrCreate(JsonUtils.toObject(JsonUtils.toJsonString(license), new TypeReference<com.yl.boss.entity.License>() {}), customer, customerCert, custFees, serviceConfigBean, receiveConfigInfoBean, customerSettle, "扫码入网", "扫码入网", 1);
				device.setStatus(ProcessStatus.BINDED);
				device.setCustomerNo(customerNo);
				device.setUpdateTime(new Date());
				deviceService.upDevice(device);
				return true;
			} else {
				// 更新临时数据
				qRCodeService.updateNetwork(JsonUtils.toObject(JsonUtils.toJsonString(license), new TypeReference<com.yl.boss.entity.License>() {}));
				// 商户基本信息
				Customer customer = customerService.findByCustNo(device.getCustomerNo());
				customer.setShortName(license.getFullName());
				customer.setFullName(license.getFullName());
				customer.setPhoneNo(license.getPhone());
				customer.setLinkMan(license.getLinkName());
				customer.setCustomerType(CustomerType.INDIVIDUAL); // 默认个体
				customer.setAddr(license.getAddr());
				customer.setCity(license.getCity());
				customer.setProvince(license.getProvince());
				customer.setAgentNo(device.getAgentNo());
				// 商户证件信息
				CustomerCert customerCert = customerCertService.findByCustNo(device.getCustomerNo());
				customerCert.setIdCard(idImgFilePath);
				customerCert.setOrganizationCert(bankImgFilePath);
				// 费率信息  默认
				List<CustomerFee> custFees = new ArrayList<>();
				// 代付交易配置
				ServiceConfigBean serviceConfigBean = serviceConfigFacade.findServiceConfigById(device.getCustomerNo());
				if (serviceConfigBean != null) {
					serviceConfigBean.setPhone(license.getPhone());
					serviceConfigBean.setComplexPassword(Md5Util.hmacSign("","DPAY"));
					serviceConfigBean.setHolidayStatus("TRUE");// 默认
					serviceConfigBean.setManualAudit("TRUE");// 默认
					serviceConfigBean.setUsePhoneCheck("TRUE");// 默认
					serviceConfigBean.setFireType("MAN");
				}
				// 代收交易配置
				ReceiveConfigInfoBean receiveConfigInfoBean = receiveQueryFacade.queryReceiveConfigBy(device.getCustomerNo());
				receiveConfigInfoBean.setDailyMaxAmount(0);
				receiveConfigInfoBean.setRemark("扫码入网");
				// 商户结算卡信息
				CustomerSettle customerSettle = customerSettleService.findByCustNo(device.getCustomerNo());
				customerSettle.setAccountName(license.getAccName());
				customerSettle.setAccountNo(license.getAccNo());
				customerSettle.setOpenBankName(license.getBankName());
				customerSettle.setCustomerType(CustomerType.INDIVIDUAL);
				customerService.qrUpdateCustomer(customer, serviceConfigBean, customerCert, customerSettle, custFees, "QR", receiveConfigInfoBean, "QRCODE", 1);
				return true;
			}
		} catch (Exception e) {
			logger.error("扫码入网失败！用户手机号{}异常信息：{}", license.getPhone(), e);
			return false;
		}
	}


	
	public QRCodeService getqRCodeService() {
		return qRCodeService;
	}

	public void setqRCodeService(QRCodeService qRCodeService) {
		this.qRCodeService = qRCodeService;
	}

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public CustomerCertService getCustomerCertService() {
		return customerCertService;
	}

	public void setCustomerCertService(CustomerCertService customerCertService) {
		this.customerCertService = customerCertService;
	}

	public CustomerSettleService getCustomerSettleService() {
		return customerSettleService;
	}

	public void setCustomerSettleService(CustomerSettleService customerSettleService) {
		this.customerSettleService = customerSettleService;
	}

	public CustomerFeeService getCustomerFeeService() {
		return customerFeeService;
	}

	public void setCustomerFeeService(CustomerFeeService customerFeeService) {
		this.customerFeeService = customerFeeService;
	}

	public ReceiveQueryFacade getReceiveQueryFacade() {
		return receiveQueryFacade;
	}

	public void setReceiveQueryFacade(ReceiveQueryFacade receiveQueryFacade) {
		this.receiveQueryFacade = receiveQueryFacade;
	}

	public AccountQueryInterface getAccountQueryInterface() {
		return accountQueryInterface;
	}

	public void setAccountQueryInterface(AccountQueryInterface accountQueryInterface) {
		this.accountQueryInterface = accountQueryInterface;
	}

	public ReceiveConfigInfoBean getReceiveConfigInfoBean() {
		return receiveConfigInfoBean;
	}

	public void setReceiveConfigInfoBean(ReceiveConfigInfoBean receiveConfigInfoBean) {
		this.receiveConfigInfoBean = receiveConfigInfoBean;
	}

	public ServiceConfigFacade getServiceConfigFacade() {
		return serviceConfigFacade;
	}

	public void setServiceConfigFacade(ServiceConfigFacade serviceConfigFacade) {
		this.serviceConfigFacade = serviceConfigFacade;
	}

	public static Properties getProp() {
		return prop;
	}

	public static void setProp(Properties prop) {
		QRCodeInterfaceImpl.prop = prop;
	}
}