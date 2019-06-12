package com.yl.pay.pos.interfaces.P500001;


import org.apache.log4j.Logger;

import com.pay.common.util.EsscUtil;
/**
 * - 初始化密钥
 * @author haitao.liu
 */
public class InitSecretKey  {
	private static final Logger logger = Logger.getLogger(InitSecretKey.class);
	public int execute(String bankPosCati,String zmkKey,String zmkValue) {
		logger.info("=============InitSecretKey  start============");
		int count = 0;
		try {
			//EsscUtil.initBankInterfaceKey("P500001_"+bankPosCati, zmkKey, zmkValue);
			count = 1;
		} catch (Exception e) {
			logger.info("",e);
		}
		logger.info("=============InitSecretKey  end============");
		return count;
	}
}
