package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.Organization;


/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public interface OrganizationDao {
	
	public Organization findByCode(String code);
	
}
