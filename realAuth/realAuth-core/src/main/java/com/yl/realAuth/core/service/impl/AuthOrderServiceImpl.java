package com.yl.realAuth.core.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.DateFormatUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.realAuth.core.dao.AuthOrderDao;
import com.yl.realAuth.core.service.AuthOrderService;
import com.yl.realAuth.hessian.bean.AuthOrderQueryBean;
import com.yl.realAuth.hessian.utils.CommonUtils;
import com.yl.realAuth.model.RealNameAuthOrder;

@Service("authOrderService")
public class AuthOrderServiceImpl implements AuthOrderService {
	private static final Logger logger = LoggerFactory.getLogger(AuthOrderServiceImpl.class);
	@Resource
	private AuthOrderDao authOrderDao;

	@Override
	public RealNameAuthOrder queryByRequestCode(String customerNo, String requestCode) {
		return authOrderDao.queryOrderByRequestCode(customerNo, requestCode);
	}

	@Override
	public void insertOrder(RealNameAuthOrder order) {
		authOrderDao.insertAuthOrder(order);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page authOrderQuery(Page page, AuthOrderQueryBean authOrderQueryBean) {
		Map<String, Object> params = initParams(authOrderQueryBean);
		List<RealNameAuthOrder> authOrder = authOrderDao.authOrderQuery(page, params);
		page.setObject(JsonUtils.toJsonString(authOrder));
		return page;
	}

	@Override
	public String authOrderTotal(AuthOrderQueryBean authOrderQueryBean) {
		String resultStr = "";
		Map<String, Object> params = initParams(authOrderQueryBean);
		Map<String, Object> authOrders = authOrderDao.authOrderTotal(params);
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("result", authOrders.get("result"));
			result.put("count", authOrders.get("count"));
			resultStr = JsonUtils.toJsonString(result);
			return resultStr;
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return resultStr;
	}

	@Override
	public String authOrderExport(AuthOrderQueryBean authOrderQueryBean) {
		Map<String, Object> params = initParams(authOrderQueryBean);
		List<RealNameAuthOrder> authOrder = authOrderDao.authOrderExport(params);
		return JsonUtils.toJsonString(authOrder);
	}

	public Map<String, Object> initParams(AuthOrderQueryBean authOrderQueryBean) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("orderCode", authOrderQueryBean.getOrderCode());
		params.put("busiType", authOrderQueryBean.getBusiType());
		params.put("customerNo", authOrderQueryBean.getCustomerNo());
		params.put("requestCode", authOrderQueryBean.getRequestCode());
		params.put("payerName", authOrderQueryBean.getPayerName());
		if (StringUtils.isNotBlank(authOrderQueryBean.getCertNo())) {
			String certNo = CommonUtils.replaceWithStar(authOrderQueryBean.getCertNo(), 6, 14);
			params.put("certNo", certNo);
		}
		if (StringUtils.isNotBlank(authOrderQueryBean.getBankCardNo())) {
			String bankCardNo = authOrderQueryBean.getBankCardNo();
			bankCardNo = CommonUtils.replaceWithStar(bankCardNo, 4, bankCardNo.length() - 4);
			params.put("bankCardNo", bankCardNo);
		}
		params.put("payerMobNo", authOrderQueryBean.getPayerMobNo());
		params.put("authOrderStatus", authOrderQueryBean.getAuthOrderStatus());
		params.put("authResult", authOrderQueryBean.getAuthResult());
		params.put("createStartTime", dateUtil(authOrderQueryBean.getCreateStartTime(), "start"));
		params.put("createEndTime", dateUtil(authOrderQueryBean.getCreateEndTime(), "end"));
		params.put("completeStartTime", dateUtil(authOrderQueryBean.getCompleteStartTime(), "start"));
		params.put("completeEndTime", dateUtil(authOrderQueryBean.getCompleteEndTime(), "end"));
		params.put("bankCode", authOrderQueryBean.getBankCode());
		// params.put("cardType", authOrderQueryBean.getCardType());
		return params;
	}

	public Date dateUtil(String date, String flag) {
		Date format = null;
		if (StringUtils.isBlank(date)) {
			return null;
		}
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			StringBuilder orderCreateStartTime = new StringBuilder(date);
			if ("start".equals(flag)) {
				format = dateFormat.parse(orderCreateStartTime.append(" 00:00:00").toString());
			} else {
				format = dateFormat.parse(orderCreateStartTime.append(" 23:59:59").toString());
			}
		} catch (ParseException e) {
			logger.error("", e);
		}
		return format;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<RealNameAuthOrder> queryWaitCloseOrderBy(Page page, Date dBefore, Date dqBefore) {
		return authOrderDao.queryWaitCloseOrder(page, dBefore, dqBefore);
	}

	@Override
	public void closeTimeoutOrder(RealNameAuthOrder RealNameAuthOrder) {
		authOrderDao.closeTimeoutOrder(RealNameAuthOrder);
	}

	@Override
	public RealNameAuthOrder queryAuthOrderByCode(String orderCode) {
		return authOrderDao.queryAuthOrderByCode(orderCode);
	}

	@Override
	public void modifyOrderStatus(RealNameAuthOrder RealNameAuthOrder) {
		authOrderDao.updateOrderStatus(RealNameAuthOrder);
	}

	@Override
	public List<RealNameAuthOrder> queryWaitOrProcessOrderBy(Page page, Date dBefore, Date dqBefore) {
		return authOrderDao.queryWaitProcessOrder(page, dBefore, dqBefore);
	}

	@Override
	public List<RealNameAuthOrder> querySuccessOrderBy(Date dBefore) {
		String dateStr = DateFormatUtils.format(dBefore, "yyyy-MM-dd");
		Date startOrderTime = new Date();
		Date endOrderTime = new Date();
		try {
			startOrderTime = DateUtils.parseDate(dateStr + " 00:00:00.000", "yyyy-MM-dd hh:mm:ss.SSS");
			endOrderTime = DateUtils.parseDate(dateStr + " 23:59:59.999", "yyyy-MM-dd hh:mm:ss.SSS");
		} catch (ParseException e) {
		}
		return authOrderDao.findSuccessOrderBy(startOrderTime, endOrderTime);

	}

	@Override
	public RealNameAuthOrder findRequestBy(String partnerCode, String outOrderId) {

		return authOrderDao.findRequestBy(partnerCode, outOrderId);
	}

}
