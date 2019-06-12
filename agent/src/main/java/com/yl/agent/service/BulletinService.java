package com.yl.agent.service;

import java.util.Date;
import java.util.List;

import com.yl.boss.api.bean.BulletinBean;
import com.yl.boss.api.enums.BulletinSysType;
import com.yl.boss.api.enums.BulletinType;
import com.yl.boss.api.utils.Page;

/**
 * 公告业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public interface BulletinService {
	public List<BulletinBean> bulletinBySysCode(BulletinType bulletinType, BulletinSysType bulletinSysType, Date date,Page page);
	public BulletinBean findById(Long id);
	/**
	 * 公告合计
	 * @param bulletinType
	 * @param bulletinSysType
	 * @param date
	 * @return
	 */
	public int findBulletinCount(BulletinType bulletinType, BulletinSysType bulletinSysType, Date date);
}
