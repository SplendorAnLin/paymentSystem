package com.yl.boss.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yl.boss.entity.ShareProfit;

/**
 * 分润信息数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public interface ShareProfitDao {
	
	/**
	 * 总计不算代收假日付
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param owner
	 * @return
	 */
	String orderSumNotRemit(String orderTimeStart, String orderTimeEnd,String owner);
	
	/**
	 * 根据日期查询支付方式 时间 金额 笔数
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param owner
	 * @return
	 */
	String orderAmountSumByPayType(String orderTimeStart, String orderTimeEnd,String owner);
	
	/**
	 * 统计总笔数
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param owner
	 * @return
	 */
	String counts(String orderTimeStart, String orderTimeEnd,String owner);
	
	/**
	 * 查询收入信息  返回成功笔数 金额 时间
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param owner
	 * @return
	 * @throws ParseException 
	 */
	String income(String orderTimeStart, String orderTimeEnd,String owner, int day) throws ParseException;
	
	/**
	 * 收支明细 - 一年的数据
	 * @return
	 */
	String incomeYear(String owner);
	
	/**
	 * 创建分润信息
	 * @param shareProfit
	 */
	void create(ShareProfit shareProfit);
	
	/**
	 * 更新分润信息
	 * @param shareProfit
	 */
	void update(ShareProfit shareProfit);
	
	/**
	 * 根据订单号查询
	 * @param orderCode
	 * @return
	 */
	ShareProfit findByOrderCode(String orderCode);
	
	/**
	 * 根据商编查询
	 * @param custNo
	 * @return
	 */
	List<ShareProfit> findByCustNo(String custNo);
	
	/**
	 * 根据服务商编号查询
	 * @param agentNo
	 * @return
	 */
	List<ShareProfit> findByAgentNo(String agentNo);
	
	/**
	 * 根据接口编号查询
	 * @param interfaceCode
	 * @return
	 */
	List<ShareProfit> findByInterfaceCode(String interfaceCode);
	
	/**
	 * 根据分润信息对象查询
	 * @param shareProfit
	 * @return
	 */
	String findByMapShareProfit(Map<String,Object> params);
	
	/**
	 * 根据其它项目分润信息查询合计
	 * @param shareProfit
	 * @return
	 */
	String findByMapShareProfitInterfaces(Map<String,Object> params);
	
	/**
	 * APP 周销售趋势统计
	 */
	Map<String, Object> weeklySales(String ownerId) throws ParseException;
	
	/**
	 * 导出
	 * @param params
	 * @return
	 */
	List<Object[]> spExport(Map<String,Object> params);
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
	List<Map<String, Object>> findOnlineSales(String ownerId,int current,int showCount,String productType,Date startTime,Date endTime);
	/**
	 * 交易经营分析通道合计
	 * @param ownerId
	 * @return
	 */
	public List<Map<String, Object>> sumOnlineSales(String ownerId,Date startTime, Date endTime);
}