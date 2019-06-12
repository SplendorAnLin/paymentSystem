package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.Parameter;

import java.util.List;


/**
 * Title: POS参数信息
 * Description:  POS参数信息
 * Copyright: Copyright (c)2010
 * Company: AFC
 * @author Administrator
 */

public interface ParameterDao {

	/**
	 * 根据posCATI, paramVersion查找POS参数信息
	 * @param posCATI,paramVersion
	 * @return
	 */
	public List<Parameter> findByPosCatiAndVersion(String posCATI, String version);

}
