package com.yl.customer.dao;

import java.util.Date;
import java.util.List;

import com.yl.boss.api.enums.BulletinSysType;
import com.yl.customer.entity.Bulletin;
import com.yl.customer.enums.BulletinType;

/**
 * 公告数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月3日
 * @version V1.0.0
 */
public interface BulletinDao {

	public Bulletin create(Bulletin bulletin);
	public Bulletin update(Bulletin bulletin);
	public Bulletin findById(Long id);
	public int bulletinCount();


	/**根据条件查询公告
	 * @param bulletinType
	 * @param bulletinSysType
	 * @param date
	 * @return
	 */
	public List<Bulletin> findBulletinBy(BulletinType bulletinType, BulletinSysType bulletinSysType, Date date);
}
