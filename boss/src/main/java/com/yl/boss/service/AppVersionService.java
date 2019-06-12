package com.yl.boss.service;

import java.util.List;

import com.yl.boss.entity.AppVersion;
import com.yl.boss.entity.AppVersionHistory;

/**
 * 商户密钥服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface AppVersionService {

	/**
	 * 创建商户密钥
	 * @param appVersion
	 * @param oper
	 */
	public void create(AppVersion appVersion, String oper);
	
	/**
	 * 更新商户密钥
	 * @param appVersion
	 * @param oper
	 */
	public void update(AppVersion appVersion, String oper);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public AppVersion findById(long id);
	/**
	 * 检测是否存在
	 * @param type
	 * @param agentNo
	 * @return
	 */
	public boolean exist(String type,String agentNo);
	public AppVersion findByType(String type,String oem);
	/**
	 * 根据服务商号查询历史
	 * @param agentNo
	 * @return List<AppVersionHistory>
	 */
	public List<AppVersionHistory> findHistByAgentNo(String agentNo);
	
}
