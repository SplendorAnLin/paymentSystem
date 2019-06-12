package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.SecretKey;

/**
 * 秘钥数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2017年6月13日
 * @version V1.0.0
 */
public interface SecretKeyDao {

	/**
	 * 秘钥新增
	 * @param secretKey
	 */
	void secretKeyAdd(SecretKey secretKey);
	
	/**
	 * 根据ID查询单条信息
	 * @param id
	 * @return
	 */
	SecretKey secretKeyById(Long id);
	
	/**
	 * 秘钥修改
	 * @param secretKey
	 */
	void secretKeyUpdate(SecretKey secretKey);
	/**
	 * 查询待同步信息
	 * @return
	 */
	List<SecretKey> findSyncSecretKey();
}
