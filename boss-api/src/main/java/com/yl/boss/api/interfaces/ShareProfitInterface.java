package com.yl.boss.api.interfaces;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yl.boss.api.bean.ShareProfitBean;

/**
 * 分润远程接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月19日
 * @version V1.0.0
 */
public interface ShareProfitInterface {
	
	/**
	 * 收支明细  年
	 * @param customerNo
	 * @return
	 */
	String initYear(String customerNo);
	
	/**
	 * 不算代付 假日付
	 * @param startTime
	 * @param endTime
	 * @param customerNo
	 * @return
	 */
	
	String orderSumNotRemit(Date startTime, Date endTime, String customerNo);
	
	/**
	 * 根据日期查询支付方式  时间 金额 笔数
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param owner
	 * @return
	 */
	
	String orderAmountSumByPayType(Date orderTimeStart, Date orderTimeEnd,String owner);
	
	/**
	 * 统计总笔数
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param owner
	 * @return
	 */
	
	String counts(Date orderTimeStart, Date orderTimeEnd,String owner);
	
	/**
	 * 查询收入信息  返回成功笔数 金额 时间
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param owner
	 * @return
	 * @throws ParseException 
	 */
	
	String income(Date orderTimeStart, Date orderTimeEnd,String owner,int day) throws ParseException;
	
	/**
	 * 查询交易经营分析
	 * @param ownerId 商户号
	 * @param current 查询页码
	 * @param showCount 显示条数
	 * @param productType 产品类型
	 * @param startTime 完成开始时间
	 * @param endTime 完成结束时间
	 * @return
	 */
	List<Map<String,Object>> findOnlineSales(String ownerId,int current,int showCount,String productType,Date startTime,Date endTime);
	String findOnlineSalesBy(String ownerId,int current,int showCount,String productType,Date startTime,Date endTime);

	/**
	 * 交易经营分析合计
	 * @param ownerId
	 * @return
	 */
	String sumOnlineSales(String ownerId,Date startTime, Date endTime);
	/**
	 * 創建分潤信息
	 * @param shareProfit
	 */
	public void createShareProfit(ShareProfitBean shareProfit);
	
	/**
	 * @param params
	 * @return
	 */
	public String findByMapShareProfitInterfaces(Map<String,Object> params);
	
	/**
	 * 分润vlh
	 * @param queryId
	 * @param params
	 * @return
	 */
	public Map<String, Object> query(String queryId, Map<String, Object> params);
	
	/**
	 * 創建分潤信息
	 * @param shareProfit
	 */
	public void createShareProfitAgent(ShareProfitBean shareProfit);
	
	/**
	 * APP 周销售趋势统计
	 */
	public Map<String, Object> weeklySales(String ownerId) throws ParseException;
	/**
	 * 根据订单号查询分润
	 */
	public Map<String, Object> findByOrderCode(String code);
}