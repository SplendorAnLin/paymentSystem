package com.yl.boss.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import com.yl.boss.dao.EntityDao;
import com.yl.boss.dao.SecretKeyDao;
import com.yl.boss.entity.MccInfo;
import com.yl.boss.entity.SecretKey;
import com.yl.boss.entity.Shop;
import com.yl.boss.enums.CustomerStatus;
import com.yl.boss.enums.Status;

/**
 * 秘钥数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2017年6月13日
 * @version V1.0.0
 */
public class SecretKeyDaoImpl implements SecretKeyDao {

	@Resource
	EntityDao entityDao;
	
	@Override
	public void secretKeyAdd(SecretKey secretKey) {
		entityDao.persist(secretKey);
	}

	@Override
	public SecretKey secretKeyById(Long id) {
		String hql = "from SecretKey where id = ?";
		List<SecretKey> list = entityDao.find(hql,id);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void secretKeyUpdate(SecretKey secretKey) {
		entityDao.update(secretKey);
	}

	@Override
	public List<SecretKey> findSyncSecretKey() {
		String hql = "from SecretKey where issync=?";
		List<SecretKey> list = entityDao.find(hql,Status.FALSE);
		return list;
	}

}
