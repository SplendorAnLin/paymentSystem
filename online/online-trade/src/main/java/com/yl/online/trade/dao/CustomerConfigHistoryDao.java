package com.yl.online.trade.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lefu.commons.utils.Page;
import com.yl.online.model.model.CustomerConfig;
import com.yl.online.model.model.CustomerConfigHistory;

/**
 * 商户交易配置历史数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
public interface CustomerConfigHistoryDao {
	
	List<CustomerConfigHistory> findAll(@Param("page")Page page, @Param("params")Map<String, Object> params);
	
	void create (CustomerConfigHistory customerConfigHistory);
}