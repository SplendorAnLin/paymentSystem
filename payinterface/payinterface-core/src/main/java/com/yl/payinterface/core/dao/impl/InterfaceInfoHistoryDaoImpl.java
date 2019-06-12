package com.yl.payinterface.core.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.payinterface.core.dao.InterfaceInfoHistoryDao;
import com.yl.payinterface.core.dao.mapper.InterfaceInfoHistoryMapper;
import com.yl.payinterface.core.model.InterfaceInfoHistory;
import com.yl.payinterface.core.utils.CodeBuilder;

/**
 * 接口信息历史数据接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月17日
 * @version V1.0.0
 */
@Repository("interfaceInfoHistoryDao")
public class InterfaceInfoHistoryDaoImpl implements InterfaceInfoHistoryDao {

	@Resource
	private InterfaceInfoHistoryMapper interfaceInfoHistoryMapper;

	@Override
	public void create(InterfaceInfoHistory infoHistory) {
		infoHistory.setCode(CodeBuilder.build("PIIH", "yyyyMMdd", 6));
		interfaceInfoHistoryMapper.create(infoHistory);
	}

	@Override
	public List<InterfaceInfoHistory> findByInterfaceCode(String interfaceCode) {
		return interfaceInfoHistoryMapper.findByInterfaceCode(interfaceCode);
	}

}
