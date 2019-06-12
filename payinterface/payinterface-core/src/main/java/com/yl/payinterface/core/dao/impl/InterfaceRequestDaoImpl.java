package com.yl.payinterface.core.dao.impl;

import com.lefu.commons.utils.Page;
import com.yl.payinterface.core.dao.InterfaceRequestDao;
import com.yl.payinterface.core.dao.mapper.InterfaceRequestMapper;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.CodeBuilder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 接口请求数据处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月17日
 * @version V1.0.0
 */
@Repository("interfaceRequestDao")
public class InterfaceRequestDaoImpl implements InterfaceRequestDao {

	@Resource
	private InterfaceRequestMapper interfaceRequestMapper;

	@Override
	public void create(InterfaceRequest interfaceRequest) {
		interfaceRequest.setCode(CodeBuilder.build("PIIR", "yyyyMMdd", 6));
		interfaceRequest.setInterfaceRequestID(CodeBuilder.build("TD", "yyyyMMdd", 6));
		interfaceRequest.setCreateTime(new Date());
		interfaceRequest.setVersion(System.currentTimeMillis());
		interfaceRequestMapper.create(interfaceRequest);
	}

	@Override
	public void update(InterfaceRequest interfaceRequest, Long newVersion) {
		interfaceRequestMapper.update(interfaceRequest, newVersion);
	}

	@Override
	public InterfaceRequest findByInterfaceRequestID(String interfaceRequestID) {
		return interfaceRequestMapper.findByInterfaceRequestID(interfaceRequestID);
	}

	@Override
	public List<InterfaceRequest> queryNeedReverseOrderInterfaceRequest(int interfaceRequestCount, Page page) {
		return interfaceRequestMapper.queryNeedReverseOrderInterfaceRequest(interfaceRequestCount, page);
	}

	@Override
	public void updateQueryInterfaceRequest(InterfaceRequest queryInterfaceRequest) {
		interfaceRequestMapper.updateQueryInterfaceRequest(queryInterfaceRequest);
	}

	@Override
	public InterfaceRequest queryByBusinessOrderID(String businessOrderID) {
		return interfaceRequestMapper.queryByBusinessOrderID(businessOrderID);
	}
	
	@Override
	public List<InterfaceRequest> findAllInterfaceRequest(Page page, Map<String, Object> map){
		return interfaceRequestMapper.findAllInterfaceRequest(page, map);
	}
	
	@Override
	public Map<String, Object> queryAllSum(Map<String, Object> param) {
		return interfaceRequestMapper.queryAllSum(param);
	}

	@Override
	public List<InterfaceRequest> allByDate(Map<String, Object> param) {
		return interfaceRequestMapper.allByDate(param);
	}

	@Override
	public Map<String, Object> totalByJob(Map<String, Object> map) {
		return interfaceRequestMapper.totalByJob(map);
	}

	@Override
	public List<Map<String, Object>> queryInterfaceRequestQuery(Map<String, Object> map) {
		return interfaceRequestMapper.queryInterfaceRequestQuery(map);
	}

	@Override
	public InterfaceRequest queryOneInterfaceRequestByInterfaceCode(String code, InterfaceTradeStatus status) {
		return interfaceRequestMapper.queryOneInterfaceRequestByInterfaceCode(code, status);
	}

	@Override
	public InterfaceRequest queryByInterfaceCodeAndRequestId(String interfaceRequestID, String interfaceCode) {
		return interfaceRequestMapper.queryByInterfaceCodeAndRequestId(interfaceRequestID, interfaceCode);
	}

    @Override
    public InterfaceRequest findByInterfaceOrderId(String interfaceOrderID) {
        return interfaceRequestMapper.findByInterfaceOrderId(interfaceOrderID);
    }
}