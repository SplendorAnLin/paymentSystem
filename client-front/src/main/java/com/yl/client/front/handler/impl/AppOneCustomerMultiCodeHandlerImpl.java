package com.yl.client.front.handler.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.yl.boss.api.bean.BankCustomerBean;
import com.yl.boss.api.bean.MccInfoBean;
import com.yl.boss.api.bean.OrganizationBean;
import com.yl.boss.api.interfaces.OneCustomerMultiCodeInterface;
import com.yl.client.front.handler.AppCustomerHandler;
import com.yl.client.front.handler.AppOneCustomerMultiCodeHandler;


/**
 * App商户处理实现
 *
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
@Service("oneCustomerMultiCodeHandler")
public class AppOneCustomerMultiCodeHandlerImpl implements AppOneCustomerMultiCodeHandler {
    private static Logger log = LoggerFactory.getLogger(AppCustomerHandler.class);

    @Resource
    private OneCustomerMultiCodeInterface oneCustomerMultiCodeInterface;
    
	@Override
	public Map<String, Object> findBankCustomerByPage(Integer currentPage,Integer showCount, String organization, String mcc) {
		Page<BankCustomerBean> page = new Page<>();
		page.setCurrentPage(currentPage);
		page.setShowCount(showCount);
		
		return oneCustomerMultiCodeInterface.findBankCustomerByPage(page, organization,mcc);
	}

	@Override
	public Map<String, Object> findMcc() {
		Map<String, Object> map = new HashMap<>();
		List<MccInfoBean> list = oneCustomerMultiCodeInterface.findMcc();
		map.put("mccInfos", list);
		return map;
	}

	@Override
	public Map<String, Object> findProvince() {
		List<OrganizationBean> list = oneCustomerMultiCodeInterface.findProvince();
		Map<String, Object> map = new HashMap<>();
		map.put("provinces", list);
		return map;
	}

	@Override
	public Map<String, Object> findCityByProvince(String parentCode) {
		List<OrganizationBean> list = oneCustomerMultiCodeInterface.findCityByProvince(parentCode);
		Map<String, Object> map = new HashMap<>();
		map.put("citys", list);
		return map;
	}


	@Override
	public Map<String, Object> followBankCustomer(String customerNo, String bankCustomerNo) {
		Map<String, Object> map = new HashMap<>();
		try {
			oneCustomerMultiCodeInterface.followBankCustomer(customerNo, bankCustomerNo);
			map.put("result", "SUCCESS");
		} catch (Exception e) {
			log.error("++++++++++++关注商家失败+++++++++customerNo:"+customerNo+",bankCustomerNo:"+bankCustomerNo,e);
			map.put("result", "FAILED");
		}
		return map;	
	}

   
}