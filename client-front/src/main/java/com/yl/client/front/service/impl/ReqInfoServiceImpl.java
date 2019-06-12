package com.yl.client.front.service.impl;

import java.util.Date;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.yl.client.front.model.ReqAllInfo;
import com.yl.client.front.service.ReqInfoService;

/**
 * 请求信息服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
@Service("reqInfoService")
public class ReqInfoServiceImpl implements ReqInfoService {
	
	private static Logger log = LoggerFactory.getLogger(ReqInfoServiceImpl.class);
	
	@Resource
	private MongoTemplate mogoTemplate;
	
	public void save(ReqAllInfo reqInfo){
		try {
			reqInfo.setReqDate(new Date());
			mogoTemplate.insert(reqInfo);
		} catch (Exception e) {
			log.info("请求信息记录异常:{}",e);
		}
	}
}
