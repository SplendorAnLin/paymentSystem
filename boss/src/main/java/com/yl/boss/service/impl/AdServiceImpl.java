package com.yl.boss.service.impl;

import java.util.List;

import com.yl.boss.dao.AdDao;
import com.yl.boss.entity.Ad;
import com.yl.boss.service.AdService;

/**
 * 广告管理业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class AdServiceImpl implements AdService {
	private AdDao adDao;

	public List<Object> queryByadminId(String adminId) {
		String sql = "from Ad where 1 = 1 and adminId="+adminId;
		return (List<Object>) adDao.queryByadminId(sql);
	}
	@Override
	public void save(Ad ad) {
		adDao.save(ad);
	}

	@Override
	public void update(Ad ad) {
		try {
			Ad a=queryById(ad.getId(),null);
			a.setName(ad.getName());
			a.setAdType(ad.getAdType());
			if(ad.getImageUrl() != null){
				a.setImageUrl(ad.getImageUrl());
			}
			a.setOem(ad.getOem());
			a.setImageClickUrl(ad.getImageClickUrl());
			a.setUpdateTime(ad.getUpdateTime());
			a.setStatus(ad.getStatus());
			Ad ads=a;
			adDao.update(ads);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Ad queryById(int id,String adminId) {
		try {
			String hql = "from Ad where id = "+id+"";
			return adDao.queryById(hql).get(0);
		} catch (Exception e) {
			return null;
		}
	}

	public AdDao getAdDao() {
		return adDao;
	}

	public void setAdDao(AdDao adDao) {
		this.adDao = adDao;
	}

	@Override
	public int selectCount() {
		return adDao.selectCount();
	}
	
}
