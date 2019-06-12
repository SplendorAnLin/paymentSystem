package com.yl.pay.pos.dao;

import java.util.Date;
import java.util.List;

import com.yl.pay.pos.entity.ProxyPayInfo;


/**
 * Title: ProxyPay Dao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author sihe.li
 */
public interface ProxyPayInfoDao {

	//根据ID查询
	public ProxyPayInfo findById(Long id);

	//创建
	public ProxyPayInfo create(ProxyPayInfo proxyPayInfo);
	
	//更新
	public ProxyPayInfo update(ProxyPayInfo proxyPayInfo);
	
	//根据编号查找
	public List<ProxyPayInfo> findByOrderNo(String osrderNo);
	
	/**
	 * 查询List
	 * @param proxyPayInfo
	 * @return
	 */
	public List<ProxyPayInfo> findListByStatusAndCreateTime(Date start, Date end);
	
	
}
