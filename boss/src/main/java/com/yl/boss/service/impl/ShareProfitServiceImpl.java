package com.yl.boss.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yl.boss.dao.ShareProfitDao;
import com.yl.boss.entity.ShareProfit;
import com.yl.boss.service.ShareProfitService;

/**
 * 分润信息业务访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class ShareProfitServiceImpl implements ShareProfitService {
	
	private ShareProfitDao shareProfitDao;

	@Override
	public void create(ShareProfit shareProfit) {
		shareProfit.setCreateTime(new Date());
		shareProfitDao.create(shareProfit);
	}

	@Override
	public void update(ShareProfit shareProfit) {
		// TODO Auto-generated method stub
	}
	
	

	@Override
	public ShareProfit findByOrderCode(String orderCode) {
		return shareProfitDao.findByOrderCode(orderCode);
	}

	@Override
	public List<ShareProfit> findByCustNo(String custNo) {
		return shareProfitDao.findByCustNo(custNo);
	}

	@Override
	public List<ShareProfit> findByAgentNo(String agentNo) {
		return shareProfitDao.findByAgentNo(agentNo);
	}

	@Override
	public List<ShareProfit> findByInterfaceCode(String interfaceCode) {
		return shareProfitDao.findByInterfaceCode(interfaceCode);
	}
	
	@Override
	public String findByMapShareProfit(Map<String, Object> params) {
		return shareProfitDao.findByMapShareProfit(params);
	}
	
	@Override
	public String findByMapShareProfitInterfaces(Map<String, Object> params) {
		return shareProfitDao.findByMapShareProfitInterfaces(params);
	}

	public ShareProfitDao getShareProfitDao() {
		return shareProfitDao;
	}

	public void setShareProfitDao(ShareProfitDao shareProfitDao) {
		this.shareProfitDao = shareProfitDao;
	}

	@Override
	public String income(Date orderTimeStart, Date orderTimeEnd, String owner, int day) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return shareProfitDao.income(sdf.format(orderTimeStart), sdf.format(orderTimeEnd), owner, day);
	}

	@Override
	public String counts(Date orderTimeStart, Date orderTimeEnd, String owner) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return shareProfitDao.counts(sdf.format(orderTimeStart), sdf.format(orderTimeEnd), owner);
	}

	@Override
	public String orderAmountSumByPayType(Date orderTimeStart, Date orderTimeEnd, String owner) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return shareProfitDao.orderAmountSumByPayType(sdf.format(orderTimeStart), sdf.format(orderTimeEnd), owner);
	}

	@Override
	public String orderSumNotRemit(Date orderTimeStart, Date orderTimeEnd, String owner) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return shareProfitDao.orderSumNotRemit(sdf.format(orderTimeStart), sdf.format(orderTimeEnd), owner);
	}

	@Override
	public String incomeYear(String owner) {
		return shareProfitDao.incomeYear(owner);
	}

	@Override
	public Map<String, Object> weeklySales(String ownerId) throws ParseException {
		return shareProfitDao.weeklySales(ownerId);
	}

	@Override
	public List<Object[]> spExport(Map<String, Object> params) {
		return shareProfitDao.spExport(params);
	}

	@Override
	public List<Map<String, Object>> findOnlineSales(String ownerId, int current, int showCount, String productType,
			Date startTime, Date endTime) {
		return shareProfitDao.findOnlineSales(ownerId, current, showCount, productType, startTime, endTime);
	}

	@Override
	public List<Map<String, Object>> sumOnlineSales(String ownerId,Date startTime, Date endTime) {
		return shareProfitDao.sumOnlineSales(ownerId,startTime,endTime);
	}
}