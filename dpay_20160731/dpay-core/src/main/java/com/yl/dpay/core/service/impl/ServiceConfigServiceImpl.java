package com.yl.dpay.core.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.pay.common.util.Md5Util;
import com.yl.dpay.core.Constant;
import com.yl.dpay.core.Utils.GengeratePassword;
import com.yl.dpay.core.dao.ServiceConfigDao;
import com.yl.dpay.core.entity.ServiceConfig;
import com.yl.dpay.core.enums.FireType;
import com.yl.dpay.core.service.ServiceConfigService;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.sms.SmsUtils;

/**
 * 代付配置服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
@Service("serviceConfigService")
public class ServiceConfigServiceImpl implements ServiceConfigService {

	//private static final Logger logger = LoggerFactory.getLogger(ServiceConfigServiceImpl.class);

	@Autowired
	private ServiceConfigDao serviceConfigDao;

	@Override
	@Transactional
	public void create(ServiceConfig serviceConfig) {
		serviceConfig.setCreateDate(new Date());
		serviceConfigDao.insert(serviceConfig);
	}

	@Override
	@Transactional
	public ServiceConfig find(String ownerId, String valid) {
		return serviceConfigDao.find(ownerId, valid);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateDfComplexPWDReset(String customerNo) {
		if (customerNo == null || customerNo.equals("")) {
			throw new RuntimeException("ServiceConfigServiceImpl updateDfComplexPWDReset ownerId is null!");
		}
		ServiceConfig serviceConfig = serviceConfigDao.find(customerNo, "TRUE");
		if (serviceConfig == null) {
			throw new RuntimeException("ServiceConfigServiceImpl updateDfComplexPWDReset serviceConfig is null!");
		}
		String pwd = GengeratePassword.getExchangeCode().toString();
		serviceConfig.setComplexPassword(Md5Util.hmacSign(pwd, "DPAY"));
		String smsStr="";
		try {
			if(serviceConfigDao.updateComplexPwd(serviceConfig)){//发送短信
				smsStr=String.format(Constant.SMS_RESET_COMPLEXPWD, pwd);
				SmsUtils.sendCustom(smsStr,serviceConfig.getPhone());
			}
		} catch (Exception e) {
			throw new RuntimeException("ServiceConfigServiceImpl updateDfComplexPWDReset update error!");
		}
	}

	@Override
	@Transactional
	public void update(ServiceConfig serviceConf) {
		if (serviceConf.getOwnerId() == null || serviceConf.getOwnerId().equals("")) {
			throw new RuntimeException("ServiceConfigServiceImpl update ownerId is null!");
		}
		ServiceConfig serviceConfig = serviceConfigDao.findByOwnerId(serviceConf.getOwnerId());
		if (serviceConfig == null) {
			throw new RuntimeException("ServiceConfigServiceImpl update serviceConfig is null!");
		}
		serviceConfig.setManualAudit(serviceConf.getManualAudit());
		serviceConfig.setCustIp(serviceConf.getCustIp());
		serviceConfig.setDomain(serviceConf.getDomain());
		serviceConfig.setPhone(serviceConf.getPhone());
		serviceConfig.setValid(serviceConf.getValid());
		serviceConfig.setUsePhoneCheck(serviceConf.getUsePhoneCheck());
		serviceConfig.setMaxAmount(serviceConf.getMaxAmount());
		serviceConfig.setMinAmount(serviceConf.getMinAmount());
		serviceConfig.setHolidayStatus(serviceConf.getHolidayStatus());
		serviceConfig.setDayMaxAmount(serviceConf.getDayMaxAmount());
		serviceConfig.setBossAudit(serviceConf.getBossAudit());
		serviceConfig.setStartTime(serviceConf.getStartTime());
		serviceConfig.setEndTime(serviceConf.getEndTime());
		serviceConfig.setFireType(serviceConf.getFireType());
		serviceConfigDao.update(serviceConfig);
	}
	@Override
	public void updateServiceConfigOnlyForAgentSystem(ServiceConfig serviceConf) {
		if (serviceConf.getOwnerId() == null || serviceConf.getOwnerId().equals("")) {
			throw new RuntimeException("ServiceConfigServiceImpl update ownerId is null!");
		}
		ServiceConfig serviceConfig = serviceConfigDao.findByOwnerId(serviceConf.getOwnerId());
		if (serviceConfig == null) {
			throw new RuntimeException("ServiceConfigServiceImpl update serviceConfig is null!");
		}
		serviceConfig.setManualAudit(serviceConf.getManualAudit()!=null?serviceConf.getManualAudit():serviceConfig.getManualAudit());
		serviceConfig.setUsePhoneCheck(serviceConf.getUsePhoneCheck()!=null?serviceConf.getUsePhoneCheck():serviceConfig.getUsePhoneCheck());
		serviceConfig.setCustIp(serviceConf.getCustIp()!=null?serviceConf.getCustIp():serviceConfig.getCustIp());
		serviceConfig.setDomain(serviceConf.getDomain()!=null?serviceConf.getDomain():serviceConfig.getDomain());//添加该更新方法原因是agent系统商户能修改域名
		serviceConfig.setMaxAmount(serviceConf.getMaxAmount()>0?serviceConf.getMaxAmount():serviceConfig.getMaxAmount());
		serviceConfig.setMinAmount(serviceConf.getMinAmount()>0?serviceConf.getMinAmount():serviceConfig.getMinAmount());
		serviceConfig.setHolidayStatus(serviceConf.getHolidayStatus());
		serviceConfig.setDayMaxAmount(serviceConf.getDayMaxAmount()>0?serviceConf.getDayMaxAmount():serviceConfig.getDayMaxAmount());
		serviceConfig.setBossAudit(serviceConf.getBossAudit());
		serviceConfig.setStartTime(serviceConf.getStartTime());
		serviceConfig.setEndTime(serviceConf.getEndTime());
		serviceConfig.setFireType(serviceConf.getFireType());
		serviceConfigDao.update(serviceConfig);
	}

	@Override
	@Transactional
	public void updatePassword(ServiceConfig serviceConf) {
		if (serviceConf.getOwnerId() == null || serviceConf.getOwnerId().equals("")) {
			throw new RuntimeException("ServiceConfigServiceImpl update ownerId is null!");
		}
		ServiceConfig serviceConfig = serviceConfigDao.findByOwnerId(serviceConf.getOwnerId());
		if (serviceConfig == null) {
			throw new RuntimeException("ServiceConfigServiceImpl update serviceConfig is null!");
		}
		serviceConfig.setComplexPassword(Md5Util.hmacSign(serviceConf.getComplexPassword(), "DPAY"));
		serviceConfigDao.update(serviceConfig);
	}

	@Override
	public ServiceConfig find(String ownerId) {
		return serviceConfigDao.findByOwnerId(ownerId);
	}

	@Override
	@Transactional
	public void updateKeys(String customerNo,String privateKey,String publicKey) {
		ServiceConfig serviceConfig = serviceConfigDao.findByOwnerId(customerNo);
		if(serviceConfig != null){
			serviceConfig.setPrivateKey(privateKey);
			serviceConfig.setPublicKey(publicKey);
			serviceConfigDao.update(serviceConfig);
		}
	}

	@Override
	public ServiceConfigBean findByPhone(String phone) {
		return serviceConfigDao.findByPhone(phone);
	}

	@Override
	public List<ServiceConfig> findByFireType(FireType fireType, String valid) {
		return serviceConfigDao.findByFireType(fireType, valid);
	}

}
