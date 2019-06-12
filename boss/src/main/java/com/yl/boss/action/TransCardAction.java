package com.yl.boss.action;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yl.boss.entity.TransCard;
import com.yl.boss.entity.TransCardHistory;
import com.yl.boss.enums.CardStatus;
import com.yl.boss.service.TransCardHistoryService;
import com.yl.boss.service.TransCardService;

/**
 * 交易卡管理控制器
 * @author AnLin
 * 
 */
public class TransCardAction extends Struts2ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(TransCardAction.class);
	private TransCardService transCardService;
	private TransCardHistoryService transCardHistoryService;
	private TransCard transCard;
	private TransCardHistory transCardHistory;
	private List<TransCard> transCards;
	private List<TransCardHistory> transCardHistories;
	private String code;
	
	/**
	 * 修改卡状态
	 */
	public String updateStatus(){
		try {
			transCard = transCardService.findByCode(code);
			if (transCard == null) {
				code = "ERROR";
				return SUCCESS;
			}
			if (transCard.getCardStatus().name().equals("NORAML")) {
				transCard.setCardStatus(CardStatus.DISABLED);
			} else if (transCard.getCardStatus().name().equals("DISABLED")) {
				transCard.setCardStatus(CardStatus.NORAML);
			}
			transCardService.updateTransCard(transCard);
		} catch (Exception e) {
			code = "ERROR";
			logger.error("卡状态修改失败！错误原因：{}", e);
		}
		code = "SUCCESS";
		return SUCCESS;
	}
	
	/**
	 * 修改交易卡
	 */
	public String updateTransCard(){
		transCardService.updateTransCard(transCard);
		return SUCCESS;
	}
	
	/**
	 * 查询卡历史
	 */
	public String findTransHistory(){
		transCardHistories = transCardHistoryService.findTransCardHistoryByCustAndAcc(transCard.getCustomerNo(), transCard.getAccountNo());
		return SUCCESS;
	}
	

	public TransCardService getTransCardService() {
		return transCardService;
	}

	public void setTransCardService(TransCardService transCardService) {
		this.transCardService = transCardService;
	}

	public TransCard getTransCard() {
		return transCard;
	}
	
	public void setTransCard(TransCard transCard) {
		this.transCard = transCard;
	}

	public TransCardHistoryService getTransCardHistoryService() {
		return transCardHistoryService;
	}

	public void setTransCardHistoryService(TransCardHistoryService transCardHistoryService) {
		this.transCardHistoryService = transCardHistoryService;
	}

	public TransCardHistory getTransCardHistory() {
		return transCardHistory;
	}

	public void setTransCardHistory(TransCardHistory transCardHistory) {
		this.transCardHistory = transCardHistory;
	}

	public List<TransCard> getTransCards() {
		return transCards;
	}

	public void setTransCards(List<TransCard> transCards) {
		this.transCards = transCards;
	}

	public List<TransCardHistory> getTransCardHistories() {
		return transCardHistories;
	}

	public void setTransCardHistories(List<TransCardHistory> transCardHistories) {
		this.transCardHistories = transCardHistories;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}