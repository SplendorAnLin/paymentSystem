package com.yl.boss.listener;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.boss.Constant;
import com.yl.boss.api.bean.ShareProfitBean;
import com.yl.boss.api.interfaces.ShareProfitInterface;
import com.yl.boss.service.ShareProfitService;
import com.yl.mq.consumer.client.ConsumerMessage;
import com.yl.mq.consumer.client.listener.ConsumerMessageListener;

/**
 * 分润监听
 *
 * @author AnLin
 * @since 2017年4月28日
 */
public class ShareMessageListener implements ConsumerMessageListener {
	
	private static final Logger logger = LoggerFactory.getLogger(ShareMessageListener.class);
	private ShareProfitInterface shareProfitInterface;
	private ShareProfitService shareProfitService;

	@Override
	public void consumerListener(ConsumerMessage consumerMessage) {
		ShareProfitBean shareProfitBean = JsonUtils.toObject(consumerMessage.getBody(), new TypeReference<ShareProfitBean>(){});
		logger.info("boss 接收分润信息 : {}", JsonUtils.toJsonString(shareProfitBean));
		if(shareProfitService.findByOrderCode(shareProfitBean.getOrderCode()) != null){
			logger.info("boss 分润信息处理 订单号 : {} , 该订单已处理", shareProfitBean.getOrderCode());
			return;
		}
		
		String lock = CacheUtils.get(Constant.LockPrefix1 + shareProfitBean.getOrderCode(), String.class);
		if (StringUtils.isBlank(lock)) {
			logger.info("boss 分润信息处理 订单号 : {} ,数据加锁" ,shareProfitBean.getOrderCode());
			CacheUtils.setEx(Constant.LockPrefix1 + shareProfitBean.getOrderCode(), "lock", 120);
			shareProfitInterface.createShareProfit(JsonUtils.toObject(consumerMessage.getBody(), new TypeReference<ShareProfitBean>(){}));
			CacheUtils.del(Constant.LockPrefix1 + shareProfitBean.getOrderCode());
			logger.info("boss 分润信息处理 订单号 : {} ,分润处理完成 数据已解锁" ,shareProfitBean.getOrderCode());
		}else{
			logger.info("boss 分润信息处理 订单号 : {} ,数据已被其他线程加锁" ,shareProfitBean.getOrderCode());
		}
	}

	public void setShareProfitInterface(ShareProfitInterface shareProfitInterface) {
		this.shareProfitInterface = shareProfitInterface;
	}

	public void setShareProfitService(ShareProfitService shareProfitService) {
		this.shareProfitService = shareProfitService;
	}

}
