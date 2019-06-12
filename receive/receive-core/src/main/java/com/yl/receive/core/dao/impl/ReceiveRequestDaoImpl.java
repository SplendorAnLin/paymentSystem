package com.yl.receive.core.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.interfaces.ShareProfitInterface;
import com.yl.mq.producer.client.ProducerClient;
import com.yl.mq.producer.client.ProducerMessage;
import com.yl.receive.core.contant.Contant;
import com.yl.receive.core.dao.ReceiveRequestDao;
import com.yl.receive.core.entity.ReceiveRequest;
import com.yl.receive.core.mybatis.mapper.ReceiveRequestMapper;
import com.yl.receive.core.utils.CodeBuilder;

/**
 * 代收请求数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
@Repository("receiveRequestDao")
public class ReceiveRequestDaoImpl implements ReceiveRequestDao {
	private static final Logger logger = LoggerFactory.getLogger(ReceiveRequestDaoImpl.class);
	@Resource
	private ReceiveRequestMapper receiveRequestMapper;

	@Resource
	private ProducerClient producerClient;
	
	@Resource
	private ShareProfitInterface shareProfitInterface;
	@Override
	public List<Map<String, String>> orderSumByDay(String orderTimeStart, String orderTimeEnd, String owner) {
		return receiveRequestMapper.orderSumByDay(orderTimeStart, orderTimeEnd, owner);
	}

	@Override
	public void update(ReceiveRequest receiveRequest, Long newVersion) {
		receiveRequest.setLastUpdateTime(new Date());
		receiveRequestMapper.update(receiveRequest, newVersion);
		try {
			Map<String, Object> share=shareProfitInterface.findByOrderCode(receiveRequest.getReceiveId());
			ReceiveRequest request=receiveRequestMapper.queryByReceiveId(receiveRequest.getReceiveId());
			SimpleDateFormat df = new SimpleDateFormat("YYYY-mm-dd HH:mm:ss");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ownerId", request.getOwnerId());
			map.put("payerBankNo", request.getPayerBankNo());
			map.put("ownerRole", request.getOwnerRole());
			map.put("contractId", request.getContractId());
			map.put("receiveId", request.getReceiveId());
			map.put("batchNo", request.getBatchNo());
			map.put("seqId", request.getSeqId());
			map.put("accountNo", request.getAccountNo());
			map.put("accountNoEncrpty", request.getAccountNoEncrpty());
			map.put("accountName", request.getAccountName());
			map.put("accType", request.getAccType());
			map.put("accNoType", request.getAccNoType());
			map.put("amount", request.getAmount());
			map.put("fee", request.getFee());
			map.put("notifyUrl", request.getNotifyUrl());
			map.put("remark", request.getRemark());
			map.put("sourceType", request.getSourceType());
			if (request.getCreateTime() != null) {
				map.put("createTime", df.format(request.getCreateTime()));
			}
			if (request.getLastUpdateTime() != null) {
				map.put("lastUpdateTime", df.format(request.getLastUpdateTime()));
			}
			
			map.put("platformProfit", share.get("platformProfit"));
			map.put("auditStatus", request.getAuditStatus());
			map.put("applyStatus", request.getApplyStatus());
			map.put("version", request.getVersion());
			map.put("openBank", request.getOpenBank());
			map.put("ownerName", request.getOwnerName());
			map.put("province", request.getProvince());
			map.put("city", request.getCity());
			map.put("certificatesType", request.getCertificatesType());
			map.put("certificatesCode", request.getCertificatesCode());
			map.put("certificatesCodeEncrpty", request.getCertificatesCodeEncrpty());
			map.put("cost", request.getCost());
			map.put("orderStatus", request.getOrderStatus());
			map.put("interfaceRequestId", request.getInterfaceRequestId());
			map.put("phone", request.getPhone());
			producerClient.sendMessage(new ProducerMessage(Contant.DATA_TOPIC, Contant.DATA_TAG,
					JsonUtils.toJsonString(map).getBytes()));
		} catch (Exception e) {
			logger.error("推送代收请求保存数据到数据中心失败，错误原因{},错误代收单号{}", e, receiveRequest.getReceiveId());
		}
	}

	@Override
	public ReceiveRequest queryByReceiveId(String receiveId) {
		return receiveRequestMapper.queryByReceiveId(receiveId);
	}

	@Override
	public ReceiveRequest queryBySeqId(String seqId, String ownerId) {
		return receiveRequestMapper.queryBySeqId(seqId, ownerId);
	}

	@Override
	public void save(ReceiveRequest receiveRequest) {
		receiveRequest.setReceiveId(CodeBuilder.build("RECEIVE", "yyMMdd"));
		receiveRequest.setVersion(System.currentTimeMillis());
		receiveRequest.setCreateTime(new Date());
		receiveRequestMapper.save(receiveRequest);
		try {
			ReceiveRequest request=receiveRequestMapper.queryByReceiveId(receiveRequest.getReceiveId());
			SimpleDateFormat df = new SimpleDateFormat("YYYY-mm-dd HH:mm:ss");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ownerId", request.getOwnerId());
			map.put("payerBankNo", request.getPayerBankNo());
			map.put("ownerRole", request.getOwnerRole());
			map.put("contractId", request.getContractId());
			map.put("receiveId", request.getReceiveId());
			map.put("batchNo", request.getBatchNo());
			map.put("seqId", request.getSeqId());
			map.put("accountNo", request.getAccountNo());
			map.put("accountNoEncrpty", request.getAccountNoEncrpty());
			map.put("accountName", request.getAccountName());
			map.put("accType", request.getAccType());
			map.put("accNoType", request.getAccNoType());
			map.put("amount", request.getAmount());
			map.put("fee", request.getFee());
			map.put("notifyUrl", request.getNotifyUrl());
			map.put("remark", request.getRemark());
			map.put("sourceType", request.getSourceType());
			if (request.getCreateTime() != null) {
				map.put("createTime", df.format(request.getCreateTime()));
			}
			if (request.getLastUpdateTime() != null) {
				map.put("lastUpdateTime", df.format(request.getLastUpdateTime()));
			}
			map.put("auditStatus", request.getAuditStatus());
			map.put("applyStatus", request.getApplyStatus());
			map.put("version", request.getVersion());
			map.put("openBank", request.getOpenBank());
			map.put("ownerName", request.getOwnerName());
			map.put("province", request.getProvince());
			map.put("city", request.getCity());
			map.put("certificatesType", request.getCertificatesType());
			map.put("certificatesCode", request.getCertificatesCode());
			map.put("certificatesCodeEncrpty", request.getCertificatesCodeEncrpty());
			map.put("cost", request.getCost());
			map.put("orderStatus", request.getOrderStatus());
			map.put("interfaceRequestId", request.getInterfaceRequestId());
			map.put("phone", request.getPhone());
			producerClient.sendMessage(new ProducerMessage("yl-data-topic", "receive_receiverequest_tag",
					JsonUtils.toJsonString(map).getBytes()));
		} catch (Exception e) {
			logger.error("推送代收请求保存数据到数据中心失败，错误原因{},错误代收单号{}", e, receiveRequest.getReceiveId());
		}
	}

	@Override
	public List<Map<String, String>> orderSum(String orderTimeStart, String orderTimeEnd, String owner) {
		return receiveRequestMapper.orderSum(orderTimeStart, orderTimeEnd, owner);
	}

	@Override
	public List<Map<String, String>> orderSumCount(String orderTimeStart, String orderTimeEnd, String owner) {
		return receiveRequestMapper.orderSumCount(orderTimeStart, orderTimeEnd, owner);
	}


	@Override
	public List<ReceiveRequest> findByReconJob(Map<String, Object> params) {
		return receiveRequestMapper.findByReconJob(params);
	}

	@Override
	public Map<String, Object> findTotalByJob(Map<String, Object> params) {
		return receiveRequestMapper.findTotalByJob(params);
	}


	@Override
	public List<ReceiveRequest> customerRecon(Map<String, Object> params) {
		return receiveRequestMapper.customerRecon(params);
	}

	@Override
	public Map<String, Object> customerReconHead(Map<String, Object> params) {
		return receiveRequestMapper.customerReconHead(params);
	}

	@Override
	public Map<String, Object> customerReconSum(Map<String, Object> params) {
		return receiveRequestMapper.customerReconSum(params);
	}

}