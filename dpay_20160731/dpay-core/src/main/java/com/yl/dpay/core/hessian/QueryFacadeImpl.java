package com.yl.dpay.core.hessian;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yl.dpay.core.entity.Request;
import com.yl.dpay.core.service.RequestService;
import com.yl.dpay.core.valuelist.ValueListAction;
import com.yl.dpay.hessian.beans.RequestBean;
import com.yl.dpay.hessian.service.QueryFacade;

import net.mlw.vlh.ValueList;

/**
 * 代付查询远程接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月17日
 * @version V1.0.0
 */
@Service("queryFacade")
public class QueryFacadeImpl implements QueryFacade {

	@Autowired
	private ValueListAction valueListAction;
	@Resource
	private RequestService requestService;


	@Override
	public Map<String, Object> history(String queryId, Map<String, Object> params) {
		ValueList vl = valueListAction.execute(queryId, params).get(queryId);
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put(QueryFacade.VALUELIST_INFO, vl.getValueListInfo());
		map.put(QueryFacade.VALUELIST, vl.getList());
		return map;
	}
	
	@Override
	public Map<String, Object> query(String queryId, Map<String, Object> params) {
		ValueList vl = valueListAction.execute(queryId, params).get(queryId);
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put(QueryFacade.VALUELIST_INFO, vl.getValueListInfo());
		map.put(QueryFacade.VALUELIST, vl.getList());
		return map;
	}

	@Override
	public RequestBean findByFlowNo(String flowNo) {
		Request request = requestService.findByFlowNo(flowNo);
		if(request == null){
			return null;
		}
		RequestBean requestBean = new RequestBean();
		requestBean.setAccountName(request.getAccountName());
		requestBean.setAccountNo(request.getAccountNo());
		requestBean.setAccountType(String.valueOf(request.getAccountType()));
		requestBean.setAmount(request.getAmount());
		requestBean.setAuditDate(request.getAuditDate());
		requestBean.setAuditStatus(String.valueOf(request.getAuditStatus()));
		requestBean.setBankCode(request.getBankCode());
		requestBean.setCardType(String.valueOf(request.getCardType()));
		requestBean.setCerNo(request.getCerNo());
		requestBean.setCerType(String.valueOf(request.getCerType()));
		requestBean.setCompleteDate(request.getCompleteDate());
		requestBean.setCompleteMsg(request.getCompleteMsg());
		requestBean.setCvv(request.getCvv());
		requestBean.setDescription(request.getDescription());
		requestBean.setFlowNo(flowNo);
		requestBean.setOperator(request.getOperator());
		requestBean.setOwnerId(request.getOwnerId());
		requestBean.setRequestNo(request.getRequestNo());
		requestBean.setRequestType(String.valueOf(request.getRequestType()));
		requestBean.setStatus(String.valueOf(request.getStatus()));
		requestBean.setValidity(request.getValidity());
		return requestBean;
	}
}
