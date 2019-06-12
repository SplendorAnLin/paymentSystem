package com.yl.pay.pos.service.impl;

import com.yl.pay.pos.dao.ParameterDao;
import com.yl.pay.pos.entity.Parameter;
import com.yl.pay.pos.service.IParameterService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Title: TMS参数 服务
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class ParameterServiceImpl implements IParameterService{
	
	private static final Log log = LogFactory.getLog(ParameterServiceImpl.class);
	
	public ParameterDao parameterDao;
	
	public boolean getUpdateFlag(String posCati, String version) {
		log.info("check parameter update flag: poscati=" + posCati + ", pos terminal current version is " + version);
		List<Parameter> list = parameterDao.findByPosCatiAndVersion(posCati, version);
		if(!list.isEmpty()){
			log.info("this pos has new version parmater!");
			return true;
		}else{
			log.info("this pos no new version parmater!");
			return false;
		}
	}
	
	public ParameterDao getParameterDao() {
		return parameterDao;
	}
	public void setParameterDao(ParameterDao parameterDao) {
		this.parameterDao = parameterDao;
	}

}

