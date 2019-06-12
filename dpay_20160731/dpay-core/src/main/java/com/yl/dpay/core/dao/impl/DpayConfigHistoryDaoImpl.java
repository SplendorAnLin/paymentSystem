package com.yl.dpay.core.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.dpay.core.dao.DpayConfigHistoryDao;
import com.yl.dpay.core.entity.DpayConfigHistory;
import com.yl.dpay.core.mybatis.mapper.DpayConfigHistoryMapper;

/**
 * 代付配置历史记录数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
@Repository("dpayConfigHistoryDao")
public class DpayConfigHistoryDaoImpl implements DpayConfigHistoryDao {
	
	@Resource
	private DpayConfigHistoryMapper dpayConfigHistoryMapper;

	@Override
	public void insert(DpayConfigHistory dpayConfigHistory) {
		dpayConfigHistoryMapper.insert(dpayConfigHistory);
	}

}
