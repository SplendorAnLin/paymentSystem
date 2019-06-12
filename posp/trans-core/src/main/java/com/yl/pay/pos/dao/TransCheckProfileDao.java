package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.TransCheckProfile;

import java.util.List;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author jun
 */
public interface TransCheckProfileDao {
	
	public TransCheckProfile create(TransCheckProfile transCheckProfile);

	public TransCheckProfile update(TransCheckProfile transCheckProfile);

	public TransCheckProfile findById(Long id);
	
	/**
	 * 
	 * @param bizType
	 * @param profileType
	 * @param profileValue
	 * @return
	 */
	public List<TransCheckProfile> findByBizTypeAndProfileTypeAndProfileValue(String bizType, String profileType, String profileValue);

	 /**
	  *
	  * @param bizType
	  * @param profileType
	  * @param profileValue
	  * @return
	  */
	public List<TransCheckProfile> findByTypeAndValue(String bizType, String profileType, String profileValue);
}
