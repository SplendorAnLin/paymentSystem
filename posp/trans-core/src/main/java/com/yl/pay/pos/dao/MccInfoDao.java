package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.MccInfo;


/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public interface MccInfoDao {
	
	/**
	 * @param mccInfo
	 */
	public MccInfo create(MccInfo mccInfo);
	
	/**
	 * 根据MCC编号查找
	 */
	public MccInfo findByMcc(String mcc);
	/**
	 * 更新
	 * @param mccInfo
	 * @return
	 */
	public MccInfo update(MccInfo mccInfo);
}
