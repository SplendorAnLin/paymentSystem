package com.yl.payinterface.core.remote.hessian.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.payinterface.core.bean.BindCardInfoBean;
import com.yl.payinterface.core.enums.AuthPayBindCardInfoBeanStatus;
import com.yl.payinterface.core.enums.AuthPayBindCardInfoStatus;
import com.yl.payinterface.core.hessian.BindCardInfoHessianService;
import com.yl.payinterface.core.model.BindCardInfo;
import com.yl.payinterface.core.service.BindCardInfoService;

/**
 * 
 * @ClassName BindCardInfoHessianServiceImpl
 * @Description 认证支付绑卡信息hessian处理类
 * @author 聚合支付
 * @date 2017年9月1日 下午10:28:34
 */
@Service("bindCardInfoHessianService")
public class BindCardInfoHessianServiceImpl implements BindCardInfoHessianService {

	@Resource
	private BindCardInfoService bindCardInfoService;

	@Override
	public void updateFailed(String cardNo, String interfaceInfoCode) {
		bindCardInfoService.updateFailed(cardNo, interfaceInfoCode);
	}

	@Override
	public void save(BindCardInfoBean bindCardInfoBean) {
		BindCardInfo bindCardInfo = new BindCardInfo();
		bindCardInfo.setStatus(AuthPayBindCardInfoStatus.SUCCESS);
		bindCardInfo.setToken(bindCardInfoBean.getToken());
		bindCardInfo.setCardNo(bindCardInfoBean.getCardNo());
		bindCardInfo.setInterfaceInfoCode(bindCardInfoBean.getInterfaceInfoCode());
		bindCardInfo.setRemark(bindCardInfoBean.getRemark());
		bindCardInfo.setEffectiveDate(bindCardInfoBean.getEffectiveDate());
		bindCardInfo.setExpiryDate(bindCardInfoBean.getExpiryDate());
		bindCardInfo.setCvv(bindCardInfoBean.getCvv());
		bindCardInfo.setValidityYear(bindCardInfoBean.getValidityYear());
		bindCardInfo.setValidityMonth(bindCardInfoBean.getValidityMonth());
		bindCardInfoService.save(bindCardInfo);
	}

	@Override
	public void update(BindCardInfoBean bindCardInfoBean) {
		BindCardInfo bindCardInfo = new BindCardInfo();
		bindCardInfo.setStatus(AuthPayBindCardInfoStatus.valueOf(bindCardInfoBean.getStatus().name()));
		bindCardInfo.setToken(bindCardInfoBean.getToken());
		bindCardInfo.setEffectiveDate(bindCardInfoBean.getEffectiveDate());
		bindCardInfo.setExpiryDate(bindCardInfoBean.getExpiryDate());
		bindCardInfoService.update(bindCardInfo);
	}

	@Override
	public BindCardInfoBean find(String cardNo, String interfaceInfoCode) {
		BindCardInfo bindCardInfo = bindCardInfoService.find(cardNo, interfaceInfoCode);
		BindCardInfoBean bean = new BindCardInfoBean();

		bean.setCardNo(bindCardInfo.getCardNo());
		bean.setCode(bindCardInfo.getCode());
		bean.setId(bindCardInfo.getId());
		bean.setInterfaceInfoCode(bindCardInfo.getInterfaceInfoCode());
		bean.setRemark(bindCardInfo.getRemark());
		bean.setToken(bindCardInfo.getToken());
		bean.setStatus(AuthPayBindCardInfoBeanStatus.valueOf(bindCardInfo.getStatus().name()));
		bean.setCvv(bindCardInfo.getCvv());
		bean.setEffectiveDate(bindCardInfo.getEffectiveDate());
		bean.setExpiryDate(bindCardInfo.getExpiryDate());
		bean.setValidityMonth(bindCardInfo.getValidityMonth());
		bean.setValidityYear(bindCardInfo.getValidityYear());
		return bean;
	}

	@Override
	public void saveOrUpdate(BindCardInfoBean bindCardInfoBean) {
		BindCardInfo bindCardInfo = new BindCardInfo();
		bindCardInfo.setStatus(AuthPayBindCardInfoStatus.valueOf(bindCardInfoBean.getStatus().name()));
		bindCardInfo.setToken(bindCardInfoBean.getToken());
		bindCardInfo.setCardNo(bindCardInfoBean.getCardNo());
		bindCardInfo.setInterfaceInfoCode(bindCardInfoBean.getInterfaceInfoCode());
		bindCardInfo.setRemark(bindCardInfoBean.getRemark());
		bindCardInfo.setEffectiveDate(bindCardInfoBean.getEffectiveDate());
		bindCardInfo.setExpiryDate(bindCardInfoBean.getExpiryDate());
		bindCardInfo.setCvv(bindCardInfoBean.getCvv());
		bindCardInfo.setValidityYear(bindCardInfoBean.getValidityYear());
		bindCardInfo.setValidityMonth(bindCardInfoBean.getValidityMonth());
		bindCardInfoService.saveOrUpdate(bindCardInfo);
	}

	@Override
	public BindCardInfoBean findEffective(String cardNo, String interfaceInfoCode) {
		BindCardInfo bindCardInfo = bindCardInfoService.find(cardNo, interfaceInfoCode);
		BindCardInfoBean bean = new BindCardInfoBean();

		bean.setCardNo(bindCardInfo.getCardNo());
		bean.setCode(bindCardInfo.getCode());
		bean.setId(bindCardInfo.getId());
		bean.setInterfaceInfoCode(bindCardInfo.getInterfaceInfoCode());
		bean.setRemark(bindCardInfo.getRemark());
		bean.setToken(bindCardInfo.getToken());
		bean.setStatus(AuthPayBindCardInfoBeanStatus.valueOf(bindCardInfo.getStatus().name()));
		bean.setEffectiveDate(bindCardInfo.getEffectiveDate());
		bean.setExpiryDate(bindCardInfo.getExpiryDate());

		return bean;
	}

}
