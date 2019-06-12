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
import com.yl.cachecenter.model.Station;
import com.yl.cachecenter.service.StationService;

/**
 * 公交车站信息服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月24日
 * @version V1.0.0
 */
@Service("stationService")
public class StationServiceImpl implements StationService{

	private static final Logger logger = LoggerFactory.getLogger(StationServiceImpl.class);
	
	@Resource
	private JedisPool jedisPool;
	
	@Override
	public void add(List<Station> stations) {
		if (stations == null || stations.size() == 0) return;
		Jedis jedis = jedisPool.getResource();
		try {
			Transaction transaction = jedis.multi();
			for (Station station : stations) {
				int index = stations.indexOf(station);
				if (index > 1 && index % 1000 == 1) {
					transaction = jedis.multi();
				}
				Map<String, String> map = new HashMap<>();
				map.put("name", station.getName());
				map.put("code", station.getCode());
				map.put("endStation", station.getEndStation());
				map.put("endTime", station.getEndTime());
				map.put("flag", station.getFlag());
				map.put("routeNo", station.getRouteNo());
				map.put("startStation", station.getStartStation());
				map.put("startTime", station.getStartTime());
				map.put("stationNo", station.getStationNo());
				transaction.hmset(Constants.STATION_KEY_PREFIX + station.getCode(), map);
				for (String word : StringUtils.splitByWord(station.getName(), false)) {
					transaction.zadd(Constants.STATION_SUGGEST_KEY_PREFIX + word, Constants.STATION_KEY_SCORE, station.getCode());
				}
				
				if ((index != 0 && index % 1000 == 0) || index == stations.size() - 1) {
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
	public List<Station> suggestSearch(String name, int limit, String[] fields,
			String routeNo, String flag) {
		List<Station> result = new ArrayList<>();
		if (StringUtils.isBlank(name)){
			return result;
		}
		String[] words = StringUtils.splitByWord(name.trim(), false);
		
		for (int i = 0; i < words.length; i++) {
			words[i] = Constants.STATION_SUGGEST_KEY_PREFIX + words[i];
		}
		
		Jedis jedis = jedisPool.getResource();
		try {
			Transaction transaction = jedis.multi();
			transaction.zinterstore(Constants.STATION_SUGGEST_TEMP_KEY, words);
			transaction.zrange(Constants.STATION_SUGGEST_TEMP_KEY, 0, limit - 1);
			List<Object> execResult = transaction.exec();
			@SuppressWarnings("unchecked")
			Set<String> stationCodes = (Set<String>) execResult.get(1);
			for (String code : stationCodes) {
				Station station = new Station();
				
				if(fields == null || fields.length == 0){
					fields = new String[1];
					fields[0] = "name"; 
				}
				
				if (fields != null && fields.length > 0) {
					
					List<String> fieldValues = jedis.hmget(Constants.STATION_KEY_PREFIX + code, fields);
					
					for(String value : fieldValues){
						PropertyUtils.setSimpleProperty(station, fields[fieldValues.indexOf(value)], value);
					}
				}
				station.setCode(code);
				result.add(station);
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

}
