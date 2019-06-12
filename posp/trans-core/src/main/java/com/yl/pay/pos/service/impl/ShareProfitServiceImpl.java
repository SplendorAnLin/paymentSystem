package com.yl.pay.pos.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yl.boss.api.bean.ShareProfitBean;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.api.interfaces.ShareProfitInterface;
import com.yl.pay.pos.entity.Order;
import com.yl.pay.pos.service.ShareProfitService;

public class ShareProfitServiceImpl implements ShareProfitService{

	private static final Log log = LogFactory.getLog(ShareProfitServiceImpl.class);
	
	private ShareProfitInterface shareProfitInterface;
	
	
	public ShareProfitInterface getShareProfitInterface() {
		return shareProfitInterface;
	}


	public void setShareProfitInterface(ShareProfitInterface shareProfitInterface) {
		this.shareProfitInterface = shareProfitInterface;
	}

	
	@Override
	public void createShareProfit(Order order) {
		// 分润
		try {
			ShareProfitBean shareProfit = new ShareProfitBean();
			shareProfit.setCustomerNo(order.getCustomerNo());
			shareProfit.setFee(order.getCustomerFee());
			shareProfit.setChannelCost(order.getBankCost());
			shareProfit.setAmount(order.getAmount());
			shareProfit.setInterfaceCode(order.getBankChannelCode());
			shareProfit.setOrderCode(order.getExternalId());
			shareProfit.setOrderTime(order.getCompleteTime());
			shareProfit.setProductType(ProductType.POS);
			shareProfit.setSource("POS");
			shareProfitInterface.createShareProfit(shareProfit);
		} catch (Exception e) {
			log.error("shareProfit failed!... [tradeOrder:{"+order.getExternalId()+"}]", e);
		}
		
	}
}
