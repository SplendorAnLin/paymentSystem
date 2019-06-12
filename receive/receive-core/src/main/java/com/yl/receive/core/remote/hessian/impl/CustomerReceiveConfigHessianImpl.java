package com.yl.receive.core.remote.hessian.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.receive.core.entity.ReceiveConfigInfo;
import com.yl.receive.core.service.ReceiveConfigInfoService;
import com.yl.receive.hessian.CustomerReceiveConfigHessian;
import com.yl.receive.hessian.bean.ReceiveConfigInfoBean;
import com.yl.receive.hessian.enums.ReceiveConfigStatus;

/**
 * 商户代收配置远程服务实现
 * 
 * @author 聚合支付有限公司
 * @since 2017年1月7日
 * @version V1.0.0
 */
@Service("customerReceiveConfigHessian")
public class CustomerReceiveConfigHessianImpl implements
		CustomerReceiveConfigHessian {
	
	@Resource
	private ReceiveConfigInfoService receiveConfigInfoService;

	@Override
	public ReceiveConfigInfoBean findByOwnerId(String ownerId) {
		ReceiveConfigInfo receiveConfigInfo = receiveConfigInfoService.queryBy(ownerId);
		if(receiveConfigInfo == null){
			return null;
		}
		ReceiveConfigInfoBean receiveConfigInfoBean = new ReceiveConfigInfoBean();
		receiveConfigInfoBean.setCreateTime(receiveConfigInfo.getCreateTime());
		receiveConfigInfoBean.setCustIp(receiveConfigInfo.getCustIp());
		receiveConfigInfoBean.setDailyMaxAmount(receiveConfigInfo.getDailyMaxAmount());
		receiveConfigInfoBean.setDomain(receiveConfigInfo.getDomain());
		receiveConfigInfoBean.setIsCheckContract(receiveConfigInfo.getIsCheckContract());
		receiveConfigInfoBean.setLastUpdateTime(receiveConfigInfo.getLastUpdateTime());
		receiveConfigInfoBean.setOwnerId(ownerId);
		receiveConfigInfoBean.setPrivateCer(receiveConfigInfo.getPrivateCer());
		receiveConfigInfoBean.setPublicCer(receiveConfigInfo.getPublicCer());
		receiveConfigInfoBean.setRemark(receiveConfigInfo.getRemark());
		receiveConfigInfoBean.setSingleBatchMaxNum(receiveConfigInfo.getSingleBatchMaxNum());
		receiveConfigInfoBean.setSingleMaxAmount(receiveConfigInfo.getSingleMaxAmount());
		receiveConfigInfoBean.setStatus(ReceiveConfigStatus.valueOf(receiveConfigInfo.getStatus().toString()));
		return receiveConfigInfoBean;
	}

	@Override
	public ReceiveConfigInfoBean findByOwnerIdAndStatus(String ownerId,
			ReceiveConfigStatus receiveConfigStatus) {
		//XXX 待修改
		ReceiveConfigInfo receiveConfigInfo = receiveConfigInfoService.queryBy(ownerId);
		if(receiveConfigInfo == null){
			return null;
		}
		if(receiveConfigInfo.getStatus() == com.yl.receive.core.enums.ReceiveConfigStatus.FALSE){
			return null;
		}
		ReceiveConfigInfoBean receiveConfigInfoBean = new ReceiveConfigInfoBean();
		receiveConfigInfoBean.setCreateTime(receiveConfigInfo.getCreateTime());
		receiveConfigInfoBean.setCustIp(receiveConfigInfo.getCustIp());
		receiveConfigInfoBean.setDailyMaxAmount(receiveConfigInfo.getDailyMaxAmount());
		receiveConfigInfoBean.setDomain(receiveConfigInfo.getDomain());
		receiveConfigInfoBean.setIsCheckContract(receiveConfigInfo.getIsCheckContract());
		receiveConfigInfoBean.setLastUpdateTime(receiveConfigInfo.getLastUpdateTime());
		receiveConfigInfoBean.setOwnerId(ownerId);
		receiveConfigInfoBean.setPrivateCer(receiveConfigInfo.getPrivateCer());
		receiveConfigInfoBean.setPublicCer(receiveConfigInfo.getPublicCer());
		receiveConfigInfoBean.setRemark(receiveConfigInfo.getRemark());
		receiveConfigInfoBean.setSingleBatchMaxNum(receiveConfigInfo.getSingleBatchMaxNum());
		receiveConfigInfoBean.setSingleMaxAmount(receiveConfigInfo.getSingleMaxAmount());
		receiveConfigInfoBean.setStatus(ReceiveConfigStatus.valueOf(receiveConfigInfo.getStatus().toString()));
		return receiveConfigInfoBean;
	}

	@Override
	public void updateKeys(ReceiveConfigInfoBean receiveConfigInfoBean) {
		receiveConfigInfoService.updateKeys(receiveConfigInfoBean.getOwnerId(), receiveConfigInfoBean.getPrivateCer(), receiveConfigInfoBean.getPublicCer());
	}
}