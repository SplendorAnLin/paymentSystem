package com.yl.dpay.core.hessian;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.dpay.core.entity.Payee;
import com.yl.dpay.core.entity.ServiceConfig;
import com.yl.dpay.core.entity.ServiceConfigHistory;
import com.yl.dpay.core.enums.OwnerRole;
import com.yl.dpay.core.enums.Status;
import com.yl.dpay.core.enums.FireType;
import com.yl.dpay.core.service.PayeeConfigService;
import com.yl.dpay.core.service.ServiceConfigHistoryService;
import com.yl.dpay.core.service.ServiceConfigService;
import com.yl.dpay.hessian.beans.PayeeBean;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.dpay.hessian.beans.ServiceConfigHistoryBean;
import com.yl.dpay.hessian.service.ServiceConfigFacade;

/**
 * 代付开通、设置、修改业务远程接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月17日
 * @version V1.0.0
 */
@Service("serviceConfigFacade")
public class ServiceConfigFacadeImpl implements ServiceConfigFacade {
	
	//private Logger log = LoggerFactory.getLogger(ServiceConfigFacadeImpl.class);
	
	@Autowired
	private ServiceConfigService serviceConfigService;
	
	@Autowired
	private ServiceConfigHistoryService serviceConfigHistoryService;
	
	@Autowired
	private PayeeConfigService payeeConfigService;

	@Override
	public void openDF(ServiceConfigBean serviceConfigBean,String oper) {
		if(serviceConfigBean!=null&&oper!=null){
			ServiceConfig serviceConf = new ServiceConfig();
			serviceConf.setComplexPassword(serviceConfigBean.getComplexPassword());
			serviceConf.setOwnerRole(OwnerRole.valueOf(serviceConfigBean.getOwnerRole()));
			serviceConf.setManualAudit(serviceConfigBean.getManualAudit());
			serviceConf.setOwnerId(serviceConfigBean.getOwnerId());
			serviceConf.setPhone(serviceConfigBean.getPhone());
			serviceConf.setUsePhoneCheck(serviceConfigBean.getUsePhoneCheck());
			serviceConf.setValid(serviceConfigBean.getValid());
			serviceConf.setMaxAmount(serviceConfigBean.getMaxAmount());
			serviceConf.setMinAmount(serviceConfigBean.getMinAmount());
			serviceConf.setPublicKey(serviceConfigBean.getPublicKey());
			serviceConf.setPrivateKey(serviceConfigBean.getPrivateKey());
			serviceConf.setComplexPassword(serviceConfigBean.getComplexPassword());
			serviceConf.setCustIp(serviceConfigBean.getCustIp());
			serviceConf.setDomain(serviceConfigBean.getDomain());
			serviceConf.setHolidayStatus(Status.valueOf(serviceConfigBean.getHolidayStatus()));
			serviceConf.setBossAudit(serviceConfigBean.getBossAudit());
			serviceConf.setDayMaxAmount(serviceConfigBean.getDayMaxAmount());
			serviceConf.setStartTime(serviceConfigBean.getStartTime());
			serviceConf.setEndTime(serviceConfigBean.getEndTime());
			serviceConf.setFireType(FireType.valueOf(serviceConfigBean.getFireType()));
			serviceConfigService.create(serviceConf);
			insert(new ServiceConfigHistoryBean(serviceConfigBean,oper));
		}
	}

