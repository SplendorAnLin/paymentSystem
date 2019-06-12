package com.yl.boss.service;

import java.util.List;

import com.yl.boss.entity.SecretKey;
import com.yl.boss.entity.SyncInfo;
import com.yl.boss.enums.Status;
import com.yl.boss.enums.SyncType;

public interface SyncInfoService {
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
	 * 添加外部信息同步
	 */
	public void syncInfoAddFormPosp(SyncType syncType,String info,Status isCreate);
}