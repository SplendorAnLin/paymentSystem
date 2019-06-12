package com.yl.boss.dao.impl;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.dao.ShareProfitDao;
import com.yl.boss.entity.ShareProfit;
import com.yl.boss.enums.ProductType;
import com.yl.boss.taglib.dict.DictUtils;

/**
 * 分润信息数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class ShareProfitDaoImpl implements ShareProfitDao {
	private Log log= LogFactory.getLog(ShareProfitDaoImpl.class);
	private EntityDao entityDao;
    final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    final static SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");

	@Override
	public void create(ShareProfit shareProfit) {
		entityDao.persist(shareProfit);
	}

	@Override
	public void update(ShareProfit shareProfit) {
		entityDao.update(shareProfit);
	}

	@Override
	public ShareProfit findByOrderCode(String orderCode) {
		String hql = " from ShareProfit where orderCode = ?";
		@SuppressWarnings("unchecked")
		List<ShareProfit> list = entityDao.find(hql, orderCode);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShareProfit> findByCustNo(String custNo) {
		String hql = " from ShareProfit where customerNo = ?";
		return entityDao.find(hql, custNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShareProfit> findByAgentNo(String agentNo) {
		String hql = " from ShareProfit where agentNo = ?";
		return entityDao.find(hql, agentNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShareProfit> findByInterfaceCode(String interfaceCode) {
		String hql = " from ShareProfit where interfaceCode = ?";
		return entityDao.find(hql, interfaceCode);
	}

	@Override
	public String findByMapShareProfit(Map<String, Object> params) {
		String sql = "SELECT COUNT(orderCode) as total_number," + "SUM(amount) as total_amount,SUM(fee) as total_fee,"
				+ "SUM(channelCost)" + " as channel_cost,SUM(agentProfit) as agent_profit,SUM(platformProfit)"
				+ " as platfrom_profit from ShareProfit";
		return ShareProfitConditions(sql, params);
	}

	@Override
	public String findByMapShareProfitInterfaces(Map<String, Object> params) {
		String sql = "SELECT COUNT(orderCode) as total_number," + "SUM(amount) as total_amount,SUM(fee) as total_fee,"
				+ "SUM(agentProfit) as agent_profit" + " from ShareProfit";
		return ShareProfitConditions(sql, params);
	}

	/**
	 * 查看合计的条件方法
	 * 
	 * @return
	 */
	private String ShareProfitConditions(String sql, Map<String, Object> params) {
		StringBuffer hql = new StringBuffer(" where 1=1");
		if (params.get("create_time_start") != null && (!params.get("create_time_start").equals(""))) {
			hql.append(" and createTime >= '" + params.get("create_time_start").toString() + "'");
		}
		if (params.get("create_time_end") != null && (!params.get("create_time_end").equals(""))) {
			hql.append(" and createTime <= '" + params.get("create_time_end").toString() + "'");
		}
		if (params.get("order_time_start") != null && (!params.get("order_time_start").equals(""))) {
			hql.append(" and orderTime >= '" + params.get("order_time_start").toString() + "'");
		}
		if (params.get("order_time_end") != null && (!params.get("order_time_end").equals(""))) {
			hql.append(" and orderTime <= '" + params.get("order_time_end").toString() + "'");
		}
		if (params.get("product_type") != null && (!params.get("product_type").equals(""))) {
			hql.append(" and productType = '" + params.get("product_type").toString() + "'");
		}
		if (params.get("order_code") != null && (!params.get("order_code").equals(""))) {
			hql.append(" and orderCode = '" + params.get("order_code").toString() + "'");
		}
		if (params.get("customer_no") != null && (!params.get("customer_no").equals(""))) {
			hql.append(" and customerNo = '" + params.get("customer_no").toString() + "'");
		}
		if (params.get("interface_code") != null && (!params.get("interface_code").equals(""))) {
			hql.append(" and interfaceCode = '" + params.get("interface_code").toString() + "'");
		}
		if (params.get("source") != null && (!params.get("source").equals(""))) {
			hql.append(" and source = '" + params.get("source").toString() + "'");
		}
		if (params.get("agent_no") != null && (!params.get("agent_no").equals(""))) {
			hql.append(" and (agent_no = '" + params.get("agent_no").toString() + "' " +
					"or direct_agent = '"+params.get("agent_no").toString() + "'  or indirect_agent = '" + params.get("agent_no").toString() + "' ) ");
		}
		if (params.get("amount_start") != null && (!params.get("amount_start").equals(""))) {
			hql.append(" and amount >= '" + Double.parseDouble(params.get("amount_start").toString()) + "'");
		}
		if (params.get("amount_end") != null && (!params.get("amount_end").equals(""))) {
			hql.append(" and amount <= '" + Double.parseDouble(params.get("amount_end").toString()) + "'");
		}
		if (params.get("fee_start") != null && (!params.get("fee_start").equals(""))) {
			hql.append(" and fee >= '" + Double.parseDouble(params.get("fee_start").toString()) + "'");
		}
		if (params.get("fee_end") != null && (!params.get("fee_end").equals(""))) {
			hql.append(" and fee <= '" + Double.parseDouble(params.get("fee_end").toString()) + "'");
		}
		if (params.get("channel_cost_start") != null && (!params.get("channel_cost_start").equals(""))) {
			hql.append(" and channelCost >= '" + Double.parseDouble(params.get("channel_cost_start").toString()) + "'");
		}
		if (params.get("channel_cost_end") != null && (!params.get("channel_cost_end").equals(""))) {
			hql.append(" and channelCost <= '" + Double.parseDouble(params.get("channel_cost_end").toString()) + "'");
		}
		if (params.get("customer_fee_start") != null && (!params.get("customer_fee_start").equals(""))) {
			hql.append(" and customerFee >= '" + Double.parseDouble(params.get("customer_fee_start").toString()) + "'");
		}
		if (params.get("customer_fee_end") != null && (!params.get("customer_fee_end").equals(""))) {
			hql.append(" and customerFee <= '" + Double.parseDouble(params.get("customer_fee_end").toString()) + "'");
		}
		if (params.get("agent_fee_start") != null && (!params.get("agent_fee_start").equals(""))) {
			hql.append(" and agentFee >= '" + Double.parseDouble(params.get("agent_fee_start").toString()) + "'");
		}
		if (params.get("agent_fee_end") != null && (!params.get("agent_fee_end").equals(""))) {
			hql.append(" and agentFee <= '" + Double.parseDouble(params.get("agent_fee_end").toString()) + "'");
		}
		if (params.get("Profit_type") != null && (!params.get("Profit_type").equals(""))) {
			hql.append(" and profitType = '" + params.get("Profit_type").toString() + "'");
		}
		if (params.get("profit_ratio_start") != null && (!params.get("profit_ratio_start").equals(""))) {
			hql.append(" and profitRatio >= '" + Double.parseDouble(params.get("profit_ratio_start").toString()) + "'");
		}
		if (params.get("profit_ratio_end") != null && (!params.get("profit_ratio_end").equals(""))) {
			hql.append(" and profitRatio <= '" + Double.parseDouble(params.get("profit_ratio_end").toString()) + "'");
		}
		if (params.get("agent_profit_start") != null && (!params.get("agent_profit_start").equals(""))) {
			hql.append(" and agentProfit >= '" + Double.parseDouble(params.get("agent_profit_start").toString()) + "'");
		}
		if (params.get("agent_profit_end") != null && (!params.get("agent_profit_end").equals(""))) {
			hql.append(" and agentProfit <= '" + Double.parseDouble(params.get("agent_profit_end").toString()) + "'");
		}
		if (params.get("platform_profit_start") != null && (!params.get("platform_profit_start").equals(""))) {
			hql.append(" and platformProfit >= '" + Double.parseDouble(params.get("platform_profit_start").toString())
					+ "'");
		}
		if (params.get("platform_profit_end") != null && (!params.get("platform_profit_end").equals(""))) {
			hql.append(" and platformProfit <= '" + Double.parseDouble(params.get("platform_profit_end").toString())
					+ "'");
		}
		sql += hql;
		List list = new ArrayList<>();
		try {
			list = entityDao.find(sql.toString());
		} catch (Exception e) {

		}
		Object[] s = (Object[]) list.get(0);
		Double[] sum = new Double[6];
		for (int i = 0; i < s.length; i++) {
			if (s[i] == null) {
				s[i] = 0;
			}
			sum[i] = AmountUtils.round(Double.parseDouble(s[i].toString()), 2, RoundingMode.HALF_UP);
		}
		return JsonUtils.toJsonString(sum);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@Override
	public String income(String orderTimeStart, String orderTimeEnd, String owner, int day) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse(orderTimeStart); // 开始日期
		orderTimeStart = sdf.format(DateUtils.addDays(sdf.parse(orderTimeStart), -1));
		Date over = DateUtils.addDays(date, -day); // 结束日期
		Date remit = DateUtils.addDays(date, -day); //代付
		StringBuffer hql = new StringBuffer(
				"SELECT COUNT(orderCode) as counts," + "DATE_FORMAT(createTime,'%Y%m%d') as times,"
				+ "SUM(amount) as sum_amount, productType as pay_type from ShareProfit where 1=1");
		if (owner != null && !owner.equals("")) {
			hql.append(" and customerNo in (" + owner + ")");
		}
		if (orderTimeEnd != null && !orderTimeEnd.equals("")) {
			hql.append(" and createTime >= '" + orderTimeEnd + " 00:00:00" + "'");
		}
		if (orderTimeStart != null && !orderTimeStart.equals("")) {
			hql.append(" and createTime <= '" + orderTimeStart + " 23:59:59" + "'");
		}
		hql.append("   group by productType,createTime");
		List list = new ArrayList<>();
		if(!owner.equals("0")){
			list = entityDao.find(hql.toString());
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < day; i++) {
			int count = 0;
			String times = sd.format(over);
			double amount = 0;
			String jd = "";
			for (int j = 0; j < list.size(); j++) {
				Object[] obj = (Object[]) list.get(j);
				if (obj[1].equals(sd.format(over)) && !obj[3].toString().equals("REMIT") && !obj[3].toString().equals("HOLIDAY_REMIT")) {
					count += Integer.parseInt(obj[0].toString());
					times = obj[1].toString();
					amount += Double.parseDouble(obj[2].toString());
					continue;
				}
			}
			/**
			 * 丢失精度处理
			 */
			if(amount != 0){
				DecimalFormat js = new DecimalFormat("0.00");
				js.setGroupingUsed(false);
				jd = js.format(amount);
				sb.append("{\"count\": " + count + ",");
				sb.append("\"times\": " + times + ",");
				sb.append("\"amount\": " + jd + "},");
			}else{
				sb.append("{\"count\": " + count + ",");
				sb.append("\"times\": " + times + ",");
				sb.append("\"amount\": " + amount + "},");
			}
			over = DateUtils.addDays(over, 1);
		}
		if(!sb.toString().equals("")){
			sb.deleteCharAt(sb.length() - 1);
		}
		StringBuffer df = new StringBuffer();
		for (int i = 0; i < day; i++) {
			int count = 0;
			String times = sd.format(remit);
			double amount = 0;
			String jd = "";
			for (int j = 0; j < list.size(); j++) {
				Object[] obj = (Object[]) list.get(j);
				if (obj[1].equals(sd.format(remit)) && obj[3].toString().equals("REMIT") || obj[1].equals(sd.format(remit)) && obj[3].toString().equals("HOLIDAY_REMIT")) {
					count += Integer.parseInt(obj[0].toString());
					times = obj[1].toString();
					amount += Double.parseDouble(obj[2].toString());
					continue;
				}
			}
			/**
			 * 丢失精度处理
			 */
			if(amount != 0){
				DecimalFormat js = new DecimalFormat("0.00");
				js.setGroupingUsed(false);
				jd = js.format(amount);
				df.append("{\"count\": " + count + ",");
				df.append("\"times\": " + times + ",");
				df.append("\"amount\": " + jd + "},");
			}else{
				df.append("{\"count\": " + count + ",");
				df.append("\"times\": " + times + ",");
				df.append("\"amount\": " + amount + "},");
			}
			remit = DateUtils.addDays(remit, 1);
		}
		if(!df.toString().equals("")){
			df.deleteCharAt(df.length() - 1);
		}
		String result = String.format("{\"in\": %s, \"out\": %s}", "[" + sb + "]", "[" + df + "]");
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public String incomeYear(String owner) {
		DecimalFormat js = new DecimalFormat("0.00");
		Date getDate = new Date();//获取当前日期
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		SimpleDateFormat months = new SimpleDateFormat("MM");
		int tyears = Integer.parseInt(year.format(getDate));//当前年份
		int tmonths = Integer.parseInt(months.format(getDate))+1;//当前月份
		int syears = tyears - 1; //结束年份
		int smonths = Integer.parseInt(months.format(getDate)); //结束月份
		String orderTimeStart = tyears + "-" + tmonths;
		String orderTimeEnd = syears + "-" + smonths;
		if(tmonths <= 9){
			orderTimeStart = tyears + "-0" + tmonths;
		}
		if(smonths <= 9){
			orderTimeEnd = syears + "-0" + smonths;
		}
		StringBuffer demo = new StringBuffer();
		for (int i = tmonths; i <= 12; i++) {
			if (i <= 9) {
				demo.append("{\"times\": " + syears+"0"+i + ",");
				demo.append("\"count\": " + syears+"0"+i + "count" + ",");
				demo.append("\"amount\": " + syears+"0"+i + "amount" + "},");
			}else {
				demo.append("{\"times\": " + syears+""+i + ",");
				demo.append("\"count\": " + syears+""+i + "count" + ",");
				demo.append("\"amount\": " + syears+""+i + "amount" + "},");
			}
		}
		for (int i = 1; i <= tmonths-1; i++) {
			if(i <= 9){
				demo.append("{\"times\": " + tyears+"0"+i + ",");
				demo.append("\"count\": " + tyears+"0"+i + "count" + ",");
				demo.append("\"amount\": " + tyears+"0"+i + "amount" + "},");
			}else{
				demo.append("{\"times\": " + tyears+""+i + ",");
				demo.append("\"count\": " + tyears+""+i + "count"+ ",");
				demo.append("\"amount\": " + tyears+""+i + "amount"+ "},");
			}
		}
		demo.deleteCharAt(demo.length() - 1);
		String demoin = demo.toString(); //收入
		String demoout = demo.toString(); //支出
		//统计收入     不统计代付  假日付  代收
		StringBuffer hql = new StringBuffer("SELECT DATE_FORMAT(createTime,'%Y%m') as months," 
		+ "COUNT(orderCode) as count," 
		+ "SUM(amount) as amounts from ShareProfit where productType not in ('REMIT','HOLIDAY_REMIT')");
		if (owner != null && !owner.equals("")) {
			hql.append(" and customerNo in (" + owner + ")");
		}
		if (orderTimeEnd != null && !orderTimeEnd.equals("")) {
			hql.append(" and createTime >= '" + orderTimeEnd + "-01 00:00:00" + "'");
		}
		if (orderTimeStart != null && !orderTimeStart.equals("")) {
			hql.append(" and createTime <= '" + orderTimeStart + "-01 00:00:00" + "'");
		}
		hql.append("	GROUP BY DATE_FORMAT(createTime,'%Y%m')");
		List list = new ArrayList<>();
		if(!owner.equals("0")){
			list = entityDao.find(hql.toString());
		}
		for (int j = 0; j < list.size(); j++) {
			Object[] obj = (Object[]) list.get(j);
			demoin = demoin.replace(obj[0]+"count",obj[1].toString());
			demoin = demoin.replace(obj[0]+"amount", js.format(obj[2]));
		}
		//支出统计
		StringBuffer hqlDf = new StringBuffer("SELECT DATE_FORMAT(createTime,'%Y%m') as months," 
		+ "COUNT(orderCode) as count," 
		+ "SUM(amount) as amounts from ShareProfit where productType in ('REMIT','HOLIDAY_REMIT')");
		if (owner != null && !owner.equals("")) {
			hqlDf.append(" and customerNo in (" + owner + ")");
		}
		if (orderTimeEnd != null && !orderTimeEnd.equals("")) {
			hqlDf.append(" and createTime >= '" + orderTimeEnd + "-01 00:00:00" + "'");
		}
		if (orderTimeStart != null && !orderTimeStart.equals("")) {
			hqlDf.append(" and createTime <= '" + orderTimeStart + "-01 00:00:00" + "'");
		}
		hqlDf.append("	GROUP BY DATE_FORMAT(createTime,'%Y%m')");
		List listDf = new ArrayList<>();
		if(!owner.equals("0")){
			listDf = entityDao.find(hqlDf.toString());
		}
		for (int j = 0; j < listDf.size(); j++) {
			Object[] obj = (Object[]) listDf.get(j);
			demoout = demoout.replace(obj[0]+"count",obj[1].toString());
			demoout = demoout.replace(obj[0]+"amount",js.format(obj[2]));
		}
		for (int i = tmonths; i <= 12; i++) {
			if (i <= 9){
				demoin = demoin.replace(syears+"0"+i + "count", "0");
				demoin = demoin.replace(syears+"0"+i + "amount", "0");
				demoout = demoout.replace(syears+"0"+i + "count", "0");
				demoout = demoout.replace(syears+"0"+i + "amount", "0");
			}else{
				demoin = demoin.replace(syears+""+i + "count", "0");
				demoin = demoin.replace(syears+""+i + "amount", "0");
				demoout = demoout.replace(syears+""+i + "count", "0");
				demoout = demoout.replace(syears+""+i + "amount", "0");
			}
		}
		for (int i = 1; i <= tmonths-1; i++) {
			if(i <= 9){
				demoin = demoin.replace(tyears+"0"+i + "count", "0");
				demoin = demoin.replace(tyears+"0"+i + "amount", "0");
				demoout = demoout.replace(tyears+"0"+i + "count", "0");
				demoout = demoout.replace(tyears+"0"+i + "amount", "0");
			}else{
				demoin = demoin.replace(tyears+""+i + "count", "0");
				demoin = demoin.replace(tyears+""+i + "amount", "0");
				demoout = demoout.replace(tyears+""+i + "count", "0");
				demoout = demoout.replace(tyears+""+i + "amount", "0");
			}
		}
		String result = String.format("{\"in\": %s, \"out\": %s}", "[" + demoin + "]", "[" + demoout + "]");
		return result;
	}

	@Override
	public String counts(String orderTimeStart, String orderTimeEnd, String owner) {
		DecimalFormat js = new DecimalFormat("0.00");
		js.setGroupingUsed(false); //丢失精度处理
		StringBuffer hql = new StringBuffer(
				"SELECT COUNT(orderCode) as counts,SUM(amount) as sum_amount from ShareProfit where productType not in ('REMIT','HOLIDAY_REMIT')");
		if (owner != null && !owner.equals("")) {
			hql.append(" and customerNo in (" + owner + ")");
		}
		if (orderTimeEnd != null && !orderTimeEnd.equals("")) {
			hql.append(" and createTime >= '" + orderTimeEnd + " 00:00:00" + "'");
		}
		if (orderTimeStart != null && !orderTimeStart.equals("")) {
			hql.append(" and createTime <= '" + orderTimeStart + " 23:59:59" + "'");
		}
		List list = new ArrayList<>();
		if(!owner.equals("0")){
			list = entityDao.find(hql.toString());
		}
		StringBuffer sb = new StringBuffer();
		if (list != null && list.size() > 0) {
			Object[] obj = (Object[]) list.get(0);
			if (obj[1] != null && !obj[1].equals("") && obj[1].toString() != "0") {
				sb.append("{\"counts\": " + obj[0] + ",");
				sb.append("\"sum_amount\": " + js.format(obj[1]) + "},");
			} else {
				sb.append("{\"counts\": " + 0 + ",");
				sb.append("\"sum_amount\": " + 0 + "},");
			}
			sb.deleteCharAt(sb.length() - 1);
		} else {
			sb.append("{\"counts\": " + 0 + ",");
			sb.append("\"sum_amount\": " + 0.00 + "}");
		}
		return sb.toString();
	}

	@Override
	public String orderAmountSumByPayType(String orderTimeStart, String orderTimeEnd, String owner) {
		StringBuffer hql = new StringBuffer(
				"SELECT COUNT(orderCode) as counts," + "DATE_FORMAT(createTime,'%Y%m%d') as times,"
						+ "SUM(amount) as sum_amount, productType as pay_type from ShareProfit where 1=1");
		if (owner != null && !owner.equals("")) {
			hql.append(" and customerNo in (" + owner + ")");
		}
		if (orderTimeEnd != null && !orderTimeEnd.equals("")) {
			hql.append(" and createTime >= '" + orderTimeEnd + " 00:00:00" + "'");
		}
		if (orderTimeStart != null && !orderTimeStart.equals("")) {
			hql.append(" and createTime <= '" + orderTimeStart + " 23:59:59" + "'");
		}
		hql.append("   group by productType");
		List<Object[]> list = new ArrayList<>();
		if(!owner.equals("0")){
			list = entityDao.find(hql.toString());
		}
		
		List<Map<String, Object>> retList = new ArrayList<>();
		for (ProductType productType : ProductType.values()) {
			Map<String, Object> map = new HashMap<>();
			boolean flag = true;
			map.put("paytype", productType.name());
			map.put("times", 0);
			map.put("productName", productType.getMessage());
			for (Object[] obj : list ) {
				String type = obj[3].toString();
				if (type.equals(productType.name())) {
					flag = false;
					map.put("count", obj[0]);
					map.put("amount", obj[2]);
					break;
				}
			}
			if (flag) {
				map.put("count", 0);
				map.put("amount", 0);
			}
			retList.add(map);
		}
		
		return JsonUtils.toJsonString(retList);
		
	}

	@Override
	public String orderSumNotRemit(String orderTimeStart, String orderTimeEnd, String owner) {
		StringBuffer hql = new StringBuffer("SELECT COUNT(orderCode) as COUNTS,"+
			" productType as TYPE, SUM(amount) as AMOUNT from ShareProfit where 1=1");
		if (owner != null && !owner.equals("")) {
			hql.append(" and customerNo in (" + owner + ")");
		}
		if (orderTimeEnd != null && !orderTimeEnd.equals("")) {
			hql.append(" and createTime >= '" + orderTimeEnd + " 00:00:00" + "'");
		}
		if (orderTimeStart != null && !orderTimeStart.equals("")) {
			hql.append(" and createTime <= '" + orderTimeStart + " 23:59:59" + "'");
		}
		hql.append("GROUP BY productType");
		StringBuffer sb = new StringBuffer();
		List list = new ArrayList<>();
		if(!owner.equals("0")){
			list = entityDao.find(hql.toString());
		}
		int counts = 0;
		double amount = 0;
		for (int j = 0; j < list.size(); j++) {
			Object[] obj = (Object[]) list.get(j);
			if (!obj[1].toString().equals("REMIT") && !obj[1].toString().equals("HOLIDAY_REMIT")) {
				counts += Integer.parseInt(obj[0].toString());
				amount += Double.parseDouble(obj[2].toString());
			}
		}
		sb.append("{\"counts\": " + counts + ",");
		sb.append("\"amount\": " + amount + "}");
		return sb.toString();
	}
	/**
	 * 查询交易经营分析
	 * @param ownerId
	 * @param current
	 * @param showCount
	 * @return
	 */
	public List<Map<String, Object>> findOnlineSales(String ownerId,int current,int showCount,String productType,Date startTime,Date endTime){
		//Query q = sessionFactory.getCurrentSession().createQuery("from ShareProfit where productType not in ('REMIT','HOLIDAY_REMIT')");
		DetachedCriteria dc=DetachedCriteria.forClass(ShareProfit.class);
		dc.add(Restrictions.not(Restrictions.in("productType", new ProductType[] {ProductType.REMIT,ProductType.HOLIDAY_REMIT})));
		if (StringUtils.notBlank(productType)) {
			dc.add(Restrictions.in("productType", new ProductType[] {ProductType.valueOf(productType)}));
		}
		if (startTime!=null) {
			dc.add(Restrictions.gt("orderTime", startTime));//大于
		}
		if (endTime!=null) {
			dc.add(Restrictions.lt("orderTime", endTime));//小于
		}
		if (ownerId!=null) {
			dc.add(Restrictions.eq("customerNo", ownerId));
		}
		dc.addOrder(Order.desc("id"));
		List list=entityDao.findByCriteria(dc, (current-1)*showCount, current*showCount);
		if (list!=null&&list.size()>0) {
			List<Map<String, Object>> infoMap=new ArrayList<>();
			for (Object object : list) {
				Map<String, Object> temp=JsonUtils.toJsonToObject(object,Map.class);
				infoMap.add(temp);
			}
			return infoMap;
		}
		return null;
	}
	
	public List<Map<String, Object>> sumOnlineSales(String ownerId,Date startTime, Date endTime){
		StringBuffer hql = new StringBuffer("SELECT COUNT(orderCode) as counts,SUM(amount)as sum_Amount,productType as pay_type  from ShareProfit " + 
				"where customerNo=? and productType not in ('REMIT','HOLIDAY_REMIT') ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (startTime!=null) {
			hql.append(" and orderTime>='"+sdf.format(startTime)+" 00:00:00'");
		}
		if (endTime!=null) {
			hql.append(" and orderTime<='"+sdf.format(endTime)+"  23:59:59'");
		}
		hql.append(" group by productType");
		List list = entityDao.find(hql.toString(),ownerId);

		if (list!=null&&list.size()>0) {
			List<Map<String, Object>> info=new ArrayList<>();
			for (Object object : list) {
				Object[] obj = (Object[]) object;
				Map<String, Object> temp=new HashMap<>();
				temp.put("count", obj[0]);
				temp.put("sum", String.valueOf(AmountUtils.round(Double.valueOf(String.valueOf(obj[1])), 2, RoundingMode.HALF_UP)));
				temp.put("payType", obj[2]);
				info.add(temp);
			}
			return info;
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
    public Map<String, Object> weeklySales(final String ownerId) throws ParseException {
        HashMap<String, Object> map = new HashMap<>();
        Date date = new Date(); // 开始日期
        String orderTimeStart = sdf.format(date);
        Date over = DateUtils.addDays(date, -6); // 结束日期

        String orderTimeEnd = sdf.format(DateUtils.addDays(date, -6));
        // 最近七日收款金额
        Map<String, Object> sumWeek = new HashMap<>();
        DecimalFormat js = new DecimalFormat("0.00");
        js.setGroupingUsed(false); //丢失精度处理

        StringBuffer hqlTo = new StringBuffer(
                "SELECT COUNT(id) as counts,SUM(amount) as sum_amount from ShareProfit where productType != 'REMIT' and productType != 'HOLIDAY_REMIT'");
        if (ownerId != null && !ownerId.equals("")) {
            hqlTo.append(" and customerNo in (" + ownerId + ")");
        }
        if(StringUtils.notBlank(orderTimeEnd) && StringUtils.notBlank(orderTimeStart)){
            hqlTo.append(" and createTime between '" + orderTimeEnd + " 00:00:00' and '" + orderTimeStart + " 23:59:59'");
        }else{
            if (orderTimeEnd != null && !orderTimeEnd.equals("")) {
                hqlTo.append(" and createTime >= DATE('" + orderTimeEnd + " 00:00:00" + "')");
            }
            if (orderTimeStart != null && !orderTimeStart.equals("")) {
                hqlTo.append(" and createTime <= DATE('" + orderTimeStart + " 23:59:59" + "')");
            }
        }
        List listTo = entityDao.find(hqlTo.toString());
        if (!listTo.isEmpty() && listTo.size() > 0) {
            Object[] obj = (Object[]) listTo.get(0);
            if (obj[1] != null && !obj[1].equals("") && obj[1].toString() != "0") {
                sumWeek.put("counts", obj[0]);
                sumWeek.put("amount", js.format(obj[1]));
            } else {
                sumWeek.put("counts", 0);
                sumWeek.put("amount", 0);
            }
        }

        List<Map<String, Object>> summaryGraph = new ArrayList<>();

        //统计图
        StringBuffer hql = new StringBuffer(
                "SELECT COUNT(id) as counts," + "DATE_FORMAT(createTime,'%Y%m%d') as times,"
                        + "SUM(amount) as amount from ShareProfit where productType != 'REMIT' and productType != 'HOLIDAY_REMIT'");
        if (ownerId != null && !ownerId.equals("")) {
            hql.append(" and customerNo in (" + ownerId + ")");
        }
        if(StringUtils.notBlank(orderTimeEnd) && StringUtils.notBlank(orderTimeStart)){
            hql.append(" and createTime between '" + orderTimeEnd + " 00:00:00' and '" + orderTimeStart + " 23:59:59'");
        }else{
            if (orderTimeEnd != null && !orderTimeEnd.equals("")) {
                hql.append(" and createTime >= DATE('" + orderTimeEnd + " 00:00:00" + "')");
            }
            if (orderTimeStart != null && !orderTimeStart.equals("")) {
                hql.append(" and createTime <= DATE('" + orderTimeStart + " 23:59:59" + "')");
            }
        }
        hql.append("   group by DATE_FORMAT(createTime, '%Y%m%d')");
        List list = entityDao.find(hql.toString());
        for (int i = 0; i < 7; i++) {
			map = new HashMap<>();
			int count = 0;
            String times = sd.format(over);
            double amount = 0;
            String jd = "";
            for (int j = 0; j < list.size(); j++) {
                Object[] obj = (Object[]) list.get(j);
             	if (times.equals(obj[1].toString())){
					count += Integer.parseInt(obj[0].toString());
					times = obj[1].toString();
					amount += Double.parseDouble(obj[2].toString());
				}
            }
            /**
             * 丢失精度处理
             */
            if(amount != 0){
                jd = js.format(amount);
                map.put("count", count);
                map.put("times", times);
                map.put("amount", jd);
            }else{
                map.put("count", count);
                map.put("times", times);
                map.put("amount", amount);
            }
            summaryGraph.add(map);
            over = DateUtils.addDays(over, 1);
        }

        // 最近七日统计
        List<Map<String, Object>> week = new ArrayList<>();
        StringBuffer hqlOw = new StringBuffer(
                "SELECT COUNT(id) as counts," + "DATE_FORMAT(createTime,'%Y%m%d') as times,"
                        + "SUM(amount) as sum_amount, productType as pay_type from ShareProfit where 1=1");
        if (ownerId != null && !ownerId.equals("")) {
            hqlOw.append(" and customerNo in (" + ownerId + ")");
        }
        if(StringUtils.notBlank(orderTimeEnd) && StringUtils.notBlank(orderTimeStart)){
            hqlOw.append(" and createTime between '" + orderTimeEnd + " 00:00:00' and '" + orderTimeStart + " 23:59:59'");
        }else{
            if (orderTimeEnd != null && !orderTimeEnd.equals("")) {
                hqlOw.append(" and createTime >= DATE('" + orderTimeEnd + " 00:00:00" + "')");
            }
            if (orderTimeStart != null && !orderTimeStart.equals("")) {
                hqlOw.append(" and createTime <= DATE('" + orderTimeStart + " 23:59:59" + "')");
            }
        }
        hqlOw.append("   group by productType");
        List listOw = entityDao.find(hqlOw.toString());
        for (int i = 0; i < listOw.size(); i++) {
            Object[] obj = (Object[]) listOw.get(i);
            String db = obj[3].toString();
            map = new HashMap<>();
            if(db.equals("ALIPAY")){
                map.put("count", obj[0]);
                if(!obj[2].toString().equals("0")){
                    map.put("amount", js.format(obj[2]));
                } else {
                    map.put("amount", 0);
                }
                map.put("paytype", "ALIPAY");
                map.put("paytype_COLOR", "#00BBFF");
            }
            if(db.equals("AUTHPAY")){
                map.put("count", obj[0]);
                if(!obj[2].toString().equals("0")){
                    map.put("amount", js.format(obj[2]));
                } else {
                    map.put("amount", 0);
                }
                map.put("paytype", "AUTHPAY");
                map.put("paytype_COLOR", "#5555FF");
            }
            if(db.equals("B2B")){
                map.put("count", obj[0]);
                if(!obj[2].toString().equals("0")){
                    map.put("amount", js.format(obj[2]));
                } else {
                    map.put("amount", 0);
                }
                map.put("paytype", "B2B");
                map.put("paytype_COLOR", "#66FF66");
            }
            if(db.equals("B2C")){
                map.put("count", obj[0]);
                if(!obj[2].toString().equals("0")){
                    map.put("amount", js.format(obj[2]));
                } else {
                    map.put("amount", 0);
                }
                map.put("paytype", "B2C");
                map.put("paytype_COLOR", "#FF5511");
            }
            if(db.equals("RECEIVE")){
                map.put("count", obj[0]);
                if(!obj[2].toString().equals("0")){
                    map.put("amount", js.format(obj[2]));
                } else {
                    map.put("amount", 0);
                }
                map.put("paytype", "RECEIVE");
                map.put("paytype_COLOR", "#FF0088");
            }
            if(db.equals("WXJSAPI")){
                map.put("count", obj[0]);
                if(!obj[2].toString().equals("0")){
                    map.put("amount", js.format(obj[2]));
                } else {
                    map.put("amount", 0);
                }
                map.put("paytype", "WXJSAPI");
                map.put("paytype_COLOR", "#09BB07");
            }
            if(db.equals("WXNATIVE")){
                map.put("count", obj[0]);
                if(!obj[2].toString().equals("0")){
                    map.put("amount", js.format(obj[2]));
                } else {
                    map.put("amount", 0);
                }
                map.put("paytype", "WXNATIVE");
                map.put("paytype_COLOR", "#09BB07");
            }
            if (db.equals("WXMICROPAY")) {
                map.put("count", obj[0]);
                if(!obj[2].toString().equals("0")){
                    map.put("amount", js.format(obj[2]));
                } else {
                    map.put("amount", 0);
                }
                map.put("paytype", "WXMICROPAY");
                map.put("paytype_COLOR", "#09BB07");
            }
            if (db.equals("POS")) {
                map.put("count", obj[0]);
                if(!obj[2].toString().equals("0")){
                    map.put("amount", js.format(obj[2]));
                } else {
                    map.put("amount", 0);
                }
                map.put("paytype", "POS");
                map.put("paytype_COLOR", "#666666");
            }
            if (db.equals("BINDCARD_AUTH")) {
                map.put("count", obj[0]);
                if(!obj[2].toString().equals("0")){
                    map.put("amount", js.format(obj[2]));
                } else {
                    map.put("amount", 0);
                }
                map.put("paytype", "BINDCARD_AUTH");
                map.put("paytype_COLOR", "#FF5511");
            }
            if (db.equals("MPOS")) {
                map.put("count", obj[0]);
                if(!obj[2].toString().equals("0")){
                    map.put("amount", js.format(obj[2]));
                } else {
                    map.put("amount", 0);
                }
                map.put("paytype", "MPOS");
                map.put("paytype_COLOR", "#DDFF77");
            }
            if (db.equals("QUICKPAY")) {
                map.put("count", obj[0]);
                if(!obj[2].toString().equals("0")){
                    map.put("amount", js.format(obj[2]));
                } else {
                    map.put("amount", 0);
                }
                map.put("paytype", "QUICKPAY");
                map.put("paytype_COLOR", "#615BE3");
            }
            if (db.equals("ALIPAYMICROPAY")) {
                map.put("count", obj[0]);
                if(!obj[2].toString().equals("0")){
                    map.put("amount", js.format(obj[2]));
                } else {
                    map.put("amount", 0);
                }
                map.put("paytype", "ALIPAYMICROPAY");
                map.put("paytype_COLOR", "#00BBFF");
            }
            if(map.size() > 0){
                week.add(map);
            }
        }
		week = DictUtils.listOfDict(week, "PAYTYPE_IMG", "paytype", "_IMG");
		week = DictUtils.listOfDict(week, "BF_SHARE_PAYTYPE", "paytype", "_CN");
        map = new HashMap<>();
        map.put("summaryGraph", summaryGraph);
        map.put("sumWeek", sumWeek);
        map.put("week", week);
        return map;
    }
	public Map<String, Object> weeklySales_new(String ownerId) throws ParseException {
		HashMap<String, Object> map = new HashMap<>();
		Date date = new Date(); // 开始日期
		String orderTimeStart = sdf.format(date);
		Date over = DateUtils.addDays(date, -6); // 结束日期
		
		String orderTimeEnd = sdf.format(DateUtils.addDays(date, -6));
		// 最近七日收款金额
		Map<String, Object> sumWeek = new HashMap<>();
		DecimalFormat js = new DecimalFormat("0.00");
		js.setGroupingUsed(false); //丢失精度处理
		
		StringBuffer hqlTo = new StringBuffer(
				"SELECT COUNT(id) as counts,SUM(amount) as sum_amount from ShareProfit where productType != 'REMIT' and productType != 'HOLIDAY_REMIT'");
		if (ownerId != null && !ownerId.equals("")) {
			hqlTo.append(" and customerNo in (" + ownerId + ")");
		}
		if(StringUtils.notBlank(orderTimeEnd) && StringUtils.notBlank(orderTimeStart)){
			hqlTo.append(" and createTime between '" + orderTimeEnd + " 00:00:00' and '" + orderTimeStart + " 23:59:59'");
		}else{
			if (orderTimeEnd != null && !orderTimeEnd.equals("")) {
				hqlTo.append(" and createTime >= DATE('" + orderTimeEnd + " 00:00:00" + "')");
			}
			if (orderTimeStart != null && !orderTimeStart.equals("")) {
				hqlTo.append(" and createTime <= DATE('" + orderTimeStart + " 23:59:59" + "')");
			}
		}
		List listTo = entityDao.find(hqlTo.toString());
		if (!listTo.isEmpty() && listTo.size() > 0) {
			Object[] obj = (Object[]) listTo.get(0);
			if (obj[1] != null && !obj[1].equals("") && obj[1].toString() != "0") {
				sumWeek.put("counts", obj[0]);
				sumWeek.put("amount", js.format(obj[1]));
			} else {
				sumWeek.put("counts", 0);
				sumWeek.put("amount", 0);
			}
		}
		
		List<Map<String, Object>> summaryGraph = new ArrayList<>();
		
		//统计图
		StringBuffer hql = new StringBuffer(
				"SELECT COUNT(id) as counts," + "DATE_FORMAT(createTime,'%Y%m%d') as times,"
						+ "SUM(amount) as amount from ShareProfit where productType != 'REMIT' and productType != 'HOLIDAY_REMIT'");
		if (ownerId != null && !ownerId.equals("")) {
			hql.append(" and customerNo in (" + ownerId + ")");
		}
		if(StringUtils.notBlank(orderTimeEnd) && StringUtils.notBlank(orderTimeStart)){
			hql.append(" and createTime between '" + orderTimeEnd + " 00:00:00' and '" + orderTimeStart + " 23:59:59'");
		}else{
			if (orderTimeEnd != null && !orderTimeEnd.equals("")) {
				hql.append(" and createTime >= DATE('" + orderTimeEnd + " 00:00:00" + "')");
			}
			if (orderTimeStart != null && !orderTimeStart.equals("")) {
				hql.append(" and createTime <= DATE('" + orderTimeStart + " 23:59:59" + "')");
			}
		}
		hql.append("   group by DATE_FORMAT(createTime, '%Y%m%d')");
		List list = entityDao.find(hql.toString());
		for (int i = 0; i < 7; i++) {
			map = new HashMap<>();
			int count = 0;
			String times = sd.format(over);
			double amount = 0;
			String jd = "";
			for (int j = 0; j < list.size(); j++) {
				Object[] obj = (Object[]) list.get(j);
				if (times.equals(obj[1].toString())){
					count += Integer.parseInt(obj[0].toString());
					times = obj[1].toString();
					amount += Double.parseDouble(obj[2].toString());
				}
			}
			/**
			 * 丢失精度处理
			 */
			if(amount != 0){
				jd = js.format(amount);
				map.put("count", count);
				map.put("times", times);
				map.put("amount", jd);
			}else{
				map.put("count", count);
				map.put("times", times);
				map.put("amount", amount);
			}
			summaryGraph.add(map);
			over = DateUtils.addDays(over, 1);
		}
		
		// 最近七日统计
		List<Map<String, Object>> week = new ArrayList<>();
		StringBuffer hqlOw = new StringBuffer(
				"SELECT COUNT(id) as counts," + "DATE_FORMAT(createTime,'%Y%m%d') as times,"
						+ "SUM(amount) as sum_amount, productType as pay_type from ShareProfit where 1=1");
		if (ownerId != null && !ownerId.equals("")) {
			hqlOw.append(" and customerNo in (" + ownerId + ")");
		}
		if(StringUtils.notBlank(orderTimeEnd) && StringUtils.notBlank(orderTimeStart)){
			hqlOw.append(" and createTime between '" + orderTimeEnd + " 00:00:00' and '" + orderTimeStart + " 23:59:59'");
		}else{
			if (orderTimeEnd != null && !orderTimeEnd.equals("")) {
				hqlOw.append(" and createTime >= DATE('" + orderTimeEnd + " 00:00:00" + "')");
			}
			if (orderTimeStart != null && !orderTimeStart.equals("")) {
				hqlOw.append(" and createTime <= DATE('" + orderTimeStart + " 23:59:59" + "')");
			}
		}
		hqlOw.append("   group by productType");
		List listOw = entityDao.find(hqlOw.toString());
		for (int i = 0; i < listOw.size(); i++) {
			Object[] obj = (Object[]) listOw.get(i);
			Map<String, Object> weekMap = new HashMap<>();
			weekMap.put("count", obj[0]);
			weekMap.put("amount", js.format(obj[2]));
			weekMap.put("paytype", obj[3]);
			week.add(weekMap);
		}
		week = DictUtils.listOfDict(week, "PAYTYPE_IMG", "paytype", "_IMG");
		week = DictUtils.listOfDict(week, "BF_SHARE_PAYTYPE", "paytype", "_CN");
		week = DictUtils.listOfDict(week, "PAY_TYPE_COLOR", "paytype", "_COLOR");
		map = new HashMap<>();
		map.put("summaryGraph", summaryGraph);
		map.put("sumWeek", sumWeek);
		map.put("week", week);
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> spExport(Map<String, Object> params) {
		
		StringBuffer hql = new StringBuffer("select order_Code,interface_Code,source,product_Type,customer_No,amount,customer_Fee,fee,agent_No,agent_Fee,profit_Type,profit_Ratio,"
				+ "agent_Profit,direct_Agent,direct_Agent_Fee,direct_Profit_Type,direct_Profit_Ratio,direct_Agent_Profit,indirect_Agent,indirect_Agent_Fee,indirect_Profit_Type,"
				+ "indirect_Profit_Ratio,indirect_Agent_Profit,channel_Cost,platfrom_Profit,order_Time,create_Time from Share_Profit where 1=1");
		if (params.get("create_time_start") != null && (!params.get("create_time_start").equals(""))) {
			hql.append(" and create_Time >= '" + params.get("create_time_start").toString() + "'");
		}
		if (params.get("create_time_end") != null && (!params.get("create_time_end").equals(""))) {
			hql.append(" and create_Time <= '" + params.get("create_time_end").toString() + "'");
		}
		if (params.get("order_time_start") != null && (!params.get("order_time_start").equals(""))) {
			hql.append(" and order_Time >= '" + params.get("order_time_start").toString() + "'");
		}
		if (params.get("order_time_end") != null && (!params.get("order_time_end").equals(""))) {
			hql.append(" and order_Time <= '" + params.get("order_time_end").toString() + "'");
		}
		if (params.get("product_type") != null && (!params.get("product_type").equals(""))) {
			hql.append(" and product_Type = '" + params.get("product_type").toString() + "'");
		}
		if (params.get("order_code") != null && (!params.get("order_code").equals(""))) {
			hql.append(" and order_Code = '" + params.get("order_code").toString() + "'");
		}
		if (params.get("customer_no") != null && (!params.get("customer_no").equals(""))) {
			hql.append(" and customer_No = '" + params.get("customer_no").toString() + "'");
		}
		if (params.get("interface_code") != null && (!params.get("interface_code").equals(""))) {
			hql.append(" and interface_Code = '" + params.get("interface_code").toString() + "'");
		}
		if (params.get("source") != null && (!params.get("source").equals(""))) {
			hql.append(" and source = '" + params.get("source").toString() + "'");
		}
		if (params.get("agent_no") != null && (!params.get("agent_no").equals(""))) {
			hql.append(" and (agent_no = '" + params.get("agent_no").toString() + "' or s.direct_agent = '" +
					params.get("agent_no").toString() + "'  or indirect_agent = '" + params.get("agent_no").toString() + "' ) ");
		}
		if (params.get("amount_start") != null && (!params.get("amount_start").equals(""))) {
			hql.append(" and amount >= '" + Double.parseDouble(params.get("amount_start").toString()) + "'");
		}
		if (params.get("amount_end") != null && (!params.get("amount_end").equals(""))) {
			hql.append(" and amount <= '" + Double.parseDouble(params.get("amount_end").toString()) + "'");
		}
		if (params.get("fee_start") != null && (!params.get("fee_start").equals(""))) {
			hql.append(" and fee >= '" + Double.parseDouble(params.get("fee_start").toString()) + "'");
		}
		if (params.get("fee_end") != null && (!params.get("fee_end").equals(""))) {
			hql.append(" and fee <= '" + Double.parseDouble(params.get("fee_end").toString()) + "'");
		}
		if (params.get("channel_cost_start") != null && (!params.get("channel_cost_start").equals(""))) {
			hql.append(" and channel_Cost >= '" + Double.parseDouble(params.get("channel_cost_start").toString()) + "'");
		}
		if (params.get("channel_cost_end") != null && (!params.get("channel_cost_end").equals(""))) {
			hql.append(" and channel_Cost <= '" + Double.parseDouble(params.get("channel_cost_end").toString()) + "'");
		}
		if (params.get("customer_fee_start") != null && (!params.get("customer_fee_start").equals(""))) {
			hql.append(" and customer_Fee >= '" + Double.parseDouble(params.get("customer_fee_start").toString()) + "'");
		}
		if (params.get("customer_fee_end") != null && (!params.get("customer_fee_end").equals(""))) {
			hql.append(" and customer_Fee <= '" + Double.parseDouble(params.get("customer_fee_end").toString()) + "'");
		}
		if (params.get("agent_fee_start") != null && (!params.get("agent_fee_start").equals(""))) {
			hql.append(" and agent_Fee >= '" + Double.parseDouble(params.get("agent_fee_start").toString()) + "'");
		}
		if (params.get("agent_fee_end") != null && (!params.get("agent_fee_end").equals(""))) {
			hql.append(" and agent_Fee <= '" + Double.parseDouble(params.get("agent_fee_end").toString()) + "'");
		}
		if (params.get("Profit_type") != null && (!params.get("Profit_type").equals(""))) {
			hql.append(" and profit_Type = '" + params.get("Profit_type").toString() + "'");
		}
		if (params.get("profit_ratio_start") != null && (!params.get("profit_ratio_start").equals(""))) {
			hql.append(" and profit_Ratio >= '" + Double.parseDouble(params.get("profit_ratio_start").toString()) + "'");
		}
		if (params.get("profit_ratio_end") != null && (!params.get("profit_ratio_end").equals(""))) {
			hql.append(" and profit_Ratio <= '" + Double.parseDouble(params.get("profit_ratio_end").toString()) + "'");
		}
		if (params.get("agent_profit_start") != null && (!params.get("agent_profit_start").equals(""))) {
			hql.append(" and agent_Profit >= '" + Double.parseDouble(params.get("agent_profit_start").toString()) + "'");
		}
		if (params.get("agent_profit_end") != null && (!params.get("agent_profit_end").equals(""))) {
			hql.append(" and agent_Profit <= '" + Double.parseDouble(params.get("agent_profit_end").toString()) + "'");
		}
		if (params.get("platform_profit_start") != null && (!params.get("platform_profit_start").equals(""))) {
			hql.append(" and platfrom_Profit >= '" + Double.parseDouble(params.get("platform_profit_start").toString())
					+ "'");
		}
		if (params.get("platform_profit_end") != null && (!params.get("platform_profit_end").equals(""))) {
			hql.append(" and platfrom_Profit <= '" + Double.parseDouble(params.get("platform_profit_end").toString())
					+ "'");
		}
		
		try {
			
			return entityDao.queryListBySqlQuery(hql.toString(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}