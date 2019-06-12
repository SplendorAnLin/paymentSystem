package com.yl.boss.api.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yl.boss.api.bean.BulletinBean;
import com.yl.boss.api.enums.BulletinSysType;
import com.yl.boss.api.enums.BulletinType;
import com.yl.boss.api.utils.Page;

/**
 * 公告远程接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月19日
 * @version V1.0.0
 */
public interface BulletinInterface {
	List<BulletinBean> bulletinBySysCode(BulletinType bulletinType, BulletinSysType bulletinSysType, Date date,Page page);
	String bulletinByMap(Map<String, Object> params);
	BulletinBean findById(Long id);
	public int findBulletinCount(BulletinType bulletinType, BulletinSysType bulletinSysType, Date date);
}
