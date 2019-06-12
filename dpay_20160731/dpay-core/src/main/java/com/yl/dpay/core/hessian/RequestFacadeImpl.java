package com.yl.dpay.core.hessian;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.dpay.core.entity.Request;
import com.yl.dpay.core.service.RequestService;
import com.yl.dpay.hessian.beans.RequestBean;
import com.yl.dpay.hessian.service.RequestFacade;


@Service("requestFacade")
public class RequestFacadeImpl implements RequestFacade{
	
	@Resource
	private RequestService requestService; 
	
	@Override
	public String orderSumByDay(Date orderTimeStart, Date orderTimeEnd,String owner,int day) {
		return requestService.orderSumByDay(orderTimeStart, orderTimeEnd,owner,day);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map<String, String>> dfSuccess(Date orderTimeStart, Date orderTimeEnd, List owner) {
		return requestService.dfSuccess(orderTimeStart, orderTimeEnd, owner);
	}

	@Override
	public List<Map<String, String>> dfSuccessCount(Date orderTimeStart, Date orderTimeEnd, String owner) {
		return requestService.dfSuccessCount(orderTimeStart, orderTimeEnd, owner);
	}

	@Override
	public List<RequestBean> customerRecon(Map<String, Object> params) {
		List<Request> requests = requestService.findByCreateTime(params);
		List<RequestBean> requestBeans = new ArrayList<>();
		RequestBean requestBean = null;
		Request request = null;
		for (int i = 0; i < requests.size(); i++) {
			requestBean = new RequestBean();
			request = new Request();
			request = requests.get(i);
			requestBean.setCreateDate(request.getCreateDate());
			requestBean.setRequestNo(request.getRequestNo());
			requestBean.setFlowNo(request.getFlowNo());
			requestBean.setFee(request.getFee());
			requestBean.setAmount(request.getAmount());
			requestBean.setCompleteDate(request.getCompleteDate());
			requestBeans.add(requestBean);
		}
		return requestBeans;
	}

	@Override
	public String customerReconHead(Map<String, Object> params) {
		Map<String, Object> map = requestService.customerReconHead(params);
		return JsonUtils.toJsonString(map);
	}
}