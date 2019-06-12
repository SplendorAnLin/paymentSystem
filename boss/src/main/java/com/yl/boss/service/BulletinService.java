package com.yl.boss.service;

import java.util.Date;
import java.util.List;

import com.yl.boss.api.enums.BulletinSysType;
import com.yl.boss.api.enums.BulletinType;
import com.yl.boss.api.utils.Page;
import com.yl.boss.entity.Bulletin;

/**
 * 公告信息业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface BulletinService {
	
	/**
	 * @param bulletin
	 * @return
	 */
	public Bulletin create(Bulletin bulletin);
	/**
	 * @param bulletin
	 */
	public void update(Bulletin bulletin);
	/**
	 * @param id
	 * @return
	 */
	public Bulletin findById(Long id);
	/**
	 * 公告合计
	 * @param bulletinType
	 * @param bulletinSysType
	 * @param date
	 * @return
	 */
	public int findBulletinCount(BulletinType bulletinType, BulletinSysType bulletinSysType, Date date);
	/**根据条件查询公告
	 * @param bulletinType
	 * @param bulletinSysType
	 * @param date
	 * @return
	 */
	public List<Bulletin> findBulletinBy(BulletinType bulletinType, BulletinSysType bulletinSysType, Date date,Page page);
}