	@Override
	public void update(ServiceConfigBean serviceConfigBean) {
		ServiceConfig serviceConf = serviceConfigService.find(serviceConfigBean.getOwnerId());
		if(serviceConf != null){
			serviceConf.setPhone(serviceConfigBean.getPhone());
			serviceConf.setValid(serviceConfigBean.getValid());
			serviceConf.setUsePhoneCheck(serviceConfigBean.getUsePhoneCheck());
			serviceConf.setManualAudit(serviceConfigBean.getManualAudit());
			serviceConf.setCustIp(serviceConfigBean.getCustIp());
			serviceConf.setDomain(serviceConfigBean.getDomain());
			serviceConf.setMaxAmount(serviceConfigBean.getMaxAmount());
			serviceConf.setMinAmount(serviceConfigBean.getMinAmount());
			serviceConf.setHolidayStatus(Status.valueOf(serviceConfigBean.getHolidayStatus()));
			serviceConf.setBossAudit(serviceConfigBean.getBossAudit());
			serviceConf.setDayMaxAmount(serviceConfigBean.getDayMaxAmount());
			serviceConf.setStartTime(serviceConfigBean.getStartTime());
			serviceConf.setEndTime(serviceConfigBean.getEndTime());
			serviceConf.setFireType(FireType.valueOf(serviceConfigBean.getFireType()));
			serviceConfigService.update(serviceConf);
		}
	}
	@Override
	public void updateServiceConfigOnlyForAgentSystem(
			ServiceConfigBean serviceConfigBean) {
		ServiceConfig serviceConf = serviceConfigService.find(serviceConfigBean.getOwnerId());
		if(serviceConf != null){
			serviceConf.setPhone(serviceConfigBean.getPhone());
			serviceConf.setValid(serviceConfigBean.getValid());
			serviceConf.setUsePhoneCheck(serviceConfigBean.getUsePhoneCheck());
			serviceConf.setManualAudit(serviceConfigBean.getManualAudit());
			serviceConf.setOwnerId(serviceConfigBean.getOwnerId());
			serviceConf.setCustIp(serviceConfigBean.getCustIp());
			serviceConf.setDomain(serviceConfigBean.getDomain());
			serviceConf.setMaxAmount(serviceConfigBean.getMaxAmount());
			serviceConf.setMinAmount(serviceConfigBean.getMinAmount());
			serviceConf.setHolidayStatus(Status.valueOf(serviceConfigBean.getHolidayStatus()));
			serviceConf.setBossAudit(serviceConfigBean.getBossAudit());
			serviceConf.setDayMaxAmount(serviceConfigBean.getDayMaxAmount());
			serviceConf.setStartTime(serviceConfigBean.getStartTime());
			serviceConf.setEndTime(serviceConfigBean.getEndTime());
			serviceConf.setFireType(FireType.valueOf(serviceConfigBean.getFireType()));
			serviceConfigService.updateServiceConfigOnlyForAgentSystem(serviceConf);
		}
	}

	@Override
	public ServiceConfigBean query(String ownerId) {
		ServiceConfig serviceConf = serviceConfigService.find(ownerId);
		if(serviceConf == null){
			return null;
		}
		ServiceConfigBean serviceConfigBean = new ServiceConfigBean();
		serviceConfigBean.setComplexPassword(serviceConf.getComplexPassword());
		serviceConfigBean.setManualAudit(serviceConf.getManualAudit());
		serviceConfigBean.setOwnerId(serviceConf.getOwnerId());
		serviceConfigBean.setOwnerRole(serviceConf.getOwnerRole().toString());
		serviceConfigBean.setPhone(serviceConf.getPhone());
		serviceConfigBean.setUsePhoneCheck(serviceConf.getUsePhoneCheck());
		serviceConfigBean.setValid(serviceConf.getValid());
		serviceConfigBean.setPublicKey(serviceConf.getPublicKey());
		serviceConfigBean.setPrivateKey(serviceConf.getPrivateKey());
		serviceConfigBean.setMaxAmount(serviceConf.getMaxAmount());
		serviceConfigBean.setMinAmount(serviceConf.getMinAmount());
		serviceConfigBean.setCustIp(serviceConf.getCustIp());
		serviceConfigBean.setDomain(serviceConf.getDomain());
		serviceConfigBean.setHolidayStatus(serviceConf.getHolidayStatus().name());
		serviceConfigBean.setBossAudit(serviceConf.getBossAudit());
		serviceConfigBean.setDayMaxAmount(serviceConf.getDayMaxAmount());
		serviceConfigBean.setStartTime(serviceConf.getStartTime());
		serviceConfigBean.setEndTime(serviceConf.getEndTime());
		serviceConfigBean.setFireType(serviceConf.getFireType().toString());
		return serviceConfigBean;
	}

	@Override
	public void dfComplexPWDReset(String customerNo) {
		serviceConfigService.updateDfComplexPWDReset(customerNo);
	}

	@Override
	public void dfUpdateComplexPwd(ServiceConfigBean serviceConfigBean) {
		ServiceConfig serviceConf = new ServiceConfig();
		serviceConf.setComplexPassword(serviceConfigBean.getComplexPassword());
		serviceConf.setOwnerId(serviceConfigBean.getOwnerId());
		serviceConfigService.updatePassword(serviceConf);
	}

