package com.yl.cachecenter.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.yl.cachecenter.model.Cnaps;
import com.yl.cachecenter.service.CnapsService;

/**
 * 联行号业务实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月24日
 * @version V1.0.0
 */
@Service("cnapsService")
public class CnapsServiceImpl implements CnapsService {
	private static final Logger logger = LoggerFactory.getLogger(CnapsServiceImpl.class);
	@Resource
	private JedisPool jedisPool;

	@Override
	public List<Cnaps> suggestSearch(String name, int limit, String[] fields, String providerCode, String areaCode,int clearBankLevel) {
		List<Cnaps> result = new ArrayList<>();
		if (StringUtils.isBlank(name) && StringUtils.isBlank(providerCode) && StringUtils.isBlank(areaCode) && clearBankLevel == 0) return result;
		String[] words = null;
		String[] tmp = new String[0];
		if(name != null){
			tmp = StringUtils.splitByWord(name.trim(), false);
			words = tmp;
		}
		if(StringUtils.isNotBlank(providerCode)){
			if(words == null){
				words = new String [tmp.length+1];
				if(providerCode.indexOf(".")>0){
					words[tmp.length] = Constants.CNAPS_CLEARINGBANK_KEY_PREFIX + providerCode;
				}else{
					words[tmp.length] = Constants.CNAPS_PROVIDER_KEY_PREFIX + providerCode;
				}
			}else{
				String [] tmpWords = new String [words.length+1];
				for(int idx=0;idx<words.length;idx++){
					tmpWords[idx] = words[idx];
				}
				
				if(providerCode.indexOf(".")>0){
					tmpWords[words.length] = Constants.CNAPS_CLEARINGBANK_KEY_PREFIX + providerCode;
				}else{
					tmpWords[words.length] = Constants.CNAPS_PROVIDER_KEY_PREFIX + providerCode;
				}
				words = tmpWords;
			}
		}
		if(StringUtils.isNotBlank(areaCode)){
			if(words == null){
				words = new String [tmp.length+1];
				words[tmp.length] = Constants.CNAPS_ADCODE_KEY_PREFIX + areaCode;
			}else{
				String [] tmpWords = new String [words.length+1];
				for(int idx=0;idx<words.length;idx++){
					tmpWords[idx] = words[idx];
				}
				tmpWords[words.length] = Constants.CNAPS_ADCODE_KEY_PREFIX + areaCode;
				words = tmpWords;
			}
		}
		
		if(clearBankLevel != 0){
			if(words == null){
				words = new String [tmp.length+1];
				words[tmp.length] = Constants.CNAPS_CLEARBANKLEVEL_KEY_PREFIX + clearBankLevel; 
			}else{
				String [] tmpWords = new String [words.length+1];
				for(int idx=0;idx<words.length;idx++){
					tmpWords[idx] = words[idx];
				}
				tmpWords[words.length] = Constants.CNAPS_CLEARBANKLEVEL_KEY_PREFIX + clearBankLevel;
				words = tmpWords;
			}
		}
		
		if(words == null){
			words = tmp;
		}
		for (int i = 0; i < tmp.length; i++) {
			words[i] = Constants.CNAPS_SUGGEST_KEY_PREFIX + tmp[i];
		}
		Jedis jedis = jedisPool.getResource();
		try {
			Transaction transaction = jedis.multi();
			transaction.zinterstore(Constants.CNAPS_SUGGEST_TEMP_KEY, words);
			transaction.zrange(Constants.CNAPS_SUGGEST_TEMP_KEY, 0, limit - 1);
			List<Object> execResult = transaction.exec();
			@SuppressWarnings("unchecked")
			Set<String> cnapsCodes = (Set<String>) execResult.get(1);
			for (String code : cnapsCodes) {
				Cnaps cnaps = new Cnaps();
				
				if(fields == null || fields.length == 0){
					fields = new String[1];
					fields[0] = "name"; 
				}
				
				if (fields != null && fields.length > 0) {
					String [] fieldsTmp = new String[fields.length+1];
					for(int i=0;i<fields.length;i++){
						fieldsTmp[i] = fields[i];
					}
					fieldsTmp[fields.length] = "name";
					fields = fieldsTmp;
					
					List<String> fieldValues = jedis.hmget(Constants.CNAPS_KEY_PREFIX + code, fields);
					for (String value : fieldValues) {
						if ("clearingBankLevel".equals(fields[fieldValues.indexOf(value)])) {
							PropertyUtils.setSimpleProperty(cnaps, fields[fieldValues.indexOf(value)], Integer.valueOf(value));
						} else {
							PropertyUtils.setSimpleProperty(cnaps, fields[fieldValues.indexOf(value)], value);
						}
					}
				}
				cnaps.setCode(code);
				result.add(cnaps);
			}
		} catch (JedisConnectionException e) {
			logger.error("", e);
			jedisPool.returnBrokenResource(jedis);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new RuntimeException(e);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return result;
	}

	@Override
	public void add(List<Cnaps> cnapses) {
		if (cnapses == null || cnapses.size() == 0) return;
		Jedis jedis = jedisPool.getResource();
		try {
			Transaction transaction = jedis.multi();
			for (Cnaps cnaps : cnapses) {
				int index = cnapses.indexOf(cnaps);
				if (index > 1 && index % 1000 == 1) {
					transaction = jedis.multi();
				}
				Map<String, String> map = new HashMap<>();
				map.put("name", cnaps.getName());
				map.put("clearingBankCode", cnaps.getClearingBankCode());
				map.put("clearingBankLevel", Integer.toString(cnaps.getClearingBankLevel()));
				map.put("providerCode", cnaps.getProviderCode());
				map.put("adCode", cnaps.getAdCode());
				transaction.hmset(Constants.CNAPS_KEY_PREFIX + cnaps.getCode(), map);
				for (String word : StringUtils.splitByWord(cnaps.getName(), false)) {
					transaction.zadd(Constants.CNAPS_SUGGEST_KEY_PREFIX + word, Constants.CNAPS_SUGGEST_DEFAULT_SCORE, cnaps.getCode());
				}
				transaction.zadd(Constants.CNAPS_PROVIDER_KEY_PREFIX + cnaps.getProviderCode(), Constants.CNAPS_PROVIDER_SCORE, cnaps.getCode());
				transaction.zadd(Constants.CNAPS_CLEARINGBANK_KEY_PREFIX + cnaps.getProviderCode()+ "." +cnaps.getClearingBankLevel(), Constants.CNAPS_CLEARINGBANK_SCORE, cnaps.getCode());
				
				transaction.zadd(Constants.CNAPS_CLEARBANKLEVEL_KEY_PREFIX + cnaps.getClearingBankLevel(), Constants.CNAPS_CLEARBANKLEVEL_KEY_SCORE, cnaps.getCode());
				transaction.zadd(Constants.CNAPS_ADCODE_KEY_PREFIX + cnaps.getAdCode(), Constants.CNAPS_ADCODE_KEY_SCORE, cnaps.getCode());
				if ((index != 0 && index % 1000 == 0) || index == cnapses.size() - 1) {
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

	@Override
	public Map<String, String> queryNickname() {
		Map<String, String> map = new HashMap<>();
		Jedis jedis = jedisPool.getResource();
		try {
			Set<String> nicknameSet = jedis.smembers(Constants.CNAPS_NICKNAME_KEY);
			for (String niknameStr : nicknameSet) {
				String[] nickname = niknameStr.split(",");
				map.put(nickname[0], nickname[1]);
			}
		} catch (JedisConnectionException e) {
			logger.error("", e);
			jedisPool.returnBrokenResource(jedis);
			throw e;
		} finally {
			jedisPool.returnResource(jedis);
		}
		return map;
	}

	@Override
	public void modifyNickname(Map<String, String> nicknames) {
		if (nicknames == null || nicknames.size() == 0) return;
		Jedis jedis = jedisPool.getResource();
		try {
			String[] nicknameArray = new String[nicknames.size()];
			Transaction transaction = jedis.multi();
			int index = 0;
			for (String nicknameKey : nicknames.keySet()) {
				nicknameArray[index] = nicknameKey + "," + nicknames.get(nicknameKey);
				String[] words = StringUtils.splitByWord(nicknameKey.trim(), true);
				for (int i = 0; i < words.length; i++) {
					words[i] = Constants.CNAPS_SUGGEST_KEY_PREFIX + words[i];
				}
				String[] words2 = StringUtils.splitByWord(nicknames.get(nicknameKey).trim(), true);
				for (int i = 0; i < words2.length; i++) {
					transaction.zinterstore(Constants.CNAPS_SUGGEST_KEY_PREFIX + words2[i], words);
				}
				index++;
			}
			transaction.del(Constants.CNAPS_NICKNAME_KEY);
			transaction.sadd(Constants.CNAPS_NICKNAME_KEY, nicknameArray);
			transaction.exec();
		} catch (JedisConnectionException e) {
			logger.error("", e);
			jedisPool.returnBrokenResource(jedis);
			throw e;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
}
