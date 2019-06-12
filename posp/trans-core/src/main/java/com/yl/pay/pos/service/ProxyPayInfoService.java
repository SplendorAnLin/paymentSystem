package com.yl.pay.pos.service;

import java.util.Date;
import java.util.List;

import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.ProxyPayQueyrResponseBean;
import com.yl.pay.pos.entity.ProxyPayInfo;


/**
 * Title: ProxyPay Dao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author sihe.li
 */
public interface ProxyPayInfoService {

	//根据ID查询
	public ProxyPayInfo findById(Long id);

	//创建
	public ProxyPayInfo create(ProxyPayInfo proxyPayInfo);
	
	//更新
	public ProxyPayInfo update(ProxyPayInfo proxyPayInfo);
	
	//根据编号查找
	public List<ProxyPayInfo> findByOrderNo(String orderNo);
	
	/**
	 * 订单成功后D+O日代付
	 * @param extendPayBean
	 */
	public void proxyPay(ProxyPayInfo proxyPayInfo);
	
	public ProxyPayInfo complete(ProxyPayInfo proxyPayInfo);
	
	/**
	 * 代付查询
	 */
	public ProxyPayQueyrResponseBean proxyPayQuery(String orderNo);
	
	/**
	 * 组装代付信息
	 * @param extendPayBean
	 * @return
	 * @throws Exception
	 */
	public ProxyPayInfo generateProxyPayInfo(ExtendPayBean extendPayBean) throws Exception;
	
	public List<ProxyPayInfo> findListByStatusAndCreateTime(Date start, Date end);
}