	@Override
	public String getPrivateKey(String ownerId) {
		return serviceConfigService.find(ownerId).getPrivateKey();
	}

	@Override
	public void updateKeys(ServiceConfigBean serviceConfigBean) {
		serviceConfigService.updateKeys(serviceConfigBean.getOwnerId(), serviceConfigBean.getPrivateKey(), serviceConfigBean.getPublicKey());
	}

	@Override
	public void delete(int id) {
		payeeConfigService.delete(id);
	}
	@Override
	public void create(PayeeBean payee) {
		payeeConfigService.create((Payee)JsonUtils.toJsonToObject(payee, Payee.class));
	}

	@Override
	public void updatePayeeBean(PayeeBean payee) {
		payeeConfigService.update((Payee)JsonUtils.toJsonToObject(payee, Payee.class));
	}

	@Override
	public PayeeBean findById(Long id) {
		PayeeBean payeeBean = null;
		Payee payee = payeeConfigService.findById(id);
		if(payee != null){
			payeeBean = new PayeeBean();
			payeeBean=JsonUtils.toJsonToObject(payee, PayeeBean.class);
		}
		return payeeBean;
	}

	@Override
	public void deleteAll(int[] ids) {
		payeeConfigService.deleteAll(ids);
	}

	
	@Override
	public ServiceConfigBean findServiceConfigById(String ownerId) {
		ServiceConfigBean serviceConfigBean=null;
		ServiceConfig serviceConfig=serviceConfigService.find(ownerId);
		if (serviceConfig!=null) {
			serviceConfigBean=new ServiceConfigBean();
			serviceConfigBean=JsonUtils.toJsonToObject(serviceConfig, ServiceConfigBean.class);
		}
		return serviceConfigBean;
	}

	@Override
	public void insert(ServiceConfigHistoryBean serviceConfigHistoryBean) {
		ServiceConfigHistory serviceConfigHistory=new ServiceConfigHistory();
		serviceConfigHistory.setCreateDate(new Date());
		serviceConfigHistory.setComplexPassword(serviceConfigHistoryBean.getComplexPassword());
		serviceConfigHistory.setCustIp(serviceConfigHistoryBean.getCustIp());
		serviceConfigHistory.setDomain(serviceConfigHistoryBean.getDomain());
		serviceConfigHistory.setManualAudit(serviceConfigHistoryBean.getManualAudit());
		serviceConfigHistory.setMaxAmount(serviceConfigHistoryBean.getMaxAmount());
		serviceConfigHistory.setMinAmount(serviceConfigHistoryBean.getMinAmount());
		serviceConfigHistory.setOperator(serviceConfigHistoryBean.getOperator());
		serviceConfigHistory.setOwnerId(serviceConfigHistoryBean.getOwnerId());
		serviceConfigHistory.setOwnerRole(OwnerRole.valueOf(serviceConfigHistoryBean.getOwnerRole()));
		serviceConfigHistory.setPhone(serviceConfigHistoryBean.getPhone());
		serviceConfigHistory.setPrivateKey(serviceConfigHistoryBean.getPrivateKey());
		serviceConfigHistory.setPublicKey(serviceConfigHistoryBean.getPublicKey());
		serviceConfigHistory.setUsePhoneCheck(serviceConfigHistoryBean.getUsePhoneCheck());
		serviceConfigHistory.setValid(serviceConfigHistoryBean.getValid());
		serviceConfigHistory.setHolidayStatus(Status.valueOf(serviceConfigHistoryBean.getHolidayStatus()));
		serviceConfigHistory.setBossAudit(serviceConfigHistoryBean.getBossAudit());
		serviceConfigHistory.setDayMaxAmount(serviceConfigHistoryBean.getDayMaxAmount());
		serviceConfigHistory.setStartTime(serviceConfigHistoryBean.getStartTime());
		serviceConfigHistory.setEndTime(serviceConfigHistoryBean.getEndTime());
		serviceConfigHistory.setFireType(FireType.valueOf(serviceConfigHistoryBean.getFireType()));
		serviceConfigHistoryService.insert(serviceConfigHistory);
	}

	@Override
	public ServiceConfigBean findByPhone(String phone) {
		return serviceConfigService.findByPhone(phone);
	}
}
