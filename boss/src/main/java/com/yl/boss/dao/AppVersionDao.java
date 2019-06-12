package com.yl.boss.dao;

import com.yl.boss.entity.AppVersion;

/**
 * oem版客户端数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public interface AppVersionDao {
	/**
	 * 创建oemAPP版本
	 * @param appVersion
	 */
	public void create(AppVersion appVersion);
	/**
	 * 根据类型查找oem版本
	 */
	public AppVersion findByType(String type,String oem);
	/**
	 * 更新oemAPP版本
	 * @param appVersion
	 */
	public void update(AppVersion appVersion);
	/**
	 * 检测是否存在
	 * @param type
	 * @param agentNo
	 * @return
	 */
	public boolean exist(String type,String agentNo);
	/**
	 * 查询oemAPP版本
	 * @param id
	 * @return
	 */
	public AppVersion findById(Long id);

}
