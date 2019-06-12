package com.yl.pay.pos.service;

import com.yl.pay.pos.entity.TransCheckProfile;

import java.util.List;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author jun
 */
public interface ITransCheckProfileService {

	public TransCheckProfile create(TransCheckProfile transCheckProfile);

	public TransCheckProfile update(TransCheckProfile transCheckProfile);

	public TransCheckProfile findById(Long id);
	
	/**
	 * 根据条件查询， 默认取开通状态的
	 * @param bizType
	 * @param profileType
	 * @param profileValue
	 * @return
	 */
	public List<TransCheckProfile> findByBizTypeAndProfileTypeAndProfileValue(String bizType, String profileType, String profileValue);
	
	
	
}
