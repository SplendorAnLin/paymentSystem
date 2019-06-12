package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.SecretKey;
import com.yl.boss.entity.SyncInfo;
import com.yl.boss.enums.Status;
import com.yl.boss.enums.SyncType;

public interface SyncInfoDao {
	/**
	 * 获取需要同步的信息
	 * @return
	 */
	public List<SyncInfo> getSyncInfo();
	/**
	 * 添加一条同步信息
	 * @param syncInfo
	 */
	public void syncInfoAdd(SyncInfo syncInfo);
	/**
	 * 更新同步信息
	 * @param syncInfo
	 */
	public void syncInfoUpdate(SyncInfo syncInfo);
	/**
	 * 根据ID查询同步信息
	 * @param id
	 * @return
	 */
	public SyncInfo syncInfoById(Long id);
	/**
	 * 同步pos信息
	 * @param syncType
	 * @param info
	 * @param isCreate
	 */
	public void syncInfoAddFormPosp(SyncType syncType,String info,Status isCreate);
}