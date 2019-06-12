package com.yl.online.trade.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.lefu.commons.utils.Page;
import com.yl.online.model.model.InterfacePolicy;
import com.yl.online.trade.dao.InterfacePolicyDao;
import com.yl.online.trade.dao.mapper.InterfacePolicyMapper;

/**
 * 路由模板数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
@Repository("interfacePolicyDao")
public class InterfacePolicyDaoImpl implements InterfacePolicyDao {
	
	@Resource
	private InterfacePolicyMapper interfacePolicyMapper;
	
	@Override
	public void createInterfacePolicy(InterfacePolicy interfacePolicy) {
		interfacePolicy.setVersion(System.currentTimeMillis());
		interfacePolicy.setCreateTime(new Date());
		interfacePolicyMapper.createInterfacePolicy(interfacePolicy);
	}

	@Override
	public Page queryInterfacePolicyByPage(Page page) {
		return interfacePolicyMapper.queryInterfacePolicyByPage(page);
	}

	@Override
	public InterfacePolicy queryInterfacePolicyByCode(String code) {
		return interfacePolicyMapper.queryInterfacePolicyByCode(code);
	}

	@Override
	public void updateInterfacePolicy(InterfacePolicy interfacePolicy) {
		interfacePolicy.setVersion(System.currentTimeMillis());
		interfacePolicyMapper.updateInterfacePolicy(interfacePolicy);
	}

	@Override
	public List<InterfacePolicy> queryInterfacePolicyBy(Map<String, Object> params) {
		return interfacePolicyMapper.queryInterfacePolicyBy(params);
	}

	@Override
	public List<InterfacePolicy> queryInterfacePolicyByInterfaceType(String interfaceType) {
		return interfacePolicyMapper.queryInterfacePolicyByInterfaceType(interfaceType);
	}

	@Override
	public List<InterfacePolicy> findAllInterfacePolicy(Page page,
			Map<String, Object> params) {
		return interfacePolicyMapper.findAllInterfacePolicy(page, params);
	
	}

    @Override
    public List<InterfacePolicy> queryInterfacePolicyByCodes(List<String> codes) {
        return interfacePolicyMapper.queryInterfacePolicyByCodes(codes);
    }
}
