package com.yl.dpay.core.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.dpay.core.dao.DpayConfigDao;
import com.yl.dpay.core.entity.DpayConfig;
import com.yl.dpay.core.mybatis.mapper.DpayConfigMapper;

/**
 * 代付配置数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
@Repository("dpayConfigDao")
public class DpayConfigDaoImpl implements DpayConfigDao {
	
	@Resource
	private DpayConfigMapper dpayConfigMapper;

	@Override
	public void insert(DpayConfig t) {
		dpayConfigMapper.insert(t);
	}

	@Override
	public DpayConfig findById(Long id) {
		return dpayConfigMapper.findById(id);
	}

	@Override
	public void update(DpayConfig t) {
		dpayConfigMapper.update(t);
	}

	@Override
	public DpayConfig findDpayConfig() {
		return dpayConfigMapper.findDpayConfig();
	}

}
