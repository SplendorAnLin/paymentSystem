package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.PosRequest;

/**
 * Title: PosRequest Dao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public interface PosRequestDao {

	//根据ID查询
	public PosRequest findById(Long id);

	//创建
	public PosRequest create(PosRequest posRequest);
	
	//更新
	public PosRequest update(PosRequest posRequest);	
}
