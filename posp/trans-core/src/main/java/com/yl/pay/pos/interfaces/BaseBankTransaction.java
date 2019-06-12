package com.yl.pay.pos.interfaces;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.yl.pay.pos.dao.SequenceDao;

/**
 * Title: 银行交易处理  - 基类
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: pay
 * @author haitao.liu
 */

public class BaseBankTransaction {
	
	private static final Log log = LogFactory.getLog(BaseBankTransaction.class);

	public SequenceDao sequenceDao;
	
	public String generateSequence(String seqSystemTrackeNumber){		
		return sequenceDao.generateSequence(seqSystemTrackeNumber);
	}

	public SequenceDao getSequenceDao() {
		return sequenceDao;
	}

	public void setSequenceDao(SequenceDao sequenceDao) {
		this.sequenceDao = sequenceDao;
	}
}
