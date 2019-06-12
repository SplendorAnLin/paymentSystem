package com.yl.boss.service.impl;

import javax.annotation.Resource;
import javax.crypto.KeyGenerator;

import com.lefu.commons.utils.lang.JsonUtils;
import com.pay.common.util.DesUtil;
import com.pay.common.util.ISO8583.ISO8583Utile;
import com.yl.boss.dao.SecretKeyDao;
import com.yl.boss.entity.SecretKey;
import com.yl.boss.enums.Status;
import com.yl.boss.enums.SyncType;
import com.yl.boss.service.SecretKeyService;
import com.yl.boss.service.SyncInfoService;

/**
 * 秘钥业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2017年6月13日
 * @version V1.0.0
 */
public class SecretKeyServiceImpl implements SecretKeyService {

	@Resource
	SecretKeyDao secretKeyDao;
	
	@Resource
	SyncInfoService syncInfoService;
	
	@Override
	public void secretKeyAdd(SecretKey secretKey) {
		
		if(secretKey.getKey().length() == 16){
			try {
				SecretKey sk = new SecretKey();
				
				KeyGenerator kg=KeyGenerator.getInstance("DES");
				kg.init(56);
				String pin=ISO8583Utile.bytesToHexString(kg.generateKey().getEncoded());
				String pinKey=DesUtil.desEncrypt(pin, secretKey.getKey());
				String pinCheck=DesUtil.desEncrypt("0000000000000000",pin);
				
				sk.setKeyName(secretKey.getKeyName());
				sk.setKey(pinKey);
				sk.setCheckValue(pinCheck);
				secretKeyDao.secretKeyAdd(sk);
				
				syncInfoService.syncInfoAddFormPosp(SyncType.SECRET_KEY_SYNC, JsonUtils.toJsonString(sk), Status.TRUE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public SecretKey secretKeyById(Long id) {
		return secretKeyDao.secretKeyById(id);
	}

	@Override
	public void secretKeyUpdate(SecretKey secretKey) {
		try {
			SecretKey sk = secretKeyDao.secretKeyById(secretKey.getId());
			if(sk != null){
				
				KeyGenerator kg=KeyGenerator.getInstance("DES");
				kg.init(56);
				String pin=ISO8583Utile.bytesToHexString(kg.generateKey().getEncoded());
				String pinKey=DesUtil.desEncrypt(pin, secretKey.getKey());
				String pinCheck=DesUtil.desEncrypt( "0000000000000000",pin);
				
				sk.setKeyName(sk.getKeyName());
				sk.setKey(pinKey);
				sk.setCheckValue(pinCheck);
				
				secretKeyDao.secretKeyUpdate(sk);
				
				syncInfoService.syncInfoAddFormPosp(SyncType.SECRET_KEY_SYNC, JsonUtils.toJsonString(sk), Status.FALSE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
