package com.yl.boss.service.impl;

import java.util.List;
import java.util.Map;

import com.yl.boss.dao.TransCardHistoryDao;
import com.yl.boss.entity.TransCard;
import com.yl.boss.entity.TransCardHistory;
import com.yl.boss.enums.CardAttr;
import com.yl.boss.service.TransCardHistoryService;
import com.yl.boss.service.TransCardService;

/**
 * 交易卡历史服务接口实现
 * @author AnLin
 *
 */
public class TransCardHistoryServiceImpl implements TransCardHistoryService{

	TransCardHistoryDao transCardHistoryDao;
	
	TransCardService transCardService;
	
	@Override
	public void addTransCardHistory(TransCardHistory transCardHistory) {
		TransCard transCard = transCardService.findByCustNoAndAccNo(transCardHistory.getAccountNo(), transCardHistory.getCustomerNo(), CardAttr.TRANS_CARD);
		if (transCard == null) throw new RuntimeException(transCardHistory.getCustomerNo() + "未查询到交易卡信息！");
		transCardHistory.setAccountName(transCard.getAccountName());
		transCardHistory.setBankCode(transCard.getBankCode());
		transCardHistory.setBankName(transCard.getBankName());
		transCardHistory.setCardAlias(transCard.getCardAlias());
		transCardHistory.setCardStatus(transCard.getCardStatus());
		transCardHistory.setCardType(transCard.getCardType());
		transCardHistory.setClearBank(transCard.getClearBank());
		transCardHistory.setClearBankCode(transCard.getClearBankCode());
		transCardHistory.setTiedTime(transCard.getTiedTime());
		transCardHistory.setUnlockTime(transCard.getUnlockTime());
		transCardHistory.setSettleCode(transCard.getSettleCode());
		transCardHistory.setPhone(transCard.getPhone());
		transCardHistory.setIdNumber(transCard.getIdNumber());
		transCardHistory.setCode(transCard.getCode());
		transCardHistory.setCnapsCode(transCard.getCnapsCode());
		transCardHistoryDao.addTransCardHistory(transCardHistory);
	}
	
	@Override
	public void addTransCardHisForTrade(Map<String, Object> params) {
		// 查询商户结算卡信息
		TransCard transCard = transCardService.findByCustNoAndAccNo(params.get("customerNo").toString(), params.get("accountNo").toString(), CardAttr.TRANS_CARD);
		if (transCard != null) {
			TransCardHistory transCardHistory = new TransCardHistory();
			transCardHistory.setAccountName(transCard.getAccountName());
			transCardHistory.setAccountNo(transCard.getAccountNo());
			transCardHistory.setAmount(Double.valueOf(params.get("amount").toString()));
			transCardHistory.setBankCode(transCard.getBankCode());
			transCardHistory.setBankName(transCard.getBankName());
			transCardHistory.setCardAlias(transCard.getCardAlias());
			transCardHistory.setCardAttr(transCard.getCardAttr());
			transCardHistory.setCardStatus(transCard.getCardStatus());
			transCardHistory.setCardType(transCard.getCardType());
			transCardHistory.setClearBank(transCard.getClearBank());
			transCardHistory.setClearBankCode(transCard.getClearBankCode());
			transCardHistory.setCnapsCode(transCard.getCnapsCode());
			transCardHistory.setCode(transCard.getCode());
			transCardHistory.setCustomerNo(transCard.getCustomerNo());
			transCardHistory.setIdNumber(transCard.getIdNumber());
			transCardHistory.setInterfaceCode(null!=params.get("interfaceCode")?params.get("interfaceCode").toString():null);
			transCardHistory.setOrderId(null!=params.get("orderId")?params.get("orderId").toString():null);
			transCardHistory.setInterfaceRequestId(null!=params.get("interfaceRequestId")?params.get("interfaceRequestId").toString():null);
			transCardHistory.setPhone(transCard.getPhone());
			transCardHistory.setSettleCode(transCard.getSettleCode());
			transCardHistory.setTiedTime(null!=transCard.getTiedTime()?transCard.getTiedTime():null);
			transCardHistory.setUnlockTime(null!=transCard.getUnlockTime()?transCard.getUnlockTime():null);
			transCardHistoryDao.addTransCardHistory(transCardHistory);
		} else {
			throw new RuntimeException("商户:{" + params.get("customerNo") + "}未绑定交易卡！");
		}
	}
	
	@Override
	public List<TransCardHistory> findByInterfaceRequestId(String interfaceRequestId) {
		return transCardHistoryDao.findByInterfaceRequestId(interfaceRequestId);
	}

	@Override
	public void updateTransCardHistory(TransCardHistory transCardHistory) {
		transCardHistoryDao.updateTransCardHistory(transCardHistory);
	}

	@Override
	public List<TransCardHistory> findTransCardHistoryByCustAndAcc(String customerNo, String accountNo) {
		return transCardHistoryDao.findTransCardHistoryByCustAndAcc(customerNo, accountNo);
	}

	
	public TransCardHistoryDao getTransCardHistoryDao() {
		return transCardHistoryDao;
	}

	public void setTransCardHistoryDao(TransCardHistoryDao transCardHistoryDao) {
		this.transCardHistoryDao = transCardHistoryDao;
	}

	public TransCardService getTransCardService() {
		return transCardService;
	}

	public void setTransCardService(TransCardService transCardService) {
		this.transCardService = transCardService;
	}
}