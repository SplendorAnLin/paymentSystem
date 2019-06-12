package com.yl.cachecenter.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.lefu.commons.utils.lang.StringUtils;
import com.yl.cachecenter.Constants;
import com.yl.cachecenter.model.IIN;
import com.yl.cachecenter.service.IINService;

/**
 * 发行者识别号码业务实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月24日
 * @version V1.0.0
 */
@Service("iINService")
public class IINServiceImpl implements IINService {
	private static final Logger logger = LoggerFactory.getLogger(IINServiceImpl.class);
	@Resource
	private JedisPool jedisPool;

	@SuppressWarnings("unchecked")
	@Override
	public IIN recognition(String cardNo, String[] fields) {
		logger.info("cardNo:{},fields[{}]",cardNo,fields);
		if(cardNo.length() < 6){
			return new IIN();
		}
		IIN iin = null;
		Jedis jedis = jedisPool.getResource();
		try {
			// 先按6位长度命中
			String iinLength6 = cardNo.substring(0, 6);
			if (!Constants.IIN_EXCLUDE_LENGTH6.contains(iinLength6)) {
				List<String> fieldValues = jedis.hmget(StringUtils.concatToSB(Constants.IIN_KEY_PREFIX, iinLength6, ".", "6", ".", String.valueOf(cardNo.length()))
						.toString(), fields);
				iin = buildIIN(fieldValues, fields);
			}
			if(iin != null){
				if (StringUtils.notBlank(iin.getProviderCode())) return iin;
			}
			// 6位未命中的从10位开始到3位一次取出来
			Transaction transaction = jedis.multi();
			for (int i = 10; i >= 3; i--) {
				if(cardNo.length() < i)break;
				transaction.hmget(
						StringUtils.concatToSB(Constants.IIN_KEY_PREFIX, cardNo.substring(0, i), ".", String.valueOf(i), ".", String.valueOf(cardNo.length()))
								.toString(), fields);
			}
			List<Object> execResult = transaction.exec();
			for (Object object : execResult) {
				iin = buildIIN((List<String>) object, fields);
				if (StringUtils.notBlank(iin.getProviderCode())) {
					break;
				}
			}
			logger.info("iin:{}",iin);
			return iin;
		} catch (JedisConnectionException e) {
			logger.error("连接redis异常:{}", e);
			jedisPool.returnBrokenResource(jedis);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new RuntimeException(e);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	private IIN buildIIN(List<String> fieldValues, String[] fields) throws Exception {
		IIN iin = new IIN();
		if (fieldValues != null && fieldValues.size() > 0) {
			for (String value : fieldValues) {
				if (value == null) {
					continue;
				}
				String fidledName = fields[fieldValues.indexOf(value)];
				if ("length".equals(fidledName) || "panLength".equals(fidledName)) {
					PropertyUtils.setSimpleProperty(iin, fidledName, Integer.parseInt(value));
				} else {
					PropertyUtils.setSimpleProperty(iin, fidledName, value);
				}
			}
		}
		return iin;
	}

	@Override
	public void add(List<IIN> iins) {
		if (iins == null || iins.size() == 0) return;
		Jedis jedis = jedisPool.getResource();
		try {
			Transaction transaction = jedis.multi();
			for (IIN iin : iins) {
				int index = iins.indexOf(iin);
				if (index > 1 && index % 1000 == 1) {
					transaction = jedis.multi();
				}
				Map<String, String> map = new HashMap<>();
				map.put("code", iin.getCode());
				map.put("length", Integer.toString(iin.getLength()));
				map.put("panLength", Integer.toString(iin.getPanLength()));
				map.put("providerCode", iin.getProviderCode());
				map.put("cardType", iin.getCardType());
				map.put("cardName", iin.getCardName());
				map.put("agencyCode", iin.getAgencyCode());
				map.put("agencyName", iin.getAgencyName());
				transaction.hmset(Constants.IIN_KEY_PREFIX + iin.getCode() + "." + iin.getLength() + "." + iin.getPanLength(), map);
				if ((index != 0 && index % 1000 == 0) || index == iins.size() - 1) {
					transaction.exec();
				}
			}
		} catch (JedisConnectionException e) {
			logger.error("", e);
			jedisPool.returnBrokenResource(jedis);
			throw e;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

}