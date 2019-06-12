/**
 * 
 */
package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.LngAndLat;


/**
 * Title: 基站信息dao
 * Description: 
 * Copyright: Copyright (c)2014 
 * Company: com.yl.pay
 * 
 * @author zhendong.wu
 */
public interface LngAndLatDao {

	public LngAndLat create(LngAndLat baseStationInfo);
	public LngAndLat findByPosCati(String posCati);
	public LngAndLat update(LngAndLat baseStationInfo); 
}
