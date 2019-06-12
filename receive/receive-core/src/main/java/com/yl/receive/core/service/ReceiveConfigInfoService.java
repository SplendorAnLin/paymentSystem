package com.yl.receive.core.service;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.receive.core.entity.ReceiveConfigInfo;
import com.yl.receive.core.entity.ReceiveConfigInfoHistory;

/**
 * 代收配置服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2017年1月4日
 * @version V1.0.0
 */
public interface ReceiveConfigInfoService {

	/**
	 * 更新代收密钥
	 * @param customerNo
	 * @param privateKey
	 * @param publicKey
	 */
	public void updateKeys(String customerNo,String privateKey,String publicKey);
	
	/**
	 * 
	 * @Description 查询代收配置
	 * @param ownerId
	 * @return
	 * @date 2017年1月4日
	 */
	public ReceiveConfigInfo queryBy(String ownerId);
	/**
	 * 分页查询代收配置
	 * @param queryParams 查询参数
	 * @param page 分页实体
	 * @return	ReceiveConfigInfo 代收配置信息
	 */
	public List<ReceiveConfigInfo> findAllRecfByPage(Map<String, Object> queryParams, Page<?> page);
	
	/**
	 * 添加代收配置
	 * @param receiveConfigInfo
	 */
	public void insert(ReceiveConfigInfo receiveConfigInfo);
	
	/**
	 * 更新代收配置
	 */
	public void update(ReceiveConfigInfo receiveConfigInfo);
	/**
	 * 添加代收历史信息
	 * @param receiveConfigInfoHistory
	 */
	public void insertHistory(ReceiveConfigInfoHistory receiveConfigInfoHistory);
}
