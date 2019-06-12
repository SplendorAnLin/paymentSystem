package com.yl.boss.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.dao.MccDao;
import com.yl.boss.entity.MccInfo;
import com.yl.boss.enums.Status;
import com.yl.boss.enums.SyncType;
import com.yl.boss.service.MccService;
import com.yl.boss.service.SyncInfoService;

/**
 * Mcc业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2017年6月13日
 * @version V1.0.0
 */
public class MccServiceImpl implements MccService {
	
	@Resource
	MccDao mccDao;
	
	@Resource
	SyncInfoService syncInfoService;

	@Override
	public void mccAdd(MccInfo mccInfo) {
		try {
			mccDao.mccAdd(mccInfo);
			
			syncInfoService.syncInfoAddFormPosp(SyncType.MCC_SYNC, JsonUtils.toJsonString(mccInfo), Status.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public MccInfo mccById(Long id) {
		return mccDao.mccById(id);
	}
	
	@Override
	public boolean mccByMcc(String mcc) {
		return mccDao.mccByMcc(mcc);
	}

	@Override
	public void mccUpdate(MccInfo mccInfo) {
		try {
			MccInfo mcc = mccDao.mccById(mccInfo.getId());
			if(mcc != null){
				mcc.setName(mccInfo.getName());
				mcc.setCategory(mccInfo.getCategory());
				mcc.setDescription(mccInfo.getDescription());
				mcc.setRate(mccInfo.getRate());
				mccDao.mccUpdate(mcc);
				
				syncInfoService.syncInfoAddFormPosp(SyncType.MCC_SYNC, JsonUtils.toJsonString(mcc), Status.FALSE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<MccInfo> findAll() {
		return mccDao.findAll();
	}

}
