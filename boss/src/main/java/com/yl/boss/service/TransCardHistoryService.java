package com.yl.boss.service;

import java.util.List;
import java.util.Map;

import com.yl.boss.entity.TransCardHistory;

/**
 * 交易卡历史服务接口
 * @author AnLin
 *
 */
public interface TransCardHistoryService {

	/**
	 * 保存卡记录
	 */
	void addTransCardHistory(TransCardHistory transCardHistory);
	
	/**
	 * 修改卡记录
	 */
	void updateTransCardHistory(TransCardHistory transCardHistory);
	
	/**
	 * 根据商户编号/卡号查询卡记录
	 */
	List<TransCardHistory> findTransCardHistoryByCustAndAcc(String customerNo, String accountNo);
	
	/**
	 * 新增卡历史记录返回结算卡卡号
	 */
	void addTransCardHisForTrade(Map<String, Object> params);
	
	/**
	 * 根据接口号查询交易信息
	 */
	List<TransCardHistory> findByInterfaceRequestId(String interfaceCode);
}