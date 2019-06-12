package com.pay.sign.dao;

import com.pay.sign.dbentity.PosRequest;

/**
 * 
 * Title: 
 * Description:   
 * Copyright: 2015年6月12日下午2:50:08
 * Company: SDJ
 * @author zhongxiang.ren
 */
public interface PosRequestDao {

	//根据ID查询
	public PosRequest findById(Long id);

	//创建
	public PosRequest create(PosRequest posRequest);
	
	//更新
	public PosRequest update(PosRequest posRequest);	
}
